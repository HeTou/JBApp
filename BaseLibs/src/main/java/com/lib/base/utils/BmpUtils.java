package com.lib.base.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.media.ExifInterface;

import com.lib.base.log.KLog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Function:图片压缩工具类
 */
public class BmpUtils {
    /**
     * 压缩图片并另存为新图
     * 【只需要指定最大宽度px，压缩过程中会按照原来的宽高比压缩】
     *
     * @param originFilePath 图片路径
     * @param reqWidth       最大宽度px
     * @param savedFilePath  新图存储路径
     * @param savedFileName  新图片名字
     * @return 新图绝对路径
     */
    public static String compressAndSavePicture(String originFilePath, int reqWidth, String savedFilePath, String savedFileName) {
        KLog.d("BmpUtils.compressAndSavePicture():originFilePath = " + originFilePath);
        KLog.d("BmpUtils.compressAndSavePicture():savedFilePath = " + savedFilePath);
        KLog.d("BmpUtils.compressAndSavePicture():savedFileName = " + savedFileName);
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(originFilePath, options);
        int originWidth = options.outWidth;
        int originHeight = options.outHeight;
        KLog.d("BmpUtils.compressAndSavePicture():originWidth = " + originWidth);
        KLog.d("BmpUtils.compressAndSavePicture():originHeight = " + originHeight);
        if (originWidth > reqWidth) {
            int reqHeight = (reqWidth * originHeight) / originWidth;
            KLog.d("BmpUtils.compressAndSavePicture():reqWidth = " + reqWidth);
            KLog.d("BmpUtils.compressAndSavePicture():reqHeight = " + reqHeight);
            options.inSampleSize = calculateInSampleSize(originWidth, originHeight, reqWidth, reqHeight);
        } else {
            options.inSampleSize = 1;
        }
        KLog.d("BmpUtils.compressAndSavePicture():inSampleSize = " + options.inSampleSize);
        options.inJustDecodeBounds = false;
        //获取压缩位图 Decode bitmap with inSampleSize set
        Bitmap scaleBmp = BitmapFactory.decodeFile(originFilePath, options);
        if (scaleBmp == null)
            return null;
        //质量压缩【经测试指定目标大小没用】
        Bitmap massCompressBmp = getMassCompressBmp(scaleBmp, 300);
        //检查图片旋转角度
        Bitmap finalBmp = adjustPicRotate(massCompressBmp, originFilePath);
        //保存图片到本地，并返回路径
        String newPath = saveBmp(finalBmp, savedFilePath, savedFileName);

        //回收资源
        scaleBmp.recycle();
        massCompressBmp.recycle();
        System.gc();

        return newPath;
    }

    //计算inSampleSize
    private static int calculateInSampleSize(int originWidth, int originHeight, int reqWidth, int reqHeight) {
        int inSampleSize = 1;
        if (originWidth > reqWidth || originHeight > reqHeight) {
            //Math.ceil(int value):表示取不小于value的最小整数
            int scaleWidth = (int) Math.ceil((originWidth * 1.0f) / reqWidth);
            int scaleHeight = (int) Math.ceil((originHeight * 1.0f) / reqHeight);
            inSampleSize = Math.max(scaleWidth, scaleHeight);
        }
        return inSampleSize;
    }

    /**
     * 将某个位图进行质量压缩【类型必须为JPEG】
     */
    public static Bitmap getMassCompressBmp(Bitmap image, int maxSize) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 40;
        image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        // 循环判断如果压缩后图片是否大于最大值,大于继续压缩
        while (baos.toByteArray().length / 1024 > maxSize) {
            options -= 10;// 每次都减少10
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 检查图片角度是否旋转了90度，如果是则反转
     *
     * @param bitmap 需要旋转的图片位图
     * @param path   图片的路径
     */
    public static Bitmap adjustPicRotate(Bitmap bitmap, String path) {
        int degree = getPicRotate(path);
        if (degree > 0) {
            Matrix m = new Matrix();
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            m.setRotate(degree); // 旋转angle度
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, m, true);// 从新生成图片
        }
        return bitmap;
    }

    /**
     * 读取图片文件旋转的角度
     *
     * @param path 图片绝对路径
     * @return 图片旋转的角度
     */
    public static int getPicRotate(String path) {
        int degree = 0;
        try {
            if (StringUtil.isEmpty(path)) {
                return -1;
            }
            ExifInterface exifInterface = new ExifInterface(path);

            int orientation =
                    exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            KLog.e("BmpUtils.getPicRotate():filePath = " + path + "\n获取图片旋转角度失败：" + e.toString());
        }
        return degree;
    }

    /**
     * 保存图片
     *
     * @param bitmap   需要保存的图片
     * @param saveName 图片保存的名称
     * @return 返回保存后的图片地址
     */
    public static String saveBmp(Bitmap bitmap, String savePath, String saveName) {
        String resultPath = null;
        try {
            //保存位置
            File file = new File(savePath, saveName);
            if (file.exists())
                file.delete();
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            bitmap.recycle();
            bitmap = null;
            System.gc();
            resultPath = file.getAbsolutePath();
        } catch (IOException e) {
            KLog.e("BmpUtils.saveBmp(): savePath = " + savePath + "\nsaveName = " + saveName + "\n保存图片失败：" + e.toString());
        }
        return resultPath;
    }

    /***
     * 合成新的图片（倒影）
     *
     * @param resId
     * @param context
     * @return
     */
    public static Bitmap getReverseBitmapById(int resId, Context context) {
        Bitmap sourceBitmap = BitmapFactory.decodeResource(context.getResources(), resId);
        //绘制原图的下一半图片
        Matrix matrix = new Matrix();
        //倒影翻转
        matrix.setScale(1, -1);

        //从原始位图剪切图像，这是一种高级的方式。可以用Matrix(矩阵)来实现旋转等高级方式截图
        Bitmap inverseBitmap = Bitmap.createBitmap(sourceBitmap, 0, sourceBitmap.getHeight() / 2, sourceBitmap.getWidth(), sourceBitmap.getHeight() / 3, matrix, false);
        //创建新的位图（用于合成两张bitmap）
        Bitmap groupbBitmap = Bitmap.createBitmap(sourceBitmap.getWidth(), sourceBitmap.getHeight() + sourceBitmap.getHeight() / 3 + 60, sourceBitmap.getConfig());
        //以合成图片为画布
        Canvas gCanvas = new Canvas(groupbBitmap);
        //将原图和倒影图片画在合成图片上
        gCanvas.drawBitmap(sourceBitmap, 0, 0, null);
        gCanvas.drawBitmap(inverseBitmap, 0, sourceBitmap.getHeight() + 50, null);
        //添加遮罩
        Paint paint = new Paint();
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        LinearGradient shader = new LinearGradient(0, sourceBitmap.getHeight() + 50, 0,groupbBitmap.getHeight(), Color.BLACK, Color.TRANSPARENT, tileMode);
        paint.setShader(shader);
        //这里取矩形渐变区和图片的交集
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        gCanvas.drawRect(0, sourceBitmap.getHeight() + 50, sourceBitmap.getWidth(), groupbBitmap.getHeight(), paint);
        return groupbBitmap;
    }
}


/***
 * 从原始位图剪切图像，这是一种高级的方式。可以用Matrix(矩阵)来实现旋转等高级方式截图
    参数说明：
 　　 Bitmap source：要从中截图的原始位图
 　　 int x:    起始x坐标
 　　 int y：    起始y坐标
     int width:     要截的图的宽度
     int height：    要截的图的宽度
     Matrix m：  矩阵,可以进行方向的改变
     Bitmap.Config  config：一个枚举类型的配置，可以定义截到的新位图的质量
     boolean filter:     当进行的不只是平移变换时，filter参数为true可以进行滤波处理，有助于改善新图像质量;flase时，计算机不做过滤处理。

     返回值：返回一个剪切好的Bitmap

     public static Bitmap createBitmap (Bitmap source, int x, int y, int width, int height, Matrix m, boolean filter)
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 * */
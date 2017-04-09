package com.programmer.jbapp.module.view.item;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.programmer.jbapp.R;
import com.programmer.jbapp.framework.AbsBaseActivity;
import com.programmer.jbapp.framework.ItemInfo;

/**
 * zft
 * 2017/4/7.
 */

public class Widget_SnackBarActivity extends AbsBaseActivity implements ItemInfo, View.OnClickListener {
    /**
     * 显示SnackBar
     */
    private Button mBtn1,mBtn2,mBtn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.snackbar_activity);
        initUI();
    }

    private void initUI() {
        mBtn1 = (Button) findViewById(R.id.btn1);
        mBtn1.setOnClickListener(this);

        mBtn2 = (Button) findViewById(R.id.btn2);
        mBtn2.setOnClickListener(this);

        mBtn3 = (Button) findViewById(R.id.btn3);
        mBtn3.setOnClickListener(this);
    }


    @Override
    public String getItemName() {
        return "SnackBar的使用";
    }

    @Override
    public String getItemDec() {
        return "5.0新控件的使用！";
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                Snackbar snackbar = Snackbar.make(getWindow().getDecorView(),"我是snackbar",Snackbar.LENGTH_SHORT)
                        .setAction("我是action", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(Widget_SnackBarActivity.this, "snackbar-action的点击", Toast.LENGTH_SHORT).show();
                            }
                        });
                snackbar.show();
                break;
            case R.id.btn2: //修改颜色
                Snackbar snackbar2 = Snackbar.make(getWindow().getDecorView(),"我是snackbar",Snackbar.LENGTH_SHORT);
                setSnackbarColor(snackbar2,0xff999999,0xffeeffff);
                snackbar2.show();
                break;
            case R.id.btn3:
                Snackbar snackbar3 = Snackbar.make(getWindow().getDecorView(),"我是snackbar",Snackbar.LENGTH_SHORT);
                setSnackbarColor(snackbar3,0xff009999,0xff00ffff);
                SnackbarAddView(snackbar3,R.layout.snackbar_view,0);
                snackbar3.show();
                break;
        }
    }


    /***
     * 修改snackbar的颜色
     * @param snackbar
     * @param messageColor  消息的颜色
     * @param backgroundColor   背景颜色
     */
    public  void setSnackbarColor(Snackbar snackbar, int messageColor, int backgroundColor) {
        View view = snackbar.getView();//获取Snackbar的view
        if(view!=null){
            view.setBackgroundColor(backgroundColor);//修改view的背景色
            ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(messageColor);//获取Snackbar的message控件，修改字体颜色
        }
    }

    /***
     * 添加View
     * @param snackbar
     * @param layoutId
     * @param index
     */
    public static void SnackbarAddView(Snackbar snackbar,int layoutId,int index) {
        View snackbarview = snackbar.getView();//获取snackbar的View(其实就是SnackbarLayout)

        Snackbar.SnackbarLayout snackbarLayout=(Snackbar.SnackbarLayout)snackbarview;//将获取的View转换成SnackbarLayout

        View add_view = LayoutInflater.from(snackbarview.getContext()).inflate(layoutId,null);//加载布局文件新建View

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);//设置新建布局参数

        p.gravity= Gravity.CENTER_VERTICAL;//设置新建布局在Snackbar内垂直居中显示

        snackbarLayout.addView(add_view,index,p);//将新建布局添加进snackbarLayout相应位置
    }
}

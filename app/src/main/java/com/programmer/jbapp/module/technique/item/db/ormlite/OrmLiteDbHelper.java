package com.programmer.jbapp.module.technique.item.db.ormlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.programmer.jbapp.common.bean.BankCard;
import com.programmer.jbapp.common.bean.User;

import java.sql.SQLException;

/**
 * zft
 * 2017/4/13.
 * OrmLite数据库的使用
 */

public class OrmLiteDbHelper extends OrmLiteSqliteOpenHelper{

    private static final String TABLE_NAME = "tb_ormlite";
    private static final int   TABLE_VERSION = 1;

    public OrmLiteDbHelper(Context context){
        this(context,TABLE_NAME,null,TABLE_VERSION);
    }
    /***
     *
     * @param context       上下文
     * @param databaseName  数据库名字
     * @param factory       游标工厂对象
     * @param databaseVersion   数据库版本
     */
    public OrmLiteDbHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
    }

    /****
     * 创建表的语句
     * @param sqLiteDatabase
     * @param connectionSource
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, BankCard.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }


//  单例模式
    private static OrmLiteDbHelper instance;
    public static  OrmLiteDbHelper  getInstance(Context context){
        if(instance==null){
            instance = new OrmLiteDbHelper(context);
        }
        return instance;
    }
}

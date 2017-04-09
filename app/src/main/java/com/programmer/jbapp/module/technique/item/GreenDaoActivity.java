package com.programmer.jbapp.module.technique.item;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.programmer.jbapp.R;
import com.programmer.jbapp.common.bean.User;
import com.programmer.jbapp.common.bean.UserDao;
import com.programmer.jbapp.common.db.GreenDaoHelper;
import com.programmer.jbapp.framework.AbsBaseActivity;
import com.programmer.jbapp.framework.ItemInfo;

/**
 * zft
 * 2017/4/7.
 */

public class GreenDaoActivity extends AbsBaseActivity implements ItemInfo, View.OnClickListener {
    /**
     * 增加
     */
    private Button mAdd;
    /**
     * 删除
     */
    private Button mDel;
    /**
     * 修改
     */
    private Button mUpdate;
    /**
     * 查找
     */
    private Button mQuery;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.greendao_activity);
        initUI();
        initData();
    }

    private void initData() {
        userDao = GreenDaoHelper.getDaoSession().getUserDao();
    }

    @Override
    public String getItemName() {
        return "GreenDao数据库的使用";
    }

    @Override
    public String getItemDec() {
        return "推荐使用教程https://www.daidingkang.cc/2016/12/08/GreenDao/";
    }

    private void initUI() {
        mAdd = (Button) findViewById(R.id.add);
        mAdd.setOnClickListener(this);
        mDel = (Button) findViewById(R.id.del);
        mDel.setOnClickListener(this);
        mUpdate = (Button) findViewById(R.id.update);
        mUpdate.setOnClickListener(this);
        mQuery = (Button) findViewById(R.id.query);
        mQuery.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                User user = new User((long)1,"中分套",0);
                long insert = userDao.insert(user);
                break;
            case R.id.del:
                break;
            case R.id.update:
                break;
            case R.id.query:
                break;
        }
    }
}

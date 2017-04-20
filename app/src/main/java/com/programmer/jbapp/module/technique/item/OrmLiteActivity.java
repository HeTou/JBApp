package com.programmer.jbapp.module.technique.item;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.programmer.jbapp.R;
import com.programmer.jbapp.common.bean.BankCard;
import com.programmer.jbapp.common.bean.User;
import com.programmer.jbapp.framework.AbsBaseActivity;
import com.programmer.jbapp.framework.ItemInfo;
import com.programmer.jbapp.module.technique.item.db.ormlite.dao.BankCardDao;
import com.programmer.jbapp.module.technique.item.db.ormlite.dao.UserDao;

import java.util.ArrayList;
import java.util.List;

/**
 * zft
 * 2017/4/13.
 * 缺点：1、不支持复合主键
 *      2、一对多，支持的是ForeignCollection,而不是List，用到这里感觉（还是greenDao不错）
 */

public class OrmLiteActivity extends AbsBaseActivity implements ItemInfo, View.OnClickListener {

    /**
     * 增加
     */
    private Button mAdd, mAdd_all;
    /**
     * 删除
     */
    private Button mDel, mDel_all;
    /**
     * 修改
     */
    private Button mUpdate, mUpdate_all;
    /**
     * 查找
     */
    private Button mQuery;
    private Button mAddCard;

    private UserDao mUserDao;
    private BankCardDao mBankCardDao;

    private List<User> oldList = new ArrayList<>();
    private List<User> newList = new ArrayList<>();
    /**
     * 增加全部
     */
    private Button mAddAll;
    /**
     * 删除全部
     */
    private Button mDelAll;
    /**
     * 修改全部
     */
    private Button mUpdateAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ormlite_activity);
        initView();
        mUserDao = new UserDao(this);
        mBankCardDao = new BankCardDao(this);
        initData();
    }

    private void initData() {
        for (long i = 0; i < 20; i++) {
            User user = new User(i, "钟奋韬", 0);
            User user2 = new User(i, "韬哥", 0);
            oldList.add(user);
            newList.add(user2);
        }
    }

    @Override
    public String getItemName() {
        return "Orm数据库的使用";
    }

    @Override
    public String getItemDec() {
        return "去http://ormlite.com/releases/下载两个包：一个是ormlite-core-x.xx.jar，另一个是ormlite-android-x.xx.jar";
    }

    int change = 0;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add: {
                User user = new User(9L, "中分套", 0);
                int add = mUserDao.add(user);
                Toast.makeText(this, "添加：" + add, Toast.LENGTH_SHORT).show();
            }
            break;
            case R.id.del: {
                User user = new User(9L, "中分套", 0);
                int del = mUserDao.del(user);
                Toast.makeText(this, "删除：" + del, Toast.LENGTH_SHORT).show();
            }
            break;
            case R.id.update: {
                User user = new User(9L, "钟奋韬", 0);
                int update = mUserDao.update(user);
                Toast.makeText(this, "更新：" + update, Toast.LENGTH_SHORT).show();
            }
            break;
            case R.id.query: {
                List<User> users = mUserDao.queryAll();
                Toast.makeText(this, users.toString(), Toast.LENGTH_SHORT).show();
            }
            break;
            case R.id.add_all: {
                mUserDao.addOrUpdateAll(oldList);
            }
            break;
            case R.id.del_all: {
                mUserDao.delAll(oldList);
            }
            break;
            case R.id.update_all: {
                mUserDao.updateAll(newList);
            }
            break;
            case R.id.addCard:
                BankCard card = new BankCard();
                card.setCardId("123456"+change);
                card.setCardName("ft银行");
//                card.setUser(new User(9L, "中分套", 0));
                mBankCardDao.addOrUpdate(card);
                change++;
                break;
        }
    }

    private void initView() {

        mAdd = (Button) findViewById(R.id.add);
        mAdd.setOnClickListener(this);
        mAddAll = (Button) findViewById(R.id.add_all);
        mAddAll.setOnClickListener(this);
        mDel = (Button) findViewById(R.id.del);
        mDel.setOnClickListener(this);
        mDelAll = (Button) findViewById(R.id.del_all);
        mDelAll.setOnClickListener(this);
        mUpdate = (Button) findViewById(R.id.update);
        mUpdate.setOnClickListener(this);
        mUpdateAll = (Button) findViewById(R.id.update_all);
        mUpdateAll.setOnClickListener(this);
        mQuery = (Button) findViewById(R.id.query);
        mQuery.setOnClickListener(this);
        mAddCard = (Button) findViewById(R.id.addCard);
        mAddCard.setOnClickListener(this);
    }
}

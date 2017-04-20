package com.programmer.jbapp.module.technique.item.db.ormlite.dao;

import android.content.Context;

import com.programmer.jbapp.common.bean.User;
import com.programmer.jbapp.module.technique.item.db.ormlite.BaseDao;

/**
 * zft
 * 2017/4/13.
 */

public class UserDao extends BaseDao<User>{

    public UserDao(Context context) {
        super(context);
    }

    @Override
    public Class<User> getType() {
        return User.class;
    }
}

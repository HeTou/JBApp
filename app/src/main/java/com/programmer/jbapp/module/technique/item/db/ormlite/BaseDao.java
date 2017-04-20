package com.programmer.jbapp.module.technique.item.db.ormlite;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * zft
 * 2017/4/13.
 */

public abstract class BaseDao<T> {
    OrmLiteDbHelper helper;
    public Dao<T, ?> dao;

    public abstract Class<T> getType();

    public BaseDao(Context context) {
        helper = OrmLiteDbHelper.getInstance(context);
        try {
            dao = helper.getDao(getType());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao<T, ?> getDao() {
        return dao;
    }

    // 增
    public int add(T t) {
        int i = 0;
        try {
            i = dao.create(t);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    //添加全部
    public long addAll(Collection<T> collection) {
        int result = 0;
        try {
            result = dao.create(collection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // 删
    public int del(T t) {
        int result = 0;
        try {
            result = dao.delete(t);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //删除全部
    public int delAll(Collection<T> collection) {
        int result = 0;
        try {
            result = dao.delete(collection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //更新
    public int update(T t) {
        int result = 0;
        try {
            result = dao.update(t);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //删除全部
    public int updateAll(List<T> list) {
        int result = 0;
        for (T t : list) {
            try {
                result += dao.update(t);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    // 查询全部
    public List<T> queryAll() {
        try {
            List<T> ts = dao.queryForAll();
            return ts;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 添加或更新
     * @param t
     */
    public void addOrUpdate(T t) {
        try {
            dao.createOrUpdate(t);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /***
     * 添加或更新
     * @param list
     */
    public void addOrUpdateAll(List<T> list) {
        for (T t : list) {
            try {
                dao.createOrUpdate(t);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

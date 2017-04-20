package com.programmer.jbapp.module.technique.item.db.ormlite.dao;

import android.content.Context;

import com.programmer.jbapp.common.bean.BankCard;
import com.programmer.jbapp.module.technique.item.db.ormlite.BaseDao;

/**
 * zft
 * 2017/4/14.
 */

public class BankCardDao extends BaseDao<BankCard> {
    public BankCardDao(Context context) {
        super(context);
    }

    @Override
    public Class<BankCard> getType() {
        return BankCard.class;
    }

}

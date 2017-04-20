package com.programmer.jbapp.common.bean;

import com.j256.ormlite.field.DatabaseField;

/**
 * zft
 * 2017/4/13.
 */

public class BankCard {
    private long id;
    @DatabaseField(id = true)
    private String cardId;
    @DatabaseField
    private String cardName;

    @DatabaseField(foreign = true,foreignColumnName = "id",columnName = "userid")
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "BankCard{" +
                "id=" + id +
                ", cardId='" + cardId + '\'' +
                ", cardName='" + cardName + '\'' +
                '}';
    }
}

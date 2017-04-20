package com.programmer.jbapp.common.bean;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;

/**
 * zft
 * 2017/4/7.
 *
 *
 * 2、基础属性注解
 * @Id :主键 Long型，可以通过@Id(autoincrement = true)设置自增长
 * @Property ：设置一个非默认关系映射所对应的列名，默认是的使用字段名 举例：@Property (nameInDb="name")
 * @NotNul ：设置数据库表当前列不能为空
 * @Transient 基础属性注解：添加次标记之后不会生成数据库表的列
 */
@Entity
public class User {
    @Id(autoincrement = true)
    @DatabaseField(id =true)
    private Long id;

    @Property(nameInDb = "USERNAME")
    @DatabaseField()
    private String name;

    @NotNull
    @DatabaseField
    private int repos;

    @Transient
    @ForeignCollectionField() // 必须
    private ForeignCollection<BankCard> cardsList;

    @Transient
    private int tempUsageCount;


    @Generated(hash = 2019277320)
    public User(Long id, String name, int repos) {
        this.id = id;
        this.name = name;
        this.repos = repos;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRepos() {
        return repos;
    }

    public void setRepos(int repos) {
        this.repos = repos;
    }

    public int getTempUsageCount() {
        return tempUsageCount;
    }

    public void setTempUsageCount(int tempUsageCount) {
        this.tempUsageCount = tempUsageCount;
    }

    public ForeignCollection<BankCard> getCardsList() {
        return cardsList;
    }

    public void setCardsList(ForeignCollection<BankCard> cardsList) {
        this.cardsList = cardsList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", repos=" + repos +
                ", cardsList=" + cardsList +
                '}';
    }
}

package com.androidsoft.mynotes_2017144235.pojo;

import java.io.Serializable;

/**
 * 存储用户信息实体类
 *
 * 注：1.表中列名---USER_ID   实体列---userId
 *     2.根据该实体类创建SQLite表
 */

public class User implements Serializable {

    private long userId;           //用户编号
    private String userPhone;     //用户电话
    private String userName;      //用户名
    private String userPassword;  //用户密码
    private int userStatus;       //用户状态
    private int userRole;         //用户角色

    public User() {
    }

    public User(String userPhone, String userName, String userPassword, int userStatus, int userRole) {
        this.userPhone= userPhone;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userStatus = userStatus;
        this.userRole = userRole;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userStatus=" + userStatus +
                ", userRole=" + userRole +
                '}';
    }
}

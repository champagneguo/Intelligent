package com.intelligent.greendao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table USER.
 */
public class user {

    private Long userid;
    private String username;
    private String name;
    private String bumen;
    private String password;
    private String sex;
    private String mobile;
    private String guanliyuan;

    public user() {
    }

    public user(Long userid) {
        this.userid = userid;
    }

    public user(Long userid, String username, String name, String bumen, String password, String sex, String mobile, String guanliyuan) {
        this.userid = userid;
        this.username = username;
        this.name = name;
        this.bumen = bumen;
        this.password = password;
        this.sex = sex;
        this.mobile = mobile;
        this.guanliyuan = guanliyuan;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBumen() {
        return bumen;
    }

    public void setBumen(String bumen) {
        this.bumen = bumen;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGuanliyuan() {
        return guanliyuan;
    }

    public void setGuanliyuan(String guanliyuan) {
        this.guanliyuan = guanliyuan;
    }

}

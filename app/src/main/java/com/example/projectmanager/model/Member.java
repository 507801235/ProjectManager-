package com.example.projectmanager.model;

public class Member {
    private Integer account_uid;
    private String username;
    private String password;
    private String avatarUrl;
    private String nickName;
    private String realName;
    private String position;
    private String phoneNum;
    private String synopsis;

    public Integer getAccount_uid() {
        return account_uid;
    }

    public void setAccount_uid(Integer account_uid) {
        this.account_uid = account_uid;
    }

    public String getName() {
        if (this.realName == null) {
            return this.nickName;
        }
        return this.nickName + "(" + this.realName + ")";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    @Override
    public String toString() {
        return "Member{" +
                "account_uid=" + account_uid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", nickName='" + nickName + '\'' +
                ", realName='" + realName + '\'' +
                ", position='" + position + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", synopsis='" + synopsis + '\'' +
                '}';
    }
}
package com.itheima.domain;

import java.util.Date;

public class Discuss {
    private String did;
    private  String article;
    private Date distime;
    private String uid;
    private String title;
    private String image;
    private String username;



    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public Date getDistime() {
        return distime;
    }

    public void setDistime(Date distime) {
        this.distime = distime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

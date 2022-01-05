package com.jiuhong.mytvapplication.bean;

import android.graphics.drawable.Drawable;

public class PictureInfo {
    private int page;
    private int index;
    private Drawable appIcon;
    private String appName;
    private String packageName;


    public String getPicdz() {
        return picdz;
    }

    public void setPicdz(String picdz) {
        this.picdz = picdz;
    }

    public String picdz;

    public PictureInfo() {
        this.appIcon = null;
        this.appName = null;
        this.packageName = null;
        this.picdz = null;





    }


    public Drawable getAppIcon() {
        return this.appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
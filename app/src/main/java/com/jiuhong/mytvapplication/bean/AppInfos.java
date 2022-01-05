package com.jiuhong.mytvapplication.bean;

import android.graphics.drawable.Drawable;

public class AppInfos {
    private int page;
    private int index;

    private String appName;
    private String packageName;

    public AppInfos() {

        this.appName = null;
        this.packageName = null;





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

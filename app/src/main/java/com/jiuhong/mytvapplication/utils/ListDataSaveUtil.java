package com.jiuhong.mytvapplication.utils;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiuhong.mytvapplication.bean.AppInfo;

import java.util.List;

public class ListDataSaveUtil {
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    public static void setPreferences(SharedPreferences preferences) {
        ListDataSaveUtil.preferences = preferences;
    }

    /**
     * 保存List和当前歌曲号
     *
     * @param listtag
     * @param datalist
     */
    public static void setDataList(String listtag, List<AppInfo> datalist) {
        if (null == datalist || datalist.size() <= 0)
            return;

        Gson gson = new Gson();
        //转换成json数据，再保存
        editor = preferences.edit();
        String Json = gson.toJson(datalist);
        editor.putString(listtag, Json);
        editor.commit();
    }

    /**
     * 当前歌曲号
     *
     * @param indextag
     * @param index
     */
    public static void setIndex(String indextag, int index) {
        editor = preferences.edit();
        editor.putInt(indextag, index);
        editor.commit();
    }

    /**
     * 获取List
     *
     * @param tag
     * @return
     */
    public static List<AppInfo> getDataList(String tag) {
        List<AppInfo> datalist;
        String Json = preferences.getString(tag, null);
        if (null == Json) {
            return null;
        }

        Gson gson = new Gson();
        datalist = gson.fromJson(Json, new TypeToken<List<AppInfo>>() {

        }.getType());
        return datalist;

    }



}

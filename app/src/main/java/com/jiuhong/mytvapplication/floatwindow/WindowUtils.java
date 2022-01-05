package com.jiuhong.mytvapplication.floatwindow;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.ArrayList;
import java.util.List;

public class WindowUtils {

    public static WindowUtils getInstance() {
        WindowUtils windowUtils = null ;
        if (windowUtils==null){
            windowUtils = new WindowUtils();
            return windowUtils;
        }
        return windowUtils;
    }

    /**
     * 获得属于桌面的应用的应用包名称
     * 返回包含所有包名的字符串列表数组
     * @return
     */
    public List<String> getHomeApplicationList(Context context) {
        List<String> names = new ArrayList<String>();
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo  resolveInfo : resolveInfos) {
            names.add(resolveInfo.activityInfo.packageName);
        }
        return names;
    }

    /**
     * 判断当前界面是否是桌面
     */
    public boolean isAtHome(Context context) {
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = mActivityManager.getRunningTasks(1);
        return getHomeApplicationList(context).contains(runningTaskInfos.get(0).topActivity.getPackageName());
    }




}

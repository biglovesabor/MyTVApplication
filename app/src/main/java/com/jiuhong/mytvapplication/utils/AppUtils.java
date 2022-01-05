package com.jiuhong.mytvapplication.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.jiuhong.mytvapplication.bean.AppInfo;
import com.jiuhong.mytvapplication.bean.AppInfos;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class AppUtils {


    /**
     * get all app info
     *
     * @param context
     * @return
     */
    public static final ArrayList<AppInfos> getAppInfo(Context context) {
        ArrayList<AppInfos> appInfos = new ArrayList<AppInfos>();
        PackageManager manager = context.getPackageManager();
        Intent mainIntent = new Intent("android.intent.action.MAIN", null);
        mainIntent.addCategory("android.intent.category.LAUNCHER");
        //通过查看是否有LAUNCHER属性来添加
        List<ResolveInfo> resolveInfos = manager.queryIntentActivities(mainIntent, 0);
        Collections.sort(resolveInfos, new ResolveInfo.DisplayNameComparator(manager));
        if (appInfos != null) {
            appInfos.clear();
            for (ResolveInfo resolveInfo : resolveInfos) {
                if (resolveInfo.activityInfo.packageName.equals("com.mstar.tv.tvplayer.ui")) {
                    continue;
                }
                //这里是过滤apk，可以加入一些你认为不需要显示的apk
                AppInfos appInfo = new AppInfos();
//                appInfo.setAppIcon(resolveInfo.loadIcon(manager));
                appInfo.setAppName((String) resolveInfo.loadLabel(manager));
                appInfo.setPackageName(resolveInfo.activityInfo.packageName);
                appInfos.add(appInfo);
            }
        }
        return appInfos;
    }




    /**
     * get all app info
     *
     * @param context
     * @return
     */
    public static final ArrayList<AppInfo> getAppInfosutils(Context context) {
        ArrayList<AppInfo> appInfos = new ArrayList<AppInfo>();
        PackageManager manager = context.getPackageManager();
        Intent mainIntent = new Intent("android.intent.action.MAIN", null);
        mainIntent.addCategory("android.intent.category.LAUNCHER");
        //通过查看是否有LAUNCHER属性来添加
        List<ResolveInfo> resolveInfos = manager.queryIntentActivities(mainIntent, 0);
        Collections.sort(resolveInfos, new ResolveInfo.DisplayNameComparator(manager));
        if (appInfos != null) {
            appInfos.clear();
            for (ResolveInfo resolveInfo : resolveInfos) {
                if (resolveInfo.activityInfo.packageName.equals("com.mstar.tv.tvplayer.ui")) {
                    continue;
                }
                //这里是过滤apk，可以加入一些你认为不需要显示的apk

                AppInfo appInfo = new AppInfo();
                appInfo.setAppIcon(resolveInfo.loadIcon(manager));
                appInfo.setAppName((String) resolveInfo.loadLabel(manager));
                appInfo.setPackageName(resolveInfo.activityInfo.packageName);
                if (appInfo.getAppName().equals("设置中心")||appInfo.getAppName().equals("文件管理")||appInfo.getAppName().equals("时钟")){
                    appInfos.add(appInfo);
                }
            }
        }
        return appInfos;
    }




    /**
     * get all app info
     *
     * @param context
     * @return
     */
    public static final ArrayList<AppInfo> getAppInfosutilsguolv(Context context) {
        ArrayList<AppInfo> appInfos = new ArrayList<AppInfo>();
        PackageManager manager = context.getPackageManager();
        Intent mainIntent = new Intent("android.intent.action.MAIN", null);
        mainIntent.addCategory("android.intent.category.LAUNCHER");
        //通过查看是否有LAUNCHER属性来添加
        List<ResolveInfo> resolveInfos = manager.queryIntentActivities(mainIntent, 0);
        Collections.sort(resolveInfos, new ResolveInfo.DisplayNameComparator(manager));
        if (appInfos != null) {
            appInfos.clear();
            for (ResolveInfo resolveInfo : resolveInfos) {
                if (resolveInfo.activityInfo.packageName.equals("com.mstar.tv.tvplayer.ui")) {
                    continue;
                }
                //这里是过滤apk，可以加入一些你认为不需要显示的apk

                AppInfo appInfo = new AppInfo();
                appInfo.setAppIcon(resolveInfo.loadIcon(manager));
                appInfo.setAppName((String) resolveInfo.loadLabel(manager));
                appInfo.setPackageName(resolveInfo.activityInfo.packageName);
                if (appInfo.getAppName().equals("设置中心")||appInfo.getAppName().equals("文件管理")||appInfo.getAppName().equals("相机")||appInfo.getAppName().equals("计算器")){
                    appInfos.add(appInfo);
                }
            }
        }
        return appInfos;
    }

    private static SharedPreferences sp;
    private static SharedPreferences.Editor editor;

    public static void init(Context context) {
        // 如果这个上下文不是全局的上下文，就自动换成全局的上下文
        if (context != context.getApplicationContext()) {
            context = context.getApplicationContext();
        }
        sp = context.getSharedPreferences("common", Context.MODE_PRIVATE);
        editor = sp.edit();
    }



    /** 删除文件，可以是文件或文件夹
     * @param delFile 要删除的文件夹或文件名
     * @return 删除成功返回true，否则返回false
     */
    public static boolean delete(String delFile) {
        File file = new File(delFile);
        if (!file.exists()) {
//            Toast.makeText(HnUiUtils.getContext(), "删除文件失败:" + delFile + "不存在！", Toast.LENGTH_SHORT).show();
//            Log.e("删除文件失败:" + delFile + "不存在！");
            return false;
        } else {
            if (file.isFile())
                return deleteSingleFile(delFile);
            else
                return deleteDirectory(delFile);
        }
    }

    /** 删除单个文件
     * @param filePath$Name 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteSingleFile(String filePath$Name) {
        File file = new File(filePath$Name);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                Log.e("--Method--", "Copy_Delete.deleteSingleFile: 删除单个文件" + filePath$Name + "成功！");
                return true;
            } else {
//                Log.e("删除单个文件" + filePath$Name + "失败！");
                return false;
            }
        } else {
//            Log.e("删除单个文件失败：" + filePath$Name + "不存在！");
            return false;
        }
    }

    /** 删除目录及目录下的文件
     * @param filePath 要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String filePath) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!filePath.endsWith(File.separator))
            filePath = filePath + File.separator;
        File dirFile = new File(filePath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
//            Log.e("删除目录失败：" + filePath + "不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (File file : files) {
            // 删除子文件
            if (file.isFile()) {
                flag = deleteSingleFile(file.getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (file.isDirectory()) {
                flag = deleteDirectory(file
                        .getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
//            Log.e("删除目录失败！");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            Log.e("--Method--", "Copy_Delete.deleteDirectory: 删除目录" + filePath + "成功！");
            return true;
        } else {
//            Log.e("删除目录：" + filePath + "失败！");
            return false;
        }
    }



    // 获取最大宽度
    public static int getMaxWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService( Context.WINDOW_SERVICE );
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics( dm );
        return dm.widthPixels;
    }
    // 获取最大高度
    public static int getMaxHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService( Context.WINDOW_SERVICE );
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics( dm );
        return dm.heightPixels;
    }











}

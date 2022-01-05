package com.jiuhong.mytvapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.hjq.toast.ToastUtils;
import com.jiuhong.mytvapplication.adapter.AllAdapter;
import com.jiuhong.mytvapplication.adapter.BottomAdapter;
import com.jiuhong.mytvapplication.adapter.TestAdapter;
import com.jiuhong.mytvapplication.bean.AppInfo;
import com.jiuhong.mytvapplication.bean.AppInfos;
import com.jiuhong.mytvapplication.bean.PictureInfo;
import com.jiuhong.mytvapplication.floatwindow.EventMessage;
import com.jiuhong.mytvapplication.utils.AppUtils;
import com.jiuhong.mytvapplication.utils.DateSave;
import com.jiuhong.mytvapplication.utils.ListDataSaveUtil;
import com.jiuhong.mytvapplication.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class OneFramgent extends Fragment implements View.OnClickListener {
    private TextView text;
    private RecyclerView  top_recycle;
    private RecyclerView  bottom_recycle;
    private LinearLayoutManager linearLayoutManager;
    private List<AppInfos> allApp;
    private List<AppInfos> newallApp;
    private TestAdapter testAdapter;
    private List<HashMap<String, Object>> maps;
    private LinearLayout clear_all1;
    private LinearLayout clear_all2;
    private String filePath;
    private BottomAdapter bottomAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one_layout, container, false);
        initView(view);
        onHiddenChanged(false);
        return view;
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            //相当于Fragment的onResume
            Log.e("onHiddenChanged", "onHiddenChanged: "+"one显示了");

            Log.e("接收appinfo", "onMessageEvent: "+SPUtils.getString("savelist", ""));
            AppInfos appInfo = new Gson().fromJson(SPUtils.getString("savelist", ""), AppInfos.class);
            newallApp = new ArrayList<>();
            newallApp.add(appInfo);

            allApp.addAll(0,newallApp);
            testAdapter = new TestAdapter(getContext(),allApp);
        } else {
            //相当于Fragment的onPause
            Log.e("onHiddenChanged", "onHiddenChanged: "+"one隐藏了");
        }
    }


    /**
     * 从sd卡获取图片资源
     * @return
     */
    private List<String> getImagePathFromSD() {
        // 图片列表
        List<String> imagePathList = new ArrayList<String>();
        // 得到sd卡内image文件夹的路径   File.separator(/)
        filePath = Environment.getExternalStorageDirectory().toString() + File.separator
                + "/MayPic/Part";
//                + "Pictures";
        // 得到该路径文件夹下所有的文件
        File fileAll = new File(filePath);
        File[] files = fileAll.listFiles();
      if (files!=null){
          // 将所有的文件存入ArrayList中,并过滤所有图片格式的文件
          for (int i = 0; i < files.length; i++) {
              File file = files[i];
              if (checkIsImageFile(file.getPath())) {
                  imagePathList.add(file.getPath());
              }
          }
      }
        // 返回得到的图片列表
        return imagePathList;
    }

    /**
     * 检查扩展名，得到图片格式的文件
     * @param fName  文件名
     * @return
     */
    @SuppressLint("DefaultLocale")
    private boolean checkIsImageFile(String fName) {
        boolean isImageFile = false;
        // 获取扩展名
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if (FileEnd.equals("jpg") || FileEnd.equals("png") || FileEnd.equals("gif")
                || FileEnd.equals("jpeg")|| FileEnd.equals("bmp") ) {
            isImageFile = true;
        } else {
            isImageFile = false;
        }
        return isImageFile;
    }

    private void initView(View view) {

        ButterKnife.bind(getActivity());

        text = (TextView) view.findViewById(R.id.text);
        top_recycle = view.findViewById(R.id.top_recycle);
        bottom_recycle = view.findViewById(R.id.bottom_recycle);
        clear_all1 = view.findViewById(R.id.clear_all1);
        clear_all2 = view.findViewById(R.id.clear_all2);
        clear_all1.setOnClickListener(this);
        clear_all2.setOnClickListener(this);


        AppInfos appInfo = new Gson().fromJson(SPUtils.getString("appinfo", ""), AppInfos.class);
        Log.e("接收appinfo", "onMessageEvent: "+appInfo);
        allApp=new ArrayList<>();
        if (appInfo!=null){
            allApp.add(appInfo);
        }
//        allApp = AppUtils.getAppInfos(getContext());



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,true);

        top_recycle.setLayoutManager(linearLayoutManager);


        reloadButtons(getActivity(),maps,5);


        if (maps!=null){
            for (int i = 0; i < maps.size(); i++) {
                Map map = maps.get(i);


                Iterator it = map.keySet().iterator();

                while(it.hasNext()) {

                    String key = (String) it.next();

                    Object value = map.get(key);

                    System.out.println(key+":"+value);

                }
            }
        }






        testAdapter = new TestAdapter(getContext(),allApp);


        top_recycle. addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if(parent.getChildPosition(view)!=(allApp.size()-1)){
                    outRect.left = -132;
                }
            }
        });

        top_recycle.setAdapter(testAdapter);


        List<String> imagePathFromSD = getImagePathFromSD();
        List<PictureInfo> infos = new ArrayList<>();
        for (String str : imagePathFromSD) {
            PictureInfo info = new PictureInfo();
            info.picdz = str;
            infos.add(info);
        }


        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,true);

        bottom_recycle.setLayoutManager(linearLayoutManager2);

        bottomAdapter = new BottomAdapter(getContext(),infos);

        bottom_recycle. addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if(parent.getChildPosition(view)!=(infos.size()-1)){
                    outRect.left = -132;
                }
            }
        });
        bottomAdapter.setOnItemClickListener(new BottomAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                new AlertDialog.Builder(getContext()).setTitle("确定删除当前文件？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //点击确定触发的事件
                                AppUtils.deleteSingleFile(imagePathFromSD.get(position));
                                bottomAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //点击取消触发的事件
                            }
                        }).show();
            }
        });
        bottom_recycle.setAdapter(bottomAdapter);





    }


    public static void reloadButtons(Activity activity, List<HashMap<String, Object>> appInfos,
                                     int appNumber) {
        int MAX_RECENT_TASKS = appNumber; // allow for some discards
        int repeatCount = appNumber;// 保证上面两个值相等,设定存放的程序个数

        /* 每次加载必须清空list中的内容 */
        if (appInfos!=null){
            appInfos.removeAll(appInfos);
        }

        // 得到包管理器和activity管理器

        final Context context = activity.getApplication();
        final PackageManager pm = context.getPackageManager();
        final ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        // 从ActivityManager中取出用户最近launch过的 MAX_RECENT_TASKS + 1 个，以从早到晚的时间排序，
        // 注意这个 0x0002,它的值在launcher中是用ActivityManager.RECENT_IGNORE_UNAVAILABLE
        // 但是这是一个隐藏域，因此我把它的值直接拷贝到这里
        final List<ActivityManager.RecentTaskInfo> recentTasks = am
                .getRecentTasks(MAX_RECENT_TASKS + 1, 0x0002);
        //.getRecentTasks(MAX_RECENT_TASKS + 1, 8);


        // 这个activity的信息是我们的launcher
        ActivityInfo homeInfo = new Intent(Intent.ACTION_MAIN).addCategory(
                Intent.CATEGORY_HOME).resolveActivityInfo(pm, 0);

        int numTasks = recentTasks.size();
        for (int i = 1; i < numTasks && (i < MAX_RECENT_TASKS); i++) {
            HashMap<String, Object> singleAppInfo = new HashMap<String, Object>();// 当个启动过的应用程序的信息
            final ActivityManager.RecentTaskInfo info = recentTasks.get(i);

            Intent intent = new Intent(info.baseIntent);
            if (info.origActivity != null) {
                intent.setComponent(info.origActivity);
            }
            /**
             * 如果找到是launcher，直接continue，后面的appInfos.add操作就不会发生了
             */
            if (homeInfo != null) {
                if (homeInfo.packageName.equals(intent.getComponent()
                        .getPackageName())
                        && homeInfo.name.equals(intent.getComponent()
                        .getClassName())) {
                    MAX_RECENT_TASKS = MAX_RECENT_TASKS + 1;
                    continue;
                }
            }
            // 设置intent的启动方式为 创建新task()【并不一定会创建】
            intent.setFlags((intent.getFlags() & ~Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
            // 获取指定应用程序activity的信息(按我的理解是：某一个应用程序的最后一个在前台出现过的activity。)
            final ResolveInfo resolveInfo = pm.resolveActivity(intent, 0);
            if (resolveInfo != null) {
                final ActivityInfo activityInfo = resolveInfo.activityInfo;
                final String title = activityInfo.loadLabel(pm).toString();
                Drawable icon = activityInfo.loadIcon(pm);

                //&& info.id != -1
                if (title != null && title.length() > 0 && icon != null ) {
                    singleAppInfo.put("title", title);
                    singleAppInfo.put("icon", icon);
                    singleAppInfo.put("tag", intent);
                    singleAppInfo.put("packageName", activityInfo.packageName);
                    singleAppInfo.put("id", info.persistentId);
                    appInfos.add(singleAppInfo);
                }
            }
        }
        MAX_RECENT_TASKS = repeatCount;
    }





    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clear_all1:
                break;
            case R.id.clear_all2:

                new AlertDialog.Builder(getContext()).setTitle("确定删除当前所有文件？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //点击确定触发的事件
                              AppUtils.deleteDirectory(filePath);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //点击取消触发的事件
                            }
                        }).show();

                break;
        }
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventMessage event) {
        // Do something

        if (event.getMessage().equals("savelist")){




        }

    }
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }




}

package com.jiuhong.mytvapplication;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.dou361.dialogui.DialogUIUtils;
import com.hjq.toast.ToastUtils;
import com.jiuhong.mytvapplication.adapter.AppSelectAdapter;
import com.jiuhong.mytvapplication.adapter.TestAdapter;
import com.jiuhong.mytvapplication.adapter.UtilsAppAdapter;
import com.jiuhong.mytvapplication.bean.AppInfo;
import com.jiuhong.mytvapplication.floatwindow.EventMessage;
import com.jiuhong.mytvapplication.utils.AppUtils;
import com.jiuhong.mytvapplication.utils.DragImageView;
import com.jiuhong.mytvapplication.utils.ListDataSaveUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class TwoFramgent extends Fragment implements View.OnClickListener {
    private TextView text;
    private TextView tv_date;
    private TextView tv_week;

    private int weekday;
    private TextView tv_shifen;
    private TextView tv_miao;
    private TextView tv_pm;


    private int TIME = 1000;  //每隔1s执行一次.

    Handler handler = new Handler();
    private Runnable runnable;
    private LinearLayout ll_tab1;
    private LinearLayout ll_tab2;
    private LinearLayout ll_tab3;
    private Intent intent;
    private ArrayList<AppInfo> allApp;
    private UtilsAppAdapter allMyAdapater;
    private RecyclerView app_recycle;
    private LinearLayoutManager linearLayoutManager;
    private DragImageView xuanfu_pic;
    private ImageView image_shang;


    private ImageView image_xia;
    private ImageView image_zuo;
    private ImageView image_you;
    private Context mcontext;
    private Dialog dialogs;
    private List<AppInfo> testlist;
    private int pos;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two_layout, container, false);
        mcontext = container.getContext();
        initView(view);
        return view;
    }

    private void initView(View view) {

        xuanfu_pic = view.findViewById(R.id.xuanfu_pic);


        xuanfu_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!xuanfu_pic.isDrag()) {


                    Toast.makeText(getContext(), "点击了", Toast.LENGTH_SHORT).show();
                }
            }
        });







        app_recycle = view.findViewById(R.id.app_recycle);
        text = (TextView) view.findViewById(R.id.text);
        tv_date = (TextView) view.findViewById(R.id.tv_date);
        tv_week = (TextView) view.findViewById(R.id.tv_week);

        tv_shifen = (TextView) view.findViewById(R.id.tv_shifen);
        tv_miao = (TextView) view.findViewById(R.id.tv_miao);
        tv_pm = (TextView) view.findViewById(R.id.tv_pm);

        //白板
        ll_tab1 = (LinearLayout) view.findViewById(R.id.ll_tab1);
        //文件
        ll_tab2 = (LinearLayout) view.findViewById(R.id.ll_tab2);
        //分享
        ll_tab3 = (LinearLayout) view.findViewById(R.id.ll_tab3);
        ll_tab1.setOnClickListener(this);
        ll_tab2.setOnClickListener(this);
        ll_tab3.setOnClickListener(this);


        //获取当前时间


        long time = System.currentTimeMillis();
        final Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(time);
        int nian=mCalendar.get(Calendar.YEAR);
        int yue=mCalendar.get(Calendar.MONTH) + 1;
        int ri=mCalendar.get(Calendar.DAY_OF_MONTH);



        Log.e("initView", "initView: "+nian+"---"+yue+"---"+ri);
        //获取今天周几
        getWeek();




        //动态设置秒
        runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    long time = System.currentTimeMillis();
                    mCalendar.setTimeInMillis(time);
                    int miao=mCalendar.get(Calendar.SECOND);
                    int shi = mCalendar.get(Calendar.HOUR);
                    int fen=mCalendar.get(Calendar.MINUTE);
                    tv_shifen.setText((shi<10?"0"+shi:shi)+":"+(fen<10?"0"+fen:fen));
                    tv_miao.setText(":"+(miao<10?"0"+miao:miao));
                    handler.postDelayed(this, TIME);
                    Log.e("initView", "initView: "+"执行了"+miao+"----------"+mCalendar.get(Calendar.SECOND));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        handler.postDelayed(runnable, TIME);


        int apm = mCalendar.get(Calendar.AM_PM);
//        apm=0 表示上午，apm=1表示下午。
        if (apm==0){
            tv_pm.setText(getResources().getString(R.string.day_am));
        }else {
            tv_pm.setText(getResources().getString(R.string.day_pm));
        }

        if (yue==1){
            tv_date.setText(getResources().getString(R.string.MONTH_1)+" "+ri+","+nian);
        }else if (yue==2){
            tv_date.setText(getResources().getString(R.string.MONTH_2)+" "+ri+","+nian);
        } else if (yue==3){
            tv_date.setText(getResources().getString(R.string.MONTH_3)+" "+ri+","+nian);
        } else if (yue==4){
            tv_date.setText(getResources().getString(R.string.MONTH_4)+" "+ri+","+nian);
        } else if (yue==5){
            tv_date.setText(getResources().getString(R.string.MONTH_5)+" "+ri+","+nian);
        } else if (yue==6){
            tv_date.setText(getResources().getString(R.string.MONTH_6)+" "+ri+","+nian);
        } else if (yue==7){
            tv_date.setText(getResources().getString(R.string.MONTH_7)+" "+ri+","+nian);
        } else if (yue==8){
            tv_date.setText(getResources().getString(R.string.MONTH_8)+" "+ri+","+nian);
        } else if (yue==9){
            tv_date.setText(getResources().getString(R.string.MONTH_9)+" "+ri+","+nian);
        } else if (yue==10){
            tv_date.setText(getResources().getString(R.string.MONTH_10)+" "+ri+","+nian);
        } else if (yue==11){
            tv_date.setText(getResources().getString(R.string.MONTH_11)+" "+ri+","+nian);
        } else if (yue==12){
            tv_date.setText(getResources().getString(R.string.MONTH_12)+" "+ri+","+nian);
        }



        if (weekday==1){
            tv_week.setText(getResources().getString(R.string.day_7));
         } else if (weekday == 2) {
            tv_week.setText(getResources().getString(R.string.day_1));

        } else if (weekday == 3) {
            tv_week.setText(getResources().getString(R.string.day_2));

        } else if (weekday == 4) {
            tv_week.setText(getResources().getString(R.string.day_3));

        } else if (weekday == 5) {
            tv_week.setText(getResources().getString(R.string.day_4));

        } else if (weekday == 6) {
            tv_week.setText(getResources().getString(R.string.day_5));

        } else if (weekday == 7) {
            tv_week.setText(getResources().getString(R.string.day_6));
        }
        //获取过滤过的应用
        allApp = new ArrayList<AppInfo>();
        allApp = AppUtils.getAppInfosutils(getContext());
        AppInfo appInfos = new AppInfo();
        appInfos.setAppIcon(getResources().getDrawable(R.mipmap.add_apps));
        allApp.add(appInfos);

        allMyAdapater = new UtilsAppAdapter(getContext(), allApp);

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        app_recycle.setLayoutManager(linearLayoutManager);


        app_recycle.setAdapter(allMyAdapater);

        //点击跳转apk
       allMyAdapater.setOnItemClickListener(new UtilsAppAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Log.e("dsadsadsa", "dsadsadsa: "+position+"-------------------"+(allApp.size()-1) );
               if (position==allApp.size()-1){
                    //最后一跳点击添加
                   ToastUtils.show("最后一条 添加");
                    //自定义弹框
                   View rootView = View.inflate(getContext(), R.layout.layout_app_sel, null);
                   RecyclerView recycle = rootView.findViewById(R.id.recycle);
                   linearLayoutManager = new LinearLayoutManager(getContext());
                   linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                   recycle.setLayoutManager(linearLayoutManager);
                   //需要过滤掉想要加入的APP列表  到这里
                   testlist = AppUtils.getAppInfosutilsguolv(getContext());


                   AppSelectAdapter appSelectAdapter = new AppSelectAdapter(mcontext, testlist);
                   recycle.setAdapter(appSelectAdapter);
                   dialogs = DialogUIUtils.showCustomAlert(getContext(), rootView).show();

                   appSelectAdapter.setOnItemClickListener(new AppSelectAdapter.OnItemClickListener() {
                       @Override
                       public void onClick(int position) {
                           pos = position;
                       }
                   });





               }else {
                   //这里通过包名来跳转
                   Intent mIntent = new Intent();
                   PackageManager packageManager = getActivity().getPackageManager();
                   AppInfo appInfo = allApp.get(position);
                   mIntent = packageManager.getLaunchIntentForPackage(appInfo.getPackageName());
                   if (mIntent != null) {
                       startActivity(mIntent);
                   }
               }
            }
        });
        //长按卸载apk，但是我这里没有做刷新的操作，需要注意一下
        allMyAdapater.setOnItemLongClickListener(new UtilsAppAdapter.OnItemLongClickListener() {
            @Override
            public void onClick(int position) {
                AppInfo appInfo = allApp.get(position);
                uninstall(appInfo.getPackageName());
            }
        });

    }


    public boolean uninstall(String packageName) {
        boolean b = checkApplication(packageName);
        if (b) {
            Uri packageURI = Uri.parse("package:".concat(packageName));
            Intent intent = new Intent(Intent.ACTION_DELETE);
            intent.setData(packageURI);
            this.startActivity(intent);
            allMyAdapater.notifyDataSetChanged();
            return true;
        }
        return false;
    }

    private boolean checkApplication(String packageName) {
        if (packageName == null || "".equals(packageName)) {
            return false;
        }
        return true;
    }


    private int getWeek() {


        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        weekday = c.get(Calendar.DAY_OF_WEEK);
//        if (weekday == 1) {
//            week = "周日";
//
//        } else if (weekday == 2) {
//            week = "周一";
//
//        } else if (weekday == 3) {
//            week = "周二";
//
//        } else if (weekday == 4) {
//            week = "周三";
//
//        } else if (weekday == 5) {
//            week = "周四";
//
//        } else if (weekday == 6) {
//            week = "周五";
//
//        } else if (weekday == 7) {
//            week = "周六";
//
//        }
        return weekday;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_tab1:
                intent = new Intent();
                ComponentName cn = new ComponentName("com.wjlocal.whiteboard", "com.wjlocal.whiteboard.ui.activity.HomeActivity");
                intent.setComponent(cn);
                startActivity(intent);
                break;
            case R.id.ll_tab2:
                 intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.hikeen.filemanager");
                startActivity(intent);
                break;
            case R.id.ll_tab3:
                 intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.bozee.andisplayguide");
                startActivity(intent);
                break;
        }
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventMessage event) {
        // Do something

        if (event.getMessage().equals("selectapp")){
            DialogUIUtils.dismiss(dialogs);

            allApp.add(allApp.size()-1,testlist.get(pos));
            allMyAdapater = new UtilsAppAdapter(getContext(), allApp);

            app_recycle.setAdapter(allMyAdapater);

            allMyAdapater.setOnItemClickListener(new UtilsAppAdapter.OnItemClickListener() {
                @Override
                public void onClick(int position) {
                    Log.e("dsadsadsa", "dsadsadsa: "+position+"-------------------"+(allApp.size()-1) );
                    if (position==allApp.size()-1){
                        //最后一跳点击添加
                        ToastUtils.show("最后一条 添加");
                        //自定义弹框
                        View rootView = View.inflate(getContext(), R.layout.layout_app_sel, null);
                        RecyclerView recycle = rootView.findViewById(R.id.recycle);
                        linearLayoutManager = new LinearLayoutManager(getContext());
                        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                        recycle.setLayoutManager(linearLayoutManager);
                        //需要过滤掉想要加入的APP列表  到这里
                        testlist = AppUtils.getAppInfosutilsguolv(getContext());


                        AppSelectAdapter appSelectAdapter = new AppSelectAdapter(mcontext, testlist);
                        recycle.setAdapter(appSelectAdapter);
                        dialogs = DialogUIUtils.showCustomAlert(getContext(), rootView).show();

                        appSelectAdapter.setOnItemClickListener(new AppSelectAdapter.OnItemClickListener() {
                            @Override
                            public void onClick(int position) {
                                pos = position;
                            }
                        });





                    }else {
                        //这里通过包名来跳转
                        Intent mIntent = new Intent();
                        PackageManager packageManager = getActivity().getPackageManager();
                        AppInfo appInfo = allApp.get(position);
                        mIntent = packageManager.getLaunchIntentForPackage(appInfo.getPackageName());
                        if (mIntent != null) {
                            startActivity(mIntent);
                        }
                    }
                }
            });
            //长按卸载apk，但是我这里没有做刷新的操作，需要注意一下
            allMyAdapater.setOnItemLongClickListener(new UtilsAppAdapter.OnItemLongClickListener() {
                @Override
                public void onClick(int position) {
                    AppInfo appInfo = allApp.get(position);
                    uninstall(appInfo.getPackageName());
                }
            });

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

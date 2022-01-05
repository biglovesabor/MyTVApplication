package com.jiuhong.mytvapplication.ui;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.viewpager.widget.ViewPager;

import com.hjq.dialog.base.BaseDialog;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hjq.toast.ToastUtils;
import com.jiuhong.mytvapplication.FragmentChangeAdapter;
import com.jiuhong.mytvapplication.OneFramgent;
import com.jiuhong.mytvapplication.R;
import com.jiuhong.mytvapplication.ThreeFramgent;
import com.jiuhong.mytvapplication.TwoFramgent;
import com.jiuhong.mytvapplication.ViewPagerIndicator;
import com.jiuhong.mytvapplication.dialog.LeftDialog;
import com.jiuhong.mytvapplication.dialog.RightDialog;
import com.jiuhong.mytvapplication.floatwindow.EventMessage;
import com.jiuhong.mytvapplication.floatwindow.MyAccessibilityService;
import com.jiuhong.mytvapplication.floatwindow.MyRightService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private FragmentChangeAdapter fadapter;
    private List<Fragment> flist;//声明一个存放Fragment的List
    private ViewPager viewpager;
    private ViewPagerIndicator viewPagerIndicator;
    private ImageView ivLeft;
    private ImageView ivRight;
    private Intent intent;
    private LinearLayout ll_bottom_tab1;
    private LinearLayout ll_bottom_tab2;
    private LinearLayout ll_bottom_tab3;
    private LinearLayout ll_bottom_tab4;
    private LinearLayout ll_bottom_tab5;
    private LinearLayout ll_bottom_tab6;
    private LinearLayout ll_bottom_tab7;
    private Intent serviceIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initPager();
        ButterKnife.bind(this);



    }

    private void initView() {
        XXPermissions.with(this)
                // 申请单个权限
//                .permission(Permission.RECORD_AUDIO)
                // 申请多个权限
                //.permission(Permission.Group.CALENDAR)
                // 申请安装包权限
                //.permission(Permission.REQUEST_INSTALL_PACKAGES)
                // 申请悬浮窗权限
                .permission(Permission.SYSTEM_ALERT_WINDOW)

                // 申请通知栏权限
                //.permission(Permission.NOTIFICATION_SERVICE)
                // 申请系统设置权限
                //.permission(Permission.WRITE_SETTINGS)
                // 设置权限请求拦截器
                //.interceptor(new PermissionInterceptor())
                // 设置不触发错误检测机制
                //.unchecked()
                .request(new OnPermissionCallback() {

                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
//                            toast("获取录音和日历权限成功");
                        } else {
//                            toast("获取部分权限成功，但部分权限未正常授予");
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        if (never) {
//                            toast("被永久拒绝授权，请手动授予录音和日历权限");
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(MainActivity.this, permissions);
                        } else {
//                            toast("获取录音和日历权限失败");
                        }
                    }
                });



        viewpager = (ViewPager) findViewById(R.id.viewpager);
        viewPagerIndicator = findViewById(R.id.view_page_indicator);

        ivLeft = findViewById(R.id.iv_left);
        ivRight = findViewById(R.id.iv_right);

        ll_bottom_tab1 = findViewById(R.id.ll_bottom_tab1);
        ll_bottom_tab2 = findViewById(R.id.ll_bottom_tab2);
        ll_bottom_tab3 = findViewById(R.id.ll_bottom_tab3);
        ll_bottom_tab4 = findViewById(R.id.ll_bottom_tab4);
        ll_bottom_tab5 = findViewById(R.id.ll_bottom_tab5);
        ll_bottom_tab6 = findViewById(R.id.ll_bottom_tab6);
        ll_bottom_tab7 = findViewById(R.id.ll_bottom_tab7);

        ll_bottom_tab1.setOnClickListener(this);
        ll_bottom_tab2.setOnClickListener(this);
        ll_bottom_tab3.setOnClickListener(this);
        ll_bottom_tab4.setOnClickListener(this);
        ll_bottom_tab5.setOnClickListener(this);
        ll_bottom_tab6.setOnClickListener(this);
        ll_bottom_tab7.setOnClickListener(this);

    }
    private void initPager(){
        flist=new ArrayList<>();//这里如果不写的话,就没有存放Fragment的list了
        //是会报错的
        //添加Fragment
        flist.add(new OneFramgent());
        flist.add(new TwoFramgent());
        flist.add(new ThreeFramgent());
        fadapter=new FragmentChangeAdapter(getSupportFragmentManager(),flist);
        viewpager.setAdapter(fadapter);
        viewPagerIndicator.setCirclerCount(flist.size());
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //positionOffset 表示距离 position的偏移量有多少
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                viewPagerIndicator.setPositionOffset(position, positionOffset);
                if (positionOffset == 0) {
                    viewPagerIndicator.setSelectPosition(position);
                }
            }

            @Override
            public void onPageSelected(int position) {
                //TextPaint textPaint = tvNumberStyle.getPaint();
                //float len = textPaint.measureText(tvNumberStyle.getText().toString().trim());
                //LogUtils.e(TAG , "tvNumberStyle length : " + tvNumberStyle.getMeasuredWidth());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (!serviceIsRunning()){



            intent = new Intent(this, MyAccessibilityService.class);
            startService(intent);

            intent = new Intent(this, MyRightService.class);
            startService(intent);



//            startAccessibilityService();
        }else {

            int postion = getIntent().getIntExtra("postion", -1);
            if (postion>=0){
                viewpager.setCurrentItem(postion);
            }


        }
    }

    /**
     * 判断自己的应用的AccessibilityService是否在运行
     *
     * @return
     */
    private boolean serviceIsRunning() {
        ActivityManager am = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> services = am.getRunningServices(Short.MAX_VALUE);
        for (ActivityManager.RunningServiceInfo info : services) {
            if (info.service.getClassName().equals(getPackageName() + ".floatwindow.MyAccessibilityService")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 前往设置界面开启服务
     */
    private void startAccessibilityService() {
        new AlertDialog.Builder(this)
                .setTitle("开启辅助功能")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("使用此项功能需要您开启辅助功能")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 隐式调用系统设置界面
                        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                        startActivity(intent);
                    }
                }).create().show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventMessage event) {
        // Do something


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



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_bottom_tab1:
                serviceIntent = new Intent();
                ComponentName component = new ComponentName("com.hikeen.sidebar", "com.hikeen.sidebar.WindowService");
                serviceIntent.setComponent(component);
                serviceIntent.putExtra("key","signalview_start");
                startService(serviceIntent);


                break;
            case R.id.ll_bottom_tab2:
                intent = new Intent();
                ComponentName cn2 = new ComponentName("com.jiuhong.mytvapplication", "com.jiuhong.mytvapplication.ui.MainActivity");
                intent.setComponent(cn2);
                intent.putExtra("postion", 1);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                initView();
                initPager();
                break;
            case R.id.ll_bottom_tab3:
                 intent = new Intent();
                ComponentName cn = new ComponentName("com.jiuhong.mytvapplication", "com.jiuhong.mytvapplication.ui.MainActivity");
                intent.setComponent(cn);
                intent.putExtra("postion", 0);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                initView();
                initPager();
                break;
            case R.id.ll_bottom_tab4:
                intent = new Intent();
                ComponentName cn1 = new ComponentName("com.jiuhong.mytvapplication", "com.jiuhong.mytvapplication.ui.MainActivity");
                intent.setComponent(cn1);
                intent.putExtra("postion", 2);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                initView();
                initPager();
                break;
            case R.id.ll_bottom_tab5:

                 intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("http://www.baidu.com");
                intent.setData(content_url);
                intent.setClassName("com.android.chrome","com.google.android.apps.chrome.ChromeTabbedActivity");
                startActivity(intent);

                break;
            case R.id.ll_bottom_tab6:
                 intent = getPackageManager().getLaunchIntentForPackage("com.android.setting3399");
                startActivity(intent);
                break;
            case R.id.ll_bottom_tab7:

                ComponentName components = new ComponentName("com.hikeen.sidebar", "com.hikeen.sidebar.WindowService");
                serviceIntent.setComponent(components);
                serviceIntent.putExtra("key","lockapp_start");
                startService(serviceIntent);
                break;

        }
    }



}





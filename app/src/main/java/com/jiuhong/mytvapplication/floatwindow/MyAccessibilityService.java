package com.jiuhong.mytvapplication.floatwindow;

import android.accessibilityservice.AccessibilityService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.jiuhong.mytvapplication.R;
import com.jiuhong.mytvapplication.utils.L;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class MyAccessibilityService extends AccessibilityService implements View.OnClickListener {


    private Intent serviceIntent;

    /**
     * 页面变化回调事件
     * @param event event.getEventType() 当前事件的类型;
     *              event.getClassName() 当前类的名称;
     *              event.getSource() 当前页面中的节点信息；
     *              event.getPackageName() 事件源所在的包名
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        L.e("Https", "onAccessibilityEvent");

    }

    @Override
    public void onInterrupt() {
        L.e("Https", "onInterrupt");
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        instance = this;
        L.e("Https", "onServiceConnected");
    }


    public  static AccessibilityService instance = null;
    private String TAG = "floatView_Accessibility";
    //定义浮动窗口布局
    private LinearLayout mFloatLayout;
    private WindowManager.LayoutParams wmParams;
    //创建浮动窗口设置布局参数的对象
    private WindowManager mWindowManager;
    private ImageView mFloatView;
    private boolean isClick;

    @Override
    public void onCreate()
    {
        super.onCreate();
        L.e("Https", "onCreate");
        createFloatView();
    }

    private void createFloatView()
    {


        wmParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.TYPE_TOAST,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        //通过getApplication获取的是WindowManagerImpl.CompatModeWrapper
        mWindowManager = (WindowManager)getApplication().getSystemService(getApplication().WINDOW_SERVICE);
        //设置window type  WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
//        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        //设置图片格式，效果为背景透明
        wmParams.format = PixelFormat.RGBA_8888;
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //调整悬浮窗显示的停靠位置为左侧置顶
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        // 以屏幕左上角为原点，设置x、y初始值，相对于gravity
        Display defaultDisplay = mWindowManager.getDefaultDisplay();
        int height = defaultDisplay.getHeight();
        int width = defaultDisplay.getWidth();
        wmParams.x = 0;
        wmParams.y = height-360;
        //设置悬浮窗口长宽数据
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        LayoutInflater inflater = LayoutInflater.from(getApplication());
        //获取浮动窗口视图所在布局
        mFloatLayout = (LinearLayout) inflater.inflate(R.layout.layout_float_window, null);
        //添加mFloatLayout
        mWindowManager.addView(mFloatLayout, wmParams);
        //浮动窗口按钮
        mFloatView = (ImageView) mFloatLayout.findViewById(R.id.iv_close_notify);

        initView();

        mFloatLayout.measure(View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
                .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        //设置监听浮动窗口的触摸移动
//        mFloatView.setOnTouchListener(new View.OnTouchListener()
//        {
//            float x = 0,y = 0;
//
//            @Override
//
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        isClick = false;
//                        x = event.getRawX();
//                        y = event.getRawY();
//                        L.e("Https", "------ACTION_DOWN---x="+x+"---y="+y);
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        //Log.d(TAG, "------ACTION_MOVE---x="+event.getRawX()+"---y="+event.getRawY());
//                        if ((x - event.getRawX()) > 20
//                                || (event.getRawX() - x) > 20
//                                || (y - event.getRawY()) > 20
//                                || (event.getRawY() - y) > 20
//                        ) {
//                            L.e("Https", "------ACTION_MOVE");
//                            isClick = true;
//                            // getRawX是触摸位置相对于屏幕的坐标，getX是相对于按钮的坐标
//                            wmParams.x = (int) event.getRawX()
//                                    - mFloatView.getMeasuredWidth() / 2;
//                            // 减25为状态栏的高度
//                            wmParams.y = (int) event.getRawY()
//                                    - mFloatView.getMeasuredHeight() / 2 - 75;
//                            // 刷新
//                            mWindowManager.updateViewLayout(mFloatLayout, wmParams);
//                            return true;
//                        }
//                    case MotionEvent.ACTION_UP:
//                        L.e("Https", "------ACTION_UP");
//                        return isClick;// 此处返回false则属于移动事件，返回true则释放事件，可以出发点击否。
//                    default:
//                        break;
//                }
//                return false;
//            }
//        });

        mFloatView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                L.e("Https", "------onClick");
                //TODO home
//                performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                performGlobalAction(AccessibilityNodeInfo.ACTION_CLICK);

                ll_gongju.setVisibility(View.VISIBLE);
                mFloatView.setVisibility(View.GONE);

            }
        });



//        mFloatView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                L.e("Https", "------onLongClick");
//                //TODO home
//                if( !isClick ){
//                    performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
//                }
//                return true;
//            }
//        });
    }



    private   LinearLayout ll_gongju;
    private   ImageView sidebar_back;
    private   LinearLayout sidebar_btn1;
    private   LinearLayout sidebar_btn2;
    private   LinearLayout sidebar_btn3;
    private   LinearLayout sidebar_btn4;
    private   LinearLayout sidebar_btn5;
    private   LinearLayout sidebar_btn6;
    private   LinearLayout sidebar_btn7;
    private   LinearLayout sidebar_btn8;
    private void initView() {

        ll_gongju = mFloatLayout.findViewById(R.id.ll_gongju);
        sidebar_back = mFloatLayout.findViewById(R.id.sidebar_back);
        sidebar_btn1 = mFloatLayout.findViewById(R.id.sidebar_btn1);
        sidebar_btn2 = mFloatLayout.findViewById(R.id.sidebar_btn2);
        sidebar_btn3 = mFloatLayout.findViewById(R.id.sidebar_btn3);
        sidebar_btn4 = mFloatLayout.findViewById(R.id.sidebar_btn4);
        sidebar_btn5 = mFloatLayout.findViewById(R.id.sidebar_btn5);
        sidebar_btn6 = mFloatLayout.findViewById(R.id.sidebar_btn6);
        sidebar_btn7 = mFloatLayout.findViewById(R.id.sidebar_btn7);
        sidebar_btn8 = mFloatLayout.findViewById(R.id.sidebar_btn8);

        sidebar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_gongju.setVisibility(View.GONE);
                mFloatView.setVisibility(View.VISIBLE);



                EventMessage msg = new EventMessage(1,"Hello MainActivity");
                EventBus.getDefault().post(msg);
            }
        });

        sidebar_btn1.setOnClickListener(this);
        sidebar_btn2.setOnClickListener(this);
        sidebar_btn3.setOnClickListener(this);
        sidebar_btn4.setOnClickListener(this);
        sidebar_btn5.setOnClickListener(this);
        sidebar_btn6.setOnClickListener(this);
        sidebar_btn7.setOnClickListener(this);
        sidebar_btn8.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sidebar_btn1:
//                Intent intent = new Intent();
//                ComponentName cn = new ComponentName("com.jiuhong.tvluncher", "com.jiuhong.tvluncher.activity.MainActivity");
//                intent.setComponent(cn);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
//                startActivity(intent);
                performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
                break;
            case R.id.sidebar_btn2:
                performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                break;
            case R.id.sidebar_btn3:
                Intent mIntent = new Intent();
                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ComponentName component = new ComponentName("com.hikeen.mark", "com.hikeen.mark.MainActivity");
                mIntent.setComponent(component);
               startActivity(mIntent);
                break;
            case R.id.sidebar_btn4:
                serviceIntent = new Intent();
                ComponentName components = new ComponentName("com.hikeen.sidebar", "com.hikeen.sidebar.WindowService");
                serviceIntent.setComponent(components);
                serviceIntent.putExtra("key","screenapp_start");
                startService(serviceIntent);
                break;
            case R.id.sidebar_btn5:
                performGlobalAction(AccessibilityNodeInfo.ACTION_CLICK);
                break;
            case R.id.sidebar_btn6:
                Intent intent = new Intent();
                ComponentName cn = new ComponentName("com.jiuhong.mytvapplication", "com.jiuhong.mytvapplication.ui.MainActivity");
                intent.setComponent(cn);
                intent.putExtra("postion", 2);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                startActivity(intent);
//                performGlobalAction(AccessibilityNodeInfo.ACTION_CLICK);
                break;
            case R.id.sidebar_btn7:
                performGlobalAction(AccessibilityNodeInfo.ACTION_CLICK);
                break;
            case R.id.sidebar_btn8:

                ComponentName componentss = new ComponentName("com.hikeen.sidebar", "com.hikeen.sidebar.WindowService");
                serviceIntent.setComponent(componentss);
                serviceIntent.putExtra("key","signalview_start");
                startService(serviceIntent);
                break;
        }
    }


//    private Context context;
//    public MyAccessibilityService(Context context) {
//        this.context = context;
//    }
}

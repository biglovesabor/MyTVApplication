package com.jiuhong.mytvapplication;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import com.hjq.language.MultiLanguages;
import com.hjq.toast.ToastInterceptor;
import com.hjq.toast.ToastUtils;
import com.jiuhong.mytvapplication.utils.AppUtils;
import com.jiuhong.mytvapplication.utils.L;
import com.jiuhong.mytvapplication.utils.SPUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        initSDK(this);
    }

    /**
     * 初始化一些第三方框架
     */
    public void initSDK(Application application) {

        SPUtils.init(this);   //这句加到Application


        // 设置 Toast 拦截器
        ToastUtils.setToastInterceptor(new ToastInterceptor() {
            @Override
            public boolean intercept(Toast toast, CharSequence text) {
                boolean intercept = super.intercept(toast, text);
                if (intercept) {
                    Log.e("Toast", "空 Toast");
                } else {
                    Log.i("Toast", text.toString());
                }
                return intercept;
            }
        });
        // 吐司工具类
        ToastUtils.init(application);


        // EventBus 事件总线
//        EventBusManager.init();

        AppUtils.init(application);

        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null,
                null, null);
        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(application));
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggerInterceptor("HTTPS", true))
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .sslSocketFactory(sslParams.sSLSocketFactory,
                        sslParams.trustManager).build();
        OkHttpUtils.initClient(client);
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                .tag("Https")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
        L.init(true);
        // 设置崩溃后自动重启 APP
        // 参数依次为 上下文（建议是Application），是否是debug模式，是否崩溃后重启，重启延迟时间，重启的Activity
//        UncaughtExceptionHandlerImpl.getInstance().init(application, BuildConfig.DEBUG, true, 0, HomeCameraActivity.class);

        EventBus.builder()
                // 使用 Apt 插件
                .ignoreGeneratedIndex(false)
                // 添加索引类
                .addIndex(new MyEventBusIndex())
                // 作为默认配置
                .installDefaultEventBus();



        // 初始化语种切换框架
        MultiLanguages.init(this);

    }

    @Override
    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
        // 使用 Dex分包
//        MultiDex.install(this);


        // 绑定语种
        super.attachBaseContext(MultiLanguages.attach(base));
    }

}

package com.jiuhong.mytvapplication.ui;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.hjq.language.MultiLanguages;


public abstract  class BaseActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        // 绑定语种
        super.attachBaseContext(MultiLanguages.attach(newBase));
    }
}
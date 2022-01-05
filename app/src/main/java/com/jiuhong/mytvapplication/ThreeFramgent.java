package com.jiuhong.mytvapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.jiuhong.mytvapplication.adapter.AllAdapter;
import com.jiuhong.mytvapplication.bean.AppInfo;
import com.jiuhong.mytvapplication.bean.AppInfos;
import com.jiuhong.mytvapplication.floatwindow.EventMessage;
import com.jiuhong.mytvapplication.utils.AppUtils;
import com.jiuhong.mytvapplication.utils.DateSave;
import com.jiuhong.mytvapplication.utils.GsonUtils;
import com.jiuhong.mytvapplication.utils.ListDataSaveUtil;
import com.jiuhong.mytvapplication.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ThreeFramgent extends Fragment {
    private TextView text;
    private RecyclerView app_recycle;
    private ArrayList<AppInfos> allApp;
    private AllAdapter allMyAdapater;
    private GridLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three_layout, container, false);
        initView(view);

        return view;
    }



    private void initView(View view) {
        text = (TextView) view.findViewById(R.id.text);
        app_recycle = view.findViewById(R.id.app_recycle);

        allApp = new ArrayList<AppInfos>();
        allApp = AppUtils.getAppInfo(getContext());
        allMyAdapater = new AllAdapter(getContext(), allApp);

        linearLayoutManager = new GridLayoutManager(getContext(),6);
        app_recycle.setLayoutManager(linearLayoutManager);

        app_recycle.setAdapter(allMyAdapater);

        //点击跳转apk
        allMyAdapater.setOnItemClickListener(new AllAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {

                //这里通过包名来跳转
                Intent mIntent = new Intent();
                PackageManager packageManager = getActivity().getPackageManager();
                AppInfos appInfo = allApp.get(position);

                mIntent = packageManager.getLaunchIntentForPackage(appInfo.getPackageName());
                if (mIntent != null) {
                    startActivity(mIntent);
                }

                //保存当前打开的APP  到第一条

                    Log.e("appinfo", "onClick: "+new Gson().toJson(allApp.get(position)));
                SPUtils.putString("savelist",new Gson().toJson(allApp.get(position)));

                EventMessage msg = new EventMessage(6,"savelist");
                EventBus.getDefault().post(msg);
            }
        });
        //长按卸载apk，但是我这里没有做刷新的操作，需要注意一下
        allMyAdapater.setOnItemLongClickListener(new AllAdapter.OnItemLongClickListener() {
            @Override
            public void onClick(int position) {
                AppInfos appInfo = allApp.get(position);
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


}

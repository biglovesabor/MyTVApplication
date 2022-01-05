package com.jiuhong.mytvapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jiuhong.mytvapplication.R;
import com.jiuhong.mytvapplication.bean.AppInfo;
import com.jiuhong.mytvapplication.bean.AppInfos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.VH> {

    private View mView;
    private List<AppInfos> mlist;
    private Context mContext;
    private VH mVH;
    private OnItemClickListener listener;
    private OnItemLongClickListener longClickListener;
    private AppInfos appInfo = null;

    public TestAdapter(Context context, List<AppInfos> list) {
        this.mlist = list;
        this.mContext = context;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_apps, parent, false);
        mVH = new VH(mView);
        return mVH;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        appInfo = mlist.get(position);
        //获取到的app的图标和名字进行设置，也可以通过判断包名来修改图片
        if (appInfo!=null){
//            holder.icon.setBackground(appInfo.getAppIcon());
        holder.name.setText(appInfo.getAppName());
        }

        final int itemposition = position;
//        holder.itemlayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (listener != null) {
//                    listener.onClick(itemposition);
//                }
//            }
//        });
//        holder.itemlayout.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                if (longClickListener != null) {
//                    longClickListener.onClick(itemposition);
//                }
//                return true;
//            }
//        });
    }

    @Override
    public int getItemCount() {

        return mlist.size()>0?mlist.size():0;
    }

    //点击接口
    public interface OnItemClickListener {
        void onClick(int position);
    }

    //公共的点击方法
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    //长按的点击接口
    public interface OnItemLongClickListener {
        void onClick(int position);
    }

    //公共的长按的接口
    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public class VH extends RecyclerView.ViewHolder {

        public TextView item_close;
        public TextView name;
        public ImageView icon;

        public VH(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.item_app_icon);
            item_close = (TextView) itemView.findViewById(R.id.item_close);
            name = (TextView) itemView.findViewById(R.id.item_app_name);
        }
    }
}
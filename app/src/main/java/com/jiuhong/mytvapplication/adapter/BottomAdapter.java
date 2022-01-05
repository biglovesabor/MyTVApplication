package com.jiuhong.mytvapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jiuhong.mytvapplication.R;
import com.jiuhong.mytvapplication.bean.AppInfo;
import com.jiuhong.mytvapplication.bean.PictureInfo;

import java.util.ArrayList;
import java.util.List;

import static com.jiuhong.mytvapplication.R.id.item_app_icon;

public class BottomAdapter extends RecyclerView.Adapter<BottomAdapter.VH> {

    private View mView;
    private List<PictureInfo> mlist;
    private Context mContext;
    private VH mVH;
    private OnItemClickListener listener;
    private OnItemLongClickListener longClickListener;
    private AppInfo appInfo = null;

    public BottomAdapter(Context context, List<PictureInfo> list) {
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

        Log.e("图片地址", "onBindViewHolder: "+mlist.size()+"---------"+mlist.get(position).getPicdz());
        Glide.with(mContext).load(mlist.get(position).getPicdz()).into(holder.icon);

        //获取到的app的图标和名字进行设置，也可以通过判断包名来修改图片
//        holder.icon.setBackground(appInfo.getAppIcon());
        holder.name.setText(mlist.get(position).getPicdz());

        final int itemposition = position;
        holder.item_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(itemposition);
                }
            }
        });
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
        Log.e("图片地址size", "getItemCount: "+mlist.size() );
        return mlist.size();
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
            icon = (ImageView) itemView.findViewById(item_app_icon);
            item_close = (TextView) itemView.findViewById(R.id.item_close);
            name = (TextView) itemView.findViewById(R.id.item_app_name);
        }
    }
}
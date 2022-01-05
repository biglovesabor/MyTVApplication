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

import java.util.List;

public class AllAdapter extends RecyclerView.Adapter<AllAdapter.VH> {

    private View mView;
    private List<AppInfos> mlist;
    private Context mContext;
    private VH mVH;
    private OnItemClickListener listener;
    private OnItemLongClickListener longClickListener;
    private AppInfos appInfo = null;

    public AllAdapter(Context context, List<AppInfos> list) {
        this.mlist = list;
        this.mContext = context;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_allapp, parent, false);
        mVH = new VH(mView);
        return mVH;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        appInfo = mlist.get(position);
        //获取到的app的图标和名字进行设置，也可以通过判断包名来修改图片
//        holder.icon.setBackground(appInfo.getAppIcon());
        holder.name.setText(appInfo.getAppName());
        holder.baoming.setText(appInfo.getPackageName());

        final int itemposition = position;
        holder.itemlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(itemposition);
                }
            }
        });
        holder.itemlayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (longClickListener != null) {
                    longClickListener.onClick(itemposition);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
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

        public LinearLayout itemlayout;
        public TextView name;
        public TextView baoming;
        public ImageView icon;

        public VH(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.item_app_icon);
            itemlayout = (LinearLayout) itemView.findViewById(R.id.item_layout);
            name = (TextView) itemView.findViewById(R.id.item_app_name);
            baoming = (TextView) itemView.findViewById(R.id.item_app_name1);
        }
    }
}
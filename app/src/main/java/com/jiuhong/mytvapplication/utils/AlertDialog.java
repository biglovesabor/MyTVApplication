package com.jiuhong.mytvapplication.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hjq.toast.ToastUtils;
import com.jiuhong.mytvapplication.R;

public class AlertDialog extends Dialog {

    public AlertDialog(Context context) {
        super(context);
    }

    public AlertDialog(Context context, int theme) {
        super(context, theme);
    }

    /**
     * 建造者类
     *
     * @author Administrator
     */
    public static class Builder {
        private Context context;
        private int topImageId;
        private String title;
        private String message;
        private Drawable drawable = null;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 设置顶部图标
         */
        public Builder setTopImage(int id) {
            this.topImageId = id;
            return this;
        }

        /**
         * 设置消息内容
         *
         * @param title
         * @return
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * 设置消息内容
         *
         * @param message
         * @return
         */
        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setDrawable(Drawable drawable_id) {
            this.drawable = drawable_id;
            return this;
        }


        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * 设置积极按钮
         *
         * @param positiveButtonText
         * @param listener
         * @return
         */
        public Builder setPositiveButton(String positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        /**
         * 设置消极按钮
         *
         * @param negativeButtonText
         * @param listener
         * @return
         */
        public Builder setNegativeButton(String negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        /**
         * 创建一个AlertDialog
         *
         * @return
         */
        public AlertDialog create() {
            LayoutInflater inflater = LayoutInflater.from(context);

            final AlertDialog dialog = new AlertDialog(context,
                    R.style.DialogStyle);

            View layout = null;
            if (null != contentView) {
                layout = contentView;
            } else {
                layout = inflater.inflate(R.layout.layout_alert_dialog, null);
            }


            // 设置顶部图标
            ImageView iv_top = (ImageView) layout.findViewById(R.id.iv_top);
            ImageView iv_bottom = (ImageView) layout.findViewById(R.id.iv_bottom);
            ImageView iv_left = (ImageView) layout.findViewById(R.id.iv_left);
            ImageView iv_right = (ImageView) layout.findViewById(R.id.iv_right);
            ImageView iv_center = (ImageView) layout.findViewById(R.id.iv_center);
            if (0 == topImageId) {
                iv_top.setVisibility(View.GONE);
            } else {
                iv_top.setImageResource(topImageId);
            }
            iv_center.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            iv_top.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.show("上面点击了");
                }
            });

//            // 设置标题
//            TextView titleView = (TextView) layout.findViewById(R.id.title);
//            if (null == title) {
//                titleView.setVisibility(View.GONE);
//            } else {
//                if (drawable != null) {
//                    titleView.setCompoundDrawables(drawable, null, null, null);
//                }
//                titleView.setText(title);
//            }
//            // 设置内容
//            TextView messageView = (TextView) layout.findViewById(R.id.message);
//            if (null == message) {
//                messageView.setVisibility(View.GONE);
//            } else {
//                messageView.setText(message);
//            }
//            // 设置积极按钮
//            RelativeLayout sure_layout = (RelativeLayout) layout
//                    .findViewById(R.id.sure_layout);
//            TextView sure_text = (TextView) layout
//                    .findViewById(R.id.sure_text);
//            if (TextUtils.isEmpty(positiveButtonText)
//                    || null == positiveButtonClickListener) {
//                sure_layout.setVisibility(View.GONE);
//            } else {
//                sure_text.setText(positiveButtonText);
//                sure_layout.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        // TODO Auto-generated method stub
//                        dialog.dismiss();
//                        positiveButtonClickListener.onClick(dialog,
//                                DialogInterface.BUTTON_POSITIVE);
//                    }
//                });
//            }
//
//            // 设置消极按钮
//            RelativeLayout quit_layout = (RelativeLayout) layout
//                    .findViewById(R.id.quit_layout);
//            TextView quit_text = (TextView) layout
//                    .findViewById(R.id.quit_text);
//            if (TextUtils.isEmpty(negativeButtonText)
//                    || null == negativeButtonClickListener) {
//                quit_layout.setVisibility(View.GONE);
//            } else {
//                quit_text.setText(negativeButtonText);
//                quit_layout.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        // TODO Auto-generated method stub
//                        dialog.dismiss();
//                        negativeButtonClickListener.onClick(dialog,
//                                DialogInterface.BUTTON_NEGATIVE);
//                    }
//                });
//            }
//
//            if (null != positiveButtonText && null == negativeButtonText) {
//                if (null == positiveButtonClickListener) {
//                    sure_layout.setVisibility(View.VISIBLE);
//
//                    sure_layout.setOnClickListener(new View.OnClickListener() {
//
//                        @Override
//                        public void onClick(View v) {
//                            // TODO Auto-generated method stub
//                            dialog.dismiss();
//                        }
//                    });
//                }
//            }

            // 设置对话框的视图
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setContentView(layout, params);
            return dialog;
        }
    }
}
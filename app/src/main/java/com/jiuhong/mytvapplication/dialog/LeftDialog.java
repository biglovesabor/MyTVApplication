package com.jiuhong.mytvapplication.dialog;

import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.hjq.dialog.base.BaseDialog;
import com.jiuhong.mytvapplication.R;


/**
 *    左侧工具栏
 */
public final class LeftDialog {

    public static final class Builder
            extends MyDialogFragment.Builder<Builder> implements View.OnClickListener {


        private OnListener mListener;
        private boolean mAutoDismiss = true;


        private final ImageView sidebar_back;
        private final LinearLayout sidebar_btn1;
        private final LinearLayout sidebar_btn2;
        private final LinearLayout sidebar_btn3;
        private final LinearLayout sidebar_btn4;
        private final LinearLayout sidebar_btn5;
        private final LinearLayout sidebar_btn6;
        private final LinearLayout sidebar_btn7;
        private final LinearLayout sidebar_btn8;

        public Builder(FragmentActivity activity) {
            super(activity);

            setContentView(R.layout.dialog_left);
            setAnimStyle(BaseDialog.AnimStyle.LEFT);
//            setGravity(Gravity.BOTTOM);

            sidebar_back = findViewById(R.id.sidebar_back);
            sidebar_btn1 = findViewById(R.id.sidebar_btn1);
            sidebar_btn2 = findViewById(R.id.sidebar_btn2);
            sidebar_btn3 = findViewById(R.id.sidebar_btn3);
            sidebar_btn4 = findViewById(R.id.sidebar_btn4);
            sidebar_btn5 = findViewById(R.id.sidebar_btn5);
            sidebar_btn6 = findViewById(R.id.sidebar_btn6);
            sidebar_btn7 = findViewById(R.id.sidebar_btn7);
            sidebar_btn8 = findViewById(R.id.sidebar_btn8);


            sidebar_back.setOnClickListener(this);
            sidebar_btn1.setOnClickListener(this);
            sidebar_btn2.setOnClickListener(this);
            sidebar_btn3.setOnClickListener(this);
            sidebar_btn4.setOnClickListener(this);
            sidebar_btn5.setOnClickListener(this);
            sidebar_btn6.setOnClickListener(this);
            sidebar_btn7.setOnClickListener(this);
            sidebar_btn8.setOnClickListener(this);

        }





        public Builder setAutoDismiss(boolean dismiss) {
            mAutoDismiss = dismiss;
            return this;
        }

        public Builder setListener(OnListener listener) {
            mListener = listener;
            return this;
        }

        @Override
        public void onClick(View view) {
            if (mAutoDismiss) {
                dismiss();
            }
            switch (view.getId()){
                case R.id.sidebar_back:
                    if (mListener != null) {
                        mListener.onCancel(getDialog());
                    }
                    break;
                case R.id.sidebar_btn1:
                    if (mListener != null) {
                        mListener.onSelectedBtn(getDialog(),1);
                    }
                    break;
                case R.id.sidebar_btn2:
                    if (mListener != null) {
                        mListener.onSelectedBtn(getDialog(),2);
                    }
                    break;
                case R.id.sidebar_btn3:
                    if (mListener != null) {
                        mListener.onSelectedBtn(getDialog(),3);
                    }
                    break;
                case R.id.sidebar_btn4:
                    if (mListener != null) {
                        mListener.onSelectedBtn(getDialog(),4);
                    }
                    break;
                case R.id.sidebar_btn5:
                    if (mListener != null) {
                        mListener.onSelectedBtn(getDialog(),5);
                    }
                    break;
                case R.id.sidebar_btn6:
                    if (mListener != null) {
                        mListener.onSelectedBtn(getDialog(),6);
                    }
                    break;
                case R.id.sidebar_btn7:
                    if (mListener != null) {
                        mListener.onSelectedBtn(getDialog(),7);
                    }
                    break;
                case R.id.sidebar_btn8:
                    if (mListener != null) {
                        mListener.onSelectedBtn(getDialog(),8);
                    }
                    break;

            }


        }


    }

    public interface OnListener {

        /**
         * 选择后回调
         */
        void onSelectedBtn(BaseDialog dialog, int flag);

        /**
         * 点击取消时回调
         */
        void onCancel(BaseDialog dialog);
    }

}
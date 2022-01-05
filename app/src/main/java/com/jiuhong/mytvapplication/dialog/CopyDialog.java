package com.jiuhong.mytvapplication.dialog;

import android.view.Gravity;

import androidx.fragment.app.FragmentActivity;

import com.hjq.dialog.base.BaseDialog;
import com.jiuhong.mytvapplication.R;


/**
 *
 *    desc   : 可进行拷贝的副本
 */
public final class CopyDialog {

    public static final class Builder
            extends MyDialogFragment.Builder<Builder> {

        public Builder(FragmentActivity activity) {
            super(activity);

            setContentView(R.layout.dialog_copy);
            setAnimStyle(BaseDialog.AnimStyle.BOTTOM);
            setGravity(Gravity.BOTTOM);
        }
    }
}
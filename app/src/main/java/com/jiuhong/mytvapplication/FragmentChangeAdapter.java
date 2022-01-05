package com.jiuhong.mytvapplication;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class FragmentChangeAdapter extends FragmentPagerAdapter {
    private List<Fragment> flist;//添加的Fragment就在这里

    public FragmentChangeAdapter(FragmentManager fm, List<Fragment> flist) {
        super(fm);
        this.flist = flist;
    }

    @Override
    public Fragment getItem(int position) {
        return flist.get(position);
    }

    @Override
    public int getCount() {

        return flist.size();
    }

}


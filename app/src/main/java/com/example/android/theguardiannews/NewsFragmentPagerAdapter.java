package com.example.android.theguardiannews;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by PWT on 2017/6/19.
 */

public class NewsFragmentPagerAdapter extends FragmentPagerAdapter {

    public static final String 安卓 = "安卓";
    public static final String 科学 = "科学";
    public static final String 体育 = "体育";
    public static final String 音乐 = "音乐";

    private Fragment[] pages = new Fragment[]{
            new AndroidFragment(),
            new ScienceFragment(),
            new SportsFragment(),
            new MusicFragment()
    };
    private String[] titles = new String[]{
            安卓, 科学, 体育, 音乐
    };

    public NewsFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return pages[position];
    }

    @Override
    public int getCount() {
        return pages.length;
    }
}

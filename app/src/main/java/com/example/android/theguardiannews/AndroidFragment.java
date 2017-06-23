package com.example.android.theguardiannews;

import android.support.v4.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AndroidFragment extends SuperFragment {

    private static final String TAG = "AndroidFragment";

    /**
     * 请求地址
     */
    private static final String REQ_URL = "http://content.guardianapis.com/search?tag=technology/android&from-date=2014-01-01&api-key=test";

    /**
     * loader的ID
     */
    private static final int ANDROID_NEWS_LOADER_ID = 1;

    @Override
    public String getReqUrl() {
        return REQ_URL;
    }

    @Override
    public int getAndroidNewsLoaderId() {
        return ANDROID_NEWS_LOADER_ID;
    }

    @Override
    public int getRootViewId() {
        return R.layout.news_list_activity;
    }
}

package com.example.android.theguardiannews;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicFragment extends SuperFragment {

    private static final String TAG = "MusicFragment";
    
    /**
     * 请求地址
     */
    private static final String REQ_URL = "http://content.guardianapis.com/search?tag=music/music&from-date=2014-01-01&api-key=test";

    /**
     * loader的ID
     */
    private static final int MUSIC_NEWS_LOADER_ID = 4;

    @Override
    public String getReqUrl() {
        return REQ_URL;
    }

    @Override
    public int getAndroidNewsLoaderId() {
        return MUSIC_NEWS_LOADER_ID;
    }

    @Override
    public int getRootViewId() {
        return R.layout.news_list_activity;
    }
}

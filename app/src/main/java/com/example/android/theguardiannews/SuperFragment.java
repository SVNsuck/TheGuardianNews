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
 * Created by PWT on 2017/6/23.
 */

public abstract  class SuperFragment  extends Fragment implements LoaderManager.LoaderCallbacks<List<News>> {

private static final String TAG = "SuperFragment";

//取出请求地址
public abstract String getReqUrl();
//取出 Loader Id
public abstract int getAndroidNewsLoaderId();
//取出 rootView id
public abstract int getRootViewId();

private NewsAdapter mNewsAdapter;

private ListView mNewsListView;



private LruCache<String, List<News>> mMemoryCaches;
private TextView mEmptyStateTextView;
private View bar;

public SuperFragment() {
        int maxMemory = (int)Runtime.getRuntime().maxMemory();
        int cacheSizes = maxMemory/5;
        mMemoryCaches = new LruCache<>(cacheSizes);
        }


@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {

        Log.i(TAG, "onCreateView: androidFrag");


        //使用 getRootView 取出 id
        View rootView = inflater.inflate(this.getRootViewId(), container, false);

        mNewsListView = (ListView)rootView.findViewById(R.id.list);
        mEmptyStateTextView = (TextView) rootView.findViewById(R.id.empty_view);
        mNewsListView.setEmptyView(mEmptyStateTextView);
        mEmptyStateTextView.setText("");
        ConnectivityManager cm =
        (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        bar = rootView.findViewById(R.id.pro_bar);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
        activeNetwork.isConnectedOrConnecting();
        if(isConnected){
        bar.setVisibility(View.VISIBLE);

        //取出 LoaderId
        getActivity().getSupportLoaderManager().initLoader(this.getAndroidNewsLoaderId(), null, SuperFragment.this);
        }else{
        mEmptyStateTextView.setText(R.string.no_network);
        }


        mNewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
@Override
public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        News currentNews = mNewsAdapter.getItem(position);
        Uri newsUri = Uri.parse(currentNews.getWebUrl());
        Intent websiteIntent = new Intent(Intent.ACTION_VIEW,newsUri);
        getActivity().startActivity(websiteIntent);
        }
        });
        return rootView;
        }

@Override
public Loader onCreateLoader(int id, Bundle args) {
        //取出请求的 URL
        return new NewsLoader(getContext(), this.getReqUrl(),mMemoryCaches);
        }

@Override
public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        bar.setVisibility(View.GONE);
        mNewsAdapter = new NewsAdapter(getActivity(), data, mNewsListView);
        mNewsListView.setAdapter(mNewsAdapter);
        }

@Override
public void onLoaderReset(Loader loader) {

        }
}

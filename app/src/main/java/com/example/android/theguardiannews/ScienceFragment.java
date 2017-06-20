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
public class ScienceFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<News>> {
    /**
     * 请求地址
     */
    private static final String REQ_URL = "http://content.guardianapis.com/search?tag=science/science&from-date=2014-01-01&api-key=test";

    /**
     * loader的ID
     */
    private static final int SCIENCE_NEWS_LOADER_ID = 2;

    private NewsAdapter mNewsAdapter;

    private ListView mNewsListView;

    private View bar;

    /**
     * 列表为空时显示的 TextView
     */
    private TextView mEmptyStateTextView;

    public ScienceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.news_list_activity, container, false);

        mNewsListView = (ListView)rootView.findViewById(R.id.list);

        //设置ListView内容为空时显示的layout
        mEmptyStateTextView = (TextView) rootView.findViewById(R.id.empty_view);
        mNewsListView.setEmptyView(mEmptyStateTextView);

        //将空页面的文字清空
        mEmptyStateTextView.setText("");
        //如果无网络连接,则显示空页面
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        //得到加载圈圈实体
        bar = rootView.findViewById(R.id.pro_bar);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(isConnected){
            //显示加载圈圈
            bar.setVisibility(View.VISIBLE);

            //每次点击都先将Loader销毁,不然这该死onCreateLoader方法只会在Loader初始化的时候调用一次,
            // 导致更新后的url传不到GBookLoader实例中,使得后面的搜索都不起作用了
            getActivity().getSupportLoaderManager().destroyLoader(SCIENCE_NEWS_LOADER_ID);
            //启动线程
            getActivity().getSupportLoaderManager().initLoader(SCIENCE_NEWS_LOADER_ID, null, ScienceFragment.this);
        }else{
            mEmptyStateTextView.setText(R.string.no_network);
        }

        //设置listView 中的item的点击事件
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
        return new NewsLoader(getContext(), REQ_URL, mNewsListView);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {

        //隐藏加载圈圈
        bar.setVisibility(View.GONE);

        //初始化Adapter
        mNewsAdapter = new NewsAdapter(getActivity(), data, mNewsListView);

        //设置adapter
        mNewsListView.setAdapter(mNewsAdapter);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}

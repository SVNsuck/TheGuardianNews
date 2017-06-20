package com.example.android.theguardiannews;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import static android.R.attr.data;
import static android.R.attr.resource;

/**
 * Created by PWT on 2017/6/19.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    private ListView mListView;

    private View mListItemView;

    private ViewHolder mViewHolder;

    private Context mContext;

    public NewsAdapter(@NonNull Context context, @NonNull List<News> data, ListView listView) {
        super(context, 0, data);
        this.mListView = listView;
        this.mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        mListItemView = convertView;
        if(mListItemView ==null){
            mViewHolder = new ViewHolder();
            mListItemView = LayoutInflater.from(mContext).inflate(R.layout.news_list_item,parent,false);
            mViewHolder.mNewsTitleView = (TextView)mListItemView.findViewById(R.id.news_title);
            mViewHolder.mNewsPublishDateView = (TextView)mListItemView.findViewById(R.id.news_publishDate);
            mListItemView.setTag(mViewHolder);
        }else{
            mViewHolder = (ViewHolder) mListItemView.getTag();
        }

        News news =  getItem(position);

        //设置标题
        mViewHolder.mNewsTitleView.setText(news.getTitle());

        //设置发布事件
        mViewHolder.mNewsPublishDateView.setText(news.getPublishDate());

        return mListItemView;

    }

    private class ViewHolder{

        public TextView mNewsTitleView;

        public TextView mNewsPublishDateView;
    }
}

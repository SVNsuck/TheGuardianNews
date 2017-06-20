package com.example.android.theguardiannews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by PWT on 2017/6/20.
 */

public class NewsLoader extends AsyncTaskLoader {

    private static final String TAG = "NewsLoader";

    private String mUrl;//查询URL

    /**
     * LRU缓存
     */
    private LruCache<String, List<News>> mMemoryCaches;

    /**
     *
     */
    private ListView mListView;

    public NewsLoader(Context context,String url, ListView listView){
        super(context);
        this.mUrl = url;

        this.mListView = listView;

        //最大内存
        int maxMemory = (int)Runtime.getRuntime().maxMemory();
        //缓存大小
        int cacheSizes = maxMemory/5;
        //初始化缓存
        mMemoryCaches = new LruCache<>(cacheSizes);
    }

    /**
     * @description 将bitmap添加到内存中去
     *
     * @param key
     * @param newsList
     */
    public void addItemToMemoryCache(String key, List<News> newsList) {
        if (getItemFromMemCache(key) == null) {
            mMemoryCaches.put(key, newsList);
        }
    }

    /**
     * @description 通过key来从内存缓存中获得bitmap对象
     *
     * @param key
     * @return
     */
    private List<News> getItemFromMemCache(String key) {
        return mMemoryCaches.get(key);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Object loadInBackground() {

        if(TextUtils.isEmpty(mUrl)){
            return null;
        }

        /**
         * 先从缓存里取item
         */
        List<News> newsList = getItemFromMemCache(mUrl);

        /**
         * 没有则根据mUrl下载
         */
        if(newsList == null){
            Log.i(TAG, "loadInBackground: 正在从网络加载item");
            newsList = new QueryUtils<News>(){
                @Override
                protected ArrayList<News> extractList(String json) {
                    // Create an empty ArrayList that we can start adding earthquakes to
                    ArrayList<News> newsList = new ArrayList<>();

                    // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
                    // is formatted, a JSONException exception object will be thrown.
                    // Catch the exception so the app doesn't crash, and print the error message to the logs.
                    try {

                        JSONObject jsonObject = new JSONObject(json);
                        if(jsonObject != null){
                            JSONObject responseJson = jsonObject.optJSONObject(Constant.jsonKey.RESPONSE);
                            JSONArray resultsArr = responseJson.optJSONArray(Constant.jsonKey.RESULTS);
                            if(resultsArr != null&&resultsArr.length()>0){
                                for(int i =0;i<resultsArr.length();i++){
                                    JSONObject news = (JSONObject) resultsArr.get(i);
                                    String webUrl = news.optString(Constant.jsonKey.WEB_URL);
                                    String newsTitle = news.optString(Constant.jsonKey.WEB_TITLE);
                                    String newsDate = news.optString(Constant.jsonKey.WEB_PUBLICATION_DATE);
                                    News newsEntity = new News(newsTitle,newsDate,webUrl);
                                    newsList.add(newsEntity);
                                }
                            }
                        }
                    } catch (JSONException e) {
                        // If an error is thrown when executing any of the above statements in the "try" block,
                        // catch the exception here, so the app doesn't crash. Print a log message
                        // with the message from the exception.
                        Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
                    }

                    //下载完成之后将其加入到LruCache中这样下次加载的时候，就可以直接从LruCache中直接读取
                    if(newsList != null){
                        addItemToMemoryCache(mUrl, newsList);
                    }
                    return newsList;
                }
            }.fetchEntityListData(mUrl);
        }else{
            Log.i(TAG, "loadInBackground: 已经从缓存获取到item");
        }


        return newsList;
    }
}

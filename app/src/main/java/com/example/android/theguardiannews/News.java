package com.example.android.theguardiannews;

/**
 * Created by PWT on 2017/6/19.
 */

public class News {

    /**
     * 新闻标题
     */
    private String title;

    /**
     * 新闻发布日期
     */
    private String publishDate;

    /**
     * 新闻的网页地址
     */
    private String webUrl;

    public News(String title, String publishDate, String webUrl) {
        this.title = title;
        this.publishDate = publishDate;
        this.webUrl = webUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public String getWebUrl() {
        return webUrl;
    }
}

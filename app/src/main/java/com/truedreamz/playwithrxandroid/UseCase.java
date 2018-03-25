package com.truedreamz.playwithrxandroid;


import com.truedreamz.data.dto.NewsItem;
import com.truedreamz.mvp.ui.listeners.BaseCallback;

import java.util.List;


public interface UseCase {
    void getNews(final BaseCallback callback);

    NewsItem searchByTitle(List<NewsItem> news, String keyWord);
}

package com.truedreamz.mvp;

import android.os.Bundle;
import android.support.annotation.VisibleForTesting;


import com.truedreamz.data.dto.NewsItem;
import com.truedreamz.data.dto.NewsModel;
import com.truedreamz.mvp.base.Presenter;
import com.truedreamz.mvp.ui.listeners.BaseCallback;
import com.truedreamz.mvp.ui.listeners.RecyclerItemListener;
import com.truedreamz.playwithrxandroid.NewsUseCase;

import java.util.List;

import javax.inject.Inject;



public class NewsPresenter extends Presenter<INewsContract.View> implements INewsContract.Presenter {

    private final NewsUseCase newsUseCase;
    private NewsModel newsModel;

    @Inject
    public NewsPresenter(NewsUseCase newsUseCase) {
        this.newsUseCase = newsUseCase;
    }

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        getNews();
    }

    @Override
    public void getNews() {
        getView().setLoaderVisibility(true);
        getView().setNoDataVisibility(false);
        getView().setListVisibility(false);
        getView().incrementCountingIdlingResource();
        newsUseCase.getNews(callback);
    }

    @Override
    public void unSubscribe() {
        newsUseCase.unSubscribe();
    }

    @Override
    public RecyclerItemListener getRecyclerItemListener() {
        return recyclerItemListener;
    }

    @Override
    public void onSearchClick(String newsTitle) {
        List<NewsItem> news = newsModel.getNewsItems();
        if (news!=null && !newsTitle.isEmpty()){
            NewsItem newsItem = newsUseCase.searchByTitle(news, newsTitle);
            if (newsItem!=null) {
                getView().navigateToDetailsScreen(newsItem);
            } else {
                getView().showSearchError();
            }
        } else {
            getView().showSearchError();
        }
    }

    private void showList(boolean isVisible) {
        getView().setNoDataVisibility(!isVisible);
        getView().setListVisibility(isVisible);
    }

    private final RecyclerItemListener recyclerItemListener = position -> {
        getView().navigateToDetailsScreen(newsModel.getNewsItems().get(position));
    };

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    public NewsModel getNewsModel() {
        return newsModel;
    }

    private final BaseCallback callback = new BaseCallback() {
        @Override
        public void onSuccess(NewsModel newsModel) {
            getView().decrementCountingIdlingResource();
            NewsPresenter.this.newsModel = newsModel;
            List<NewsItem> newsItems = null;
            if (newsModel!=null) {
                newsItems = newsModel.getNewsItems();
            }
            if (newsItems!=null && !newsItems.isEmpty()) {
                getView().initializeNewsList(newsModel.getNewsItems());
                showList(true);
            } else {
                showList(false);
            }
            getView().setLoaderVisibility(false);
        }

        @Override
        public void onFail() {
            getView().decrementCountingIdlingResource();
            showList(false);
            getView().setLoaderVisibility(false);
        }
    };
}

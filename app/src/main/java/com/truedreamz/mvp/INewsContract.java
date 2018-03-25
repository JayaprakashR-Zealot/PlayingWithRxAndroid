package com.truedreamz.mvp;

import com.truedreamz.data.dto.NewsItem;
import com.truedreamz.mvp.ui.listeners.BaseView;
import com.truedreamz.mvp.ui.listeners.RecyclerItemListener;

import java.util.List;


public interface INewsContract {

    interface View  extends BaseView{
        void initializeNewsList(List<NewsItem> news);

        void setLoaderVisibility(boolean isVisible);

        void navigateToDetailsScreen(NewsItem news);

        void setNoDataVisibility(boolean isVisible);

        void setListVisibility(boolean isVisible);

        void showSearchError();

        void showNoNewsError();

        void incrementCountingIdlingResource();

        void decrementCountingIdlingResource();
    }


    interface Presenter {
        void getNews();

        void onSearchClick(String newsTitle);

        void unSubscribe();

        RecyclerItemListener getRecyclerItemListener();
    }
}

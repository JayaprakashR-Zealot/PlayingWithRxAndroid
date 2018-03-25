package com.truedreamz.playwithrxandroid;


import com.truedreamz.data.DataRepository;
import com.truedreamz.data.dto.NewsItem;
import com.truedreamz.data.dto.NewsModel;
import com.truedreamz.mvp.ui.listeners.BaseCallback;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;



public class NewsUseCase implements UseCase {
    private DataRepository dataRepository;
    private CompositeDisposable compositeDisposable;
    private Disposable newsDisposable;
    Single<NewsModel> newsModelSingle;
    private DisposableSingleObserver<NewsModel> disposableSingleObserver;

    @Inject
    public NewsUseCase(DataRepository dataRepository, CompositeDisposable compositeDisposable) {
        this.dataRepository = dataRepository;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void getNews(BaseCallback callback) {
        disposableSingleObserver = new DisposableSingleObserver<NewsModel>() {
            @Override
            public void onSuccess(NewsModel newsModel) {
                callback.onSuccess(newsModel);
            }

            @Override
            public void onError(Throwable e) {
                callback.onFail();
            }
        };
        if (!compositeDisposable.isDisposed()) {
            newsModelSingle = dataRepository.requestNews();
            newsDisposable = newsModelSingle.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(disposableSingleObserver);
            compositeDisposable.add(newsDisposable);
        }
    }

    @Override
    public NewsItem searchByTitle(List<NewsItem> news, String keyWord) {
        for (NewsItem newsItem : news) {
            if (newsItem.getTitle().toLowerCase().contains(keyWord.toLowerCase())) {
                return newsItem;
            }
        }
        return null;
    }

    public void unSubscribe() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.remove(newsDisposable);
        }
    }
}

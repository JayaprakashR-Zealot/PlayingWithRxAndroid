package com.truedreamz.mvp.ui.listeners;


import com.truedreamz.data.dto.NewsModel;

public interface BaseCallback {
    void onSuccess(NewsModel newsModel);

    void onFail();
}

package com.truedreamz.data;


import com.truedreamz.data.dto.NewsModel;
import com.truedreamz.data.remote.RemoteRepository;

import javax.inject.Inject;

import io.reactivex.Single;



public class DataRepository implements DataSource {
    private RemoteRepository remoteRepository;
    //private LocalRepository localRepository;

    @Inject
    public DataRepository(RemoteRepository remoteRepository) {
        this.remoteRepository = remoteRepository;
        //this.localRepository = localRepository;
    }

    @Override
    public Single<NewsModel> requestNews() {
        return remoteRepository.getNews();
    }
}

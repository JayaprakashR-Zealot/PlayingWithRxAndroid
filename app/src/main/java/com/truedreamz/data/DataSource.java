package com.truedreamz.data;



import com.truedreamz.data.dto.NewsModel;

import io.reactivex.Single;


interface DataSource {
    Single<NewsModel> requestNews();
}

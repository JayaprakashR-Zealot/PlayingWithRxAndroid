package com.truedreamz.data.service;



import com.truedreamz.data.dto.NewsModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface INewsService {
    @GET("topstories/v2/home.json")
    Call<NewsModel> fetchNews();
}

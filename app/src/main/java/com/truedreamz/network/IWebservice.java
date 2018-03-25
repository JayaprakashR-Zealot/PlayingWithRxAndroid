package com.truedreamz.network;

import com.truedreamz.modal.Post;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by JAYAPRAKASH-FGTS on 27-02-2018.
 */

public interface IWebservice {

    /*@GET("posts")
    Call<List<Post>> getPost();*/

    @GET("posts")
    Single<List<Post>> getPost();
}

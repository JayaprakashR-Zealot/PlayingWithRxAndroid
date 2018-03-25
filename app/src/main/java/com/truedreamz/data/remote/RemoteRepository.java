package com.truedreamz.data.remote;

import android.accounts.NetworkErrorException;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.truedreamz.PlayRxAndroidApp;
import com.truedreamz.data.dto.NewsModel;
import com.truedreamz.data.service.INewsService;
import com.truedreamz.utility.Constants;
import com.truedreamz.utility.L;


import java.io.IOException;


import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.plugins.RxJavaPlugins;
import retrofit2.Call;

import static com.truedreamz.data.remote.ServiceError.NETWORK_ERROR;
import static com.truedreamz.data.remote.ServiceError.SUCCESS_CODE;
import static com.truedreamz.utility.Constants.ERROR_UNDEFINED;
import static com.truedreamz.utility.NetworkUtils.isConnected;
import static java.util.Objects.isNull;


public class RemoteRepository implements RemoteSource {
    private ServiceGenerator serviceGenerator;
    private final String EXCEPTION_TAG = "RemoteRepository";

    @Inject
    public RemoteRepository(ServiceGenerator serviceGenerator) {
        this.serviceGenerator = serviceGenerator;
    }

    @Override
    public Single<NewsModel> getNews() {
        RxJavaPlugins.setErrorHandler(throwable -> {
            Log.i(EXCEPTION_TAG, throwable.getMessage());
            return;
        });
        Single<NewsModel> newsModelSingle = Single.create(singleOnSubscribe -> {
                    if (!isConnected(PlayRxAndroidApp.getContext())) {
                        Exception exception = new NetworkErrorException();
                        singleOnSubscribe.onError(exception);
                    } else {
                        try {
                            INewsService newsService = serviceGenerator.createService(INewsService.class, Constants.BASE_URL);
                            ServiceResponse serviceResponse = processCall(newsService.fetchNews(), false);
                            if (serviceResponse.getCode() == SUCCESS_CODE) {
                                NewsModel newsModel = (NewsModel) serviceResponse.getData();
                                singleOnSubscribe.onSuccess(newsModel);
                            } else {
                                Throwable throwable = new NetworkErrorException();
                                singleOnSubscribe.onError(throwable);
                            }
                        } catch (Exception e) {
                            singleOnSubscribe.onError(e);
                        }
                    }
                }
        );
        return newsModelSingle;
    }

    @NonNull
    private ServiceResponse processCall(Call call, boolean isVoid) {
        if (!isConnected(PlayRxAndroidApp.getContext())) {
            return new ServiceResponse(new ServiceError());
        }
        try {
            retrofit2.Response response = call.execute();
            Gson gson = new Gson();
            L.json(NewsModel.class.getName(), gson.toJson(response.body()));
            if (response==null) {
                return new ServiceResponse(new ServiceError(NETWORK_ERROR, ERROR_UNDEFINED));
            }
            int responseCode = response.code();
            if (response.isSuccessful()) {
                return new ServiceResponse(responseCode, isVoid ? null : response.body());
            } else {
                ServiceError ServiceError;
                ServiceError = new ServiceError(response.message(), responseCode);
                return new ServiceResponse(ServiceError);
            }
        } catch (IOException e) {
            return new ServiceResponse(new ServiceError(NETWORK_ERROR, ERROR_UNDEFINED));
        }
    }
}

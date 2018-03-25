package com.truedreamz.data.remote;

import io.reactivex.Single;

interface RemoteSource {
    Single getNews();
}

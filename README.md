# PlayingWithRxAndroid
I have implemented calling API, scheduling task for every 5 seconds, and MVP design pattern using RxAndroid.

*******************Calling API RxAndroid methods*************

		IWebservice apiInterface= RetrofitAPIClient.getRetroClient().create(IWebservice.class);

        mCompositeDisposable.add(apiInterface.getPost()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Post>>() {
                    @Override
                    public void accept(List<Post> posts) throws Exception {
                        //List<Post> postList=response.body();
                        Log.d(TAG,"Post response:"+posts);
                        setAdapter(view,posts);
                    }
                }));

                
 **************** Scheduling task for every 5 seconds ********************************
 
 DisposableObserver<Boolean> d = getDisposableObserver();

        getObservable()
                .repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Object> objectObservable) throws Exception {
                        return objectObservable.delay(5, TimeUnit.SECONDS);
                    }
                })
                //.replay(10,TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(d);


        disposables.add(d);
        
*****************MVP design pattern***********************

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

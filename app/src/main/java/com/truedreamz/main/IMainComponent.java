package com.truedreamz.main;

import com.truedreamz.fragment.NewsFragment;
import com.truedreamz.mvp.NewsActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by JAYAPRAKASH-FGTS on 20-03-2018.
 */

@Singleton
@Component(modules = MainModule.class)
public interface IMainComponent {
    void inject(NewsActivity newsActivity);
}

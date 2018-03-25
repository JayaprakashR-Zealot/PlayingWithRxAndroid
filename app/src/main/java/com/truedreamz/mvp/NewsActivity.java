package com.truedreamz.mvp;

import android.content.Intent;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.truedreamz.PlayRxAndroidApp;
import com.truedreamz.data.dto.NewsItem;
import com.truedreamz.mvp.base.BaseActivity;
import com.truedreamz.mvp.ui.NewsAdapter;
import com.truedreamz.playwithrxandroid.R;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static android.support.design.widget.Snackbar.LENGTH_SHORT;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class NewsActivity extends BaseActivity implements INewsContract.View  {

    @Inject
    NewsPresenter presenter;
    @BindView(R.id.rv_news_list)
    RecyclerView rvNews;
    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;
    @BindView(R.id.rl_news_list)
    RelativeLayout rlNewsList;
    @BindView(R.id.btn_search)
    ImageButton btnSearch;
    @BindView(R.id.et_search)
    EditText editTextSearch;

    @Override
    protected void initializeDagger() {
        PlayRxAndroidApp app = (PlayRxAndroidApp) getApplicationContext();
        app.getMainComponent().inject(NewsActivity.this);
    }

    @Override
    protected void initializePresenter() {
        super.presenter = presenter;
        presenter.setView(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_news;
    }

    @Override
    public void initializeNewsList(List<NewsItem> news) {
        NewsAdapter newsAdapter = new NewsAdapter(presenter.getRecyclerItemListener(), news);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvNews.setLayoutManager(layoutManager);
        rvNews.setHasFixedSize(true);
        rvNews.setAdapter(newsAdapter);
    }

    @Override
    public void setLoaderVisibility(boolean isVisible) {
        pbLoading.setVisibility(isVisible ? VISIBLE : GONE);
    }

    @Override
    public void navigateToDetailsScreen(NewsItem news) {
        Toast.makeText(this,news.getTitle()+" is clicked.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setNoDataVisibility(boolean isVisible) {
        tvNoData.setVisibility(isVisible ? VISIBLE : GONE);
    }

    @Override
    public void setListVisibility(boolean isVisible) {
        rlNewsList.setVisibility(isVisible ? VISIBLE : GONE);
    }

    @Override
    public void showSearchError() {
        Snackbar.make(rlNewsList, getString(R.string.search_error), LENGTH_SHORT).show();
    }

    @Override
    public void showNoNewsError() {
        Snackbar.make(rlNewsList, getString(R.string.news_error), LENGTH_SHORT).show();
    }

    @Override
    public void incrementCountingIdlingResource() {
        //increment();
    }

    @Override
    public void decrementCountingIdlingResource() {
       // decrement();
    }

    @OnClick({R.id.ic_toolbar_setting, R.id.ic_toolbar_refresh, R.id.btn_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ic_toolbar_refresh:
                presenter.getNews();
                break;
            case R.id.btn_search:
                presenter.onSearchClick(editTextSearch.getText().toString());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unSubscribe();
    }

}

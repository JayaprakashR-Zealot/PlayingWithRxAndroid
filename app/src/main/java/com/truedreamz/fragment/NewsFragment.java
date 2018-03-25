package com.truedreamz.fragment;


import android.app.ActionBar;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.truedreamz.PlayRxAndroidApp;
import com.truedreamz.data.dto.NewsItem;
import com.truedreamz.mvp.INewsContract;
import com.truedreamz.mvp.NewsPresenter;
import com.truedreamz.mvp.base.BaseFragment;
import com.truedreamz.mvp.ui.NewsAdapter;
import com.truedreamz.playwithrxandroid.R;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.widget.Toast.LENGTH_SHORT;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends BaseFragment implements INewsContract.View{

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


    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initializeDagger() {
        PlayRxAndroidApp app = (PlayRxAndroidApp) getActivity().getApplicationContext();
        //app.getMainComponent().inject(NewsFragment.this);
    }

    @Override
    protected void initializePresenter() {
        super.presenter = presenter;
        presenter.setView(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void initializeNewsList(List<NewsItem> news) {
        NewsAdapter newsAdapter = new NewsAdapter(presenter.getRecyclerItemListener(), news);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
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
        Toast.makeText(getActivity(),news.getTitle()+" is clicked.", LENGTH_SHORT).show();
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
        Snackbar.make(rlNewsList, getString(R.string.news_error), Snackbar.LENGTH_SHORT).show();
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
    public void onDestroy() {
        super.onDestroy();
        if(presenter!=null)presenter.unSubscribe();
    }

    @Override
    public void incrementCountingIdlingResource() {

    }

    @Override
    public void decrementCountingIdlingResource() {

    }

    @Override
    public void setUpIconVisibility(boolean visible) {
        final ActionBar actionBar = getActivity().getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(visible);
        }
    }

    @Override
    public void setSettingsIconVisibility(boolean visible) {
        final ActionBar actionBar = getActivity().getActionBar();
        if (actionBar != null) {
            ImageView icon = ButterKnife.findById(getActivity(), R.id.ic_toolbar_setting);
            if (icon != null) {
                icon.setVisibility(visible ? VISIBLE : GONE);
            }
        }
    }

    @Override
    public void setRefreshVisibility(boolean visible) {
        final ActionBar actionBar = getActivity().getActionBar();
        if (actionBar != null) {
            ImageView icon = ButterKnife.findById(getActivity(), R.id.ic_toolbar_refresh);
            if (icon != null) {
                icon.setVisibility(visible ? VISIBLE : GONE);
            }
        }
    }
}

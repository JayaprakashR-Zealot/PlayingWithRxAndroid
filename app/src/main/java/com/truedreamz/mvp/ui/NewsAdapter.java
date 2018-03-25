package com.truedreamz.mvp.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.truedreamz.data.dto.NewsItem;
import com.truedreamz.mvp.ui.listeners.RecyclerItemListener;
import com.truedreamz.playwithrxandroid.R;

import java.util.List;


public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {
    private final List<NewsItem> news;
    private RecyclerItemListener onItemClickListener;

    public NewsAdapter(RecyclerItemListener onItemClickListener, List<NewsItem> news) {
        this.onItemClickListener = onItemClickListener;
        this.news = news;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new NewsViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        holder.bind(position, news.get(position), onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }
}


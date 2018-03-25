package com.truedreamz.mvp.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.squareup.picasso.Picasso;
import com.truedreamz.data.dto.NewsItem;
import com.truedreamz.mvp.ui.listeners.RecyclerItemListener;
import com.truedreamz.playwithrxandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.text.TextUtils.isEmpty;
import static com.truedreamz.utility.ResourcesUtil.getDrawableById;
import static java.util.Objects.isNull;


public class NewsViewHolder extends RecyclerView.ViewHolder {

    private RecyclerItemListener onItemClickListener;

    @BindView(R.id.tv_caption)
    TextView tvCaption;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_news_item)
    RelativeLayout newsItemLayout;
    @BindView(R.id.iv_news_item_image)
    ImageView newsImage;


    public NewsViewHolder(View itemView, RecyclerItemListener onItemClickListener) {
        super(itemView);
        this.onItemClickListener = onItemClickListener;
        ButterKnife.bind(this, itemView);
    }

    public void bind(int position, NewsItem newsItem, RecyclerItemListener recyclerItemListener) {
        //need to move to mapper
        if (!isEmpty(newsItem.getAbstract())) {
            tvTitle.setText(newsItem.getTitle());
        }
        if (!isEmpty(newsItem.getTitle())) {
            tvCaption.setText(newsItem.getAbstract());
        }
        String URL = null;
        if ((newsItem.getMultimedia()!=null) && newsItem.getMultimedia().size() > 3) {
            URL = newsItem.getMultimedia().get(3).getUrl();
        }
        Picasso.with(newsImage.getContext()).load(URL).placeholder(getDrawableById(R.drawable.news)).into(newsImage);
        newsItemLayout.setOnClickListener(v -> recyclerItemListener.onItemSelected(position));
    }
}


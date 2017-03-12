package com.dy.workshop;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public class BookListAdapter extends RecyclerView.Adapter {
  private final int VIEW_ITEM = 1;
  private final int VIEW_PROGRESS = 0;

  private int visibleThreshold = 5;
  private int lastVisibleItem, totalItemCount;
  private boolean loading;


  private List<Book> mBooks = new ArrayList<>();
  private OnLoadMoreListener onLoadMoreListener;

  public BookListAdapter(RecyclerView recyclerView) {
    if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

      final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
          .getLayoutManager();


      recyclerView
          .addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView,
                                   int dx, int dy) {
              super.onScrolled(recyclerView, dx, dy);

              totalItemCount = linearLayoutManager.getItemCount();
              lastVisibleItem = linearLayoutManager
                  .findLastVisibleItemPosition();
              if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                // End has been reached
                // Do something
                if (onLoadMoreListener != null) {
                  onLoadMoreListener.onLoadMore();
                }
                loading = true;
              }
            }
          });
    }

  }


  public void addAll(List<Book> newBooks) {
    mBooks.addAll(newBooks);
    notifyDataSetChanged();
  }

  public void setmBooks(List<Book> mBooks) {
    this.mBooks = mBooks;
  }

  public void clearAll() {
    mBooks.clear();
  }

  @Override
  public int getItemViewType(int position) {
    return mBooks.get(position) != null ? VIEW_ITEM : VIEW_PROGRESS;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    RecyclerView.ViewHolder vh;
    if (viewType == VIEW_ITEM) {
      View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_book, parent, false);
      vh = new BookViewHolder(v);
    } else {

      View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.progressbar_item, parent, false);
      vh = new ProgressViewHolder(v);
    }
    return vh;
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (holder instanceof BookViewHolder) {
      final Book data = mBooks.get(position);
      ((BookViewHolder) holder).title.setText(data.getTitle());
      ((BookViewHolder) holder).summary.setText(data.getSummary());
      ((BookViewHolder) holder).information.setText(data.getInformation());
      ((BookViewHolder) holder).ratingBar.setRating((float) (data.getRating() / 2));
      ((BookViewHolder) holder).ratingVal.setText(String.valueOf(data.getRating()));

      Glide
          .with(Workshop.getApplication())
          .load(data.getImage())
          .centerCrop()
          .placeholder(R.drawable.ic_default_cover)
          .crossFade()
          .into(((BookViewHolder) holder).image);
    } else {
      ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
    }
  }

  @Override
  public int getItemCount() {
    return mBooks.size();
  }

  public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
    this.onLoadMoreListener = onLoadMoreListener;
  }

  public void setLoaded() {
    loading = false;
  }

  public class BookViewHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public TextView information;
    public TextView summary;
    public ImageView image;
    public RatingBar ratingBar;
    public TextView ratingVal;

    public BookViewHolder(View v) {
      super(v);

      title = (TextView) v.findViewById(R.id.title);
      information = (TextView) v.findViewById(R.id.information);
      summary = (TextView) v.findViewById(R.id.summary);
      image = (ImageView) v.findViewById(R.id.thumbnail);
      ratingBar = (RatingBar) v.findViewById(R.id.rating);
      ratingVal = (TextView) v.findViewById(R.id.ratingValue);
    }
  }

  private class ProgressViewHolder extends RecyclerView.ViewHolder {
    public ProgressBar progressBar;

    public ProgressViewHolder(View v) {
      super(v);
      progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
    }
  }
}

package com.dy.workshop;

import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.Voice;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

  private static final String DATA_URL = "https://api.douban.com/v2/book/search?tag=%s&count=%d&start=%d";
  private static final String DATA_TAG = "IT";
  private static final int DATA_PER_PAGE = 20;
  private static final int DATA_INITIAL_START = 0;

  private RecyclerView mRecyclerView;
  private BookListAdapter mAdapter;

  private RecyclerView.LayoutManager mLayoutManager;
  private SwipeRefreshLayout mRefreshLayout;
  private boolean isLoading;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
    mRecyclerView.setHasFixedSize(true);

    mLayoutManager = new LinearLayoutManager(this);
    mRecyclerView.setLayoutManager(mLayoutManager);

    mAdapter = new BookListAdapter();
    mRecyclerView.setAdapter(mAdapter);

    mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
    mRefreshLayout.setOnRefreshListener(this);

    doRefresh();
  }

  @Override
  public void onRefresh() {
    doRefresh();
  }

  private void doRefresh() {
    new LoadDataTask() {
      @Override
      protected void onPreExecute() {
        super.onPreExecute();
        isLoading = true;
        if (!mRefreshLayout.isRefreshing()) {
          mRefreshLayout.setRefreshing(true);
        }

      }

      @Override
      protected void onPostExecute(BookData bookData) {
        super.onPostExecute(bookData);
        isLoading = false;
        if (mRefreshLayout.isRefreshing()) {
          mRefreshLayout.setRefreshing(false);
        }
        mRefreshLayout.setRefreshing(false);
        mAdapter.clearAll();
        mAdapter.addAll(bookData.getBooks());
      }
    }.execute(getDataUrl(DATA_INITIAL_START));
  }

  public static class LoadDataTask extends AsyncTask<String, Voice, BookData> {
    @Override
    protected BookData doInBackground(String... params) {
      return BookData.from(DataLoader.loadJSONData(params[0]));
    }
  }

  private String getDataUrl(int start) {
    return String.format(Locale.ENGLISH, DATA_URL, DATA_TAG, DATA_PER_PAGE, start);
  }

}

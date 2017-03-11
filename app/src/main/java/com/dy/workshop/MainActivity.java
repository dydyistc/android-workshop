package com.dy.workshop;

import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.Voice;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

  private RecyclerView mRecyclerView;
  private BookListAdapter mAdapter;

  private RecyclerView.LayoutManager mLayoutManager;
  private SwipeRefreshLayout mSwipeRefreshLayout;

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

    mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
    mSwipeRefreshLayout.setOnRefreshListener(this);
    doRefresh();
  }

  @Override
  public void onRefresh() {
    doRefresh();
  }

  private void doRefresh() {
    new LoadDataTask() {
      @Override
      protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.clearAll();
        mAdapter.addAll(new BookData(jsonObject).getBooks());
      }
    }.execute();
  }

  public static class LoadDataTask extends AsyncTask<Void, Voice, JSONObject> {
    @Override
    protected JSONObject doInBackground(Void... params) {
      return DataLoader.loadJSONData(Workshop.getApplication());
    }
  }

}

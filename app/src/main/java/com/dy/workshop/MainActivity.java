package com.dy.workshop;

import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.Voice;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

  private ListView mListView;
  private ArrayAdapter<Book> bookArrayAdapter;
  private SwipeRefreshLayout mSwipeRefreshLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mListView = (ListView) findViewById(android.R.id.list);
    mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
    mSwipeRefreshLayout.setOnRefreshListener(this);

    bookArrayAdapter = new ArrayAdapter<Book>(this, R.layout.list_item_book) {
      @Override
      public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_book, parent, false);

        final ImageView bookImage = (ImageView) view.findViewById(R.id.thumbnail);
        TextView bookTitle = (TextView) view.findViewById(R.id.title);
        TextView bookSummary = (TextView) view.findViewById(R.id.summary);
        TextView bookInformation = (TextView) view.findViewById(R.id.information);
        TextView ratingVal = (TextView) view.findViewById(R.id.ratingValue);
        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.rating);

        final Book book = getItem(position);

        bookTitle.setText(book.getTitle());
        bookSummary.setText(book.getSummary());
        bookInformation.setText(book.getInformation());
        ratingBar.setRating((float) book.getRating() / 2);
        ratingVal.setText(String.valueOf(book.getRating()));


        Glide.with(getContext())
            .load(book.getImage())
            .centerCrop()
            .placeholder(R.drawable.ic_default_cover)
            .crossFade()
            .into(bookImage);


        return view;
      }
    };
    mListView.setAdapter(bookArrayAdapter);
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
        bookArrayAdapter.clear();
        bookArrayAdapter.addAll(new BookData(jsonObject).getBooks());
      }
    }.execute("https://api.douban" +
        ".com/v2/book/search?tag=IT&count=100");
  }

  public static class LoadDataTask extends AsyncTask<String, Voice, JSONObject> {
    @Override
    protected JSONObject doInBackground(String... params) {
      return DataLoader.loadData(params[0]);
    }
  }

}

package com.dy.workshop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

  private ListView mListView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final BookData bookData = new BookData(DataLoader.loadData(this));

    mListView = (ListView) findViewById(android.R.id.list);


    mListView.setAdapter(new ArrayAdapter<Book>(this, R.layout.list_item_book, bookData.getBooks()) {
      @Override
      public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_book, parent, false);
        ImageView bookImage = (ImageView) view.findViewById(R.id.thumbnail);
        TextView bookTitle = (TextView) view.findViewById(R.id.title);
        TextView bookSummary = (TextView) view.findViewById(R.id.summary);
        TextView bookInformation = (TextView) view.findViewById(R.id.information);
        TextView ratingVal = (TextView) view.findViewById(R.id.ratingValue);
        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.rating);

        Book book = getItem(position);
        bookTitle.setText(book.getTitle());
        bookSummary.setText(book.getSummary());
        bookInformation.setText(book.getInformation());
        ratingBar.setRating((float) book.getRating() / 2);
        ratingVal.setText(String.valueOf(book.getRating()));

        return view;
      }
    });
  }
}

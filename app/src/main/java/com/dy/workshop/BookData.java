package com.dy.workshop;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookData {
  private static final String COUNT = "count";
  private static final String START = "start";
  private static final String TOTAL = "total";
  private static final String BOOKS = "books";

  private final JSONObject mJsonObject;

  public BookData(JSONObject mJsonObject) {
    this.mJsonObject = mJsonObject;
  }

  public static String getCOUNT() {
    return COUNT;
  }

  public static String getSTART() {
    return START;
  }

  public static String getTOTAL() {
    return TOTAL;
  }

  public static String getBOOKS() {
    return BOOKS;
  }

  public List<Book> getBooks() {
    JSONArray jsonArray = mJsonObject.optJSONArray(BOOKS);
    List<Book> books = new ArrayList<>(jsonArray.length());

    for (int i = 0; i < jsonArray.length(); i++) {
      JSONObject object = (JSONObject) jsonArray.opt(i);
      books.add(
          new Book(
              object.optString("image"),
              object.optString("title"),
              object.optJSONArray("author").toString(),
              object.optString("publisher"),
              object.optString("pubdate"),
              object.optString("summary"),
              object.optJSONObject("rating").optDouble("average")
          ));
    }
    return books;
  }

}

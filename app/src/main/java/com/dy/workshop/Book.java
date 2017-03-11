package com.dy.workshop;

import android.text.TextUtils;

public class Book {

  private String image;
  private String title;
  private String author;
  private String publisher;
  private String publishDate;
  private String summary;
  private double rating;

  public Book(String image, String title, String author, String publisher, String publishDate, String summary, double rating) {
    this.image = image;
    this.title = title;
    this.author = author;
    this.publisher = publisher;
    this.publishDate = publishDate;
    this.summary = summary;
    this.rating = rating;
  }

  public String getTitle() {
    return title;
  }


  public String getAuthor() {
    return author;
  }

  public String getPublisher() {
    return publisher;
  }

  public String getPublishDate() {
    return publishDate;
  }

  public String getSummary() {
    return summary;
  }

  public double getRating() {
    return rating;
  }

  public String getInformation() {
    return TextUtils.join("/", new String[]{getAuthor(), getPublisher(), getPublishDate()});
  }

  public String getImage() {
    return image;
  }

}

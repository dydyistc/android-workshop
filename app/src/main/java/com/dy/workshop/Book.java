package com.dy.workshop;

public class Book {
  private String title;
  private String name;
  private String author;
  private String publisher;
  private String publishDate;
  private String summary;
  private double rating;

  public Book(String title, String name, String author, String publisher, String publishDate, String summary, double rating) {
    this.title = title;
    this.name = name;
    this.author = author;
    this.publisher = publisher;
    this.publishDate = publishDate;
    this.summary = summary;
    this.rating = rating;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public void setPublishDate(String publishDate) {
    this.publishDate = publishDate;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public void setRating(double rating) {
    this.rating = rating;
  }

  public String getTitle() {
    return title;
  }

  public String getName() {
    return name;
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

}

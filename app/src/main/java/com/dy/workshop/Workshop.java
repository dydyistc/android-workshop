package com.dy.workshop;

import android.app.Application;
import android.content.Context;

public class Workshop extends Application {
  private static Application instance;

  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;
  }

  public static Context getApplication() {
    return instance;
  }
}

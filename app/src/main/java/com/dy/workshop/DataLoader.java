package com.dy.workshop;


import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataLoader {
  private static final String TAG = "DataLoader";

  public static final JSONObject loadJSONData(final String urlString) {
    StringBuilder contentBuilder = new StringBuilder();

    try {
      URL url = new URL(urlString);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      BufferedInputStream inputStream = new BufferedInputStream(connection.getInputStream());

      byte[] buffer = new byte[1024];

      while (inputStream.read(buffer) != -1) {
        contentBuilder.append(new String(buffer, "UTF-8"));
      }

      inputStream.close();
      connection.disconnect();
      return new JSONObject(contentBuilder.toString());
    } catch (Exception e) {
      Log.e(TAG, e.getLocalizedMessage(), e);
    }

    return null;
  }

}

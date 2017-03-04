package com.dy.workshop;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataLoader {
  private static final String TAG = DataLoader.class.toString();

  public final static JSONObject loadData(String urlString) {
    StringBuilder contentBuilder = new StringBuilder();
    String line = null;

    try {
      URL url = new URL(urlString);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.setReadTimeout(15000);
      connection.setConnectTimeout(15000);
      connection.connect();

      BufferedReader rd = new BufferedReader(
          new InputStreamReader(connection.getInputStream())
      );

      while ((line = rd.readLine()) != null) {
        contentBuilder.append(line);
      }

      rd.close();
      connection.disconnect();

      return new JSONObject(contentBuilder.toString());

    } catch (IOException | JSONException e) {
      e.printStackTrace();
    }
    return null;
  }

}

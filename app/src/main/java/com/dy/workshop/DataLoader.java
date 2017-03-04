package com.dy.workshop;


import static android.util.Xml.Encoding.UTF_8;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class DataLoader {
  private static final String TAG = DataLoader.class.toString();

  public final static JSONObject loadData(Context context) {
    JSONObject jsonObject = new JSONObject();

    InputStream inputStream = context.getResources().openRawResource(R.raw.data);

    try {
      int size = inputStream.available();
      byte[] buffer = new byte[size];
      inputStream.read(buffer);
      inputStream.close();
      jsonObject = new JSONObject(new String(buffer, UTF_8.toString()));
    } catch (IOException | JSONException e) {
      e.printStackTrace();
      Log.e(TAG, e.getLocalizedMessage(), e);
    } finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    return jsonObject;
  }


}

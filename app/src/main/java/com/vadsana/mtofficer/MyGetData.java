package com.vadsana.mtofficer;

import android.content.Context;
import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import static android.net.wifi.WifiConfiguration.Status.strings;

/**
 * Created by User on 19/3/2560.
 */

public class MyGetData extends AsyncTask<String, Void, String>{

    private Context context;

    public MyGetData(Context context) {
       this.context = context;
    }


    @Override
    protected String doInBackground(String... params) {

        try {

            OkHttpClient okHttpClient = new OkHttpClient();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(params[0]).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }
}


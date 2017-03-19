package com.vadsana.mtofficer;

import android.content.Context;
import android.os.AsyncTask;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * Created by masterUNG on 3/19/2017 AD.
 */

public class MyPostData extends AsyncTask<Void, Void, String>{

    private static final String urlPHP = "http://swiftcodingthai.com/4mar/addUserMaster.php";
    private Context context;
    private String nameString, userString, passwordString, imageString;

    public MyPostData(Context context,
                      String nameString,
                      String userString,
                      String passwordString,
                      String imageString) {
        this.context = context;
        this.nameString = nameString;
        this.userString = userString;
        this.passwordString = passwordString;
        this.imageString = imageString;
    }

    @Override
    protected String doInBackground(Void... voids) {

        try {

            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("isAdd", "true")
                    .add("Name", nameString)
                    .add("User", userString)
                    .add("Password", passwordString)
                    .add("Image", imageString)
                    .build();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(urlPHP).post(requestBody).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }
}   // Main Class
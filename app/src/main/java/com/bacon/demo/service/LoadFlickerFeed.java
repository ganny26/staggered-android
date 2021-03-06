package com.bacon.demo.service;



import android.os.AsyncTask;

import java.io.*;


import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by serajam on 6/8/2017.
 */

public class LoadFlickerFeed extends AsyncTask<Integer,Void,String> {



    public static final String API_URL = "https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=c79895ea6c454b50e1c7ce72f887f24c";
    //private static final String API_URL = "http://192.168.0.106/recipes/";


    @Override
    protected String doInBackground(Integer... params) {

        int pageNumber = params[0];
        String API_URL_PAGE = API_URL + "&page=" + pageNumber;
     //   String API_URL_PAGE = API_URL + "?page=" + pageNumber;
        String dataResult = null;
        try{
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(API_URL_PAGE).build();
//            RequestBody requestBody = new MultipartBody.Builder()
//                    .setType(MultipartBody.FORM)
//                    .addFormDataPart("","")
//                    .build();
//
//            Request request = new Request.Builder()
//                    .url(API_URL_PAGE)
//                    .method("POST", RequestBody.create(null, new byte[0]))
//                    .post(requestBody)
//                    .build();
            Response response = client.newCall(request).execute();
            dataResult =  response.body().string();

        }catch(IOException e){
            e.printStackTrace();
        }
        return dataResult;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}


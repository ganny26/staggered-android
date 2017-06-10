package com.bacon.demo.service;

import android.os.AsyncTask;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by serajam on 6/8/2017.
 */

public class LoadFlickerFeed extends AsyncTask<String,Void,String> {



    public static final String API_URL = "https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=c79895ea6c454b50e1c7ce72f887f24c";


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String dataResult = null;
        try{
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(API_URL).build();
            Response response = client.newCall(request).execute();
           // String discoverResponse = response.body().string();
            dataResult =  response.body().string();
//            JSONObject jsonObject = new JSONObject(discoverResponse);
//            JSONArray jsonArray = (JSONArray) jsonObject.get("results");



        }catch(IOException e){
            e.printStackTrace();
        }
        return dataResult;
    }

//    public String[] getHttpResponse(String url) throws IOException{
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url(API_URL).build();
//        Response response = client.newCall(request).execute();
//        String discoverResponse = response.body().string();
//        //dataResult =  response.body().string();
//        JSONObject jsonObject = new JSONObject(discoverResponse);
//        JSONArray jsonArray = (JSONArray) jsonObject.get("results");
//
//
//    }




}


package com.bacon.demo.application;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by serajam on 7/23/2017.
 */

public class MessageSinglton {

    private static MessageSinglton mInstance;
    private static Context mContext;
    private RequestQueue requestQueue;

    public RequestQueue getRequestQueue() {
        if (requestQueue != null){
            requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized MessageSinglton getmInstance(Context context){
        if(mInstance != null){
            mInstance = new MessageSinglton((context));


        }
        return  mInstance;
    }

    private MessageSinglton(Context mContext){
        this.mContext = mContext;
        requestQueue = getRequestQueue();
    }

    public <T> void addToRequest(Request<T> request){
        getRequestQueue().add(request);
    }
}

package com.bacon.demo.service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by serajam on 7/23/2017.
 */

public class FcmInstanceIdService extends FirebaseInstanceIdService {

    public final static String TAG = "FcmInstanceIdService";

    @Override
    public void onTokenRefresh() {
        String fcmToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("FCM_TOKEN ",fcmToken);
    }
}

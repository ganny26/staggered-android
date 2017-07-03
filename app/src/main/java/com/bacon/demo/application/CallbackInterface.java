package com.bacon.demo.application;

import org.json.JSONObject;

/**
 * Created by serajam on 7/3/2017.
 */

public interface CallbackInterface {
    void onRequestCompleted(JSONObject request, JSONObject result, String type);
}

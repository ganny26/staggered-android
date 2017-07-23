package com.bacon.demo.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.bacon.demo.R;
import com.bacon.demo.activities.MainActivity;
import com.bacon.demo.application.MessageSinglton;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by serajam on 7/23/2017.
 */

public class FcmMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FcmMessagingService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG,"Invoked Fire base API");
        Log.d("MESSAGE",""+remoteMessage);
        if(remoteMessage.getData().size() > 0){
            String title,message,imageUrl;
            Bitmap bitmap = null;

            Log.i(TAG,"Remote Message"+remoteMessage);
            Log.d("Title",""+ remoteMessage.getData().get("title"));
            title = remoteMessage.getData().get("title");
            message = remoteMessage.getData().get("message");
           imageUrl = remoteMessage.getData().get("imageUrl");
//            try {
//                URL aURL = new URL(imageUrl);
//                URLConnection conn = aURL.openConnection();
//                conn.connect();
//                InputStream is = conn.getInputStream();
//                BufferedInputStream bis = new BufferedInputStream(is);
//                bitmap = BitmapFactory.decodeStream(bis);
//                bis.close();
//                is.close();
//            } catch (IOException e) {
//                Log.e(TAG, "Error getting bitmap", e);
//            }


            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,0, intent, PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
            notificationBuilder.setContentTitle(title);
            notificationBuilder.setContentText(message);
            notificationBuilder.setContentIntent(pendingIntent);
            notificationBuilder.setSound(defaultSoundUri);
            notificationBuilder.setSmallIcon(R.drawable.notificationlogo);
            notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap));


            ImageRequest imageRequest = new ImageRequest(imageUrl,new Response.Listener<Bitmap>(){
                @Override
                public void onResponse(Bitmap response) {
                    notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(response));
                    NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                    notificationManager.notify(0,notificationBuilder.build());

                }
            },0,0,null,new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            MessageSinglton.getmInstance(this).addToRequest(imageRequest);
        }

    }
}

package com.bacon.demo.activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bacon.demo.R;

public class ResultActivity extends AppCompatActivity {

    private NotificationCompat.Builder mNotification;
    private static final int uniqueID = 12342;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mNotification = new NotificationCompat.Builder(this);
        mNotification.setAutoCancel(true);
    }

    public void notificationClicked(View view){
        mNotification.setSmallIcon(R.drawable.notificationlogo);
        mNotification.setTicker("New Recipe");
        mNotification.setWhen(System.currentTimeMillis());
        mNotification.setContentTitle("New recipes for you to stay fit");
        mNotification.setContentText("Archana's kitchen");

        mNotification.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        mNotification.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(uniqueID,mNotification.build());


    }
}

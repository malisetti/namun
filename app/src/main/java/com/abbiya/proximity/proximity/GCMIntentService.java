package com.abbiya.proximity.proximity;

/**
 * Created by seshachalam on 14/10/14.
 */
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import com.proximity5.px5.sdk.PX5NotificationManager;

public class GCMIntentService extends IntentService {
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;

    NotificationCompat.Builder builder;

    public GCMIntentService() {
        super("GCMIntentService");
    }
    public static final String TAG = GCMIntentService.class.getName();

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle

            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                //sendNotification("Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                //sendNotification("Deleted messages on server: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                //This loop represents the service doing some work.
                Log.i(TAG, "Message Recieved @ " + SystemClock.elapsedRealtime());
                // Post notification of received message.

                if (extras.containsKey("px5_type"))
                {
                    //HANDLE PX5 NOTIFICATION
                    PX5NotificationManager px5NotificationManager = PX5NotificationManager.getInstance();
                    px5NotificationManager.processNotification(extras);
                } else {
                    //HANDLE NON PX5 MESSAGES HERE
                }


            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }


}

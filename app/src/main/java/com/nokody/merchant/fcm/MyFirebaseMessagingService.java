package com.nokody.merchant.fcm;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Check if message contains a data payload.
        if (remoteMessage.getData() != null || remoteMessage.getNotification() != null) {
            Log.d("FirebaseMessaging", "Message data payload: " + remoteMessage.getData());
            Log.d("FirebaseMessaging", "Message notification payload: " + remoteMessage.getNotification().getBody());

            NotificationsUtils.showNotification(getApplicationContext(),
                    remoteMessage.getNotification().getTitle(),
                    remoteMessage.getNotification().getBody());
        }
    }
}

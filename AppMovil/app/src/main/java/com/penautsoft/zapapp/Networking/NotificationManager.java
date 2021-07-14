package com.penautsoft.zapapp.Networking;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.penautsoft.zapapp.DashBoardActivity;
import com.penautsoft.zapapp.MainActivity;
import com.penautsoft.zapapp.R;

public class NotificationManager extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
//        sendRegistrationToServer(token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages
        // are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data
        // messages are the type
        // traditionally used with GCM. Notification messages are only received here in
        // onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated
        // notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages
        // containing both notification
        // and data payloads are treated as notification messages. The Firebase console always
        // sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
                //scheduleJob();
            } else {
                // Handle message within 10 seconds
                //handleNow();
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

        String strTitle=remoteMessage.getNotification().getTitle();
        String message=remoteMessage.getNotification().getBody();
        Log.d(TAG,"onMessageReceived: Message Received: \n" +
        "Title: " + strTitle + "\n" +
                "Message: "+ message);

        sendNotification(strTitle,message);
    }

    private void mostrarNotificacion(String title, String body) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_accessibility_24)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);

        /*NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());*/

    }

    private  void sendNotification(String title,String messageBody) {
        Intent[] intents= new Intent[1];
        Intent intent= new      Intent(this, DashBoardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("IncidentNo", title);
        intent.putExtra("ShortDesc", messageBody);
        intent.putExtra("Description",messageBody);
        intents[0]=intent;
        PendingIntent pendingIntent=PendingIntent.getActivities(this,0,
                intents,PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri
                (RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationbuilder=
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_accessibility_24)
                        .setContentTitle("Service Now")
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent)
                        .setLargeIcon(BitmapFactory.decodeResource
                                (getResources(), R.mipmap.ic_launcher));;

       /* @SuppressLint("ServiceCast") NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //notificationManager.notify(0, notificationbuilder.build());
        notificationManager.notify();*/
    }


}

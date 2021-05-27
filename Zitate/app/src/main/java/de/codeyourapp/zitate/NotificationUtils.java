package de.codeyourapp.zitate;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

import static android.content.ContentValues.TAG;

public class NotificationUtils extends ContextWrapper {

    private static NotificationManager mManager;
    public static final String CHANNEL_ID = "123456";
    public static final String CHANNEL_NAME = "News";

    public NotificationUtils(Context base) {
        super(base);
        createChannels();
    }

    // creates a notification channel
    public void createChannels() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);

            getManager().createNotificationChannel(channel);
        }

    }

    // creates a manager that handles notifications
    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    // create a notification
    public NotificationCompat.Builder createChannelNotification(String messageTitle, String messageBody){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        // Send notification to Channel with Id "123456"
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_stat_ic_notification)
                        .setContentTitle(messageTitle)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        return notificationBuilder;
    }

    public void sendMessage(String message){
        Random random = new Random();
        FirebaseMessaging fm = FirebaseMessaging.getInstance();
        String projectId = "325069809843";
        Log.d(TAG, "Try to send a Message at server: " + projectId);
        fm.send(new RemoteMessage.Builder(projectId + "@gcm.googleapis.com")
                .setMessageId(""+random.nextInt())
                .addData("MessageFromClient",message)
                .addData("action","ECHO")
                .build());
    }

}

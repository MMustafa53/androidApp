package com.yakyakyak;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Timer;
import java.util.TimerTask;

import static com.yakyakyak.MyService.period;

public class NotificationManage extends Application {
    private NotificationManagerCompat notificationManager;
    Timer timer;
    public static final String CHANNEL_1_ID = "channel1";


    @Override
    public void onCreate() {
        super.onCreate();

        if(MyService.alarm){
            createNotificationChannels();
            notificationManager = NotificationManagerCompat.from(this);

            timer = new Timer();
            timer.schedule(new TimerTask() {  //check and sendNotification();
                @Override
                public void run() {
                    sendNotification();
                }

            }, 0,period);
        }

    }

    private void sendNotification() {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this,CHANNEL_1_ID);
        notification.setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Title")
                .setContentText("Text")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE);
        notification.setDefaults(Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE|Notification.FLAG_AUTO_CANCEL);
        notificationManager.notify(1,notification.build());

    }

    private  void  createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(CHANNEL_1_ID, "Channel 1", NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("This is channel 1");

            NotificationManager manager = getSystemService(NotificationManager.class);
            assert manager != null;
            manager.createNotificationChannel(channel1);
        }
    }
}

package com.yakyakyak;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import static com.yakyakyak.NotificationManage.CHANNEL_1_ID;
import static com.yakyakyak.NotificationManage.kontrol;



public class MyService extends Service {


    private NotificationManagerCompat notificationManager;
    private String baslik="Bence biraz mola vermelisin ...";
    private String yazi="Sende yorulmadın mı. Bi araya nedersin";
    public static boolean alarm =false;
    String bugun ="",zamanBirimi,saat,gunler,hat="";
    String[] listItemD,gunlerD,hatlar;
    long[] periods = new long[50];
    DataBase dataBase;
    public static long period=1000;
    List<String> list;
    int gun, sure, k=0;
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Toast.makeText(this,"Service Oluştu",Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//
//        String tim = DataBase.asd;
//        String[] timeD = tim.split("-");
//        String time = timeD[timeD.length-1];
//        String zamanBirimi = timeD[1];
//        int periot = Integer.parseInt(timeD[0].substring(0,2));
        Toast.makeText(this, "Service Başladı", Toast.LENGTH_LONG).show();

        period = periods[0];

//        notificationManage.not(period);


        return super.onStartCommand(intent, flags, startId);

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"Service Kapatıldı",Toast.LENGTH_SHORT).show();
    }
}

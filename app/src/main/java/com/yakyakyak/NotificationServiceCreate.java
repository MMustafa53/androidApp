package com.yakyakyak;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;



public class NotificationServiceCreate extends Service  {

    public static final String CHANNEL_1_ID = "channel1";
    private NotificationManagerCompat notificationManager;
    private String baslik="Bence biraz mola vermelisin ...";
    private String yazi="Sende yorulmadın mı. Bi araya nedersin";
    public static boolean alarm =false;
    String bugun ="",zamanBirimi,saat,gunler,hat="",timeStringCtl;
    String[] listItemD,gunlerD,hatlar;
    long[] periods = new long[50];
    DataBase dataBase;
    public static long period=1000;
    List<String> list;
    int gun, sure, k=0;
    public boolean kontrol = false;
    Timer timer, timerCtl;
    Date d1,d2;

    public NotificationServiceCreate() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Calendar cl = Calendar.getInstance();
        gun = cl.get(Calendar.DAY_OF_WEEK);
        switch (gun){
            case Calendar.SUNDAY:
                bugun = getString(R.string.pazar);
                break;
            case Calendar.MONDAY:
                bugun = getString(R.string.pzt);
                break;
            case Calendar.TUESDAY:
                bugun = getString(R.string.sali);
                break;
            case Calendar.WEDNESDAY:
                bugun = getString(R.string.crs);
                break;
            case Calendar.THURSDAY:
                bugun = getString(R.string.prs);
                break;
            case Calendar.FRIDAY:
                bugun = getString(R.string.cuma);
                break;
            case Calendar.SATURDAY:
                bugun = getString(R.string.cts);
                break;
        }
        dataBase = new DataBase(NotificationServiceCreate.this);
        list = dataBase.VeriListele();
        if(list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                listItemD = list.get(i).split(" - ");
                gunler = listItemD[1];
                gunlerD = gunler.split(", ");
                sure = Integer.parseInt(listItemD[2]);
                zamanBirimi = listItemD[3];
                saat = listItemD[4];
                for (int j = 0; j < gunlerD.length; j++) {
                    while (k == i) {
                        if (bugun.equals(gunlerD[j])) {
                            hat += listItemD[0] + ",";
                            k++;
                        }
                    }
                }
                switch (zamanBirimi) {
                    case "Saniye":
                        period = 1000;
                        period *= sure;
                        break;
                    case "Dakika":
                        period = 1000;
                        period *= 60 * sure;
                        break;
                    case "Saat":
                        period = 1000;
                        period *= 60 * 60 * sure;
                        break;
                    case "Gün":
                        period = 1000;
                        period *= 60 * 60 * 24 * sure;
                        break;
                    case "Ay":
                        period = 1000;
                        period *= 60 * 60 * 24 * 30 * sure;
                        break;
                    case "Yıl":
                        period = 1000;
                        period *= 60 * 60 * 24 * 365 * sure;
                        break;
                }
            }
            if (!hat.equals("")) {
                hat = hat.substring(0, hat.length() - 1);
                hatlar = hat.split(",");
            }
        }

        if (period != 0) {
            createNotificationChannels();
            notificationManager = NotificationManagerCompat.from(this);

            timer = new Timer();
            timer.schedule(new TimerTask() {  //check and sendNotification();
                @Override
                public void run() {
                    sendNotification();
                    kontrol = true;
                }

            }, 0, period);
        }

        Toast.makeText(this,"Service Oluştu",Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId){
        Toast.makeText(this, "Service Başladı", Toast.LENGTH_LONG).show();



        timerCtl.schedule(new TimerTask() {
            @Override
            public void run() {
                timeStringCtl = SimpleDateFormat.getTimeInstance().format(new Date());
                SimpleDateFormat sDF = new SimpleDateFormat("HH:mm:ss");
                try {
                    d1 = sDF.parse(timeStringCtl);
                    d2 = sDF.parse(saat);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long fark = d1.getTime()-d2.getTime();
            }
        },0,1000);
        return super.onStartCommand(intent, flags, startId);

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"Service Kapatıldı",Toast.LENGTH_SHORT).show();
    }

    public void sendNotification() {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this,CHANNEL_1_ID);
        notification.setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(baslik)
                .setContentText(yazi)
                .setColor(getColor(R.color.colorPrimary))
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),R.drawable.ic_smoking))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE);
        notification.setDefaults(Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE|Notification.FLAG_AUTO_CANCEL);
        notificationManager.notify(1,notification.build());

    }

    public  void  createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(CHANNEL_1_ID, "Channel 1", NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("This is channel 1");

            NotificationManager manager = getSystemService(NotificationManager.class);
            assert manager != null;
            manager.createNotificationChannel(channel1);
        }
    }

}

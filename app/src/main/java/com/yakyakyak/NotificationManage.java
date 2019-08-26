package com.yakyakyak;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;



public class NotificationManage extends Application {
    private NotificationManagerCompat notificationManager;
    private String baslik="Bence biraz mola vermelisin ...";
    private String yazi="Sende yorulmadın mı. Bi araya nedersin";
    public static boolean kontrol = false;
    String bugun ="",zamanBirimi,saat,gunler,hat="";
    String[] listItemD,gunlerD,hatlar;
    DataBase dataBase;
    List<String> list;
    int k=0,gun;
    long[] periods;
    Timer timer;
    int sure;
    public static final String CHANNEL_1_ID = "channel1";
    public long period;

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
        dataBase = new DataBase(NotificationManage.this);
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

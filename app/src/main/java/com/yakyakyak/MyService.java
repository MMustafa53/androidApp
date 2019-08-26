package com.yakyakyak;

import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.IBinder;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyService extends Service {
    public static long period=1000;
    public static boolean alarm =false;
    String bugun ="",zamanBirimi,saat,gunler,hat="";
    String[] listItemD,gunlerD,hatlar;
    long[] periods = new long[50];
    DataBase dataBase;

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

        dataBase = new DataBase(MyService.this);
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
                periods[i] = period;
            }
            if (!hat.equals("")) {
                hat = hat.substring(0, hat.length() - 1);
                hatlar = hat.split(",");
            }
        }
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
        Toast.makeText(this,"Service Başladı" +periods[0],Toast.LENGTH_LONG).show();

        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"Service Kapatıldı",Toast.LENGTH_SHORT).show();
    }
}

package com.yakyakyak;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class AddReminder extends AppCompatActivity {

    //Widgets defination
    Date currentTime;
    TextView currentTimeTv;
    Button kaydet;
    CheckBox hergun,pzt,sali,crs,prs,cuma,cts,pazar;
    EditText hisim, zaman;
    Spinner zamanBirimi;
    public static String zB;

    //Variable defination
    int saniye=0,dakika=0;
    String[] gunler;
    String isim="Hatırlat", timeString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        currentTimeTv = findViewById(R.id.currentTime);
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                timeString = SimpleDateFormat.getTimeInstance().format(new Date());
                                currentTimeTv.setText(timeString);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();
        kaydet =findViewById(R.id.button);
        hergun = findViewById(R.id.cb_hergun);
        pzt = findViewById(R.id.cb_pzt);
        sali = findViewById(R.id.cb_sali);
        crs = findViewById(R.id.cb_crs);
        prs = findViewById(R.id.cb_prs);
        cuma = findViewById(R.id.cb_cuma);
        cts = findViewById(R.id.cb_cts);
        pazar = findViewById(R.id.cb_pazar);
        hisim = findViewById(R.id.editText);
        zaman = findViewById(R.id.editTextN);
        zamanBirimi = findViewById(R.id.spinner);
        gunler = new String[]{"0"};
    }

    public void HcheckBoxCtl(View view){
        if(hergun.isChecked()){
            pzt.setChecked(true);
            sali.setChecked(true);
            crs.setChecked(true);
            prs.setChecked(true);
            cuma.setChecked(true);
            cts.setChecked(true);
            pazar.setChecked(true);

            gunler = new String[]{getString(R.string.pzt),", ",getString(R.string.sali),", ",getString(R.string.crs),", ",getString(R.string.prs)
                    ,", ",getString(R.string.cuma),", ",getString(R.string.cts),", ",getString(R.string.pazar)};
        }
        else{
            pzt.setChecked(false);
            sali.setChecked(false);
            crs.setChecked(false);
            prs.setChecked(false);
            cuma.setChecked(false);
            cts.setChecked(false);
            pazar.setChecked(false);

            gunler = new String[]{"0"};
        }
    }

    public void checkBoxCtl(View view){
        if(!hergun.isChecked()){
            if(pzt.isChecked()&&sali.isChecked()&&crs.isChecked()&&prs.isChecked()&&cuma.isChecked()&&cts.isChecked()&&pazar.isChecked()){
                hergun.setChecked(true);
                gunler = new String[]{getString(R.string.pzt),", ",getString(R.string.sali),", ",getString(R.string.crs),", ",getString(R.string.prs)
                        ,", ",getString(R.string.cuma),", ",getString(R.string.cts),", ",getString(R.string.pazar)};
            }
            else{
                gunler = new String[]{"0"};
            }
        }
        else{
            if(!(pzt.isChecked()&&sali.isChecked()&&crs.isChecked()&&prs.isChecked()&&cuma.isChecked()&&cts.isChecked()&&pazar.isChecked())){
                hergun.setChecked(false);
                gunler = new String[]{"0"};
            }
        }
    }

    public void addNewDuty(View view){

        String gun = "";
        if(gunler[0].equals("0")){
            if(pzt.isChecked()){
                gun += "Pazartesi, ";
            }
            if(sali.isChecked()){
                gun += "Salı, ";
            }
            if(crs.isChecked()){
                gun += "Çarşamba, ";
            }
            if(prs.isChecked()){
                gun += "Perşmebe, ";
            }
            if(cuma.isChecked()){
                gun += "Cuma, ";
            }
            if(cts.isChecked()){
                gun += "Cumartesi, ";
            }
            if(pazar.isChecked()){
                gun += "Pazar";
            }

            if(!gun.equals(""))
                gunler = gun.split(",");
            else
                gunler = new String[]{"0"};
        }
        else{
            for(int i=0;i<gunler.length;i++)
                gun+=gunler[i];
        }
        currentTime = Calendar.getInstance().getTime();
        zB = zamanBirimi.getSelectedItem().toString();
        zB += " - " +currentTimeTv.getText();


        if(!(gun.equals(""))&&!(gunler[0].equals("0"))&&!(hisim.getText().toString().equals(""))&&!(zaman.getText().toString().equals(""))){
            //Insert database
            DataBase db = new DataBase(AddReminder.this);
            db.VeriEkle(hisim.getText().toString(),gun,zB,Integer.parseInt(zaman.getText().toString()));
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
        else
            Toast.makeText(this,"Lütfen boşlukları doldurun",Toast.LENGTH_SHORT).show();

//        Intent returnIntent = new Intent();
//        setResult(Activity.RESULT_OK, returnIntent);
//        finish();
    }
}

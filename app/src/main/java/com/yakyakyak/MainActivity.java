package com.yakyakyak;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.renderscript.RenderScript;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Locale;
import java.util.ServiceConfigurationError;


import static com.yakyakyak.NotificationManage.CHANNEL_1_ID;

public class MainActivity extends AppCompatActivity {

    private final int request_code = 1;
    ListView listView;
    FloatingActionButton fab,del;
    String ad = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.floatingActionButton);
        del = findViewById(R.id.floatingActionButton2);
        listView = findViewById(R.id.listView);
        listele();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Tıklanan verimizi alıyoruz
                String item = listView.getItemAtPosition(position).toString();
                // - Göre bölüyoruz
                String[] itemBol = item.split(" - ");
                // id'mizi alıyoruz
                ad = itemBol[0];
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        listele();
        if(resultCode == RESULT_OK && requestCode==request_code){
            Toast.makeText(this,"Hatırlatıcı oluşturuldu",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this,"Hatırlatıcı iptal edildi",Toast.LENGTH_SHORT).show();
        }
    }

    public void listele(){
        DataBase dataBase = new DataBase(MainActivity.this);
        List<String> list = dataBase.VeriListele();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,android.R.id.text1,list);
        listView.setAdapter(adapter);
    }
    public void fabClick(View view){
        Intent addReminder = new Intent(this,AddReminder.class);
        startActivityForResult(addReminder,request_code);
    }

    public void fabClickDell(View view){

        if(!ad.equals("")){
            Toast.makeText(this,ad+ " hatırlatıcısı silindi",Toast.LENGTH_SHORT).show();
            DataBase dataBase = new DataBase(MainActivity.this);
            dataBase.VeriSil(ad);
            listele();
        }
        else{
            Toast.makeText(this,"Lütfen silmek istediğiniz hatırlatıcıya tıklayın",Toast.LENGTH_SHORT).show();
        }
    }

    //        BILDIRIM KODU
    //        NotificationCompat.Builder notification = new NotificationCompat.Builder(this,CHANNEL_1_ID);
    //        notification.setSmallIcon(R.drawable.ic_launcher_foreground)
    //                .setContentTitle("Title")
    //                .setContentText("Text")
    //                .setPriority(NotificationCompat.PRIORITY_HIGH)
    //                .setCategory(NotificationCompat.CATEGORY_MESSAGE);
    //        notificationManager.notify(1,notification.build());
    //        BILDIRIM KODU

}

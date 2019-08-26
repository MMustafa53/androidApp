package com.yakyakyak;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String zaman = "Zaman";
    private static final String DATABASE_NAME = "HATIRLAT";
    private static final String ROW_ID = "ID";
    private static final String TABLO_A = "HATIRLATICI";
    private static final String isim = "İSİM";
    private static final String gun = "GÜN";
    private static final String zamanBirimi = "ZAMAN_BİRİMİ";

    public static SQLiteDatabase database;
    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLO_A + "("
                + isim + " TEXT NOT NULL, "
                + gun + " TEXT NOT NULL, "
                + zaman + " INTEGER NOT NULL, "
                + zamanBirimi + " TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public void VeriEkle(String iSim, String guns, String zb, int z) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(isim, iSim);
            cv.put(gun, guns);
            cv.put(zaman, z);
            cv.put(zamanBirimi, zb);
            db.insert(TABLO_A, null, cv);
        } catch (Exception e) {
        }
        db.close();
    }


    public List<String> VeriListele() {
        List<String> veriler = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String[] stunlar = {isim, gun, zaman, zamanBirimi};
            Cursor cursor = db.query(TABLO_A, stunlar, null, null, null, null, null);
            while (cursor.moveToNext()) {
                veriler.add(cursor.getString(0)
                        + " - "
                        + cursor.getString(1)
                        + " - "
                        + cursor.getString(2)
                        + " - "
                        + cursor.getString(3));
            }
        } catch (Exception e) {
        }
        db.close();
        return veriler;
    }

    public void VeriSil(String iSim) {
        String a = "'"+iSim+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            // id ye göre verimizi siliyoruz
            String query = isim +" = " +a;
            db.delete(TABLO_A,query,null);
        } catch (Exception e) {
        }
        db.close();

    }
}
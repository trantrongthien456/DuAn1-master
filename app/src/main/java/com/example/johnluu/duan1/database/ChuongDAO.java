package com.example.johnluu.duan1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.johnluu.duan1.SQLite.DBHelper;
import com.example.johnluu.duan1.model.Chuong;

import java.util.ArrayList;

public class ChuongDAO {

    SQLiteDatabase db;
    Context c;
    DBHelper dbh;

    public ChuongDAO(Context c) {
        this.c=c;
        dbh=new DBHelper(c);
    }

    public void ThemChuong(Chuong ch){
        db = dbh.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("_idchuong",ch._idchuong);
        values.put("_idsach",ch._idsach);
        values.put("tenchuong",ch.tenchuong);
        values.put("duongdan",ch.duongdan);
        values.put("audio",ch.audio);
        db.insert("chuong",null,values);


    }
    public ArrayList<Chuong> xemDSChuong(){
        ArrayList<Chuong> dsschuong = new ArrayList<Chuong>();
        dbh = new DBHelper(c);
        db = dbh.getReadableDatabase();
        Cursor c = db.rawQuery("select * from chuong",null);
        if(c.moveToFirst()){
            do{
                int _idchuong = c.getInt(0);
                int _idsach= c.getInt(1);
                String tenchuong =c.getString(2);
                String duongdan = c.getString(3);
                String audio = c.getString(4);
                Chuong chuong = new Chuong(_idchuong,_idsach,tenchuong,duongdan,audio);
                dsschuong.add(chuong);

            }while (c.moveToNext());
        }
        return dsschuong;

    }
    public void XoaChuong (int _id){
        db=dbh.getWritableDatabase();
        db.delete("chuong","_idchuong=?",new String[]{_id+""});

    }
    public void suaChuonh(Chuong ch){
        db = dbh.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("_idchuong",ch._idchuong);
        values.put("_idsach",ch._idsach);
        values.put("tenchuong",ch.tenchuong);
        values.put("duongdan",ch.duongdan);
        values.put("audio",ch.audio);
        db.update("chuong",values,"_idchuong=?",new String[]{ch._idchuong+""});


    }


}



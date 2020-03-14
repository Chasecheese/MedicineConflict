package com.example.medicineconflict;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DBOpenHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME="medicineItem";
    public static final String ID = "ID";
    public static final String NAME="Name";
    public static final String CONFLICT="Conflict_Name";


    private SQLiteDatabase db;

    public DBOpenHelper(Context context, String name,
                        CursorFactory factory, int version) {
        super(context, name, factory, version);
        db = this.getWritableDatabase();
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("drop table if exists "+"TABLE_NAME");
        db.execSQL("create table if not exists "+TABLE_NAME+" ("+
                ID+" integer primary key autoincrement,"+NAME+" varchar,"+ CONFLICT+" varchar)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void delete(int id){
        db.delete(TABLE_NAME, "ID=" + id, null);
    }

    public int deleteAll()
    {
        return db.delete(TABLE_NAME, null, null);
    }

    public void close(){
        db.close();
    }

    public List<MedicineItem> getBasicInfo(){
        Cursor c=db.query(TABLE_NAME,new String[]{},null,null,null,null,ID);
        List<MedicineItem> list=new ArrayList<MedicineItem>();
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            String name=c.getString(c.getColumnIndex(NAME));
            String conflict=c.getString(c.getColumnIndex(CONFLICT));
            MedicineItem d=new MedicineItem(name,conflict);
            d.setID(c.getInt(c.getColumnIndex(ID)));
            list.add(d);
        }
        c.close();
        return list;
    }

}

package com.example.medicineconflict;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;


public class DBOpenHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "medicine_db";

    public static final String TABLE_NAME="newMedicineItem";
    public static final String ID = "ID";
    public static final String NAME="Name";
    public static final String LEVEL4 = "level_4";
    public static final String LEVEL3 = "level_3";
    public static final String LEVEL2 = "level_2";
    public static final String LEVEL1 = "level_1";

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
                ID+" integer primary key autoincrement,"+NAME+" varchar,"+
                LEVEL4+" varchar,"+LEVEL3+" varchar,"+LEVEL2+" varchar,"+
                LEVEL1+" varchar)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void close(){
        db.close();
    }


    public ArrayList<MedicineInfo> getListInfo(){
        Cursor c=db.query(TABLE_NAME,new String[]{},null,null,null,null,ID);
        ArrayList<MedicineInfo> list=new ArrayList<>();
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            String name=c.getString(c.getColumnIndex(NAME));
            String level4 = c.getString(c.getColumnIndex(LEVEL4));
            String level3 = c.getString(c.getColumnIndex(LEVEL3));
            String level2 = c.getString(c.getColumnIndex(LEVEL2));
            String level1 = c.getString(c.getColumnIndex(LEVEL1));
            MedicineInfo d=new MedicineInfo(name,level4,level3,level2,level1);
            d.setID(c.getInt(c.getColumnIndex(ID)));
            list.add(d);
        }
        c.close();
        return list;
    }

    public MedicineInfo getMedicineInfo(int id) {
        Cursor c = db.query(TABLE_NAME, new String[]{}, "ID=" + id, null, null, null, null);
        if (c.moveToFirst()) {
            String name = c.getString(c.getColumnIndex(NAME));

            String level4 = c.getString(c.getColumnIndex(LEVEL4));
            String level3 = c.getString(c.getColumnIndex(LEVEL3));
            String level2 = c.getString(c.getColumnIndex(LEVEL2));
            String level1 = c.getString(c.getColumnIndex(LEVEL1));
            MedicineInfo d = new MedicineInfo(name, level4, level3, level2, level1);
            d.setID(c.getInt(c.getColumnIndex("ID")));
            c.close();
            return d;
        } else {
            c.close();
            return null;
        }
    }

}

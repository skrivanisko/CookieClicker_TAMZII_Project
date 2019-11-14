package com.example.cookieclicker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DB extends SQLiteOpenHelper {

    private static String TABLE_NAME = "helpers";
    private static String DB_NAME = "database.db";

    private static String ID_COLUMN = "id";
    private static String NAME_COLUMN = "name";
    private static String COST_COLUMN = "cost";
    private static String PRODUCTION_COLUMN = "production";
    private static String COUNT_COLUMN = "count";

    public DB(Context context){
        super(context,DB_NAME,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table helpers " +
                        "(id integer primary key, name text, cost int, production int, count int)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insert(int id, String name, int cost, int production, int count) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_COLUMN, id);
        contentValues.put(NAME_COLUMN, name);
        contentValues.put(COST_COLUMN, cost);
        contentValues.put(PRODUCTION_COLUMN, production);
        contentValues.put(COUNT_COLUMN, count);
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public ArrayList<Helper> select() {
        ArrayList<Helper> array_list = new ArrayList<Helper>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){

            Helper h = new Helper();
            h.setId(res.getInt(res.getColumnIndex(ID_COLUMN)));
            h.setName(res.getString(res.getColumnIndex(NAME_COLUMN)));
            h.setCost(res.getInt(res.getColumnIndex(COST_COLUMN)));
            h.setProduction(res.getInt(res.getColumnIndex(PRODUCTION_COLUMN)));
            h.setCount(res.getInt(res.getColumnIndex(COUNT_COLUMN)));

            array_list.add(h);
            res.moveToNext();
        }
        return array_list;
    }

    public int getProduction(){
        float productionSum = 0;
        for(Helper h : this.select())
            productionSum+= h.getProduction()*h.getCount();
        return (int)productionSum;
    }

    public Helper select(String name) {
        for (Helper h : select()){
            if(h.getName().equals(name))
                return h;
        } return null;
    }

    public boolean update(Helper helper){
        ContentValues cv = new ContentValues();
        cv.put(ID_COLUMN,helper.getId());
        cv.put(NAME_COLUMN,helper.getName());
        cv.put(COST_COLUMN,helper.getCost());
        cv.put(PRODUCTION_COLUMN,helper.getProduction());
        cv.put(COUNT_COLUMN,helper.getCount());

        SQLiteDatabase db = this.getReadableDatabase();
        db.update(TABLE_NAME, cv, ID_COLUMN+"="+helper.getId(), null);
        return true;
    }

}

package com.first.Anki_blank;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class SQLiteControl {
    SQLiteHelper helper;
    SQLiteDatabase sqlite;
    Context context;

    public SQLiteControl(Context context){
        this.context=context;
        this.helper=new SQLiteHelper(context,"Control.db",null,1);
    }

    public void insert(String pdfname, int _pagenum, float ltcx, float ltcy, float rbcx, float rbcy,int interval, int ef){
        sqlite=helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        if (ltcx>rbcx){
            values.put(helper.ltCornerx,rbcx);
            values.put(helper.rbCornerx,ltcx);
        }else{
            values.put(helper.ltCornerx,ltcx);
            values.put(helper.rbCornerx,rbcx);
        }
        if (ltcy>rbcy){
            values.put(helper.ltCornery,rbcy);
            values.put(helper.rbCornery,ltcy);
        }else{
            values.put(helper.ltCornery,ltcy);
            values.put(helper.rbCornery,rbcy);
        }
        values.put(helper.interval,interval);
        values.put(helper.pdfname,pdfname);
        values.put(helper.pagenum,_pagenum);
        values.put(helper.isgraduated,1);
        values.put(helper.ef,ef);
        values.put(helper.date,"0000-01-01 00:00");

        sqlite.insert(helper.TABLE_NAME,null,values);
    }


    public void listInsert(String pdfpath){
        sqlite=helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(helper.filelist,pdfpath);
        sqlite.insert(helper.List_Table,null,values);
        dbclose();
    }

    public void reinsert(SQLiteDatabase sqlite,SQLiteHelper helper,int id,int ef, int isgraduated, String date,int interval){
        sqlite=helper.getWritableDatabase();
        String ud="update "+helper.TABLE_NAME+" set "+helper.ef+" = '"+ ef+"', "+helper.isgraduated+" = '"+isgraduated+"', "+helper.date+" = '"+ date +"', "+helper.interval+" = '"+ interval + "' where "+helper.id+ " = '"+id+"';";
        sqlite.execSQL(ud);
    }

    public String fast_pick(){
        String date = null/*"9999-12-31 23:59"*/;
        SharedPreferences sharedPreferences= context.getSharedPreferences("General",Context.MODE_PRIVATE);
        boolean sub_check=sharedPreferences.getBoolean("Subscribing2",false);
        String pickup;
        try {
            sqlite=helper.getReadableDatabase();
            if (sub_check){
                pickup="select MIN ("+helper.date+") "+" from ("+helper.TABLE_NAME+")";
            }else{
                pickup="select MIN ("+helper.date+") "+" from ("+helper.TABLE_NAME+") where "+helper.pdfname+" like 'a%'";
            }

            Cursor c= sqlite.rawQuery(pickup,null);
            while (c.moveToNext()){
                date=c.getString(0);
            }
        }catch (SQLiteException e){
            date=null/*"9999-12-31 23:59"*/;
        }

        dbclose();
        return date;
    }

    public void delete(String id){

        sqlite=helper.getWritableDatabase();
        sqlite.delete(helper.TABLE_NAME,"_id=?",new String[] {id});
    }

    
    public void dbclose(){
        sqlite.close();
        helper.close();
    }

}

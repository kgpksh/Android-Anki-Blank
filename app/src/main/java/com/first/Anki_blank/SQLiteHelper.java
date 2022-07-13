package com.first.Anki_blank;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {
    public String TABLE_NAME="controldata";
    public String  id="_id";
    public String pagenum="pagenum";
    public String ltCornerx="ltCornerx";
    public String ltCornery="ltCornery";
    public String rbCornerx="rbCornerx";
    public String rbCornery="rbCornery";
    public String ef="ef";
    public String interval="interval";
    public String date="date";
    public String pdfname="pdfname";
    public String isgraduated="isgraduated";
    public String nameindex="nameindex";
    public String filelist="filelist";
    public String List_Table="list";
    public String Test="test";
    public SQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE if not exists controldata ("
                + id +" integer primary key autoincrement,"
                + pdfname +" text not null,"
                + pagenum +" integer not null,"
                + isgraduated +" integer not null,"
                + ltCornerx +" real not null,"
                + ltCornery +" real not null,"
                + rbCornerx +" real not null,"
                + rbCornery +" real not null,"
                + interval +" integer not null,"
                + ef +" integer not null,"
                + date +" text not null);";

        String list="CREATE TABLE if not exists list ("
                + id +" integer primary key autoincrement,"
                + filelist +" text not null);";

        String index="CREATE index "+nameindex+" on "+TABLE_NAME+" ("+pdfname+","+pagenum+","+date+" )";

        db.execSQL(sql);
        db.execSQL(list);
        db.execSQL(index);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*if (oldVersion < 2) {
            updateAgeColumn(db);
        }*/
    }

    /*private void updateAgeColumn(SQLiteDatabase a_db) {
        a_db.execSQL("ALTER TABLE " + TABLE_NAME + " " + "ADD COLUMN " + Test + " " + "TEXT DEFAULT " + " 'abc' ");
    }*/

}

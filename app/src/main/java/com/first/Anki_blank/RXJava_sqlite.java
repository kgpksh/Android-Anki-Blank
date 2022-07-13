package com.first.Anki_blank;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;

import com.first.Anki_blank.Alarm.Alarm_Reciver;
import com.first.Anki_blank.Alarm.NotificationHelper;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.reactivex.rxjava3.disposables.Disposable;

public class RXJava_sqlite extends AppCompatActivity {

    Disposable backgroundTask;
    SQLiteControl sqlite;
    AlarmManager alarmManager;
    Intent alarmintent;
    Context mcontext;
    File basic_folder, premium_folder;
    SQLiteHelper helper;

    public void start(SQLiteControl sqLiteControl,AlarmManager alarmManager,Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                alarm_setter(sqLiteControl,alarmManager,context);
                noti_package();
            }
        }).start();
    }



    private void alarm_setter(SQLiteControl sqlite, AlarmManager alarmManager, Context context){
        this.mcontext=context;
        this.sqlite=sqlite;
        this.alarmManager=alarmManager;
        alarmintent = new Intent(mcontext, Alarm_Reciver.class);
    }


    private void startAlarm(){
        Calendar check=string_to_calendar();
        if (check!=null){
            PendingIntent pendingIntent = PendingIntent.getBroadcast(mcontext, 1, alarmintent, 0);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, check.getTimeInMillis(), pendingIntent);
            //alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 1*60*1000 ,  pendingIntent);
        }
    }


    public void cancelAlarm(){
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mcontext, 1, alarmintent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (pendingIntent!=null){
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }
    }

    public void secondary_cancelAlarm(){
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mcontext, 1, alarmintent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (pendingIntent!=null){
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }
    }

    private Calendar string_to_calendar(){
        sqlite=new SQLiteControl(mcontext);
        String str = sqlite.fast_pick();
        Calendar cd=Calendar.getInstance();
        if (str!=null){
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault());
            try {
                Date date = sdf.parse(str);
                cd.setTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else{
            cd=null;
        }
        return cd;
    }

    private void noti_package(){
        SharedPreferences general=mcontext.getSharedPreferences("General",MODE_PRIVATE);
        if (general.getBoolean("notification",true)==true){
            NotificationHelper notificationHelper=new NotificationHelper(mcontext);
            notificationHelper.cancelNotification();
            cancelAlarm();
            startAlarm();
        }
    }


    private String getreplace(String filepath){
        String replacedpath = new String();
        File basic_folder=new File(this.getFilesDir()+"/"+"Basic folder");
        File premium_folder=new File(this.getExternalFilesDir(null).getParent()+"/"+"Premium folder");

        if (filepath.contains(basic_folder.getAbsolutePath())){
            replacedpath=filepath.replaceFirst(basic_folder.getAbsolutePath(),"a");
        }else if(filepath.contains(premium_folder.getAbsolutePath())){
            replacedpath=filepath.replaceFirst(premium_folder.getAbsolutePath(),"b");
        }
        return replacedpath;
    }

    private void DB_clear_core(){
        ArrayList<String> listed_file=new ArrayList<>();
        ArrayList<String> existing_file=new ArrayList<>();

        When_delete when_delete=new When_delete();
        SQLiteDatabase sqLiteDatabase=helper.getWritableDatabase();
        String get_list="select "+helper.filelist+" from "+ helper.List_Table;
        Cursor cursor=sqLiteDatabase.rawQuery(get_list,null);

        sqLiteDatabase.beginTransaction();
        try {
            while(cursor.moveToNext()){
                listed_file.add(cursor.getString(0));
            }
            cursor.close();

            when_delete.is_exist(basic_folder,existing_file);
            when_delete.is_exist(premium_folder,existing_file);
            listed_file.removeAll(existing_file);

            for (String path : listed_file){
                String delete_list="delete from "+ helper.List_Table+" where "+helper.filelist+" = '"+path+"'";
                String delete_data="delete from "+ helper.TABLE_NAME+" where "+helper.pdfname+" = '"+getreplace(path)+"'";
                sqLiteDatabase.execSQL(delete_list);
                sqLiteDatabase.execSQL(delete_data);
            }
            sqLiteDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqLiteDatabase.endTransaction();
        }
        sqLiteDatabase.close();
        helper.close();
    }

}

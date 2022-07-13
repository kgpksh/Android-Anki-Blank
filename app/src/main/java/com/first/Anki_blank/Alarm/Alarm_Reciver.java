package com.first.Anki_blank.Alarm;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.first.Anki_blank.R;
import com.first.Anki_blank.RXJava_sqlite;
import com.first.Anki_blank.SQLiteControl;
import com.first.Anki_blank.SQLiteHelper;
import com.first.Anki_blank.Subscribe;

public class Alarm_Reciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Subscribe subscribe_check=new Subscribe(context);
        subscribe_check.only_subs_check();
        SharedPreferences sharedPreferences= context.getSharedPreferences("General",Context.MODE_PRIVATE);
        SharedPreferences.Editor spf_edt=sharedPreferences.edit();
        if (sharedPreferences.getBoolean("notification",true)){
            NotificationHelper mNotificationHelper=new NotificationHelper(context);
            NotificationCompat.Builder nb = mNotificationHelper.getChannel1Notification(context.getResources().getString(R.string.notification_title),context.getResources().getString(R.string.notification_content));
            boolean isSubscribed=sharedPreferences.getBoolean("Subscribing2",false);

            if (isSubscribed){
                if (mNotificationHelper.isnotification_exist()){

                }else{
                    mNotificationHelper.getManager().notify(1, nb.build());
                }
            }else{
                boolean isfirst_check=sharedPreferences.getBoolean("IsfirstAlarm",true);
                if (isfirst_check){
                    spf_edt.putBoolean("IsfirstAlarm",false);
                    spf_edt.apply();
                    SQLiteControl sqLiteControl=new SQLiteControl(context.getApplicationContext());
                    AlarmManager alarmManager=(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    RXJava_sqlite rxJava_sqlite=new RXJava_sqlite();
                    rxJava_sqlite.start(sqLiteControl,alarmManager,context);
                }else{
                    if (mNotificationHelper.isnotification_exist()){

                    }else{
                        mNotificationHelper.getManager().notify(1, nb.build());
                    }
                    spf_edt.putBoolean("IsfirstAlarm",true);
                    spf_edt.apply();
                }
            }

        }
    }
}

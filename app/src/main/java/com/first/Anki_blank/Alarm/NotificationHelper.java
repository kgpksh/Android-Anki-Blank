package com.first.Anki_blank.Alarm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.service.notification.StatusBarNotification;

import androidx.core.app.NotificationCompat;

import com.first.Anki_blank.R;
import com.first.Anki_blank.StudyPDFFiles;

import java.io.File;

public class NotificationHelper extends ContextWrapper {
    public final String channel1ID = "channel1ID";
    public static final String channel1Name = "channel 1 ";
    public static final String channel2ID = "channel2ID";
    public static final String channel2Name = "channel 2 ";
    public NotificationChannel channel1;
    PendingIntent start_study;

    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannels(false);
            File basic_folder=new File(this.getFilesDir()+"/"+"Basic folder");
            File premium_folder=new File(this.getExternalFilesDir(null).getParent()+"/"+"Premium folder");

            Intent forstudy=new Intent(getApplicationContext(),StudyPDFFiles.class);
            forstudy.putExtra("all_studyS",true);
            forstudy.putExtra("all_study1",basic_folder.getAbsolutePath());
            forstudy.putExtra("all_study2",premium_folder.getAbsolutePath());
            forstudy.putExtra("iscomefromNoti",true);

            start_study= PendingIntent.getActivity(this,0,forstudy,PendingIntent.FLAG_UPDATE_CURRENT);
            //start_study = PendingIntent.getActivity(this,0,forstudy,PendingIntent.FLAG_UPDATE_CURRENT);
        }
    } 


    public void createChannels(boolean vib){
        channel1 = new NotificationChannel(channel1ID,channel1Name, NotificationManager.IMPORTANCE_DEFAULT);
        channel1.enableLights(true);
        channel1.enableVibration(vib);
        channel1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(channel1);
    }

    public NotificationManager getManager() {
        if (mManager == null){
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return mManager;
    }

    public void cancelNotification(){
        getManager().cancel(1);
    }

    public NotificationCompat.Builder getChannel1Notification(String title, String message){
        return new NotificationCompat.Builder(getApplicationContext(), channel1ID)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.mipmap.launcher_icon)
                    .setContentIntent(start_study);
    }

    public NotificationCompat.Builder getChannel2Notification(String title, String message){
        return new NotificationCompat.Builder(getApplicationContext(), channel2ID)
                .setContentTitle(title)
                .setContentText(message);
    }

    public boolean isnotification_exist(){
        boolean istrue=false;
        StatusBarNotification[] notifications = mManager.getActiveNotifications();
        for (StatusBarNotification notification : notifications) {
            if (notification.getId() == 1) {
                istrue=true;
            }
        }
        return istrue;
    }

}

package com.first.Anki_blank;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.PurchaseInfo;
import com.anjlab.android.iab.v3.SkuDetails;

import java.util.List;

public class Subscribe implements BillingProcessor.IBillingHandler{
    final String google_license_key="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2l0BzpNcANrKn27e/XpVZEOe/nTb+LUw7+J57ea1V/kVFjuNbvbxWo0/r7E9xSdKLvDliJINzEZXvqKqQb7Jp1RrTyRW7CWIzKUm95YnXqk2a6arqiQe3CbV8B2BYtAqsT/VtpHisFUzUIhbEhIen578S0lcWDBoBXQvIJTd5szN/eMONWFGRTpxywUJO/b8wKo2MEb0UJzeJb1tK41oqcsleEmnVAbs8PT1p0NG7GMdjFiNDLQPkvFoiq2ubx7Mp8hMrqzF/7M9gK/RRY1golJKLFL3r4pyJKUdTeZrxUBGOIZ3ITrt1aa1aY3/nMEtWXtUhto434I81Nck9oYgmQIDAQAB";
    final String subscriptionID="subscribe1";
    TextView cancel,subscribe;
    PurchaseInfo purchaseInfo=null;
    private BillingProcessor bp;
    Context mcontext;
    SharedPreferences sub_recorder;
    SharedPreferences.Editor recorder;
    AppCompatActivity appCompatActivity;

    Dialog dialog;
    boolean ispurchased;
    boolean sub_check;

    public Subscribe(Context mcontext) {
        this.mcontext = mcontext;
        sub_recorder= mcontext.getSharedPreferences("General",Context.MODE_PRIVATE);
        recorder=sub_recorder.edit();

    }

    public void showDialog(AppCompatActivity activity){
        dialog=new Dialog(mcontext);
        appCompatActivity=activity;
        dialog.setContentView(R.layout.purchage_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();

        cancel=dialog.findViewById(R.id.no_thanks);
        subscribe =dialog.findViewById(R.id.subscribe_now);

        bp=new BillingProcessor(mcontext,google_license_key,this);
        bp.initialize();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bp.isSubscriptionUpdateSupported()){
                    bp.subscribe(activity,subscriptionID);
                }
            }
        });

    }

    public void sharedPre_clear(){
        SharedPreferences cache=mcontext.getSharedPreferences(mcontext.getPackageName() + "_preferences",Context.MODE_PRIVATE);
        cache.edit().clear().apply();
    }


    public void checkSubscription(AppCompatActivity activity, LinearLayout premium){
        //sharedPre_clear();
        appCompatActivity=activity;
        bp=new BillingProcessor(mcontext,google_license_key,this);
        bp.initialize();

    }

    public void justcheckSubscription(AppCompatActivity activity){
        //sharedPre_clear();
        appCompatActivity=activity;
        bp=new BillingProcessor(mcontext,google_license_key,this);
        bp.initialize();
    }

    public void only_subs_check(){
        bp=new BillingProcessor(mcontext,google_license_key,this);
        bp.initialize();
    }


    @Override
    public void onBillingInitialized() {
        if (dialog==null){

            bp.loadOwnedPurchasesFromGoogleAsync(new BillingProcessor.IPurchasesResponseListener() {
                @Override
                public void onPurchasesSuccess() {
                    boolean init_checking=sub_recorder.getBoolean("Subscribing2",false);

                    if (bp.getSubscriptionPurchaseInfo(subscriptionID)!=null){
                        ispurchased=true;
                        recorder.putBoolean("Subscribing1",ispurchased);
                        recorder.putBoolean("Subscribing2",ispurchased);
                        recorder.apply();
                    }else{
                        ispurchased=false;
                        recorder.putBoolean("Subscribing2",ispurchased);
                        recorder.apply();
                    }

                    if (init_checking!=ispurchased){

                        Toast.makeText(mcontext,"Subscription state renewed",Toast.LENGTH_SHORT).show();
                        //mpremium.invalidate();
                        appCompatActivity.recreate();
                    }


                }

                @Override
                public void onPurchasesError() {
                    Toast.makeText(mcontext,"Sync Error",Toast.LENGTH_SHORT).show();
                }
            });



                    /*if (init_checking==true&&ispurchased==false){
                        Toast.makeText(mcontext,"Subscription state renewed  GOO 2",Toast.LENGTH_SHORT).show();

                        if (sub_recorder.getBoolean("notification",false)&&alarm_call==true){
                            SQLiteControl sqlite=new SQLiteControl(mcontext);
                            AlarmManager alarmManager = (AlarmManager) mcontext.getSystemService(mcontext.ALARM_SERVICE);
                            RXJava_sqlite rxJava_sqlite=new RXJava_sqlite();
                            rxJava_sqlite.start(sqlite,alarmManager,mcontext);
                        }

                        //mpremium.invalidate();
                        appCompatActivity.recreate();
                    }*/


        }
        /*if (dialog==null){
            if (sub_recorder.getBoolean("Subscribing2",false)){
                boolean init_checking=sub_recorder.getBoolean("Subscribing2",false);

                if (bp.listOwnedSubscriptions().size()==0){
                    Toast.makeText(mcontext,"NO List",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(mcontext,bp.listOwnedSubscriptions().get(0),Toast.LENGTH_SHORT).show();
                }

                if (bp.getSubscriptionPurchaseInfo(subscriptionID)!=null){
                    Toast.makeText(mcontext,bp.getSubscriptionPurchaseInfo(subscriptionID).purchaseData.autoRenewing+" AR",Toast.LENGTH_SHORT).show();
                    ispurchased=true;
                    recorder.putBoolean("Subscribing2",ispurchased);
                    recorder.apply();
                }else{
                    Toast.makeText(mcontext,"IT's null",Toast.LENGTH_SHORT).show();
                    ispurchased=false;
                    recorder.putBoolean("Subscribing2",ispurchased);
                    recorder.apply();
                }



                if (init_checking==false&&ispurchased==true){

                    Toast.makeText(mcontext,"Subscription state renewed",Toast.LENGTH_SHORT).show();
                    mpremium.invalidate();
                    //appCompatActivity.recreate();
                }

                if (init_checking==true&&ispurchased==false){
                    Toast.makeText(mcontext,"Subscription state renewed",Toast.LENGTH_SHORT).show();

                    if (sub_recorder.getBoolean("notification",false)&&alarm_call==true){
                        SQLiteControl sqlite=new SQLiteControl(mcontext);
                        AlarmManager alarmManager = (AlarmManager) mcontext.getSystemService(mcontext.ALARM_SERVICE);
                        RXJava_sqlite rxJava_sqlite=new RXJava_sqlite();
                        rxJava_sqlite.start(sqlite,alarmManager,mcontext);
                    }
                    //mpremium.invalidate();
                    appCompatActivity.recreate();
                }

                if (bp!=null){
                    bp.release();
                }

            }else{


            }

        }*/


    }

    @Override
    public void onProductPurchased(String productId,PurchaseInfo details) {
        if (dialog!=null){

            dialog.dismiss();

            if (bp!=null){

                bp.loadOwnedPurchasesFromGoogleAsync(new BillingProcessor.IPurchasesResponseListener() {
                    @Override
                    public void onPurchasesSuccess() {
                        if (bp.getSubscriptionPurchaseInfo(subscriptionID)!=null){
                            ispurchased=true;
                            recorder.putBoolean("Subscribing1",ispurchased);
                            recorder.putBoolean("Subscribing2",ispurchased);
                            recorder.apply();

                            //mpremium.invalidate();
                            appCompatActivity.recreate();
                            if (bp!=null){
                                bp.release();
                            }
                        }else{
                            ispurchased=false;
                            recorder.putBoolean("Subscribing2",ispurchased);
                            recorder.apply();

                            //mpremium.invalidate();
                            appCompatActivity.recreate();
                            if (bp!=null){
                                bp.release();
                            }
                        }
                    }


                    @Override
                    public void onPurchasesError() {

                    }
                });

                //mpremium.invalidate();

            }
        }
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode,Throwable error) {

    }
}

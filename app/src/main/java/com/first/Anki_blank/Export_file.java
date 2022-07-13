package com.first.Anki_blank;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Export_file extends AppCompatActivity {

    MainActivity mainActivity=new MainActivity();
    ImageView loading;
    Intent backtoMain;
    Disposable backgroundTask;
    File export;
    String path;
    String name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiting);
        loading=findViewById(R.id.loading);
        backtoMain = new Intent(this, MainActivity.class);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
        init_Loading_anim();
        if (getIntent().getBooleanExtra("isExport",false)==true){
            if (getIntent().getIntExtra("exportPDF",999)==0){
                export=new File(getIntent().getStringExtra("PDFname"));
                path=this.getExternalFilesDir(null).getAbsolutePath()+"/"+"Exported PDF";
                name=export.getName();
            }else if (getIntent().getIntExtra("exportDB",999)==1){
                export=this.getDatabasePath("Control.db");
                path=this.getExternalFilesDir(null).getAbsolutePath()+ "/" + "DB_Storage";
                name=simpleDateFormat.format(Calendar.getInstance().getTime());
            }

            start();
        }

        if (getIntent().getBooleanExtra("isImport",false)==true){

            if (getIntent().getIntExtra("importPDF",999)==0){

            }else if (getIntent().getIntExtra("importDB",999)==1){

            }

        }
    }

    @Nullable
    public void file_move(@NonNull File inputfile, String name, String topath) {

        try {
            InputStream inputStream = new FileInputStream(inputfile);
            if (inputStream == null){
                return;
            }

            OutputStream nfos= null;
            byte[] buf = new byte[1024];
            int len;
            nfos = new FileOutputStream(topath+"/"+name);

            while ((len = inputStream.read(buf)) > 0)
                nfos.write(buf, 0, len);

            nfos.close();
            inputStream.close();


        } catch (IOException ignore) {
            ignore.printStackTrace();
        }
    }

    @Nullable
    public void file_select(@NonNull File inputfile, String name, String topath) {

        try {
            InputStream inputStream = new FileInputStream(inputfile);
            if (inputStream == null){
                return;
            }

            OutputStream nfos= null;
            byte[] buf = new byte[1024];
            int len;
            nfos = new FileOutputStream(topath+"/"+name);

            while ((len = inputStream.read(buf)) > 0)
                nfos.write(buf, 0, len);

            nfos.close();
            inputStream.close();


        } catch (IOException ignore) {
            ignore.printStackTrace();
        }
    }


    void init_Loading_anim(){
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.right_rotating);
        loading.setAnimation(animation);
    }

    public void start() {
        backgroundTask = Observable.fromCallable(() -> {

            return true;

        })      .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((t) -> {
                    file_move(export,name,path);
                    startActivity(backtoMain);
                    finish();
                });
    }
}

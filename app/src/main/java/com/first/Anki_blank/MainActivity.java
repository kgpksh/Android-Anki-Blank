package com.first.Anki_blank;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.AlarmManager;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    LinearLayout basic_dir, premium_dir,premium;
    HashMap<String,LinearLayout> rv=new HashMap<>();
    String selectedKey;
    SharedPreferences spf,mother,General;
    SharedPreferences.Editor edt,motheredt,GEedt;
    SQLiteHelper helper;
    SQLiteControl sqlite;
    ObjectAnimator objectAnimator;
    public static Context context;
    File basic_folder, premium_folder;
    private DrawerLayout drawerLayout;
    int allpagenum;
    TextView page_display;
    ProgressBar display_page;

    AlarmManager alarmManager;

    Subscribe subscribe;
    boolean ispurchased1,ispurchased2;

    private AppUpdateManager mAppUpdateManager;
    private static final int RC_APP_UPDATE=111;
    boolean start_download=true;

    String motherpath;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context=getApplicationContext();

        updaterequest();

        motherpath=getFilesDir().getParent();

        General=getSharedPreferences("General",MODE_PRIVATE);
        GEedt=General.edit();
        if (General.getBoolean("first_noti",false)==false){
            first_alert_dialog_notificaiton();
        }
        GEedt.putBoolean("study_directly",false);
        GEedt.apply();

        premium=findViewById(R.id.drawer_premium);
        //subscribing=new Subscribing();
        //subscribing.setupBillingClient(MainActivity.this,MainActivity.this);
        subscribe=new Subscribe(MainActivity.this);
        subscribe.checkSubscription(MainActivity.this,premium);
        ispurchased1 =General.getBoolean("Subscribing1",false);
        ispurchased2 =General.getBoolean("Subscribing2",false);

        sqlite=new SQLiteControl(MainActivity.this);
        helper=sqlite.helper;

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        basic_folder=new File(this.getFilesDir()+"/"+"Basic folder");
        premium_folder=new File(this.getExternalFilesDir(null).getParent()+"/"+"Premium folder");

        File Default_setting=new File(motherpath+"/shared_prefs/Default.xml");
        File General=new File(motherpath+"/shared_prefs/General.xml");
        mother=getSharedPreferences("Mother",MODE_PRIVATE);
        motheredt = mother.edit();

        if (General.exists()){

        }else{
            General_setting general_setting=new General_setting();
            general_setting.basicGeneral("General");
        }
        if (Default_setting.exists()){

        }else{
            Default_option default_option=new Default_option();
            default_option.create_sharedpreference("Default");
        }
        mkRootdir(basic_folder);
        mkRootdir(premium_folder);
        mkRootdir(new File(this.getExternalFilesDir(null)+"/"+"DB_Storage"));
        mkRootdir(new File(this.getExternalFilesDir(null)+"/"+"Exported PDF"));
        basic_dir =findViewById(R.id.basic);
        premium_dir =findViewById(R.id.premium);

        LinearLayout study_all=findViewById(R.id.all_study);
        study_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent all_study=new Intent(getApplicationContext(),StudyPDFFiles.class);
                all_study.putExtra("all_study1",basic_folder.getAbsolutePath());
                all_study.putExtra("all_study2",premium_folder.getAbsolutePath());
                all_study.putExtra("all_studyS",true);
                startActivity(all_study);
            }
        });

        spf=getSharedPreferences("directory_status",MODE_PRIVATE);
        edt = spf.edit();

        page_display=findViewById(R.id.page_capability_display);
        display_page =findViewById(R.id.progressBar);

        whenFilechanged();

        innerdir(basic_folder, basic_folder, basic_dir);
        innerdir(premium_folder, premium_folder, premium_dir);

        drawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);

        LinearLayout general,schedule,clear;
        general=findViewById(R.id.drawer_general_setting);
        schedule=findViewById(R.id.drawer_learning_schedule);
        clear=findViewById(R.id.drawer_data_clear);


        general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,General_setting.class);
                startActivity(intent);
                drawerLayout.closeDrawers();
            }
        });

        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Default_option.class);
                intent.putExtra("fromMain",true);
                startActivity(intent);
                drawerLayout.closeDrawers();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db_clear_alertdialog();
                drawerLayout.closeDrawers();
            }
        });

        ImageView purchase_check_mark=findViewById(R.id.purchage_check_mark);
        TextView purchase_status_textbox=findViewById(R.id.purchage_status_textbox);
        if (ispurchased2 ==false){
            premium.setClickable(true);
            purchase_check_mark.setImageResource(R.drawable.empty_premium);
            purchase_status_textbox.setTextColor(Color.WHITE);
            purchase_status_textbox.setText(R.string.Free);
        }else{
            premium.setClickable(false);
            purchase_check_mark.setImageResource(R.drawable.having_premium);
            purchase_status_textbox.setTextColor(Color.parseColor("#FF81E40E"));
            purchase_status_textbox.setText(R.string.having_premium);
        }

        premium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ispurchased2 ==false){
                    drawerLayout.closeDrawers();
                    //subscribing.showDialog(MainActivity.this,MainActivity.this);
                    subscribe.showDialog(MainActivity.this);
                }
            }
        });

        Button hamburgur_On=findViewById(R.id.hamburger_button);
        hamburgur_On.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }



    @Override
    public void onConfigurationChanged(@NonNull @NotNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        basic_dir.removeAllViews();
        innerdir(basic_folder, basic_folder, basic_dir);
        premium_dir.removeAllViews();
        innerdir(premium_folder, premium_folder, premium_dir);
        basic_dir.invalidate();
        premium_dir.invalidate();
    }

    private void innerdir(File nowDirectory, File rootDirectory, LinearLayout shown_dir){
        File files[]= nowDirectory.listFiles();
        ArrayList<File> folder = new ArrayList<>();
        ArrayList<File> pdf = new ArrayList<>();

        if (files!=null){
            for (int i=0; i<files.length; i++){
                if (files[i].isDirectory()&&!files[i].isHidden()){
                    folder.add(files[i]);
                }else{
                    if (files[i].getName().endsWith("pdf")){
                        pdf.add(files[i]);
                    }
                }
            }


            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rv.put(nowDirectory.getAbsolutePath(),(LinearLayout) layoutInflater.inflate(R.layout.dir,null));
            RecyclerView recyclerView=rv.get(nowDirectory.getAbsolutePath()).findViewById(R.id.inre);
            FrameLayout rootframe=rv.get(nowDirectory.getAbsolutePath()).findViewById(R.id.dir_frame);
            LinearLayout container=rv.get(nowDirectory.getAbsolutePath()).findViewById(R.id.ct);
            LinearLayout linearLayout=rv.get(nowDirectory.getAbsolutePath()).findViewById(R.id.big);
            if (nowDirectory!=basic_folder){
                linearLayout.setPadding(0, convettDPtoPX(10),0,0);
            }
            ImageView imageView=rv.get(nowDirectory.getAbsolutePath()).findViewById(R.id.imageView);

            TextView textView=rv.get(nowDirectory.getAbsolutePath()).findViewById(R.id.textView2);
            textView.setText(nowDirectory.getName());


            Button creator=rv.get(nowDirectory.getAbsolutePath()).findViewById(R.id.button4);

            creator.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    objectAnimator=ObjectAnimator.ofFloat(creator,"rotation",0,-45);
                    objectAnimator.setDuration(200);
                    objectAnimator.setRepeatCount(1);
                    objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
                    objectAnimator.start();
                    objectAnimator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {

                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                            objectAnimator.pause();
                        }
                    });
                    folder_dialog(nowDirectory.getAbsolutePath(), nowDirectory, rootDirectory, shown_dir,basic_folder,creator);
                }
            });



            ToggleButton toggleButton=rv.get(nowDirectory.getAbsolutePath()).findViewById(R.id.toggleButton);
            toggleButton.setGravity(Gravity.LEFT);
            toggleButton.setGravity(Gravity.CENTER_VERTICAL);
            toggleButton.setPadding(230,0,0,0);

            boolean checked=spf.getBoolean(nowDirectory.getAbsolutePath(),true);
            if (checked){
                toggleButton.setChecked(true);
                container.setVisibility(View.VISIBLE);
                imageView.setImageResource(R.drawable.down_chevron);
            }else{
                toggleButton.setChecked(false);
                container.setVisibility(View.GONE);
                imageView.setImageResource(R.drawable.right_chevron);
            }

            if (nowDirectory!=rootDirectory){
                rootframe.setBackgroundColor(Color.parseColor("#F6F4E2"));
            }


            if (nowDirectory==rootDirectory&&rootDirectory==basic_folder){
                rootframe.setBackgroundColor(Color.TRANSPARENT);
                toggleButton.setBackgroundColor(Color.TRANSPARENT);

            }


            toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (!isChecked){
                        edt.putBoolean(nowDirectory.getAbsolutePath(),true);
                        container.setVisibility(View.VISIBLE);
                        imageView.setImageResource(R.drawable.down_chevron);
                        edt.apply();
                    }else if(isChecked){
                        edt.putBoolean(nowDirectory.getAbsolutePath(),false);
                        container.setVisibility(View.GONE);
                        imageView.setImageResource(R.drawable.right_chevron);
                        edt.apply();
                    }

                }
            });


            if (nowDirectory==rootDirectory&&rootDirectory==premium_folder){
                rootframe.setBackgroundColor(Color.parseColor("#BCE4F6"));
                if (ispurchased1 ==false){
                    toggleButton.setChecked(false);
                    toggleButton.setClickable(false);
                    creator.setClickable(false);
                    linearLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //subscribing.showDialog(MainActivity.this,MainActivity.this);
                            subscribe.showDialog(MainActivity.this);
                        }
                    });
                    imageView.setImageResource(R.drawable.right_chevron);
                }
            }

            boolean isPremium;
            if (rootDirectory==premium_folder){
                isPremium=true;
            }else{
                isPremium=false;
            }
            PDFAdapterre pdfAdapterre=new PDFAdapterre(this,pdf,basic_folder,premium_folder, ispurchased2,isPremium);
            recyclerView.setAdapter(pdfAdapterre);
            recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));

            if(nowDirectory==rootDirectory){
                shown_dir.addView(rv.get(rootDirectory.getAbsolutePath()));
            }else{
                int cutindex=nowDirectory.getAbsolutePath().lastIndexOf("/");
                String fore=nowDirectory.getAbsolutePath().substring(0,cutindex);
                LinearLayout foredirset=rv.get(fore).findViewById(R.id.indir);
                foredirset.addView(rv.get(nowDirectory.getAbsolutePath()));
            }



            for (int j=0;j<folder.size();j++){
                innerdir(folder.get(j),rootDirectory,shown_dir);
            }

            recyclerView.addRecyclerListener(new RecyclerView.RecyclerListener() {
                @Override
                public void onViewRecycled(@NonNull @NotNull RecyclerView.ViewHolder holder) {
                    if (rootDirectory==basic_folder){
                        whenFilechanged();
                    }
                    RXJava_sqlite rxJava_sqlite=new RXJava_sqlite();
                    rxJava_sqlite.start(sqlite,alarmManager,getApplicationContext());
                }
            });

        }

    }

    private void new_folder(String path,File rootDir,LinearLayout shown_directory){
        final String[] cb={getResources().getString(R.string.Keep_show_box)};
        final boolean[]checkedItems={false};
        EditText editText=new EditText(getApplicationContext());


        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.New_folder_make))
                .setView(editText);

        builder.setMultiChoiceItems(cb, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                checkedItems[which]=isChecked;
            }
        });


        builder.setPositiveButton(getResources().getString(R.string.A_folder_create), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNegativeButton(getResources().getString(R.string.Cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog=builder.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File dir=new File(path+"/"+editText.getText());
                if (!dir.exists()){
                    dir.mkdirs();
                    shown_directory.removeAllViews();
                    innerdir(rootDir,rootDir,shown_directory);
                    shown_directory.invalidate();
                }else{
                    if (editText.length()==0){
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.folder_name_required),Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.folder_name_already_exists),Toast.LENGTH_SHORT).show();
                    }
                }
                if (checkedItems[0]==false){
                    alertDialog.dismiss();
                }
            }
        });

    }



    private void delete_ask(String path, File nowdir, File rootDir,LinearLayout show_dir,Button creator){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);

        if (nowdir==rootDir){
            builder.setTitle(getResources().getString(R.string.Delete_confirm))
                    .setMessage(getResources().getString(R.string.Folder_delete_content));
        }else{
            builder.setTitle(getResources().getString(R.string.Delete_confirm))
                    .setMessage(getResources().getString(R.string.Folder_delete_content));
        }

        builder.setPositiveButton(getResources().getString(R.string.Delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Handler handler=new Handler();
                Handler handler1=new Handler();
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_rotating);
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        handler1.post(new Runnable() {
                            @Override
                            public void run() {
                                creator.setClickable(false);
                                creator.setBackgroundResource(R.drawable.loading_rotating_right);
                                creator.setAnimation(animation);
                            }
                        });
                        setDirempty(path,nowdir,rootDir);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                show_dir.setClickable(false);
                                show_dir.removeAllViews();
                                innerdir(rootDir,rootDir,show_dir);
                                show_dir.invalidate();

                                animation.cancel();
                                creator.setBackgroundResource(R.drawable.add_plus);

                                show_dir.setClickable(true);
                                whenFilechanged();
                            }
                        });

                    }
                }).start();


                RXJava_sqlite rxJava_sqlite=new RXJava_sqlite();
                rxJava_sqlite.start(sqlite,alarmManager,getApplicationContext());
            }
        });

        builder.setNegativeButton(getResources().getString(R.string.Cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    private void setDirempty(String path,File nowDir, File rootDir){
        ArrayList<String> pdf_sql_data=new ArrayList<>();
        File dir=new File(path);
        File[] childFileList=dir.listFiles();

        if (dir.exists()){
            for (File childFile : childFileList){
                if (childFile.isDirectory()){
                    setDirempty(childFile.getAbsolutePath(),dir,rootDir);
                }else{
                    pdf_sql_data.add(childFile.getAbsolutePath());
                    motheredt.remove(childFile.getAbsolutePath());
                    childFile.delete();
                }
            }

            if (!(nowDir==rootDir)){
                dir.delete();
            }
            data_Delete(pdf_sql_data);

            motheredt.apply();
        }
    }

    private void data_Delete(ArrayList<String> Pdf_Data_Delete){
        SQLiteDatabase sl=helper.getWritableDatabase();
        sl.beginTransaction();
        try {
            for (String data : Pdf_Data_Delete){
                String sql="delete from "+ helper.TABLE_NAME+" where "+helper.pdfname+" = '"+getreplace(data)+"'";
                String delete_list="delete from "+ helper.List_Table+" where "+helper.filelist+" = '"+data+"'";
                sl.execSQL(sql);
                sl.execSQL(delete_list);
            }
            sl.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sl.endTransaction();
        }

        sl.close();
        helper.close();
    }

    private void mkRootdir(File file){
        if (!file.exists()){
            file.mkdirs();
        }
    }

    private void folder_dialog(String path, File nowDir, File rootDir, LinearLayout show_directory,File comparer,Button creator){
        Dialog dialog=new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.main_folder_control);
        dialog.show();

        TextView folder_name=dialog.findViewById(R.id.folder_name);

        LinearLayout folder_create=dialog.findViewById(R.id.new_folder);

        LinearLayout get_pdf=dialog.findViewById(R.id.get_pdf);

        LinearLayout multi_study=dialog.findViewById(R.id.multi_study);

        LinearLayout schedule=dialog.findViewById(R.id.pick_option);

        LinearLayout folder_delete=dialog.findViewById(R.id.folder_delete);

        folder_name.setText(nowDir.getName());

        folder_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                new_folder(path,rootDir,show_directory);
            }
        });


        get_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set set2 = rv.entrySet();
                Iterator iterator2 = set2.iterator();
                while(iterator2.hasNext()) {
                    Map.Entry<String, LinearLayout> entry = (Map.Entry<String, LinearLayout>) iterator2.next();
                    String key = (String) entry.getKey();
                    LinearLayout value = (LinearLayout) entry.getValue();
                    if (value == rv.get(nowDir.getAbsolutePath())) {
                        selectedKey = key;
                        break;
                    }
                }
                Intent filepdf = new Intent(getApplicationContext(), File_Manager.class);
                filepdf.putExtra("topath",selectedKey);
                if (rootDir.getAbsolutePath()==comparer.getAbsolutePath()){
                    filepdf.putExtra("isbasic",true);
                }
                startActivity(filepdf);
                dialog.dismiss();
                finish();
            }
        });

        multi_study.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent multi_study=new Intent(getApplicationContext(),StudyPDFFiles.class);
                multi_study.putExtra("multi_study",nowDir.getAbsolutePath());
                multi_study.putExtra("multi_studyS",true);
                startActivity(multi_study);
                dialog.dismiss();
            }
        });

        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent option=new Intent(getApplicationContext(),Default_option.class);
                option.putExtra("multiple_pdf",nowDir.getAbsolutePath());
                option.putExtra("multiple_check",0);
                startActivity(option);
                dialog.dismiss();
            }
        });


        folder_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                delete_ask(path, nowDir, rootDir, show_directory,creator);
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                objectAnimator.resume();
            }
        });

        if (ispurchased2 ==false&&rootDir==premium_folder){
            folder_create.setClickable(false);
            get_pdf.setClickable(false);
            multi_study.setClickable(false);
            schedule.setClickable(false);

            folder_create.setBackgroundColor(Color.GRAY);
            get_pdf.setBackgroundColor(Color.GRAY);
            multi_study.setBackgroundColor(Color.GRAY);
            schedule.setBackgroundColor(Color.GRAY);
        }

    }

    String getreplace(String filepath){
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

    private void db_clear(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> listed_file=new ArrayList<>();
                ArrayList<String> existing_file=new ArrayList<>();
                When_delete when_delete=new When_delete();

                helper=sqlite.helper;
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
        }).start();
    }

    private void db_clear_alertdialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.Data_cleaning_title));



        builder.setPositiveButton(getResources().getString(R.string.Clear), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //db_clear();
                RXJava_sqlite rxJava_sqlite=new RXJava_sqlite();
                db_clear();
                rxJava_sqlite.start(sqlite,alarmManager,getApplicationContext());
                basic_dir.removeAllViews();
                premium_dir.removeAllViews();
                innerdir(basic_folder, basic_folder, basic_dir);
                innerdir(premium_folder, premium_folder, premium_dir);
                basic_dir.invalidate();
                premium_dir.invalidate();
            }
        });

        builder.setNegativeButton(getResources().getString(R.string.Cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    private void db_backup(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.DB_backup_path));

        builder.setPositiveButton(getResources().getString(R.string.OK), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(getApplicationContext(),Export_file.class);
                intent.putExtra("isExport",true);
                intent.putExtra("exportDB",1);
                startActivity(intent);
            }
        });

        builder.setNegativeButton(getResources().getString(R.string.Cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    private int convettDPtoPX(int dp){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        float density = displayMetrics.density;
        int px=(int)(dp*density+0.5);
        return px;
    }

    void pagecounter(File dir){
        File files[]= dir.listFiles();

        for (File fileList : files){
            if (fileList.isDirectory()&&!fileList.isHidden()){
                pagecounter(fileList);
            }else{
                try {
                    allpagenum=allpagenum+File_Manager.getTotalPages(fileList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void whenFilechanged(){
        allpagenum=0;
        pagecounter(basic_folder);
        display_page.setProgress(allpagenum);
        page_display.setText(allpagenum+" / "+"2000 "+getResources().getString(R.string.pages));
    }

    private void first_alert_dialog_notificaiton(){
        Dialog dialog=new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.first_start_page1);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();


        Button first_dialog=dialog.findViewById(R.id.first_dialog);
        first_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                GEedt.putBoolean("first_noti",true);
                GEedt.apply();
            }
        });
    }

    private void updaterequest() {
        mAppUpdateManager= AppUpdateManagerFactory.create(this);
        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {
                if (result.updateAvailability()== UpdateAvailability.UPDATE_AVAILABLE
                        && result.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)){
                    try {
                        mAppUpdateManager.startUpdateFlowForResult(result,AppUpdateType.FLEXIBLE,MainActivity.this,RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        mAppUpdateManager.registerListener(installStateUpdatedListener);

    }

    private InstallStateUpdatedListener installStateUpdatedListener=new InstallStateUpdatedListener() {
        @Override
        public void onStateUpdate(@NonNull @NotNull InstallState state) {

            if (start_download){
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.update_download_started),Toast.LENGTH_SHORT).show();
                start_download=false;
            }

            if (state.installStatus() == InstallStatus.DOWNLOADED){
                showCompletedUpdate();
            }
        }
    };

    @Override
    protected void onStop() {
        if (mAppUpdateManager!=null) mAppUpdateManager.unregisterListener(installStateUpdatedListener);
        super.onStop();
    }

    private void showCompletedUpdate() {
        Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),getResources().getString(R.string.App_update_snackbar),
                Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(getResources().getString(R.string.update_install), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAppUpdateManager.completeUpdate();
            }
        });
        snackbar.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        if (requestCode==RC_APP_UPDATE && resultCode !=RESULT_OK){
            Toast.makeText(this,"Cancel",Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
package com.first.Anki_blank;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class Default_option extends AppCompatActivity {
    private Context context;
    SharedPreferences sharedPreferences, parent;
    ArrayList<String> mother;
    ArrayList<File> son;
    RecyclerView oL;
    OptionList optionList;
    File multi,shared_folder,directory_status,Mother,General,google_measurement,chromium,admob,anki_blank_pref;
    File[] spList;
    boolean selectable,fromMain;
    TextView Title;
    String motherpath;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mother=new ArrayList<String>();
        son=new ArrayList<File>();
        motherpath=getFilesDir().getParent();
        setContentView(R.layout.schedule_select);
        oL=findViewById(R.id.OL);
        shared_folder=new File(motherpath+"/shared_prefs");
        spList=shared_folder.listFiles();

        google_measurement=new File(motherpath+"/shared_prefs/com.google.android.gms.measurement.prefs.xml");
        chromium=new File(motherpath+"/shared_prefs/WebViewChromiumPrefs.xml");
        admob=new File(motherpath+"/shared_prefs/admob.xml");
        anki_blank_pref=new File(motherpath+"/shared_prefs/com.first.Anki_blank_preferences.xml");
        directory_status=new File(motherpath+"/shared_prefs/directory_status.xml");
        Mother=new File(motherpath+"/shared_prefs/Mother.xml");
        General=new File(motherpath+"/shared_prefs/General.xml");

        Collections.addAll(son,spList);

        son.remove(google_measurement);
        son.remove(chromium);
        son.remove(admob);
        son.remove(anki_blank_pref);
        son.remove(directory_status);
        son.remove(Mother);
        son.remove(General);

        fromMain=getIntent().getBooleanExtra("fromMain",false);
        Title=findViewById(R.id.Title);

        if (fromMain){
            Title.setVisibility(View.GONE);
        }
        if (getIntent().getIntExtra("multiple_check",9)==0){
            multi=new File(getIntent().getStringExtra("multiple_pdf"));
            Title.setText(multi.getName());
            for (int i=0; i<getfile(multi).size(); i++){
                mother.add(getfile(multi).get(i).getAbsolutePath());
            }
        }else if(getIntent().getIntExtra("multiple_check",9)==1){
            File getPdfname=new File(getIntent().getStringExtra("pdfname"));
            Title.setText(getPdfname.getName().substring(0,getPdfname.getName().length()-4));
            mother.add(getIntent().getStringExtra("pdfname"));
        }

        if (getIntent().getIntExtra("multiple_check",9)==0||getIntent().getIntExtra("multiple_check",9)==1){
            selectable=true;
        }else{
            selectable=false;
        }
        LinearLayout make_Option=findViewById(R.id.newoption);
        make_Option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewoption();

            }
        });



        optionList=new OptionList(this,son,mother,selectable,getIntent().getIntExtra("multiple_check",9),getIntent().getStringExtra("pdfname"),fromMain);
        oL.setAdapter(optionList);
        oL.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
    }

    private void createNewoption(){
        EditText editText=new EditText(getApplicationContext());

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.putin_new_option_name))
                .setView(editText);

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
                String name=editText.getText().toString();
                File option=new File(motherpath+"/shared_prefs/"+name+".xml");
                if (option.exists()||name.length()<1){
                    if (option.exists()){
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.name_is_already_exists),Toast.LENGTH_SHORT).show();
                    }else if (name.length()<1){
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.least_namelength),Toast.LENGTH_SHORT).show();
                    }
                }else{
                    create_sharedpreference(name);
                    optionList.son.add(option);
                    optionList.notifyDataSetChanged();
                    alertDialog.dismiss();
                }

            }
        });
    }

    public void create_sharedpreference(String name){
        context=MainActivity.context;
        sharedPreferences = context.getSharedPreferences(name,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("whenagain",0);
        editor.putInt("whengood1",10);
        editor.putInt("whengood2",0);
        editor.putInt("whengood3",0);
        editor.putInt("whengood4",0);
        editor.putInt("whengood5",0);
        editor.putInt("graduating",1);
        editor.putInt("easyinterval",4);
        editor.putInt("startingease",250);
        editor.putInt("easybonus",130);
        editor.putInt("intervalmodifier",100);
        editor.putInt("maximuminterval",36500);
        editor.putInt("whenreagain",0);
        editor.putInt("relearningsteps1",10);
        editor.putInt("relearningsteps2",0);
        editor.putInt("relearningsteps3",0);
        editor.putInt("relearningsteps4",0);
        editor.putInt("relearningsteps5",0);
        editor.putInt("relearningminimum",1);

        editor.apply();
    }

    public void mother_preference(String mother, String son){
        context=MainActivity.context;
        parent=context.getSharedPreferences("Mother",MODE_PRIVATE);
        SharedPreferences.Editor editor=parent.edit();
        editor.putString(mother,son);
        editor.apply();
    }

    ArrayList<File> getfile(File dir) {
        ArrayList<File> arrayList = new ArrayList<>();
        File files[] = dir.listFiles();

        if (files != null) {
            for (File singleFile : files){
                if (singleFile.isDirectory()&&!singleFile.isHidden()){
                    arrayList.addAll(getfile(singleFile));
                }else{
                    if (singleFile.getName().endsWith("pdf")){
                        arrayList.add(singleFile);
                    }
                }
            }

        }
        for (int i=0; i<arrayList.size(); i++){
            if (arrayList.get(i).isDirectory()){
                arrayList.remove(i);
            }
        }
        return arrayList;
    }
}

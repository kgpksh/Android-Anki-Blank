package com.first.Anki_blank;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;

import java.util.ArrayList;

public class Option extends AppCompatActivity {
    Integer[] good=new Integer[5];
    Integer[] goodre=new Integer[5];
    EditText whenagain,whengood1,whengood2,whengood3,whengood4,whengood5,graduating,easyinterval,startingease,easybonus,intervalmodifier,maximuminterval,whenreagain,
    relearningsteps1,relearningsteps2,relearningsteps3,relearningsteps4,relearningsteps5,relearningintervalpercent,relearningminimum;
    int whenagainr,whengood1r,whengood2r,whengood3r,whengood4r,whengood5r,graduatingr,easyintervalr,startingeaser,easybonusr,intervalmodifierr,maximumintervalr,whenreagainr,relearningsteps1r,relearningsteps2r,relearningsteps3r,relearningsteps4r,relearningsteps5r,relearningintervalpercentr,relearningminimumr;
    ArrayList<Integer> list=new ArrayList<Integer>();
    ArrayList<Integer> listre=new ArrayList<Integer>();
    TextView zoom;
    String[] keys=new String[5];
    String[] keys2=new String[5];
    String spName;
    AppCompatSeekBar zoombar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option);
        spName=getIntent().getStringExtra("sharedname");
        SharedPreferences sf=getSharedPreferences(spName,MODE_PRIVATE);
        SharedPreferences.Editor editor = sf.edit();
        zoombar=findViewById(R.id.zoom_bar);
        zoom=findViewById(R.id.zoom);
        whenagain=findViewById(R.id.whenagain);
        whengood1=findViewById(R.id.whengood1);
        whengood2=findViewById(R.id.whengood2);
        whengood3=findViewById(R.id.whengood3);
        whengood4=findViewById(R.id.whengood4);
        whengood5=findViewById(R.id.whengood5);
        graduating=findViewById(R.id.gruduating);
        easyinterval=findViewById(R.id.easyinterval);
        startingease=findViewById(R.id.startingease);
        easybonus=findViewById(R.id.easybonus);
        intervalmodifier=findViewById(R.id.intervalmodifier);
        maximuminterval=findViewById(R.id.maximuminterval);
        whenreagain=findViewById(R.id.whenreagain);
        relearningsteps1=findViewById(R.id.relearningsteps1);
        relearningsteps2=findViewById(R.id.relearningsteps2);
        relearningsteps3=findViewById(R.id.relearningsteps3);
        relearningsteps4=findViewById(R.id.relearningsteps4);
        relearningsteps5=findViewById(R.id.relearningsteps5);
        relearningintervalpercent=findViewById(R.id.relearningintervalpercent);
        relearningminimum=findViewById(R.id.relearningminimum);
        keys= new String[]{"isempty1", "isempty2", "isempty3", "isempty4", "isempty5"};
        keys2=new String[]{"reempty1","reempty2","reempty3","reempty4","reempty5"};
        zoom.setText((int)sf.getFloat("zoom",(float)1.0)+"."+(int)sf.getFloat("zoom",(float)0.0)%10);
        lengthLimit(whenagain);
        lengthLimit(whengood1);
        lengthLimit(whengood2);
        lengthLimit(whengood3);
        lengthLimit(whengood4);
        lengthLimit(whengood5);
        lengthLimit(graduating);
        lengthLimit(easyinterval);
        lengthLimit(startingease);
        lengthLimit(easybonus);
        lengthLimit(intervalmodifier);
        lengthLimit(maximuminterval);
        lengthLimit(whenreagain);
        lengthLimit(relearningsteps1);
        lengthLimit(relearningsteps2);
        lengthLimit(relearningsteps3);
        lengthLimit(relearningsteps4);
        lengthLimit(relearningsteps5);
        lengthLimit(relearningintervalpercent);
        lengthLimit(relearningminimum);

        changelistner("whenagain",whenagain);
        changelistner("graduating",graduating);
        changelistner("easyinterval",easyinterval);
        changelistner("startingease",startingease);
        changelistner("easybonus",easybonus);
        changelistner("intervalmodifier",intervalmodifier);
        changelistner("maximuminterval",maximuminterval);
        changelistner("whenreagain",whenreagain);
        changelistner("relearningintervalpercent", relearningintervalpercent);
        changelistner("relearningminimum",relearningminimum);

        whenagainr=sf.getInt("whenagain",0);
        whengood1r=sf.getInt("whengood1",10);
        whengood2r=sf.getInt("whengood2",0);
        whengood3r=sf.getInt("whengood3",0);
        whengood4r=sf.getInt("whengood4",0);
        whengood5r=sf.getInt("whengood5",0);
        graduatingr=sf.getInt("graduating",1);
        easyintervalr=sf.getInt("easyinterval",4);
        startingeaser=sf.getInt("startingease",250);
        easybonusr=sf.getInt("easybonus",130);
        intervalmodifierr=sf.getInt("intervalmodifier",100);
        maximumintervalr=sf.getInt("maximuminterval",36500);
        whenreagainr=sf.getInt("whenreagain",0);
        relearningsteps1r=sf.getInt("relearningsteps1",15);
        relearningsteps2r=sf.getInt("relearningsteps2",0);
        relearningsteps3r=sf.getInt("relearningsteps3",0);
        relearningsteps4r=sf.getInt("relearningsteps4",0);
        relearningsteps5r=sf.getInt("relearningsteps5",0);
        relearningintervalpercentr=sf.getInt("relearningintervalpercent",100);
        relearningminimumr=sf.getInt("relearningminimum",1);

        good[0]=whengood1r;
        good[1]=whengood2r;
        good[2]=whengood3r;
        good[3]=whengood4r;
        good[4]=whengood5r;
        goodre[0]=relearningsteps1r;
        goodre[1]=relearningsteps2r;
        goodre[2]=relearningsteps3r;
        goodre[3]=relearningsteps4r;
        goodre[4]=relearningsteps5r;

        basicsettinggood(whengood1,whengood1r,0);
        basicsettinggood(whengood2,whengood2r,1);
        basicsettinggood(whengood3,whengood3r,2);
        basicsettinggood(whengood4,whengood4r,3);
        basicsettinggood(whengood5,whengood5r,4);

        basicsettinggoodre(relearningsteps1,relearningsteps1r,0);
        basicsettinggoodre(relearningsteps2,relearningsteps2r,1);
        basicsettinggoodre(relearningsteps3,relearningsteps3r,2);
        basicsettinggoodre(relearningsteps4,relearningsteps4r,3);
        basicsettinggoodre(relearningsteps5,relearningsteps5r,4);

        basicsetting(whenagain,whenagainr);
        basicsetting(graduating,graduatingr);
        basicsetting(easyinterval,easyintervalr);
        basicsetting(startingease,startingeaser);
        basicsetting(easybonus,easybonusr);
        basicsetting(intervalmodifier,intervalmodifierr);
        basicsetting(maximuminterval,maximumintervalr);
        basicsetting(whenreagain,whenreagainr);
        basicsetting(relearningintervalpercent,relearningintervalpercentr);
        basicsetting(relearningminimum,relearningminimumr);
        zoombar.setProgress((int)(sf.getFloat("zoom",(float) 1.0)*10));
        zoombar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                SharedPreferences sharedPreferences=getSharedPreferences(spName,MODE_PRIVATE);
                SharedPreferences.Editor ed=sharedPreferences.edit();
                ed.putFloat("zoom",(float) progress/10);
                ed.apply();
                zoom.setText(progress/10+"."+progress%10);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        save();
    }


    private void basicsetting(EditText editText, Integer basic){
        editText.setText(basic.toString());
    }

    private void basicsettinggood(EditText editText, Integer basic, int i){
        SharedPreferences sf=getSharedPreferences(spName,MODE_PRIVATE);
        if (editText==whengood1){
            editText.setText(basic.toString());
        }else{
            if (basic==0||sf.getBoolean(keys[i],false)){
                editText.setText("");
            }else{
                editText.setText(basic.toString());
            }
        }
    }

    private void basicsettinggoodre(EditText editText, Integer basic, int i){
        SharedPreferences sf=getSharedPreferences(spName,MODE_PRIVATE);
        if (editText==relearningsteps1){
            editText.setText(basic.toString());
        }else{
            if (basic==0||sf.getBoolean(keys2[i],false)){
                editText.setText("");
            }else{
                editText.setText(basic.toString());
            }
        }
    }


    private Integer putin(EditText editText){
            return Integer.parseInt(editText.getText().toString());
    }
    private Float putfloat(EditText editText){
        return Float.parseFloat(editText.getText().toString());
    }

    private void changelistner(String key ,EditText editText){
        SharedPreferences sharedPreferences = getSharedPreferences(spName,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        TextWatcher tw=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText.getText().length()==0){
                    editor.remove(key);
                }else {
                    editor.putInt(key,putin(editText));

                }
                editor.apply();
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        };
        editText.addTextChangedListener(tw);
    }

    private void good1(EditText editText,int i){
        SharedPreferences sharedPreferences = getSharedPreferences(spName,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (editText.getText().toString().isEmpty()){
            good[i]=0;
            editor.putBoolean(keys[i],true);
        }else if (editText.getText().toString().length()!=0){
            good[i]=putin(editText);
            editor.putBoolean(keys[i],false);
        }

    }
    private void good2(EditText editText,int i){
        SharedPreferences sharedPreferences = getSharedPreferences(spName,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (editText.getText().toString().isEmpty()){
            goodre[i]=0;
            editor.putBoolean(keys2[i],true);
        }else if (editText.getText().toString().length()!=0){
            goodre[i]=putin(editText);
            editor.putBoolean(keys2[i],false);
        }

    }

    private void save(){
        SharedPreferences sharedPreferences = getSharedPreferences(spName,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        good1(whengood1,0);
        good1(whengood2,1);
        good1(whengood3,2);
        good1(whengood4,3);
        good1(whengood5,4);
        good2(relearningsteps1,0);
        good2(relearningsteps2,1);
        good2(relearningsteps3,2);
        good2(relearningsteps4,3);
        good2(relearningsteps5,4);

        for (int l=0;l<5;l++){
            if (good[l]!=0){
                list.add(good[l]);
            }
        }

        for (int g=0; g<list.size();g++){
            good[g]=list.get(g);
        }

        if (list.size()>4){

        }else{
            for (int k=list.size(); k<5;k++){
                good[k]=0;
            }
        }




        for (int l=0;l<5;l++){
            if (goodre[l]!=0){
                listre.add(goodre[l]);
            }
        }

        for (int g=0; g<listre.size();g++){
            goodre[g]=listre.get(g);
        }

        if (listre.size()>4){

        }else{
            for (int k=listre.size(); k<5;k++){
                goodre[k]=0;
            }
        }





        if (good[0]==0){
            editor.putBoolean("isempty1",true);
        }else if (good[0]!=0){
            editor.putBoolean("isempty1",false);
        }

        if (good[1]==0){
            editor.putBoolean("isempty2",true);
        }else if (good[1]!=0){
            editor.putBoolean("isempty2",false);
        }

        if (good[2]==0){
            editor.putBoolean("isempty3",true);
        }else if (good[2]!=0){
            editor.putBoolean("isempty3",false);
        }

        if (good[3]==0){
            editor.putBoolean("isempty4",true);
        }else if (good[3]!=0){
            editor.putBoolean("isempty4",false);
        }

        if (good[4]==0){
            editor.putBoolean("isempty5",true);
        }else if (good[4]!=0){
            editor.putBoolean("isempty5",false);
        }

        if (good[0]==0&&good[1]==0&&good[2]==0&&good[3]==0&&good[4]==0){
            good[0]=1440;
            editor.putBoolean("isempty1",true);
        }



        if (goodre[0]==0){
            editor.putBoolean("reempty1",true);
        }else if (goodre[0]!=0){
            editor.putBoolean("reempty1",false);
        }

        if (goodre[1]==0){
            editor.putBoolean("reempty2",true);
        }else if (goodre[1]!=0){
            editor.putBoolean("reempty2",false);
        }

        if (goodre[2]==0){
            editor.putBoolean("reempty3",true);
        }else if (goodre[2]!=0){
            editor.putBoolean("reempty3",false);
        }

        if (goodre[3]==0){
            editor.putBoolean("reempty4",true);
        }else if (goodre[3]!=0){
            editor.putBoolean("reempty4",false);
        }

        if (goodre[4]==0){
            editor.putBoolean("reempty5",true);
        }else if (goodre[4]!=0){
            editor.putBoolean("reempty5",false);
        }

        if (goodre[0]==0&&goodre[1]==0&&goodre[2]==0&&goodre[3]==0&&goodre[4]==0){
            goodre[0]=1440;
            editor.putBoolean("relearningsteps1",true);
        }


        editor.putInt("whengood1",good[0]);
        editor.putInt("whengood2",good[1]);
        editor.putInt("whengood3",good[2]);
        editor.putInt("whengood4",good[3]);
        editor.putInt("whengood5",good[4]);

        editor.putInt("relearningsteps1",goodre[0]);
        editor.putInt("relearningsteps2",goodre[1]);
        editor.putInt("relearningsteps3",goodre[2]);
        editor.putInt("relearningsteps4",goodre[3]);
        editor.putInt("relearningsteps5",goodre[4]);
        editor.apply();

        list.clear();
        listre.clear();
    }

    private void lengthLimit(EditText editText){
        editText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(Integer.MAX_VALUE)});
    }



}

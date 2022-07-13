package com.first.Anki_blank.show_time;

import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

public class Show_easy extends AppCompatActivity{
    int isgraduated,timeadd,pickedinterval,maximuminterval,ef;
    SharedPreferences setoption;

    public void Show_easy(int isgraduated,int timeadd,int pickedinterval,int maximuminterval,int ef, SharedPreferences setoption){
        this.isgraduated=isgraduated;
        this.timeadd=timeadd;
        this.pickedinterval=pickedinterval;
        this.maximuminterval=maximuminterval;
        this.ef=ef;
        this.setoption=setoption;
    }

    public int gettime(){
        int time=this.timeadd;
        return time;
    }

    public void whenEasy(){
        if (isgraduated<7){
            timeadd=(setoption.getInt("easyinterval",4)*1440);
        }else if(isgraduated>6&&isgraduated<14){
            pickedinterval= (int) (pickedinterval*((float)ef/100)*((float)setoption.getInt("intervalmodifier",100)/100)*((float)setoption.getInt("easybonus",130)/100));
            if (pickedinterval>maximuminterval){
                pickedinterval=maximuminterval;
            }
            timeadd=pickedinterval;
        }
    }
}

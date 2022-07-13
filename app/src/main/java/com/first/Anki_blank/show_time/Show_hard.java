package com.first.Anki_blank.show_time;

import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

public class Show_hard extends AppCompatActivity {
    int timeadd,pickedinterval,earlystandard,maximuminterval;
    SharedPreferences setoption;

    public void Show_hard(int timeadd, int pickedinterval, int earlystandard, int maximuminterval, SharedPreferences setoption){
        this.timeadd=timeadd;
        this.pickedinterval=pickedinterval;
        this.earlystandard=earlystandard;
        this.maximuminterval=maximuminterval;
        this.setoption=setoption;
    }

    public int gettime(){
        int time=this.timeadd;
        return time;
    }

    public void whenHard(){
        timeadd= (int) (pickedinterval*(1.2)*((float)setoption.getInt("intervalmodifier",100)/100));
        if (timeadd<=earlystandard){
            timeadd=0;
        }else{
            if(timeadd>maximuminterval){
                timeadd=maximuminterval;
            }
        }
    }
}

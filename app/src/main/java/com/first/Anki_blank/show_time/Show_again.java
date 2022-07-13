package com.first.Anki_blank.show_time;

import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

public class Show_again extends AppCompatActivity {
    int isgraduated,pickedinterval,timeadd,ef,earlystandard;
    SharedPreferences setoption;
    public void Show_again(int isgraduated, int pickedinterval, int timeadd, int ef, int earlystandard,SharedPreferences setoption){
        this.isgraduated=isgraduated;
        this.pickedinterval=pickedinterval;
        this.timeadd=timeadd;
        this.ef=ef;
        this.earlystandard=earlystandard;
        this.setoption=setoption;

    }
    public int gettime(){
        int time=this.timeadd;
        return time;
    }
    public void whenAgain(){
        if (isgraduated==13){
            pickedinterval= (int) (pickedinterval*((float)setoption.getInt("relearningintervalpercent",100)/100));

            if (setoption.getInt("whenreagain",0)<=earlystandard){
                timeadd=0;
            }else {
                timeadd=setoption.getInt("whenreagain",0);
            }
            isgraduated=7;

            if (ef-20<130){
                ef=130;
            }else{
                ef=ef-20;
            }

        }else if (isgraduated<7){

            if (setoption.getInt("whenagain",0)<=earlystandard){
                timeadd=0;
            }else{
                timeadd=setoption.getInt("whenagain",0);
            }
            isgraduated=1;
        }else if (6<isgraduated&&isgraduated<13){

            if (setoption.getInt("whenreagain",0)<=earlystandard){
                timeadd=0;
            }else{
                timeadd=setoption.getInt("whenreagain",0);
            }
            isgraduated=7;

        }
    }
}

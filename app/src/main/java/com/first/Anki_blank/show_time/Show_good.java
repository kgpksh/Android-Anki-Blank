package com.first.Anki_blank.show_time;

import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Show_good extends AppCompatActivity {
    ArrayList<Integer> newcard;
    ArrayList<Integer> relearning;
    SharedPreferences setoption;
    int isgraduated,earlystandard,timeadd,maximuminterval,pickedinterval,ef;

    public void Show_good(int isgraduated,int earlystandard,int timeadd, int maximuminterval,int pickedinterval,int ef, ArrayList<Integer> newcard, ArrayList<Integer> relearning,SharedPreferences setoption){
        this.isgraduated=isgraduated;
        this.earlystandard=earlystandard;
        this.timeadd=timeadd;
        this.maximuminterval=maximuminterval;
        this.pickedinterval=pickedinterval;
        this.ef=ef;
        this.newcard=newcard;
        this.relearning=relearning;
        this.setoption=setoption;
    }

    public int gettime(){
        int time=this.timeadd;
        return time;
    }

    public void whenGood(){
        for (int i=1; i<6; i++){
            if (i==1){
                newcard.add(setoption.getInt("whengood"+i,10));
            }else{
                newcard.add(setoption.getInt("whengood"+i,0));
            }
        }

        for (int i=1; i<6; i++){
            if (i==1){
                relearning.add(setoption.getInt("relearningsteps"+i,15));
            }else{
                relearning.add(setoption.getInt("relearningsteps"+i,0));
            }
        }


        if (isgraduated<5){

            if (newcard.get(isgraduated)!=0){
                if (newcard.get(isgraduated-1)<=earlystandard){
                    timeadd=0;
                }else{
                    timeadd=newcard.get(isgraduated-1);
                }
                isgraduated=isgraduated+1;
            }else{
                if (newcard.get(isgraduated-1)<=earlystandard){
                    timeadd=0;
                }else{
                    timeadd=newcard.get(isgraduated-1);
                }
                isgraduated=6;
            }


        }else if (isgraduated==5){
            if (newcard.get(isgraduated-1)<=earlystandard){
                timeadd=0;
            }else{
                timeadd=newcard.get(isgraduated-1);
            }

            isgraduated=6;

        }else if(isgraduated==6){

            if ((setoption.getInt("graduating",1)*1440)<=earlystandard){
                timeadd=0;
            }else{
                timeadd=(int) (setoption.getInt("graduating",1)*1440*((float)setoption.getInt("intervalmodifier",100)/100));
            }

            isgraduated=13;

        }else if (isgraduated>6&&isgraduated<11){

            if (relearning.get(isgraduated-6)!=0){

                if (relearning.get(isgraduated-7)<=earlystandard){
                    timeadd=0;
                }else{
                    if (relearning.get(isgraduated-7)>maximuminterval){
                        timeadd=maximuminterval;
                    }else{
                        timeadd=relearning.get(isgraduated-7);
                    }
                }

                isgraduated=isgraduated+1;
            }else{

                if (relearning.get(isgraduated-7)<=earlystandard){
                    timeadd=0;
                }else{
                    if (relearning.get(isgraduated-7)>maximuminterval){
                        timeadd=maximuminterval;
                    }else{
                        timeadd=relearning.get(isgraduated-7);
                    }
                }

                isgraduated=12;
            }

        }else if (isgraduated==11){

            if (relearning.get(isgraduated-7)<=earlystandard){
                timeadd=0;
            }else{
                if (relearning.get(isgraduated)>maximuminterval){
                    timeadd=maximuminterval;
                }else{
                    timeadd=relearning.get(isgraduated-7);
                }
            }

            isgraduated=12;

        }else if(isgraduated==12){
            pickedinterval= (int) (pickedinterval*((float)setoption.getInt("intervalmodifier",100)/100));
            if (pickedinterval<=earlystandard){
                timeadd=0;
            }else{
                if (pickedinterval>maximuminterval){
                    pickedinterval=maximuminterval;
                    timeadd=maximuminterval;
                }else{
                    timeadd=pickedinterval;
                }
            }
            isgraduated=13;
        }else if (isgraduated==13){
            pickedinterval= (int) (pickedinterval*((float)ef/100)*((float)setoption.getInt("intervalmodifier",100)/100));
            if (pickedinterval<=earlystandard){
                timeadd=0;
            }else{
                if (pickedinterval>maximuminterval){
                    pickedinterval=maximuminterval;
                    timeadd=maximuminterval;
                }else{
                    timeadd=pickedinterval;
                }
            }
            isgraduated=13;
        }
    }
}

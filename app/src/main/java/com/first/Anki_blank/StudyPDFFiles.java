package com.first.Anki_blank;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.first.Anki_blank.Alarm.Alarm_Reciver;
import com.first.Anki_blank.Alarm.NotificationHelper;
import com.first.Anki_blank.show_time.Show_again;
import com.first.Anki_blank.show_time.Show_easy;
import com.first.Anki_blank.show_time.Show_good;
import com.first.Anki_blank.show_time.Show_hard;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.util.FitPolicy;


import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;;
import java.util.Random;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;


public class StudyPDFFiles extends AppCompatActivity{
    PDFView pdfView;
    String picker,multi_pick_list,picked_name,recoveredFilepath,first_new,first_old,last_picker;
    Paint normalInner, normalBorder,pointedInner,pointedBorder;
    ScrollView touch;
    float coordi[][];
    SQLiteHelper helper;
    SQLiteControl sqlite;
    int j,borderColor,innerColor,pointedInnercolor,pointedBordercolor, pointedId,pointedPage,ef,isgraduated,pickedinterval,ran, earlystandard,timestandard,mixstandard,maximuminterval;
    SQLiteDatabase sl;
    boolean flip, clear,isborderon,ispointedBorderon,repick;
    int[] idcounter;
    ArrayList<Integer> picked_id;
    File pickedfile;
    SharedPreferences General,Mother;
    SharedPreferences.Editor GE;
    LinearLayout learningtray,fourbutton,ending,all_cover;
    Button showanswer,getbacktoMainpage;
    HashMap<String,String> getMother;
    ArrayList<String> multi_learn;
    Random random;
    FrameLayout maincontainer;
    String picked_date;
    SharedPreferences setoption;
    Calendar calendar,calendar2,fornoti;
    SimpleDateFormat mindateFormat,hourdateFormat,daydateFormat;
    Date date;
    ArrayList<Integer> newcard;
    ArrayList<Integer> relearning;
    TouchAccept touchAccept;
    boolean zoom_block_solve,isstart,issearch_complete;
    int timeadd;
    LinearLayout again,hard,good,easy;
    TextView againtime,hardtime,goodtime,easytime;
    TimeZone tz;
    AlarmManager alarmManager;
    RXJava_sqlite rxJava_sqlite;
//    AD ad=new AD();
    boolean ispurchaged,isonlyshow;


    @Override
    protected void onDestroy() {
        super.onDestroy();

        rxJava_sqlite.start(sqlite,alarmManager,getApplicationContext());
        //Intent gobackMain=new Intent(this,MainActivity.class);
        //startActivity(gobackMain);

        /*if (General.getBoolean("notification",true)==true){
            startAlarm();
        }*/

        sl.close();
        helper.close();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_p_d_f_files);
        pdfView=(PDFView) findViewById(R.id.pdfView);
        learningtray=findViewById(R.id.learningtray);
        touch=findViewById(R.id.Touchlistener);
        again=findViewById(R.id.again);
        hard=findViewById(R.id.hard);
        good=findViewById(R.id.good);
        easy=findViewById(R.id.easy);
        touchAccept=findViewById(R.id.empty);
        showanswer=findViewById(R.id.show_answer);
        fourbutton=findViewById(R.id.four_button_tray);
        maincontainer=findViewById(R.id.maincontainer);
        ending=findViewById(R.id.ending);
        getbacktoMainpage=findViewById(R.id.backtomain);
        all_cover=findViewById(R.id.Linear);
        againtime=findViewById(R.id.againtime);
        hardtime=findViewById(R.id.hardtime);
        goodtime=findViewById(R.id.goodtime);
        easytime=findViewById(R.id.easytime);
        touch.setVisibility(View.GONE);

        touchAccept.setVisibility(View.GONE);
        flip=true;
        repick=false;
        newcard=new ArrayList<>();
        relearning=new ArrayList<>();

        General=getSharedPreferences("General",MODE_PRIVATE);
        GE=General.edit();
        Mother=getSharedPreferences("Mother",MODE_PRIVATE);
        innerColor=Color.parseColor(General.getString("innercolor","#03A9F4"));
        borderColor=Color.parseColor(General.getString("bordercolor","#3F51B5"));
        pointedInnercolor=Color.parseColor(General.getString("pointedinner","#FF9800"));
        pointedBordercolor=Color.parseColor(General.getString("pointedborder","#33D143"));
        isborderon=General.getBoolean("borderon",true);
        ispointedBorderon=General.getBoolean("pointedBorderon",true);
        earlystandard=General.getInt("learn_ahead",0);
        timestandard=General.getInt("timeStandard",0);
        mixstandard=General.getInt("mixStandard",0);
        isonlyshow=General.getBoolean("onlyshow",false);

        getMother=new HashMap<>();
        normalInner =new Paint();
        normalBorder =new Paint();
        pointedInner =new Paint();
        pointedBorder =new Paint();

        normalInner.setColor(innerColor);

        normalBorder.setStyle(Paint.Style.STROKE);
        normalBorder.setColor(borderColor);
        normalBorder.setStrokeWidth(7);

        pointedInner.setColor(pointedInnercolor);

        pointedBorder.setStyle(Paint.Style.STROKE);
        pointedBorder.setColor(pointedBordercolor);
        pointedBorder.setStrokeWidth(7);

        issearch_complete=false;

        Subscribe subscribe=new Subscribe(StudyPDFFiles.this);
        //Subscribing subscribing=new Subscribing();
        //subscribing.setupBillingClient(StudyPDFFiles.this,StudyPDFFiles.this);
        subscribe.justcheckSubscription(StudyPDFFiles.this);
        ispurchaged= General.getBoolean("Subscribing2",false);

        blink();

        multi_learn=new ArrayList<>();
        picked_id=new ArrayList<>();

        sqlite=new SQLiteControl(StudyPDFFiles.this);
        helper=sqlite.helper;

        rxJava_sqlite=new RXJava_sqlite();
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        NotificationHelper notificationHelper=new NotificationHelper(this);
        notificationHelper.cancelNotification();
        cancelAlarm();

        sl=helper.getReadableDatabase();
        tz=TimeZone.getTimeZone("Europe/London");
        calendar=Calendar.getInstance(tz);
        mindateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault());

        hourdateFormat=new  SimpleDateFormat("yyyy-MM-dd HH", java.util.Locale.getDefault());
        daydateFormat=new  SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
        first_new=" and "+helper.isgraduated+" <'"+7+"'";
        first_old=" and "+helper.isgraduated+" >'"+6+"'";

        basic_pick_string();

        getbacktoMainpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (mixstandard==0){
            last_picker=picker;
        }else if (mixstandard==1){
            last_picker=picker+first_new;
        }else if (mixstandard==2){
            last_picker=picker+first_old;
        }
        idselect();
        if (picked_id.size()!=0){

            if (ispurchaged==false){
                //Adcall();
            }
            pickup();
            setoption=getSharedPreferences(getMother.get(recoveredFilepath),MODE_PRIVATE);
            maximuminterval=setoption.getInt("maximuminterval",36500)*1440;
            learningtray.setVisibility(View.VISIBLE);
            showanswer.setVisibility(View.VISIBLE);
            displayPDF(pickedfile);
        }else{
            issearch_complete=true;
            if (mixstandard==0){
                last_picker=picker;
            }else if (mixstandard==1){
                last_picker=picker+first_old;
            }else if (mixstandard==2){
                last_picker=picker+first_new;
            }
            idselect();

            if (picked_id.size()==0){
                maincontainer.setVisibility(View.GONE);
                ending.setVisibility(View.VISIBLE);
            }else{
                if (ispurchaged==false){
                    //Adcall();
                }
                pickup();
                setoption=getSharedPreferences(getMother.get(recoveredFilepath),MODE_PRIVATE);
                maximuminterval=setoption.getInt("maximuminterval",36500)*1440;
                learningtray.setVisibility(View.VISIBLE);
                showanswer.setVisibility(View.VISIBLE);
                displayPDF(pickedfile);
            }
        }

        Show_again show_again=new Show_again();
        Show_hard show_hard=new Show_hard();
        Show_good show_good=new Show_good();
        Show_easy show_easy=new Show_easy();
        
        showanswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showanswer.setVisibility(View.GONE);
                fourbutton.setVisibility(View.VISIBLE);
                if (isgraduated<13){
                    hard.setVisibility(View.GONE);
                }

                show_again.Show_again(isgraduated,pickedinterval,timeadd,ef,earlystandard,setoption);
                show_again.whenAgain();

                day_checker(show_again.gettime(),againtime);

                show_hard.Show_hard(timeadd,pickedinterval,earlystandard,maximuminterval,setoption);
                show_hard.whenHard();
                day_checker(show_hard.gettime(),hardtime);

                show_good.Show_good(isgraduated,earlystandard,timeadd,maximuminterval,pickedinterval,ef,newcard,relearning,setoption);
                show_good.whenGood();
                day_checker(show_good.gettime(),goodtime);

                show_easy.Show_easy(isgraduated,timeadd,pickedinterval,maximuminterval,ef,setoption);
                show_easy.whenEasy();
                day_checker(show_easy.gettime(),easytime);
                flip=false;
                pdfView.invalidate();
            }
        });



        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whenAgain();
                timechange(timeadd);
                afterfour();
            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whenHard();
                timechange(timeadd);

                if (ef-15<130){
                    ef=130;
                }else{
                    ef=ef-15;
                }
                afterfour();
            }
        });

        good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whenGood();
                timechange(timeadd);
                afterfour();
                newcard.clear();
                relearning.clear();
            }
        });

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whenEasy();
                timechange(timeadd);

                if (isgraduated==13){
                    ef=ef+15;
                }
                isgraduated=13;

                afterfour();
            }
        });
    }

    @Override
    public void onConfigurationChanged(@NonNull @NotNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        pdfView.recycle();
        displayPDF(pickedfile);
    }

    private void displayPDF(File file){
        zoom_block_solve =true;
        pdfView.fromFile(file)
                .pages(pointedPage)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableAnnotationRendering(false)
                .onDraw(new OnDrawListener() {
                    @Override
                    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {
                        sl=helper.getReadableDatabase();
                        String sql="select "+helper.ltCornerx+","+helper.ltCornery+","+helper.rbCornerx+","+helper.rbCornery+","+helper.id+" from "+ helper.TABLE_NAME+" where "+helper.pdfname+" = '"+picked_name+"' and "+helper.pagenum+" ='"+pointedPage+"'";
                        Cursor c=sl.rawQuery(sql,null);
                        coordi=new float[c.getCount()][5];
                        idcounter=new int[c.getCount()];
                        j=0;
                        if (!clear){
                            while(c.moveToNext()){
                                for (int i=0; i<4; i++){
                                    coordi[j][i]=c.getFloat(i);
                                }
                                coordi[j][4]=c.getInt(4);

                                if (coordi[j][4]==pointedId){
                                    if (flip){
                                        canvas.drawRect(coordi[j][0]*pageWidth,coordi[j][1]*pageHeight,coordi[j][2]*pageWidth,coordi[j][3]*pageHeight, pointedInner);
                                        if (ispointedBorderon){
                                            canvas.drawRect(coordi[j][0]*pageWidth,coordi[j][1]*pageHeight,coordi[j][2]*pageWidth,coordi[j][3]*pageHeight, pointedBorder);
                                        }

                                        /*if (zoom_block_solve){
                                            float zoom=setoption.getFloat("zoom",(float)1.0);
                                            PointF center_zoom=new PointF((coordi[j][0]+coordi[j][2])*pageWidth/2,(coordi[j][1]+coordi[j][3])*pageHeight/2);
                                            Toast.makeText(getApplicationContext(),center_zoom.x+"+"+center_zoom.y,Toast.LENGTH_SHORT).show();
                                            pdfView.zoomCenteredTo(zoom,center_zoom);

                                            zoom_block_solve =false;
                                        }*/
                                    }
                                }else{
                                    if (!isonlyshow){
                                        canvas.drawRect(coordi[j][0]*pageWidth,coordi[j][1]*pageHeight,coordi[j][2]*pageWidth,coordi[j][3]*pageHeight, normalInner);

                                        if (isborderon){
                                            canvas.drawRect(coordi[j][0]*pageWidth,coordi[j][1]*pageHeight,coordi[j][2]*pageWidth,coordi[j][3]*pageHeight, normalBorder);
                                        }
                                    }

                                }
                                j++;
                            }
                        }
                        c.close();
                    }
                })
                .pageSnap(true)
                .autoSpacing(true)
                .pageFitPolicy(FitPolicy.BOTH)
                .load();

    }


    private String getreplaced(String filepath){
        String getback=new String();
        if (filepath.contains(getApplicationContext().getFilesDir().getAbsolutePath()+"/"+"Basic folder")){
            getback=filepath.replaceFirst(getApplicationContext().getFilesDir().getAbsolutePath()+"/"+"Basic folder","a");
        }else if(filepath.contains(getApplicationContext().getExternalFilesDir(null).getParent()+"/"+"Premium folder")){
            getback=filepath.replaceFirst(getApplicationContext().getExternalFilesDir(null).getParent()+"/"+"Premium folder","b");
        }
        return getback;
    }

    private void filepathRecover(){
        if (picked_name.indexOf("a")==0){
            recoveredFilepath=picked_name.replaceFirst("a",getApplicationContext().getFilesDir().getAbsolutePath()+"/"+"Basic folder");
        }else if (picked_name.indexOf("b")==0){
            recoveredFilepath=picked_name.replaceFirst("b",getApplicationContext().getExternalFilesDir(null).getParent()+"/"+"Premium folder");
        }
    }


    private void pickup(){
        if (picked_id.size()!=0){
            random=new Random();
            sl=helper.getReadableDatabase();
            ran=random.nextInt(picked_id.size());
            pointedId=picked_id.get(ran);
            String toPickname="select "+helper.pdfname+","+helper.pagenum+","+helper.ef+","+helper.isgraduated+","+helper.date+","+helper.interval+" from "+ helper.TABLE_NAME+" where "+helper.id+" = '"+pointedId+"'";
            Cursor c=sl.rawQuery(toPickname,null);
            while (c.moveToNext()){
                picked_name=c.getString(0);
                pointedPage=c.getInt(1);
                ef=c.getInt(2);
                isgraduated=c.getInt(3);
                picked_date=c.getString(4);
                pickedinterval=c.getInt(5);
            }
            c.close();
            filepathRecover();
            pickedfile =new File(recoveredFilepath);
        }
    }

    private void afterfour() {
        sqlite.reinsert(sl,helper,pointedId, ef, isgraduated, picked_date, pickedinterval);
        picked_id.remove(ran);

        afterparts();
    }

    private void afterparts(){
        calendar=Calendar.getInstance(tz);
        calendar2=Calendar.getInstance(tz);
        if (picked_id.size() != 0) {
            fourbutton.setVisibility(View.GONE);
            showanswer.setVisibility(View.VISIBLE);
            pickup();
            setoption=getSharedPreferences(getMother.get(recoveredFilepath),MODE_PRIVATE);
            maximuminterval=setoption.getInt("maximuminterval",36500)*1440;
            hard.setVisibility(View.VISIBLE);
            flip = true;
            pdfView.recycle();
            displayPDF(pickedfile);
        }else{

            basic_pick_string();
            if (issearch_complete==false){
                if (mixstandard==0){
                    last_picker=picker;
                }else if (mixstandard==1){
                    last_picker=picker+first_new;
                }else if (mixstandard==2){
                    last_picker=picker+first_old;
                }
            }

            idselect();
            if (picked_id.size()==0){
                issearch_complete=true;
                if (mixstandard==0){
                    last_picker=picker;
                }else if (mixstandard==1){
                    last_picker=picker+first_old;
                }else if (mixstandard==2){
                    last_picker=picker+first_new;
                }

                idselect();

                if (picked_id.size() == 0) {
                    //ad.showad(StudyPDFFiles.this);
                    maincontainer.setVisibility(View.GONE);
                    learningtray.setVisibility(View.GONE);
                    ending.setVisibility(View.VISIBLE);
                } else {
                    afterparts();
                }
            }else{
                afterparts();
            }
        }
    }

    private void idselect(){
        sl = helper.getReadableDatabase();
        calendar2=Calendar.getInstance(tz);
        Cursor c=sl.rawQuery(last_picker,null);
        while (c.moveToNext()){
            if (timestandard==0){
                picked_id.add(c.getInt(0));
            }else if (timestandard==1){
                try {
                    date=hourdateFormat.parse(c.getString(1));
                    calendar2.setTime(date);
                    calendar2.add(Calendar.HOUR,1);
                    if (calendar.compareTo(calendar2)>-1){
                        picked_id.add(c.getInt(0));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }else if (timestandard==2){
                try {
                    date=daydateFormat.parse(c.getString(1));
                    calendar2.setTime(date);
                    calendar2.add(Calendar.DATE,1);
                    if (calendar.compareTo(calendar2)>-1){
                        picked_id.add(c.getInt(0));

                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        c.close();
        for (int i=0; i<picked_id.size(); i++){
        }

    }

    private void timechange(int addedtime){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        fornoti=Calendar.getInstance(tz);
        fornoti.add(Calendar.MINUTE,addedtime);
        picked_date=simpleDateFormat.format(fornoti.getTime());
    }

    private void blink(){
        isstart=false;
        TimerTask on = new TimerTask() {
            public void run() {
                    if (isstart){
                        pointedBorder.setColor(borderColor);
                        pointedInner.setColor(innerColor);
                        pdfView.invalidate();
                        isstart=false;
                    }else{
                        pointedBorder.setColor(pointedBordercolor);
                        pointedInner.setColor(pointedInnercolor);
                        pdfView.invalidate();
                        isstart=true;
                    }
            }
        };
        Timer timer = new Timer();
        timer.schedule(on, 0, 500);

    }


    private void whenAgain(){
        if (isgraduated==13){

            if (ef-20<130){
                ef=130;
            }else{
                ef=ef-20;
            }

            pickedinterval= (int) (pickedinterval*((float)setoption.getInt("relearningintervalpercent",100)/100));

            if (setoption.getInt("whenreagain",0)==0){
                if (timestandard==0){
                    timeadd=0;
                }else if (timestandard==1){
                    timeadd=-61;
                }else if (timestandard==2){
                    timeadd=-1441;
                }
            }else{
                if (setoption.getInt("whenreagain",0)<=earlystandard){
                    timeadd=0;
                }else {
                    timeadd=setoption.getInt("whenreagain",0);
                }
            }

            isgraduated=7;



        }else if (isgraduated<7){

            if (setoption.getInt("whenagain",0)==0){
                if (timestandard==0){
                    timeadd=0;
                }else if (timestandard==1){
                    timeadd=-61;
                }else if (timestandard==2){
                    timeadd=-1441;
                }
            }else{
                if (setoption.getInt("whenagain",0)<=earlystandard){
                    timeadd=0;
                }else {
                    timeadd=setoption.getInt("whenagain",0);
                }
            }

            isgraduated=1;
        }else if (6<isgraduated&&isgraduated<13){

            if (setoption.getInt("whenreagain",0)==0){
                if (timestandard==0){
                    timeadd=0;
                }else if (timestandard==1){
                    timeadd=-61;
                }else if (timestandard==2){
                    timeadd=-1441;
                }
            }else{
                if (setoption.getInt("whenreagain",0)<=earlystandard){
                    timeadd=0;
                }else {
                    timeadd=setoption.getInt("whenreagain",0);
                }
            }

            isgraduated=7;

        }
    }

    private void whenHard(){
        timeadd= (int) (pickedinterval*(1.2)*((float)setoption.getInt("intervalmodifier",100)/100));
        if (timeadd==0){
            if (timestandard==0){
                timeadd=0;
            }else if (timestandard==1){
                timeadd=-61;
            }else if (timestandard==2){
                timeadd=-1441;
            }
        }else{
            if (timeadd<=earlystandard){
                timeadd=0;
            }else{
                if(timeadd>maximuminterval){
                    timeadd=maximuminterval;
                }
            }
        }

    }

    private void whenGood(){
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
                if (newcard.get(isgraduated-1)==0){
                    if (timestandard==0){
                        timeadd=0;
                    }else if (timestandard==1){
                        timeadd=-61;
                    }else if (timestandard==2){
                        timeadd=-1441;
                    }
                }else{
                    if (newcard.get(isgraduated-1)<=earlystandard){
                        timeadd=0;
                    }else{
                        timeadd=newcard.get(isgraduated-1);
                    }
                }

                isgraduated=isgraduated+1;
            }else{
                if (newcard.get(isgraduated-1)==0){
                    if (timestandard==0){
                        timeadd=0;
                    }else if (timestandard==1){
                        timeadd=-61;
                    }else if (timestandard==2){
                        timeadd=-1441;
                    }
                }else{
                    if (newcard.get(isgraduated-1)<=earlystandard){
                        timeadd=0;
                    }else{
                        timeadd=newcard.get(isgraduated-1);
                    }
                }

                isgraduated=6;
            }


        }else if (isgraduated==5){
            if (newcard.get(isgraduated-1)==0){
                if (timestandard==0){
                    timeadd=0;
                }else if (timestandard==1){
                    timeadd=-61;
                }else if (timestandard==2){
                    timeadd=-1441;
                }
            }else{
                if (newcard.get(isgraduated-1)<=earlystandard){
                    timeadd=0;
                }else{
                    timeadd=newcard.get(isgraduated-1);
                }
            }

            isgraduated=6;

        }else if(isgraduated==6){
            if ((setoption.getInt("graduating",1)*1440)==0){
                if (timestandard==0){
                    timeadd=0;
                }else if (timestandard==1){
                    timeadd=-61;
                }else if (timestandard==2){
                    timeadd=-1441;
                }
            }else{
                if ((setoption.getInt("graduating",1)*1440)<=earlystandard){
                    timeadd=0;
                }else{
                    timeadd=(int) (setoption.getInt("graduating",1)*1440*((float)setoption.getInt("intervalmodifier",100)/100));
                }
            }

            isgraduated=13;

        }else if (isgraduated>6&&isgraduated<11){

            if (relearning.get(isgraduated-6)!=0){
                if (relearning.get(isgraduated-7)==0){
                    if (timestandard==0){
                        timeadd=0;
                    }else if (timestandard==1){
                        timeadd=-61;
                    }else if (timestandard==2){
                        timeadd=-1441;
                    }
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
                }
                isgraduated=isgraduated+1;
            }else{
                if (relearning.get(isgraduated-7)==0){
                    if (timestandard==0){
                        timeadd=0;
                    }else if (timestandard==1){
                        timeadd=-61;
                    }else if (timestandard==2){
                        timeadd=-1441;
                    }
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
                }

                isgraduated=12;
            }

        }else if (isgraduated==11){

            if (relearning.get(isgraduated-7)==0){
                if (timestandard==0){
                    timeadd=0;
                }else if (timestandard==1){
                    timeadd=-61;
                }else if (timestandard==2){
                    timeadd=-1441;
                }
            }else{
                if (relearning.get(isgraduated-7)<=earlystandard){
                    timeadd=0;
                }else{
                    if (relearning.get(isgraduated)>maximuminterval){
                        timeadd=maximuminterval;
                    }else{
                        timeadd=relearning.get(isgraduated-7);
                    }
                }
            }
            isgraduated=12;

        }else if(isgraduated==12){
            pickedinterval= (int) (pickedinterval*((float)setoption.getInt("intervalmodifier",100)/100));
            if (pickedinterval==0){
                if (timestandard==0){
                    timeadd=0;
                }else if (timestandard==1){
                    timeadd=-61;
                }else if (timestandard==2){
                    timeadd=-1441;
                }
            }else{
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
            }
            isgraduated=13;
        }else if (isgraduated==13){
            pickedinterval= (int) (pickedinterval*((float)ef/100)*((float)setoption.getInt("intervalmodifier",100)/100));
            if (pickedinterval==0){
                if (timestandard==0){
                    timeadd=0;
                }else if (timestandard==1){
                    timeadd=-61;
                }else if (timestandard==2){
                    timeadd=-1441;
                }
            }else{
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
            }
            isgraduated=13;
        }
    }

    private void whenEasy(){
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

    private void day_checker(int given_minute,TextView textView){
        if (given_minute>1320&&given_minute<1440){
            textView.setText(1+" "+getResources().getString(R.string.A_day)+" "+getResources().getString(R.string.After));
        }else if (given_minute>=1440){

            if (given_minute%1440<=720){
                textView.setText(given_minute/1440+" "+getResources().getString(R.string.Days)+" "+getResources().getString(R.string.After));
            }else if (given_minute%1440>720){
                textView.setText(((given_minute/1440)+1)+" "+getResources().getString(R.string.Days)+" "+getResources().getString(R.string.After));
            }

        }else if (given_minute<=1320){
            hour_checker(given_minute,textView);
        }
    }

    private void hour_checker(int given_minute,TextView textView){
        if (given_minute>50&&given_minute<60){
            textView.setText(1+" "+getResources().getString(R.string.A_day)+" "+getResources().getString(R.string.After));
        }else if (given_minute>=60){

            if (given_minute%60<=30){
                textView.setText(given_minute/60+" "+getResources().getString(R.string.Hours)+" "+getResources().getString(R.string.After));
            }else if (given_minute%60>30){
                textView.setText(((given_minute/60)+1)+" "+getResources().getString(R.string.Hours)+" "+getResources().getString(R.string.After));
            }

        }else if (given_minute<=50){

            if (given_minute==1){
                textView.setText(1+" "+getResources().getString(R.string.A_minute)+" "+getResources().getString(R.string.After));
            }else{
                textView.setText(given_minute+" "+getResources().getString(R.string.Minutes)+" "+getResources().getString(R.string.After));
            }
        }
    }

    private void basic_pick_string(){
        if (getIntent().getBooleanExtra("onestudyS",false)){
            getMother.put(getIntent().getStringExtra("onestudy"),Mother.getString(getIntent().getStringExtra("onestudy"),"Default"));
            picker ="select "+helper.id+","+helper.date+" from "+ helper.TABLE_NAME+" where "+helper.date+" <= '"+mindateFormat.format(calendar.getTime())+"' and "+helper.pdfname+" ='"+getreplaced(getIntent().getStringExtra("onestudy"))+"'";
        }

        if(getIntent().getBooleanExtra("multi_studyS",false)){
            Default_option default_option=new Default_option();

            File multi=new File(getIntent().getStringExtra("multi_study"));

            for (File get : default_option.getfile(multi)){
                multi_learn.add(getreplaced(get.getAbsolutePath()));
                getMother.put(get.getAbsolutePath(),Mother.getString(get.getAbsolutePath(),"Default"));

                multi_pick_list="";
                for (int i=0; i<multi_learn.size(); i++){
                    if (i==multi_learn.size()-1){
                        multi_pick_list=multi_pick_list+"'"+multi_learn.get(i)+"'";
                    }else{
                        multi_pick_list=multi_pick_list+"'"+multi_learn.get(i)+"', ";
                    }
                }
            }

            picker ="select "+helper.id+","+helper.date+" from "+ helper.TABLE_NAME+" where "+helper.date+" <= '"+mindateFormat.format(calendar.getTime())+"' and "+helper.pdfname+" in ("+multi_pick_list+")";

        }

        if(getIntent().getBooleanExtra("all_studyS",false)){
            Default_option default_option=new Default_option();
            File basic=new File(getIntent().getStringExtra("all_study1"));
            File premium=new File(getIntent().getStringExtra("all_study2"));

            for (File get : default_option.getfile(basic)){
                multi_learn.add(getreplaced(get.getAbsolutePath()));
                getMother.put(get.getAbsolutePath(),Mother.getString(get.getAbsolutePath(),"Default"));
            }

            picker ="select "+helper.id+","+helper.date+" from "+ helper.TABLE_NAME+" where "+helper.date+" <= '"+mindateFormat.format(calendar.getTime())+"' and "+helper.pdfname+" like 'a%'";



            if (ispurchaged==true){
                for (File get : default_option.getfile(premium)){
                    multi_learn.add(getreplaced(get.getAbsolutePath()));
                    getMother.put(get.getAbsolutePath(),Mother.getString(get.getAbsolutePath(),"Default"));
                }
                picker ="select "+helper.id+","+helper.date+" from "+ helper.TABLE_NAME+" where "+helper.date+" <= '"+mindateFormat.format(calendar.getTime())+"'";
            }
        }
    }


    private void cancelAlarm(){
        Intent alarmintent = new Intent(this, Alarm_Reciver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, alarmintent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (pendingIntent!=null){
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }
    }

    /*private void Adcall(){
        Handler ADcall=new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                ADcall.post(new Runnable() {
                    @Override
                    public void run() {
                        ad.readyAD(getApplicationContext());
                    }
                });
            }
        }).start();
    }*/

    /*private void check(){
        pendingIntent = PendingIntent.getBroadcast(this, 1, alarmintent, PendingIntent.FLAG_NO_CREATE);

        if (pendingIntent==null){
            Toast.makeText(getApplicationContext(),"알람없음1",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),"알람있음1",Toast.LENGTH_SHORT).show();
        }

        cancelAlarm();

        if (pendingIntent==null){
            Toast.makeText(getApplicationContext(),"알람없음2",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),"알람있음2",Toast.LENGTH_SHORT).show();
        }
    }*/

    /*public void notiprocess() {
        backgroundTask = Observable.fromCallable(() -> {

            return true;

        })      .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((t) -> {
                    startAlarm();
                });
    }*/


}
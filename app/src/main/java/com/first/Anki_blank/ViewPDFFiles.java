package com.first.Anki_blank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnTapListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.FitPolicy;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Arrays;


public class ViewPDFFiles extends AppCompatActivity{
    PDFView pdfView;
    String replace;
    RadioGroup radioGroup;
    Paint normalInner, normalBorder;
    ScrollView touch;
    float startX,startY,endX,endY,pagew,pageh,deleteX,deleteY,lastx,lasty;
    float coordi[][];
    String DB_NAME;
    SQLiteHelper helper;
    SQLiteControl sqlite;
    int currentpage,j;
    SQLiteDatabase sl;
    boolean isdelete, clear,isborderon;
    int[] idcounter;

    int borderColor,innerColor;
    File read;
    SharedPreferences General,Mother,finder;
    LinearLayout buttontray;
    TouchAccept touchAccept;

    AlarmManager alarmManager;
    boolean is_something_changed;
    RXJava_sqlite rxJava_sqlite;


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (is_something_changed==true){
            rxJava_sqlite.start(sqlite,alarmManager,getApplicationContext());
        }
        /*if (General.getBoolean("notification",true)==true&&is_something_changed==true){
            NotificationHelper notificationHelper=new NotificationHelper(this);
            notificationHelper.cancelNotification();
            cancelAlarm();
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
        buttontray =findViewById(R.id.buttontray);
        radioGroup=findViewById(R.id.rdgroup);
        touch=findViewById(R.id.Touchlistener);
        touch.setVisibility(View.GONE);
        touchAccept=findViewById(R.id.empty);
        touchAccept.setVisibility(View.GONE);
        SharedPreferences sf=getSharedPreferences("pref",MODE_PRIVATE);
        General=getSharedPreferences("General",MODE_PRIVATE);
        Mother=getSharedPreferences("Mother",MODE_PRIVATE);

        borderColor=Color.parseColor(General.getString("bordercolor","#FF9800"));
        innerColor=Color.parseColor(General.getString("innercolor","#03A9F4"));
        isborderon=General.getBoolean("borderon",true);

        lastx=-1;
        lasty=-1;
        is_something_changed=false;

        normalInner =new Paint();
        normalBorder =new Paint();
        normalInner.setColor(innerColor);
        normalBorder.setStyle(Paint.Style.STROKE);
        normalBorder.setColor(borderColor);
        normalBorder.setStrokeWidth(7);

        getreplace(getIntent().getStringExtra("R_E"));
        read= new File(getIntent().getStringExtra("R_E"));
        buttontray.setVisibility(View.VISIBLE);
        String son= Mother.getString(getIntent().getStringExtra("R_E"),"Default");
        finder=getSharedPreferences(son,MODE_PRIVATE);
        int ef=finder.getInt("startingease",250);
        int interval=finder.getInt("graduating",1);


        sqlite=new SQLiteControl(ViewPDFFiles.this);
        helper=sqlite.helper;
        sl=helper.getWritableDatabase();

        rxJava_sqlite=new RXJava_sqlite();
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        DB_NAME=read.getName();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.scroll){
                    touch.setVisibility(View.GONE);
                    touchAccept.setVisibility(View.GONE);
                    isdelete=false;
                    clear=false;
                    pdfView.invalidate();
                }else if(checkedId==R.id.clear){
                    touch.setVisibility(View.GONE);
                    touchAccept.setVisibility(View.GONE);
                    isdelete=false;
                    clear=true;
                    pdfView.invalidate();
                }else if (checkedId==R.id.blank){
                    touch.setVisibility(View.VISIBLE);
                    touchAccept.setVisibility(View.VISIBLE);
                    isdelete=false;
                    clear=false;
                    pdfView.invalidate();
                }else if (checkedId==R.id.erase){
                    touch.setVisibility(View.GONE);
                    touchAccept.setVisibility(View.GONE);
                    isdelete=true;
                    clear=false;
                    pdfView.invalidate();
                }
            }
        });

        boolean ispurchased=General.getBoolean("Subscribing2",false);
        if (replace.substring(0,1).equals("b")&&ispurchased==false){
            RadioButton scroll,create,erase,hide;
            scroll=findViewById(R.id.scroll);
            create=findViewById(R.id.blank);
            erase=findViewById(R.id.erase);
            hide=findViewById(R.id.clear);
            scroll.setClickable(false);
            create.setClickable(false);
            erase.setClickable(false);

            hide.setChecked(true);
            touch.setVisibility(View.GONE);
            touchAccept.setVisibility(View.GONE);
            isdelete=false;
            clear=true;
            pdfView.invalidate();
        }

        touch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action=event.getAction();
                float x= event.getX();
                float y= event.getY();
                switch (action){
                    case MotionEvent.ACTION_DOWN:
                        float xPositionInRealScales = pdfView.toRealScale(-pdfView.getCurrentXOffset() + event.getX());
                        float yPositionInRealScales = pdfView.toRealScale(-pdfView.getCurrentYOffset() + event.getY());

                        if (pdfView.isSwipeVertical()) {
                            xPositionInRealScales = xPositionInRealScales - pdfView.pdfFile.getSecondaryPageOffset(pdfView.getCurrentPage(), 1);
                            yPositionInRealScales = yPositionInRealScales - pdfView.pdfFile.getPageOffset(pdfView.getCurrentPage(), 1);
                        } else {
                            xPositionInRealScales = xPositionInRealScales - pdfView.pdfFile.getPageOffset(pdfView.getCurrentPage(), 1);
                            yPositionInRealScales = yPositionInRealScales - pdfView.pdfFile.getSecondaryPageOffset(pdfView.getCurrentPage(), 1);
                        }
                        startX = xPositionInRealScales / pdfView.getPageSize(pdfView.getCurrentPage()).getWidth();
                        startY = yPositionInRealScales / pdfView.getPageSize(pdfView.getCurrentPage()).getHeight();
                        /*Toast.makeText(getApplicationContext(),startX+"+"+startY,Toast.LENGTH_SHORT).show();
                        if (startX<0f||startX>1f||startY<0f||startY>0f){
                            return true;
                        }else{*/
                            lastx=x;
                            lasty=y;
                        /*}*/
                        break;

                    case MotionEvent.ACTION_MOVE:
                        if (lastx!=-1){
                            touchAccept.mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                            touchAccept.mCanvas.drawRect(lastx,lasty,x,y,normalBorder);
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        float xPositionInRealScalee = pdfView.toRealScale(-pdfView.getCurrentXOffset() + event.getX());
                        float yPositionInRealScalee = pdfView.toRealScale(-pdfView.getCurrentYOffset() + event.getY());

                        if (pdfView.isSwipeVertical()) {
                            xPositionInRealScalee = xPositionInRealScalee - pdfView.pdfFile.getSecondaryPageOffset(pdfView.getCurrentPage(), 1);
                            yPositionInRealScalee = yPositionInRealScalee - pdfView.pdfFile.getPageOffset(pdfView.getCurrentPage(), 1);
                        } else {
                            xPositionInRealScalee = xPositionInRealScalee - pdfView.pdfFile.getPageOffset(pdfView.getCurrentPage(), 1);
                            yPositionInRealScalee = yPositionInRealScalee - pdfView.pdfFile.getSecondaryPageOffset(pdfView.getCurrentPage(), 1);
                        }
                        endX = xPositionInRealScalee / pdfView.getPageSize(pdfView.getCurrentPage()).getWidth();
                        endY = yPositionInRealScalee / pdfView.getPageSize(pdfView.getCurrentPage()).getHeight();
                        /*if (endX<=0){
                            endX=0f;
                        }
                        if (endX>=1){
                            endX=1f;
                        }
                        if (endY<=0){
                            endY=0f;
                        }
                        if (endY>=1){
                            endY=1f;
                        }*/
                        lastx=-1;
                        lasty=-1;
                        touchAccept.mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

                        sqlite.insert(replace,pdfView.getCurrentPage(),startX,startY,endX,endY,interval*1440,ef);
                        pdfView.invalidate();

                        break;
                }
                is_something_changed=true;
                touchAccept.invalidate();
                return true;

            }
        });

        displayPDF(read);
    }

    @Override
    public void onConfigurationChanged(@NonNull @NotNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        pdfView.recycle();
        displayPDF(read);
    }

    private void displayPDF(File file){
        pdfView.fromFile(file)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableAnnotationRendering(false)
                .scrollHandle(new DefaultScrollHandle(this))
                .onDraw(new OnDrawListener() {
                    @Override
                    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {
                        sl=helper.getReadableDatabase();
                        String sql="select "+helper.ltCornerx+","+helper.ltCornery+","+helper.rbCornerx+","+helper.rbCornery+","+helper.id+" from "+ helper.TABLE_NAME+" where "+helper.pdfname+" = '"+replace+"' and "+helper.pagenum+" ='"+currentpage+"'";
                        Cursor c=sl.rawQuery(sql,null);
                        coordi=new float[c.getCount()][5];
                        idcounter=new int[c.getCount()];
                        pagew=pageWidth;
                        pageh=pageHeight;
                        j=0;
                        if (!clear){
                            while(c.moveToNext()){
                                for (int i=0; i<4; i++){
                                    coordi[j][i]=c.getFloat(i);
                                }
                                coordi[j][4]=c.getInt(4);

                                canvas.drawRect(coordi[j][0]*pageWidth,coordi[j][1]*pageHeight,coordi[j][2]*pageWidth,coordi[j][3]*pageHeight, normalInner);

                                if (isborderon){
                                    canvas.drawRect(coordi[j][0]*pageWidth,coordi[j][1]*pageHeight,coordi[j][2]*pageWidth,coordi[j][3]*pageHeight, normalBorder);
                                }

                                j++;
                            }
                        }

                        c.close();
                    }
                })
                .pageSnap(true)
                .onTap(new OnTapListener() {
                    @Override
                    public boolean onTap(MotionEvent e) {
                        if (isdelete){
                            float xPositionInRealScales = pdfView.toRealScale(-pdfView.getCurrentXOffset() + e.getX());
                            float yPositionInRealScales = pdfView.toRealScale(-pdfView.getCurrentYOffset() + e.getY());

                            if (pdfView.isSwipeVertical()) {
                                xPositionInRealScales = xPositionInRealScales - pdfView.pdfFile.getSecondaryPageOffset(pdfView.getCurrentPage(), 1);
                                yPositionInRealScales = yPositionInRealScales - pdfView.pdfFile.getPageOffset(pdfView.getCurrentPage(), 1);
                            } else {
                                xPositionInRealScales = xPositionInRealScales - pdfView.pdfFile.getPageOffset(pdfView.getCurrentPage(), 1);
                                yPositionInRealScales = yPositionInRealScales - pdfView.pdfFile.getSecondaryPageOffset(pdfView.getCurrentPage(), 1);
                            }
                            deleteX = xPositionInRealScales / pdfView.getPageSize(pdfView.getCurrentPage()).getWidth();
                            deleteY = yPositionInRealScales / pdfView.getPageSize(pdfView.getCurrentPage()).getHeight();
                            for (int i=0; i<coordi.length;i++){
                                if (coordi[i][0]<=deleteX&&coordi[i][2]>=deleteX&&coordi[i][1]<=deleteY&&coordi[i][3]>=deleteY){
                                    idcounter[i]=(int)coordi[i][4];
                                }
                            }

                            Arrays.sort(idcounter);
                            Integer id;

                            if (idcounter.length<1){

                            }else {
                                id=idcounter[idcounter.length-1];
                                sl=helper.getWritableDatabase();
                                String sql="delete from "+helper.TABLE_NAME+" where "+helper.id+" = '"+id+"'";
                                sl.execSQL(sql);
                                is_something_changed=true;
                                pdfView.invalidate();
                            }

                        }
                        return true;
                    }
                })
                .autoSpacing(true)
                .onPageChange(new OnPageChangeListener() {
                    @Override
                    public void onPageChanged(int page, int pageCount) {
                        currentpage=pdfView.getCurrentPage();
                    }
                })
                .pageFitPolicy(FitPolicy.BOTH)
                .load();
    }

    private void getreplace(String filepath){
        if (filepath.contains(getApplicationContext().getFilesDir().getAbsolutePath()+"/"+"Basic folder")){
            replace=filepath.replaceFirst(getApplicationContext().getFilesDir().getAbsolutePath()+"/"+"Basic folder","a");
        }else if(filepath.contains(getApplicationContext().getExternalFilesDir(null).getParent()+"/"+"Premium folder")){
            replace=filepath.replaceFirst(getApplicationContext().getExternalFilesDir(null).getParent()+"/"+"Premium folder","b");
        }
    }


    /*public void notiprocess() {
        backgroundTask = Observable.fromCallable(() -> {

            return true;

        })      .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((t) -> {
                    if (is_something_changed==true){
                        NotificationHelper notificationHelper=new NotificationHelper(this);
                        notificationHelper.cancelNotification();
                        cancelAlarm();
                        startAlarm();
                    }
                });
    }*/

}
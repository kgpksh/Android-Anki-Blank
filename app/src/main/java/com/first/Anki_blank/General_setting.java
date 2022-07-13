package com.first.Anki_blank;

import android.app.AlarmManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.first.Anki_blank.Alarm.NotificationHelper;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.flag.BubbleFlag;
import com.skydoves.colorpickerview.flag.FlagMode;

public class General_setting extends AppCompatActivity {

    Context context;

    SharedPreferences general;
    SharedPreferences.Editor editor;
    SwitchCompat notification,borderOn,pointedBorderon,onlyshow;
    LinearLayout language;
    RadioButton minute,hour,day,random,firstcard,oldcard;
    RadioGroup timeRadio,priority;
    Button pickColor,borderColor,pointedColor,pointedBordercolor;
    EditText learn_ahead_limit;
    SQLiteControl sqlite;
    boolean notichecker,onlyoneshowchecker;
    AlarmManager alarmManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_setting);
        general=getSharedPreferences("General",MODE_PRIVATE);
        editor=general.edit();
        notification=findViewById(R.id.Notification);
        onlyshow=findViewById((R.id.onlypoint));
        learn_ahead_limit=findViewById(R.id.learn_ahead_limit);
        timeRadio=findViewById(R.id.time_radio);
        priority=findViewById(R.id.priority);
        minute=findViewById(R.id.minute);
        hour=findViewById(R.id.hour);
        day=findViewById(R.id.day);
        random=findViewById(R.id.totally_random);
        firstcard=findViewById(R.id.newcard_first);
        oldcard=findViewById(R.id.oldcard_first);
        language=findViewById(R.id.language);
        borderOn=findViewById(R.id.blank_border);
        pickColor=findViewById(R.id.pickcolor);
        borderColor=findViewById(R.id.pick_border_color);
        pointedBorderon=findViewById(R.id.pointed_blank_border);
        pointedColor=findViewById(R.id.pick_pointed_color);
        pointedBordercolor=findViewById(R.id.pointed_pick_border_color);


        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        sqlite=new SQLiteControl(this);
        notification.setChecked(general.getBoolean("notification",true));
        if (general.getBoolean("notification",true)){
            notichecker=true;
        }else{
            notichecker=false;
        }

        onlyshow.setChecked(general.getBoolean("onlyshow",false));
        if (general.getBoolean("onlyshow",true)){
            onlyoneshowchecker=true;
        }else{
            onlyoneshowchecker=false;
        }

        borderOn.setChecked(general.getBoolean("borderon",true));
        pointedBorderon.setChecked(general.getBoolean("pointedBorderon",true));
        int time_standard=general.getInt("timeStandard",0);
        int mix_standard=general.getInt("mixStandard",0);

        lengthLimit(learn_ahead_limit);
        Integer l_ahead=general.getInt("learn_ahead",0);
        learn_ahead_limit.setText(l_ahead.toString());

        if (time_standard==0){
            minute.setChecked(true);
            hour.setChecked(false);
            day.setChecked(false);

        }else if (time_standard==1){
            minute.setChecked(false);
            hour.setChecked(true);
            day.setChecked(false);

        }else if (time_standard==2){
            minute.setChecked(false);
            hour.setChecked(false);
            day.setChecked(true);
        }

        if (mix_standard==0){
            random.setChecked(true);
            firstcard.setChecked(false);
            oldcard.setChecked(false);
        }else if (mix_standard==1){
            random.setChecked(false);
            firstcard.setChecked(true);
            oldcard.setChecked(false);
        }else if (mix_standard==2){
            random.setChecked(false);
            firstcard.setChecked(false);
            oldcard.setChecked(true);
        }

        onoff(notification,"notification");
        onoff(borderOn,"borderon");
        onoff(pointedBorderon,"pointedBorderon");
        onoff(onlyshow,"onlyshow");

        timeRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.minute){
                    editor.putInt("timeStandard",0);
                    editor.apply();

                }else if (checkedId==R.id.hour){
                    editor.putInt("timeStandard",1);
                    editor.apply();

                }else if (checkedId==R.id.day){
                    editor.putInt("timeStandard",2);
                    editor.apply();
                }

            }
        });

        priority.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.totally_random){
                    editor.putInt("mixStandard",0);
                    editor.apply();

                }else if (checkedId==R.id.newcard_first){
                    editor.putInt("mixStandard",1);
                    editor.apply();

                }else if (checkedId==R.id.oldcard_first){
                    editor.putInt("mixStandard",2);
                    editor.apply();
                }
            }
        });

        setcolorPickview(borderColor,0);
        setcolorPickview(pickColor,1);
        setcolorPickview(pointedColor,2);
        setcolorPickview(pointedBordercolor,3);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (notichecker==false){
            RXJava_sqlite rxJava_sqlite=new RXJava_sqlite();
            rxJava_sqlite.start(sqlite,alarmManager,getApplicationContext());
        }

        /*if (general.getBoolean("notification",true)==true&&notichecker==false){
            cancelAlarm();
            notificationHelper.cancelNotification();
            startAlarm();
        }*/

        if (general.getBoolean("notification",true)==false){
            NotificationHelper notificationHelper=new NotificationHelper(getApplicationContext());
            notificationHelper.cancelNotification();
        }
        if (learn_ahead_limit.length()!=0){
            editor.putInt("learn_ahead",Integer.parseInt(learn_ahead_limit.getText().toString()));
            editor.apply();
        }else{
            editor.putInt("learn_ahead",0);
            editor.apply();
        }
    }

    private void onoff(SwitchCompat switchCompat, String string){
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    editor.putBoolean(string,true);
                    editor.apply();
                }else{
                    editor.putBoolean(string,false);
                    editor.apply();
                }
            }
        });
    }

    public void basicGeneral(String gen){
        context=MainActivity.context;
        SharedPreferences sp=context.getSharedPreferences(gen,MODE_PRIVATE);
        SharedPreferences.Editor ed=sp.edit();
        ed.putBoolean("notification",true);
        ed.putInt("interval",0);
        ed.apply();
    }

    private void setcolorPickview(Button button, int seperator){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorPickerview(seperator);
            }
        });
    }

    private void colorPickerview(int seperator){
        ColorPickerDialog.Builder builder=new ColorPickerDialog.Builder(General_setting.this);
        ColorPickerView colorPickerView=builder.getColorPickerView();
        builder.setTitle("Pick color");

        builder.setPositiveButton("Pick", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String hexColor = String.format("#%06X", (0xFFFFFF & colorPickerView.getColor()));

                if (seperator==0){
                    editor.putString("bordercolor",hexColor);
                }else if (seperator==1){
                    editor.putString("innercolor",hexColor);
                }else if (seperator==2){
                    editor.putString("pointedinner",hexColor);
                }else if (seperator==3){
                    editor.putString("pointedborder",hexColor);
                }
                editor.apply();

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.attachAlphaSlideBar(false);

        BubbleFlag bubbleFlag=new BubbleFlag(this);
        bubbleFlag.setFlagMode(FlagMode.ALWAYS);
        colorPickerView.setFlagView(bubbleFlag);

        builder.show();
    }

    private void lengthLimit(EditText editText){
        editText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(Integer.MAX_VALUE)});
    }

}

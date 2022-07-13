package com.first.Anki_blank;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PDFAdapterre extends RecyclerView.Adapter<PDFAdapterre.MyViewHolder> {

    private Context mContext;
    public ArrayList<File> mFiles;
    SQLiteControl sqlite;
    SQLiteHelper helper;
    SQLiteDatabase sl;
    SharedPreferences mother;
    SharedPreferences.Editor motheredt;
    File basic,premium;
    boolean ispurchased,isPremium;



    public PDFAdapterre(Context mContext, ArrayList<File> mFiles, File basic, File premium, boolean ispurchased, boolean isPremium) {
        this.mContext = mContext;
        this.mFiles = mFiles;
        this.basic=basic;
        this.premium=premium;
        this.ispurchased = ispurchased;
        this.isPremium=isPremium;
        mother=mContext.getSharedPreferences("Mother",Context.MODE_PRIVATE);
        motheredt = mother.edit();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.pdf_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.file_name.setText(mFiles.get(position).getName().replace(".pdf",""));

        holder.pdf_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_dialog(holder,position);
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }



    private void delete_ask(MyViewHolder holder,int position){
        AlertDialog.Builder builder=new AlertDialog.Builder(mContext);

        builder.setTitle(mContext.getResources().getString(R.string.Delete_confirm))
                .setMessage(mContext.getResources().getString(R.string.Delete_confirm_content));


        builder.setPositiveButton(mContext.getResources().getString(R.string.Delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                thread_delete(holder,position);
            }
        });

        builder.setNegativeButton(mContext.getResources().getString(R.string.Cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }



    @Override
    public int getItemCount() {
        return mFiles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView file_name;
        ImageView img_icon;
        LinearLayout pdf_item;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pdf_item=itemView.findViewById(R.id.pdf_item);
            file_name=itemView.findViewById(R.id.pdf_file_name);
            img_icon=itemView.findViewById(R.id.img_pdf);
        }
    }

    private void show_dialog(MyViewHolder holder,int position){
        Dialog dialog=new Dialog(mContext);
        dialog.setContentView(R.layout.pdf_dialog);
        dialog.show();

        TextView name=dialog.findViewById(R.id.pdf_name);
        LinearLayout R_E=dialog.findViewById(R.id.re);
        LinearLayout study=dialog.findViewById(R.id.study);
        LinearLayout option=dialog.findViewById(R.id.pdf_option);
        LinearLayout delete=dialog.findViewById(R.id.pdf_delete);

        name.setText(mFiles.get(position).getName().replace(".pdf",""));
        R_E.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent=new Intent(mContext,ViewPDFFiles.class);
                intent.putExtra("R_E",mFiles.get(position).getAbsolutePath());
                mContext.startActivity(intent);
            }
        });

        study.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent=new Intent(mContext,StudyPDFFiles.class);
                intent.putExtra("onestudy",mFiles.get(position).getAbsolutePath());
                intent.putExtra("onestudyS",true);
                mContext.startActivity(intent);
            }
        });

        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent=new Intent(mContext,Default_option.class);
                intent.putExtra("pdfname",mFiles.get(position).getAbsolutePath());
                intent.putExtra("multiple_check",1);
                mContext.startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                delete_ask(holder,position);
            }
        });

        if (ispurchased ==false&&isPremium==true){
            study.setClickable(false);
            option.setClickable(false);

            study.setBackgroundColor(Color.GRAY);
            option.setBackgroundColor(Color.GRAY);
        }
    }

    String getreplace(String filepath){
        String replacedpath = new String();
        if (filepath.contains(basic.getAbsolutePath())){
            replacedpath=filepath.replaceFirst(basic.getAbsolutePath(),"a");
        }else if(filepath.contains(premium.getAbsolutePath())){
            replacedpath=filepath.replaceFirst(premium.getAbsolutePath(),"b");
        }
        return replacedpath;
    }

    /*Intent intent=new Intent(mContext,ViewPDFFiles.class);

                intent.putExtra("position",position);
                mContext.startActivity(intent);*/

    private void delete(int position){
        motheredt.remove(mFiles.get(position).getAbsolutePath());
        motheredt.apply();

        sqlite=new SQLiteControl(mContext);
        helper=sqlite.helper;
        sl=helper.getWritableDatabase();
        String sql="delete from "+ helper.TABLE_NAME+" where "+helper.pdfname+" = '"+getreplace(mFiles.get(position).getAbsolutePath())+"'";
        String list_delete="delete from "+ helper.List_Table+" where "+helper.filelist+" = '"+mFiles.get(position).getAbsolutePath()+"'";
        try {
            sl.beginTransaction();
            sl.execSQL(sql);
            sl.execSQL(list_delete);
            sl.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sl.endTransaction();
        }
        sl.close();
        helper.close();

        mFiles.get(position).delete();
        mFiles.remove(position);


        Intent intent=new Intent(mContext,MainActivity.class);
        intent.putExtra("progress",true);
    }

    private void thread_delete(MyViewHolder holder,int position){
        Handler handler=new Handler();
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.right_rotating);
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        holder.pdf_item.setClickable(false);
                        holder.img_icon.setImageResource(R.drawable.loading_rotating_right);
                        holder.img_icon.setAnimation(animation);
                    }
                });

                delete(position);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        animation.cancel();
                        holder.img_icon.setImageResource(R.drawable.ic_baseline_picture_as_pdf_24);
                        holder.pdf_item.setClickable(true);
                        notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }


}

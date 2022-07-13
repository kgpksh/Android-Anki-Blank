package com.first.Anki_blank;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;


public class OptionList extends RecyclerView.Adapter<OptionList.ViewHolder> {
    Context mContext;
    ArrayList<File> son;
    ArrayList<String> mother;
    boolean selectable,same,issame,isfromMain;
    int issingle;
    String pdfname;
    ColorDrawable pink,white;
    int red;
    String optionname="";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Default_option default_option=new Default_option();

    public OptionList(Context mContext, ArrayList<File> son, ArrayList<String> mother, boolean selectable, int issingle, String pdfname,Boolean isfromMain) {
        this.mContext = mContext;
        this.son=son;
        this.mother=mother;
        this.selectable=selectable;
        this.issingle=issingle;
        this.pdfname=pdfname;
        this.isfromMain=isfromMain;
    }

    @NonNull
    @Override
    public OptionList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.option_item,parent,false);
        issame=true;
        sharedPreferences= mContext.getSharedPreferences("Mother",Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        if (isfromMain){

        }else{
            if (issingle==1){
                optionname=sharedPreferences.getString(pdfname,"Default");
            }else{
                if (mother.size()>1){
                    for (int i=1; i<mother.size(); i++){
                        if(sharedPreferences.getString(mother.get(i),"Default").equals(sharedPreferences.getString(mother.get(i-1),"Default"))){
                            same=true;
                        }else{
                            same=false;
                        }
                        issame=issame&&same;
                        if (!issame){
                            break;
                        }
                    }
                }else if (mother.size()==1){
                    optionname=sharedPreferences.getString(pdfname,"Default");
                }else if (mother.size()==0){
                    optionname="";
                }

                if (issame&&mother.size()!=0){
                    optionname=sharedPreferences.getString(mother.get(0),"Default");
                }
            }
        }

        red=Color.parseColor("#F4D5D5");
        pink = new ColorDrawable(red);
        white = new ColorDrawable(Color.TRANSPARENT);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.optionName.setText(xmlCut(son.get(position).getName()));

        if (optionname.equals(holder.optionName.getText())){
            holder.itemView.setBackground(pink);
        }else if (optionname.equals(holder.optionName.getText())==false){
            holder.itemView.setBackground(white);
        }

        if (xmlCut(son.get(position).getName()).equals("Default")){
            holder.delete.setVisibility(View.GONE);
        }
        if (selectable==false){
            holder.select.setVisibility(View.GONE);
        }
        holder.editOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent option_Edit=new Intent(mContext,Option.class);
                option_Edit.putExtra("sharedname",xmlCut(son.get(position).getName()));
                mContext.startActivity(option_Edit);
            }
        });

        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectable){

                    for (String mother : mother){
                        default_option.mother_preference(mother,xmlCut(son.get(position).getName()));
                    }
                    optionname= (String) holder.optionName.getText();
                    notifyDataSetChanged();

                }
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(mContext);

                builder.setTitle("Delete confirm")
                        .setMessage("Are you certain you want to delete this schedule option?");


                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        son.get(position).delete();
                        son.remove(position);
                        if (optionname.length()==0){

                        }else if (optionname.equals(holder.optionName.getText())){
                            optionname="Default";
                        }
                        Map<String,?> keys=sharedPreferences.getAll();
                        for (Map.Entry<String,?> entry : keys.entrySet()){
                            if (entry.getValue().equals(holder.optionName.getText())){
                                editor.putString(entry.getKey(),"Default");
                            }
                        }
                        editor.apply();

                        notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog alertDialog=builder.create();
                alertDialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        if (son==null){
            return 0;
        }else{
            return son.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView optionName;
        Button editOption, delete,select;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            optionName=itemView.findViewById(R.id.option_name);
            editOption=itemView.findViewById(R.id.edit_option);
            delete=itemView.findViewById(R.id.option_delete);
            select=itemView.findViewById(R.id.option_select);
        }
    }

    private  String xmlCut(String xml){
        return xml.substring(0,xml.length()-4);
    }
}

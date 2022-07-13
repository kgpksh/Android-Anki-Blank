package com.first.Anki_blank;

import java.io.File;
import java.util.ArrayList;

public class When_delete {
    ArrayList<File> container= new ArrayList<File>();

    public void is_exist(File dir,ArrayList arrayList){
        File files[]= dir.listFiles();

        for (File fileList : files){
            if (fileList.isDirectory()&&!fileList.isHidden()){
                is_exist(fileList,arrayList);
            }else{
                arrayList.add(fileList.getAbsolutePath());
            }
        }
    }

    public void exist_check(File dir){
        File files[]= dir.listFiles();

        for (File fileList : files){
            if (fileList.isDirectory()&&!fileList.isHidden()){
                exist_check(fileList);
            }else{
                container.add(fileList);
            }
        }
    }



    public boolean fileComparer(File pdf, ArrayList<File> comparer){
        boolean same = false;
        for (File is_Exist : comparer){
                if (pdf.getAbsolutePath().equals(is_Exist.getAbsolutePath())){
                    same= true;
                    break;
                }else{
                    same= false;
                }
        }
        return same;
    }
}

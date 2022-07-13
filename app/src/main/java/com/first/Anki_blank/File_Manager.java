package com.first.Anki_blank;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.provider.OpenableColumns;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import io.reactivex.rxjava3.disposables.Disposable;

public class File_Manager extends AppCompatActivity {
    Intent backtoMain;
    String name;
    String topath;
    public static final int PICK_PDF_FILE = 2;
    SQLiteControl sqLite;
    int pagenum;
    File file;
    boolean isbasic;
    boolean isok=false;
    MainActivity mainActivity;
    int allpagenum;
    int max_allowed_page;
    Uri uri=null;
    ImageView loading;
    Animation animation;
    Disposable backgroundTask;
    ArrayList<File> container=new ArrayList<>();
    When_delete when_delete=new When_delete();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiting);
        mainActivity=new MainActivity();
        sqLite=new SQLiteControl(File_Manager.this);

        backtoMain = new Intent(this, MainActivity.class);

        allpagenum=0;
        max_allowed_page=999999999;

        topath=getIntent().getStringExtra("topath");
        isbasic=getIntent().getBooleanExtra("isbasic",false);
        File basicDir=new File(this.getFilesDir().getAbsolutePath()+"/"+"Basic folder");
        File premiumDir=new File(this.getExternalFilesDir(null).getParent()+"/"+"Premium folder");

        if (isbasic){
            pagecounter(basicDir);
        }else{
            pagecounter(premiumDir);
        }

        loading=findViewById(R.id.loading);

        openFile();

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (isok){
            init_Loading_anim();
            go();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (backgroundTask!=null){
            backgroundTask.dispose();
        }
    }

    public void openFile() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");

        startActivityForResult(intent, PICK_PDF_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == PICK_PDF_FILE
                && resultCode == Activity.RESULT_OK) {
            uri = resultData.getData();
            isok=true;
        }else{
            startActivity(backtoMain);
            finish();
        }
    }

    public static int getTotalPages(File pdfFile) throws IOException, Exception {
        ParcelFileDescriptor parcelFileDescriptor = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            PdfRenderer pdfRenderer = new PdfRenderer(parcelFileDescriptor);
            return pdfRenderer.getPageCount();

        } else {
            throw new Exception("PDF cannot be processed in this device");
        }
    }

    public String getFileName(Uri uri) {
        String result=null;
        if(uri.getScheme().equals("content")){
            Cursor cursor=getContentResolver().query(uri,null,null,null,null);
            try {
                if (cursor !=null&&cursor.moveToFirst()){
                    result=cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result==null){
            result =uri.getPath();
            int cut=result.lastIndexOf('/');
            if (cut !=-1){
                result=result.substring(cut+1);
            }
        }
        return result;
    }

    @Nullable
    public void getFileFromURI(@NonNull Context context, @NonNull Uri uri,String name,String topath) {
        final ContentResolver contentResolver = context.getContentResolver();
        boolean is_copy_finished=false;
        if (contentResolver == null){
            return;
        }

        try {
            InputStream inputStream = contentResolver.openInputStream(uri);
            if (inputStream == null){
                return;
            }

            OutputStream nfos= null;
            byte[] buf = new byte[1024];
            int len;
            nfos = new FileOutputStream(topath+"/"+name);

            while ((len = inputStream.read(buf)) > 0)
                nfos.write(buf, 0, len);
            is_copy_finished=true;

            nfos.close();
            inputStream.close();


        } catch (IOException ignore) {
            ignore.printStackTrace();
        }
    }


    private void pagecounter(File dir){
        File files[]= dir.listFiles();

        for (File fileList : files){
            if (fileList.isDirectory()&&!fileList.isHidden()){
                pagecounter(fileList);
            }else{
                try {
                    allpagenum=allpagenum+getTotalPages(fileList);
                    container.add(fileList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void afterget_Uri(){
        if (uri!=null){
            name=getFileName(uri);
            getFileFromURI(File_Manager.this,uri,name,topath);
            file=new File(topath+"/"+name);


        }
    }

    void init_Loading_anim(){
        animation = AnimationUtils.loadAnimation(this, R.anim.right_rotating);
        loading.setAnimation(animation);
    }

    private void go(){
        Handler handler=new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                afterget_Uri();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            pagenum=getTotalPages(file);
                            if (isbasic){

                                if (allpagenum+pagenum>max_allowed_page){

                                    if (!when_delete.fileComparer(file,container)){
                                        file.delete();
                                        Toast.makeText(getApplicationContext(),pagenum+getResources().getString(R.string.pages),Toast.LENGTH_SHORT).show();
                                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.Basicfolder_capacity),Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.file_already_exists),Toast.LENGTH_SHORT).show();
                                    }

                                }else{
                                    if (when_delete.fileComparer(file,container)){
                                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.file_already_exists),Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getApplicationContext(),pagenum+getResources().getString(R.string.pages),Toast.LENGTH_SHORT).show();
                                        sqLite.listInsert(topath+"/"+name);
                                    }

                                }

                            }else{
                                if (!when_delete.fileComparer(file,container)){
                                    sqLite.listInsert(topath+"/"+name);
                                }else{
                                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.file_already_exists),Toast.LENGTH_SHORT).show();
                                }

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        startActivity(backtoMain);
                        finish();
                    }
                });
            }
        }).start();
    }


}

package com.example.a52external;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SettingClass extends AppCompatActivity {
    public static final int REQUEST_CODE_PERMISSION_WRITE_STORAGE = 10;
    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadImg();
                Intent intent = new Intent(SettingClass.this,MainActivity.class);
                startActivity(intent);
            }
        });


        int permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            LoadImg();  //работа с файлами
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION_WRITE_STORAGE);
        }
    }
    public void LoadImg() {
        if (isExternalStorageWriteble()) {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"12.jpg");

            ImageView im = findViewById(R.id.imageView2);
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            im.setImageBitmap(bitmap);

            File logFile = new File(getApplicationContext().getExternalFilesDir(null),"log.txt");
            FileWriter writer = null;
            try {
                writer = new FileWriter(logFile,true);
                writer.append("app started");

            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    public boolean isExternalStorageWriteble() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
    public void onRequestPermissionResult (int requestCode, @NonNull String[] permission, @NonNull int[] grantResult){
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_WRITE_STORAGE:
                if (grantResult.length > 0
                        && grantResult[0] == PackageManager.PERMISSION_GRANTED) {
                    LoadImg();  //работа с файлами
                }
        }
    }

}

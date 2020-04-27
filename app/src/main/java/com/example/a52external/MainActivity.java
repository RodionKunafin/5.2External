package com.example.a52external;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Button btnSettings;
    private TextView resultField;
    private Button btnPoint;
    private static final int READ_REQUEST_CODE = 10;
    private ImageView background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        btnSettings = findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performFileSearch();
            }
        });
    }


    private void performFileSearch() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        intent.addCategory(Intent.CATEGORY_OPENABLE);

        intent.setType("image/*");

        startActivityForResult(intent, READ_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                try (ParcelFileDescriptor parcelFileDescriptor =
                             getContentResolver().openFileDescriptor(Objects.requireNonNull(uri), "r")) {
                    FileDescriptor fileDescriptor = Objects.requireNonNull(parcelFileDescriptor).getFileDescriptor();
                    Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                    background.setImageBitmap(image);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void numPress(View view) {
        int id = view.getId();
        if (id == R.id.btn0) {
            resultField.setText("0");
        }
        if (id == R.id.btn1) {
            resultField.setText("1");
        }
        if (id == R.id.btn2) {
            resultField.setText("2");
        }
        if (id==R.id.btn3){
            resultField.setText("3");
        }
        if (id==R.id.btn4){
            resultField.setText("4");
        }
        if (id==R.id.btn5){
            resultField.setText("5");
        }
        if (id==R.id.btn6){
            resultField.setText("6");
        }
        if (id==R.id.btn7){
            resultField.setText("7");
        }
        if (id==R.id.btn8){
            resultField.setText("8");
        }
        if (id==R.id.btn9){
            resultField.setText("9");
        }
    }
    public void dotPress(View v){
        int id = v.getId();
        if (id==R.id.btnPoint){
            resultField.setText(".");
        }
    }

    private void initViews() {
        background = findViewById(R.id.background);
        resultField  = findViewById(R.id.resultField);
        btnPoint = findViewById(R.id.btnPoint);

    }

}

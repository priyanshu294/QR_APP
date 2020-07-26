package com.example.qrapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.zxing.WriterException;

import androidx.appcompat.app.AppCompatActivity;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class MainActivity extends AppCompatActivity {

    Button generateBtn,scanBtn;
    ImageView qrImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //qrvalue = findViewById(R.id.qrInput);
        generateBtn = findViewById(R.id.generateBtn);
        scanBtn = findViewById(R.id.scanBtn);
        qrImage = findViewById(R.id.qrPlaceHolder);





        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Scanner.class));
            }
        });

        generateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Select_QR_Code.class));
            }
        });



    }
}

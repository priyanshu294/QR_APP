package com.example.qrapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import java.io.FileNotFoundException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class Suggestion extends AppCompatActivity {

    ImageView qr_code_img,delete_img,share_img,copy_img;
    TextView content_txt,delete_txt,share_txt,copy_txt;
    Button sugg_button;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        qr_code_img = findViewById(R.id.qrcode_result);
        delete_img = findViewById(R.id.delte_img);
        share_img = findViewById(R.id.share_img);
        copy_img = findViewById(R.id.copy_img);

        content_txt = findViewById(R.id.result_text);
        delete_txt = findViewById(R.id.delete_text);
        share_txt = findViewById(R.id.share_text);
        copy_txt = findViewById(R.id.copy_text);
        sugg_button = findViewById(R.id.creare_btn);

// content text code scan part
        if(content_txt.getText().toString().length() > 0) {

            content_txt.setText(getIntent().getStringExtra("MyResult"));

            String data = content_txt.getText().toString();
            QRGEncoder qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT, 500);

            try {
                Bitmap qrBits = qrgEncoder.getBitmap();
                qr_code_img.setImageBitmap(qrBits);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
// content text code gallery part
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(this.openFileInput("myImage"));
                qr_code_img.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            content_txt.setText(getIntent().getStringExtra("MyResult"));
        }

}


}
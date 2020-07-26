package com.example.qrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class Phone extends AppCompatActivity {

    EditText editText;
    ImageView imageView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        editText = findViewById(R.id.phone_input);
        imageView = findViewById(R.id.qrcode_image);
        button = findViewById(R.id.creare_btn);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String data =   editText.getText().toString();
                if (data.isEmpty()) {
                    editText.setError("Value Required.");

                }
                else {
                    QRGEncoder qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.PHONE, 500);

                    try {
                        Bitmap qrBits = qrgEncoder.getBitmap();

                        imageView.setImageBitmap(qrBits);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }



            }
        });
    }
}

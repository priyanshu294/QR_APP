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

public class GeoLocation extends AppCompatActivity {

    EditText editText_lat,editText_long;
    ImageView imageView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_location);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        editText_lat = findViewById(R.id.lat_input);
        editText_long = findViewById(R.id.long_input);
        imageView  = findViewById(R.id.qrcode_image);
        button = findViewById(R.id.creare_btn);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String data =  "geo: "+ (editText_lat.getText().toString()) +"," + (editText_long.getText().toString());

                String data_lat = editText_lat.getText().toString() ;
                String data_long = editText_long.getText().toString() ;

                if (data_lat.trim().isEmpty()) {
                    editText_lat.setError("Value Required.");
                }else if(data_long.trim().isEmpty()){
                    editText_long.setError("Value Required.");
                }
                else {
                    QRGEncoder qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT, 500);

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

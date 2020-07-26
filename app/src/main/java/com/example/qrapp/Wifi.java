package com.example.qrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class Wifi extends AppCompatActivity {

    EditText editText_ssid,editText_pass;
    ImageView imageView,imageView_icon;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        editText_ssid = findViewById(R.id.ssid_input);
        editText_pass = findViewById(R.id.password_input);
        button = findViewById(R.id.creare_btn);
        imageView = findViewById(R.id.qrcode_image);
        imageView_icon = findViewById(R.id.show_pass_btn);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String data =  "ssid: "+(editText_ssid.getText().toString()) +"\n pass: " + (editText_pass.getText().toString());

                if (data.isEmpty()) {
                    editText_ssid.setError("Value Required.");
                    editText_pass.setError("Value Required.");


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

        imageView_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(v.getId()==R.id.show_pass_btn){

                    if(editText_pass.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                        imageView_icon.setImageResource(R.drawable.hide_password);

                        //Show Password
                        editText_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    }
                    else{
                        imageView_icon.setImageResource(R.drawable.show_password);

                        //Hide Password
                        editText_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    }
                }

            }
        });




    }
}

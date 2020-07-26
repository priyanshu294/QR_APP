package com.example.qrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Select_QR_Code extends AppCompatActivity {

  LinearLayout layout;
  ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__qr__code);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // layout
        layout = findViewById(R.id.text_container1);
        layout = findViewById(R.id.text_container2);
        layout = findViewById(R.id.text_container3);
        layout = findViewById(R.id.text_container4);
        layout = findViewById(R.id.text_container5);
        layout = findViewById(R.id.text_container6);
        layout = findViewById(R.id.text_container7);
        layout = findViewById(R.id.text_container8);
        layout = findViewById(R.id.text_container9);

        // image view

        imageView = findViewById(R.id.email);
        imageView = findViewById(R.id.wifi);
        imageView = findViewById(R.id.event);
        imageView = findViewById(R.id.contact);
        imageView = findViewById(R.id.text);
        imageView = findViewById(R.id.website);
        imageView = findViewById(R.id.location);
        imageView = findViewById(R.id.phone);
        imageView = findViewById(R.id.message);

        // Text view
        TextView  textView1 = findViewById(R.id.email_text);
        TextView  textView2 = findViewById(R.id.wifi_text);
        TextView textView3 = findViewById(R.id.event_text);
        TextView textView4 = findViewById(R.id.contact_text);
        TextView textView5 = findViewById(R.id.Text_text);
        TextView textView6 = findViewById(R.id.website_text);
        TextView  textView7 = findViewById(R.id.location_text);
        TextView textView8 = findViewById(R.id.phone_text);
        TextView  textView9 = findViewById(R.id.message_text);


    //Onclick email

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),Email.class));

            }
        });

        //Onclick Text

        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),Text.class));
            }
        });

        //Onclick phone

        textView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),Phone.class));

            }
        });

        //Onclick message

        textView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), SMS.class));

            }
        });


        //Onclick website

        textView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), URL.class));

            }
        });


        //Onclick wifi

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), Wifi.class));

            }
        });


        //Onclick contact

        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), Contact_info.class));

            }
        });


        //Onclick event

        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), Event.class));

            }
        });

        //Onclick location

        textView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), GeoLocation.class));

            }
        });









    }
}

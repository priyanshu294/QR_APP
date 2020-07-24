package com.example.qrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class Contact_info extends AppCompatActivity {
    EditText editText_first_name,editText_last_name,editText_org,editText_email,editText_phone,editText_address,editText_city,editText_state,editText_contry,editText_pincode,editText_url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);
    }
}

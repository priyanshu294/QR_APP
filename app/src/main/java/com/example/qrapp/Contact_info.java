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

public class Contact_info extends AppCompatActivity {
    EditText editText_first_name,editText_last_name,editText_org,editText_email,editText_phone,editText_address,editText_city,editText_state,editText_contry,editText_pincode,editText_url;
    ImageView imageView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        editText_first_name = findViewById(R.id.first_name_input);
        editText_last_name = findViewById(R.id.last_name_input);
        editText_org = findViewById(R.id.org_input);
        editText_email = findViewById(R.id.email_input);
        editText_phone = findViewById(R.id.phone_input);
        editText_address = findViewById(R.id.address_input);
        editText_city = findViewById(R.id.city_input);
        editText_state = findViewById(R.id.state_input);
        editText_contry = findViewById(R.id.country_input);
        editText_pincode = findViewById(R.id.pincode_input);
        editText_url = findViewById(R.id.url_web_input);

        imageView = findViewById(R.id.qrcode_image);
        button = findViewById(R.id.creare_btn);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String data = (editText_first_name.getText().toString())+" "+ (editText_last_name.getText().toString())+"\n"+ (editText_org.getText().toString())+"\n"+(editText_email.getText().toString())+"\n"+(editText_phone.getText().toString())+"\n"+(editText_address.getText().toString())+","+(editText_city.getText().toString())+","+(editText_state.getText().toString())+","+(editText_contry.getText().toString())+"-"+(editText_pincode.getText().toString())+"\n"+(editText_url.getText().toString());

                String data_first = editText_first_name.getText().toString() ;
                String data_last = editText_last_name.getText().toString() ;
                String data_org = editText_org.getText().toString() ;
                String data_email = editText_email.getText().toString() ;
                String data_phone = editText_phone.getText().toString() ;
                String data_address = editText_address.getText().toString() ;
                String data_city = editText_city.getText().toString() ;
                String data_state = editText_state.getText().toString() ;
                String data_country = editText_contry.getText().toString() ;
                String data_pincode = editText_pincode.getText().toString() ;
                String data_url = editText_url.getText().toString() ;

                if (data_first.trim().isEmpty()) {
                    editText_first_name.setError("Value Required.");
                } else if(data_last.trim().isEmpty()){
                    editText_last_name.setError("Value Required.");
                }else if (data_org.trim().isEmpty()){
                    editText_org.setError("Value Required.");
                }else if(data_email.trim().isEmpty()){
                    editText_email.setError("Value Required.");
                } else if(data_phone.trim().isEmpty()){
                    editText_phone.setError("Value Required.");
                }else if(data_address.trim().isEmpty()){
                    editText_address.setError("Value Required.");
                }else if(data_city.trim().isEmpty()){
                    editText_city.setError("Value Required.");
                }else  if(data_state.trim().isEmpty()){
                    editText_state.setError("Value Required.");
                }else if(data_country.trim().isEmpty()){
                    editText_contry.setError("Value Required.");
                } else if(data_pincode.trim().isEmpty()){
                    editText_pincode.setError("Value Required.");
                }else if(data_url.trim().isEmpty()){
                    editText_url.setError("Value Required.");
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

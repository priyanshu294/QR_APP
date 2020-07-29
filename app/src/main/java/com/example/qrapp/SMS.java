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

public class SMS extends AppCompatActivity {

    EditText editText_num,editText_mes;
    ImageView imageView;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        editText_num = findViewById(R.id.number_input);
        editText_mes = findViewById(R.id.message_input);
        imageView = findViewById(R.id.qrcode_image);
        button = findViewById(R.id.creare_btn);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String data =  "Phone: " + (editText_num.getText().toString()) + "\n message: "+ (editText_mes.getText().toString());

                String data_phone = editText_num.getText().toString() ;
                String data_mess = editText_mes.getText().toString() ;

                if (data_phone.trim().isEmpty()) {
                    editText_num.setError("Value Required.");
                } else if(data_mess.trim().isEmpty()){
                    editText_mes.setError("Value Required.");
                }
                else {
                    QRGEncoder qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.SMS, 500);

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

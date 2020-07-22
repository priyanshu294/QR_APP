    package com.example.qrapp;

    import androidx.appcompat.app.AppCompatActivity;

    import android.content.Intent;
    import android.graphics.Bitmap;
    import android.graphics.BitmapFactory;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ImageView;


    import androidmads.library.qrgenearator.QRGContents;
    import androidmads.library.qrgenearator.QRGEncoder;

    public class Email extends AppCompatActivity {

        Button button;
        ImageView imageView;
        EditText editText_add,editText_sub,editText_mes;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_email);


             editText_add = findViewById(R.id.address_input);
             editText_sub = findViewById(R.id.subject_input);
             editText_mes = findViewById(R.id.message_input);
             imageView = findViewById(R.id.qrcode_image);
             button = findViewById(R.id.creare_btn);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String data1 = editText_add.getText().toString();
                    String data2 = editText_sub.getText().toString();
                    String data3 = editText_mes.getText().toString();
                    if (data1.isEmpty()) {
                        editText_add.setError("Value Required.");
                    } else if(data2.isEmpty()){
                        editText_sub.setError("Value Required.");
                    } else if(data3.isEmpty()){
                        editText_mes.setError("Value Required.");
                    }
                    else {
                        QRGEncoder qrgEncoder1 = new QRGEncoder(data1, null, QRGContents.Type.TEXT, 500);
                        QRGEncoder qrgEncoder2 = new QRGEncoder(data2, null, QRGContents.Type.TEXT, 500);
                        QRGEncoder qrgEncoder3 = new QRGEncoder(data3, null, QRGContents.Type.TEXT, 500);


                        try {
                            Bitmap qrBits1 = qrgEncoder1.getBitmap();
                            Bitmap qrBits2 = qrgEncoder2.getBitmap();
                            Bitmap qrBits3 = qrgEncoder3.getBitmap();

                            imageView.setImageBitmap(qrBits1);
                            imageView.setImageBitmap(qrBits2);
                            imageView.setImageBitmap(qrBits3);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }



                }
            });


        }
    }
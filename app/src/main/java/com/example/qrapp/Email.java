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

    import static java.security.AccessController.getContext;

    public class Email extends AppCompatActivity {

        Button button;
        ImageView imageView;
        EditText editText_add,editText_sub,editText_mes;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_email);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);



            editText_add = findViewById(R.id.address_input);
             editText_sub = findViewById(R.id.subject_input);
             editText_mes = findViewById(R.id.message_input);
             imageView = findViewById(R.id.qrcode_image);
             button = findViewById(R.id.creare_btn);

            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                     String data1 =  "email: " + (editText_add.getText().toString()) + "\n sub: "+ (editText_sub.getText().toString()) + "\n body: " + (editText_mes.getText().toString());

                    if (data1.isEmpty()) {
                        editText_add.setError("Value Required.");
                        editText_sub.setError("Value Required.");
                        editText_mes.setError("Value Required.");

                    }
                      else {
                        QRGEncoder qrgEncoder = new QRGEncoder(data1, null, QRGContents.Type.TEXT, 500);

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
    package com.example.qrapp;

    import androidx.appcompat.app.AppCompatActivity;

    import android.content.Intent;
    import android.graphics.Bitmap;
    import android.graphics.BitmapFactory;
    import android.os.Bundle;
    import android.os.Parcelable;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ImageView;

    import androidmads.library.qrgenearator.QRGContents;
    import androidmads.library.qrgenearator.QRGEncoder;

    public class Email extends AppCompatActivity  {

        Button button;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_email);



      final    EditText editText_add = findViewById(R.id.address_input);
        final    EditText editText_sub = findViewById(R.id.subject_input);
        final   EditText  editText_mes = findViewById(R.id.message_input);

            button = findViewById(R.id.creare_btn);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String data = editText_add.getText().toString();
                    if(data.isEmpty()){
                        editText_add.setError("Value Required.");
                        editText_sub.setError("Value Required.");
                        editText_mes.setError("Value Required.");

                    }else {
                        QRGEncoder qrgEncoder = new QRGEncoder(data,null, QRGContents.Type.TEXT,500);

                        try {
                            Bitmap qrBits = qrgEncoder.getBitmap();


                            qrImage.setImageBitmap(qrBits);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                   startActivity(new Intent(getApplicationContext(),result_QR.class));

                }
            });


        }
    }

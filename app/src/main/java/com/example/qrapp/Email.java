    package com.example.qrapp;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.app.ActivityCompat;

    import android.Manifest;
    import android.content.ClipData;
    import android.content.Intent;
    import android.graphics.Bitmap;
    import android.graphics.BitmapFactory;
    import android.graphics.drawable.BitmapDrawable;
    import android.os.Bundle;
    import android.os.Environment;
    import android.provider.MediaStore;
    import android.view.Menu;
    import android.view.MenuInflater;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ImageView;
    import android.widget.Toast;


    import java.io.File;
    import java.io.FileNotFoundException;
    import java.io.FileOutputStream;
    import java.io.IOException;
    import java.io.OutputStream;

    import androidmads.library.qrgenearator.QRGContents;
    import androidmads.library.qrgenearator.QRGEncoder;
    import androidmads.library.qrgenearator.QRGSaver;

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

            ActivityCompat.requestPermissions(Email.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            ActivityCompat.requestPermissions(Email.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);


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

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.mymenu,menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.download:
                    saveToGallery();
                    Toast.makeText(this,"Save QR Code",Toast.LENGTH_SHORT).show();
                    break;


                case R.id.delete:
                    Toast.makeText(this,"delete selected",Toast.LENGTH_SHORT).show();
                    break;

                    default:

            }
            return super.onOptionsItemSelected(item);

        }

        private void saveToGallery() {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();

            FileOutputStream fileOutputStream = null;
            File file = Environment.getExternalStorageDirectory();
            File dir = new File(file.getAbsolutePath()+"/Qr code");
            dir.mkdir();

            String filename = String.format("%d.png",System.currentTimeMillis());
            File outfile = new File(dir,filename);
            try{
                fileOutputStream = new FileOutputStream(outfile);
            } catch (Exception e){
                e.printStackTrace();
            }
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);
            try {
                fileOutputStream.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
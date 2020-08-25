package com.example.qrapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import java.io.FileNotFoundException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class Suggestion extends AppCompatActivity implements View.OnClickListener {

    ImageView qr_code_img, delete_img, share_img, copy_img;
    TextView content_txt, delete_txt, share_txt, copy_txt;
    Button sugg_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        qr_code_img = findViewById(R.id.qrcode_result);
        delete_img = findViewById(R.id.delte_img);
        share_img = findViewById(R.id.share_img);
        copy_img = findViewById(R.id.copy_img);

        content_txt = findViewById(R.id.result_text);
        delete_txt = findViewById(R.id.delete_text);
        share_txt = findViewById(R.id.share_text);
        copy_txt = findViewById(R.id.copy_text);
        sugg_button = findViewById(R.id.creare_btn);

// content text code scan part
        if (content_txt.getText().toString().length() > 0) {

            content_txt.setText(getIntent().getStringExtra("MyResult"));

            String data = content_txt.getText().toString();
            QRGEncoder qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT, 500);

            try {
                Bitmap qrBits = qrgEncoder.getBitmap();
                qr_code_img.setImageBitmap(qrBits);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
// content text code gallery part
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(this.openFileInput("myImage"));
                qr_code_img.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            content_txt.setText(getIntent().getStringExtra("MyResult"));
        }

// Give suggestion for different QR code
        IntentSuggestions();

    }

// calling suggestion
    private void MakeCall() {
        String number = content_txt.getText().toString();
        if (number.trim().length() > 0) {
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        123);
            } else {
                String dial = "tel:" + number;
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(dial));
                startActivity(intent);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 123) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                MakeCall();
            } else {
                Toast.makeText(this, "Permission Required.", Toast.LENGTH_LONG).show();
            }
        }
    }
//  Give suggestion for different QR code
    private void IntentSuggestions() {

        if (Linkify.addLinks(content_txt, Linkify.PHONE_NUMBERS)) {
            sugg_button.setText("Call");
            sugg_button.setOnClickListener(this);
        }
    }


    // Action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.scan_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.download:
                // checkpermission() called and then if-else
                // used to confirm for status of permission.
//                checkpermission();
//                if (!checkpermission()) {
//                    checkpermission();
//                } else {
//                   // saveToGallery();
//                }
                break;

            default:

        }
        return super.onOptionsItemSelected(item);

    }

// For calling
    @Override
    public void onClick(View v) {
        MakeCall();
    }
}
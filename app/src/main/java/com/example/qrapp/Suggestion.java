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
import android.text.TextUtils;
import android.text.util.Linkify;
import android.util.Log;
import android.util.Patterns;
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

    private static final String TAG = "Suggestion";


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
// Give suggestion for different QR code   btn
        sugg_button.setOnClickListener(this);
    }

    // calling suggestion or call Intent
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
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(dial));
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

    // Intent for browser
    private void BrowserIntent() {
        String url = content_txt.getText().toString();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    // Intent for email
    private void EmailIntent(String addresses) {
        String url = content_txt.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + url));
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        startActivity(intent);
    }


    // check for phone number
    private boolean isValidPhone(String phone) {
        String regex = "\\s*\\btel:\\b\\s*";
        phone = phone.replaceAll(regex, "");
        if (phone.trim().length() < 0) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(phone).matches();
        }
    }

    // check for URL
    private boolean isValidURL(String potentialUrl) {
        if (potentialUrl.trim().length() < 0) {
            return false;
        } else {
            return Patterns.WEB_URL.matcher(potentialUrl).matches();
        }
    }

    // check for email
    public boolean isValidEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }


    //  Give suggestion for different QR code
    private void IntentSuggestions() {

        if (isValidPhone(content_txt.getText().toString())) {
            sugg_button.setText("Call");
        } else {
            isValidURL(content_txt.getText().toString());
            sugg_button.setText("Open in Browser");

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
        if (v == sugg_button) {
            if (isValidPhone(content_txt.getText().toString())) {
                MakeCall();
            } else {
                isValidURL(content_txt.getText().toString());
                BrowserIntent();

            }

        }
    }
}
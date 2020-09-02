package com.example.qrapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.MailTo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
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

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class Suggestion extends AppCompatActivity implements View.OnClickListener {

    ImageView qr_code_img, delete_img, share_img, copy_img;
    TextView content_txt, delete_txt, share_txt, copy_txt;
    Button sugg_button;

    private static final String TAG = "Suggestion";

    boolean mPermission = false;
    boolean isQRGenerated = true;


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

    // Intent for search on web
    private void SearchIntent() {
        String web = content_txt.getText().toString();
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, web);
        startActivity(intent);
    }
    // Intent for sms
   private void SentMessage() {
        try {
            String msg = content_txt.getText().toString();
            Intent smsIntent = new Intent(Intent.ACTION_VIEW);
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.putExtra("sms_body", msg);
            startActivity(smsIntent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "No SIM Found", Toast.LENGTH_LONG).show();
        }
    }

     //Intent for email
    private void EmailIntent( String to, String subject, String body) {
        String url = content_txt.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:" + url));
        intent.putExtra(Intent.EXTRA_EMAIL, to);
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        intent.putExtra(Intent.EXTRA_TEMPLATE,body);
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

    // check for SMS
    private boolean isValidSMS(String sms) {
        if (sms.trim().length() < 0) {
            return false;
        } else {
            sms = content_txt.getText().toString();
            if(sms.startsWith("sms:")){
                return  true;
            } else if(sms.startsWith("SMSTO:")){
                return true;
            }
        }
        return  false;
    }



    // check for email
    private boolean isValidEmail(String email) {
        if (email.trim().length() < 0) {
            return false;
        } else {
            email = content_txt.getText().toString();
            if (email.startsWith("mailto:")) {
                MailTo mt = MailTo.parse(email);
                String mail = mt.getTo();
                return Patterns.EMAIL_ADDRESS.matcher(mail).matches();
            } else if(email.startsWith("MATMSG:TO:")){
//                MailTo mt = MailTo.parse(email);
//                String mail2 = mt.getTo();
//                return Patterns.EMAIL_ADDRESS.matcher(mail2).matches();
            } else if(email.startsWith("SMTP:")){
//                MailTo mt3 = MailTo.parse(email);
//                String mail3 = mt3.getTo();
//                return Patterns.EMAIL_ADDRESS.matcher(mail3).matches();
            }
        }
        return true;
    }

    //  Give suggestion for different QR code
    private void IntentSuggestions() {

        if (isValidPhone(content_txt.getText().toString())) {
            sugg_button.setText("Call");
        } else if(isValidURL(content_txt.getText().toString())){
            sugg_button.setText("Open in Browser");
        } else  if(isValidSMS(content_txt.getText().toString())){
            sugg_button.setText("Send SMS");
        }else {
            sugg_button.setText("Search On Web");
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
//                 checkpermission() called and then if-else
//                 used to confirm for status of permission.
                checkpermission();
                if (!checkpermission()) {
                    checkpermission();
                } else {
                    saveToGallery();
                }
                break;

            default:

        }
        return super.onOptionsItemSelected(item);

    }
// save qr code
    private void saveToGallery () {
        // checking for imageview is empty or not.

        if (!isQRGenerated) {
            Toast.makeText(this, "QR code not generated.!", Toast.LENGTH_SHORT).show();
        } else {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) qr_code_img.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();

            FileOutputStream fileOutputStream = null;
            File file = Environment.getExternalStorageDirectory();
            File dir = new File(file.getAbsolutePath() + "/Qr code scan");
            dir.mkdir();

            String filename = String.format("%d.png", System.currentTimeMillis());
            File outfile = new File(dir, filename);

            try {
                fileOutputStream = new FileOutputStream(outfile);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                Toast.makeText(this, "QR code saved \nInternal storage/Qr code scan", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.d(TAG, "saveToGallery() EXCEPTION : " + e.getMessage());
                Toast.makeText(this, "Could not Download.!!!", Toast.LENGTH_SHORT).show();
            }
        }

    }
    // permission for storage
    private boolean checkpermission () {
        // checkpermission returns boolean value.

        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};
        Dexter.withActivity(this)
                .withPermissions(permissions)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        Log.d(TAG, "onPermissionsChecked() Report = " + report);
                        if (report.areAllPermissionsGranted()) {
                            mPermission = true;
                            Toast.makeText(Suggestion.this, "Permissions granted", Toast.LENGTH_SHORT).show();
                        } else {
                            mPermission = false;
                            Toast.makeText(Suggestion.this, "Permissions are Required.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }

                }).check();
        return mPermission;
    }

    // For calling
    @Override
    public void onClick(View v) {
        if (v == sugg_button) {
            if (isValidPhone(content_txt.getText().toString())) {
                MakeCall();
            } else if(isValidURL(content_txt.getText().toString())){
                BrowserIntent();
            } else if(isValidSMS(content_txt.getText().toString())){
                SentMessage();
            }
            else {
                SearchIntent();
            }

        }
    }
}
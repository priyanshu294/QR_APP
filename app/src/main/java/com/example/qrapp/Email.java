package com.example.qrapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

import static java.security.AccessController.getContext;

public class Email extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Email Class";
    // variable name changed .
    boolean mPermission = false;
    boolean isQRGenerated = false;

    Button button;
    ImageView imageView;
    EditText editText_add, editText_sub, editText_mes;


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


        // onCLick() is called at last to make code look cleaner , please use this way from future.
        button.setOnClickListener(this);
    }
    // Action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.download:
                // checkpermission() called and then if-else
                // used to confirm for status of permission.
                checkpermission();
                if (!checkpermission()) {
                    checkpermission();
                } else {
                    saveToGallery();
                }
                break;

            case R.id.delete:
                deleteImage();
                break;

            case R.id.share:
                if(!isQRGenerated){
                    Toast.makeText(this, "Please Create QR Code.!", Toast.LENGTH_SHORT).show();
                }else {
                    shareImage();
                }
                break;


            default:

        }
        return super.onOptionsItemSelected(item);

    }

    private void deleteImage() {
        if (!isQRGenerated) {
            Toast.makeText(this, "QR code not generated.!", Toast.LENGTH_SHORT).show();
        } else {
            imageView.setImageDrawable(null);
            editText_add.getText().clear();
            editText_mes.getText().clear();
            editText_sub.getText().clear();
            Toast.makeText(this, "Delete QR Code", Toast.LENGTH_SHORT).show();
        }
    }


    private void shareImage() {
        // share using File Provider


            Drawable drawable = imageView.getDrawable();
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

                try {
                    File file = new File(getApplicationContext().getExternalCacheDir(), File.separator + "image.png");
                    FileOutputStream fOut = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                    fOut.flush();
                    fOut.close();
                    file.setReadable(true, false);
                    final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", file);

                    intent.putExtra(Intent.EXTRA_STREAM, photoURI);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setType("image/png");

                    startActivity(Intent.createChooser(intent, "Share image via"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

        }


        private void saveToGallery () {
            // checking for imageview is empty or not.

            if (!isQRGenerated) {
                Toast.makeText(this, "QR code not generated.!", Toast.LENGTH_SHORT).show();
            } else {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();

                FileOutputStream fileOutputStream = null;
                File file = Environment.getExternalStorageDirectory();
                File dir = new File(file.getAbsolutePath() + "/Qr code");
                dir.mkdir();

                String filename = String.format("%d.png", System.currentTimeMillis());
                File outfile = new File(dir, filename);

                try {
                    fileOutputStream = new FileOutputStream(outfile);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    Toast.makeText(this, "QR code saved \nInternal storage/Qr code", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.d(TAG, "saveToGallery() EXCEPTION : " + e.getMessage());
                    Toast.makeText(this, "Could not Download.!!!", Toast.LENGTH_SHORT).show();
                }
            }

        }

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
                                Toast.makeText(Email.this, "Permissions granted", Toast.LENGTH_SHORT).show();
                            } else {
                                mPermission = false;
                                Toast.makeText(Email.this, "Permissions are Required.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                            token.continuePermissionRequest();
                        }


                    }).check();
            return mPermission;
        }

        // used to confirm if imageview is empty or not
        // BUT in this case its never empty as you have made its background grey  .
        private boolean hasImage (@NonNull ImageView view){
            Drawable drawable = view.getDrawable();
            boolean hasImage = (drawable != null);

            if (hasImage && (drawable instanceof BitmapDrawable)) {
                hasImage = ((BitmapDrawable) drawable).getBitmap() != null;
            }

            return hasImage;
        }

        @Override
        public void onClick (View view){
            if (view == button) {
                String data1 = "email: " + (editText_add.getText().toString()) + "\n sub: " +
                        (editText_sub.getText().toString()) + "\n body: " + (editText_mes.getText().toString());

                String data_email = editText_add.getText().toString();
                String data_sub = editText_sub.getText().toString();
                String data_mes = editText_mes.getText().toString();

                if (data_email.trim().isEmpty()) {
                    editText_add.setError("Value Required.");
                } else if (data_sub.trim().isEmpty()) {
                    editText_sub.setError("Value Required.");
                } else if (data_mes.trim().isEmpty()) {
                    editText_mes.setError("Value Required.");
                } else {
                    QRGEncoder qrgEncoder = new QRGEncoder(data1, null, QRGContents.Type.TEXT, 500);

                    try {
                        Bitmap qrBits = qrgEncoder.getBitmap();

                        imageView.setImageBitmap(qrBits);
                        isQRGenerated = true;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    }

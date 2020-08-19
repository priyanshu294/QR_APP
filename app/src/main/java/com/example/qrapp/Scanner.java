package com.example.qrapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Scanner extends AppCompatActivity implements View.OnClickListener {

    CodeScanner codeScanner;
    CodeScannerView scannView;
    TextView resultData;
    Button pick_up;


    private static final int Gallery_REQUEST_CODE = 123;

    public static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        scannView = findViewById(R.id.scannerView);
        codeScanner = new CodeScanner(this, scannView);
        resultData = findViewById(R.id.resultsOfQr);
        pick_up = findViewById(R.id.pick_up);

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resultData.setText(result.getText());
                        startActivity(new Intent(getApplicationContext(), Suggestion.class));
                    }
                });

            }
        });
        // scan qr code
        scannView.setOnClickListener(this);
        // pick photos from gallery
        pick_up.setOnClickListener(this);



    }

    // gallery take qr code image
    public void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select QR code"), Gallery_REQUEST_CODE);

    }

    @Override

    protected void onActivityResult(int reqCode, int resultCode, Intent data) {

        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {

            try {

                final Uri imageUri = data.getData();

                final InputStream imageStream = getContentResolver().openInputStream(imageUri);

                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);


                try {

                    Bitmap bMap = selectedImage;

                    String contents = null;


                    int[] intArray = new int[bMap.getWidth() * bMap.getHeight()];

                    bMap.getPixels(intArray, 0, bMap.getWidth(), 0, 0, bMap.getWidth(), bMap.getHeight());


                    LuminanceSource source = new RGBLuminanceSource(bMap.getWidth(), bMap.getHeight(), intArray);

                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));


                    Reader reader = new MultiFormatReader();

                    Result result = reader.decode(bitmap);

                    contents = result.getText();

                    resultData.setText(contents);

                    Log.d(TAG, "onActivityResult() CONTENT =" + contents);

                    Toast.makeText(getApplicationContext(), contents, Toast.LENGTH_LONG).show();



                } catch (Exception e) {

                    Log.d(TAG, "onActivityResult() ERROR " + e.getMessage());

                }

                //  image_view.setImageBitmap(selectedImage);

            } catch (FileNotFoundException e) {

                Log.d(TAG, "onActivityResult() ERROR " + e.getMessage());

                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();

            }

        } else {

            Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();

        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        requestForCamera();

    }
// permission for camera

    public void requestForCamera() {
        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                codeScanner.startPreview();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                Toast.makeText(Scanner.this, "Camera Permission is Required.", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();

            }
        }).check();
    }


    @Override
    public void onClick(View v) {
        // scan qr code
        if (v == scannView) {
            codeScanner.startPreview();
        }
        // pick photos from gallery
        if (v == pick_up) {
            chooseImage();
            onResume();

        }
    }



}


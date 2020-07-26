package com.example.qrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

import java.util.Calendar;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class Event extends AppCompatActivity {

    EditText editText_title,editText_description,editText_location,editText_organizer,editText_startdate,editText_enddate;
    ImageView imageView;
    Button button;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        editText_title = findViewById(R.id.title_input);
        editText_description = findViewById(R.id.description_input);
        editText_location = findViewById(R.id.location_input);
        editText_organizer = findViewById(R.id.organizer_input);
        editText_startdate = findViewById(R.id.start_date_input);
        editText_enddate = findViewById(R.id.end_date_input);

        imageView = findViewById(R.id.qrcode_image);
        button = findViewById(R.id.creare_btn);


        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String data =  "title: "+(editText_title.getText().toString()) +"\n des: " + (editText_description.getText().toString())+"\n place:"+(editText_location.getText().toString())+"\n organizer:"+(editText_organizer.getText().toString())+"\n start:"+(editText_startdate.getText().toString())+" - "+(editText_enddate.getText().toString());

                if (data.isEmpty()) {
                    editText_title.setError("Value Required.");
                    editText_description.setError("Value Required.");


                }
                else {
                    QRGEncoder qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT, 500);

                    try {
                        Bitmap qrBits = qrgEncoder.getBitmap();

                        imageView.setImageBitmap(qrBits);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        editText_startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(Event.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                editText_startdate.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        editText_enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(Event.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                editText_enddate.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });



    }
}

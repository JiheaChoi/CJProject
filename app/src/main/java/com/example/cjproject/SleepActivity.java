package com.example.cjproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.cjproject.bean.SleepBean;
import com.squareup.timessquare.CalendarPickerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.util.Calendar.YEAR;

public class SleepActivity extends AppCompatActivity {
    Calendar calendar;
    CalendarView calendarView;
    Button btnAppl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);

        //btnAppl = (Button)findViewById(R.id.btnAppl);

        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(YEAR, 1);
        Date today = new Date();

        final CalendarPickerView datePicker = findViewById(R.id.calendar);
        datePicker.init(today, nextYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .withSelectedDate(today);


        datePicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                // String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(date);
                Calendar calSelected = Calendar.getInstance();
                calSelected.setTime(date);

                String selectedDate = "" + calSelected.get(YEAR)
                        + " " + (calSelected.get(Calendar.MONTH) + 1)
                        + " " + calSelected.get(Calendar.DAY_OF_MONTH);
                Toast.makeText(SleepActivity.this, selectedDate, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });

        List<Date> dates = datePicker.getSelectedDates();

        findViewById(R.id.btnAppl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(getApplicationContext(),datePicker.getSelectedDates().toString(),Toast.LENGTH_LONG).show();


                final AlertDialog.Builder builder = new AlertDialog.Builder(SleepActivity.this);
                builder.setTitle("외박 신청");
                builder.setMessage("선택한 기간으로 신청하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      //  bean.start = datePicker.getSelectedDates().toString();
                     //   String d = new SimpleDateFormat("yyyy년 MM월 dd일").format(datePicker.getSelectedDates());
                        Intent i = new Intent(SleepActivity.this, ReservationActivity.class);
                        i.putExtra("start",datePicker.getSelectedDates().toString());
                        startActivity(i);
                    }
                });

                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();


            }

        });



    }

}
package com.example.cjproject;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BoardActivity extends AppCompatActivity {

    FloatingActionButton btnNote, btnMop, btnTool, btnsleep, btnDelivery, btnQrcode;
    String hak2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        btnNote = findViewById(R.id.btnNote);
        btnMop = findViewById(R.id.btnMop);
        btnTool = findViewById(R.id.btnTool);
        btnsleep = (FloatingActionButton)findViewById(R.id.btnsleep);
        btnDelivery = findViewById(R.id.btnDelivery);
        btnQrcode = findViewById(R.id.btnQrcode);


        btnNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BoardActivity.this,NoticeActivity.class);
                startActivity(i);
            }
        });

        btnMop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BoardActivity.this,CleanActivity.class);
                startActivity(i);
            }
        });

        btnTool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BoardActivity.this,ToolActivity.class);
                startActivity(i);
            }
        });

        btnsleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BoardActivity.this,SleepActivity.class);
                startActivity(i);
            }
        });


        btnDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BoardActivity.this,DeliveryActivity.class);
                startActivity(i);
            }
        });

        btnQrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BoardActivity.this,PaymentActivity.class);
                startActivity(i);
            }
        });

    }

}
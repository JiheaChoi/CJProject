package com.example.cjproject;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DeliveryActivity extends AppCompatActivity {

    TextView txtChina, txtKim, txtBhc, txtSin;
    Button btnChina, btnKim, btnBhc, btnSin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        txtChina = (TextView) findViewById(R.id.txtChina);
        txtKim = (TextView) findViewById(R.id.txtKim);
        txtBhc = findViewById(R.id.txtBhc);
        txtSin = findViewById(R.id.txtSin);
        btnChina = (Button) findViewById(R.id.btnChina);
        btnKim = (Button) findViewById(R.id.btnKim);
        btnBhc = findViewById(R.id.btnBhc);
        btnSin = findViewById(R.id. btnSin);

        btnChina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent .ACTION_DIAL, Uri.parse("tel:02-979-5353"));
                startActivity(i);
            }
        });

        btnKim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent .ACTION_DIAL, Uri.parse("tel:02-978-0151"));
                startActivity(i);
            }
        });

        btnBhc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent .ACTION_DIAL, Uri.parse("tel:02-949-9222"));
                startActivity(i);
            }
        });

        btnSin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent .ACTION_DIAL, Uri.parse("tel:050-4457-7495"));
                startActivity(i);
            }
        });

    }



}
package com.example.cjproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cjproject.bean.SleepBean;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReservationActivity extends AppCompatActivity {

    private Button btn_sleep;
    private TextView txtNum, txtName, txtPlace;
    private EditText edtNum, edtName, edtPlace;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);


        btn_sleep = findViewById(R.id.btn_sleep);
        txtName = findViewById(R.id.txtName);
        txtNum = findViewById(R.id.txtNum);
        txtPlace = findViewById(R.id.txtPlace);
        edtName = findViewById(R.id.edtName);
        edtNum = findViewById(R.id.edtNum);
        edtPlace = findViewById(R.id.edtPlace);

        btn_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabase = FirebaseDatabase.getInstance();
                DatabaseReference firebaseRef = mDatabase.getReference();
                String id = firebaseRef.push().getKey();   //key를 id로 사용

                Intent i = getIntent();
                LoginActivity log1 = new LoginActivity();

                SleepBean bean = new SleepBean();
                bean.start = i.getExtras().getString("start");
                bean.start = new SimpleDateFormat("yyyy년 MM월 dd일").format(new Date());
                bean.id = id;
                bean.name = edtName.getText().toString();
                bean.place = edtPlace.getText().toString();
                bean.num = edtNum.getText().toString();
                bean.hak = log1.mLoginedInfoBean.userId;
                bean.today = new SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분 ss초").format(new Date());
                firebaseRef.child("sleep").child(bean.today).setValue(bean);

                Toast.makeText(ReservationActivity.this, "정보가 성공적으로 입력되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}

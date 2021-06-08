package com.example.cjproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.cjproject.adapter.CleanAdapter;
import com.example.cjproject.bean.CleanBean;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CleanActivity extends AppCompatActivity {

    private ListView lstContents;

    private FirebaseDatabase mDatabase;
    private Button btn_write;

    private List<CleanBean> mCleanList = new ArrayList<>();
    private CleanAdapter mCleanAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clean);

        mDatabase = FirebaseDatabase.getInstance();

        lstContents = findViewById(R.id.lstContents);

        check();

        findViewById(R.id.btn_write).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CleanActivity.this, CWriteActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.btnHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CleanActivity.this,BoardActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    void check(){
        mDatabase.getReference().child("clean").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //실시간으로 서버가 변경된 내용이 있을 경우 호출된다.

                //기존 리스트는 날려버린다.
                mCleanList.clear();

                //리스트를 서버로부터 온 데이터로 새로 만든다.
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CleanBean bean = snapshot.getValue(CleanBean.class);
                    mCleanList.add(bean);
                }

                mCleanAdapter = new CleanAdapter(CleanActivity.this, mCleanList);
                lstContents.setAdapter(mCleanAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    protected void onResume() {
        super.onResume();

        //내용 다 받아오기
        DatabaseReference clean = mDatabase.getReference().child("clean");

        if(clean!=null){
            clean.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //실시간으로 서버가 변경된 내용이 있을 경우 호출된다.

                    //기존 리스트는 날려버린다.
                    if(mCleanList!=null) {
                        mCleanList.clear();
                    }

                    //리스트를 서버로부터 온 데이터로 새로 만든다.
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                            CleanBean bean = snapshot1.getValue(CleanBean.class);
                            mCleanList.add(bean);
                        }
                    }

                    //어댑터 적용
                    mCleanAdapter = new CleanAdapter(CleanActivity.this, mCleanList);
                    lstContents.setAdapter(mCleanAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}

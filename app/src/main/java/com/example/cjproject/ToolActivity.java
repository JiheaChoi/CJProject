package com.example.cjproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.cjproject.adapter.ToolAdapter;
import com.example.cjproject.bean.ToolBean;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ToolActivity extends AppCompatActivity {

    private Button btnWrite;
    private ListView lstContents;


    private FirebaseDatabase mDatabase;

    private List<ToolBean> mToolAdapterList = new ArrayList<>();
    private ToolAdapter mToolAdapter;
    private ToolBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool);

        mDatabase = FirebaseDatabase.getInstance();

        //btnWrite = findViewById(R.id.btnWrite);
        lstContents = findViewById(R.id.lstContents);

        check();

        findViewById(R.id.btnHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ToolActivity.this,BoardActivity.class);
                startActivity(i);
            }
        });

        //글쓰기 버튼 이벤트
        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ToolActivity.this, TWriteActivity.class);
                startActivity(i);
            }
        });


    }// end OnCreate();

    void check(){
        mDatabase.getReference().child("tool").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //실시간으로 서버가 변경된 내용이 있을 경우 호출된다.

                //기존 리스트는 날려버린다.
                mToolAdapterList.clear();

                //리스트를 서버로부터 온 데이터로 새로 만든다.
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ToolBean bean = snapshot.getValue(ToolBean.class);
                    mToolAdapterList.add(bean);
                }

                mToolAdapter = new ToolAdapter(ToolActivity.this, mToolAdapterList);
                lstContents.setAdapter(mToolAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        //내용 다 받아오기
        DatabaseReference tool = mDatabase.getReference().child("tool");


        if(tool!=null){
            tool.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //실시간으로 서버가 변경된 내용이 있을 경우 호출된다.

                    //기존 리스트는 날려버린다.
                    if(mToolAdapterList!=null) {
                        mToolAdapterList.clear();
                    }

                    //리스트를 서버로부터 온 데이터로 새로 만든다.
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                            ToolBean bean = snapshot1.getValue(ToolBean.class);
                            mToolAdapterList.add(bean);
                        }
                    }

                    //어댑터 적용
                    mToolAdapter = new ToolAdapter(ToolActivity.this, mToolAdapterList);
                    lstContents.setAdapter(mToolAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
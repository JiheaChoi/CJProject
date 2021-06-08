package com.example.cjproject;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;


import com.example.cjproject.adapter.CastAdapter;
import com.example.cjproject.bean.CastBean;


import com.example.cjproject.bean.ChatBean;
import com.example.cjproject.bean.ToolBean;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;


public class CastActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private ListView lstBoard;
    private List<CastBean> mCastAdapterList = new ArrayList<>();
    private CastAdapter mCastAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast);

        mDatabase = FirebaseDatabase.getInstance();



        lstBoard = findViewById(R.id.lstBoard);

        try {
            String token = FirebaseInstanceId.getInstance().getToken();
            Log.d("IDService", "device token : " + token);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        //내용 다 받아오기
        DatabaseReference cast = mDatabase.getReference().child("cast");


        if(cast!=null){
            cast.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //실시간으로 서버가 변경된 내용이 있을 경우 호출된다.

                    //기존 리스트는 날려버린다.
                    if(mCastAdapterList!=null) {
                        mCastAdapterList.clear();
                    }

                    //리스트를 서버로부터 온 데이터로 새로 만든다.
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        CastBean bean = snapshot.getValue(CastBean.class);
                        mCastAdapterList.add(bean);

                    }




                    //어댑터 적용
                    mCastAdapter = new CastAdapter(CastActivity.this, mCastAdapterList);
                    lstBoard.setAdapter(mCastAdapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }   //end onCreate()

}
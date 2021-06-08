package com.example.cjproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cjproject.bean.CleanBean;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CWriteActivity extends AppCompatActivity {

    private Spinner spinner;
    private EditText edtTitle, edtContents;
    private Button btnWrite;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    private String category;
    private String categoryNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cwrite);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        edtTitle = findViewById(R.id.edtTitle);
        edtContents = findViewById(R.id.edtContents);
        btnWrite = findViewById(R.id.btnSave);
        spinner = findViewById(R.id.Spinner);

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = spinner.getSelectedItem().toString();
                upload();
                Intent i = new Intent(CWriteActivity.this, CleanActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void upload(){

        //프로그레스바 보이기 시작

        //이미지 업로드가 끝나면 호출되는 CallBack 메서드
        //해야될 일 : Uploaded된 이미지 URL과 사용자가 작성한
        //메모의 내용을 RealTime DB에 업로드 시킨다.
        DatabaseReference firebaseRef = mDatabase.getReference();
        String id = firebaseRef.push().getKey();

        //DATABASE 에 저장한다.
        CleanBean cleanBean = new CleanBean();
        LoginActivity log1 = new LoginActivity();

        cleanBean.id=id;
        cleanBean.title = edtTitle.getText().toString();
        cleanBean.contents =  edtContents.getText().toString();

        if(category.equals("1차")){
            categoryNum = "1";
        }else if(category.equals("2차")){
            categoryNum = "2";
        }else if(category.equals("3차")){
            categoryNum = "3";
        }else categoryNum = "4";

        cleanBean.category = categoryNum;

        firebaseRef.child("clean").child(categoryNum).child(cleanBean.id).setValue(cleanBean);
        Toast.makeText(CWriteActivity.this, "서버 글쓰기 성공", Toast.LENGTH_SHORT).show();

    }

}

package com.example.cjproject;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cjproject.bean.InfoBean;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class JoinActivity extends AppCompatActivity {

    private Button btnJoin;
    private EditText edtId, edtPass, edtName, edtRoom;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        edtId = findViewById(R.id.edtId);
        edtName = findViewById(R.id.edtName);
        edtPass = findViewById(R.id.edtPass);
        edtRoom = findViewById(R.id.edtRoom);
        btnJoin = findViewById(R.id.btnJoin);

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edtId.getText().toString() + "@naver.com";
                String pw = edtPass.getText().toString();
                createUser(id, pw);
            }
        });
    }

    private void createUser(String email, String pass) {
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    DatabaseReference firebaseRef = mDatabase.getReference();
                    String id = firebaseRef.push().getKey();   //key를 id로 사용

                    //Database에 저장한다.
                    final InfoBean bean = new InfoBean();
                    bean.id = id;
                    bean.room = edtRoom.getText().toString();
                    bean.pass = edtPass.getText().toString();
                    bean.userId = edtId.getText().toString() + "@naver.com";
                    bean.name = edtName.getText().toString();
                    //bean.address = strAdr;

                    //userEmail의 고유번호를 기준으로 사용자 데이터를 쌓기 위해서 고유키를 생성한다.
                    String userIdUUID = getUserIdFromUUID(bean.userId);

                    // 4. 서버에 infoBean 업데이트
                    firebaseRef.child("info").child(userIdUUID).setValue(bean);

                    Toast.makeText(JoinActivity.this, "정보가 성공적으로 입력되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(JoinActivity.this,LoginActivity.class);
                    startActivity(i);
                    finish();

                } else {
                    //회원가입 실패
                    Toast.makeText(JoinActivity.this, "중복된 학번입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static String getUserIdFromUUID(String userEamil) {
        long val = UUID.nameUUIDFromBytes(userEamil.getBytes()).getMostSignificantBits();
        return val + "";
    }
}
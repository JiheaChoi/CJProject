package com.example.cjproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cjproject.bean.InfoBean;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$");

    //로그인 사용자의 정보
    public static InfoBean mLoginedInfoBean;

    //파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase mDatabase;

    private EditText edtId, edtPass;
    private Button btnLogin, btnJoin;
    private CheckBox btnCheck;      //자동로그인 체크박스 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        edtId = findViewById(R.id.edtID);
        edtPass = findViewById(R.id.edtPass);
        btnJoin = findViewById(R.id.btnJoin);
        btnLogin = findViewById(R.id.btnLogin);


        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = edtId.getText().toString() + "@naver.com";
                String pass = edtPass.getText().toString();

                if(isValidEmail(id) && isValidPasswd(pass)) {
                    //로그인 하겠다. FireBase에

                    //로그인 실행
                    loginUser(id,pass);

                }


            }
        });
    }

    private void loginUser(String email, String pass) {
        //다이얼로그 보이기

        firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this,"로그인 성공",Toast.LENGTH_SHORT).show();

                    //TODO 서버에서 성공한 사람의 정보를 가져온다.
                    //데이터 목록을 Firebase로부터 가져온다.
                    // 5. 로그인 페이지에 돌아와서 서버 info database를 받아옴
                    String emailUUID = JoinActivity.getUserIdFromUUID(firebaseAuth.getCurrentUser().getEmail());
                    mDatabase.getReference().child("info").child(emailUUID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            // 실시간으로 서버가 변경된 내용이 있을 경우 호출된다.

                            InfoBean bean = dataSnapshot.getValue(InfoBean.class);
                            mLoginedInfoBean = bean;
                            mLoginedInfoBean.userId = edtId.getText().toString();
                            goBoardActivity();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });

                }
                else {
                    Toast.makeText(LoginActivity.this,"로그인 실패",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private boolean isValidEmail(String email) {
        if (email.isEmpty()) {
            // 이메일 공백
            Toast.makeText(LoginActivity.this,"이메일 공백입니다.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // 이메일 형식 불일치
            Toast.makeText(LoginActivity.this,"이메일 형식 불일치입니다.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    // 비밀번호 유효성 검사
    private boolean isValidPasswd(String password) {
        if (password.isEmpty()) {
            // 비밀번호 공백
            Toast.makeText(LoginActivity.this,"비밀번호 공백입니다.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            // 비밀번호 형식 불일치
            Toast.makeText(LoginActivity.this,"비밀번호 형식 불일치입니다.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


    private void goBoardActivity() {
        Intent i = new Intent(LoginActivity.this,BoardActivity.class);
        startActivity(i);
    }



    public static String getUserIdFromUUID(String userEamil) {
        long val = UUID.nameUUIDFromBytes(userEamil.getBytes()).getMostSignificantBits();
        return val + "";
    }
}

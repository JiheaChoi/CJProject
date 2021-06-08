package com.example.cjproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cjproject.bean.ToolBean;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

import static com.example.cjproject.JoinActivity.getUserIdFromUUID;

public class TWriteActivity extends AppCompatActivity {

    private ImageView imgReceiver;
    private TextView txtTitle, txtContents;
    private EditText edtTitle, edtContents;
    public static List<ToolBean> mToolBeanList = new ArrayList<ToolBean>();

    private FirebaseAuth mAuth;
    private FirebaseStorage mStorage;
    private FirebaseDatabase mDatabase;

    private ToolBean mToolBean;
    private Button btnSave;
    private String category;
    private String categoryNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twrite);

        mAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        txtTitle = findViewById(R.id.txtTitle);
        edtTitle = findViewById(R.id.edtTitle);
        edtContents = findViewById(R.id.edtContents);

        btnSave = findViewById(R.id.btnSave);
        mToolBean = new ToolBean();

        mToolBean = (ToolBean) getIntent().getSerializableExtra("detail");


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "신청완료";
                upload();
                finish();
            }
        });

    } // end onCreate()


    private void upload() {

        DatabaseReference firebaseRef = mDatabase.getReference();
        String id = firebaseRef.push().getKey();

        //Database에 저장한다.


        final ToolBean mToolBean = new ToolBean();

        mToolBean.id=id;

        //키 값 받아오기

        mToolBean.title = edtTitle.getText().toString();
        mToolBean.contents =  edtContents.getText().toString();
        mToolBean.category = "신청완료";

        if(category.equals("신청완료")) {
            categoryNum = "1";
        }else if(category.equals("처리완료")){
            categoryNum = "2";
        }else if(category.equals("처리중")){
            categoryNum = "3";
        }else if(category.equals("접수완료")){
            categoryNum = "4";
        }else categoryNum = "5";





        //userEmail의 고유번호를 기준으로 사용자 데이터를 쌓기 위해서 고유키를 생성한다.
        //String userIdUUID = getUserIdFromUUID(mToolBean.id);

        // 4. 서버에 infoBean 업데이트
        firebaseRef.child("tool").child(categoryNum).child(mToolBean.id).setValue(mToolBean);

        Toast.makeText(TWriteActivity.this, "정보가 성공적으로 입력되었습니다.", Toast.LENGTH_SHORT).show();

        finish();



    }//end Upload
}


package com.example.cjproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.cjproject.bean.BoardBean;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class BoardDetailActivity extends AppCompatActivity {

    private ImageView imgView;
    private TextView txtTitle, txtContents;
    public static BoardBean boardBean;
    private FirebaseDatabase mDatabase;
    private FirebaseStorage mStorage;
    private String mPhotoPath;
    private Uri photo;
    SharedPreferences SharedPre;
    private String locale;
    int locale_number;
    ArrayList<String> locales;
    ArrayAdapter adapter;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_detail);
        mDatabase = FirebaseDatabase.getInstance();
        Intent intent = getIntent();
        boardBean = (BoardBean) getIntent().getSerializableExtra(BoardBean.class.getName());

        SharedPre = getSharedPreferences("shared",MODE_PRIVATE);
        txtContents = findViewById(R.id.txtContents);
        txtTitle = findViewById(R.id.txtTitle);
        spinner = findViewById(R.id.spinner);

        txtTitle.setText(boardBean.title);
        txtContents.setText(boardBean.contents);
        imgView = findViewById(R.id.imgView);


        // Reference to an image file in Cloud Storage
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://cjproject-65b24.appspot.com/");

        //생성된 FirebaseStorage를 참조하는 storage 생성
        StorageReference storageRef = storage.getReference();

        storageRef.child("images/"+boardBean.imgName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                try {

                    Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    imgView.setImageBitmap(bm);

                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

               /* mPhotoPath = getPath(uri);
                Bitmap bitmap = BitmapFactory.decodeFile(mPhotoPath);
                //Bitmap rotatedBmp = roate(resizedBmp, exifDegree);
                imgView.setImageBitmap(bitmap);  */


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        //새로 추가

        /*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = SharedPre.getString("locale", getResources().getConfiguration().getLocales().get(0).getLanguage());
        }
        else {
            locale = SharedPre.getString("locale", Resources.getSystem().getConfiguration().locale.getLanguage());
        }

        switch (locale){
            case "ko" : {
                locale_number = 0;
                break;
            }
            case "en" : {
                locale_number = 1;
                break;
            }
            case "zh" : {
                locale_number = 2;
            }
        }

        int jellyId = Integer.parseInt(boardBean.id);
        txtContents.setText(getStringByLocal(this,jellyId,locale));
        locales = new ArrayList<>();

        locales.add(getStringByLocal(this,R.id.textView,locale));

        locales.add(getStringByLocal(R.string.ko))


*/


    }

  /*  public static String getStringByLocal(Activity context, int resId , String locale) {
            return getStringByLocalPlus17(context, resId, locale);
    }

    private static String getStringByLocalPlus17(Activity context, int res, String locale)
    {
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        configuration.setLocale(new Locale(locale));
        return context.createConfigurationContext(configuration).getResources().getString(res);
    }



*/

}

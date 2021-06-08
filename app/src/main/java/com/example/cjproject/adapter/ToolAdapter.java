package com.example.cjproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.cjproject.R;
import com.example.cjproject.ToolDetailActivity;
import com.example.cjproject.bean.ToolBean;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.List;

import static com.example.cjproject.JoinActivity.getUserIdFromUUID;
public class ToolAdapter extends BaseAdapter {

    private Context mContext;
    private List<ToolBean> mList;
    private Spinner spinner;
    private String category;
    private String categoryNum;
    private FirebaseDatabase mDatabase;


    public ToolAdapter(Context context, List<ToolBean> list){
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.view_tool, null);

        View layTopRow = convertView.findViewById(R.id.layTopRow);
        TextView txtTitle = convertView.findViewById(R.id.txtTitle);
        TextView txtState = convertView.findViewById(R.id.txtState);


        if(position == 0) {
            layTopRow.setVisibility(View.VISIBLE);
        } else {
            layTopRow.setVisibility(View.GONE);
        }

        final ToolBean bean = mList.get(position);

        txtTitle.setText(bean.title);
        txtState.setText(bean.category);

        /* spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category = spinner.getSelectedItem().toString();
                upload();

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });     */


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, ToolDetailActivity.class);
                i.putExtra(ToolBean.class.getName(), bean);

                mContext.startActivity(i);
            }
        });

        return convertView;
    }

   /* private void upload(){
        DatabaseReference firebaseRef = mDatabase.getReference();

        ToolBean bean = new ToolBean();

        if(category.equals("신청완료")) {
            categoryNum = "1";
        }else if(category.equals("처리완료")){
            categoryNum = "2";
        }else if(category.equals("처리중")){
            categoryNum = "3";
        }else if(category.equals("접수완료")){
            categoryNum = "4";
        }else categoryNum = "5";

        bean.category = categoryNum;

        String userIdUUID = getUserIdFromUUID(bean.id);

        firebaseRef.child("tool").child(categoryNum).child(userIdUUID).setValue(bean);


    } */

}
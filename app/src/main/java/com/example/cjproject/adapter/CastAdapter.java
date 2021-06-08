package com.example.cjproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;

import com.example.cjproject.R;
import com.example.cjproject.bean.CastBean;
import com.example.cjproject.fcm.MyFirebaseMessagingService;
import com.google.firebase.database.FirebaseDatabase;


import java.util.List;

public class CastAdapter extends BaseAdapter {

    private Context mContext;
    private List<CastBean> mList;

    private FirebaseDatabase mDatabase;

    public CastAdapter(Context context, List<CastBean> list) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.view_cast, null);
        mDatabase = FirebaseDatabase.getInstance();



        TextView txtTitle = convertView.findViewById(R.id.txtTitle);
        TextView txtContent = convertView.findViewById(R.id.txtContent);



        final CastBean castBean = mList.get(position);
        txtTitle.setText(castBean.title);
        txtContent.setText(castBean.content);

        return convertView;
    }
}
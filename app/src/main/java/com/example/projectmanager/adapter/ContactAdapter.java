package com.example.projectmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectmanager.R;

import java.util.List;

public class ContactAdapter extends BaseAdapter {
    Context context;
    List<List<String>> memberList;
    View.OnClickListener listener;        //定义点击事件


    public ContactAdapter(Context context, List<List<String>> memberList) {
        this.context = context;
        this.memberList = memberList;
    }

    @Override
    public int getCount() {
        System.out.println(this.memberList.size());
        return this.memberList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.memberList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.contact_item, parent, false);
        TextView nickname = (TextView)view.findViewById(R.id.nickname);
        TextView account = (TextView)view.findViewById(R.id.account);
        ImageView add = (ImageView)view.findViewById(R.id.remove_image);

        List<String> columnList = this.memberList.get(position);
        System.out.println(columnList);
        nickname.setText(columnList.get(0));
        account.setText(columnList.get(1));

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemAddListener.onAddClick(position);
            }
        });

        return view;
    }

    //添加联系人add_image的监听接口
    public interface onItemAddListener{
        void onAddClick(int position);
    }

    private onItemAddListener mOnItemAddListener;

    public void setOnItemDeleteClickListener(onItemAddListener mOnItemAddListener) {
        this.mOnItemAddListener = mOnItemAddListener;
    }
}

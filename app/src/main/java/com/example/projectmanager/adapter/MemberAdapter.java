package com.example.projectmanager.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectmanager.R;
import com.example.projectmanager.model.Member;
import com.example.projectmanager.model.Project;

import java.util.List;

public class MemberAdapter extends BaseAdapter {
    Context context;
    String product_id;
    Boolean isManager;
    List<Member> memberList;

    public MemberAdapter(Context context, String product_id, Boolean isManager, List<Member> members) {
        this.context = context;
        this.product_id = product_id;
        this.isManager = isManager;
        this.memberList = members;
    }


    @Override
    public int getCount() {
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
        View view = inflater.inflate(R.layout.member_item, parent, false);

        TextView nickname = view.findViewById(R.id.nickname);
        TextView account = view.findViewById(R.id.account);
        ImageView remove = (ImageView)view.findViewById(R.id.remove_image);
        if(isManager){
            remove.setVisibility(View.VISIBLE);
        }
//        SimpleDraweeView ivAvatar = view.findViewById(R.id.iv_avatar);

        Member member = this.memberList.get(position);

        nickname.setText(member.getName());
        account.setText(member.getUsername());
//        Uri uri = Uri.parse(member.getAvatarUrl());
//        ivAvatar.setImageURI(uri);
//
//        ImageView btDelete = view.findViewById(R.id.member_delete_button);
//        ImageView btShow = view.findViewById(R.id.member_personal_button);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemAddListener.onAddClick(position);
            }
        });

        return view;
    }

    public interface onItemAddListener{
        void onAddClick(int position);
    }

    private MemberAdapter.onItemAddListener mOnItemAddListener;

    public void setOnItemDeleteClickListener(MemberAdapter.onItemAddListener mOnItemAddListener) {
        this.mOnItemAddListener = mOnItemAddListener;
    }
}
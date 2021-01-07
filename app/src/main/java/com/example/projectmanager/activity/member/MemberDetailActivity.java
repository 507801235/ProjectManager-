package com.example.projectmanager.activity.member;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.projectmanager.R;
import com.example.projectmanager.model.Member;
import com.example.projectmanager.util.NetCallBack;
import com.example.projectmanager.util.Request;
import com.facebook.drawee.view.SimpleDraweeView;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class MemberDetailActivity extends AppCompatActivity {
    Member member;
    private SimpleDraweeView ivAvatar;
    private TextView tv_nickname;
    private TextView tv_account;
    private TextView tv_phone;
    private TextView tv_position;
    private TextView tv_synopsis;
    private String account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_detail);
        //修改页面背景色
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.gray);
        this.getWindow().setBackgroundDrawable(drawable);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //设置返回键
        initView();
        Intent intent = this.getIntent();
        account = intent.getStringExtra("account");     //获取上个页面传来的account
        getInfo();
    }

    private void getInfo() {
        Request.clientGet( "account/" + account, new NetCallBack() {
            @Override
            public void onMySuccess(JSONObject result) {
                Member member = JSON.toJavaObject(result, Member.class);
                tv_nickname.setText(member.getNickName());
                tv_account.setText(member.getUsername());
                tv_phone.setText(member.getPhoneNum());
                tv_position.setText(member.getPosition());
                tv_synopsis.setText(member.getSynopsis());
                Uri uri = Uri.parse(member.getAvatarUrl());
                ivAvatar.setImageURI(uri);
            }

            @Override
            public void onMyFailure(String error, String data) {

            }
        });
    }

    private void initView() {
        tv_nickname = (TextView) findViewById(R.id.nickname);
        tv_account = (TextView) findViewById(R.id.account);
        tv_phone = (TextView) findViewById(R.id.phone);
        tv_position = (TextView) findViewById(R.id.position);
        tv_synopsis = (TextView) findViewById(R.id.synopsis);
        ivAvatar = (SimpleDraweeView) findViewById(R.id.iv_avatar);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
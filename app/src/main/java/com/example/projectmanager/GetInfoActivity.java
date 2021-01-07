package com.example.projectmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.opengl.ETC1;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.projectmanager.activity.project.EditProjectActivity;
import com.example.projectmanager.model.GlobalData;
import com.example.projectmanager.util.Check;
import com.example.projectmanager.util.NetCallBack;
import com.example.projectmanager.util.Request;
import com.facebook.drawee.view.SimpleDraweeView;

import cz.msebera.android.httpclient.entity.StringEntity;

public class GetInfoActivity extends AppCompatActivity {
    /*
    * 个人信息页面
    * */
    private SimpleDraweeView ivAvatar;
    private TextView tv_nickname;
    private TextView tv_account;
    private EditText et_nickname;
    private EditText et_phone;
    private EditText et_position;
    private EditText et_synopsis;
    private Button bt_save;
    private String nickname;
    private String account;
    private String phone;
    private String position;
    private String synopsis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        GlobalData app = (GlobalData) getApplication();
        initView();
        Uri uri = Uri.parse(app.getAvatarUrl());
        ivAvatar.setImageURI(uri);
        nickname = app.getNickName();
        account = app.getUsername();
        phone = app.getPhoneNum();
        position = app.getPosition();
        synopsis = app.getSynopsis();
        tv_nickname.setText(nickname);
        tv_account.setText(account);
        et_nickname.setText(nickname);
        et_position.setText(position);
        et_phone.setText(phone);
        et_synopsis.setText(synopsis);

        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nickname = et_nickname.getText().toString();
                position = et_position.getText().toString();
                phone = et_phone.getText().toString();
                synopsis = et_synopsis.getText().toString();
                if(nickname.equals("")||position.equals("")||phone.equals("")||synopsis.equals("")){
                    Toast.makeText(GetInfoActivity.this,"请填写完整信息", Toast.LENGTH_LONG).show();
                }else{
                    if(!Check.isPhone(phone)){
                        Toast.makeText(GetInfoActivity.this,"号码格式不正确", Toast.LENGTH_LONG).show();
                        et_phone.setText("");
                    }else{
                        JSONObject body = new JSONObject();
                        body.put("username", app.getUsername());
                        body.put("nickName",nickname);
                        body.put("phoneNum", phone);
                        body.put("position", position);
                        body.put("synopsis", synopsis);
                        StringEntity entity = new StringEntity(body.toJSONString(), "UTF-8");
                        Request.clientPut(GetInfoActivity.this, "/account", entity, new NetCallBack() {
                            @Override
                            public void onMySuccess(JSONObject result) {
                                Toast.makeText(GetInfoActivity.this,"保存成功", Toast.LENGTH_LONG).show();
                                tv_nickname.setText(nickname);
                            }

                            @Override
                            public void onMyFailure(String error, String data) {
                                Toast.makeText(GetInfoActivity.this,error, Toast.LENGTH_LONG).show();
                                System.out.println(data);
                            }
                        });
                    }
                }
            }
        });
    }

    private void initView() {
        ivAvatar = (SimpleDraweeView) findViewById(R.id.iv_avatar);
        tv_nickname = (TextView) findViewById(R.id.nickname);
        tv_account = (TextView) findViewById(R.id.account);
        et_nickname = (EditText) findViewById(R.id.et_nickname);
        et_position = (EditText) findViewById(R.id.et_position);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_synopsis = (EditText) findViewById(R.id.et_synopsis);
        bt_save = (Button) findViewById(R.id.bt_save);
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
package com.example.projectmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.projectmanager.activity.member.ProjectMemberActivity;
import com.example.projectmanager.ui.my.MyFragment;
import com.example.projectmanager.util.NetCallBack;
import com.example.projectmanager.util.Request;

import cz.msebera.android.httpclient.entity.StringEntity;

public class SettingActivity extends AppCompatActivity {
    private static String[] title = {"修改密码", "退出登录"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //修改页面背景色
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.gray);
        this.getWindow().setBackgroundDrawable(drawable);
        ListView menuList = findViewById(R.id.list_view);
        menuList.setAdapter(new MyAdapter());
        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        setPassword();
                        break;
                    case 1:
                        androidx.appcompat.app.AlertDialog dialog = new AlertDialog.Builder(SettingActivity.this).setTitle("提示").setMessage("是否要退出登录").setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                JSONObject body = new JSONObject();
                                StringEntity entity = new StringEntity(body.toJSONString(), "UTF-8");
                                Request.clientPost(SettingActivity.this, "/logout", entity, new NetCallBack() {
                                    @Override
                                    public void onMySuccess(JSONObject result) {
                                        Toast.makeText(SettingActivity.this, "登出成功!", Toast.LENGTH_LONG).show();
                                        logout();
                                    }

                                    @Override
                                    public void onMyFailure(String error, String data) {
                                        Toast.makeText(SettingActivity.this, error, Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }).show();
                        break;
                    default: break;
                }
            }
        });
    }

    private void setPassword() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void logout() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);   //销毁栈中所有页面，并启动新页面
        startActivity(intent);
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return title.length;
        }

        @Override
        public Object getItem(int position) {
            return title[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = SettingActivity.this.getLayoutInflater();
            View view = inflater.inflate(R.layout.menu_item, parent, false);
            TextView menu = (TextView)view.findViewById(R.id.menu_title);
            menu.setText(title[position]);
            return view;
        }
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
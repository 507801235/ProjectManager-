package com.example.projectmanager.activity.member;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.projectmanager.R;
import com.example.projectmanager.adapter.ContactAdapter;
import com.example.projectmanager.model.Project;
import com.example.projectmanager.util.Check;
import com.example.projectmanager.util.NetCallBack;
import com.example.projectmanager.util.Request;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.entity.StringEntity;

public class InviteMemberActivity extends AppCompatActivity {
    Project project;
    private String project_uid;
    private androidx.appcompat.widget.SearchView searchView;
    private TextView tv_result;
    private String account;
    private String nickname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_member);
        //修改页面背景色
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.gray);
        this.getWindow().setBackgroundDrawable(drawable);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //设置返回键
        Intent intent = this.getIntent();
        project = (Project) intent.getSerializableExtra("project");
        project_uid = project.getProject_uid();
        initView();
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                account = query;
                if(account.equals("")){
                    Toast.makeText(InviteMemberActivity.this, "请输入账号！", Toast.LENGTH_LONG).show();
                }
                if(!Check.isEmail(account)){
                    Toast.makeText(InviteMemberActivity.this, "邮箱格式不正确！", Toast.LENGTH_LONG).show();
                    searchView.setQuery("",false);
                }else{
                    ListView member_list = findViewById(R.id.list_view);
                    Request.clientGet( "/account/" + account, new NetCallBack() {
                        @Override
                        public void onMySuccess(JSONObject result) {
                            tv_result.setVisibility(View.VISIBLE);
                            String listString = JSONObject.toJSONString(result);
                            System.out.println("ListString:"+listString);
                            nickname = result.getString("nickName");    //查询到的用户昵称
                            account = result.getString("username");     //查询到的用户账号
                            List<List<String>> memberList = new ArrayList<List<String>>();
                            List<String> columnList = new ArrayList<String>();
                            columnList.add(0,nickname);
                            columnList.add(1,account);
                            memberList.add(0,columnList);
                            System.out.println(memberList);
                            ContactAdapter adapter = new ContactAdapter(InviteMemberActivity.this, memberList);
                            member_list.setAdapter(adapter);
                            member_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    List<String> columnList = memberList.get(position);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("username", account);
                                    Intent intent = new Intent(InviteMemberActivity.this, MemberDetailActivity.class);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            });
                            adapter.setOnItemDeleteClickListener(new ContactAdapter.onItemAddListener() {
                                @Override
                                public void onAddClick(int position) {
                                    JSONObject body = new JSONObject();
                                    StringEntity entity = new StringEntity(body.toJSONString(), "UTF-8");
                                    Request.clientPost(InviteMemberActivity.this, "project/" + project_uid + "/invite/" + account, entity, new NetCallBack() {
                                        @Override
                                        public void onMySuccess(JSONObject result) {
                                            Toast.makeText(InviteMemberActivity.this, "邀请已发出", Toast.LENGTH_LONG).show();
                                        }

                                        @Override
                                        public void onMyFailure(String error, String data) {
                                            Toast.makeText(InviteMemberActivity.this, error, Toast.LENGTH_LONG).show();
                                        }
                                    });

                                }
                            });
                        }

                        @Override
                        public void onMyFailure(String error, String data) {
                            Toast.makeText(InviteMemberActivity.this, error, Toast.LENGTH_LONG).show();
                            searchView.setQuery("",false);
                        }
                    });
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void initView() {
        searchView = (SearchView) findViewById(R.id.search);
        tv_result = (TextView) findViewById(R.id.search_result);
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
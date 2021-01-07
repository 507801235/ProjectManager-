package com.example.projectmanager.activity.member;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.projectmanager.R;
import com.example.projectmanager.adapter.MemberAdapter;
import com.example.projectmanager.model.GlobalData;
import com.example.projectmanager.model.Member;
import com.example.projectmanager.model.Project;
import com.example.projectmanager.util.NetCallBack;
import com.example.projectmanager.util.Request;

import java.util.List;

import cz.msebera.android.httpclient.entity.StringEntity;

public class ProjectMemberActivity extends AppCompatActivity {
    Project project;
    Member member;
    List<Member> memberList;
    MemberAdapter memberAdapter;
    private ListView lv_member;
    private String project_uid;
    Boolean isManager = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_member);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);   //设置toolbar
        //修改页面背景色
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.gray);
        this.getWindow().setBackgroundDrawable(drawable);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //设置返回键

        Intent intent  = this.getIntent();
        project = (Project) intent.getSerializableExtra("project");
        project_uid = project.getProject_uid();     //获取项目uid
        member = (Member) intent.getSerializableExtra("member");
        GlobalData app = (GlobalData) getApplication();
        if (project.getManager_uid().equals(app.getUid())) {
            isManager = true;
        }
        lv_member = (ListView) findViewById(R.id.list_view);
        Request.clientGet( "project/" + project_uid + "/member", new NetCallBack() {
            @Override
            public void onMySuccess(JSONObject result) {
                JSONArray list = result.getJSONArray("list");
                String listString = JSONObject.toJSONString(list);
                memberList = JSONObject.parseArray(listString, Member.class);//把字符串转换成集合
                memberAdapter = new MemberAdapter(ProjectMemberActivity.this, project_uid, isManager, memberList);
                lv_member.setAdapter(memberAdapter);
                lv_member.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(ProjectMemberActivity.this, MemberDetailActivity.class);
                        member = memberList.get(position);
                        intent.putExtra("account", member.getUsername());
                        startActivity(intent);
                    }
                });
                memberAdapter.setOnItemDeleteClickListener(new MemberAdapter.onItemAddListener() {
                    @Override
                    public void onAddClick(int position) {
                        androidx.appcompat.app.AlertDialog dialog = new AlertDialog.Builder(ProjectMemberActivity.this).setTitle("提示").setMessage("是否移除该成员").setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                member = memberList.get(position);
                                JSONObject body = new JSONObject();
                                body.put("project_uid", project_uid);
                                body.put("username", member.getUsername());
                                StringEntity entity = new StringEntity(body.toJSONString(), "UTF-8");
                                Request.clientPost(ProjectMemberActivity.this, "/project/" + project_uid + "/remove/" + member.getUsername(), entity, new NetCallBack() {
                                    @Override
                                    public void onMySuccess(JSONObject result) {
                                        Toast.makeText(ProjectMemberActivity.this, "移除成功!", Toast.LENGTH_LONG).show();
                                        memberList.remove(position);
                                        memberAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onMyFailure(String error, String data) {
                                        Toast.makeText(ProjectMemberActivity.this, error, Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }).show();

                    }
                });
            }

            @Override
            public void onMyFailure(String error, String data) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.member_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.invite_member:
                Intent intent = new Intent(this, InviteMemberActivity.class);     //页面跳转至更新项目
                Bundle bundle = new Bundle();
                bundle.putSerializable("project", project);
                intent.putExtras(bundle);
                this.startActivity(intent);
        }
        return true;
    }
}
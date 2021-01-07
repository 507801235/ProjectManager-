package com.example.projectmanager.activity.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.projectmanager.activity.dynamic.ProjectDynamicActivity;
import com.example.projectmanager.R;
import com.example.projectmanager.activity.member.ProjectMemberActivity;
import com.example.projectmanager.activity.task.CreateTaskActivity;
import com.example.projectmanager.activity.task.DoneTaskActivity;
import com.example.projectmanager.activity.task.ProjectTaskActivity;
import com.example.projectmanager.model.GlobalData;
import com.example.projectmanager.model.Project;
import com.example.projectmanager.util.NetCallBack;
import com.example.projectmanager.util.Request;

public class ProjectDetailActivity extends AppCompatActivity {
    private Menu project_menu;
    private TextView tv_project_name;
    private TextView tv_project_uid;
    private TextView number_dynamic;
    private TextView number_member;
    private TextView number_doing;
    private TextView number_done;
    private TextView tv_manager_name;
    private TextView tv_project_rate;
    private TextView tv_start_end;
    private TextView tv_project_plan;
    private TextView tv_project_synopsis;
    private String project_name;
    private String project_uid;
    private String manager_nickname;
    private String manager_account;
    private String project_rate;
    private String projectStartTime;
    private String projectEndTime;
    private String project_plan;
    private String project_synopsis;

    Boolean isManager = false;
    Project project;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);
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
        project_name = project.getProjectName();    //获取项目名称
        manager_nickname = project.getManagerNickName();    //获取项目经理昵称
        manager_account = project.getManagerName();     //获取项目经理账号
        project_rate = project.getProjectRate().toString();     //获取项目进度
        projectStartTime = project.getProjectStartTime();       //获取项目开始时间
        projectEndTime = project.getProjectEndTime();       //获取项目结束时间
        project_plan = project.getProjectPlan();    //获取项目计划
        project_synopsis = project.getProjectSynopsis();    //获取项目简介
        initView();
        GlobalData app = (GlobalData) getApplication();
        app.setNow_project_id(project_uid);
        if (project.getManager_uid().equals(app.getUid())) {
            isManager = true;
        }

        number_dynamic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                projectDynamic();
            }
        });

        number_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                projectMember();
            }
        });

        number_doing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                projectTask();
            }
        });

        number_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doneTask();
            }
        });
    }

    private void projectDynamic() {
        Intent intent = new Intent(this, ProjectDynamicActivity.class);     //页面跳转至更新项目
        Bundle bundle = new Bundle();
        bundle.putSerializable("project", project);
        intent.putExtras(bundle);
        this.startActivity(intent);
    }


    private void projectMember() {
        Intent intent = new Intent(this, ProjectMemberActivity.class);     //页面跳转至更新项目
        Bundle bundle = new Bundle();
        bundle.putSerializable("project", project);
        intent.putExtras(bundle);
        this.startActivity(intent);
    }

    private void projectTask() {
        Intent intent = new Intent(this, ProjectTaskActivity.class);     //页面跳转至更新项目
        Bundle bundle = new Bundle();
        bundle.putSerializable("project", project);
        intent.putExtras(bundle);
        this.startActivity(intent);
    }

    private void doneTask() {
        Intent intent = new Intent(this, DoneTaskActivity.class);     //页面跳转至更新项目
        Bundle bundle = new Bundle();
        bundle.putSerializable("project", project);
        intent.putExtras(bundle);
        this.startActivity(intent);
    }

    private void initView() {
        tv_project_name = (TextView) findViewById(R.id.project_name);
        tv_project_uid = (TextView) findViewById(R.id.account);
        number_dynamic = (TextView) findViewById(R.id.number_dynamic);
        number_member = (TextView) findViewById(R.id.number_member);
        number_doing = (TextView) findViewById(R.id.number_doing);
        number_done = (TextView) findViewById(R.id.number_done);
        tv_manager_name = (TextView) findViewById(R.id.manager_name);
        tv_project_rate = (TextView) findViewById(R.id.project_rate);
        tv_start_end = (TextView) findViewById(R.id.start_end);
        tv_project_plan = (TextView) findViewById(R.id.project_plan);
        tv_project_synopsis = (TextView) findViewById(R.id.project_synopsis);
        tv_manager_name.setText(manager_nickname + "(" + manager_account + ")");
        tv_project_rate.setText(project_rate + "%");
        tv_start_end.setText(projectStartTime + "至" + projectEndTime);
        tv_project_plan.setText(project_plan);
        tv_project_synopsis.setText(project_synopsis);
        Request.clientGet( "statistics?ingTaskPro=yes&doneTaskPro=yes&hasOverdue=yes&noClaimTask&expireToday=yes&proMemNum=yes&project_uid=" + project_uid, new NetCallBack() {
            @Override
            public void onMySuccess(JSONObject result) {
                tv_project_name.setText(project_name);
                tv_project_uid.setText("UID: " + project_uid);
                number_member.setText(result.getString("proMemNum"));
                number_doing.setText(result.getString("ingTaskPro"));
                number_done.setText(result.getString("doneTaskPro"));
            }

            @Override
            public void onMyFailure(String error, String data) {
                Toast.makeText(ProjectDetailActivity.this, error, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.project_menu, menu);
        project_menu = menu;
        project_menu.findItem(R.id.edit_project).setVisible(false);     //初始化编辑项目不可见
        project_menu.findItem(R.id.create_task).setVisible(false);
        if(isManager){      //如果是项目经理
            project_menu.findItem(R.id.edit_project).setVisible(true);      //设为可见，有权编辑项目
            project_menu.findItem(R.id.create_task).setVisible(true);
        }
        return true;
    }

    public void editProject(){
        Intent intent = new Intent(this, EditProjectActivity.class);     //页面跳转至更新项目
        Bundle bundle = new Bundle();
        bundle.putSerializable("project", project);
        intent.putExtras(bundle);
        this.startActivity(intent);
    }

    public void createTask(){
        Intent intent = new Intent(this, CreateTaskActivity.class);     //页面跳转至更新项目
        Bundle bundle = new Bundle();
        bundle.putSerializable("project", project);
        intent.putExtras(bundle);
        this.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.edit_project:
                editProject();
                break;
            case R.id.create_task:
                createTask();
                break;
        }
        return true;
    }
}
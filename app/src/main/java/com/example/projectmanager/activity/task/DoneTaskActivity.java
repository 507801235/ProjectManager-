package com.example.projectmanager.activity.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.projectmanager.R;
import com.example.projectmanager.adapter.TaskAdapter;
import com.example.projectmanager.model.GlobalData;
import com.example.projectmanager.model.Project;
import com.example.projectmanager.model.TaskModel;
import com.example.projectmanager.util.NetCallBack;
import com.example.projectmanager.util.Request;

import java.util.List;

public class DoneTaskActivity extends AppCompatActivity {
    Project project;
    ListView task_view;
    private String project_uid;
    private Boolean isManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done_task);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //修改页面背景色
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.gray);
        this.getWindow().setBackgroundDrawable(drawable);
        Intent intent  = this.getIntent();
        project = (Project) intent.getSerializableExtra("project");
        project_uid = project.getProject_uid();     //获取项目uid
        GlobalData app = (GlobalData) getApplication();
        isManager = false;
        if (project.getManager_uid().equals(app.getUid())) {
            isManager = true;
        }
        task_view = (ListView) findViewById(R.id.list_view);
//        getData();
        project_uid = app.getNow_project_id();
        if(isManager){
            Request.clientGet("task?asManager=yes&claimState=3&S=1&project_uid=" + project_uid , new NetCallBack() {
                @Override
                public void onMySuccess(JSONObject result) {
                    JSONArray list = result.getJSONArray("list");
                    String listString = JSONObject.toJSONString(list);
                    List<TaskModel> tasks = JSONObject.parseArray(listString, TaskModel.class);//把字符串转换成集合
                    TaskAdapter taskAdapter = new TaskAdapter(DoneTaskActivity.this, R.layout.task_item, tasks);
                    task_view.setAdapter(taskAdapter);
                    task_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            TaskModel task = tasks.get(position);
                            Intent intent = new Intent(DoneTaskActivity.this, TaskDetailActivity.class);
                            intent.putExtra("task", task);
                            startActivity(intent);
                        }
                    });
                }

                @Override
                public void onMyFailure(String error, String data) {

                }
            });
        }else{
            Request.clientGet("task?asMember=yes&claimState=3&S=1&project_uid=" + project_uid , new NetCallBack() {
                @Override
                public void onMySuccess(JSONObject result) {
                    JSONArray list = result.getJSONArray("list");
                    String listString = JSONObject.toJSONString(list);
                    List<TaskModel> tasks = JSONObject.parseArray(listString, TaskModel.class);//把字符串转换成集合
                    TaskAdapter taskAdapter = new TaskAdapter(DoneTaskActivity.this, R.layout.task_item, tasks);
                    task_view.setAdapter(taskAdapter);
                    task_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            TaskModel task = tasks.get(position);
                            Intent intent = new Intent(DoneTaskActivity.this, TaskDetailActivity.class);
                            intent.putExtra("task", task);
                            startActivity(intent);
                        }
                    });
                }

                @Override
                public void onMyFailure(String error, String data) {

                }
            });
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
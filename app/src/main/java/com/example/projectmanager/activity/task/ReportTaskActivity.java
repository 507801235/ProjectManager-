package com.example.projectmanager.activity.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.projectmanager.R;
import com.example.projectmanager.model.TaskModel;
import com.example.projectmanager.util.NetCallBack;
import com.example.projectmanager.util.Request;

import cz.msebera.android.httpclient.entity.StringEntity;

public class ReportTaskActivity extends AppCompatActivity {
    TaskModel task;
    private EditText work_hour;
    private EditText rest_hour;
    private RadioGroup task_state;
    private EditText work_content;
    private Button report_task;
    private String workHour;
    private String restHour;
    private Integer taskState;
    private String workContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_task);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //设置返回键
        task = (TaskModel) getIntent().getSerializableExtra("task");
        initView();
        report_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workContent = work_content.getText().toString();
                workHour = work_hour.getText().toString();
                restHour = rest_hour.getText().toString();
                taskState = getState()-1;
                JSONObject body = new JSONObject();
                body.put("workingTime", workHour);
                body.put("restWorkingTime", restHour);
                body.put("taskState", taskState);
                body.put("workingContent", workContent);
                StringEntity entity = new StringEntity(body.toJSONString(), "UTF-8");
                Request.clientPut(ReportTaskActivity.this, "task/" + task.getTask_uid(), entity, new NetCallBack() {

                    @Override
                    public void onMySuccess(JSONObject result) {
                        Toast.makeText(ReportTaskActivity.this, "汇报完成", Toast.LENGTH_LONG).show();
                        finish();
                    }

                    @Override
                    public void onMyFailure(String error, String data) {
                        Toast.makeText(ReportTaskActivity.this, error, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void initView() {
        work_hour = (EditText) findViewById(R.id.work_title);
        rest_hour = (EditText) findViewById(R.id.rest_hour);
        task_state = (RadioGroup) findViewById(R.id.task_state);
        work_content = (EditText) findViewById(R.id.work_content);
        report_task = (Button) findViewById(R.id.report_task);
    }

    public Integer getState(){
        RadioButton radioButton = (RadioButton)findViewById(task_state.getCheckedRadioButtonId());
        if (radioButton!=null){
            return task_state.getCheckedRadioButtonId();
        }else{
            return null;
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
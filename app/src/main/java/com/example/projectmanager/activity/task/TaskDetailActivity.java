package com.example.projectmanager.activity.task;

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
import android.widget.Button;
import android.widget.TextView;

import com.example.projectmanager.AddContactActivity;
import com.example.projectmanager.R;
import com.example.projectmanager.activity.project.CreateProjectActivity;
import com.example.projectmanager.model.GlobalData;
import com.example.projectmanager.model.TaskModel;

import static com.example.projectmanager.util.EventIDToString.getEmergent;
import static com.example.projectmanager.util.EventIDToString.getState;

public class TaskDetailActivity extends AppCompatActivity {
    TaskModel task;
    private TextView task_name;
    private TextView claim_username;
    private TextView start_time;
    private TextView end_time;
    private TextView task_hour;
    private TextView rest_hour;
    private TextView task_emergent;
    private TextView task_state;
    private TextView task_plan;
    private String taskName;
    private String claimUsername;
    private String startTime;
    private String endTime;
    private String taskHour;
    private String restHour;
    private String taskEmergent;
    private String taskState;
    private String taskPlan;
    private Button report_task;
    private int emergentColor;
    private int stateColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);   //设置toolbar
        //修改页面背景色
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.gray);
        this.getWindow().setBackgroundDrawable(drawable);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //设置返回键
        GlobalData app = (GlobalData) getApplication();
        Intent intent  = this.getIntent();
        task = (TaskModel) intent.getSerializableExtra("task");
        taskName = task.getTaskName();
        claimUsername = task.getClaimNickName();
        startTime = task.getTaskStartTime();
        endTime = task.getTaskEndTime();
        taskHour = task.getTaskPredictHours();
        restHour = task.getTaskRestHours();
        taskEmergent = getEmergent(task.getTaskEmergent());
        taskState = getState(task.getTaskState());
        taskPlan = task.getTaskSynopsis();
        app.setNow_task_id(task.getTask_uid());
        switch (task.getTaskEmergent()){
            case 0 : emergentColor = 0xFF7b8b6f;break;
            case 1 : emergentColor = 0xFFF0E68C;break;
            case 2 : emergentColor = 0xFF965454;break;
        }
        switch (task.getTaskState()){
            case 0 : stateColor = 0xFF965454;break;
            case 1 : stateColor = 0xFF7b8b6f;break;
        }
        initView();
        if (task.getClaim_uid() == app.getUid()) {
            report_task.setVisibility(View.VISIBLE);
            report_task.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TaskDetailActivity.this, ReportTaskActivity.class);
                    intent.putExtra("task", task);
                    startActivity(intent);
                }
            });
        }
    }

    private void initView() {
        task_name = (TextView) findViewById(R.id.task_name);
        claim_username = (TextView) findViewById(R.id.claim_username);
        start_time = (TextView) findViewById(R.id.start_time);
        end_time = (TextView) findViewById(R.id.end_time);
        task_hour = (TextView) findViewById(R.id.task_hour);
        rest_hour = (TextView) findViewById(R.id.rest_hour);
        task_emergent = (TextView) findViewById(R.id.task_emergent);
        task_state = (TextView) findViewById(R.id.task_state);
        task_plan = (TextView) findViewById(R.id.task_plan);
        report_task = (Button) findViewById(R.id.report_task);

        task_name.setText(taskName);
        claim_username.setText(claimUsername);
        start_time.setText(startTime);
        end_time.setText(endTime);
        task_hour.setText(taskHour);
        rest_hour.setText(restHour);
        task_emergent.setText(taskEmergent);
        task_emergent.setTextColor(emergentColor);
        task_state.setText(taskState);
        task_state.setTextColor(stateColor);
        task_plan.setText(taskPlan);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.task_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.task_report:
                Intent intent = new Intent(this, TaskReportActivity.class);
                this.startActivity(intent);
                break;
        }
        return true;
    }
}
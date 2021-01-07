package com.example.projectmanager.activity.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.projectmanager.R;
import com.example.projectmanager.model.Project;
import com.example.projectmanager.util.NetCallBack;
import com.example.projectmanager.util.Request;

import java.util.Calendar;

import cz.msebera.android.httpclient.entity.StringEntity;

public class CreateTaskActivity extends AppCompatActivity {
    Project project;
    private String project_uid;
    private EditText et_task_name;
    private EditText et_claim_username;
    private TextView tv_start_time;
    private TextView tv_end_time;
    private EditText et_task_hour;
    private RadioGroup task_emergent;
    private EditText et_task_plan;
    private Button create_task;
    private String task_name;
    private String claim_username;
    private String start_time;
    private String end_time;
    private String task_hour;
    private Integer emergent;
    private String task_plan;

    private Calendar calendar;  //显示当前日期
    private int year;
    private int month;
    private int day;
    private  String Instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent  = this.getIntent();
        project = (Project) intent.getSerializableExtra("project");
        project_uid = project.getProject_uid();     //获取项目uid
        getDate();
        initView();
        create_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task_name = et_task_name.getText().toString();
                claim_username = et_claim_username.getText().toString();
                start_time = tv_start_time.getText().toString();
                end_time = tv_end_time.getText().toString();
                task_hour = et_task_hour.getText().toString();
                task_plan = et_task_plan.getText().toString();
                if(task_name.equals("") || claim_username.equals("") || start_time.equals("") ||
                        end_time.equals("") || task_hour.equals("") || getEmergent() == null || task_plan.equals("")){
                    Toast.makeText(CreateTaskActivity.this, "请填写完整信息", Toast.LENGTH_LONG).show();
                }else{
                    if(start_time.compareTo(end_time)>0){
                        Toast.makeText(CreateTaskActivity.this, "请正确选择时间", Toast.LENGTH_LONG).show();
                        tv_start_time.setText("");
                        tv_end_time.setText("");
                    }else{
                        emergent = getEmergent()-1;
                        JSONObject body = new JSONObject();
                        body.put("taskName", task_name);
                        body.put("claim_uid", claim_username);
                        body.put("taskStartTime", start_time);
                        body.put("taskEndTime", end_time);
                        body.put("taskPredictHours", task_hour);
                        body.put("taskEmergent", emergent);
                        body.put("taskSynopsis", task_plan);
                        StringEntity entity = new StringEntity(body.toJSONString(), "UTF-8");
                        Request.clientPost(CreateTaskActivity.this, "project/" + project_uid + "/task", entity, new NetCallBack() {
                            @Override
                            public void onMySuccess(JSONObject result) {
                                Toast.makeText(CreateTaskActivity.this, "发布成功", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onMyFailure(String error, String data) {
                                Toast.makeText(CreateTaskActivity.this, error, Toast.LENGTH_LONG).show();
                                System.out.println(data);
                            }
                        });
                    }
                }
            }
        });
        tv_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener listener= new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int selected_year, int selected_month, int selected_day) {
                        start_time = selected_year+"-"+(++selected_month)+"-"+selected_day;
                        if(Instance.compareTo(start_time)>0){
                            Toast.makeText(CreateTaskActivity.this, "请正确选择时间", Toast.LENGTH_LONG).show();
                            tv_start_time.setText("");
                        }else{
                            tv_start_time.setText(start_time);
                        }
                    }
                };
                DatePickerDialog dialog=new DatePickerDialog(CreateTaskActivity.this, 0,listener,year,month-1,day);
                dialog.show();
            }
        });
        tv_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener listener= new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int selected_year, int selected_month, int selected_day) {
                        end_time = selected_year+"-"+(++selected_month)+"-"+selected_day;
                        if(Instance.compareTo(end_time)>0 && start_time.compareTo(end_time)>0){
                            Toast.makeText(CreateTaskActivity.this, "请正确选择时间", Toast.LENGTH_LONG).show();
                            tv_end_time.setText("");
                        }else{
                            tv_end_time.setText(end_time);
                        }
                    }
                };
                DatePickerDialog dialog=new DatePickerDialog(CreateTaskActivity.this, 0,listener,year,month-1,day);
                dialog.show();
            }
        });
    }

    private void getDate() {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);       //获取年月日时分秒
        month = calendar.get(Calendar.MONTH)+1;   //获取到的月份是从0开始计数
        day = calendar.get(Calendar.DAY_OF_MONTH);
        Instance = year+"-"+month+"-"+day;
    }

    private void initView() {
        et_task_name = (EditText) findViewById(R.id.et_task_name);
        et_claim_username = (EditText) findViewById(R.id.claim_username);
        tv_start_time = (TextView) findViewById(R.id.start_date_picker);
        tv_end_time = (TextView) findViewById(R.id.end_date_picker);
        et_task_hour = (EditText) findViewById(R.id.task_hour);
        task_emergent = (RadioGroup) findViewById(R.id.task_emergent);
        et_task_plan = (EditText) findViewById(R.id.et_task_plan);
        create_task = (Button) findViewById(R.id.create_task);
    }

    public Integer getEmergent(){
        RadioButton radioButton = (RadioButton)findViewById(task_emergent.getCheckedRadioButtonId());
        if (radioButton!=null){
            return task_emergent.getCheckedRadioButtonId();
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
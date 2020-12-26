package com.example.projectmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.alibaba.fastjson.JSONObject;
import com.example.projectmanager.util.NetCallBack;
import com.example.projectmanager.util.Request;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import cz.msebera.android.httpclient.entity.StringEntity;

public class CreateProjectActivity extends AppCompatActivity {

    private EditText et_project_name;
    private TextView date_picker;
    private EditText et_project_plan;
    private EditText et_project_synopsis;
    private Button create_project;

    private String project_name;
    private String start_time;
    private String end_time;
    private String project_plan;
    private String project_synopsis;

    private Calendar calendar;  //显示当前日期
    private int year;
    private int month;
    private int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getDate();
        initView();

        date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener listener= new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int selected_year, int selected_month, int selected_day) {
                        end_time = selected_year+"-"+(++selected_month)+"-"+selected_day;
                        if(start_time.compareTo(end_time)>=0){
                            Toast.makeText(CreateProjectActivity.this, "截止时间格式错误!", Toast.LENGTH_LONG).show();
                            date_picker.setText("");
                        }else{
                            date_picker.setText(end_time);
                        }
                    }
                };
                DatePickerDialog dialog=new DatePickerDialog(CreateProjectActivity.this, 0,listener,year,month-1,day);
                dialog.show();
            }
        });

        create_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                project_name = et_project_name.getText().toString();
                project_plan = et_project_plan.getText().toString();
                project_synopsis = et_project_synopsis.getText().toString();
                if(project_name.equals("") || end_time.equals("")){
                    Toast.makeText(CreateProjectActivity.this, "请填写完整信息!", Toast.LENGTH_LONG).show();
                }else{
                    JSONObject body = new JSONObject();
                    body.put("projectName", project_name);
                    body.put("projectStartTime",start_time);
                    body.put("projectEndTime", end_time);
                    body.put("projectPlan", project_plan);
                    body.put("projectSynopsis", project_synopsis);
                    StringEntity entity = new StringEntity(body.toJSONString(), "UTF-8");
                    Request.clientPost(CreateProjectActivity.this, "project", entity, new NetCallBack() {
                        @Override
                        public void onMySuccess(JSONObject result) {
                            Toast.makeText(CreateProjectActivity.this, "创建成功！", Toast.LENGTH_LONG).show();
                            finish();
                        }

                        @Override
                        public void onMyFailure(String error) {
                            Toast.makeText(CreateProjectActivity.this, error, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    private void initView() {
        et_project_name = (EditText) findViewById(R.id.et_project_name);
        date_picker = (TextView) findViewById(R.id.date_picker);
        et_project_plan = (EditText) findViewById(R.id.et_project_plan);
        et_project_synopsis = (EditText) findViewById(R.id.et_project_synopsis);
        create_project = (Button) findViewById(R.id.create_project);
    }

    //获取当前日期，初始化日期选择器
    private void getDate() {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);       //获取年月日时分秒
        month = calendar.get(Calendar.MONTH)+1;   //获取到的月份是从0开始计数
        day = calendar.get(Calendar.DAY_OF_MONTH);
        start_time = year+"-"+month+"-"+day;
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
package com.example.projectmanager.activity.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.projectmanager.R;
import com.example.projectmanager.adapter.ReportAdapter;
import com.example.projectmanager.model.Report;
import com.example.projectmanager.ui.home.HomeViewModel;

import java.util.List;

public class TaskReportActivity extends AppCompatActivity {
    TaskReportViewModel taskReportViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_report);
        //修改页面背景色
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.gray);
        this.getWindow().setBackgroundDrawable(drawable);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //设置返回键
        ListView lv_report = findViewById(R.id.list_view);
        taskReportViewModel = new TaskReportViewModel();
        taskReportViewModel.getReportList().observe(this, new Observer<List<Report>>() {
            @Override
            public void onChanged(List<Report> reports) {
                lv_report.setAdapter(new ReportAdapter(TaskReportActivity.this, reports));
            }
        });
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
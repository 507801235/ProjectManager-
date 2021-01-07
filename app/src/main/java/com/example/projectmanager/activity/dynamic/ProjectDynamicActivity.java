package com.example.projectmanager.activity.dynamic;

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
import com.example.projectmanager.adapter.DynamicAdapter;
import com.example.projectmanager.model.Dynamic;
import com.example.projectmanager.ui.home.HomeViewModel;

import java.util.List;

public class ProjectDynamicActivity extends AppCompatActivity {
    private HomeViewModel homeViewModel;
    private ListView dynamic_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_dynamic);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        //修改页面背景色
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.gray);
        this.getWindow().setBackgroundDrawable(drawable);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        homeViewModel.getProjectDynamic().observe(this, new Observer<List<Dynamic>>() {
            @Override
            public void onChanged(List<Dynamic> dynamics) {
                dynamic_view.setAdapter(new DynamicAdapter(ProjectDynamicActivity.this, dynamics));
            }
        });
    }

    private void initView() {
        dynamic_view = (ListView) findViewById(R.id.list_view);
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
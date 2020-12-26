package com.example.projectmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.projectmanager.R;
import com.example.projectmanager.model.Project;

import java.util.List;

public class ProjectAdapter extends BaseAdapter {
    Context context;
    List<Project> projectList;

    public ProjectAdapter(Context context, List<Project> projectList) {
        this.context = context;
        this.projectList = projectList;
    }

    @Override
    public int getCount() {
        return this.projectList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.projectList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.project_item, parent, false);
        TextView project_name = (TextView)view.findViewById(R.id.project_name);
        TextView start_time = (TextView)view.findViewById(R.id.start_time);
        ProgressBar progressBar = view.findViewById(R.id.progress_bar);
        TextView project_rate = view.findViewById(R.id.project_rate);

        Project project = this.projectList.get(position);
        project_name.setText(project.getProjectName());
        start_time.setText(project.getProjectStartTime());
        progressBar.setProgress(project.getProjectRate());
        project_rate.setText(project.getProjectRate() + "%");

        return view;
    }
}

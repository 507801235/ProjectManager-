package com.example.projectmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.projectmanager.model.Report;
import com.example.projectmanager.R;
import java.util.List;

public class ReportAdapter extends BaseAdapter {
    Context context;
    List<Report> reportList;

    public ReportAdapter(Context context, List<Report> reportList) {
        this.context = context;
        this.reportList = reportList;
    }


    @Override
    public int getCount() {
        return this.reportList.size();
    }

    @Override
    public Report getItem(int position) {
        return this.reportList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.report_item,parent,false);

            viewHolder.report_content = convertView.findViewById(R.id.report_content);
            viewHolder.claim_nickname = convertView.findViewById(R.id.claim_nickname);
            viewHolder.report_time = convertView.findViewById(R.id.report_time);
            viewHolder.work_hour = convertView.findViewById(R.id.work_hour);
            viewHolder.rest_hour = convertView.findViewById(R.id.rest_hour);
            viewHolder.task_state = convertView.findViewById(R.id.task_state);

            convertView.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Report report = getItem(position);
        viewHolder.claim_nickname.setText(report.getAccountNickName());
        viewHolder.report_content.setText(report.getWorkingContent());
        viewHolder.report_time.setText(report.getReportTime());
        viewHolder.work_hour.setText(report.getWorkingTime());
        viewHolder.rest_hour.setText(report.getRestTime());

        Integer state = report.getTaskState();
        if (state == 1) {
            viewHolder.task_state.setText("已完成");
            viewHolder.task_state.setTextColor(0xFF7b8b6f);
        }else{
            viewHolder.task_state.setText("未完成");
            viewHolder.task_state.setTextColor(0xFF965454);
        }

        return convertView;
    }

    class ViewHolder {
        TextView report_content;
        TextView report_time;
        TextView claim_nickname;
        TextView rest_hour;
        TextView work_hour;
        TextView task_state;
    }
}

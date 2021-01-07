package com.example.projectmanager.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.projectmanager.model.TaskModel;
import com.example.projectmanager.R;
import java.util.List;

public class TaskAdapter extends ArrayAdapter<TaskModel> {
    private int resourceId;

    public TaskAdapter(Context context, int textViewResourceId, List<TaskModel> objects){
        super(context, textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        TaskModel task=getItem(position);

        View view;
        ViewHolder viewHolder;
        if (convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.task_name = view.findViewById(R.id.task_name);
            viewHolder.start_time = view.findViewById(R.id.start_time);
            viewHolder.end_time = view.findViewById(R.id.end_time);
            viewHolder.task_emergent = view.findViewById(R.id.task_emergent);

            view.setTag(viewHolder);
        } else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.task_name.setText(task.getTaskName());
        viewHolder.start_time.setText(task.getTaskStartTime());
        viewHolder.end_time.setText(task.getTaskEndTime());
        String prio="";
        int color=0xFF7FFFAA;
        int textColor= Color.BLACK;
        if(task.getTaskEmergent()==0) {
            prio="普通";
            textColor=0xFF7b8b6f;
        }
        else if(task.getTaskEmergent()==1) {
            prio="紧急";
            textColor = 0xFFF0E68C;
        }
        else if(task.getTaskEmergent()==2) {
            prio="加急";
            textColor = 0xFF965454;
        }
        viewHolder.task_emergent.setText(prio);
        viewHolder.task_emergent.setTextColor(textColor);
        return view;
    }

    class ViewHolder{
        TextView task_name;
        TextView start_time;
        TextView end_time;
        TextView task_emergent;
    }
}

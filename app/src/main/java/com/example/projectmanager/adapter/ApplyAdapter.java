package com.example.projectmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.projectmanager.model.Apply;
import com.example.projectmanager.R;
import java.util.List;

public class ApplyAdapter extends BaseAdapter {
    Context context;
    List<Apply> applyList;

    public ApplyAdapter(Context context, List<Apply> applyList) {
        this.context = context;
        this.applyList = applyList;
    }

    @Override
    public int getCount() {
        return applyList.size();
    }

    @Override
    public Object getItem(int position) {
        return applyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(this.context);
            convertView = inflater.inflate(R.layout.notify_item, parent, false);

            viewHolder.project_name = convertView.findViewById(R.id.project_name);
            viewHolder.notify_content = convertView.findViewById(R.id.notify_content);
            viewHolder.deal = convertView.findViewById(R.id.deal);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.deal.setText("需处理");

        Apply apply = this.applyList.get(position);
        viewHolder.project_name.setText(apply.getProjectName());
        viewHolder.notify_content.setText(apply.getApplyAccountNickName() + " 申请加入您的项目 [" + apply.getProjectName() + "]");

        return convertView;
    }

    class ViewHolder {
        TextView project_name;
        TextView notify_content;
        TextView deal;
    }
}

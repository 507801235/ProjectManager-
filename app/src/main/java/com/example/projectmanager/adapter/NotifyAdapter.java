package com.example.projectmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.projectmanager.model.GlobalData;
import com.example.projectmanager.model.Notify;
import com.example.projectmanager.R;
import java.util.List;

public class   NotifyAdapter extends BaseAdapter {
    Context context;
    List<Notify> notifyList;
    Integer my_uid;
    View.OnClickListener listener;

    public NotifyAdapter(Context context, List<Notify> notifyList, View.OnClickListener listener) {
        this.context = context;
        this.notifyList = notifyList;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return this.notifyList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.notifyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        viewHolder = new ViewHolder();
        LayoutInflater inflater = LayoutInflater.from(this.context);
        convertView = inflater.inflate(R.layout.notify_item, parent, false);

        viewHolder.notify_content = convertView.findViewById(R.id.notify_content);
        viewHolder.project_name = convertView.findViewById(R.id.project_name);
        viewHolder.notify_time = convertView.findViewById(R.id.notify_time);
        viewHolder.deal = convertView.findViewById(R.id.deal);

        Notify notify = this.notifyList.get(position);
        int event = notify.getEventID();
        String content = "";
        switch (event){
            case 0:
                content = notify.getAccountNickName() + " 拒绝了任务 [" + notify.getTaskName() + "]";
                break;
            case 1:
                content = notify.getAccountNickName() + " 接受了任务 [" + notify.getTaskName() + "]";
                break;
            case 2:
                GlobalData app = (GlobalData) context.getApplicationContext();
                if (notify.getAccount_uid() == app.getUid()) {
                    viewHolder.deal.setText("需处理");
                    content = "项目经理 " + notify.getManagerNickName() + " 给你指派了任务 [" + notify.getTaskName() + "]";
                    convertView.setOnClickListener(listener);
                    convertView.setTag(position);
                } else {
                    content = "你给 " + notify.getAccountNickName() +  " 指派了任务 [" + notify.getTaskName() + "]";
                }
                break;
            case 3:
                content = notify.getAccountNickName() + " 完成了任务 [" + notify.getTaskName() + "]";
                break;
        }

        viewHolder.notify_content.setText(content);
        viewHolder.project_name.setText(notify.getProjectName());
        viewHolder.notify_time.setText(notify.getNotifyTime());

        return convertView;
    }

    class ViewHolder {
        TextView notify_content;
        TextView project_name;
        TextView notify_time;
        TextView deal;
    }
}
package com.example.projectmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.projectmanager.model.Invite;
import com.example.projectmanager.R;
import java.util.List;

public class InviteAdapter extends BaseAdapter {
    Context context;
    List<Invite> inviteList;

    public InviteAdapter(Context context, List<Invite> inviteList) {
        this.context = context;
        this.inviteList = inviteList;
    }

    @Override
    public int getCount() {
        return this.inviteList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.inviteList.get(position);
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

        Invite invite = this.inviteList.get(position);
        viewHolder.project_name.setText(invite.getProjectName());
        viewHolder.notify_content.setText("项目经理" + invite.getInviteNickName() + "邀请你加入项目 [" + invite.getProjectName() + "]");

        return convertView;
    }

    class ViewHolder {
        TextView project_name;
        TextView notify_content;
        TextView deal;
    }
}
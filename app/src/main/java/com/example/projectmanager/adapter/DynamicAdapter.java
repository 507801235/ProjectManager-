package com.example.projectmanager.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.projectmanager.model.Dynamic;
import com.example.projectmanager.R;
import java.util.List;

import static com.example.projectmanager.util.EventIDToString.getEvent;

public class DynamicAdapter extends BaseAdapter {
    Context context;
    List<Dynamic> dynamicList;

    public DynamicAdapter(Context context, List<Dynamic> dynamicList) {
        this.context = context;
        this.dynamicList = dynamicList;
    }

    @Override
    public int getCount() {
        return this.dynamicList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.dynamicList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.context);

        View view = inflater.inflate(R.layout.dynamic_item, parent, false);

        TextView project_name = (TextView) view.findViewById(R.id.project_name);
        TextView dynamic_time = (TextView) view.findViewById(R.id.date);
        TextView member = (TextView) view.findViewById(R.id.member);
        TextView content = (TextView) view.findViewById(R.id.content);

        Dynamic dynamic = this.dynamicList.get(position);
        int eventID = dynamic.getEventID();
        String event = getEvent(eventID);


        project_name.setText(dynamic.getProjectName());
        dynamic_time.setText(dynamic.getDynamicTime().split(" ")[0]);
        member.setText(dynamic.getAccountNickName());
        content.setText("["+event+"]");

        return view;
    }
}
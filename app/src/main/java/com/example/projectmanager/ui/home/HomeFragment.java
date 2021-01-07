package com.example.projectmanager.ui.home;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.projectmanager.NotifyActivity;
import com.example.projectmanager.R;
import com.example.projectmanager.activity.member.ProjectMemberActivity;
import com.example.projectmanager.adapter.DynamicAdapter;
import com.example.projectmanager.model.Dynamic;

import java.util.Calendar;
import java.util.List;

import static com.example.projectmanager.R.layout.fragment_home;

public class HomeFragment extends Fragment {
    private TextView calendar;
    private TextView notice;
    private ListView dynamic_view;
    private HomeViewModel homeViewModel;

    private Calendar date;  //显示当前日期
    private int year;
    private int month;
    private int day;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(fragment_home, container, false);
        getDate();
        initView(root);
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener listener= new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int selected_year, int selected_month, int selected_day) {
                        return ;
                    }
                };
                DatePickerDialog dialog=new DatePickerDialog(getActivity(), 0,listener,year,month-1,day);
                dialog.show();
            }
        });
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NotifyActivity.class);     //页面跳转至更新项目
                startActivity(intent);
            }
        });
        homeViewModel.getDynamicList().observe(getViewLifecycleOwner(), new Observer<List<Dynamic>>() {
            @Override
            public void onChanged(List<Dynamic> dynamics) {
                DynamicAdapter dynamicAdapter = new DynamicAdapter(getActivity(), dynamics);
                dynamic_view.setAdapter(dynamicAdapter);
            }
        });
        return root;
    }

    private void getDate() {
        date = Calendar.getInstance();
        year = date.get(Calendar.YEAR);
        month = date.get(Calendar.MONTH)+1;
        day = date.get(Calendar.DAY_OF_MONTH);
    }
    private void initView(View root) {
        calendar = (TextView) root.findViewById(R.id.calendar);
        notice = (TextView) root.findViewById(R.id.notice);
        dynamic_view = (ListView) root.findViewById(R.id.list_view);
    }
}
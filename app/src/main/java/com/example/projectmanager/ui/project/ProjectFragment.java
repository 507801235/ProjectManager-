package com.example.projectmanager.ui.project;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.projectmanager.activity.project.ProjectDetailActivity;
import com.example.projectmanager.R;
import com.example.projectmanager.adapter.MyPagerAdapter;
import com.example.projectmanager.adapter.ProjectAdapter;
import com.example.projectmanager.model.Project;
import com.example.projectmanager.util.NetCallBack;
import com.example.projectmanager.util.Request;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ProjectFragment extends Fragment {

    private ProjectViewModel mViewModel;
    private ViewPager vpChosen;
    private TabLayout tbSelect;

    View myProject_view;
    View otherProject_view;
    ArrayList<View> viewList;
    ArrayList<String> tabIndicators;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel =
                new ViewModelProvider(this).get(ProjectViewModel.class);
        View root = inflater.inflate(R.layout.fragment_project, container, false);
        findView(root);
        initView(root);

        tbSelect.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vpChosen.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return root;
    }

    private void init_myProject(View myProject_view) {
        ListView project_list = myProject_view.findViewById(R.id.list_view);

        Request.clientGet( "project?asManager=yes&asMember=no", new NetCallBack(){
            @Override
            public void onMySuccess(JSONObject result) {
                System.out.println("project:" + result);
                JSONArray list = result.getJSONArray("list");
                System.out.println(list);
                String liststring = JSONObject.toJSONString(list);
                System.out.println("liststring:"+liststring);
                List<Project> projectList = JSONObject.parseArray(liststring, Project.class);//把字符串转换成集合
                project_list.setAdapter(new ProjectAdapter(getActivity(), projectList));
                project_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Project project = projectList.get(position);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("project", project);

                        Intent intent = new Intent(getActivity(), ProjectDetailActivity.class);
                        intent.putExtras(bundle);   //把project信息传给ProjectDetailActivity
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onMyFailure(String error, String data) {

            }
        });
    }

    private void init_otherProject(View otherProject_view) {
        ListView project_list = otherProject_view.findViewById(R.id.list_view);

        Request.clientGet("project?asManager=no", new NetCallBack(){
            @Override
            public void onMySuccess(JSONObject result) {
                System.out.println("project:" + result);
                JSONArray list = result.getJSONArray("list");
                String liststring = JSONObject.toJSONString(list);
                List<Project> projectList = JSONObject.parseArray(liststring, Project.class);//把字符串转换成集合
                project_list.setAdapter(new ProjectAdapter(getActivity(), projectList));
                project_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Project project = projectList.get(position);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("project", project);

                        Intent intent = new Intent(getActivity(), ProjectDetailActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onMyFailure(String error, String data) {

            }
        });
    }

    private void initView(View view) {
        LayoutInflater li = getActivity().getLayoutInflater();
        myProject_view = li.inflate(R.layout.listview, null, false);
        init_myProject(myProject_view);
        otherProject_view = li.inflate(R.layout.listview, null, false);
        init_otherProject(otherProject_view);
        viewList = new ArrayList<View>();
        tabIndicators = new ArrayList<String>();
        viewList.add(myProject_view);
        viewList.add(otherProject_view);
        tabIndicators.add("我创建的项目");
        tabIndicators.add("我加入的项目");
        MyPagerAdapter mAdapter = new MyPagerAdapter(viewList, tabIndicators);
        vpChosen.setAdapter((mAdapter));
    }

    private void findView(View view) {
        vpChosen = (ViewPager) view.findViewById(R.id.vp_chosen);
        tbSelect = (TabLayout) view.findViewById(R.id.tb_select);
        tbSelect.setupWithViewPager(vpChosen);
    }
}
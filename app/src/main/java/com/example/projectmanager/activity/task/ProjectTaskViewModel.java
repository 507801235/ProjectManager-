package com.example.projectmanager.activity.task;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.projectmanager.model.GlobalData;
import com.example.projectmanager.model.TaskModel;
import com.example.projectmanager.util.NetCallBack;
import com.example.projectmanager.util.Request;

import java.util.List;

import static com.example.projectmanager.model.GlobalData.getGlobalData;

public class ProjectTaskViewModel extends ViewModel {
    private MutableLiveData<List<TaskModel>> taskList;
    private MutableLiveData<List<TaskModel>> doneList;
    String project_uid;

    public ProjectTaskViewModel() {
        GlobalData app = (GlobalData) getGlobalData();
        project_uid = app.getNow_project_id();
        taskList = new MutableLiveData<List<TaskModel>>();
        doneList = new MutableLiveData<List<TaskModel>>();
        update();
    }

    public void update() {
        Request.clientGet("task?claimState=3&project_uid=" + project_uid , new NetCallBack() {
            @Override
            public void onMySuccess(JSONObject result) {
                System.out.println(result);
                JSONArray list = result.getJSONArray("list");
                String listString = JSONObject.toJSONString(list);
                List<TaskModel> tasks = JSONObject.parseArray(listString, TaskModel.class);//把字符串转换成集合
                taskList.setValue(tasks);
            }

            @Override
            public void onMyFailure(String error, String data) {

            }
        });

        Request.clientGet("task?claimState=3&project_uid=" + project_uid , new NetCallBack() {
            @Override
            public void onMySuccess(JSONObject result) {
                JSONArray list = result.getJSONArray("list");
                String listString = JSONObject.toJSONString(list);
                List<TaskModel> tasks = JSONObject.parseArray(listString, TaskModel.class);//把字符串转换成集合
                doneList.setValue(tasks);
            }

            @Override
            public void onMyFailure(String error, String data) {

            }
        });
    }

    public MutableLiveData<List<TaskModel>> getTaskList() {
        return taskList;
    }
    public MutableLiveData<List<TaskModel>> getDoneList() {
        return doneList;
    }
}

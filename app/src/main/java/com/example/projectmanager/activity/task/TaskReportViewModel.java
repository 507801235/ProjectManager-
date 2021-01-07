package com.example.projectmanager.activity.task;

import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.projectmanager.model.GlobalData;
import com.example.projectmanager.model.Report;
import com.example.projectmanager.util.NetCallBack;
import com.example.projectmanager.util.Request;

import java.util.List;

import static com.example.projectmanager.model.GlobalData.getGlobalData;

public class TaskReportViewModel {
    MutableLiveData<List<Report>> reportList;
    String task_id;

    public TaskReportViewModel() {
        GlobalData app = (GlobalData) getGlobalData();
        task_id = app.getNow_task_id().toString();
        reportList = new MutableLiveData<>();
        update();
    }

    public void update() {
        Request.clientGet("report?task_uid=" + task_id, new NetCallBack(){
            @Override
            public void onMySuccess(JSONObject result) {
                JSONArray list = result.getJSONArray("list");
                String listString = JSONObject.toJSONString(list);
                List<Report> reports = JSONObject.parseArray(listString, Report.class);//把字符串转换成集合
                reportList.setValue(reports);
            }

            @Override
            public void onMyFailure(String error, String data) {

            }


        });
    }

    public MutableLiveData<List<Report>> getReportList() {
        return reportList;
    }
}

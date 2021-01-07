package com.example.projectmanager.ui.home;

import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.projectmanager.model.Dynamic;
import com.example.projectmanager.model.GlobalData;
import com.example.projectmanager.model.Report;
import com.example.projectmanager.util.NetCallBack;
import com.example.projectmanager.util.Request;

import java.util.List;

import static com.example.projectmanager.model.GlobalData.getGlobalData;

public class HomeViewModel extends ViewModel {
    private String project_uid;
    private MutableLiveData<List<Dynamic>> dynamicList;
    private MutableLiveData<List<Dynamic>> projectDynamic;

    public HomeViewModel() {
        GlobalData app = (GlobalData) getGlobalData();
        project_uid = app.getNow_project_id();
        dynamicList = new MutableLiveData<List<Dynamic>>();
        projectDynamic = new MutableLiveData<List<Dynamic>>();
        update();
    }

    public void update() {
        Request.clientGet( "dynamic", new NetCallBack() {
            @Override
            public void onMySuccess(JSONObject result) {
                JSONArray list = result.getJSONArray("list");
                String listString = JSONObject.toJSONString(list);
                List<Dynamic> dynamics = JSONObject.parseArray(listString, Dynamic.class);//把字符串转换成集合
                dynamicList.setValue(dynamics);
            }
            @Override
            public void onMyFailure(String error, String data) {

            }
        });

        Request.clientGet("dynamic?project_uid=" + project_uid, new NetCallBack() {
            @Override
            public void onMySuccess(JSONObject result) {
                JSONArray list = result.getJSONArray("list");
                String listString = JSONObject.toJSONString(list);
                List<Dynamic> dynamics = JSONObject.parseArray(listString, Dynamic.class);//把字符串转换成集合
                projectDynamic.setValue(dynamics);
            }
            @Override
            public void onMyFailure(String error, String data) {

            }
        });

    }

    public MutableLiveData<List<Dynamic>> getDynamicList() {
        return dynamicList;
    }

    public MutableLiveData<List<Dynamic>> getProjectDynamic() {
        return projectDynamic;
    }
}
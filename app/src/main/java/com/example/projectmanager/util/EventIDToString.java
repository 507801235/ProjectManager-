package com.example.projectmanager.util;

public class EventIDToString {

    public static String getEvent(int eventID){
        String event = "";

        switch (eventID){
            case 0: event = " 拒绝认领任务 "; break;
            case 1: event = " 接受任务 "; break;
            case 2: event = " 待认领 "; break;
            case 3: event = " 完成任务 "; break;
            case 4: event = " 汇报任务 "; break;
            case 5: event = " 发布任务 "; break;
            case 100:   event = " 创建项目 "; break;
            case 300:   event = " 加入项目 "; break;
            case 301:   event = " 退出项目 "; break;
            case 302:   event = " 移出项目 "; break;
        }

        return event;
    }

    public static String getEmergent(int index){
        String emergent = "";
        switch (index){
            case 0: emergent = "普通"; break;
            case 1: emergent = "紧急"; break;
            case 2: emergent = "加急"; break;
        }
        return emergent;
    }

    public static String getState(int index){
        String state = "";
        switch (index){
            case 0: state = "未完成"; break;
            case 1: state = "已完成"; break;
        }
        return state;
    }

}
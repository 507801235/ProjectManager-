package com.example.projectmanager.util;

import android.content.Context;
import android.content.SharedPreferences;

public class Cache {
    public static void init_state(Context context, Boolean keep_state){
        SharedPreferences sharedPreferences = context.getSharedPreferences("keep_state", context.MODE_PRIVATE);
        sharedPreferences.edit().
                putString("account","").
                putString("password","").
                putBoolean("keep_state",keep_state).apply();
    }
    public static void keep_state(Context context, String account, String password, Boolean keep_state){
        SharedPreferences sharedPreferences = context.getSharedPreferences("keep_state", context.MODE_PRIVATE);
        if(!account.equals("") && !password.equals("")){
            sharedPreferences.edit().
                    putString("account",account).
                    putString("password",password).
                    putBoolean("keep_state",keep_state).apply();
        }else{
            System.out.println("请输入完成信息");
        }

    }
}

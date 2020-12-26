package com.example.projectmanager.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class DatabaseManager {
    Context context;

    public static void add (Context context, String nickname, String account){
        DatabaseHelper dbHelper = new DatabaseHelper(context, "ProjectManager",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nickname", nickname);
        values.put("account", account);
        db.insert("Contact", null, values);
    }

    public static void remove (Context context, String account){
        DatabaseHelper dbHelper = new DatabaseHelper(context, "ProjectManager",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("Contact", "account=?", new String[]{account});
    }

    public static void query (Context context, List<List<String>> contactList, List<String> contact){
        DatabaseHelper dbHelper = new DatabaseHelper(context, "ProjectManager",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Contact", new String[]{"nickname","account"}, null, null, null, null, null);
        contact.add(null);
        contact.add(null);
        while(cursor.moveToNext()){
            String nickname = cursor.getString(cursor.getColumnIndex("nickname"));
            String account = cursor.getString(cursor.getColumnIndex("account"));
            contact.set(0,nickname);
            contact.set(1,account);
            contactList.add(contact);
        }
        // 关闭游标，释放资源
        cursor.close();
        return ;
    }
}

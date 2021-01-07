package com.example.projectmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.projectmanager.adapter.ApplyAdapter;
import com.example.projectmanager.adapter.InviteAdapter;
import com.example.projectmanager.adapter.NotifyAdapter;
import com.example.projectmanager.model.Apply;
import com.example.projectmanager.model.Invite;
import com.example.projectmanager.model.Notify;
import com.example.projectmanager.util.NetCallBack;
import com.example.projectmanager.util.Request;

import java.util.List;

import cz.msebera.android.httpclient.entity.StringEntity;

public class NotifyActivity extends AppCompatActivity implements View.OnClickListener {
    List<Notify> notifyList;
    NotifyViewModel notifyViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notifyViewModel = new ViewModelProvider(this).get(NotifyViewModel.class);
        setContentView(R.layout.activity_notify);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //修改页面背景色
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.gray);
        this.getWindow().setBackgroundDrawable(drawable);
        ListView lvInvite = findViewById(R.id.lv_invite);
        ListView lvApply = findViewById(R.id.lv_apply);
        ListView lvNotify = findViewById(R.id.lv_notify);

        notifyViewModel.getNotifyList().observe(this, new Observer<List<Notify>>() {
            @Override
            public void onChanged(List<Notify> notifys) {
                notifyList = notifys;
                lvNotify.setAdapter(new NotifyAdapter(NotifyActivity.this, notifys, (View.OnClickListener) NotifyActivity.this));
            }
        });

        notifyViewModel.getApplyList().observe(this, new Observer<List<Apply>>() {
            @Override
            public void onChanged(List<Apply> applies) {
                lvApply.setAdapter(new ApplyAdapter(NotifyActivity.this, applies));
                lvApply.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Apply apply = applies.get(position);
                        androidx.appcompat.app.AlertDialog dialog = new AlertDialog.Builder(NotifyActivity.this).setTitle("提示")
                                .setMessage("确定同意 " + apply.getApplyAccountNickName() + " 加入项目【" + apply.getProjectName() + "】吗？")
                                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                JSONObject body = new JSONObject();
                                StringEntity entity = new StringEntity(body.toJSONString(), "UTF-8");
                                Request.clientPost(NotifyActivity.this, "project/apply/" + apply.getApply_uid() + "/reject", entity, new NetCallBack() {
                                    @Override
                                    public void onMySuccess(JSONObject result) {
                                        Toast.makeText(NotifyActivity.this, "已拒绝申请", Toast.LENGTH_LONG).show();
                                        notifyViewModel.update();
                                    }

                                    @Override
                                    public void onMyFailure(String error, String data) {
                                        Toast.makeText(NotifyActivity.this, error, Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                JSONObject body = new JSONObject();
                                StringEntity entity = new StringEntity(body.toJSONString(), "UTF-8");
                                Request.clientPost(NotifyActivity.this, "project/apply/" + apply.getApply_uid() + "/accept", entity, new NetCallBack() {
                                    @Override
                                    public void onMySuccess(JSONObject result) {
                                        Toast.makeText(NotifyActivity.this, "申请通过", Toast.LENGTH_LONG).show();
                                        notifyViewModel.update();
                                    }

                                    @Override
                                    public void onMyFailure(String error, String data) {
                                        Toast.makeText(NotifyActivity.this, error, Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }).show();
                    }
                });
            }
        });

        notifyViewModel.getInviteList().observe(this, new Observer<List<Invite>>() {
            @Override
            public void onChanged(List<Invite> invites) {
                lvInvite.setAdapter(new InviteAdapter(NotifyActivity.this, invites));
                lvInvite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Invite invite = invites.get(position);
                        androidx.appcompat.app.AlertDialog dialog = new AlertDialog.Builder(NotifyActivity.this).setTitle("提示")
                                .setMessage("确定加入项目【" + invite.getProjectName() + "】吗？")
                                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                JSONObject body = new JSONObject();
                                StringEntity entity = new StringEntity(body.toJSONString(), "UTF-8");
                                Request.clientPost(NotifyActivity.this, "project/invite/" + invite.getInvite_uid() + "/reject", entity, new NetCallBack() {
                                    @Override
                                    public void onMySuccess(JSONObject result) {
                                        Toast.makeText(NotifyActivity.this, "你已拒绝加入该项目", Toast.LENGTH_LONG).show();
                                        notifyViewModel.update();
                                    }

                                    @Override
                                    public void onMyFailure(String error, String data) {

                                    }
                                });
                            }
                        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                JSONObject body = new JSONObject();
                                StringEntity entity = new StringEntity(body.toJSONString(), "UTF-8");
                                Request.clientPost(NotifyActivity.this, "project/invite/" + invite.getInvite_uid() + "/accept", entity, new NetCallBack() {
                                    @Override
                                    public void onMySuccess(JSONObject result) {
                                        Toast.makeText(NotifyActivity.this, "你已加入成功", Toast.LENGTH_LONG).show();
                                        notifyViewModel.update();
                                    }

                                    @Override
                                    public void onMyFailure(String error, String data) {
                                        Toast.makeText(NotifyActivity.this, error, Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }).show();
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        int pos = (int) v.getTag();
        Notify notify = notifyList.get(pos);
        androidx.appcompat.app.AlertDialog dialog = new AlertDialog.Builder(NotifyActivity.this).setTitle("提示")
                .setMessage("任务【" + notify.getTaskName() + "】")
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                JSONObject body = new JSONObject();
                StringEntity entity = new StringEntity(body.toJSONString(), "UTF-8");
                Request.clientPost(NotifyActivity.this, "task/" + notify.getNotify_uid() + "/reject", entity, new NetCallBack() {
                    @Override
                    public void onMySuccess(JSONObject result) {
                        Toast.makeText(NotifyActivity.this, "您已拒绝任务", Toast.LENGTH_LONG).show();
                        notifyViewModel.update();
                    }

                    @Override
                    public void onMyFailure(String error, String data) {
                        Toast.makeText(NotifyActivity.this, error, Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).setPositiveButton("接受", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                JSONObject body = new JSONObject();
                StringEntity entity = new StringEntity(body.toJSONString(), "UTF-8");
                Request.clientPost(NotifyActivity.this, "task/" + notify.getNotify_uid() + "/accept", entity, new NetCallBack() {
                    @Override
                    public void onMySuccess(JSONObject result) {
                        Toast.makeText(NotifyActivity.this, "您已接受任务", Toast.LENGTH_LONG).show();
                        notifyViewModel.update();
                    }

                    @Override
                    public void onMyFailure(String error, String data) {
                        Toast.makeText(NotifyActivity.this, error, Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
package com.example.projectmanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.projectmanager.adapter.MyPagerAdapter;
import com.example.projectmanager.model.GlobalData;
import com.example.projectmanager.util.Cache;
import com.example.projectmanager.util.Check;
import com.example.projectmanager.util.NetCallBack;
import com.example.projectmanager.util.Request;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.security.auth.callback.CallbackHandler;

import cz.msebera.android.httpclient.entity.StringEntity;

public class LoginActivity extends AppCompatActivity {
    private TabLayout tbSelect;
    private ViewPager vpChosen;
    private EditText log_account;
    private EditText log_password;
    private CheckBox keep_state;
    private CheckBox auto_login;
    private EditText et_account;
    private EditText et_password;
    private EditText et_password_re;
    private EditText et_nickname;
    private EditText et_phone;
    private EditText et_position;
    private EditText et_synopsis;
    private Button bt_login;
    private Button bt_register;

    View loginView;
    View registerView;
    ArrayList<View> viewList;
    ArrayList<String> tabIndicators;

    private String login_account;
    private String login_password;
    private String reg_password;
    private String reg_password_re;
    private Boolean is_keep_state;
    private Boolean is_auto_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findView();
        initView();
        initSharedPreferences();

        if(is_keep_state){
            keep_state.setChecked(true);
        }else{
            keep_state.setChecked(false);
        }


        keep_state.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                is_keep_state = isChecked;
                System.out.println(is_keep_state);
                if(is_keep_state){
                    keepState();
                }else{
                    auto_login.setChecked(false);
                    initState();
                }
            }
        });

        auto_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                is_auto_login = isChecked;

            }
        });

        et_account.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String account = et_account.getText().toString();
                    if(!Check.isEmail(account)){
                        Toast.makeText(LoginActivity.this,"邮箱格式不正确!", Toast.LENGTH_LONG).show();
                        et_account.setText("");
                    }
                }
            }
        });

        et_password_re.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    reg_password = et_password.getText().toString();
                    reg_password_re = et_password_re.getText().toString();
                    if(!reg_password.equals(reg_password_re)){
                        Toast.makeText(LoginActivity.this, "请确认两次密码相同!", Toast.LENGTH_LONG).show();
                        et_password_re.setText("");
                    }
                }
            }
        });

        et_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String phone = et_phone.getText().toString();
                    if(!Check.isPhone(phone)){
                        Toast.makeText(LoginActivity.this,"号码格式不正确!", Toast.LENGTH_LONG).show();
                        et_phone.setText("");
                    }
                }
            }
        });

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_account = log_account.getText().toString();
                login_password = log_password.getText().toString();
                if(login_account.equals("") || login_password.equals("")){
                    Toast.makeText(LoginActivity.this,"请填写完整信息!", Toast.LENGTH_LONG).show();
                }else{
                    login();
                }
            }
        });

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reg_account = et_account.getText().toString();
                String reg_password = et_password.getText().toString();
                String reg_password_re = et_password_re.getText().toString();
                String reg_nickname = et_nickname.getText().toString();
                String reg_phone = et_phone.getText().toString();
                String reg_position = et_position.getText().toString();
                String reg_synopsis = et_synopsis.getText().toString();
                if(reg_account.equals("") || reg_password.equals("") || reg_password_re.equals("")){
                    Toast.makeText(LoginActivity.this, "请填写完整信息!", Toast.LENGTH_LONG).show();
                }else{
                    if(!Check.isEmail(reg_account)){
                        Toast.makeText(LoginActivity.this, "邮箱格式不正确!", Toast.LENGTH_LONG).show();
                    }else{
                        if(!reg_password.equals(reg_password_re)){
                            Toast.makeText(LoginActivity.this, "请确认两次密码相同!", Toast.LENGTH_LONG).show();
                            et_password_re.setText("");
                        }else{
                            if(!Check.isPhone(reg_phone)){
                                Toast.makeText(LoginActivity.this,"号码格式不正确!", Toast.LENGTH_LONG).show();
                                et_phone.setText("");
                            }else{
                                if(reg_nickname.equals("")){
                                    reg_nickname = "打工人";
                                }
                                JSONObject body = new JSONObject();
                                body.put("username", reg_account);
                                body.put("password", reg_password);
                                body.put("nickName", reg_nickname);
                                body.put("phoneNum", reg_phone);
                                body.put("position", reg_position);
                                body.put("synopsis", reg_synopsis);
                                StringEntity entity = new StringEntity(body.toJSONString(), "UTF-8");
                                Request.clientPost(LoginActivity.this, "account/add", entity, new NetCallBack() {
                                    @Override
                                    public void onMySuccess(JSONObject result) {
                                        Toast.makeText(LoginActivity.this, "注册成功！", Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onMyFailure(String error) {
                                        Toast.makeText(LoginActivity.this, error, Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    }
                }
            }
        });

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
    }

    private void initSharedPreferences() {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("keep_state", getApplication().MODE_PRIVATE);
        log_account.setText(sharedPreferences.getString("account", ""));
        log_password.setText(sharedPreferences.getString("password", ""));
        is_keep_state = sharedPreferences.getBoolean("keep_state",false);
    }

    //记住账号密码,数据存入sharedpreferences
    private void keepState() {
        login_account = log_account.getText().toString();
        login_password = log_password.getText().toString();
        Cache.keep_state(LoginActivity.this, login_account, login_password, is_keep_state);
    }

    private void initState() {
        Cache.init_state(LoginActivity.this, is_keep_state);
    }

    private void login(){
        JSONObject body = new JSONObject();
        body.put("username", login_account);
        body.put("password", login_password);
        StringEntity entity = new StringEntity(body.toJSONString(), "UTF-8");
        Request.clientPost(LoginActivity.this, "login", entity, new NetCallBack() {
            @Override
            public void onMySuccess(JSONObject result) {
                String token = result.getString("token");
                final GlobalData app = (GlobalData) getApplication();
                app.setToken(token);
                GlobalData.save_account(result, LoginActivity.this);
                Request.clientGet(LoginActivity.this, "account/" + login_account, new NetCallBack() {
                    @Override
                    public void onMySuccess(JSONObject result) {
                        GlobalData.save_account(result, LoginActivity.this);
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onMyFailure(String error) {
                        Toast.makeText(LoginActivity.this, error, Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onMyFailure(String error) {
                Toast.makeText(LoginActivity.this, error, Toast.LENGTH_LONG).show();
                log_account.setText("");
                log_password.setText("");
            }
        });
    }

    private void initView() {
        viewList = new ArrayList<View>();
        tabIndicators = new ArrayList<String>();
        LayoutInflater li = getLayoutInflater();

        loginView = li.inflate(R.layout.login, null, false);
        registerView = li.inflate(R.layout.register, null, false);

        log_account = (EditText) loginView.findViewById(R.id.log_account);
        log_password = (EditText) loginView.findViewById(R.id.log_password);
        keep_state = (CheckBox) loginView.findViewById(R.id.keep_state);
        auto_login = (CheckBox) loginView.findViewById(R.id.auto_login);
        bt_login = (Button) loginView.findViewById(R.id.bt_login);

        et_account = (EditText) registerView.findViewById(R.id.et_account);
        et_password = (EditText) registerView.findViewById(R.id.et_password);
        et_password_re = (EditText) registerView.findViewById(R.id.et_password_re);
        et_nickname = (EditText) registerView.findViewById(R.id.et_nickname);
        et_phone = (EditText) registerView.findViewById(R.id.et_phone);
        et_position = (EditText) registerView.findViewById(R.id.et_position);
        et_synopsis = (EditText) registerView.findViewById(R.id.et_synopsis);
        bt_register = (Button) registerView.findViewById(R.id.bt_register);

        viewList.add(loginView);
        viewList.add(registerView);
        tabIndicators.add("登 录");
        tabIndicators.add("注 册");
        MyPagerAdapter mAdapter = new MyPagerAdapter(viewList, tabIndicators);
        vpChosen.setAdapter((mAdapter));
    }


    private void findView() {
        vpChosen = (ViewPager) findViewById(R.id.vp_chosen);
        tbSelect = (TabLayout) findViewById(R.id.tb_select);
        tbSelect.setupWithViewPager(vpChosen);
    }

}
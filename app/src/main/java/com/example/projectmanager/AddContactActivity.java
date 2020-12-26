package com.example.projectmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.projectmanager.adapter.MemberAdapter;
import com.example.projectmanager.model.Member;
import com.example.projectmanager.model.Project;
import com.example.projectmanager.util.Check;
import com.example.projectmanager.util.DatabaseManager;
import com.example.projectmanager.util.NetCallBack;
import com.example.projectmanager.util.Request;

import java.util.ArrayList;
import java.util.List;

public class AddContactActivity extends AppCompatActivity {
    private String et_account;
    private SearchView searchView;
    View contact_view;
    private TextView search_result;
    private String nickname;
    private String account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //修改页面背景色
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.gray);
        this.getWindow().setBackgroundDrawable(drawable);
        initView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                et_account = query;
                if(et_account.equals("")){
                    Toast.makeText(AddContactActivity.this, "请输入账号！", Toast.LENGTH_LONG).show();
                }
                if(!Check.isEmail(et_account)){
                    Toast.makeText(AddContactActivity.this, "邮箱格式不正确！", Toast.LENGTH_LONG).show();
                    searchView.setQuery("",false);
                }else{
                    ListView member_list = findViewById(R.id.list_view);
                    Request.clientGet(AddContactActivity.this, "/account/" + et_account, new NetCallBack() {
                        @Override
                        public void onMySuccess(JSONObject result) {
                            search_result.setVisibility(View.VISIBLE);
                            System.out.println(result);
                            String liststring = JSONObject.toJSONString(result);
                            System.out.println("ListString:"+liststring);
                            nickname = result.getString("nickName");
                            account = result.getString("username");
                            System.out.println("昵称:"+nickname);
                            System.out.println("账号:"+account);
                            List<List<String>> memberList = new ArrayList<List<String>>();
                            List<String> columnList = new ArrayList<String>();
                            columnList.add(0,nickname);
                            columnList.add(1,account);
                            memberList.add(0,columnList);
                            System.out.println(memberList);
                            MemberAdapter adapter = new MemberAdapter(AddContactActivity.this, memberList);
                            member_list.setAdapter(adapter);
                            member_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    List<String> columnList = memberList.get(position);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("username", account);
                                    Intent intent = new Intent(AddContactActivity.this, MemberDetailActivity.class);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            });
                            adapter.setOnItemDeleteClickListener(new MemberAdapter.onItemAddListener() {
                                @Override
                                public void onAddClick(int position) {
                                    DatabaseManager.add(AddContactActivity.this, nickname, account);
                                    Toast.makeText(AddContactActivity.this, "添加成功!", Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                        @Override
                        public void onMyFailure(String error) {
                            Toast.makeText(AddContactActivity.this, error, Toast.LENGTH_LONG).show();
                            searchView.setQuery("",false);
                        }
                    });
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }



    private void initView() {
        searchView = (SearchView) findViewById(R.id.search);
        search_result = (TextView) findViewById(R.id.search_result);
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
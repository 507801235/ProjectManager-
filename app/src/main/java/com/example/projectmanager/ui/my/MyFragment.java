package com.example.projectmanager.ui.my;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.projectmanager.AboutUsActivity;
import com.example.projectmanager.GetInfoActivity;
import com.example.projectmanager.R;
import com.example.projectmanager.SettingActivity;
import com.example.projectmanager.model.GlobalData;
import com.facebook.drawee.view.SimpleDraweeView;

public class MyFragment extends Fragment {
    private TextView tv_nickname;
    private TextView tv_account;
    private SimpleDraweeView ivAvatar;
    private MyViewModel mViewModel;
    GlobalData app;
    private static String[] title = {"个人信息", "账号设置", "关于我们"};
    public static MyFragment newInstance() {
        return new MyFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my, container, false);
        initView(root);
        app = (GlobalData) getActivity().getApplication();

        tv_nickname.setText(app.getNickName());
        System.out.println(app.getNickName());
        System.out.println(app.getUsername());
        System.out.println(app.getAvatarUrl());
        tv_account.setText(app.getUsername());
        Uri uri = Uri.parse(app.getAvatarUrl());
        ivAvatar.setImageURI(uri);
        ListView menuList = root.findViewById(R.id.list_view);
        menuList.setAdapter(new MyAdapter());
        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        getInfo();
                        break;
                    case 1:
                        setting();
                        break;
                    case 2:
                        aboutUs();
                        break;
                    default: break;
                }
            }
        });
        return root;
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return title.length;
        }

        @Override
        public Object getItem(int position) {
            return title[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.menu_item, parent, false);
            TextView menu = (TextView)view.findViewById(R.id.menu_title);
            menu.setText(title[position]);
            return view;
        }
    }

    private void initView(View root) {
        tv_nickname = (TextView) root.findViewById(R.id.nickname);
        tv_account = (TextView) root.findViewById(R.id.account);
        ivAvatar = (SimpleDraweeView) root.findViewById(R.id.iv_avatar);
    }

    private void getInfo(){
        Intent intent = new Intent(getActivity(), GetInfoActivity.class);
        startActivity(intent);
    }

    private void setting(){
        Intent intent = new Intent(getActivity(), SettingActivity.class);
        startActivity(intent);
    }

    private void aboutUs(){
        Intent intent = new Intent(getActivity(), AboutUsActivity.class);
        startActivity(intent);
    }
}
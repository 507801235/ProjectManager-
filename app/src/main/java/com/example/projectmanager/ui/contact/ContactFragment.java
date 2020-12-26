package com.example.projectmanager.ui.contact;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.projectmanager.AddContactActivity;
import com.example.projectmanager.ProjectDetailActivity;
import com.example.projectmanager.R;
import com.example.projectmanager.adapter.MemberAdapter;
import com.example.projectmanager.util.DatabaseManager;
import com.facebook.common.streams.LimitedInputStream;

import java.util.ArrayList;
import java.util.List;

public class ContactFragment extends Fragment {
    private List<List<String>> contactList;
    private List<String> contact;
    private SearchView searchView;
    private String account;
    private ContactViewModel mViewModel;

    public static ContactFragment newInstance() {
        return new ContactFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_contact, container, false);
        initView(root);
        ListView contact_List = (ListView) root.findViewById(R.id.list_view);
        final List<List<String>> contactList = new ArrayList<List<String>>();
        List<String> contact = new ArrayList<String>();
        DatabaseManager.query(getActivity(), contactList, contact);
        MemberAdapter adapter = new MemberAdapter(getActivity(), contactList);
        contact_List.setAdapter(adapter);
        System.out.println(contactList);
        System.out.println(contactList.get(0));
        System.out.println(contactList.get(1));

        contact_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<String> columnList = contactList.get(position);
                columnList.get(0);
                System.out.println(columnList);
                account = columnList.get(1);
                System.out.println(account);
                Bundle bundle = new Bundle();
                bundle.putSerializable("username", account);
                Intent intent = new Intent(getActivity(), ProjectDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        adapter.setOnItemDeleteClickListener(new MemberAdapter.onItemAddListener() {
            @Override
            public void onAddClick(int position) {
                androidx.appcompat.app.AlertDialog dialog = new AlertDialog.Builder(getActivity()).setTitle("提示").setMessage("是否移除该联系人")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            List<String> columnList = contactList.get(position);
                            columnList.get(0);
                            System.out.println(columnList);
                            account = columnList.get(1);
                            System.out.println(account);
                            DatabaseManager.remove(getActivity(),account);
                            Toast.makeText(getActivity(), "移除成功!", Toast.LENGTH_LONG).show();
                            contactList.remove(position);
                            adapter.notifyDataSetChanged();
                        }
                    })
                    .show();
            }
        });
        return root;
    }

    private void initView(View view) {
        searchView = (SearchView) view.findViewById(R.id.search);
    }

}
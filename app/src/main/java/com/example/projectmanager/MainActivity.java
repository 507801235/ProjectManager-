package com.example.projectmanager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.example.projectmanager.ui.contact.ContactFragment;
import com.example.projectmanager.ui.home.HomeFragment;
import com.example.projectmanager.ui.my.MyFragment;
import com.example.projectmanager.ui.project.ProjectFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private HomeFragment HomeFragment;
    private ProjectFragment ProjectFragment;
    private ContactFragment ContactFragment;
    private MyFragment MyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(HomeFragment == null){
            HomeFragment = new HomeFragment();
            fragmentTransaction.add(R.id.nav_host_fragment, HomeFragment);
        }else{
            fragmentTransaction.show(HomeFragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setTabSelection(0);
                    return true;
                case R.id.navigation_project:
                    setTabSelection(1);
                    return true;
                case R.id.navigation_contact:
                    setTabSelection(2);
                    return true;
                case R.id.navigation_my:
                    setTabSelection(3);
                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void setTabSelection(int index) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragment(fragmentTransaction);
        switch (index){
            case 0:
                if(HomeFragment == null){
                    HomeFragment = new HomeFragment();
                    fragmentTransaction.add(R.id.nav_host_fragment, HomeFragment);
                }else{
                    fragmentTransaction.show(HomeFragment);
                }
                break;

            case 1:
                if(ProjectFragment == null){
                    ProjectFragment = new ProjectFragment();
                    fragmentTransaction.add(R.id.nav_host_fragment, ProjectFragment);
                }else{
                    fragmentTransaction.show(ProjectFragment);
                }
                break;

            case 2:
                if(ContactFragment == null){
                    ContactFragment = new ContactFragment();
                    fragmentTransaction.add(R.id.nav_host_fragment, ContactFragment);
                }else{
                    fragmentTransaction.show(ContactFragment);
                }
                break;

            case 3:
                if(MyFragment == null){
                    MyFragment = new MyFragment();
                    fragmentTransaction.add(R.id.nav_host_fragment, MyFragment);
                }else{
                    fragmentTransaction.show(MyFragment);
                }
                break;
        }
        fragmentTransaction.commit();
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (HomeFragment != null) {
            fragmentTransaction.hide(HomeFragment);
        }
        if (ProjectFragment != null) {
            fragmentTransaction.hide(ProjectFragment);
        }
        if (ContactFragment != null) {
            fragmentTransaction.hide(ContactFragment);
        }
        if (MyFragment != null) {
            fragmentTransaction.hide(MyFragment);
        }
    }
}
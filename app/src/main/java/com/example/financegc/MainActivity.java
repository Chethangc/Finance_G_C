package com.example.financegc;

import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.financegc.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    final MenuItem[] previousItem = {null};

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        toggle =  new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.OpenDrawer,R.string.CloseDrawer);
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();
        toggle.setDrawerSlideAnimationEnabled(true);

        loadFragment(new Home());
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(previousItem[0] !=null){
                    previousItem[0].setChecked(false);
                }
                if(id == R.id.action_savings){
                    actionOnItemSelected(item,new Savings());
                }
                else if(id==R.id.action_spendings){
                    actionOnItemSelected(item,new Spendings());
                }
                else if(id==R.id.action_earnings){
                    actionOnItemSelected(item,new Earnings());
                }
                else if(id==R.id.action_loans){
                    actionOnItemSelected(item,new Loans());
                }
                else if(id==R.id.action_investments){
                    actionOnItemSelected(item,new Investments());
                }
                else if(id==R.id.action_home){
                    actionOnItemSelected(item,new Home());
                }


                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }


        });


    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            FragmentManager manager = getSupportFragmentManager();
            int count = manager.getBackStackEntryCount();
            if(count==0){
                super.onBackPressed();
            }
            else{

                FragmentTransaction transaction = manager.beginTransaction();
                manager.popBackStackImmediate();
                transaction.commit();
            }

        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.container, fragment);
        transaction.commit();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    public int getFragmentCount(){
        FragmentManager manager = getSupportFragmentManager();
        int count = manager.getBackStackEntryCount();
        return count;
    }

    public void actionOnItemSelected(MenuItem item,Fragment fragment){
        int count;
        count = getFragmentCount();
        if(count!= 0){
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.addToBackStack(null);
            transaction.commit();
        }
        item.setChecked(true);
        loadFragment(fragment);
        previousItem[0] =item;
    }
}
package com.alo.alomobile.activitys;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.alo.alomobile.R;
import com.alo.alomobile.app.Application;
import com.alo.alomobile.app.Util;
import com.alo.alomobile.fragments.ManagerUserFragment;
import com.alo.alomobile.fragments.MenuFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Robson Coutinho
 * @version 1.0
 * @since  22/02/2017.
 */


public class MainActivity extends AppCompatActivity {
    private static String TAG = MainActivity.class.getSimpleName();
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG,"onCreate()");
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences("alo_prefs", MainActivity.MODE_PRIVATE);

        if (!Util.existeToken(prefs)) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            finish();
            MainActivity.this.startActivity(intent);
        }

        setContentView(R.layout.icons_tab_layout);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        configViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        configTabIcons();

    }

    private void configViewPager(ViewPager viewPager) {
        Log.i(TAG,"configViewPager()");
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new MenuFragment(), "MenuFragment");
        adapter.addFrag(new ManagerUserFragment(), "ManagerUserFragment");
        viewPager.setAdapter(adapter);
    }

    private void configTabIcons() {
        Log.i(TAG,"configTabIcons()");
        int[] tabIcons = {
                R.drawable.cart,
                R.drawable.user,
        };

        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }




}

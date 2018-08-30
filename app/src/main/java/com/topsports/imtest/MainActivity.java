package com.topsports.imtest;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;

import com.example.common.widget.adapter.ViewPagerAdapter;
import com.example.common.widget.navigation.BottomNavigationViewHelper;
import com.topsports.imtest.base.BaseActivity;
import com.topsports.imtest.ui.conversation.ConversationFragment;

public class MainActivity extends BaseActivity {

    private ViewPager mViewPager;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        mViewPager = findViewById(R.id.view_pager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(ConversationFragment.newInstance());
        viewPagerAdapter.addFragment(ConversationFragment.newInstance());
        viewPagerAdapter.addFragment(ConversationFragment.newInstance());
        viewPagerAdapter.addFragment(ConversationFragment.newInstance());
        mViewPager.setAdapter(viewPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);
        initBottomNavigation();

    }

    private void initBottomNavigation() {
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper bottomNavigationViewHelper = new BottomNavigationViewHelper(bottomNavigation, mViewPager);
        bottomNavigationViewHelper.disableShiftMode();
        bottomNavigationViewHelper.setNavigationSelectListener(false);
    }


}

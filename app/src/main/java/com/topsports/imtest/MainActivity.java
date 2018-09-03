package com.topsports.imtest;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
import com.example.common.net.subscriber.BaseObjectObserver;
import com.example.common.permission.Permission;
import com.example.common.permission.RxPermissions;
import com.example.common.utils.ToastUtil;
import com.example.common.widget.adapter.ViewPagerAdapter;
import com.example.common.widget.navigation.BottomNavigationViewHelper;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.business.contact.selector.activity.ContactSelectActivity;
import com.netease.nim.uikit.business.recent.RecentContactsFragment;
import com.netease.nim.uikit.business.team.helper.TeamHelper;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.topsports.imtest.base.BaseActivity;
import com.topsports.imtest.ui.contact.AddFriendActivity;
import com.topsports.imtest.ui.conversation.ConversationFragment;
import com.topsports.imtest.ui.mine.MineFragment;
import com.topsports.imtest.ui.session.SessionFragment;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity {

    private ViewPager mViewPager;
    @BindView(R.id.tool_bar)
    Toolbar mToolbar;
    /**
     * 基本权限管理
     */
    private final String[] BASIC_PERMISSIONS = new String[]{
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.RECORD_AUDIO,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(mToolbar);
        mViewPager = findViewById(R.id.view_pager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
//        viewPagerAdapter.addFragment(ConversationFragment.newInstance());
        viewPagerAdapter.addFragment(new RecentContactsFragment());
        viewPagerAdapter.addFragment(SessionFragment.newInstance());
        viewPagerAdapter.addFragment(ConversationFragment.newInstance());
        viewPagerAdapter.addFragment(MineFragment.newInstance());
        mViewPager.setAdapter(viewPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);
        initBottomNavigation();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermission();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.requestEach(BASIC_PERMISSIONS)
                .subscribe(new BaseObjectObserver<Permission>() {
                    @Override
                    public void onSuccess(Permission permission) {
                        if (permission.granted) {
                            LogUtils.d(TAG, "授权成功 = " + permission.name);
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            LogUtils.d(TAG, "未全部授权，Denied permission without ask never again \n name = " + permission.name);
                        } else {
                            LogUtils.d(TAG, "未全部授权，Denied permission with ask never again  \n name = " + permission.name);
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        ToastUtil.showLongToast("未全部授权，部分功能可能无法正常运行！");
                    }
                });
    }

    private void initBottomNavigation() {
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper bottomNavigationViewHelper = new BottomNavigationViewHelper(bottomNavigation, mViewPager);
        bottomNavigationViewHelper.disableShiftMode();
        bottomNavigationViewHelper.setNavigationSelectListener(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                break;
            case R.id.create_normal_team:
                break;
            case R.id.create_regular_team:
                break;
            case R.id.search_advanced_team:
                break;
            case R.id.add_buddy:
                AddFriendActivity.start(this);
                break;
            case R.id.search_btn:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 109) {
            ToastUtil.showLongToast("aaa");
        }
    }
}

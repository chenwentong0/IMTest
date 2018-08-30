package com.topsports.imtest.ui;

import com.topsports.imtest.MainActivity;
import com.topsports.imtest.R;
import com.topsports.imtest.base.BaseActivity;
import com.topsports.imtest.manager.UserManager;
import com.topsports.imtest.ui.login.LoginActivity;

/**
 * Date 2018/8/30
 * Time 12:02
 *
 * @author wentong.chen
 */
public class SplashActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initData() {
        //判断是否之前登录过
        startActivity(LoginActivity.class);
        finish();
//        if (UserManager.getInstance().canLogin()) {
//            startActivity(MainActivity.class);
//            finish();
//        } else {
//
//            finish();
//        }
    }
}

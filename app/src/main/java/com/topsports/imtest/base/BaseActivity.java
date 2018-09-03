package com.topsports.imtest.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.example.common.base.CBaseActivity;
import com.example.common.widget.TitleBar;
import com.netease.nim.uikit.common.activity.ToolBarOptions;
import com.topsports.imtest.R;

import butterknife.ButterKnife;

/**
 * Date 2018/8/24
 * Time 12:06
 *
 * @author wentong.chen
 */
public abstract class BaseActivity extends CBaseActivity {
    protected TitleBar mTitleBar;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mTitleBar = findViewById(R.id.title_bar);
        if (mTitleBar != null) {
            mTitleBar.setLeftClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickBackBtn(v);
                }
            });
        }
        initView();
        initListener();
        initEvent();
        initData();
    }

    /**
     * 点击返回按钮的回调
     */
    protected void onClickBackBtn(View view) {
        finish();
    }

    public void setToolBar(int toolBarId, ToolBarOptions options) {
        mToolbar = (Toolbar) findViewById(toolBarId);
        if (options.titleId != 0) {
            mToolbar.setTitle(options.titleId);
        }
        if (!TextUtils.isEmpty(options.titleString)) {
            mToolbar.setTitle(options.titleString);
        }
        if (options.logoId != 0) {
            mToolbar.setLogo(options.logoId);
        }
        setSupportActionBar(mToolbar);

        if (options.isNeedNavigate) {
            mToolbar.setNavigationIcon(options.navigateId);
            mToolbar.setContentInsetStartWithNavigation(0);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickBackBtn(v);
                }
            });
        }
    }

    public void setToolBar(int toolbarId, int titleId, int logoId) {
        mToolbar = (Toolbar) findViewById(toolbarId);
        mToolbar.setTitle(titleId);
        mToolbar.setLogo(logoId);
        setSupportActionBar(mToolbar);
    }

    public Toolbar getToolBar() {
        return mToolbar;
    }
}

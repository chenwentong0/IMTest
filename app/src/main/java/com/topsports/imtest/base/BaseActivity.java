package com.topsports.imtest.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.common.base.CBaseActivity;
import com.example.common.widget.TitleBar;
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
}

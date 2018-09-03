package com.topsports.imtest.ui.mine;

import android.view.View;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;
import com.topsports.imtest.R;
import com.topsports.imtest.base.BaseFragment;
import com.topsports.imtest.manager.UserManager;
import com.topsports.imtest.ui.login.LoginActivity;

import butterknife.OnClick;

/**
 * Date 2018/9/3
 * Time 15:18
 *
 * @author wentong.chen
 */
public class MineFragment extends BaseFragment {

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @OnClick(R.id.btn_logout)
    public void logout(View view) {
        NIMClient.getService(AuthService.class).logout();
        UserManager.getInstance().setAutoLogin(false);
        startActivity(LoginActivity.class);
        getActivity().finish();
    }
}

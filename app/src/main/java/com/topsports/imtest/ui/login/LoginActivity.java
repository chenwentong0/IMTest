package com.topsports.imtest.ui.login;

import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
import com.example.common.net.core.NetService;
import com.example.common.utils.ToastUtil;
import com.example.common.widget.TitleBar;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.common.ui.widget.ClearableEditTextWithIcon;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nim.uikit.common.util.string.MD5;
import com.netease.nim.uikit.impl.NimUIKitImpl;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.topsports.imtest.MainActivity;
import com.topsports.imtest.R;
import com.topsports.imtest.base.BaseActivity;
import com.topsports.imtest.common.Constants;
import com.topsports.imtest.manager.UserManager;
import com.topsports.imtest.net.ContactHttpClient;
import com.topsports.imtest.net.INetService;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Date 2018/8/30
 * Time 13:35
 *
 * @author wentong.chen
 * 登录注册页面
 */
public class LoginActivity extends BaseActivity {


    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.et_register_account)
    ClearableEditTextWithIcon etRegisterAccount;
    @BindView(R.id.et_register_nickname)
    ClearableEditTextWithIcon etRegisterNickname;
    @BindView(R.id.et_register_pwd)
    ClearableEditTextWithIcon etRegisterPwd;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.ll_register)
    LinearLayout llRegister;
    @BindView(R.id.et_login_account)
    ClearableEditTextWithIcon etLoginAccount;
    @BindView(R.id.et_login_pwd)
    ClearableEditTextWithIcon etLoginPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.ll_login)
    LinearLayout llLogin;
    @BindView(R.id.btn_switch_mode)
    Button btnSwitchMode;
    private INetService mNetService;
    private boolean registerMode = true;
    private AbortableFuture<LoginInfo> mLoginRequest;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        mTitleBar.setTitle("登录/注册");
        mNetService = NetService.getInstance().getService();
        mTitleBar.getTvTitle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (registerMode) {
                    etRegisterAccount.setText("chen11111");
                    etRegisterNickname.setText("chen11111");
                    etRegisterPwd.setText("11111111");
                } else {
                    etLoginAccount.setText("chen11111");
                    etLoginPwd.setText("11111111");
                }
            }
        });
    }

    @Override
    protected void initData() {
        LoginInfo loginInfo = UserManager.getInstance().getLoginInfo();
        if (loginInfo != null) {
            if (!UserManager.getInstance().isAutoLogin()) {
                switchMode(false);
                etLoginAccount.setText(loginInfo.getAccount());
                etLoginAccount.requestFocus();
            } else {
                login(loginInfo.getAccount(), loginInfo.getToken());
            }
        }
    }

    private void register() {
        final String account = etRegisterAccount.getText().toString();
        String nickName = etRegisterNickname.getText().toString();
        final String pwd = etRegisterPwd.getText().toString();
        DialogMaker.showProgressDialog(this, getString(R.string.registering), false);

        // 注册流程

        ContactHttpClient.getInstance().register(account, nickName, pwd, new ContactHttpClient.ContactHttpCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                LogUtils.d(TAG, getString(R.string.register_success));
                Toast.makeText(LoginActivity.this, R.string.register_success, Toast.LENGTH_SHORT).show();


                DialogMaker.dismissProgressDialog();
                String token = tokenFromPassword(pwd);
                UserManager.getInstance().saveLoginInfo(new LoginInfo(account, token));
                login(account, token);
            }

            @Override
            public void onFailed(int code, String errorMsg) {
                LogUtils.d(TAG, getString(R.string.register_failed));
                ToastUtil.showLongToast(getString(R.string.register_failed));

                DialogMaker.dismissProgressDialog();
            }
        });
    }

    @OnClick({R.id.btn_switch_mode, R.id.btn_login, R.id.btn_register})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn_switch_mode:
                switchMode(!registerMode);
                break;

            case R.id.btn_register:
                register();
                break;
            case R.id.btn_login:
                String account = etLoginAccount.getText().toString();
                String pwd = etLoginPwd.getText().toString();
                String token = tokenFromPassword(pwd);
                UserManager.getInstance().saveLoginInfo(new LoginInfo(account, token));
                login(account, token);
                break;
            default:
        }
    }

    private void switchMode(boolean registerMode) {
        this.registerMode = registerMode;
        llLogin.setVisibility(this.registerMode ? View.GONE : View.VISIBLE);
        llRegister.setVisibility(!this.registerMode ? View.GONE : View.VISIBLE);
    }

    private void login(String account, String token) {
        if (checkStr(account) || checkStr(token)) {
            return;
        }

        DialogMaker.showProgressDialog(this, null, getString(R.string.logining), true, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (mLoginRequest != null) {
                    mLoginRequest.abort();
                    onLoginDone();
                }
            }
        }).setCanceledOnTouchOutside(false);
        mLoginRequest = NimUIKitImpl.login(UserManager.getInstance().getLoginInfo(), new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo loginInfo) {
                LogUtil.i(TAG, "login success");
                ToastUtil.showLongToast("登录成功");

                onLoginDone();

                startActivity(MainActivity.class);
                finish();
            }

            @Override
            public void onFailed(int code) {
                onLoginDone();
                if (code == 302 || code == 404) {
                    Toast.makeText(LoginActivity.this, R.string.login_failed, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "登录失败: " + code, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onException(Throwable throwable) {
                Toast.makeText(LoginActivity.this, R.string.login_exception, Toast.LENGTH_LONG).show();
                onLoginDone();
            }
        });
    }

    private void onLoginDone() {
        mLoginRequest = null;
        DialogMaker.dismissProgressDialog();
    }

    private boolean checkStr(String str) {
        if (str == null || str.length() < 5) {
            return true;
        }
        return false;
    }

    //DEMO中使用 username 作为 NIM 的account ，md5(password) 作为 token
    //开发者需要根据自己的实际情况配置自身用户系统和 NIM 用户系统的关系
    private String tokenFromPassword(String password) {
        String appKey = Constants.APP_KEY;
        boolean isDemo = "45c6af3c98409b18a84451215d0bdd6e".equals(appKey)
                || "fe416640c8e8a72734219e1847ad2547".equals(appKey);

        return isDemo ? MD5.getStringMD5(password) : password;
    }
}

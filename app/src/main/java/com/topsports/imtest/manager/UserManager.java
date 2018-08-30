package com.topsports.imtest.manager;

import android.text.TextUtils;

import com.example.common.utils.CacheDiskUtils;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.topsports.imtest.common.Constants;

import java.io.Serializable;

/**
 * Date 2018/8/30
 * Time 14:44
 *
 * @author wentong.chen
 */
public class UserManager {
    private static UserManager sInstance;
    private UserManager() {}

    public static UserManager getInstance() {
        if (sInstance == null) {
            synchronized (UserManager.class) {
                if (sInstance == null) {
                    sInstance = new UserManager();
                }
            }
        }
        return sInstance;
    }

    public boolean canLogin() {
        LoginInfo loginInfo = getLoginInfo();
        return loginInfo != null && !TextUtils.isEmpty(loginInfo.getAccount()) && !TextUtils.isEmpty(loginInfo.getToken());
    }

    public LoginInfo getLoginInfo() {
        Object object = CacheDiskUtils.getInstance().getSerializable(Constants.USER_KEY);
        if (object != null) {
            return (LoginInfo) object;
        }
        return null;
    }

    public void saveLoginInfo(LoginInfo loginInfo) {
        CacheDiskUtils.getInstance().put(Constants.USER_KEY, (Serializable) loginInfo);
    }

    public void clear() {
        CacheDiskUtils.getInstance().clear();
    }
}

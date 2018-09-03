package com.topsports.imtest.ui.contact;

import android.content.Context;

import com.example.common.utils.ToastUtil;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.api.model.contact.ContactEventListener;

/**
 * UIKit联系人列表定制展示类
 * <p/>
 * Created by huangjun on 2015/9/11.
 */
public class ContactHelper {

    public static void init() {
        setContactEventListener();
    }

    private static void setContactEventListener() {
        NimUIKit.setContactEventListener(new ContactEventListener() {
            @Override
            public void onItemClick(Context context, String account) {
                ToastUtil.showLongToast("进入用户信息页面");
                UserProfileActivity.start(context, account);
            }

            @Override
            public void onItemLongClick(Context context, String account) {

            }

            @Override
            public void onAvatarClick(Context context, String account) {
                ToastUtil.showLongToast("进入用户信息页面");
                UserProfileActivity.start(context, account);
            }
        });
    }

}

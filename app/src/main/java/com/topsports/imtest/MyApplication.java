package com.topsports.imtest;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.example.common.net.core.NetService;
import com.example.common.utils.BaseUtil;
import com.example.common.utils.CacheDiskUtils;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.util.NIMUtil;
import com.topsports.imtest.common.Constants;
import com.topsports.imtest.manager.UserManager;
import com.topsports.imtest.net.INetService;
import com.topsports.imtest.ui.contact.ContactHelper;

/**
 * Date 2018/8/30
 * Time 11:45
 *
 * @author wentong.chen
 */
public class MyApplication extends Application {

    private static Context sContext;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        BaseUtil.init(this);
        NetService.init(INetService.baseUrl, INetService.class);
        initSdk();
    }

    private void initSdk() {
        // SDK初始化（启动后台服务，若已经存在用户登录信息， SDK 将完成自动登录）
        NIMClient.init(this, UserManager.getInstance().getLoginInfo(), null);

        // ... your codes
        if (NIMUtil.isMainProcess(this)) {
            // 注意：以下操作必须在主进程中进行
            // 1、UI相关初始化操作
            // 2、相关Service调用
            // 初始化
            NimUIKit.init(this);

            // 可选定制项
            // 注册定位信息提供者类（可选）,如果需要发送地理位置消息，必须提供。
            // demo中使用高德地图实现了该提供者，开发者可以根据自身需求，选用高德，百度，google等任意第三方地图和定位SDK。
//            NimUIKit.setLocationProvider(new NimDemoLocationProvider());

            // 会话窗口的定制: 示例代码可详见demo源码中的SessionHelper类。
            // 1.注册自定义消息附件解析器（可选）
            // 2.注册各种扩展消息类型的显示ViewHolder（可选）
            // 3.设置会话中点击事件响应处理（一般需要）
//            SessionHelper.init();

            // 通讯录列表定制：示例代码可详见demo源码中的ContactHelper类。
            // 1.定制通讯录列表中点击事响应处理（一般需要，UIKit 提供默认实现为点击进入聊天界面)
//            ContactHelper.init();

            // 在线状态定制初始化。
//            NimUIKit.setOnlineStateContentProvider(new DemoOnlineStateContentProvider());
        }
    }

    public static Context getContext() {
        return sContext;
    }
}

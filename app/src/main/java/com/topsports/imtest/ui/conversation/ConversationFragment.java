package com.topsports.imtest.ui.conversation;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.common.utils.ToastUtil;
import com.netease.nim.uikit.business.recent.RecentContactsCallback;
import com.netease.nim.uikit.business.recent.RecentContactsFragment;
import com.netease.nim.uikit.business.recent.adapter.RecentContactAdapter;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.topsports.imtest.R;
import com.topsports.imtest.base.BaseActivity;
import com.topsports.imtest.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Date 2018/8/30
 * Time 16:05
 *
 * @author wentong.chen
 */
public class ConversationFragment extends BaseFragment {
    private RecentContactsFragment fragment;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private RecentContactAdapter mAdapter;


    public static ConversationFragment newInstance() {
        ConversationFragment fragment = new ConversationFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_conversation;
    }

    @Override
    protected void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RecentContactAdapter(mRecyclerView, null);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        NIMClient.getService(MsgService.class).queryRecentContacts()
                .setCallback(new RequestCallbackWrapper<List<RecentContact>>() {
                    @Override
                    public void onResult(int code, List<RecentContact> recents, Throwable e) {
                        // recents参数即为最近联系人列表（最近会话列表）
                        if (code == ResponseCode.RES_SUCCESS) {
                            mAdapter.setNewData(recents);
                        } else {
                            ToastUtil.showLongToast("没有最近回话消息");
                        }
                    }
                });
    }

    @Override
    protected boolean isUseLazyLoad() {
        return true;
    }
}

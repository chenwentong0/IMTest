package com.topsports.imtest.ui.session;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common.utils.ToastUtil;
import com.netease.nim.uikit.api.model.contact.ContactsCustomization;
import com.netease.nim.uikit.business.contact.ContactsFragment;
import com.netease.nim.uikit.business.contact.core.item.AbsContactItem;
import com.netease.nim.uikit.business.contact.core.item.ItemTypes;
import com.netease.nim.uikit.business.contact.core.model.ContactDataAdapter;
import com.netease.nim.uikit.business.contact.core.viewholder.AbsContactViewHolder;
import com.topsports.imtest.R;
import com.topsports.imtest.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Date 2018/8/31
 * Time 13:35
 *
 * @author wentong.chen
 */
public class SessionFragment extends BaseFragment {

    public static SessionFragment newInstance() {
        SessionFragment fragment = new SessionFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_session;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ContactsFragment contactsFragment = new ContactsFragment();
        contactsFragment.setContactsCustomization(new ContactsCustomization() {
            @Override
            public Class<? extends AbsContactViewHolder<? extends AbsContactItem>> onGetFuncViewHolderClass() {
                return FunItem.FunItemViewHolder.class;
            }

            @Override
            public List<AbsContactItem> onGetFuncItems() {
                return FunItem.provider();
            }

            @Override
            public void onFuncItemClick(AbsContactItem item) {
//                FunItem.handleClick(getContext(), item);
            }
        });
        ft.add(R.id.ll_root, contactsFragment)
                .show(contactsFragment)
                .commitNowAllowingStateLoss();
    }

    public static class FunItem extends AbsContactItem {
        static final FunItem VERIFY = new FunItem();
        static final FunItem ROBOT = new FunItem();
        static final FunItem NORMAL_TEAM = new FunItem();
        static final FunItem ADVANCED_TEAM = new FunItem();
        static final FunItem BLACK_LIST = new FunItem();
        static final FunItem MY_COMPUTER = new FunItem();

        @Override
        public int getItemType() {
            return ItemTypes.FUNC;
        }

        @Override
        public String belongsGroup() {
            return null;
        }

        public static class FunItemViewHolder extends AbsContactViewHolder<FunItem> {
            private ImageView image;
            private TextView funcName;
            private TextView unreadNum;
            @Override
            public void refresh(ContactDataAdapter adapter, int position, FunItem item) {
                if (item == VERIFY) {
                    funcName.setText("验证提醒");
                    image.setImageResource(R.drawable.icon_verify_remind);
                    image.setScaleType(ImageView.ScaleType.FIT_XY);
                    //todo 系统未读消息设置
//                    int unreadCount = SystemMessageUnreadManager.getInstance().getSysMsgUnreadCount();
//                    updateUnreadNum(unreadCount);
//
//                    ReminderManager.getInstance().registerUnreadNumChangedCallback(new ReminderManager.UnreadNumChangedCallback() {
//                        @Override
//                        public void onUnreadNumChanged(ReminderItem item) {
//                            if (item.getId() != ReminderId.CONTACT) {
//                                return;
//                            }
//
//                            updateUnreadNum(item.getUnread());
//                        }
//                    });
                } else if (item == ROBOT) {
                    funcName.setText("智能机器人");
                    image.setImageResource(R.drawable.ic_robot);
                } else if (item == NORMAL_TEAM) {
                    funcName.setText("讨论组");
                    image.setImageResource(R.drawable.ic_secretary);
                } else if (item == ADVANCED_TEAM) {
                    funcName.setText("高级群");
                    image.setImageResource(R.drawable.ic_advanced_team);
                } else if (item == BLACK_LIST) {
                    funcName.setText("黑名单");
                    image.setImageResource(R.drawable.ic_black_list);
                } else if (item == MY_COMPUTER) {
                    funcName.setText("我的电脑");
                    image.setImageResource(R.drawable.ic_my_computer);
                }

                if (item != VERIFY) {
                    image.setScaleType(ImageView.ScaleType.FIT_XY);
                    unreadNum.setVisibility(View.GONE);
                }
            }

            @Override
            public View inflate(LayoutInflater inflater) {
                View view = inflater.inflate(R.layout.func_contacts_item, null);
                this.image = (ImageView) view.findViewById(R.id.img_head);
                this.funcName = (TextView) view.findViewById(R.id.tv_func_name);
                this.unreadNum = (TextView) view.findViewById(R.id.tab_new_msg_label);
                return view;
            }
        }

        public static List<AbsContactItem> provider() {
            List<AbsContactItem> items = new ArrayList<AbsContactItem>();
            items.add(VERIFY);
            items.add(ROBOT);
            items.add(NORMAL_TEAM);
            items.add(ADVANCED_TEAM);
            items.add(BLACK_LIST);
            items.add(MY_COMPUTER);

            return items;
        }

        static void handleClick(Context context, AbsContactItem item) {
            if (item == VERIFY) {
                ToastUtil.showLongToast("系统信息");
//                SystemMessageActivity.start(context);
            } else if (item == ROBOT) {
                ToastUtil.showLongToast("机器人");
//                RobotListActivity.start(context);
            } else if (item == NORMAL_TEAM) {
                ToastUtil.showLongToast("讨论组");
//                TeamListActivity.start(context, ItemTypes.TEAMS.NORMAL_TEAM);
            } else if (item == ADVANCED_TEAM) {
                ToastUtil.showLongToast("群名单");
//                TeamListActivity.start(context, ItemTypes.TEAMS.ADVANCED_TEAM);
            } else if (item == MY_COMPUTER) {
                ToastUtil.showLongToast("我的电脑");
//                SessionHelper.startP2PSession(context, DemoCache.getAccount());
            } else if (item == BLACK_LIST) {
                ToastUtil.showLongToast("黑名单列表");
//                BlackListActivity.start(context);
            }
        }
    }

}

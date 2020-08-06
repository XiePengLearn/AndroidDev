package com.xiaoanjujia.home.composition.main;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.xiaoanjujia.common.base.BaseActivity;
import com.xiaoanjujia.common.util.AppManager;
import com.xiaoanjujia.common.util.LogUtil;
import com.xiaoanjujia.common.util.statusbar.StatusBarUtil;
import com.xiaoanjujia.common.widget.bottomnavigation.BadgeItem;
import com.xiaoanjujia.common.widget.bottomnavigation.BottomNavigationBar;
import com.xiaoanjujia.common.widget.bottomnavigation.BottomNavigationItem;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.composition.html.HtmlMeFragment;
import com.xiaoanjujia.home.composition.html.HtmlStoreFragment;
import com.xiaoanjujia.home.composition.main.community.CommunityFragment;
import com.xiaoanjujia.home.composition.main.mine.MineFragment;
import com.xiaoanjujia.home.composition.main.store.StoreFragment;
import com.xiaoanjujia.home.composition.main.tenement.TenementFragment;
import com.xiaoanjujia.home.composition.main.unlocking.UnlockingFragment;
import com.xiaoanjujia.home.entities.LoginResponse;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/main/MainActivity")
public class MainActivity extends BaseActivity implements MainContract.View, BottomNavigationBar.OnTabSelectedListener {

    @Inject
    MainPresenter presenter;
    @BindView(R2.id.bottom_navigation_bar1)
    BottomNavigationBar bottomNavigationBar;
    @BindView(R2.id.main_container)
    FrameLayout mainContainer;
    private UnlockingFragment mUnlockingFragment;
    private TenementFragment mTenementFragment;
    private FragmentManager mFragmentManager;
    private CommunityFragment mCommunityFragment;
    private HtmlStoreFragment mStoreFragment;
    private HtmlMeFragment mMyFragment;
    @SuppressLint("StaticFieldLeak")
    public static MainActivity instance;//关闭当前页面的instance
    private final String MESSAGE_ACTION = "com.jkx.message"; // 消息通知的广播名称


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);
        mFragmentManager = getSupportFragmentManager();
        initView();
        initData();
        //在oncreate中添加
        instance = this;
        registerMessageBroadcast();

        //        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        //        int heapSize = manager.getMemoryClass();
        //        int maxHeapSize = manager.getLargeMemoryClass();  // manafest.xml   android:largeHeap="true"
        //        LogUtil.e(TAG, "--------heapSize--------:" + heapSize + ";--------maxHeapSize--------:" + maxHeapSize);
    }

    /**
     * 注册消息广播
     */
    private void registerMessageBroadcast() {
        IntentFilter filter = new IntentFilter(MESSAGE_ACTION);
        registerReceiver(mSystemMessageReceiver, filter);// 注册广播
    }

    private BroadcastReceiver mSystemMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (MESSAGE_ACTION.equals(action)) {
                initMoreState();
            }
        }

    };

    private void initMoreState() {

        LogUtil.e(TAG, "-----MainActivity收到信鸽的服务推送消息-----");
        //初始化首页数据
        initLogin();
    }

    private void initLogin() {
        //        String lAccount = PrefUtils.readUserNameDefault(this.getApplicationContext());
        //        String lPassword = PrefUtils.readPasswordDefault(this.getApplicationContext());
        //
        //        String mXinGeToken = PrefUtils.readXinGeToken(this.getApplicationContext());
        //        Map<String, Object> mapParameters = new HashMap<>(6);
        //        mapParameters.put("MOBILE", lAccount);
        //        mapParameters.put("PASSWORD", lPassword);
        //        mapParameters.put("SIGNIN_TYPE", "1");
        //        mapParameters.put("USER_TYPE", "1");
        //        mapParameters.put("MOBILE_TYPE", "1");
        //        mapParameters.put("XINGE_TOKEN", mXinGeToken);
        //        LogUtil.e(TAG, "-------mXinGeToken-------" + mXinGeToken);
        //
        //        Map<String, String> mapHeaders = new HashMap<>(1);
        //        mapHeaders.put("ACTION", "S002");
        //        //        mapHeaders.put("SESSION_ID", TaskManager.SESSION_ID);
        //
        //        presenter.getLoginData(mapHeaders, mapParameters);
    }


    public void initView() {

        mUnlockingFragment = (UnlockingFragment) mFragmentManager.findFragmentByTag("unlocking_fg");
        mTenementFragment = (TenementFragment) mFragmentManager.findFragmentByTag("tenement_fg");
        mCommunityFragment = (CommunityFragment) mFragmentManager.findFragmentByTag("community_fg");
        mStoreFragment = (HtmlStoreFragment) mFragmentManager.findFragmentByTag("store_fg");
        mMyFragment = (HtmlMeFragment) mFragmentManager.findFragmentByTag("my_fg");


        if (mUnlockingFragment == null) {
            mUnlockingFragment = UnlockingFragment.newInstance();
            addFragment(R.id.main_container, mUnlockingFragment, "unlocking_fg");
        }

        mFragmentManager.beginTransaction().show(mUnlockingFragment)
                .commitAllowingStateLoss();
        hideTenementFragment();
        hideCommunityFragment();
        hideStoreFragment();
        hideMyFragment();

        DaggerMainActivityComponent.builder()
                .appComponent(getAppComponent())
                .mainPresenterModule(new MainPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);


        initBottomNavigation();


    }

    @Override
    public void onTabSelected(int position) {
        if (position == 0) {
            if (mUnlockingFragment == null) {
                mUnlockingFragment = UnlockingFragment.newInstance();
                addFragment(R.id.main_container, mUnlockingFragment, "unlocking_fg");
            }

            hideTenementFragment();
            hideCommunityFragment();
            hideStoreFragment();
            hideMyFragment();

            mFragmentManager.beginTransaction()

                    .show(mUnlockingFragment)
                    .commitAllowingStateLoss();


        } else if (position == 1) {
            if (mTenementFragment == null) {
                mTenementFragment = TenementFragment.newInstance();
                addFragment(R.id.main_container, mTenementFragment, "tenement_fg");
            }

            hideHomeFragment();

            hideCommunityFragment();
            hideStoreFragment();
            hideMyFragment();


            mFragmentManager.beginTransaction()
                    .show(mTenementFragment)
                    .commitAllowingStateLoss();


        } else if (position == 2) {
            if (mCommunityFragment == null) {
                mCommunityFragment = CommunityFragment.newInstance();
                addFragment(R.id.main_container, mCommunityFragment, "community_fg");
            }


            hideHomeFragment();
            hideTenementFragment();
            hideStoreFragment();
            hideMyFragment();


            mFragmentManager.beginTransaction()
                    .show(mCommunityFragment)
                    .commitAllowingStateLoss();


        } else if (position == 3) {

            if (mStoreFragment == null) {
                mStoreFragment = HtmlStoreFragment.newInstance();
                addFragment(R.id.main_container, mStoreFragment, "store_fg");
            }

            hideHomeFragment();
            hideTenementFragment();
            hideCommunityFragment();
            hideMyFragment();
            mFragmentManager.beginTransaction()
                    .show(mStoreFragment)
                    .commitAllowingStateLoss();


        } else if (position == 4) {

            if (mMyFragment == null) {
                mMyFragment = HtmlMeFragment.newInstance();
                addFragment(R.id.main_container, mMyFragment, "my_fg");
            }

            hideHomeFragment();
            hideTenementFragment();
            hideCommunityFragment();
            hideStoreFragment();
            mFragmentManager.beginTransaction()
                    .show(mMyFragment)
                    .commitAllowingStateLoss();


        }
    }

    private void hideHomeFragment() {
        if (mUnlockingFragment != null) {
            mFragmentManager.beginTransaction().hide(mUnlockingFragment).commitAllowingStateLoss();
        }
    }

    private void hideMyFragment() {
        if (mMyFragment != null) {
            mFragmentManager.beginTransaction().hide(mMyFragment).commitAllowingStateLoss();
        }
    }

    private void hideCommunityFragment() {
        if (mCommunityFragment != null) {
            mFragmentManager.beginTransaction().hide(mCommunityFragment).commitAllowingStateLoss();
        }
    }

    private void hideStoreFragment() {
        if (mStoreFragment != null) {
            mFragmentManager.beginTransaction().hide(mStoreFragment).commitAllowingStateLoss();
        }
    }

    private void hideTenementFragment() {
        if (mTenementFragment != null) {
            mFragmentManager.beginTransaction().hide(mTenementFragment).commitAllowingStateLoss();
        }
    }

    private void initBottomNavigation() {
        BadgeItem numberBadgeItem = new BadgeItem()
                .setBorderWidth(4)
                .setBackgroundColorResource(R.color.colorAccent)
                .setText("99+")
                .setHideOnSelect(false);

        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        //bottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        //bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        //bottomNavigationBar.setAutoHideEnabled(true);


        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.axh, "").setInactiveIconResource(R.drawable.axg).setActiveColorResource(R.color.colorAccent))
                .addItem(new BottomNavigationItem(R.drawable.axd, "").setInactiveIconResource(R.drawable.axc).setActiveColorResource(R.color.colorAccent))
                .addItem(new BottomNavigationItem(R.drawable.axf, "").setInactiveIconResource(R.drawable.axe).setActiveColorResource(R.color.colorAccent))
                .addItem(new BottomNavigationItem(R.drawable.axb, "").setInactiveIconResource(R.drawable.axa).setActiveColorResource(R.color.colorAccent).setBadgeItem(numberBadgeItem))
                .addItem(new BottomNavigationItem(R.drawable.axj, "").setInactiveIconResource(R.drawable.axi).setActiveColorResource(R.color.colorAccent))
                .setFirstSelectedPosition(0)
                .initialise();

        bottomNavigationBar.setTabSelectedListener(this);
    }

    private static final String TAG = "MainActivity";


    public void initData() {
        //        presenter.getText();
    }

    private String text;
    private String loginData;


    @Override
    public void setLoginData(LoginResponse loginResponse) {


    }

    @Override
    public void showProgressDialogView() {
        showProgressDialog();
    }

    @Override
    public void hiddenProgressDialogView() {
        hiddenProgressDialog();
    }


    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.destory();
        }

        if (mSystemMessageReceiver != null) {
            unregisterReceiver(mSystemMessageReceiver);
        }

        instance = null;

    }

    private long timeMillis;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - timeMillis) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                timeMillis = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}

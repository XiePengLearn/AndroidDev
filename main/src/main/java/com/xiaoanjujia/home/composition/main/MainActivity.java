package com.xiaoanjujia.home.composition.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.xiaoanjujia.common.BaseApplication;
import com.xiaoanjujia.common.base.BaseActivity;
import com.xiaoanjujia.common.util.LogUtil;
import com.xiaoanjujia.common.util.PrefUtils;
import com.xiaoanjujia.common.util.statusbar.StatusBarUtil;
import com.xiaoanjujia.common.widget.bottomnavigation.BadgeItem;
import com.xiaoanjujia.common.widget.bottomnavigation.BottomNavigationBar;
import com.xiaoanjujia.common.widget.bottomnavigation.BottomNavigationItem;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.composition.html.HtmlStoreFragment;
import com.xiaoanjujia.home.composition.html.me_html.MeWebFragment;
import com.xiaoanjujia.home.composition.main.community.CommunityFragment;
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
    private MeWebFragment mMyFragment;
    @SuppressLint("StaticFieldLeak")
    public static MainActivity instance;//关闭当前页面的instance
    private final String MESSAGE_ACTION = "com.jkx.message"; // 消息通知的广播名称`
    private int mRoleType;
    // 要申请的权限
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE};
    private AlertDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);
        mFragmentManager = getSupportFragmentManager();
        //在oncreate中添加
        instance = this;
        registerMessageBroadcast();
        mRoleType = PrefUtils.readRoleType(BaseApplication.getInstance());
        //roletype:---0是普通用户---1是物业主管----2是物业人员
        initView();
        initData();
        //         版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // 检查该权限是否已经获取
            int i = ContextCompat.checkSelfPermission(this, permissions[0]);
            int i1 = ContextCompat.checkSelfPermission(this, permissions[1]);
            int i2 = ContextCompat.checkSelfPermission(this, permissions[2]);
            int i3 = ContextCompat.checkSelfPermission(this, permissions[3]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (i != PackageManager.PERMISSION_GRANTED || i1 != PackageManager.PERMISSION_GRANTED
                    || i2 != PackageManager.PERMISSION_GRANTED || i3 != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                showDialogTipUserRequestPermission();
            }
        }
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
        mMyFragment = (MeWebFragment) mFragmentManager.findFragmentByTag("my_fg");


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
        if (mRoleType == 0) {
            //普通用户
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


            } else if (position == 2) {

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


            } else if (position == 3) {

                if (mMyFragment == null) {
                    mMyFragment = MeWebFragment.newInstance();
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
        } else {
            //物业人员
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
                    mMyFragment = MeWebFragment.newInstance();
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
                .setBackgroundColorResource(R.color.rh_color2)
                .setText("99+")
                .setHideOnSelect(false);

        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        //bottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        //bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        //bottomNavigationBar.setAutoHideEnabled(true);

        if (mRoleType == 0) {
            //普通用户
            bottomNavigationBar
                    .addItem(new BottomNavigationItem(R.drawable.axh, "").setInactiveIconResource(R.drawable.axg).setActiveColorResource(R.color.colorAccent))
                    //                    .addItem(new BottomNavigationItem(R.drawable.axd, "").setInactiveIconResource(R.drawable.axc).setActiveColorResource(R.color.colorAccent))
                    .addItem(new BottomNavigationItem(R.drawable.axf, "").setInactiveIconResource(R.drawable.axe).setActiveColorResource(R.color.colorAccent))
                    .addItem(new BottomNavigationItem(R.drawable.axb, "").setInactiveIconResource(R.drawable.axa).setActiveColorResource(R.color.colorAccent))
                    .addItem(new BottomNavigationItem(R.drawable.axj, "").setInactiveIconResource(R.drawable.axi).setActiveColorResource(R.color.colorAccent))
                    .setFirstSelectedPosition(0)
                    .initialise();
        } else {
            //物业人员
            bottomNavigationBar
                    .addItem(new BottomNavigationItem(R.drawable.axh, "").setInactiveIconResource(R.drawable.axg).setActiveColorResource(R.color.colorAccent))
                    .addItem(new BottomNavigationItem(R.drawable.axd, "").setInactiveIconResource(R.drawable.axc).setActiveColorResource(R.color.colorAccent))
                    .addItem(new BottomNavigationItem(R.drawable.axf, "").setInactiveIconResource(R.drawable.axe).setActiveColorResource(R.color.colorAccent))
                    .addItem(new BottomNavigationItem(R.drawable.axb, "").setInactiveIconResource(R.drawable.axa).setActiveColorResource(R.color.colorAccent))
                    .addItem(new BottomNavigationItem(R.drawable.axj, "").setInactiveIconResource(R.drawable.axi).setActiveColorResource(R.color.colorAccent))
                    .setFirstSelectedPosition(0)
                    .initialise();
        }


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

    // 提示用户该请求权限的弹出框
    private void showDialogTipUserRequestPermission() {

        AlertDialog mAlertDialog = new AlertDialog.Builder(this).create();

        mAlertDialog.setTitle("权限不可用");
        mAlertDialog.setMessage("由于小安居家需要获取定位/存储空间/相机权限，为你定位与存储个人信息；\n否则，您将无法正常使用小安居家");
        mAlertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "立即开启", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startRequestPermission();
            }
        });

        mAlertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        mAlertDialog.setCancelable(false);
        mAlertDialog.show();
        mAlertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.color_888888));
        mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.color_2AAD67));
    }


    // 开始提交请求权限
    private void startRequestPermission() {
        ActivityCompat.requestPermissions(this, permissions, 321);
    }

    // 用户权限 申请 的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 321) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                    // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                    boolean b = shouldShowRequestPermissionRationale(permissions[0]);
                    if (!b) {
                        // 用户还是想用我的 APP 的
                        // 提示用户去应用设置界面手动开启权限
                        showDialogTipUserGoToAppSettting();
                    }
                } else if (grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                    // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                    boolean b = shouldShowRequestPermissionRationale(permissions[1]);
                    if (!b) {
                        // 用户还是想用我的 APP 的
                        // 提示用户去应用设置界面手动开启权限
                        showDialogTipUserGoToAppSettting();
                    }
                } else if (grantResults[2] != PackageManager.PERMISSION_GRANTED) {
                    // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                    boolean b = shouldShowRequestPermissionRationale(permissions[2]);
                    if (!b) {
                        // 用户还是想用我的 APP 的
                        // 提示用户去应用设置界面手动开启权限
                        showDialogTipUserGoToAppSettting();
                    }
                } else if (grantResults[3] != PackageManager.PERMISSION_GRANTED) {
                    // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                    boolean b = shouldShowRequestPermissionRationale(permissions[3]);
                    if (!b) {
                        // 用户还是想用我的 APP 的
                        // 提示用户去应用设置界面手动开启权限
                        showDialogTipUserGoToAppSettting();
                    }
                }
            }
        }
    }


    // 提示用户去应用设置界面手动开启权限

    private void showDialogTipUserGoToAppSettting() {

        dialog = new AlertDialog.Builder(this)
                .setTitle("权限不可用")
                .setMessage("请在-应用设置-权限-中，允许小安居家使用定位/存储空间/相机权限来保存用户数据")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 跳转到应用设置界面
                        goToAppSetting();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setCancelable(false).show();
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.color_888888));
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.color_2AAD67));
    }

    // 跳转到当前应用的设置界面
    private void goToAppSetting() {
        Intent intent = new Intent();

        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);

        startActivityForResult(intent, 123);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 检查该权限是否已经获取
                int i = ContextCompat.checkSelfPermission(this, permissions[0]);
                int i1 = ContextCompat.checkSelfPermission(this, permissions[1]);
                int i2 = ContextCompat.checkSelfPermission(this, permissions[2]);
                int i3 = ContextCompat.checkSelfPermission(this, permissions[3]);
                // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                if (i == PackageManager.PERMISSION_GRANTED && i1 == PackageManager.PERMISSION_GRANTED &&
                        i2 == PackageManager.PERMISSION_GRANTED && i3 == PackageManager.PERMISSION_GRANTED) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }

                } else {


                    // 提示用户应该去应用设置界面手动开启权限
                    showDialogTipUserGoToAppSettting();
                }


            }
        }
    }


}

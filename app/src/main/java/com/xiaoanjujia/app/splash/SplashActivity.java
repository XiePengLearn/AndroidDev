package com.xiaoanjujia.app.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.app.R;
import com.xiaoanjujia.app.MyApplication;
import com.xiaoanjujia.common.base.BaseActivity;
import com.xiaoanjujia.common.util.PrefUtils;
import com.xiaoanjujia.common.util.statusbar.StatusBarUtil;

/**
 * @author xp
 * @date 2018/12/1
 * @description
 */
public class SplashActivity extends BaseActivity {
    public static final  String PREF_IS_USER_GUIDE_SHOWED = "is_user_guide_showed";//标记新手引导是否已经展示过
    public static final  String PREF_IS_ENTER_LOG_OR_MAIN = "is_enter_login_or_main";//标记进入登录页还是主页
    private static final String TAG                       = "SplashActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        StatusBarUtil.setImmersiveStatusBar(this, true);
        new Handler().postDelayed(() -> {
            // 判断新手引导是否展示过
            boolean showed = PrefUtils.getBoolean(MyApplication.getInstance().getApplicationContext(), PREF_IS_USER_GUIDE_SHOWED, false);

            if (showed) {
                //已经展示过, 进入主页面
                //enterLogin true 进登录界面 false 进 main界面
                boolean enterLogin = PrefUtils.getBoolean(MyApplication.getInstance().getApplicationContext(), PREF_IS_ENTER_LOG_OR_MAIN, true);
                if (enterLogin) {

                    ARouter.getInstance().build("/login/login").greenChannel().navigation(mContext);

                } else {
                    /*
                      小安居家现在是的需求是直接打开应用就是登录页，后期该需求的话，可以把下面打开，是登录之后获取SESSION_ID不为空就跳主页
                     */

                    String SESSION_ID = PrefUtils.readSESSION_ID(SplashActivity.this);
                    if (!TextUtils.isEmpty(SESSION_ID)) {
                        ARouter.getInstance().build("/main/MainActivity").greenChannel().navigation(SplashActivity.this);

                    } else {
                        ARouter.getInstance().build("/login/login").greenChannel().navigation(SplashActivity.this);

                    }

//                    /*
//                      暂时都写跳登录页
//                     */
//                    ARouter.getInstance().build("/login/login").greenChannel().navigation(mContext);

                }
                finish();
            } else {
                // 没展示, 进入新手引导页面
                startActivity(new Intent(mContext, GuideActivity.class));
            }

            finish();
        }, 500);


    }
}

package com.sxjs.testmodule;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 22@author：xp on 2017/4/10 14:50.
 */

public class TestModuleMainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testmodule_test_main_activity);
        ButterKnife.bind(this);

    }


    @OnClick(R.id.to_test_activity)
    public void onClick() {
        ARouter.getInstance().build("/test1/activity").greenChannel().navigation();
    }
}

package com.sxjs.testmodule;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xiaoanjujia.common.base.BaseActivity;
import com.xiaoanjujia.common.base.rxjava.ErrorDisposableObserver;

import butterknife.ButterKnife;
import okhttp3.ResponseBody;

/**
 * 22@author：xp on 2017/4/10 14:50.
 */
@Route(path = "/test1/activity")
public class TestActivity extends BaseActivity /*implements View.OnClickListener, EasyPermissions.PermissionCallbacks*/ {


    private static final int RC_CAMERA_AND_LOCATION = 123;
//    @BindView(R2.id.expand_img)
//    ExpandImageView expandImg;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testmodule_test_activity);
        unbinder = ButterKnife.bind(this);
//        ImageLoaderUtil.getInstance(expandImg)
//                .setOverlayImage(getResources().getDrawable(R.drawable.over))
//                .setRoundingParams(10);
    }


//    @AfterPermissionGranted(RC_CAMERA_AND_LOCATION)
//    public void testPermissionRequest() {
//        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION};
//        if (EasyPermissions.hasPermissions(this, perms)) {
//            // Already have permission, do the thing
//            // ...
//            showShortToast("已经获取权限了");
//        } else {
//            // Do not have permissions, request them now
//            EasyPermissions.requestPermissions(this, "权限请求",
//                    RC_CAMERA_AND_LOCATION, perms);
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
//    }
//
//    @Override
//    public void onPermissionsGranted(int requestCode, List<String> perms) {
//        showShortToast("我同意了你的权限" + perms.toString());
//
//    }
//
//    @Override
//    public void onPermissionsDenied(int requestCode, List<String> perms) {
//        showShortToast("我拒绝了你的权限" + perms.toString());
//    }
//
//    /**
//     * 运行时id会和编译时id发生变化 所以运行时判断必须用R.id
//     *
//     * @param view
//     */
//    @OnClick({R2.id.show_toast, R2.id.clear_cache})
//    public void onClick(View view) {
//        if (view.getId() == R.id.show_toast) {
//            testPermissionRequest();
//        } else if (view.getId() == R.id.clear_cache) {
//            Fresco.getImagePipeline().clearDiskCaches();
//            showShortToast("缓存已清理");
//        }
//    }

    /**
     * 演示各module访问网络示例
     */
    private void getTestData(){
        addDisposable(TestDataManager.getInstance(mDataManager).testData(new ErrorDisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {

            }

            @Override
            public void onComplete() {

            }
        },"xxx","xxx"));
    }
}

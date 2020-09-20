package com.xiaoanjujia.home.composition.unlocking.qr_code;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.request.RequestOptions;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.xiaoanjujia.common.base.BaseActivity;
import com.xiaoanjujia.common.util.ResponseCode;
import com.xiaoanjujia.common.util.ToastUtil;
import com.xiaoanjujia.common.util.statusbar.StatusBarUtil;
import com.xiaoanjujia.common.widget.bottomnavigation.utils.Utils;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.entities.QrCodeResponse;
import com.xiaoanjujia.home.tool.Api;
import com.yzq.zxinglibrary.encode.CodeCreator;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author xiepeng
 */
@Route(path = "/visitorActivity/visitorActivity")
public class VisitorActivity extends BaseActivity implements VisitorContract.View {
    @Inject
    VisitorPresenter mPresenter;
    private static final String TAG = "PermitActivity";
    @BindView(R2.id.fake_status_bar)
    View fakeStatusBar;
    @BindView(R2.id.main_title_back)
    ImageView mainTitleBack;
    @BindView(R2.id.main_title_text)
    TextView mainTitleText;
    @BindView(R2.id.main_title_right)
    ImageView mainTitleRight;
    @BindView(R2.id.main_title_container)
    LinearLayout mainTitleContainer;
    @BindView(R2.id.qr_house_title_tv)
    TextView qrHouseTitleTv;
    @BindView(R2.id.qr_house_code_iv)
    ImageView qrHouseCodeIv;
    @BindView(R2.id.ll_knowledge_publish_root)
    LinearLayout llKnowledgePublishRoot;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor);
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);
        Intent intent = getIntent();
        String houseName = intent.getStringExtra("houseName");
        if (!Utils.isNull(houseName)) {
            qrHouseTitleTv.setText(houseName);
        } else {
            qrHouseTitleTv.setText("");
        }
        initView();
        initData();
        initTitle();
    }

    /**
     * 初始化title
     */
    private void initTitle() {
        mainTitleBack.setVisibility(View.INVISIBLE);
        mainTitleText.setText("");
        mainTitleRight.setImageDrawable(getResources().getDrawable(R.drawable.close_white));
    }

    private void initView() {
        DaggerVisitorActivityComponent.builder()
                .appComponent(getAppComponent())
                .visitorPresenterModule(new VisitorPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);

    }

    private void initData() {
        Map<String, Object> mapParameters = new HashMap<>(1);
        mapParameters.put("personId", "65277007e605477fb80eaa25dd91e4b8");

        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getRequestData(headersTreeMap, mapParameters);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void setResponseData(QrCodeResponse mQrCodeResponse) {
        try {
            String code = mQrCodeResponse.getStatus();
            String msg = mQrCodeResponse.getMessage();
            if (code.equals(ResponseCode.SUCCESS_OK_STRING)) {
                QrCodeResponse.DataBean data = mQrCodeResponse.getData();
                String barCode = data.getBarCode();
                RequestOptions options = new RequestOptions()
                        .error(R.drawable.default_icon);
                //头像

                //                Bitmap logo = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                Bitmap bitmap = CodeCreator.createQRCode(barCode, 400, 400, null);
                qrHouseCodeIv.setImageBitmap(bitmap);
                //                Glide.with(mContext)
                //                        .load(barCode)
                //                        .apply(options)
                //                        .into(qrHouseCodeIv);
                //                // 生成中间带log二维码
                //                Glide.with(PermitActivity.this)
                //                        .asBitmap()
                //                        .load(barCode)
                //                        .into(new CustomTarget<Bitmap>() {
                //                            @Override
                //                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                //                                //生成普通的二维码
                //                                Bitmap image = CodeUtils.createImage("请输入文本", 400, 400, resource);
                //                                imageview.setImageBitmap(image);
                //                            }
                //
                //                            @Override
                //                            public void onLoadCleared(@Nullable Drawable placeholder) {
                //
                //                            }
                //                        });

            } else if (code.equals(ResponseCode.SEESION_ERROR_STRING)) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(this);
                finish();
            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(this.getApplicationContext(), "获取二维码数据失败");
                }

            }
        } catch (Exception e) {
            ToastUtil.showToast(this.getApplicationContext(), "解析数据失败");
        }

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
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.destory();
        }
    }

    @OnClick({R2.id.main_title_back, R2.id.main_title_right})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.main_title_back) {
            finish();
        } else if (id == R.id.main_title_right) {
            finish();
        }
    }


}

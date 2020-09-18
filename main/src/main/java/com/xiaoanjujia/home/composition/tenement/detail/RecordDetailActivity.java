package com.xiaoanjujia.home.composition.tenement.detail;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.rmondjone.camera.CameraActivity;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.xiaoanjujia.common.base.BaseActivity;
import com.xiaoanjujia.common.util.LogUtil;
import com.xiaoanjujia.common.util.ResponseCode;
import com.xiaoanjujia.common.util.ToastUtil;
import com.xiaoanjujia.common.util.statusbar.StatusBarUtil;
import com.xiaoanjujia.common.widget.alphaview.AlphaButton;
import com.xiaoanjujia.common.widget.bottomnavigation.utils.Utils;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.composition.me.merchants.GlideEngine;
import com.xiaoanjujia.home.entities.LogDetailsResponse;
import com.xiaoanjujia.home.entities.LogExamineResponse;
import com.xiaoanjujia.home.entities.LogRefuseResponse;
import com.xiaoanjujia.home.tool.Api;
import com.xiaoanjujia.home.tool.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author xiepeng
 */
@Route(path = "/recordDetailActivity/recordDetailActivity")
public class RecordDetailActivity extends BaseActivity implements RecordDetailContract.View {
    @Inject
    RecordDetailPresenter mPresenter;
    private static final String TAG = "CompositionDetailActivity";

    public static final String KEY_IMAGE_PATH = "imagePath";
    @BindView(R2.id.fake_status_bar)
    View fakeStatusBar;
    @BindView(R2.id.main_title_back)
    ImageView mainTitleBack;
    @BindView(R2.id.main_title_text)
    TextView mainTitleText;
    @BindView(R2.id.main_title_right)
    TextView mainTitleRight;
    @BindView(R2.id.main_title_container)
    RelativeLayout mainTitleContainer;
    @BindView(R2.id.invitation_particulars_of_matter_ll)
    LinearLayout invitationParticularsOfMatterLl;
    @BindView(R2.id.record_detail_rv)
    RecyclerView recordDetailRv;
    @BindView(R2.id.staff_take_picture_layout_list_ll)
    LinearLayout staffTakePictureLayoutListLl;
    @BindView(R2.id.record_detail_text_tv)
    TextView recordDetailTextTv;
    @BindView(R2.id.item_record_detail_time_date_tv)
    TextView itemRecordDetailTimeDateTv;
    @BindView(R2.id.item_record_detail_week_date_tv)
    TextView itemRecordDetailWeekDateTv;
    @BindView(R2.id.item_record_detail_publisher_tv)
    TextView itemRecordDetailPublisherTv;
    @BindView(R2.id.item_record_detail_refuse_text_tv)
    TextView itemRecordDetailRefuseTextTv;
    @BindView(R2.id.item_record_detail_abnormal_text_tv)
    TextView itemRecordDetailAbnormalTextTv;
    @BindView(R2.id.reject_layout_btn)
    AlphaButton rejectLayoutBtn;
    @BindView(R2.id.agree_layout_btn)
    AlphaButton agreeLayoutBtn;
    @BindView(R2.id.edit_reject_layout)
    EditText editRejectLayout;
    @BindView(R2.id.confirm_reject_layout_btn)
    AlphaButton confirmRejectLayoutBtn;
    @BindView(R2.id.reject_layout_ll)
    LinearLayout rejectLayoutLl;

    private List<LocalMedia> selectList = new ArrayList<>();
    private RecordDetailGridImageAdapter mAdapter;
    private int themeId;
    private int mId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);
        themeId = R.style.picture_default_style;
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);

        Intent intent = getIntent();
        mId = intent.getIntExtra("id", 0);
        initView();
        requestData();
        initTitle();
    }

    /**
     * 初始化title
     */
    private void initTitle() {
        mainTitleBack.setVisibility(View.VISIBLE);
        mainTitleText.setText("审核通知");
        mainTitleRight.setText("");
    }

    private void initView() {
        DaggerRecordDetailActivityComponent.builder()
                .appComponent(getAppComponent())
                .recordDetailPresenterModule(new RecordDetailPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);

        RecordDetailGridLayoutManager manager = new RecordDetailGridLayoutManager(RecordDetailActivity.this, 3, GridLayoutManager.VERTICAL, false);
        recordDetailRv.setLayoutManager(manager);
        mAdapter = new RecordDetailGridImageAdapter(RecordDetailActivity.this, onAddPicClickListener);
        mAdapter.setList(selectList);
        //        mAdapter.setSelectMax(5);
        recordDetailRv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new RecordDetailGridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                LogUtil.e(TAG, "长度---->" + selectList.size());
                if (selectList.size() > 0) {
                    PictureSelector.create(RecordDetailActivity.this)
                            .themeStyle(themeId) // xml设置主题
                            //                                .setPictureStyle(mPictureParameterStyle)// 动态自定义相册主题
                            //.setPictureWindowAnimationStyle(animationStyle)// 自定义页面启动动画
                            .isNotPreviewDownload(true)// 预览图片长按是否可以下载
                            .loadImageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                            .openExternalPreview(position, selectList);
                }
            }
        });
    }

    private RecordDetailGridImageAdapter.onAddPicClickListener1 onAddPicClickListener = new RecordDetailGridImageAdapter.onAddPicClickListener1() {
        @Override
        public void onAddPicClick1() {
            CameraActivity.startMe(RecordDetailActivity.this, 2005, CameraActivity.MongolianLayerType.IDCARD_POSITIVE);

        }

    };

    public void refershAddPictureButton() {
        staffTakePictureLayoutListLl.setVisibility(View.GONE);
    }

    private void logExamineData() {
        Map<String, Object> mapParameters = new HashMap<>(1);
        mapParameters.put("id", String.valueOf(mId));
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getLogExamineData(headersTreeMap, mapParameters);
    }

    private void logRefuseData() {
        //        id:日志id
        //        refuse_text:拒绝理由
        String editRejectLayoutText = editRejectLayout.getText().toString().trim();
        Map<String, Object> mapParameters = new HashMap<>(1);
        mapParameters.put("id", String.valueOf(mId));
        mapParameters.put("refuse_text", editRejectLayoutText);
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getLogRefuseData(headersTreeMap, mapParameters);
    }


    private void requestData() {


        Map<String, Object> mapParameters = new HashMap<>(4);
        mapParameters.put("id", String.valueOf(mId));

        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getRequestData(headersTreeMap, mapParameters);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void setResponseData(LogDetailsResponse mLogDetailsResponse) {
        try {
            int code = mLogDetailsResponse.getStatus();
            String msg = mLogDetailsResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                LogDetailsResponse.DataBean data = mLogDetailsResponse.getData();
                if (data != null) {
                    List<String> log_imgs = data.getLog_imgs();
                    if (log_imgs != null) {
                        for (int i = 0; i < log_imgs.size(); i++) {
                            LocalMedia localMedia = new LocalMedia();
                            localMedia.setPath(log_imgs.get(i));
                            localMedia.setAndroidQToPath(log_imgs.get(i));
                            selectList.add(localMedia);
                        }

                        mAdapter.notifyDataSetChanged();
                    }
                    String log_text = data.getLog_text();
                    if (!Utils.isNull(log_text)) {
                        recordDetailTextTv.setText(log_text);
                    }
                    String abnormal_text = data.getAbnormal_text();
                    if (!Utils.isNull(abnormal_text)) {
                        itemRecordDetailAbnormalTextTv.setText(abnormal_text);
                    }
                    String create_time = data.getCreate_time();
                    if (!Utils.isNull(create_time)) {
                        itemRecordDetailTimeDateTv.setText(create_time);
                    }
                    String week = data.getWeek();
                    if (!Utils.isNull(week)) {
                        itemRecordDetailWeekDateTv.setText(week);
                    }
                    String name = data.getName();
                    if (!Utils.isNull(name)) {
                        itemRecordDetailPublisherTv.setText(name);
                    }
                    String refuse_text = data.getRefuse_text();
                    if (!Utils.isNull(refuse_text)) {
                        itemRecordDetailRefuseTextTv.setText(refuse_text);
                    }
                    int examinestatus = data.getExaminestatus();
                    //examinestatus:0是未审核1是通过2被拒绝
                    if (examinestatus == 1) {
                        agreeLayoutBtn.setVisibility(View.INVISIBLE);
                        rejectLayoutBtn.setText("审核通过");
                        rejectLayoutBtn.setBackground(getResources().getDrawable(R.drawable.bg_shap_button_gray));
                        rejectLayoutBtn.setClickable(false);
                    } else if (examinestatus == 2) {
                        agreeLayoutBtn.setVisibility(View.INVISIBLE);
                        rejectLayoutBtn.setText("审核被拒");
                        rejectLayoutBtn.setBackground(getResources().getDrawable(R.drawable.bg_shap_button_gray));
                        rejectLayoutBtn.setClickable(false);
                    } else {
                        //未审核
                    }


                }


            } else if (code == ResponseCode.SEESION_ERROR) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(this);
                finish();
            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(this.getApplicationContext(), msg);
                }

            }
        } catch (Exception e) {
            ToastUtil.showToast(this.getApplicationContext(), "解析数据失败");
        }

    }

    @Override
    public void setLogExamineData(LogExamineResponse mLogExamineResponse) {
        try {
            int code = mLogExamineResponse.getStatus();
            String msg = mLogExamineResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(this.getApplicationContext(), msg);
                }
                ARouter.getInstance().build("/AuditSuccessActivity/AuditSuccessActivity").greenChannel().navigation(mContext);
                finish();
            } else if (code == ResponseCode.SEESION_ERROR) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(this);
                finish();
            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(this.getApplicationContext(), msg);
                }

            }
        } catch (Exception e) {
            ToastUtil.showToast(this.getApplicationContext(), "解析数据失败");
        }

    }

    @Override
    public void setLogRefuseData(LogRefuseResponse mLogRefuseResponse) {
        try {
            int code = mLogRefuseResponse.getStatus();
            String msg = mLogRefuseResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(this.getApplicationContext(), msg);
                }
                finish();
            } else if (code == ResponseCode.SEESION_ERROR) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(this);
                finish();
            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(this.getApplicationContext(), msg);
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


    @OnClick({R2.id.main_title_back, R2.id.reject_layout_btn, R2.id.agree_layout_btn, R2.id.confirm_reject_layout_btn, R2.id.reject_layout_ll})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.main_title_back) {
            finish();
        } else if (id == R.id.reject_layout_btn) {
            rejectLayoutLl.setVisibility(View.VISIBLE);

        } else if (id == R.id.agree_layout_btn) {
            logExamineData();
        } else if (id == R.id.confirm_reject_layout_btn) {
            String editRejectLayoutText = editRejectLayout.getText().toString().trim();

            if (Util.isNull(editRejectLayoutText)) {
                ToastUtil.showToast(mContext.getApplicationContext(), "请输入驳回理由......");
                return;
            }
            logRefuseData();
        } else if (id == R.id.reject_layout_ll) {

        }
    }
}

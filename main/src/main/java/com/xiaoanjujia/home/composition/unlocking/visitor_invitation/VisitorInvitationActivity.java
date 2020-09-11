package com.xiaoanjujia.home.composition.unlocking.visitor_invitation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.xiaoanjujia.common.BaseApplication;
import com.xiaoanjujia.common.base.BaseActivity;
import com.xiaoanjujia.common.util.LogUtil;
import com.xiaoanjujia.common.util.ResponseCode;
import com.xiaoanjujia.common.util.ToastUtil;
import com.xiaoanjujia.common.util.statusbar.StatusBarUtil;
import com.xiaoanjujia.common.widget.SelectPicPopupWindow;
import com.xiaoanjujia.common.widget.alphaview.AlphaButton;
import com.xiaoanjujia.common.widget.bottomnavigation.utils.Utils;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.composition.me.post_message.GlideEngine;
import com.xiaoanjujia.home.composition.unlocking.dialog.ChoiceGenderDialog;
import com.xiaoanjujia.home.composition.unlocking.dialog.ChoiceIdTypeDialog;
import com.xiaoanjujia.home.composition.unlocking.dialog.ChoiceRriginIncidentDialog;
import com.xiaoanjujia.home.composition.unlocking.permit.PermitActivity;
import com.xiaoanjujia.home.entities.UploadImageResponse;
import com.xiaoanjujia.home.entities.VisitorFaceScoreResponse;
import com.xiaoanjujia.home.entities.VisitorInvitationResponse;
import com.xiaoanjujia.home.tool.Api;
import com.xiaoanjujia.home.tool.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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
@Route(path = "/visitorInvitationActivity/visitorInvitationActivity")
public class VisitorInvitationActivity extends BaseActivity implements VisitorInvitationContract.View, PlateNumberDialog.PlateNumberCallBack {
    @Inject
    VisitorInvitationPresenter mPresenter;
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
    @BindView(R2.id.edit_invitation_visiting_name)
    EditText editInvitationVisitingName;
    @BindView(R2.id.edit_invitation_visiting_phone)
    EditText editInvitationVisitingPhone;
    @BindView(R2.id.invitation_visiting_time)
    TextView invitationVisitingTime;
    @BindView(R2.id.invitation_visiting_time_ll)
    LinearLayout invitationVisitingTimeLl;
    @BindView(R2.id.invitation_leave_time)
    TextView invitationLeaveTime;
    @BindView(R2.id.invitation_leave_time_ll)
    LinearLayout invitationLeaveTimeLl;
    @BindView(R2.id.edit_invitation_thing)
    EditText editInvitationThing;
    @BindView(R2.id.uploading_special_certificate_iv)
    ImageView uploadingSpecialCertificateIv;
    @BindView(R2.id.uploading_special_certificate_rv)
    RecyclerView uploadingSpecialCertificateRv;
    @BindView(R2.id.sex_man)
    AlphaButton sexMan;
    @BindView(R2.id.sex_woman)
    AlphaButton sexWoman;
    @BindView(R2.id.invitation_visiting_gender_ll)
    LinearLayout invitationVisitingGenderLl;
    @BindView(R2.id.edit_invitation_visiting_id_number)
    EditText editInvitationVisitingIdNumber;
    @BindView(R2.id.capital_abbreviation_tv)
    TextView capitalAbbreviationTv;
    @BindView(R2.id.capital_abbreviation_ll)
    LinearLayout capitalAbbreviationLl;
    @BindView(R2.id.edit_invitation_visiting_license_number)
    EditText editInvitationVisitingLicenseNumber;
    @BindView(R2.id.ll_knowledge_publish_root)
    LinearLayout llKnowledgePublishRoot;
    @BindView(R2.id.generate_visitor_card)
    AlphaButton generateVisitorCard;

    private ChoiceGenderDialog mChoiceGenderDialog;
    private ChoiceRriginIncidentDialog mChoiceRriginIncidentDialog;
    private ChoiceIdTypeDialog mChoiceIdTypeDialog;
    private TimePickerView mPvTime;
    private TimePickerView mPvTimeLeave;
    private String mLeaveTime;
    private String mVisitorTime;

    private List<LocalMedia> selectList2 = new ArrayList<>();
    private int themeId;
    private String mImagePath;

    private boolean isCameraButton;
    private int chooseMode;
    private int gender = 1;
    private String personId;
    private PlateNumberDialog reStartOtherCountryDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor_invitation);
        themeId = R.style.picture_default_style;
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);
        Intent intent = getIntent();
        personId = intent.getStringExtra("personId");

        initView();
        initTitle();
    }

    /**
     * 初始化title
     */
    private void initTitle() {
        mainTitleBack.setVisibility(View.VISIBLE);
        mainTitleText.setText("访客预约");
        mainTitleRight.setImageDrawable(getResources().getDrawable(R.drawable.visitor_invitation_record));
    }

    private VisitorGridImageAdapter mAdapter2;

    private void initView() {
        DaggerVisitorInvitationActivityComponent.builder()
                .appComponent(getAppComponent())
                .visitorInvitationPresenterModule(new VisitorInvitationPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        initTimePicker();
        initTimePickerLeave();
        LinearLayoutManager manager2 = new LinearLayoutManager(VisitorInvitationActivity.this, GridLayoutManager.HORIZONTAL, false);
        uploadingSpecialCertificateRv.setLayoutManager(manager2);
        mAdapter2 = new VisitorGridImageAdapter(VisitorInvitationActivity.this, onAddPicClickListener2);
        mAdapter2.setList(selectList2);
        //        mAdapter2.setSelectMax(1);
        uploadingSpecialCertificateRv.setAdapter(mAdapter2);
        mAdapter2.setOnItemClickListener(new VisitorGridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                LogUtil.e(TAG, "长度---->" + selectList2.size());
                if (selectList2.size() > 0) {

                    LocalMedia media = selectList2.get(position);
                    String mimeType = media.getMimeType();
                    int mediaType = PictureMimeType.getMimeType(mimeType);
                    switch (mediaType) {
                        case PictureConfig.TYPE_VIDEO:
                            // 预览视频
                            PictureSelector.create(VisitorInvitationActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case PictureConfig.TYPE_AUDIO:
                            // 预览音频
                            PictureSelector.create(VisitorInvitationActivity.this).externalPictureAudio(media.getPath());
                            break;
                        default:
                            // 预览图片 可自定长按保存路径
                            //                        PictureWindowAnimationStyle animationStyle = new PictureWindowAnimationStyle();
                            //                        animationStyle.activityPreviewEnterAnimation = R.anim.picture_anim_up_in;
                            //                        animationStyle.activityPreviewExitAnimation = R.anim.picture_anim_down_out;
                            PictureSelector.create(VisitorInvitationActivity.this)
                                    .themeStyle(themeId) // xml设置主题
                                    //                                .setPictureStyle(mPictureParameterStyle)// 动态自定义相册主题
                                    //.setPictureWindowAnimationStyle(animationStyle)// 自定义页面启动动画
                                    .isNotPreviewDownload(true)// 预览图片长按是否可以下载
                                    .loadImageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                                    .openExternalPreview(position, selectList2);
                            break;
                    }
                }
            }
        });
    }

    private VisitorGridImageAdapter.onAddPicClickListener onAddPicClickListener2 = new VisitorGridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            selectPictureSetting(isCameraButton, selectList2, 1);

        }

    };

    public void refershAddPictureButton2() {
        uploadingSpecialCertificateIv.setVisibility(View.VISIBLE);
        uploadingSpecialCertificateRv.setVisibility(View.GONE);
    }

    private void initData() {
        // receptionistId=aebcf3a7b59b4fb889daaea2b45c2bf5&
        // visitorName=井号2&
        // phoneNo=18635805566&
        // visitStartTime=2020-09-01 15:00:00&
        // visitEndTime=2020-09-01 19:00:00&
        // visitPurpose=参观&
        // gender=1&
        // certificateType=111&
        // nation=1

        String editInvitationVisitingNameText = editInvitationVisitingName.getText().toString().trim();
        String editInvitationVisitingPhoneText = editInvitationVisitingPhone.getText().toString().trim();
        String invitationVisitingTimeText = invitationVisitingTime.getText().toString().trim();
        String invitationLeaveTimeText = invitationLeaveTime.getText().toString().trim();

        String editInvitationThingText = editInvitationThing.getText().toString().trim();
        String editInvitationVisitingIdNumberText = editInvitationVisitingIdNumber.getText().toString().trim();
        String editInvitationVisitingLicenseNumberText = editInvitationVisitingLicenseNumber.getText().toString().trim();
        String capitalAbbreviationTvText = capitalAbbreviationTv.getText().toString().trim();
        Map<String, Object> mapParameters = new HashMap<>(1);
        //        mapParameters.put("receptionistId", "aebcf3a7b59b4fb889daaea2b45c2bf5");
        if (!Utils.isNull(personId)) {
            mapParameters.put("receptionistId", personId);
        } else {
            mapParameters.put("receptionistId", "");
        }

        mapParameters.put("visitorName", editInvitationVisitingNameText);
        mapParameters.put("phoneNo", editInvitationVisitingPhoneText);
        mapParameters.put("visitStartTime", invitationVisitingTimeText);
        mapParameters.put("visitEndTime", invitationLeaveTimeText);
        mapParameters.put("visitPurpose", editInvitationThingText);
        mapParameters.put("gender", gender);

        mapParameters.put("certificateNo", editInvitationVisitingIdNumberText);
        mapParameters.put("plateNo", capitalAbbreviationTvText + editInvitationVisitingLicenseNumberText);

        mapParameters.put("certificateType", "111");
        mapParameters.put("nation", "1");
        mapParameters.put("visitorPhoto", mImagePath);


        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getRequestData(headersTreeMap, mapParameters);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void setResponseData(VisitorInvitationResponse mVisitorInvitationResponse) {
        try {
            int code = Integer.parseInt(mVisitorInvitationResponse.getStatus());
            String msg = mVisitorInvitationResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                VisitorInvitationResponse.DataBean data = mVisitorInvitationResponse.getData();
                List<VisitorInvitationResponse.DataBean.AppointmentInfoListBean> appointmentInfoList = data.getAppointmentInfoList();
                if (appointmentInfoList != null && appointmentInfoList.size() > 0) {
                    //"visitorName": "谢鹏",
                    //			"QRCode": "VjAwMSuegkn65rEzw5qhIkCYQKvUzkGXpH87k1c3TA358ry/tkj393hzBrvHnOwmLHWlIHc=",
                    //			"receptionistName": "老谢",
                    //			"orderId": "413edb4a-f1ea-11ea-8dc9-6770aaf1588b",
                    //			"receptionistId": "77edf8446c7c448e914caf14eeaf9f20",
                    //			"verificationCode": "6490"
                    VisitorInvitationResponse.DataBean.AppointmentInfoListBean appointmentInfoListBean = appointmentInfoList.get(0);
                    if (appointmentInfoListBean != null) {
                        String qrCode = appointmentInfoListBean.getQRCode();
                        String receptionistName = appointmentInfoListBean.getReceptionistName();
                        String orderId = appointmentInfoListBean.getOrderId();
                        Intent intent = new Intent(VisitorInvitationActivity.this, PermitActivity.class);
                        intent.putExtra("qrCode", qrCode);
                        intent.putExtra("receptionistName", receptionistName);
                        intent.putExtra("orderId", orderId);
                        startActivity(intent);

                    }
                }
                ToastUtil.showToast(BaseApplication.getInstance(), "生成访客证成功");

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


    private void initTimePicker() {//Dialog 模式下，在底部弹出
        //时间选择器

        mPvTime = new TimePickerBuilder(VisitorInvitationActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View view) {
                //                Toast.makeText(VisitorInvitationActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
                mVisitorTime = Utils.getTime(date);
                invitationVisitingTime.setText(mVisitorTime);
            }
        })
                .setItemVisibleCount(5)
                .setType(new boolean[]{true, true, true, true, true, true})// 默认全部显示
                .setSubmitColor(getResources().getColor(R.color.color_2AAD67))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.color_2AAD67))//取消按钮文字颜色
                .build();
    }

    private void initTimePickerLeave() {//Dialog 模式下，在底部弹出
        //时间选择器
        mPvTimeLeave = new TimePickerBuilder(VisitorInvitationActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View view) {
                //                Toast.makeText(VisitorInvitationActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
                mLeaveTime = Utils.getTime(date);
                invitationLeaveTime.setText(mLeaveTime);
            }
        })
                .setItemVisibleCount(5)
                .setType(new boolean[]{true, true, true, true, true, true})// 默认全部显示
                .setSubmitColor(getResources().getColor(R.color.color_2AAD67))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.color_2AAD67))//取消按钮文字颜色
                .build();
    }

    //    private void showChoiceGenderDialog(int genderType) {
    //        if (mChoiceGenderDialog == null) {
    //            mChoiceGenderDialog = new ChoiceGenderDialog(this, choiceGenderDialogOnClickListener, new ChoiceGenderDialog.GenderCallback() {
    //                @Override
    //                public void genderSelect(int gender) {
    //                    invitationVisitingGender.setText(gender == 0 ? "男" : "女");
    //                    cancelChoiceGenderDialog();
    //                }
    //
    //                @Override
    //                public void saveGenderSuccess(int gender) {
    //
    //                }
    //            });
    //        }
    //        mChoiceGenderDialog.selectGender(genderType);
    //        mChoiceGenderDialog.show();
    //    }

    public void cancelChoiceGenderDialog() {
        if (mChoiceGenderDialog != null) {
            mChoiceGenderDialog.cancel();
        }
    }

    View.OnClickListener choiceGenderDialogOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    //    private void showChoiceRriginIncidentDialog(int genderType) {
    //        if (mChoiceRriginIncidentDialog == null) {
    //            mChoiceRriginIncidentDialog = new ChoiceRriginIncidentDialog(this, new ChoiceRriginIncidentDialog.IncidentCallback() {
    //                @Override
    //                public void genderSelect(int numIncident) {
    //                    if (numIncident == 0) {
    //                        invitationParticularsOfMatter.setText("其他");
    //                    } else if ((numIncident == 1)) {
    //                        invitationParticularsOfMatter.setText("工作");
    //                    } else if ((numIncident == 2)) {
    //                        invitationParticularsOfMatter.setText("拜访");
    //                    } else if ((numIncident == 3)) {
    //                        invitationParticularsOfMatter.setText("外卖");
    //                    } else {
    //                        invitationParticularsOfMatter.setText("快递");
    //                    }
    //
    //                    cancelChoiceRriginIncidentDialog();
    //                }
    //
    //                @Override
    //                public void saveGenderSuccess(int gender) {
    //
    //                }
    //            });
    //        }
    //        mChoiceRriginIncidentDialog.selectGender(genderType);
    //        mChoiceRriginIncidentDialog.show();
    //    }

    public void cancelChoiceRriginIncidentDialog() {
        if (mChoiceRriginIncidentDialog != null) {
            mChoiceRriginIncidentDialog.cancel();
        }
    }

    //    private void showChoiceIdTypeDialog(int genderType) {
    //        if (mChoiceIdTypeDialog == null) {
    //            mChoiceIdTypeDialog = new ChoiceIdTypeDialog(this, new ChoiceIdTypeDialog.IncidentCallback() {
    //                @Override
    //                public void genderSelect(int numIncident) {
    //                    if (numIncident == 0) {
    //                        invitationVisitingIdType.setText("居民身份证");
    //                    } else if ((numIncident == 1)) {
    //                        invitationVisitingIdType.setText("警官证");
    //                    } else if ((numIncident == 2)) {
    //                        invitationVisitingIdType.setText("军官证");
    //                    } else {
    //                        invitationVisitingIdType.setText("护照");
    //                    }
    //
    //                    cancelChoiceIdTypeDialog();
    //                }
    //
    //                @Override
    //                public void saveGenderSuccess(int gender) {
    //
    //                }
    //            });
    //        }
    //        mChoiceIdTypeDialog.selectGender(genderType);
    //        mChoiceIdTypeDialog.show();
    //    }

    public void cancelChoiceIdTypeDialog() {
        if (mChoiceIdTypeDialog != null) {
            mChoiceIdTypeDialog.cancel();
        }
    }

    private void showReStartOtherCountryDialog() {
        if (reStartOtherCountryDialog == null) {
            reStartOtherCountryDialog = new PlateNumberDialog(this, reStartOtherCountryDialogClickListener);
            reStartOtherCountryDialog.setJsCallback(this);
        }

        reStartOtherCountryDialog.show();
    }

    private View.OnClickListener reStartOtherCountryDialogClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //            if (view.getId() == R.id.dialog_normal_first_btn_tv) {
            //
            //            } else if (view.getId() == R.id.dialog_normal_second_btn_tv) {
            //
            //            }
        }
    };

    @OnClick({R2.id.main_title_back, R2.id.invitation_visiting_time_ll, R2.id.invitation_leave_time_ll,
            R2.id.sex_man, R2.id.sex_woman, R2.id.capital_abbreviation_ll,
            R2.id.uploading_special_certificate_iv, R2.id.generate_visitor_card, R2.id.main_title_right})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.main_title_back) {
            finish();
        } else if (id == R.id.main_title_right) {
            ToastUtil.showToast(BaseApplication.getInstance(), "开发中");
        } else if (id == R.id.invitation_visiting_time_ll) {
            hideKeyboard(view);
            mPvTime.show(view);//弹出时间选择器，传递参数过去，回调的时候则可以绑定此view
        } else if (id == R.id.invitation_leave_time_ll) {
            hideKeyboard(view);
            mPvTimeLeave.show(view);
        } else if (id == R.id.sex_man) {
            hideKeyboard(view);
            gender = 1;
            sexMan.setBackground(getResources().getDrawable(R.drawable.sex_select));
            sexMan.setTextColor(getResources().getColor(R.color.color_2AAD67));

            sexWoman.setBackground(getResources().getDrawable(R.drawable.sex_normal));
            sexWoman.setTextColor(getResources().getColor(R.color.color_494949));
        } else if (id == R.id.sex_woman) {
            hideKeyboard(view);
            gender = 2;

            sexMan.setBackground(getResources().getDrawable(R.drawable.sex_normal));
            sexMan.setTextColor(getResources().getColor(R.color.color_494949));

            sexWoman.setBackground(getResources().getDrawable(R.drawable.sex_select));
            sexWoman.setTextColor(getResources().getColor(R.color.color_2AAD67));
        } else if (id == R.id.capital_abbreviation_ll) {
            showReStartOtherCountryDialog();
        } else if (id == R.id.generate_visitor_card) {
            String editInvitationVisitingNameText = editInvitationVisitingName.getText().toString().trim();
            String editInvitationVisitingPhoneText = editInvitationVisitingPhone.getText().toString().trim();
            String invitationVisitingTimeText = invitationVisitingTime.getText().toString().trim();
            String invitationLeaveTimeText = invitationLeaveTime.getText().toString().trim();

            String editInvitationThingText = editInvitationThing.getText().toString().trim();
            String editInvitationVisitingIdNumberText = editInvitationVisitingIdNumber.getText().toString().trim();
            String editInvitationVisitingLicenseNumberText = editInvitationVisitingLicenseNumber.getText().toString().trim();

            if (Util.isNull(editInvitationVisitingNameText)) {
                ToastUtil.showToast(mContext.getApplicationContext(), "请输入访客姓名");
                return;
            }
            if (Util.isNull(editInvitationVisitingPhoneText)) {
                ToastUtil.showToast(mContext.getApplicationContext(), "请输入手机号码");
                return;
            }


            if (Util.isNull(invitationVisitingTimeText)) {

                ToastUtil.showToast(mContext.getApplicationContext(), "请输入预计来访时间");
                return;
            }
            if (Util.isNull(invitationLeaveTimeText)) {
                ToastUtil.showToast(mContext.getApplicationContext(), "请输入预计离开时间");
                return;
            }


            if (Util.isNull(editInvitationThingText)) {
                ToastUtil.showToast(mContext.getApplicationContext(), "请输入来访事由");
                return;
            }
            if (selectList2.size() == 0) {
                ToastUtil.showToast(mContext.getApplicationContext(), "请选择人脸照片");
                return;
            }
            if (Util.isNull(editInvitationVisitingIdNumberText)) {
                ToastUtil.showToast(mContext.getApplicationContext(), "请输入身份证号");
                return;
            }
            if (Util.isNull(editInvitationVisitingLicenseNumberText)) {
                ToastUtil.showToast(mContext.getApplicationContext(), "请输入车牌号码");
                return;
            }


            uploadPictureToServer(selectList2);
        } else if (id == R.id.uploading_special_certificate_iv) {
            hideKeyboard(view);
            SelectPicPopupWindow selectPicPopupWindow = new SelectPicPopupWindow(mContext, llKnowledgePublishRoot);
            selectPicPopupWindow.setData("拍照/相册", "", null);
            selectPicPopupWindow.setOnSelectItemOnclickListener(new SelectPicPopupWindow.OnSelectItemOnclickListener() {
                @Override
                public void selectItem(String str) {

                    if ("拍照/相册".equals(str)) {
                        photoSelection(true, selectList2);
                    }
                }
            });
            selectPicPopupWindow.showPopWindow();
        }
    }

    //上传公司证件(方法)
    private void uploadPictureToServer(List<LocalMedia> selectList) {
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();
        mPresenter.getUploadPicture(headersTreeMap, selectList);
    }

    @Override
    public void setUploadPicture(UploadImageResponse uploadImageResponse) {
        try {
            int code = uploadImageResponse.getStatus();
            String msg = uploadImageResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                mImagePath = uploadImageResponse.getData().getPath();
                initFaceScoreData(mImagePath);

            } else if (code == ResponseCode.SEESION_ERROR) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(mContext);
                finish();
            } else {
                ToastUtil.showToast(this.getApplicationContext(), msg);
                hiddenProgressDialogView();
            }
        } catch (Exception e) {
            ToastUtil.showToast(this.getApplicationContext(), "解析数据失败");
        }
    }

    //facePicUrl=http://hk.xiaoanjujia.com/image/renlian.jpg
    public void initFaceScoreData(String facePicUrl) {
        Map<String, Object> mapParameters = new HashMap<>(2);
        mapParameters.put("facePicUrl", facePicUrl);
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();
        mPresenter.getFaceScoreData(headersTreeMap, mapParameters);
    }

    @Override
    public void setFaceScoreData(VisitorFaceScoreResponse mVisitorFaceScoreResponsee) {
        try {
            int code = Integer.parseInt(mVisitorFaceScoreResponsee.getStatus());
            String msg = mVisitorFaceScoreResponsee.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {


                VisitorFaceScoreResponse.DataBean data = mVisitorFaceScoreResponsee.getData();
                if (data != null) {
                    int faceScore = data.getFaceScore();
                    if (faceScore > 75) {
                        initData();
                    } else {
                        ToastUtil.showToast(VisitorInvitationActivity.this, "人脸评分低于75分,请重新选择人脸照片!");
                    }
                }

            } else if (code == ResponseCode.SEESION_ERROR) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(VisitorInvitationActivity.this);
            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(VisitorInvitationActivity.this.getApplicationContext(), msg);
                }

            }
        } catch (Exception e) {
            ToastUtil.showToast(VisitorInvitationActivity.this.getApplicationContext(), "解析数据失败");
        }
    }


    public void photoSelection(boolean isFirst, List<LocalMedia> selectList) {
        //*上传特殊材料：
        isCameraButton = true;
        //            mAdapter2.setSelectMax(1);
        // 单独拍照和相册
        chooseMode = PictureMimeType.ofImage();
        selectPictureSetting(isCameraButton, selectList2, 1);
        mAdapter2.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:


                    //---------------------------------------------------------------------------------------------------------
                    // 图片选择结果回调
                    selectList2 = PictureSelector.obtainMultipleResult(data);

                    //选择后有结果隐藏图片添加的按钮,无结果则隐藏
                    //选择后有结果显示uploadingSpecialCertificateRv,无结果则隐藏
                    if (selectList2.size() > 0) {
                        uploadingSpecialCertificateIv.setVisibility(View.GONE);
                        uploadingSpecialCertificateRv.setVisibility(View.VISIBLE);
                    } else {
                        uploadingSpecialCertificateIv.setVisibility(View.VISIBLE);
                        uploadingSpecialCertificateRv.setVisibility(View.GONE);
                    }
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    // 4.media.getAndroidQToPath();为Android Q版本特有返回的字段，此字段有值就用来做上传使用
                    for (LocalMedia media : selectList2) {
                        LogUtil.e(TAG, "压缩---->" + media.getCompressPath());
                        LogUtil.e(TAG, new File(media.getCompressPath()).length() / 1024 + "k");
                        LogUtil.e(TAG, "原图---->" + media.getPath());
                        LogUtil.e(TAG, "裁剪---->" + media.getCutPath());
                        LogUtil.e(TAG, "Android Q 特有Path---->" + media.getAndroidQToPath());
                    }
                    mAdapter2.setList(selectList2);
                    mAdapter2.notifyDataSetChanged();


                    break;
            }
        }
    }

    //    @OnClick({R2.id.main_title_back, R2.id.invitation_particulars_of_matter_ll, R2.id.invitation_visiting_time_ll, R2.id.invitation_leave_time_ll, R2.id.invitation_visiting_gender_ll, R2.id.invitation_visiting_id_type_ll, R2.id.register_success_entry})
    //    public void onViewClicked(View view) {
    //        int id = view.getId();
    //        if (id == R.id.main_title_back) {
    //
    //        } else if (id == R.id.invitation_particulars_of_matter_ll) {
    //            String particularsType = invitationParticularsOfMatter.getText().toString();
    //            if (!Utils.isNull(particularsType) && particularsType.equals("其他")) {
    //                showChoiceRriginIncidentDialog(0);
    //            } else if (!Utils.isNull(particularsType) && particularsType.equals("工作")) {
    //                showChoiceRriginIncidentDialog(1);
    //            } else if (!Utils.isNull(particularsType) && particularsType.equals("拜访")) {
    //                showChoiceRriginIncidentDialog(2);
    //            } else if (!Utils.isNull(particularsType) && particularsType.equals("外卖")) {
    //                showChoiceRriginIncidentDialog(3);
    //            } else if (!Utils.isNull(particularsType) && particularsType.equals("快递")) {
    //                showChoiceRriginIncidentDialog(4);
    //            } else {
    //                showChoiceRriginIncidentDialog(-1);
    //            }
    //
    //        } else if (id == R.id.invitation_visiting_time_ll) {
    //            mPvTime.show(view);//弹出时间选择器，传递参数过去，回调的时候则可以绑定此view
    //        } else if (id == R.id.invitation_leave_time_ll) {
    //            mPvTimeLeave.show(view);
    //        } else if (id == R.id.invitation_visiting_gender_ll) {
    //            String genderType = invitationVisitingGender.getText().toString();
    //            if (!Utils.isNull(genderType)) {
    //                showChoiceGenderDialog(genderType.equals("男") ? 0 : 1);
    //            } else {
    //                showChoiceGenderDialog(-1);
    //            }
    //
    //        } else if (id == R.id.invitation_visiting_id_type_ll) {
    //            String visitingIdType = invitationVisitingIdType.getText().toString();
    //            if (!Utils.isNull(visitingIdType) && visitingIdType.equals("居民身份证")) {
    //                showChoiceIdTypeDialog(0);
    //            } else if (!Utils.isNull(visitingIdType) && visitingIdType.equals("警官证")) {
    //                showChoiceIdTypeDialog(1);
    //            } else if (!Utils.isNull(visitingIdType) && visitingIdType.equals("军官证")) {
    //                showChoiceIdTypeDialog(2);
    //            } else if (!Utils.isNull(visitingIdType) && visitingIdType.equals("护照")) {
    //                showChoiceIdTypeDialog(3);
    //            } else {
    //                showChoiceIdTypeDialog(-1);
    //            }
    //
    //        } else if (id == R.id.register_success_entry) {
    //
    //        }
    //    }
    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void selectPictureSetting(Boolean isCameraButton, List<LocalMedia> selectList, int mMaxSelectNum) {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(VisitorInvitationActivity.this)
                .openGallery(chooseMode)// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .loadImageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style v2.3.3后 建议使用setPictureStyle()动态方式
                //                        .setLanguage(language)// 设置语言，默认中文
                //                        .setPictureStyle(mPictureParameterStyle)// 动态自定义相册主题
                //                        .setPictureCropStyle(mCropParameterStyle)// 动态自定义裁剪主题
                //.setPictureWindowAnimationStyle(new PictureWindowAnimationStyle())// 自定义相册启动退出动画
                .maxSelectNum(mMaxSelectNum)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                //                        .cameraFileName("")// 使用相机时保存至本地的文件名称,注意这个只在拍照时可以使用，选图时不要用
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                .isSingleDirectReturn(false)// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
                .previewImage(true)// 是否可预览图片
                .previewVideo(true)// 是否可预览视频
                .querySpecifiedFormatSuffix(PictureMimeType.ofJPEG())// 查询指定后缀格式资源
                .enablePreviewAudio(true) // 是否可播放音频
                .isCamera(isCameraButton)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .imageFormat(PictureMimeType.JPEG)// 拍照保存图片格式后缀,默认jpeg
                .enableCrop(false)// 是否裁剪
                .compress(true)// 是否压缩
                .compressQuality(80)// 图片压缩后输出质量 0~ 100
                .synOrAsy(true)//同步false或异步true 压缩 默认同步
                .queryMaxFileSize(100)// 只查多少M以内的图片、视频、音频  单位M
                //.compressSavePath(getPath())//压缩图片保存地址
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效 注：已废弃
                //.glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度 注：已废弃
                //                        .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                .isGif(true)// 是否显示gif图片
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(false)// 是否圆形裁剪
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .openClickSound(false)// 是否开启点击声音
                .selectionMedia(selectList)// 是否传入已选图片
                //.isDragFrame(false)// 是否可拖动裁剪框(固定)
                //                        .videoMaxSecond(15)
                //                        .videoMinSecond(10)
                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 注：已废弃 改用cutOutQuality()
                .cutOutQuality(90)// 裁剪输出质量 默认100
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.rotateEnabled(true) // 裁剪是否可旋转图片
                //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                //.videoQuality()// 视频录制质量 0 or 1
                //.videoSecond()//显示多少秒以内的视频or音频也可适用
                //.recordVideoSecond()//录制视频秒数 默认60s
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径  注：已废弃
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @Override
    public void jsPlateNumber(String param) {
        capitalAbbreviationTv.setText(param);
    }
}

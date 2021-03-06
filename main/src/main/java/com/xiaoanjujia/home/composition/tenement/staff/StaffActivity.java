package com.xiaoanjujia.home.composition.tenement.staff;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.rmondjone.camera.CameraActivity;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.xiaoanjujia.common.base.BaseActivity;
import com.xiaoanjujia.common.util.CommonUtil;
import com.xiaoanjujia.common.util.DensityUtils;
import com.xiaoanjujia.common.util.LogUtil;
import com.xiaoanjujia.common.util.NoDoubleClickUtils;
import com.xiaoanjujia.common.util.ResponseCode;
import com.xiaoanjujia.common.util.ToastUtil;
import com.xiaoanjujia.common.util.statusbar.StatusBarUtil;
import com.xiaoanjujia.common.widget.alphaview.AlphaButton;
import com.xiaoanjujia.common.widget.alphaview.AlphaRelativeLayout;
import com.xiaoanjujia.common.widget.bottomnavigation.utils.Utils;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.composition.me.merchants.GlideEngine;
import com.xiaoanjujia.home.entities.AddPropertyLogResponse;
import com.xiaoanjujia.home.entities.TypeOfRoleResponse;
import com.xiaoanjujia.home.entities.UploadImageResponse;
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
@Route(path = "/staffActivity/staffActivity")
public class StaffActivity extends BaseActivity implements StaffContract.View {
    @Inject
    StaffPresenter mPresenter;
    private static final String TAG = "CompositionDetailActivity";
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
    @BindView(R2.id.staff_select_id_name)
    TextView staffSelectIdName;
    @BindView(R2.id.staff_select_id_name_rl)
    AlphaRelativeLayout staffSelectIdNameRl;
    @BindView(R2.id.invitation_particulars_of_matter_ll)
    LinearLayout invitationParticularsOfMatterLl;
    @BindView(R2.id.take_picture_layout_ll)
    LinearLayout takePictureLayoutLl;
    @BindView(R2.id.staff_take_picture_add_iv)
    ImageView staffTakePictureAddIv;
    @BindView(R2.id.staff_uploading_pic_rv)
    RecyclerView staffUploadingPicRv;
    @BindView(R2.id.staff_take_picture_layout_list_ll)
    LinearLayout staffTakePictureLayoutListLl;
    @BindView(R2.id.take_picture_layout_alpha_rl)
    AlphaRelativeLayout takePictureLayoutAlphaRl;
    @BindView(R2.id.edit_work_diary)
    EditText editWorkDiary;
    @BindView(R2.id.edit_abnormal_submitted)
    EditText editAbnormalSubmitted;
    @BindView(R2.id.staff_submit_immediately)
    AlphaButton staffSubmitImmediately;
    public static final String KEY_IMAGE_PATH = "imagePath";
    private ArrayAdapter adapter3;
    private View contentView3;
    private PopupWindow popupWindow3 = null;
    private List<LocalMedia> selectList = new ArrayList<>();
    private StaffGridImageAdapter mAdapter;
    private int themeId;
    private String mStaffImgsPath;
    List<String> listString = new ArrayList<>();
    private List<TypeOfRoleResponse.DataBean.OrdinaryroleBean> ordinaryrole;
    private int staffSelectIdId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);
        themeId = R.style.picture_default_style;
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);
        String[] list3 = new String[]{"客服员", "综合维修工", "门岗保安", "巡逻保安", "消防中控员", "绿化工", "保洁员"};
        adapter3 = new ArrayAdapter<String>(mContext, R.layout.item_textview, listString);
        initView();
        initData();
        initTitle();
    }

    /**
     * 初始化title
     */
    private void initTitle() {
        mainTitleBack.setVisibility(View.VISIBLE);
        mainTitleText.setText("物业管理");
        mainTitleRight.setText("往期查询");
        //        mainTitleRight.setText("");
    }

    private void initView() {
        DaggerStaffActivityComponent.builder()
                .appComponent(getAppComponent())
                .staffPresenterModule(new StaffPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);

        StaffFullyGridLayoutManager manager = new StaffFullyGridLayoutManager(StaffActivity.this, 3, GridLayoutManager.VERTICAL, false);
        staffUploadingPicRv.setLayoutManager(manager);
        mAdapter = new StaffGridImageAdapter(StaffActivity.this, onAddPicClickListener);
        mAdapter.setList(selectList);
        //        mAdapter.setSelectMax(5);
        staffUploadingPicRv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new StaffGridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                LogUtil.e(TAG, "长度---->" + selectList.size());
                if (selectList.size() > 0) {

                    LocalMedia media = selectList.get(position);
                    String mimeType = media.getMimeType();
                    int mediaType = PictureMimeType.getMimeType(mimeType);
                    switch (mediaType) {
                        case PictureConfig.TYPE_VIDEO:
                            // 预览视频
                            PictureSelector.create(StaffActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case PictureConfig.TYPE_AUDIO:
                            // 预览音频
                            PictureSelector.create(StaffActivity.this).externalPictureAudio(media.getPath());
                            break;
                        default:
                            // 预览图片 可自定长按保存路径
                            //                        PictureWindowAnimationStyle animationStyle = new PictureWindowAnimationStyle();
                            //                        animationStyle.activityPreviewEnterAnimation = R.anim.picture_anim_up_in;
                            //                        animationStyle.activityPreviewExitAnimation = R.anim.picture_anim_down_out;
                            PictureSelector.create(StaffActivity.this)
                                    .themeStyle(themeId) // xml设置主题
                                    //                                .setPictureStyle(mPictureParameterStyle)// 动态自定义相册主题
                                    //.setPictureWindowAnimationStyle(animationStyle)// 自定义页面启动动画
                                    .isNotPreviewDownload(true)// 预览图片长按是否可以下载
                                    .loadImageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                                    .openExternalPreview(position, selectList);
                            break;
                    }
                }
            }
        });
    }

    private StaffGridImageAdapter.onAddPicClickListener1 onAddPicClickListener = new StaffGridImageAdapter.onAddPicClickListener1() {
        @Override
        public void onAddPicClick1() {
            CameraActivity.startMe(StaffActivity.this, 2005, CameraActivity.MongolianLayerType.IDCARD_POSITIVE);

        }

    };

    public void refershAddPictureButton() {
        takePictureLayoutLl.setVisibility(View.VISIBLE);
        staffTakePictureLayoutListLl.setVisibility(View.GONE);
    }

    private void initData() {
        // roletype :1是物业主管.2普通物业
        Map<String, Object> mapParameters = new HashMap<>(1);
        mapParameters.put("roletype", "2");
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getTypesOfRoleData(headersTreeMap, mapParameters);
    }

    private void submitData() {
        //        id:角色id
        //        log_imgs:照片
        //        log_text:日志文本
        //        abnormal_text:异常提交
        String staffSelectIdNameText = staffSelectIdName.getText().toString().trim();
        String editWorkDiaryText = editWorkDiary.getText().toString().trim();
        String editAbnormalSubmittedText = editAbnormalSubmitted.getText().toString().trim();

        Map<String, Object> mapParameters = new HashMap<>(4);
        mapParameters.put("id", String.valueOf(staffSelectIdId));
        mapParameters.put("log_imgs", mStaffImgsPath);
        mapParameters.put("log_text", editWorkDiaryText);
        if (!Utils.isNull(editAbnormalSubmittedText)) {
            mapParameters.put("abnormal_text", editAbnormalSubmittedText);
        } else {
            mapParameters.put("abnormal_text", "");
        }
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getRequestData(headersTreeMap, mapParameters);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void setResponseData(AddPropertyLogResponse mAddPropertyLogResponse) {
        try {
            int code = mAddPropertyLogResponse.getStatus();
            String msg = mAddPropertyLogResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(this.getApplicationContext(), msg);
                }
                ARouter.getInstance().build("/PublishSuccessActivity/PublishSuccessActivity").greenChannel().navigation(mContext);
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
    public void setTypesOfRoleData(TypeOfRoleResponse mTypeOfRoleResponse) {
        try {
            int code = mTypeOfRoleResponse.getStatus();
            String msg = mTypeOfRoleResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                TypeOfRoleResponse.DataBean data = mTypeOfRoleResponse.getData();
                listString.clear();
                ordinaryrole = data.getOrdinaryrole();
                for (int i = 0; i < ordinaryrole.size(); i++) {
                    listString.add(ordinaryrole.get(i).getName());
                }
                adapter3.notifyDataSetChanged();
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
    public void setUploadImage(UploadImageResponse uploadImageResponse) {
        try {
            int code = uploadImageResponse.getStatus();
            String msg = uploadImageResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                mStaffImgsPath = uploadImageResponse.getData().getPath();
                //用户未选择 特殊材料
                submitData();
                hiddenProgressDialogView();
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

    @OnClick({R2.id.main_title_back, R2.id.staff_select_id_name_rl, R2.id.staff_take_picture_add_iv,
            R2.id.take_picture_layout_alpha_rl, R2.id.staff_submit_immediately, R2.id.main_title_right})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.main_title_back) {
            finish();
        } else if (id == R.id.staff_select_id_name_rl) {
            classifyMethod();
        } else if (id == R.id.main_title_right) {
            if (!NoDoubleClickUtils.isDoubleClick()) {
                //往期查询
                ARouter.getInstance().build("/issueQueryActivity/issueQueryActivity").greenChannel().navigation(this);
            }

        } else if (id == R.id.staff_take_picture_add_iv) {

        } else if (id == R.id.take_picture_layout_alpha_rl) {
            CameraActivity.startMe(this, 2005, CameraActivity.MongolianLayerType.IDCARD_POSITIVE);
        } else if (id == R.id.staff_submit_immediately) {
            String staffSelectIdNameText = staffSelectIdName.getText().toString().trim();
            String editWorkDiaryText = editWorkDiary.getText().toString().trim();
            String editAbnormalSubmittedText = editAbnormalSubmitted.getText().toString().trim();

            if (Util.isNull(staffSelectIdNameText)) {
                ToastUtil.showToast(mContext.getApplicationContext(), "请选择身份");
                return;
            }
            if (selectList.size() == 0) {
                ToastUtil.showToast(mContext.getApplicationContext(), "请拍照上传照片");
                return;
            }
            if (Util.isNull(editWorkDiaryText)) {
                ToastUtil.showToast(mContext.getApplicationContext(), "其输入工作日志");
                return;
            }
            uploadImageToServer(selectList);
        }
    }

    //上传特殊图片(方法)
    private void uploadImageToServer(List<LocalMedia> selectList) {
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();
        mPresenter.getUploadImage(headersTreeMap, selectList);
    }

    private void classifyMethod() {
        contentView3 = create3Classificationview();
        //        int width = ScreenUtil.getScreenWidth(BaseApplication.getInstance());
        int width = DensityUtils.dp2px(this, 266);
        int height = adapter3.getCount() * CommonUtil.dip2px(mContext, 45) + CommonUtil.dip2px(mContext, (adapter3.getCount() - 1));
        boolean focusable = true;
        popupWindow3 = new PopupWindow(contentView3, width, height, focusable);
        popupWindow3.setBackgroundDrawable(new ColorDrawable());
        popupWindow3.showAsDropDown(staffSelectIdNameRl, 0, 0);
    }

    private View create3Classificationview() {
        ListView listView = (ListView) View.inflate(mContext, R.layout.listview, null);
        listView.setAdapter(adapter3);
        //        listView.setDivider(new ColorDrawable(Color.GRAY));
        //        listView.setDividerHeight(DensityUtils.dp2px(this,0.5f));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String checkPayment = (String) parent.getItemAtPosition(position);
                staffSelectIdId = ordinaryrole.get(position).getId();
                staffSelectIdName.setText(checkPayment);
                popupWindow3.dismiss();


            }
        });
        return listView;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            takePictureLayoutLl.setVisibility(View.GONE);
            staffTakePictureLayoutListLl.setVisibility(View.VISIBLE);
            String stringExtraPath = data.getStringExtra(KEY_IMAGE_PATH);
            LocalMedia localMedia = new LocalMedia();
            localMedia.setPath(stringExtraPath);
            localMedia.setAndroidQToPath(stringExtraPath);
            selectList.add(localMedia);
            mAdapter.notifyDataSetChanged();

            LogUtil.e(TAG, stringExtraPath);
            //            resultImg.setImageURI(Uri.fromFile(new File(data.getStringExtra(KEY_IMAGE_PATH))));
        }
    }
}

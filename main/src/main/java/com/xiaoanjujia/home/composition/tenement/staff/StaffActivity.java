package com.xiaoanjujia.home.composition.tenement.staff;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.xiaoanjujia.common.base.BaseActivity;
import com.xiaoanjujia.common.util.ResponseCode;
import com.xiaoanjujia.common.util.ToastUtil;
import com.xiaoanjujia.common.util.statusbar.StatusBarUtil;
import com.xiaoanjujia.common.widget.alphaview.AlphaButton;
import com.xiaoanjujia.common.widget.alphaview.AlphaRelativeLayout;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.entities.LoginResponse;
import com.xiaoanjujia.home.tool.Api;

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
@Route(path = "/staffActivity/staffActivity")
public class StaffActivity extends BaseActivity implements StaffContract.View {
    @Inject
    StaffPresenter mPresenter;
    private static final String TAG = "StaffActivity";
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
    @BindView(R2.id.staff_select_id_name)
    AlphaButton staffSelectIdName;
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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);

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
    }

    private void initView() {
        DaggerStaffActivityComponent.builder()
                .appComponent(getAppComponent())
                .staffPresenterModule(new StaffPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);

    }

    private void initData() {

        Map<String, Object> mapParameters = new HashMap<>(1);
        //        mapParameters.put("ACTION", "I002");


        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getRequestData(headersTreeMap, mapParameters);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void setResponseData(LoginResponse loginResponse) {
        try {
            int code = loginResponse.getStatus();
            String msg = loginResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                LoginResponse.DataBean data = loginResponse.getData();


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

    @OnClick({R2.id.main_title_back, R2.id.staff_select_id_name, R2.id.staff_take_picture_add_iv,
            R2.id.take_picture_layout_alpha_rl, R2.id.staff_submit_immediately})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.main_title_back) {
            finish();
        } else if (id == R.id.staff_select_id_name) {

        } else if (id == R.id.staff_take_picture_add_iv) {

        } else if (id == R.id.take_picture_layout_alpha_rl) {

        } else if (id == R.id.staff_submit_immediately) {

        }
    }
}

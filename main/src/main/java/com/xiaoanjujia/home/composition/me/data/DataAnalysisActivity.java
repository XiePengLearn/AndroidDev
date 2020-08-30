package com.xiaoanjujia.home.composition.me.data;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.xiaoanjujia.common.BaseApplication;
import com.xiaoanjujia.common.base.BaseActivity;
import com.xiaoanjujia.common.util.PrefUtils;
import com.xiaoanjujia.common.util.ResponseCode;
import com.xiaoanjujia.common.util.ToastUtil;
import com.xiaoanjujia.common.util.statusbar.StatusBarUtil;
import com.xiaoanjujia.common.widget.alphaview.AlphaButton;
import com.xiaoanjujia.common.widget.bottomnavigation.utils.Utils;
import com.xiaoanjujia.common.widget.headerview.JDHeaderView;
import com.xiaoanjujia.common.widget.pulltorefresh.PtrFrameLayout;
import com.xiaoanjujia.common.widget.pulltorefresh.PtrHandler;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.entities.DataAnalysisResponse;
import com.xiaoanjujia.home.tool.Api;

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
@Route(path = "/dataAnalysisActivity/dataAnalysisActivity")
public class DataAnalysisActivity extends BaseActivity implements DataAnalysisContract.View, PtrHandler {
    @Inject
    DataAnalysisPresenter mPresenter;
    private static final String TAG = "CompositionDetailActivity";


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
    @BindView(R2.id.invitation_particulars_of_matter_ll)
    LinearLayout invitationParticularsOfMatterLl;
    @BindView(R2.id.tv_new_add_fans_num)
    TextView tvNewAddFansNum;
    @BindView(R2.id.tv_total_add_fans_num)
    TextView tvTotalAddFansNum;
    @BindView(R2.id.tv_by_the_end_time)
    TextView tvByTheEndTime;
    @BindView(R2.id.refresh_data_btn)
    AlphaButton refreshDataBtn;
    @BindView(R2.id.invitation_leave_time_ll)
    LinearLayout invitationLeaveTimeLl;
    @BindView(R2.id.tv_visitor_num)
    TextView tvVisitorNum;
    @BindView(R2.id.tv_like_num)
    TextView tvLikeNum;
    @BindView(R2.id.tv_com_num)
    TextView tvComNum;
    @BindView(R2.id.tv_day_time_text)
    TextView tvDayTimeText;
    @BindView(R2.id.tv_contact_num)
    TextView tvContactNum;
    @BindView(R2.id.day_yesterday)
    TextView dayYesterday;
    @BindView(R2.id.day_today)
    TextView dayToday;
    @BindView(R2.id.line_chart_lc)
    LineChart lineChartLc;
    @BindView(R2.id.immediate_promotion_btn)
    AlphaButton immediatePromotionBtn;
    @BindView(R2.id.immediate_promotion_ll)
    LinearLayout immediatePromotionLl;
    @BindView(R2.id.tv_count_time)
    TextView tvCountTime;
    @BindView(R2.id.chart)
    PieChart chart;
    @BindView(R2.id.tv_count_num1)
    TextView tvCountNum1;
    @BindView(R2.id.tv_count_num2)
    TextView tvCountNum2;
    @BindView(R2.id.tv_count_num3)
    TextView tvCountNum3;
    @BindView(R2.id.find_pull_refresh_header)
    JDHeaderView findPullRefreshHeader;

    private int dayType = 1;//1今天,2昨天
    private int mId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_analysis);
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);
        mId = getIntent().getIntExtra("id", -1);

        initView();
        initData();
        initTitle();
    }

    /**
     * 初始化title
     */
    private void initTitle() {
        mainTitleBack.setVisibility(View.VISIBLE);
        mainTitleText.setText("数据分析");
    }

    private void initView() {
        DaggerDataAnalysisActivityComponent.builder()
                .appComponent(getAppComponent())
                .dataAnalysisPresenterModule(new DataAnalysisPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        findPullRefreshHeader.setPtrHandler(this);
    }

    private void initData() {

        //user_id:用户id
        //id:商家id
        //time_status:1今天,2昨天  默认今天
        Map<String, Object> mapParameters = new HashMap<>(1);
        mapParameters.put("user_id", PrefUtils.readUserId(BaseApplication.getInstance()));
        mapParameters.put("id", String.valueOf(mId));//商户Id
        mapParameters.put("time_status", "1");


        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getRequestData(headersTreeMap, mapParameters);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void setResponseData(DataAnalysisResponse mDataAnalysisResponse) {
        try {
            int code = mDataAnalysisResponse.getStatus();
            String msg = mDataAnalysisResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                DataAnalysisResponse.DataBean data = mDataAnalysisResponse.getData();
                //"addfans": 0,---今天新增粉丝
                //    "count_fans": 4,---粉丝总量
                //    "day_visit": 0,---今天访客
                //    "day_comment": 0,---今天评论
                //    "day_like": 0,---今天点赞数
                //    "statistics"[]: --24小时实时统计
                int addfans = data.getAddfans();
                tvNewAddFansNum.setText(String.valueOf(addfans));
                int count_fans = data.getCount_fans();
                tvTotalAddFansNum.setText(String.valueOf(count_fans));
                int day_visit = data.getDay_visit();
                tvVisitorNum.setText(String.valueOf(day_visit));
                int day_like = data.getDay_like();
                tvLikeNum.setText(String.valueOf(day_like));
                int day_comment = data.getDay_comment();
                tvComNum.setText(String.valueOf(day_comment));
                int day_contact = data.getDay_contact();
                tvContactNum.setText(String.valueOf(day_contact));
                List<DataAnalysisResponse.DataBean.StatisticsBean> statistics = data.getStatistics();

                //"datetime": "2020-07-24",---统计时间
                //    "day_new_visit_num": 0,今天访问新增客户数量
                //    "day_new_visit_bai": "0%",今天访问新增客户百分比
                //    "day_old_visit_num": 0,今天回访客户数量
                //    "day_old_visit_bai": "0%",今天回访客户百分比
                //    "yesterday_visit": 4,昨天访问量
                //    "yesterday_browse": 2,昨天浏览量

                String datetime = data.getDatetime();
                if (!Utils.isNull(datetime)) {
                    tvCountTime.setText(String.format("统计时间: %s", datetime));
                }
                int day_new_visit_num = data.getDay_new_visit_num();
                String day_new_visit_bai = data.getDay_new_visit_bai();
                int day_old_visit_num = data.getDay_old_visit_num();
                String day_old_visit_bai = data.getDay_old_visit_bai();
                int yesterday_visit = data.getYesterday_visit();
                int yesterday_browse = data.getYesterday_browse();
                int yesterday_contact = data.getYesterday_contact();
                //1今天,2昨天
                if (dayType == 1) {
                    tvCountNum1.setText(String.format("今日联系客户: %s", yesterday_contact));
                    tvCountNum2.setText(String.format("今日访客数: %s", yesterday_visit));
                    tvCountNum3.setText(String.format("今日浏览次数: %s", yesterday_browse));
                    tvDayTimeText.setText("今日时时");
                } else {
                    tvCountNum1.setText(String.format("昨日联系客户: %s", yesterday_contact));
                    tvCountNum2.setText(String.format("昨日访客数: %s", yesterday_visit));
                    tvCountNum3.setText(String.format("昨日浏览次数: %s", yesterday_browse));
                    tvDayTimeText.setText("昨日时时");
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

    @OnClick({R2.id.main_title_back, R2.id.day_yesterday, R2.id.day_today, R2.id.immediate_promotion_btn, R2.id.refresh_data_btn})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.main_title_back) {
            finish();
        } else if (id == R.id.day_yesterday) {

        } else if (id == R.id.day_today) {

        } else if (id == R.id.immediate_promotion_btn) {

        } else if (id == R.id.refresh_data_btn) {

        }
    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
                frame.refreshComplete();
            }
        }, 500);
    }
}

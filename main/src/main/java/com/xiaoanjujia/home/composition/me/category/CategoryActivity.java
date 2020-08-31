package com.xiaoanjujia.home.composition.me.category;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.xiaoanjujia.common.BaseApplication;
import com.xiaoanjujia.common.base.BaseActivity;
import com.xiaoanjujia.common.base.baseadapter.BaseQuickAdapter;
import com.xiaoanjujia.common.util.PrefUtils;
import com.xiaoanjujia.common.util.ResponseCode;
import com.xiaoanjujia.common.util.ToastUtil;
import com.xiaoanjujia.common.util.statusbar.StatusBarUtil;
import com.xiaoanjujia.common.widget.bottomnavigation.utils.Utils;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.composition.main.community.CommunityGridLayoutManager;
import com.xiaoanjujia.home.composition.me.post_message.PostMessageActivity;
import com.xiaoanjujia.home.entities.ComcateListsResponse;
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
@Route(path = "/categoryActivity/categoryActivity")
public class CategoryActivity extends BaseActivity implements CategoryContract.View {
    @Inject
    CategoryPresenter mPresenter;
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
    @BindView(R2.id.rl_category)
    RecyclerView aflCotent;
    private CategoryAdapter mAdapterResult;
    private List<ComcateListsResponse.DataBean> dataList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
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
        mainTitleText.setText("选择发布类别");
    }

    private void initView() {
        DaggerCategoryActivityComponent.builder()
                .appComponent(getAppComponent())
                .categoryPresenterModule(new CategoryPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        CommunityGridLayoutManager manager = new CommunityGridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        aflCotent.setLayoutManager(manager);
        mAdapterResult = new CategoryAdapter(R.layout.item_category_grald);
        mAdapterResult.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ComcateListsResponse.DataBean dataBean = dataList.get(position);
                int cate_id = dataBean.getCate_id();
                String cate_name = dataBean.getCate_name();
                Intent intent = new Intent(CategoryActivity.this, PostMessageActivity.class);
                intent.putExtra("cate_id", cate_id);
                startActivity(intent);
                if (!Utils.isNull(cate_name)) {
                    ToastUtil.showToast(BaseApplication.getInstance(), "发布类别:" + cate_name);
                }

            }
        });
        aflCotent.setAdapter(mAdapterResult);
    }

    private void initData() {

        Map<String, Object> mapParameters = new HashMap<>(1);
        mapParameters.put("user_id", PrefUtils.readUserId(BaseApplication.getInstance()));
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();
        mPresenter.getRequestData(headersTreeMap, mapParameters);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void setResponseData(ComcateListsResponse mComcateListsResponse) {
        try {
            int code = mComcateListsResponse.getStatus();
            String msg = mComcateListsResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                dataList = mComcateListsResponse.getData();
                if (dataList != null) {
                    if (dataList.size() > 0) {
                        List<ComcateListsResponse.DataBean> dataHistory = mAdapterResult.getData();
                        dataHistory.clear();
                        mAdapterResult.addData(dataList);
                    }
                }
            } else if (code == ResponseCode.SEESION_ERROR) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(this);
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

    @OnClick({R2.id.main_title_back, R2.id.main_title_right})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.main_title_back) {
            finish();
        } else if (id == R.id.main_title_right) {
        }
    }
}

package com.xiaoanjujia.home.composition.unlocking.select_housing;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.xiaoanjujia.common.base.BaseActivity;
import com.xiaoanjujia.common.util.ResponseCode;
import com.xiaoanjujia.common.util.ToastUtil;
import com.xiaoanjujia.common.util.statusbar.StatusBarUtil;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.composition.tenement.detail.RecordDetailGridLayoutManager;
import com.xiaoanjujia.home.composition.unlocking.select_housing.view.ContactSortModel;
import com.xiaoanjujia.home.composition.unlocking.select_housing.view.EditTextWithDel;
import com.xiaoanjujia.home.composition.unlocking.select_housing.view.PinyinComparator;
import com.xiaoanjujia.home.composition.unlocking.select_housing.view.PinyinUtils;
import com.xiaoanjujia.home.composition.unlocking.select_housing.view.SideBar;
import com.xiaoanjujia.home.composition.unlocking.select_housing.view.SortAdapter;
import com.xiaoanjujia.home.entities.SelectHousingResponse;
import com.xiaoanjujia.home.entities.VisitorPersonInfoResponse;
import com.xiaoanjujia.home.tool.Api;

import java.util.ArrayList;
import java.util.Collections;
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
@Route(path = "/selectHousingActivity/selectHousingActivity")
public class SelectHousingActivity extends BaseActivity implements SelectHousingContract.View {
    @Inject
    SelectHousingPresenter mPresenter;
    private static final String TAG = "SelectHousingActivity";
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
    @BindView(R2.id.et_search)
    EditTextWithDel mEtSearchName;
    @BindView(R2.id.lv_contact)
    ListView sortListView;
    @BindView(R2.id.dialog)
    TextView dialog;
    @BindView(R2.id.sidrbar)
    SideBar sideBar;
    @BindView(R2.id.ll_knowledge_publish_root)
    LinearLayout llKnowledgePublishRoot;


    private SortAdapter adapter;
    private List<ContactSortModel> SourceDateList;
    private String mParentIndexCodes;
    private List<SelectHousingResponse.DataBean> mData;
    private List<String> mHousingDataList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_housing);
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);
        Intent intent = getIntent();
        mParentIndexCodes = intent.getStringExtra("parentIndexCodes");
        SourceDateList = new ArrayList<>();
        mHousingDataList = new ArrayList<>();
        initView();
        initData();
        initTitle();

        initDatas();
        initEvents();

    }


    private void initEvents() {
        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position + 1);
                }
            }
        });

        //ListView的点击事件
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContactSortModel item = (ContactSortModel) adapter.getItem(position);
                String name = item.getName();
                Intent intent = new Intent();
                intent.putExtra("name", name); //添加额外的值
                for (int i = 0; i < mData.size(); i++) {
                    if (mData.get(i).getName().equals(name)) {
                        String indexCode = mData.get(i).getIndexCode();
                        intent.putExtra("indexCode", indexCode); //添加额外的值
                    }


                }
                //                if (mData.size() > position) {
                //                    String indexCode = mData.get(position).getIndexCode();
                //                    intent.putExtra("indexCode", indexCode); //添加额外的值
                //                }
                setResult(RESULT_OK, intent); //返回子Activity的值
                finish();

                Toast.makeText(getApplication(), ((ContactSortModel) adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
            }
        });

        //根据输入框输入值的改变来过滤搜索
        mEtSearchName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initDatas() {
        sideBar.setTextView(dialog);
    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<ContactSortModel> mSortList = new ArrayList<>();
        if (TextUtils.isEmpty(filterStr)) {
            mSortList = SourceDateList;
        } else {
            mSortList.clear();
            for (ContactSortModel sortModel : SourceDateList) {
                String name = sortModel.getName();
                if (name.toUpperCase().indexOf(filterStr.toString().toUpperCase()) != -1 || PinyinUtils.getPingYin(name).toUpperCase().startsWith(filterStr.toString().toUpperCase())) {
                    mSortList.add(sortModel);
                }
            }
        }
        // 根据a-z进行排序
        Collections.sort(mSortList, new PinyinComparator());
        adapter.updateListView(mSortList);
    }

    private List<ContactSortModel> filledData(String[] date) {
        List<ContactSortModel> mSortList = new ArrayList<>();
        ArrayList<String> indexString = new ArrayList<>();

        for (int i = 0; i < date.length; i++) {
            ContactSortModel sortModel = new ContactSortModel();
            sortModel.setName(date[i]);
            String pinyin = PinyinUtils.getPingYin(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
                if (!indexString.contains(sortString)) {
                    indexString.add(sortString);
                }
            }
            mSortList.add(sortModel);
        }
        Collections.sort(indexString);
        sideBar.setIndexText(indexString);
        return mSortList;
    }

    /**
     * 初始化title
     */
    private void initTitle() {
        mainTitleBack.setVisibility(View.VISIBLE);
        mainTitleText.setText("选择小区");
    }


    private void initView() {
        DaggerSelectHousingComponent.builder()
                .appComponent(getAppComponent())
                .selectHousingPresenterModule(new SelectHousingPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        RecordDetailGridLayoutManager manager = new RecordDetailGridLayoutManager(SelectHousingActivity.this, 1, GridLayoutManager.VERTICAL, false);

    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    //    public void initData() {
    //        Map<String, Object> mapParameters = new HashMap<>(2);
    //        mapParameters.put("paramName", "phoneNo");
    //        mapParameters.put("paramValue", PrefUtils.readPhone(BaseApplication.getInstance()));
    //        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();
    //        mPresenter.getRequestData(headersTreeMap, mapParameters);
    //    }


    @Override
    public void setResponseData(VisitorPersonInfoResponse mVisitorPersonInfoResponse) {
        try {
            String code = mVisitorPersonInfoResponse.getStatus();
            String msg = mVisitorPersonInfoResponse.getMessage();
            if (code.equals(ResponseCode.SUCCESS_OK_STRING)) {
                List<VisitorPersonInfoResponse.DataBean> data = mVisitorPersonInfoResponse.getData();
                if (data != null && data.size() > 0) {
                    VisitorPersonInfoResponse.DataBean dataBean = data.get(0);
                    if (dataBean != null) {
                        String orgName = dataBean.getOrgName();

                    }
                }


            } else if (code.equals(ResponseCode.SEESION_ERROR_STRING)) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(SelectHousingActivity.this);
            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(SelectHousingActivity.this, msg);
                }

            }
        } catch (Exception e) {
            ToastUtil.showToast(SelectHousingActivity.this, "解析数据失败");
        }
    }

    //"pageNo": "1"
    //-     "pageSize": "10"
    //-     "resourceType": "region"
    //-     "parentIndexCodes": "root000000"
    public void initData() {
        Map<String, Object> mapParameters = new HashMap<>(4);
        mapParameters.put("pageNo", "1");
        mapParameters.put("pageSize", "1000");
        mapParameters.put("resourceType", "region");
        mapParameters.put("parentIndexCodes", mParentIndexCodes);
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();
        mPresenter.getSelectHousingData(headersTreeMap, mapParameters);
    }

    private void setAdapter() {

    }

    @Override
    public void setSelectHousingData(SelectHousingResponse mSelectHousingResponse) {
        try {
            String code = mSelectHousingResponse.getStatus();
            String msg = mSelectHousingResponse.getMessage();
            if (code.equals(ResponseCode.SUCCESS_OK_STRING)) {
                mData = mSelectHousingResponse.getData();

                mHousingDataList.clear();
                if (mData != null && mData.size() > 0) {
                    for (int i = 0; i < mData.size(); i++) {
                        String name = mData.get(i).getName();
                        mHousingDataList.add(name);
                    }
                    String[] array = mHousingDataList.toArray(new String[mHousingDataList.size()]);
                    SourceDateList = filledData(array);
                    Collections.sort(SourceDateList, new PinyinComparator());
                    adapter = new SortAdapter(this, SourceDateList);
                    sortListView.setAdapter(adapter);
                }


            } else if (code.equals(ResponseCode.SEESION_ERROR_STRING)) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(SelectHousingActivity.this);
            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(SelectHousingActivity.this, msg);
                }

            }
        } catch (Exception e) {
            ToastUtil.showToast(SelectHousingActivity.this, "解析数据失败");
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


    @OnClick({R2.id.main_title_back})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.main_title_back) {
            finish();
        }
    }


}

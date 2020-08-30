package com.xiaoanjujia.home.composition.community;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.rmondjone.camera.CameraActivity;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.xiaoanjujia.common.BaseApplication;
import com.xiaoanjujia.common.base.BaseActivity;
import com.xiaoanjujia.common.base.baseadapter.BaseQuickAdapter;
import com.xiaoanjujia.common.util.LogUtil;
import com.xiaoanjujia.common.util.PrefUtils;
import com.xiaoanjujia.common.util.ResponseCode;
import com.xiaoanjujia.common.util.ToastUtil;
import com.xiaoanjujia.common.util.statusbar.StatusBarUtil;
import com.xiaoanjujia.common.widget.alphaview.AlphaButton;
import com.xiaoanjujia.common.widget.alphaview.AlphaLinearLayout;
import com.xiaoanjujia.common.widget.alphaview.AlphaRelativeLayout;
import com.xiaoanjujia.common.widget.bottomnavigation.utils.Utils;
import com.xiaoanjujia.common.widget.headerview.JDHeaderView;
import com.xiaoanjujia.common.widget.pulltorefresh.PtrFrameLayout;
import com.xiaoanjujia.common.widget.pulltorefresh.PtrHandler;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.composition.me.merchants.GlideEngine;
import com.xiaoanjujia.home.entities.CommentDetailsResponse;
import com.xiaoanjujia.home.entities.CommentListResponse;
import com.xiaoanjujia.home.entities.CommentPublishResponse;
import com.xiaoanjujia.home.entities.CommunityDetailsResponse;
import com.xiaoanjujia.home.tool.Api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * @author xiepeng
 */
@Route(path = "/compositionDetailActivity/compositionDetailActivity")
public class CompositionDetailActivity extends BaseActivity implements CompositionDetailContract.View, PtrHandler, BaseQuickAdapter.RequestLoadMoreListener {
    @Inject
    CompositionDetailPresenter mPresenter;
    private static final String TAG = "CompositionDetailActivity";

    public static final String KEY_IMAGE_PATH = "imagePath";
    @BindView(R2.id.fake_status_bar)
    View fakeStatusBar;
    @BindView(R2.id.main_title_back)
    ImageView mainTitleBack;
    @BindView(R2.id.main_title_text)
    TextView mainTitleText;
    @BindView(R2.id.main_title_right)
    ImageView mainTitleRight;
    @BindView(R2.id.iv_collect)
    ImageView ivCollect;
    @BindView(R2.id.main_title_container)
    LinearLayout mainTitleContainer;
    @BindView(R2.id.chat_list)
    RecyclerView mRecyclerView;
    @BindView(R2.id.find_pull_refresh_header)
    JDHeaderView findPullRefreshHeader;
    @BindView(R2.id.ll_collect)
    AlphaLinearLayout llCollect;
    @BindView(R2.id.ll_copy_phone_number)
    AlphaLinearLayout llCopyPhoneNumber;
    @BindView(R2.id.call_phone)
    TextView callPhone;
    @BindView(R2.id.rl_call_phone)
    AlphaRelativeLayout rlCallPhone;
    @BindView(R2.id.bottom_layout)
    LinearLayout bottomLayout;


    private List<LocalMedia> selectList = new ArrayList<>();
    private CompositionDetailGridImageAdapter mAdapterHeaderRv;
    private int themeId;
    private int mId;
    private LayoutInflater mLayoutInflater;
    private CommunityDetailsActivityAdapter mAdapter;
    private TextView communityTitleTextTv;
    private RecyclerView recordDetailRv;
    private TextView itemCompositionServeTv;
    private TextView itemCompositionAddressTv;
    private TextView itemCompositionBtnDetails;
    private TextView itemCompositionBtnComment;
    private TextView itemCompositionBtnMerchant;
    private ImageView itemCompositionImageAd;
    private EditText editItemCompositionCommentContent;
    private AlphaButton publishCompositionCommentContentBtn;
    private AlphaButton compositionCommentStatus1;
    private AlphaButton compositionCommentStatus2;
    private AlphaButton compositionCommentStatus3;
    private TextView shopTitleTv;
    private TextView shopTitleDesTv;
    private LinearLayout itemCompositionBtnDetailsLl;
    private LinearLayout itemCompositionBtnCommentLl;
    private LinearLayout itemCompositionBtnMerchantLl;
    private TextView itemShopNameTv;
    private TextView itemShopNamePublishNumberTv;
    private TextView itemShopGradeDesTv;
    private int page = 1;
    private String type = "all_count";
    private MaterialRatingBar comNormalRatingbar;
    private int mCommunityId;
    private String shop_phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composition_detail);
        themeId = R.style.picture_default_style;
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);

        Intent intent = getIntent();
        mId = intent.getIntExtra("id", 0);
        mCommunityId = intent.getIntExtra("community_id", 0);
        initView();
        requestData();
        initTitle();
        //        page = 1;
        //        type = "all_count";
        //        initCommentDetailsData(page,type);
        initCommentCount();
        //新增访客
        initAddVisit();
        //用户领取商户红包
        initActionCheckBonus();

    }

    /**
     * 初始化title
     */
    private void initTitle() {
        mainTitleBack.setVisibility(View.VISIBLE);
        mainTitleText.setText("详情页");
    }

    private void initView() {
        DaggerCompositionDetailActivityComponent.builder()
                .appComponent(getAppComponent())
                .compositionDetailPresenterModule(new CompositionDetailPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);

        mLayoutInflater = LayoutInflater.from(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CommunityDetailsActivityAdapter(R.layout.item_community_activity_adapter);
        mAdapter.setOnLoadMoreListener(this);
        findPullRefreshHeader.setPtrHandler(this);

        View itemHeader = mLayoutInflater.inflate(R.layout.activity_composition_detail_header, null);
        recordDetailRv = itemHeader.findViewById(R.id.record_detail_rv);
        communityTitleTextTv = itemHeader.findViewById(R.id.community_title_text_tv);
        itemCompositionServeTv = itemHeader.findViewById(R.id.item_composition_serve_tv);
        itemCompositionAddressTv = itemHeader.findViewById(R.id.item_composition_address_tv);
        itemCompositionBtnDetails = itemHeader.findViewById(R.id.item_composition_btn_details);
        itemCompositionBtnDetails.setTextColor(getResources().getColor(R.color.color_2AAD67));
        itemCompositionBtnComment = itemHeader.findViewById(R.id.item_composition_btn_comment);
        itemCompositionBtnMerchant = itemHeader.findViewById(R.id.item_composition_btn_merchant);

        itemCompositionBtnDetailsLl = itemHeader.findViewById(R.id.item_composition_btn_details_ll);
        itemCompositionBtnCommentLl = itemHeader.findViewById(R.id.item_composition_btn_comment_ll);
        itemCompositionBtnMerchantLl = itemHeader.findViewById(R.id.item_composition_btn_merchant_ll);
        comNormalRatingbar = itemHeader.findViewById(R.id.com_normal_ratingbar);

        itemShopNameTv = itemHeader.findViewById(R.id.item_shop_name_tv);
        itemShopNamePublishNumberTv = itemHeader.findViewById(R.id.item_shop_name_publish_number_tv);
        itemShopGradeDesTv = itemHeader.findViewById(R.id.item_shop_grade_des_tv);

        itemCompositionImageAd = itemHeader.findViewById(R.id.item_composition_image_ad);
        editItemCompositionCommentContent = itemHeader.findViewById(R.id.edit_item_composition_comment_content);
        publishCompositionCommentContentBtn = itemHeader.findViewById(R.id.publish_composition_comment_content_btn);
        publishCompositionCommentContentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String comtext = editItemCompositionCommentContent.getText().toString().trim();
                if (Utils.isNull(comtext)) {
                    ToastUtil.showToast(BaseApplication.getInstance(), "请写出您的评价内容");
                    return;
                }
                int rating = (int) comNormalRatingbar.getRating();
                if (rating == 0) {
                    ToastUtil.showToast(BaseApplication.getInstance(), "请输入星级评价");
                    return;
                }
                initCommentPublish(String.valueOf(rating), comtext);
            }
        });
        compositionCommentStatus1 = itemHeader.findViewById(R.id.composition_comment_status_1);
        compositionCommentStatus1.setBackground(getResources().getDrawable(R.drawable.bg_shap_button_select));
        compositionCommentStatus1.setTextColor(getResources().getColor(R.color.color_2AAD67));
        compositionCommentStatus2 = itemHeader.findViewById(R.id.composition_comment_status_2);
        compositionCommentStatus3 = itemHeader.findViewById(R.id.composition_comment_status_3);

        itemCompositionBtnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //详情
                itemCompositionBtnDetails.setTextColor(getResources().getColor(R.color.color_2AAD67));
                itemCompositionBtnComment.setTextColor(getResources().getColor(R.color.color_494949));
                itemCompositionBtnMerchant.setTextColor(getResources().getColor(R.color.color_494949));

                itemCompositionBtnDetailsLl.setVisibility(View.VISIBLE);
                itemCompositionBtnCommentLl.setVisibility(View.GONE);
                itemCompositionBtnMerchantLl.setVisibility(View.GONE);

                List<CommentListResponse.DataBean> data = mAdapter.getData();
                data.clear();
                mAdapter.notifyDataSetChanged();
                hideKeyboard(v);

            }
        });
        itemCompositionBtnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //评价
                itemCompositionBtnDetails.setTextColor(getResources().getColor(R.color.color_494949));
                itemCompositionBtnComment.setTextColor(getResources().getColor(R.color.color_2AAD67));
                itemCompositionBtnMerchant.setTextColor(getResources().getColor(R.color.color_494949));
                itemCompositionBtnDetailsLl.setVisibility(View.GONE);
                itemCompositionBtnCommentLl.setVisibility(View.VISIBLE);
                itemCompositionBtnMerchantLl.setVisibility(View.GONE);
                page = 1;
                type = "all_count";
                initCommentDetailsData(page, type);
                compositionCommentStatus1.setBackground(getResources().getDrawable(R.drawable.bg_shap_button_select));
                compositionCommentStatus1.setTextColor(getResources().getColor(R.color.color_2AAD67));
                compositionCommentStatus2.setBackground(getResources().getDrawable(R.drawable.bg_shap_button));
                compositionCommentStatus2.setTextColor(getResources().getColor(R.color.color_494949));
                compositionCommentStatus3.setBackground(getResources().getDrawable(R.drawable.bg_shap_button));
                compositionCommentStatus3.setTextColor(getResources().getColor(R.color.color_494949));
            }
        });
        itemCompositionBtnMerchant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //商家
                itemCompositionBtnDetails.setTextColor(getResources().getColor(R.color.color_494949));
                itemCompositionBtnComment.setTextColor(getResources().getColor(R.color.color_494949));
                itemCompositionBtnMerchant.setTextColor(getResources().getColor(R.color.color_2AAD67));
                itemCompositionBtnDetailsLl.setVisibility(View.GONE);
                itemCompositionBtnCommentLl.setVisibility(View.GONE);
                itemCompositionBtnMerchantLl.setVisibility(View.VISIBLE);

                List<CommentListResponse.DataBean> data = mAdapter.getData();
                data.clear();
                mAdapter.notifyDataSetChanged();
                hideKeyboard(v);
            }
        });

        compositionCommentStatus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compositionCommentStatus1.setBackground(getResources().getDrawable(R.drawable.bg_shap_button_select));
                compositionCommentStatus1.setTextColor(getResources().getColor(R.color.color_2AAD67));
                compositionCommentStatus2.setBackground(getResources().getDrawable(R.drawable.bg_shap_button));
                compositionCommentStatus2.setTextColor(getResources().getColor(R.color.color_494949));
                compositionCommentStatus3.setBackground(getResources().getDrawable(R.drawable.bg_shap_button));
                compositionCommentStatus3.setTextColor(getResources().getColor(R.color.color_494949));
                page = 1;
                type = "all_count";

                mAdapter.setEnableLoadMore(true);
                mAdapter.loadMoreComplete();
                mAdapter.getData().clear();
                mAdapter.notifyDataSetChanged();
                initCommentDetailsData(page, type);

            }
        });
        compositionCommentStatus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compositionCommentStatus1.setBackground(getResources().getDrawable(R.drawable.bg_shap_button));
                compositionCommentStatus1.setTextColor(getResources().getColor(R.color.color_494949));
                compositionCommentStatus2.setBackground(getResources().getDrawable(R.drawable.bg_shap_button_select));
                compositionCommentStatus2.setTextColor(getResources().getColor(R.color.color_2AAD67));
                compositionCommentStatus3.setBackground(getResources().getDrawable(R.drawable.bg_shap_button));
                compositionCommentStatus3.setTextColor(getResources().getColor(R.color.color_494949));
                page = 1;
                type = "good_count";
                mAdapter.setEnableLoadMore(true);
                mAdapter.loadMoreComplete();
                mAdapter.getData().clear();
                mAdapter.notifyDataSetChanged();
                initCommentDetailsData(page, type);

            }
        });
        compositionCommentStatus3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compositionCommentStatus1.setBackground(getResources().getDrawable(R.drawable.bg_shap_button));
                compositionCommentStatus1.setTextColor(getResources().getColor(R.color.color_494949));
                compositionCommentStatus2.setBackground(getResources().getDrawable(R.drawable.bg_shap_button));
                compositionCommentStatus2.setTextColor(getResources().getColor(R.color.color_494949));
                compositionCommentStatus3.setBackground(getResources().getDrawable(R.drawable.bg_shap_button_select));
                compositionCommentStatus3.setTextColor(getResources().getColor(R.color.color_2AAD67));
                page = 1;
                type = "difference_count";
                mAdapter.setEnableLoadMore(true);
                mAdapter.loadMoreComplete();
                mAdapter.getData().clear();
                mAdapter.notifyDataSetChanged();
                initCommentDetailsData(page, type);

            }
        });

        shopTitleTv = itemHeader.findViewById(R.id.shop_title_tv);
        shopTitleDesTv = itemHeader.findViewById(R.id.shop_title_des_tv);
        CompositionDetailGridLayoutManager manager = new CompositionDetailGridLayoutManager(CompositionDetailActivity.this, 3, GridLayoutManager.VERTICAL, false);
        recordDetailRv.setLayoutManager(manager);
        mAdapterHeaderRv = new CompositionDetailGridImageAdapter(CompositionDetailActivity.this, onAddPicClickListener);
        mAdapterHeaderRv.setList(selectList);
        recordDetailRv.setAdapter(mAdapterHeaderRv);
        mAdapterHeaderRv.setOnItemClickListener(new CompositionDetailGridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                LogUtil.e(TAG, "长度---->" + selectList.size());
                if (selectList.size() > 0) {
                    PictureSelector.create(CompositionDetailActivity.this)
                            .themeStyle(themeId) // xml设置主题
                            .isNotPreviewDownload(true)// 预览图片长按是否可以下载
                            .loadImageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                            .openExternalPreview(position, selectList);
                }
            }
        });

        mAdapter.addHeaderView(itemHeader);
        mAdapter.setEnableLoadMore(true);
        mAdapter.loadMoreComplete();
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //                List data = adapter.getData();
                //                CommentDetailsResponse.DataBean.AllCommentsBean dateBean = (CommentDetailsResponse.DataBean.AllCommentsBean) data.get(position);
                //                int id = dateBean.getId();
                //                Intent intent = new Intent(CompositionDetailActivity.this, CompositionDetailActivity.class);
                //                intent.putExtra("id", id);
                //                startActivity(intent);
            }
        });
    }

    private CompositionDetailGridImageAdapter.onAddPicClickListener1 onAddPicClickListener = new CompositionDetailGridImageAdapter.onAddPicClickListener1() {
        @Override
        public void onAddPicClick1() {
            CameraActivity.startMe(CompositionDetailActivity.this, 2005, CameraActivity.MongolianLayerType.IDCARD_POSITIVE);

        }

    };


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
    public void setResponseData(CommunityDetailsResponse mCommunityDetailsResponse) {
        try {
            int code = mCommunityDetailsResponse.getStatus();
            String msg = mCommunityDetailsResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                CommunityDetailsResponse.DataBean data = mCommunityDetailsResponse.getData();
                if (data != null) {
                    List<String> log_imgs = data.getTop_imgs();
                    if (log_imgs != null) {
                        for (int i = 0; i < log_imgs.size(); i++) {
                            LocalMedia localMedia = new LocalMedia();
                            localMedia.setPath(log_imgs.get(i));
                            selectList.add(localMedia);
                        }

                        mAdapterHeaderRv.notifyDataSetChanged();
                    }

                    /**
                     * "id": 5,
                     *         "community_id": 3,
                     *         "title": "啊啊啊1111骚点的",
                     *         "top_imgs": [
                     *             "https://a.xiaoanjujia.com/uploads/images/2020/07-27/685fac350b8cf99251ffe459f24fad24.jpg",
                     *             "https://a.xiaoanjujia.com/uploads/images/2020/07-27/685fac350b8cf99251ffe459f24fad24.jpg",
                     *             "https://a.xiaoanjujia.com/uploads/images/2020/07-27/685fac350b8cf99251ffe459f24fad24.jpg"
                     *         ],
                     *         "region": "北京市",
                     *         "detailed_address": "朝阳区1022",
                     *         "advertisement_img": "https://a.xiaoanjujia.com/uploads/images/2020/07-27/685fac350b8cf99251ffe459f24fad24.jpg",
                     *         "advertisement_url": "https://a.xiaoanjujia.com/uploads/images/2020/07-27/685fac350b8cf99251ffe459f24fad24.jpg",
                     *         "title_text": "啊啊啊是的撒多所多多",
                     *         "contacts_phone": "18635805566",
                     *         "name_acronym": "小安",
                     *         "shop_name": "小安居家",
                     *         "shop_phone": "1863805566",
                     *         "shop_order_count": 12
                     */
                    String title = data.getTitle();
                    if (!Utils.isNull(title)) {
                        communityTitleTextTv.setText(title);
                    }
                    String region = data.getRegion();
                    if (!Utils.isNull(region)) {
                        itemCompositionServeTv.setText(String.format("服务于%s", region));
                    }

                    String detailed_address = data.getDetailed_address();
                    if (!Utils.isNull(detailed_address)) {
                        itemCompositionAddressTv.setText(detailed_address);
                    }

                    String advertisement_img = data.getAdvertisement_img();
                    if (!Utils.isNull(advertisement_img)) {
                        RequestOptions options = new RequestOptions()
                                .centerCrop()
                                .placeholder(R.drawable.default_icon)
                                .diskCacheStrategy(DiskCacheStrategy.ALL);
                        Glide.with(CompositionDetailActivity.this)
                                .load(advertisement_img)
                                .apply(options)
                                .into(itemCompositionImageAd);
                    }

                    String category_name = data.getCategory_name();
                    if (!Utils.isNull(category_name)) {
                        shopTitleTv.setText(String.format("服务类型: %s", category_name));
                    }
                    String title_text = data.getTitle_text();
                    if (!Utils.isNull(title_text)) {
                        shopTitleDesTv.setText(title_text);
                    }


                    String shop_name = data.getShop_name();
                    if (!Utils.isNull(shop_name)) {
                        itemShopNameTv.setText(shop_name);
                    }
                    int shop_order_count = data.getShop_order_count();
                    itemShopNamePublishNumberTv.setText(String.format("(总共发布%s条帖子)", String.valueOf(shop_order_count)));
                    String grade = data.getGrade();
                    if (!Utils.isNull(grade)) {
                        itemShopGradeDesTv.setText(grade);
                    }

                    shop_phone = data.getShop_phone();
                    if (!Utils.isNull(shop_phone)) {
                        callPhone.setText(shop_phone);
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

    private void initCommentDetailsData(int page, String type) {
        //all_count:总评
        //good_count:好评
        //difference_count:差评
        Map<String, Object> mapParameters = new HashMap<>(3);
        mapParameters.put("type", type);
        mapParameters.put("id", mId);
        mapParameters.put("page", String.valueOf(page));
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getCommentDetailsData(headersTreeMap, mapParameters);
    }


    @Override
    public void setCommentDetailsData(CommentListResponse mCommentListResponse) {
        try {
            int code = mCommentListResponse.getStatus();
            String msg = mCommentListResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                List<CommentListResponse.DataBean> messageDate = mCommentListResponse.getData();
                if (messageDate != null) {
                    if (messageDate.size() > 0) {
                        if (messageDate.size() < 10) {
                            mAdapter.setEnableLoadMore(false);
                        } else {
                            mAdapter.setEnableLoadMore(true);
                        }
                        List<CommentListResponse.DataBean> data = mAdapter.getData();
                        data.clear();
                        mAdapter.addData(messageDate);
                    } else {
                        mAdapter.setEnableLoadMore(false);
                        List<CommentListResponse.DataBean> data = mAdapter.getData();
                        data.clear();
                        mAdapter.notifyDataSetChanged();
                    }

                } else {

                    mAdapter.setEnableLoadMore(false);
                    List<CommentListResponse.DataBean> data = mAdapter.getData();
                    data.clear();
                    mAdapter.notifyDataSetChanged();
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

    private void initCommentPublish(String star_rating, String common_text) {
        /**
         * user_id:用户id
         * community_id:商户id
         * id:此信息(订单)id
         * star_rating:12345星(传对应数值就行)
         * common_text:内容
         */
        Map<String, Object> mapParameters = new HashMap<>(1);
        mapParameters.put("user_id", PrefUtils.readUserId(BaseApplication.getInstance()));
        mapParameters.put("id", String.valueOf(mId));
        mapParameters.put("community_id", mCommunityId);
        mapParameters.put("star_rating", star_rating);
        mapParameters.put("common_text", common_text);
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getCommentPublish(headersTreeMap, mapParameters);
    }

    @Override
    public void setCommentPublish(CommentPublishResponse mCommentPublishResponse) {
        try {
            int code = mCommentPublishResponse.getStatus();
            String msg = mCommentPublishResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                if (!Utils.isNull(msg)) {
                    ToastUtil.showToast(BaseApplication.getInstance(), msg);
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

    private void initCommentLike() {
        /**
         * user_id:用户id
         * community_id:商户id
         * id:此信息(订单)id
         * star_rating:12345星(传对应数值就行)
         * common_text:内容
         */
        Map<String, Object> mapParameters = new HashMap<>(1);
        mapParameters.put("user_id", PrefUtils.readUserId(BaseApplication.getInstance()));
        mapParameters.put("id", String.valueOf(mId));
        mapParameters.put("community_id", mCommunityId);
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getCommentLike(headersTreeMap, mapParameters);
    }

    @Override
    public void setCommentLike(CommentPublishResponse mCommentPublishResponse) {
        try {
            int code = mCommentPublishResponse.getStatus();
            String msg = mCommentPublishResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                ivCollect.setImageResource(R.drawable.star_icon);
                if (!Utils.isNull(msg)) {
                    ToastUtil.showToast(BaseApplication.getInstance(), msg);
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

    private void initAddVisit() {
        /**
         * user_id:用户id
         * community_id:商户id
         * id:此信息(订单)id
         * star_rating:12345星(传对应数值就行)
         * common_text:内容
         */
        Map<String, Object> mapParameters = new HashMap<>(1);
        mapParameters.put("user_id", PrefUtils.readUserId(BaseApplication.getInstance()));
        mapParameters.put("id", String.valueOf(mId));
        mapParameters.put("community_id", mCommunityId);
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getAddVisit(headersTreeMap, mapParameters);
    }

    @Override
    public void setAddVisit(CommentPublishResponse mCommentPublishResponse) {
        try {
            int code = mCommentPublishResponse.getStatus();
            String msg = mCommentPublishResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                //                if (!Utils.isNull(msg)) {
                //                    ToastUtil.showToast(BaseApplication.getInstance(), msg);
                //                }

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

    private void initAddContact() {
        /**
         * user_id:用户id
         * community_id:商户id
         * id:此信息(订单)id
         * star_rating:12345星(传对应数值就行)
         * common_text:内容
         */
        Map<String, Object> mapParameters = new HashMap<>(1);
        mapParameters.put("user_id", PrefUtils.readUserId(BaseApplication.getInstance()));
        mapParameters.put("id", String.valueOf(mId));
        mapParameters.put("community_id", mCommunityId);
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getAddContact(headersTreeMap, mapParameters);
    }

    @Override
    public void setAddContact(CommentPublishResponse mCommentPublishResponse) {
        try {
            int code = mCommentPublishResponse.getStatus();
            String msg = mCommentPublishResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                //                if (!Utils.isNull(msg)) {
                //                    ToastUtil.showToast(BaseApplication.getInstance(), msg);
                //                }
                if (!Utils.isNull(shop_phone)) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    Uri data = Uri.parse("tel:" + shop_phone);
                    intent.setData(data);
                    startActivity(intent);
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

    private void initActionCheckBonus() {
        /**
         * user_id:用户id
         * community_id:商户id
         * id:此信息(订单)id
         * star_rating:12345星(传对应数值就行)
         * common_text:内容
         */
        Map<String, Object> mapParameters = new HashMap<>(1);
        mapParameters.put("user_id", PrefUtils.readUserId(BaseApplication.getInstance()));
        mapParameters.put("id", String.valueOf(mId));
        mapParameters.put("community_id", mCommunityId);
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getActionCheckBonus(headersTreeMap, mapParameters);
    }

    @Override
    public void setActionCheckBonus(CommentPublishResponse mCommentPublishResponse) {
        try {
            int code = mCommentPublishResponse.getStatus();
            String msg = mCommentPublishResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                initWatchBonus();

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

    private void initWatchBonus() {
        /**
         * user_id:用户id
         * community_id:商户id
         * id:此信息(订单)id
         * star_rating:12345星(传对应数值就行)
         * common_text:内容
         */
        Map<String, Object> mapParameters = new HashMap<>(1);
        mapParameters.put("user_id", PrefUtils.readUserId(BaseApplication.getInstance()));
        mapParameters.put("id", String.valueOf(mId));
        mapParameters.put("community_id", mCommunityId);
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getWatchBonus(headersTreeMap, mapParameters);
    }

    @Override
    public void setWatchBonus(CommentPublishResponse mCommentPublishResponse) {
        try {
            int code = mCommentPublishResponse.getStatus();
            String msg = mCommentPublishResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                if (!Utils.isNull(msg)) {
                    ToastUtil.showToast(BaseApplication.getInstance(), msg);
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
    public void setMoreData(CommentListResponse mCommentListResponse) {
        try {
            int code = mCommentListResponse.getStatus();
            String msg = mCommentListResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                LogUtil.e(TAG, "SESSION_ID: " + mCommentListResponse.getData());
                List<CommentListResponse.DataBean> data = mCommentListResponse.getData();
                if (data != null) {

                    for (int i = 0; i < data.size(); i++) {
                        mAdapter.getData().add(data.get(i));
                    }
                    if (data.size() < 10) {
                        mAdapter.setEnableLoadMore(false);
                    } else {
                        mAdapter.setEnableLoadMore(true);
                    }
                } else {
                    mAdapter.setEnableLoadMore(false);
                }


            } else if (code == ResponseCode.SEESION_ERROR) {
                mAdapter.loadMoreComplete();
                //SESSION_ID过期或者报错  要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(mContext);

            } else {
                mAdapter.loadMoreComplete();
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(mContext.getApplicationContext(), msg);
                }

            }


        } catch (Exception e) {
            mAdapter.loadMoreComplete();
            ToastUtil.showToast(mContext.getApplicationContext(), "解析数据失败");
        } finally {
            mAdapter.loadMoreComplete();
        }
    }

    private void initCommentCount() {
        Map<String, Object> mapParameters = new HashMap<>(1);
        mapParameters.put("id", String.valueOf(mId));
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getCommentCount(headersTreeMap, mapParameters);
    }

    @Override
    public void setCommentCount(CommentDetailsResponse mCommentDetailsResponse) {
        try {
            int code = mCommentDetailsResponse.getStatus();
            String msg = mCommentDetailsResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                CommentDetailsResponse.DataBean messageDate = mCommentDetailsResponse.getData();
                if (messageDate != null) {
                    int all_count = messageDate.getAll_count();
                    int good_count = messageDate.getGood_count();
                    int difference_count = messageDate.getDifference_count();
                    int liksestatus = messageDate.getLiksestatus();
                    // liksestatus:1已点,0未点
                    if (liksestatus == 1) {
                        ivCollect.setImageResource(R.drawable.star_icon);
                    } else {
                        ivCollect.setImageResource(R.drawable.shou_cang_icon);
                    }
                    compositionCommentStatus1.setText(String.format("全部%s", String.valueOf(all_count)));
                    compositionCommentStatus2.setText(String.format("好评%s", String.valueOf(good_count)));
                    compositionCommentStatus3.setText(String.format("差评%s", String.valueOf(difference_count)));


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


    //    @OnClick({R2.id.main_title_back, R2.id.item_composition_btn_details, R2.id.item_composition_btn_comment,
    //            R2.id.item_composition_btn_merchant, R2.id.publish_composition_comment_content_btn, R2.id.composition_comment_status_1,
    //            R2.id.composition_comment_status_2, R2.id.composition_comment_status_3, R2.id.item_composition_image_ad})
    //    public void onViewClicked(View view) {
    //        int id = view.getId();
    //        if (id == R.id.main_title_back) {
    //            finish();
    //        } else if (id == R.id.item_composition_btn_details) {
    //
    //        } else if (id == R.id.item_composition_btn_comment) {
    //
    //        } else if (id == R.id.item_composition_btn_merchant) {
    //
    //        } else if (id == R.id.publish_composition_comment_content_btn) {
    //            //            String editItemCompositionCommentContentText = editItemCompositionCommentContent.getText().toString().trim();
    //            //
    //            //            if (Util.isNull(editItemCompositionCommentContentText)) {
    //            //                ToastUtil.showToast(mContext.getApplicationContext(), "请写出您的评价内容");
    //            //                return;
    //            //            }
    //            //            logRefuseData();
    //        } else if (id == R.id.composition_comment_status_1) {
    //
    //        } else if (id == R.id.composition_comment_status_2) {
    //
    //        } else if (id == R.id.composition_comment_status_3) {
    //
    //        } else if (id == R.id.item_composition_image_ad) {
    //            //ad
    //
    //        }
    //    }


    @Override
    public void onLoadMoreRequested() {

        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                page++;
                initMoreData(page, type);
            }
        }, 500);
    }

    //page
    //datetype :时间类型默认----是1(当天)2(本周)3(本月)4(上月)5(近三月)
    //id:角色id  默认0  全部
    private void initMoreData(int page, String type) {

        Map<String, Object> mapParameters = new HashMap<>(3);
        mapParameters.put("page", String.valueOf(page));
        mapParameters.put("id", mId);
        mapParameters.put("type", type);


        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getMoreData(headersTreeMap, mapParameters);
    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                page = 1;
                if (mAdapter != null) {
                    mAdapter.setEnableLoadMore(true);
                }
                initCommentDetailsData(page, type);
                initCommentCount();
                frame.refreshComplete();
            }
        }, 500);
    }


    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @OnClick({R2.id.ll_collect, R2.id.ll_copy_phone_number, R2.id.rl_call_phone, R2.id.main_title_back})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.ll_collect) {
            initCommentLike();
        } else if (id == R.id.ll_copy_phone_number) {
            if (!Utils.isNull(shop_phone)) {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(shop_phone);
                ToastUtil.showToast(BaseApplication.getInstance(), "复制成功!");
            }

        } else if (id == R.id.rl_call_phone) {

            initAddContact();
        } else if (id == R.id.main_title_back) {
            finish();
        }
    }

}

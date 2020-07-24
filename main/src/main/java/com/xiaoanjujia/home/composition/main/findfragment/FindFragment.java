package com.xiaoanjujia.home.composition.main.findfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xiaoanjujia.common.base.BaseFragment;
import com.xiaoanjujia.common.base.baseadapter.BaseQuickAdapter;
import com.xiaoanjujia.common.widget.headerview.JDHeaderView;
import com.xiaoanjujia.common.widget.pulltorefresh.PtrFrameLayout;
import com.xiaoanjujia.common.widget.pulltorefresh.PtrHandler;
import com.xiaoanjujia.home.MainDataManager;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.xiaoanjujia.home.entities.FindsBean;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author xp
 */
public class FindFragment extends BaseFragment implements FindContract.View, PtrHandler, BaseQuickAdapter.RequestLoadMoreListener {
    @Inject
    FindPresenter mPresenter;
    @BindView(R2.id.find_recyclerview)
    RecyclerView findRecyclerview;
    @BindView(R2.id.find_pull_refresh_header)
    JDHeaderView findPullRefreshHeader;
    private FindsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;

    }

    public static FindFragment newInstance() {
        return new FindFragment();
    }

    public void initView() {

        DaggerFindFragmentComponent.builder()
                .appComponent(getAppComponent())
                .findPresenterModule(new FindPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);

        findPullRefreshHeader.setPtrHandler(this);
        findRecyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter = new FindsAdapter(R.layout.item_finds_recyclerview);
        adapter.setOnLoadMoreListener(this);
        adapter.setEnableLoadMore(true);
        findRecyclerview.setAdapter(adapter);



    }

    public void initData() {
        mPresenter.getFindData();
    }


    private static final String TAG = "FindFragment";

    @Override
    public void setFindData(FindsBean find) {
        adapter.addData(find.content);
    }

    @Override
    public void setMoreData(FindsBean find) {
        adapter.getData().addAll(find.content);
        adapter.loadMoreComplete();

    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                frame.refreshComplete();
            }
        }, 2000);

    }
    @Override
    public void onLoadMoreRequested() {
        findRecyclerview.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (adapter.getData().size() >= 90){
                    adapter.loadMoreEnd(false);
                }
                else{
                    mPresenter.getMoreFindData();
                }
            }
        },1000);

    }
}

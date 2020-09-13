package com.xiaoanjujia.home.composition.unlocking.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.sxjs.jd.R;
import com.xiaoanjujia.common.BaseApplication;
import com.xiaoanjujia.common.util.DensityUtils;
import com.xiaoanjujia.home.entities.ChooseYourAreaInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: xp
 * @Date: 2020/7/8 12:45
 * @Description: 首次支付：选择银行卡类型
 */
public class SelectAreaDialog extends DialogFragment {
    private static final String BANK_LIST_KEY = "bankList";
    private static final String MPOSITION = "mPosition";
    private ArrayList<ChooseYourAreaInfo> mBankBeanList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RelativeLayout closeRl;
    private AddSelectAreaAdapter mAdapter;
    private int mPosition;
    private OnDialogListener mlistener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_select_area_layout, null);
        mRecyclerView = view.findViewById(R.id.add_your_bank_account_rv);
        closeRl = view.findViewById(R.id.close);
        Bundle arguments = getArguments();
        String dataResult = arguments.getString(BANK_LIST_KEY);
        mPosition = arguments.getInt(MPOSITION);
        List<ChooseYourAreaInfo> chooseYourAreaInfo = JSON.parseArray(dataResult, ChooseYourAreaInfo.class);
        if (chooseYourAreaInfo != null && chooseYourAreaInfo.size() > 0) {
            for (int i = 0; i < chooseYourAreaInfo.size(); i++) {
                chooseYourAreaInfo.get(i).setItemId(i);
            }
            if (mPosition != -1) {
                chooseYourAreaInfo.get(mPosition).setSelect(true);
            }
            mBankBeanList.clear();
            mBankBeanList.addAll(chooseYourAreaInfo);
        } else {
            mBankBeanList.clear();
        }

        init();
        return view;

    }

    public static SelectAreaDialog newInstance(String dataResult, int position) {
        SelectAreaDialog fragment = new SelectAreaDialog();
        Bundle bundle = new Bundle();
        bundle.putString(BANK_LIST_KEY, dataResult);
        bundle.putInt(MPOSITION, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        //设置背景半透明
        Window window = getDialog().getWindow();
        int screenWidth = DensityUtils.dp2px(BaseApplication.getInstance(), 374);
        int screenHeight = DensityUtils.dp2px(BaseApplication.getInstance(), 320);
        if (window != null) {
            //设置弹出动画
            window.setWindowAnimations(R.style.mypopwindow_anim_style);
            window.setLayout(screenWidth, screenHeight);


            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setGravity(Gravity.BOTTOM);
        }
    }

    public void init() {
        initRecycleView();

        closeRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    private void initRecycleView() {
        if (mAdapter == null) {
            mAdapter = new AddSelectAreaAdapter(getActivity(), nigeriaDepositListOnclickListener, mBankBeanList);
            LinearLayoutManager lineLayoutManager = new LinearLayoutManager(getActivity());
            if (mPosition != -1) {
                mRecyclerView.scrollToPosition(mPosition);
                lineLayoutManager.scrollToPositionWithOffset(mPosition, DensityUtils.dp2px(BaseApplication.getInstance(), 24));
            }
            mRecyclerView.setLayoutManager(lineLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private View.OnClickListener nigeriaDepositListOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.select_bank_layout_ll) {
                ChooseYourAreaInfo rechargeItem1 = (ChooseYourAreaInfo) view.getTag();
                for (ChooseYourAreaInfo o : mBankBeanList) {
                    if (o.getItemId() == rechargeItem1.getItemId()) {
                        o.setSelect(true);
                    } else {
                        o.setSelect(false);
                    }
                }
                initRecycleView();
                String bankCode = rechargeItem1.getIndexCode();
                String bankName = rechargeItem1.getName();
                int position = rechargeItem1.getItemId();
                if (mlistener != null) {
                    mlistener.onItemClick(bankName, bankCode,position);
                }

                //                if (getTargetFragment() == null) {
                //                    return;
                //                }
                //
                //                int position = rechargeItem1.getItemId();
                //                Intent intent = new Intent();
                //                intent.putExtra("bankName", bankName);
                //                intent.putExtra("bankCode", bankCode);
                //                intent.putExtra(MPOSITION, position);
                //                getTargetFragment().onActivityResult(REQUEST_CODE, RESULT_CODE, intent);
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                }, 100);
            }
        }
    };

    public interface OnDialogListener {
        void onItemClick(String bankName, String bankCode,int position);
    }

    public void setOnDialogListener(OnDialogListener dialogListener) {
        this.mlistener = dialogListener;
    }
}

package com.xiaoanjujia.home.composition.unlocking.visitor_invitation;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sxjs.common.R;
import com.xiaoanjujia.common.BaseApplication;
import com.xiaoanjujia.common.base.baseadapter.BaseQuickAdapter;
import com.xiaoanjujia.common.util.DensityUtils;
import com.xiaoanjujia.home.composition.main.community.CommunityGridLayoutManager;

import java.util.Arrays;
import java.util.List;


/**
 * Created on 2020-05-04.<br/>
 * Author: M_DaLing<br/>
 * Email: Null_1024@126.com<br/>
 * Development tools: Android studio<br/>
 * Introduce:
 */
public class PlateNumberDialog extends Dialog {

    private final View.OnClickListener onClickListener;
    private Context mContext;

    private View mView;


    TextView titleTv;
    TextView contentTv;

    private boolean needClose = false;
    private RecyclerView mRview;
    private final List<String> plateList;
    private PlateNumberCallBack mPlateNumberCallBack;

    public PlateNumberDialog(@NonNull Context context, View.OnClickListener onClickListener) {
        super(context);

        mContext = context;
        this.onClickListener = onClickListener;
        mView = LayoutInflater.from(context).inflate(R.layout.dialog_plate_number_deposit, null, false);
        setContentView(mView);

        String[] plateArrays = new String[]{"京", "津", "沪", "渝", "蒙", "新", "藏", "宁", "桂", "港", "澳", "黑"
                , "吉", "辽", "晋", "冀", "青", "鲁", "豫", "苏", "皖",
                "浙", "闽", "赣", "湘", "鄂", "粤", "琼", "甘", "陕", "贵", "云", "川"};

        plateList = Arrays.asList(plateArrays);

        setCancelable(false);
        if (getWindow() != null) {
            getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(BaseApplication.getInstance(), 200));
            getWindow().setGravity(Gravity.BOTTOM);
            WindowManager m = (WindowManager) BaseApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
            Display d = m.getDefaultDisplay();
            WindowManager.LayoutParams p = getWindow().getAttributes();
            p.width = d.getWidth();
            getWindow().setAttributes(p);
        }
        mRview = mView.findViewById(R.id.rl_community_home_page);
        //搜索结果的展示
        CommunityGridLayoutManager manager = new CommunityGridLayoutManager(getContext(), 10, GridLayoutManager.VERTICAL, false);
        mRview.setLayoutManager(manager);
        PlateNumberAdapter mAdapterResult = new PlateNumberAdapter(R.layout.item_plate_number_grald);
        mAdapterResult.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List data = adapter.getData();
                String servicehistoryBean = (String) data.get(position);
                if (mPlateNumberCallBack != null) {
                    mPlateNumberCallBack.jsPlateNumber(servicehistoryBean);
                }
                dismiss();
            }
        });
        mRview.setAdapter(mAdapterResult);
        List<String> dataHistory = mAdapterResult.getData();
        dataHistory.clear();
        mAdapterResult.addData(plateList);


    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitleStr(String title) {
        contentTv.setText(title);
    }

    /**
     * 设置内容
     *
     * @param content
     */
    public void setContentStr(String content) {
        contentTv.setText(content);
    }

    public void okToClose(boolean needClose) {
        this.needClose = needClose;
    }

    /**
     * 更换底部显示dialogView
     */
    public void setButtonColor(int res) {
        if (mView != null) {
            mView.findViewById(R.id.dialog_confirm_ok_atv).setBackgroundResource(res);
        }
    }

    public PlateNumberDialog setJsCallback(PlateNumberCallBack mPlateNumberCallBack) {
        this.mPlateNumberCallBack = mPlateNumberCallBack;
        return this;
    }

    public interface PlateNumberCallBack {


        void jsPlateNumber(String param);


    }
}

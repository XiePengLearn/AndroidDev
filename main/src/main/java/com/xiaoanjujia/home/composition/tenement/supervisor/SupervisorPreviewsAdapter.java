package com.xiaoanjujia.home.composition.tenement.supervisor;

import com.xiaoanjujia.common.base.baseadapter.BaseQuickAdapter;
import com.xiaoanjujia.common.base.baseadapter.BaseViewHolder;
import com.xiaoanjujia.home.entities.PropertyManagementListLogResponse;

/**
 * Created by admin on 2019/9/24.
 */

public class SupervisorPreviewsAdapter extends BaseQuickAdapter<PropertyManagementListLogResponse.DataBean, BaseViewHolder> {

    public SupervisorPreviewsAdapter(int layoutResId) {
        super(layoutResId);
    }


    @Override
    protected void convert(BaseViewHolder helper, final PropertyManagementListLogResponse.DataBean info, int position) {

//        List<String> indication_name = info.getINDICATION_NAME();
//        if (indication_name != null) {
//            if (indication_name.size() > 0) {
//                helper.setText(R.id.first_level_name, indication_name.get(0));
//            }
//            if (indication_name.size() > 1) {
//                helper.setText(R.id.second_level_name, indication_name.get(1));
//            }
//            if (indication_name.size() > 2) {
//                helper.setText(R.id.three_level_name, indication_name.get(2));
//            }
//        }
//
//
//        LinearLayout linearLayout = helper.getView(R.id.zhibiao_value_layout);
//
//        linearLayout.removeAllViews();
//        List<JkxYuPingResponse.DataBean.FILLINITEMSBean> fill_in_items = info.getFILL_IN_ITEMS();
//        if (fill_in_items != null) {
//            for (int i = 0; i < fill_in_items.size(); i++) {
//                View zhibiao_value;
//                if (i == fill_in_items.size() - 1) {
//                    zhibiao_value = View.inflate(mContext, R.layout.jkx_zhibiao_value_item, null);
//                } else {
//                    zhibiao_value = View.inflate(mContext, R.layout.jkx_zhibiao_value_item_no_radius, null);
//                }
//                TextView item_name = zhibiao_value.findViewById(R.id.item_name);
//                item_name.setText(fill_in_items.get(i).getITEM_NAME());
//                TextView item_value = zhibiao_value.findViewById(R.id.item_value);
//                item_value.setText(fill_in_items.get(i).getITEM_VALUE());
//                linearLayout.addView(zhibiao_value);
//            }
//        }
//
//
//        LinearLayout llView = helper.getView(R.id.zhibiao_desc_layout);
//        llView.removeAllViews();
//        List<JkxYuPingResponse.DataBean.REMINDBean> remind = info.getREMIND();
//        if (remind != null) {
//            for (int i = 0; i < remind.size(); i++) {
//                View zhibiao_desc = View.inflate(mContext, R.layout.jkx_zhibiao_desc_item, null);
//                TextView content = zhibiao_desc.findViewById(R.id.content);
//                content.setText((i + 1) + "." + remind.get(i).getITEM_CONTENT());
//                llView.addView(zhibiao_desc);
//            }
//        }


    }


}

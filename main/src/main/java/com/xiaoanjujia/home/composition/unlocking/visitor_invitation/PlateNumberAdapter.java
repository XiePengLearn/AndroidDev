package com.xiaoanjujia.home.composition.unlocking.visitor_invitation;

import com.sxjs.jd.R;
import com.xiaoanjujia.common.base.baseadapter.BaseQuickAdapter;
import com.xiaoanjujia.common.base.baseadapter.BaseViewHolder;
import com.xiaoanjujia.common.widget.bottomnavigation.utils.Utils;

/**
 * Created by admin on 2019/9/24.
 */

public class PlateNumberAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public PlateNumberAdapter(int layoutResId) {
        super(layoutResId);
    }


    @Override
    protected void convert(BaseViewHolder helper, final String info, int position) {

        if (!Utils.isNull(info)) {
            helper.setText(R.id.item_community_content_text, info);
        }


    }


}

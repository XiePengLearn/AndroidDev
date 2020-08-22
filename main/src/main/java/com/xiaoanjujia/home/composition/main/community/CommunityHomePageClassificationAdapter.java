package com.xiaoanjujia.home.composition.main.community;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sxjs.jd.R;
import com.xiaoanjujia.common.base.baseadapter.BaseQuickAdapter;
import com.xiaoanjujia.common.base.baseadapter.BaseViewHolder;
import com.xiaoanjujia.common.widget.bottomnavigation.utils.Utils;
import com.xiaoanjujia.home.entities.ComcateListsResponse;

/**
 * Created by admin on 2019/9/24.
 */

public class CommunityHomePageClassificationAdapter extends BaseQuickAdapter<ComcateListsResponse.DataBean, BaseViewHolder> {

    public CommunityHomePageClassificationAdapter(int layoutResId) {
        super(layoutResId);
    }


    @Override
    protected void convert(BaseViewHolder helper, final ComcateListsResponse.DataBean info, int position) {

        String abnormal_text = info.getCate_name();
        if (!Utils.isNull(abnormal_text)) {
            helper.setText(R.id.item_community_content_text, abnormal_text);
        }

        RequestOptions options = new RequestOptions()
                .error(R.drawable.ic_launcher);
        //头像
        Glide.with(mContext)
                .load(info.getCate_iimg_url())
                .apply(options)
                .into((ImageView) helper.getView(R.id.item_community_image));


    }


}

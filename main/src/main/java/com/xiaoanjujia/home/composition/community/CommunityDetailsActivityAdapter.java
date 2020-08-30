package com.xiaoanjujia.home.composition.community;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sxjs.jd.R;
import com.xiaoanjujia.common.base.baseadapter.BaseQuickAdapter;
import com.xiaoanjujia.common.base.baseadapter.BaseViewHolder;
import com.xiaoanjujia.common.widget.bottomnavigation.utils.Utils;
import com.xiaoanjujia.home.entities.CommentListResponse;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by admin on 2019/9/24.
 */

public class CommunityDetailsActivityAdapter extends BaseQuickAdapter<CommentListResponse.DataBean, BaseViewHolder> {

    public CommunityDetailsActivityAdapter(int layoutResId) {
        super(layoutResId);
    }


    @Override
    protected void convert(BaseViewHolder helper, final CommentListResponse.DataBean info, int position) {

        int star_rating = info.getStar_rating();
        MaterialRatingBar materialRatingBar = helper.getView(R.id.library_tinted_normal_ratingbar);
        materialRatingBar.setNumStars(star_rating);
        String content = info.getContent();
        if (!Utils.isNull(content)) {
            helper.setText(R.id.item_com_text, content);
        }

        String create_time = info.getCreate_time();
        if (!Utils.isNull(create_time)) {
            helper.setText(R.id.item_create_time, create_time);
        }

        RequestOptions options = new RequestOptions()
                .error(R.drawable.default_icon);
        //头像
        Glide.with(mContext)
                .load(info.getComment_img())
                .apply(options)
                .into((ImageView) helper.getView(R.id.item_com_image_one));

    }


}

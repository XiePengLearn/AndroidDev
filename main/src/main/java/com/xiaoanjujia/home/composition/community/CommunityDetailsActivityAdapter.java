package com.xiaoanjujia.home.composition.community;

import com.xiaoanjujia.common.base.baseadapter.BaseQuickAdapter;
import com.xiaoanjujia.common.base.baseadapter.BaseViewHolder;
import com.xiaoanjujia.home.entities.CommentDetailsResponse;

/**
 * Created by admin on 2019/9/24.
 */

public class CommunityDetailsActivityAdapter extends BaseQuickAdapter<CommentDetailsResponse.DataBean.AllCommentsBean, BaseViewHolder> {

    public CommunityDetailsActivityAdapter(int layoutResId) {
        super(layoutResId);
    }


    @Override
    protected void convert(BaseViewHolder helper, final CommentDetailsResponse.DataBean.AllCommentsBean info, int position) {

//        String title = info.getTitle();
//        if (!Utils.isNull(title)) {
//            helper.setText(R.id.item_supervisor_content_text, title);
//        }
//        int count = info.getCount();
//        helper.setText(R.id.comment_count_tv, String.format("%s" + "条评论", String.valueOf(count)));
//
//        String single_money = info.getSingle_money();
//        AlphaButton alphaButton = helper.getView(R.id.item_item_community_btn_2);
//        if (!Utils.isNull(single_money)) {
//            double doubleMoney = Double.parseDouble(single_money);
//            if (doubleMoney != 0.0) {
//                alphaButton.setVisibility(View.VISIBLE);
//            } else {
//                alphaButton.setVisibility(View.GONE);
//            }
//        } else {
//            alphaButton.setVisibility(View.GONE);
//        }
//
//        RequestOptions options = new RequestOptions()
//                .error(R.drawable.ic_launcher);
//        //头像
//        Glide.with(mContext)
//                .load(info.getShow_img())
//                .apply(options)
//                .into((ImageView) helper.getView(R.id.item_supervisor_image_one));

    }


}

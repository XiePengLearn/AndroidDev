package com.xiaoanjujia.home.composition.main.community;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sxjs.jd.R;
import com.xiaoanjujia.common.base.baseadapter.BaseQuickAdapter;
import com.xiaoanjujia.common.base.baseadapter.BaseViewHolder;
import com.xiaoanjujia.common.widget.bottomnavigation.utils.Utils;
import com.xiaoanjujia.home.entities.CommunitySearchResponse;

/**
 * Created by admin on 2019/9/24.
 */

public class CommunityFragmentAdapter extends BaseQuickAdapter<CommunitySearchResponse.DataBean, BaseViewHolder> {

    public CommunityFragmentAdapter(int layoutResId) {
        super(layoutResId);
    }


    @Override
    protected void convert(BaseViewHolder helper, final CommunitySearchResponse.DataBean info, int position) {

        String title = info.getTitle();
        if (!Utils.isNull(title)) {
            helper.setText(R.id.item_supervisor_content_text, title);
        }
        int count = info.getCount();
        helper.setText(R.id.comment_count_tv, String.format("%s" + "条评论", String.valueOf(count)));


        RequestOptions options = new RequestOptions()
                .error(R.drawable.ic_launcher);
        //头像
        Glide.with(mContext)
                .load(info.getShow_img())
                .apply(options)
                .into((ImageView) helper.getView(R.id.item_supervisor_image_one));
//
//        //设置子View的点击事件
//        helper.addOnClickListener(R.id.item_supervisor_btn_status);
//        //examinestatus:0是未审核1是通过2被拒绝
//        int examinestatus = info.getExaminestatus();
//        if (examinestatus == 1) {
//            helper.setText(R.id.item_supervisor_btn_status, "审核通过");
//        } else if (examinestatus == 2) {
//            helper.setText(R.id.item_supervisor_btn_status, "审核已拒绝");
//        } else {
//            helper.setText(R.id.item_supervisor_btn_status, "未审核");
//        }

    }


}

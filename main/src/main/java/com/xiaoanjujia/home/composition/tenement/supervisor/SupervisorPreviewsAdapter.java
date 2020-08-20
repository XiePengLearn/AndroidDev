package com.xiaoanjujia.home.composition.tenement.supervisor;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sxjs.jd.R;
import com.xiaoanjujia.common.base.baseadapter.BaseQuickAdapter;
import com.xiaoanjujia.common.base.baseadapter.BaseViewHolder;
import com.xiaoanjujia.common.widget.bottomnavigation.utils.Utils;
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

        String abnormal_text = info.getAbnormal_text();
        if(!Utils.isNull(abnormal_text)){
            helper.setText(R.id.item_supervisor_content_text, abnormal_text);
        }
        String create_time = info.getCreate_time();
        if(!Utils.isNull(create_time)){
            helper.setText(R.id.item_supervisor_time_date, create_time);
        }
        String week = info.getWeek();
        if(!Utils.isNull(week)){
            helper.setText(R.id.item_supervisor_week_date, week);
        }
        String publishName = info.getName();
        if(!Utils.isNull(publishName)){
            helper.setText(R.id.item_supervisor_publisher, publishName);
        }

        RequestOptions options = new RequestOptions()
                .error(R.drawable.ic_launcher);
        //头像
        Glide.with(mContext)
                .load(info.getLog_imgs())
                .apply(options)
                .into((ImageView) helper.getView(R.id.item_supervisor_image_one));

        //设置子View的点击事件
        helper.addOnClickListener(R.id.item_supervisor_btn_status);



    }


}

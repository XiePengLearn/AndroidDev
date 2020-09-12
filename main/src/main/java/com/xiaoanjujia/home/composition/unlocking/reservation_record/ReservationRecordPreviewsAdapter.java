package com.xiaoanjujia.home.composition.unlocking.reservation_record;

import android.widget.ImageView;

import com.sxjs.jd.R;
import com.xiaoanjujia.common.BaseApplication;
import com.xiaoanjujia.common.base.baseadapter.BaseQuickAdapter;
import com.xiaoanjujia.common.base.baseadapter.BaseViewHolder;
import com.xiaoanjujia.common.widget.alphaview.AlphaButton;
import com.xiaoanjujia.common.widget.bottomnavigation.utils.Utils;
import com.xiaoanjujia.home.entities.AppointmentRecordsResponse;

/**
 * Created by admin on 2019/9/24.
 */

public class ReservationRecordPreviewsAdapter extends BaseQuickAdapter<AppointmentRecordsResponse.DataBean, BaseViewHolder> {

    public ReservationRecordPreviewsAdapter(int layoutResId) {
        super(layoutResId);
    }


    @Override
    protected void convert(BaseViewHolder helper, final AppointmentRecordsResponse.DataBean info, int position) {


        String visitorName = info.getVisitorName();
        if (!Utils.isNull(visitorName)) {
            helper.setText(R.id.huzhu_tv, visitorName);
        }

        ImageView sexImg = helper.getView(R.id.sex_img);
        int gender = info.getGender();
        //++gender number False 性别，1：男；2：女；0：未知
        if (gender == 1) {
            //1：男
            sexImg.setImageDrawable(BaseApplication.getInstance().getResources().getDrawable(R.drawable.sex_man));
        } else if (gender == 2) {
            //2：女
            sexImg.setImageDrawable(BaseApplication.getInstance().getResources().getDrawable(R.drawable.sex_woman));
        } else {
            //0：未知
            sexImg.setImageDrawable(BaseApplication.getInstance().getResources().getDrawable(R.drawable.sex_weizhi));
        }
        //访客状态,
        //0:待审核、
        //1：正常、
        //2：迟到、
        //3：失效、
        //4：审核退回、
        //9：审核失效、
        //10：邀约中、
        //11:邀约失效

        //        5：超期自动签离
        //        6：已签离
        //        7：超期未签离
        //        8：已到达
        AlphaButton mAlphaButton = helper.getView(R.id.item_vis_btn_status);
        int visitorStatus = info.getVisitorStatus();
        if (visitorStatus == 0) {
            mAlphaButton.setText("待审核");
        } else if (visitorStatus == 1) {
            mAlphaButton.setText("正常");
        } else if (visitorStatus == 2) {
            mAlphaButton.setText("迟到");
        } else if (visitorStatus == 3) {
            mAlphaButton.setText("失效");
        } else if (visitorStatus == 4) {
            mAlphaButton.setText("审核退回");
        } else if (visitorStatus == 5) {
            mAlphaButton.setText("超期自动签离");
        } else if (visitorStatus == 6) {
            mAlphaButton.setText("已签离");
        } else if (visitorStatus == 7) {
            mAlphaButton.setText("超期未签离");
        } else if (visitorStatus == 8) {
            mAlphaButton.setText("已到达");
        } else if (visitorStatus == 9) {
            mAlphaButton.setText("审核失效");
        } else if (visitorStatus == 10) {
            mAlphaButton.setText("邀约中");
        } else if (visitorStatus == 11) {
            mAlphaButton.setText("邀约失效");
        }

        /**
         * "gender": 1,
         * 				"nation": 1,
         * 				"orderId": "1aec4686-f2a2-11ea-97aa-57c205138e5a",
         * 				"plateNo": "蒙5487248",
         * 				"visitStartTime": "2020-09-09T21:37:20+08:00",
         * 				"visitPurpose": "看看老朋友",
         * 				"customa": "",
         * 				"certificateNo": "411425199101166030",
         * 				"phoneNo": "15601267550",
         * 				"visitEndTime": "2020-09-09T23:37:20+08:00",
         * 				"verificationCode": "5842",
         * 				"privilegeGroupNames": ["访客权限门禁点"],
         * 				"visitorName": "谢鹏",
         * 				"QRCode": "VjAwMSuVfWabcSTy0lfNQ+WycCR1dCeucyLzvcT6Bo4+P219he7TdWhHvL5q4r5vSb6EoFs=",
         * 				"receptionistName": "老谢",
         * 				"appointRecordId": "911d01e7c684467ea84bb5ad9ee57c2f",
         * 				"receptionistCode": "01101002010010100102",
         * 				"visitorStatus": 3,
         * 				"receptionistId": "77edf8446c7c448e914caf14eeaf9f20",
         * 				"certificateType": 111,
         * 				"visitorId": "1aec4686-f2a2-11ea-97aa-57c205138e5a"
         */
        String visitStartTime = info.getVisitStartTime();
        String visitEndTime = info.getVisitEndTime();
        String phoneNo = info.getPhoneNo();
        String plateNo = info.getPlateNo();

        if (!Utils.isNull(visitStartTime) && !Utils.isNull(visitEndTime)) {

            String timeDateTextStare = "";
            String timeDateTextEnd = "";
            if (visitStartTime.contains("+")) {
                String[] split = visitStartTime.split("\\+");
                String timeDate = split[0];
                if (timeDate.contains("T")) {
                    String[] ts = timeDate.split("T");
                    timeDateTextStare = ts[0] + "  " + ts[1];
                } else {
                    timeDateTextStare = timeDate;
                }
            } else {
                timeDateTextStare = visitStartTime;
            }

            if (visitEndTime.contains("+")) {
                String[] split = visitEndTime.split("\\+");
                String timeDate = split[0];
                if (timeDate.contains("T")) {
                    String[] ts = timeDate.split("T");
                    timeDateTextEnd = ts[0] + "  " + ts[1];
                } else {
                    timeDateTextEnd = timeDate;
                }
            } else {
                timeDateTextEnd = visitStartTime;
                //                2020-08-25  18:45 ~ 2020-08-26  18:45
            }
            helper.setText(R.id.lai_fang_time_tv, timeDateTextStare + " ~ " + timeDateTextEnd);
        }

        if (!Utils.isNull(phoneNo)) {
            helper.setText(R.id.id_phone_tv, phoneNo);
        }

        if (!Utils.isNull(plateNo)) {
            helper.setText(R.id.id_car_number_tv, plateNo);
        }

    }


}

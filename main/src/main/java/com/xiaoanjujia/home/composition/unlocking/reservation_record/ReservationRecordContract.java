package com.xiaoanjujia.home.composition.unlocking.reservation_record;


import com.xiaoanjujia.home.entities.AppointmentRecordsResponse;
import com.xiaoanjujia.home.entities.VisitingRecordsResponse;

import java.util.Map;
import java.util.TreeMap;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */
public interface ReservationRecordContract {

    interface View {


        void setResponseData(AppointmentRecordsResponse mAppointmentRecordsResponse);

        void setMoreData(AppointmentRecordsResponse mAppointmentRecordsResponse);

        void setLaiFangData(VisitingRecordsResponse mVisitingRecordsResponse);

        void setLaiFangMoreData(VisitingRecordsResponse mVisitingRecordsResponse);


        void showProgressDialogView();

        void hiddenProgressDialogView();
    }

    interface Presenter {


        void destory();

        void saveData();

        Map getData();

        void getRequestData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);

        void getMoreData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);

        void getLaiFangData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);

        void getLaiFangMoreData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);
    }
}

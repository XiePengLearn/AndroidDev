package com.xiaoanjujia.home.composition.unlocking.house_manager;

import com.sxjs.jd.R;
import com.xiaoanjujia.common.base.baseadapter.BaseQuickAdapter;
import com.xiaoanjujia.common.base.baseadapter.BaseViewHolder;
import com.xiaoanjujia.common.widget.bottomnavigation.utils.Utils;
import com.xiaoanjujia.home.entities.VisitorPersonInfoResponse;

/**
 * Created by admin on 2019/9/24.
 */

public class HouseManagerPreviewsAdapter extends BaseQuickAdapter<VisitorPersonInfoResponse.DataBean, BaseViewHolder> {

    public HouseManagerPreviewsAdapter(int layoutResId) {
        super(layoutResId);
    }


    @Override
    protected void convert(BaseViewHolder helper, final VisitorPersonInfoResponse.DataBean info, int position) {

        //"marriaged": 0,
        //		"orgName": "2户室",
        //		"gender": 1,
        //		"orgPath": "@root000000@67e12695-c84a-4237-8cc8-7704fa180e89@27891eaa-03fa-40da-9fce-47c3d19ecc69@83237a94-2226-42bd-9c21-adfe380699dd@e01dae84-10fc-4f21-8863-acac68ea41ae@f2c46644-fcae-441c-831c-196362f4e36b@d1e64845-9083-4c37-8bdd-a2a7eead2a09@",
        //		"syncFlag": 0,
        //		"orgPathName": "铭嘉地产/张营小区三栋楼/1管理分区/14号楼/1单元/1楼层/2户室",
        //		"orgIndexCode": "d1e64845-9083-4c37-8bdd-a2a7eead2a09",
        //		"orgList": ["d1e64845-9083-4c37-8bdd-a2a7eead2a09"],
        //		"updateTime": "2020-09-12T07:10:10.371+08:00",
        //		"certificateNo": "1411251999912354562",
        //		"phoneNo": "15601267550",
        //		"personName": "老谢",


        String personName = info.getPersonName();
        if (!Utils.isNull(personName)) {
            helper.setText(R.id.huzhu_tv, personName);
        } else {
            helper.setText(R.id.huzhu_tv, "  -  ");
        }
        //        String orgName = info.getOrgName();
        //        if (!Utils.isNull(orgName)) {
        //            helper.setText(R.id.xiao_qu_tv, orgName);
        //        } else {
        //            helper.setText(R.id.xiao_qu_tv, "  -  ");
        //        }
        String orgPathName = info.getOrgPathName();

        if (!Utils.isNull(orgPathName)) {
            if (orgPathName.contains("/")) {
                String[] split = orgPathName.split("/");
                if (split.length >= 2) {
                    helper.setText(R.id.xiao_qu_tv, split[0]);
                    StringBuffer buffer = new StringBuffer();

                    for (int i = 0; i < split.length; i++) {
                        if (i != 0) {
                            buffer.append(split[i]);
                        }
                    }
                    helper.setText(R.id.id_address_tv, buffer);
                } else {
                    helper.setText(R.id.id_address_tv, orgPathName);
                }
            } else {
                helper.setText(R.id.id_address_tv, orgPathName);
            }

        } else {
            helper.setText(R.id.id_address_tv, "  -  ");
        }

    }


}

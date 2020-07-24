package com.xiaoanjujia.common.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.GlobalAppComponent;
import com.xiaoanjujia.common.model.DataManager;
import com.xiaoanjujia.common.util.DialogUtil;

import butterknife.Unbinder;

/**
 * Created by xp on 2017/3/15.
 */

public class BaseFragment extends Fragment{
    protected Activity mActivity;
    protected Unbinder unbinder;
    protected Context mContext;

    protected DataManager mDataManager;
    /**
     * gif_logo进度dialog
     */
    private Dialog dialog;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
        mContext = getAppComponent().getContext();
        mDataManager = getAppComponent().getDataManager();
    }

    protected void showShortToast(String message){
        Toast.makeText(mActivity.getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

    protected void showLongToast(String message){
        Toast.makeText(mActivity.getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }

    protected AppComponent getAppComponent() {
        return GlobalAppComponent.getAppComponent();
    }

    protected void showJDLoadingDialog(){
        if(dialog == null)dialog = DialogUtil.createJDLoadingDialog(mActivity, null);
        if(!dialog.isShowing()){
            dialog.show();
        }
    }

    protected void hideJDLoadingDialog(){
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(unbinder != null){
            unbinder.unbind();
        }
        if(dialog != null){
            if(dialog.isShowing())dialog.dismiss();
            dialog = null;
        }
    }
}

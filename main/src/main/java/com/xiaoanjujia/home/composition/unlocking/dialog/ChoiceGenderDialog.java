package com.xiaoanjujia.home.composition.unlocking.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.sxjs.jd.R;
import com.xiaoanjujia.common.widget.bottomnavigation.utils.Utils;


/**
 */
public class ChoiceGenderDialog extends Dialog {

    private Context mContext;

    private View mView;

    private View.OnClickListener onClickListener;

    private GenderCallback genderCallback;
    private LinearLayout allLl;

    public static final int MALE = 0;
    public static final int FEMALE = 1;

    public int selectGender;

    private ImageView maleSelectIv;
    private ImageView femaleSelectIv;

    public ChoiceGenderDialog(@NonNull Context context, View.OnClickListener onClickListener, GenderCallback genderCallback1) {
        super(context);
        mContext = context;
        this.onClickListener = onClickListener;
        this.genderCallback = genderCallback1;
        mView = LayoutInflater.from(context).inflate(R.layout.dialog_user_info_choice_gender, null, false);
        setContentView(mView);
        allLl = mView.findViewById(R.id.dialog_all_ll);

        setCancelable(true);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.BOTTOM);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) allLl.getLayoutParams();
        layoutParams.setMargins(0,0,0, Utils.getVirtualBarHeight(mContext));
        allLl.setLayoutParams(layoutParams);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getWindow().setWindowAnimations(R.style.BottomDialogAnim);
        maleSelectIv = mView.findViewById(R.id.dialog_choice_male_select_iv);
        femaleSelectIv = mView.findViewById(R.id.dialog_choice_female_select_iv);
        mView.findViewById(R.id.dialog_choice_male_rl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maleSelectIv.setImageResource(R.drawable.shape_change_region_checked);
                femaleSelectIv.setImageResource(R.drawable.shape_change_region_no_checked);
                genderCallback.genderSelect(MALE);
                selectGender = MALE;
            }
        });

        mView.findViewById(R.id.dialog_choice_female_rl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maleSelectIv.setImageResource(R.drawable.shape_change_region_no_checked);
                femaleSelectIv.setImageResource(R.drawable.shape_change_region_checked);
                genderCallback.genderSelect(FEMALE);
                selectGender = FEMALE;
            }
        });
    }


    public void selectGender(int genderType){
        if (genderType == MALE){
            maleSelectIv.setImageResource(R.drawable.shape_change_region_checked);
            femaleSelectIv.setImageResource(R.drawable.shape_change_region_no_checked);
            selectGender = genderType;
        }else if (genderType == FEMALE){
            maleSelectIv.setImageResource(R.drawable.shape_change_region_no_checked);
            femaleSelectIv.setImageResource(R.drawable.shape_change_region_checked);
            selectGender = genderType;
        }else {
            maleSelectIv.setImageResource(R.drawable.shape_change_region_no_checked);
            femaleSelectIv.setImageResource(R.drawable.shape_change_region_no_checked);
            selectGender = genderType;
        }
    }

    public void saveSuccess(){
        genderCallback.saveGenderSuccess(selectGender);
    }

    public interface GenderCallback {
        void genderSelect(int gender);

        void saveGenderSuccess(int gender);
    }
}

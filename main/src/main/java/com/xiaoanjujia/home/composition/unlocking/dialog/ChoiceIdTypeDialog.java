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
 *
 */
public class ChoiceIdTypeDialog extends Dialog {

    private Context mContext;

    private View mView;


    private IncidentCallback genderCallback;
    private LinearLayout allLl;

    public static final int INCIDENT_ELSE = 0;
    public static final int INCIDENT_WORK = 1;
    public static final int INCIDENT_SEE = 2;
    public static final int INCIDENT_EXPRESS = 3;

    public int selectIncident;

    private ImageView elseSelectIv;
    private ImageView workSelectIv;
    private ImageView seeSelectIv;
    private ImageView expressSelectIv;

    public ChoiceIdTypeDialog(@NonNull Context context, IncidentCallback genderCallback1) {
        super(context);
        mContext = context;
        this.genderCallback = genderCallback1;
        mView = LayoutInflater.from(context).inflate(R.layout.dialog_user_info_choice_id_type, null, false);
        setContentView(mView);
        allLl = mView.findViewById(R.id.dialog_all_ll);

        setCancelable(true);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.BOTTOM);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) allLl.getLayoutParams();
        layoutParams.setMargins(0, 0, 0, Utils.getVirtualBarHeight(mContext));
        allLl.setLayoutParams(layoutParams);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getWindow().setWindowAnimations(R.style.BottomDialogAnim);

        elseSelectIv = mView.findViewById(R.id.dialog_choice_else_select_iv);
        workSelectIv = mView.findViewById(R.id.dialog_choice_work_select_iv);
        seeSelectIv = mView.findViewById(R.id.dialog_choice_see_select_iv);
        expressSelectIv = mView.findViewById(R.id.dialog_choice_express_select_iv);

        workSelectIv = mView.findViewById(R.id.dialog_choice_work_select_iv);
        workSelectIv = mView.findViewById(R.id.dialog_choice_work_select_iv);
        workSelectIv = mView.findViewById(R.id.dialog_choice_work_select_iv);
        mView.findViewById(R.id.dialog_choice_else_rl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choiceElseMethod(R.drawable.shape_change_region_checked, R.drawable.shape_change_region_no_checked, R.drawable.shape_change_region_no_checked, R.drawable.shape_change_region_no_checked);

                genderCallback.genderSelect(INCIDENT_ELSE);
                selectIncident = INCIDENT_ELSE;
            }
        });

        mView.findViewById(R.id.dialog_choice_work_rl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choiceElseMethod(R.drawable.shape_change_region_no_checked, R.drawable.shape_change_region_checked, R.drawable.shape_change_region_no_checked, R.drawable.shape_change_region_no_checked);
                genderCallback.genderSelect(INCIDENT_WORK);
                selectIncident = INCIDENT_WORK;
            }
        });
        mView.findViewById(R.id.dialog_choice_see_rl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choiceElseMethod(R.drawable.shape_change_region_no_checked, R.drawable.shape_change_region_no_checked, R.drawable.shape_change_region_checked, R.drawable.shape_change_region_no_checked);
                genderCallback.genderSelect(INCIDENT_SEE);
                selectIncident = INCIDENT_SEE;
            }
        });

        mView.findViewById(R.id.dialog_choice_express_rl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choiceElseMethod(R.drawable.shape_change_region_no_checked, R.drawable.shape_change_region_no_checked, R.drawable.shape_change_region_no_checked, R.drawable.shape_change_region_no_checked);
                genderCallback.genderSelect(INCIDENT_EXPRESS);
                selectIncident = INCIDENT_EXPRESS;
            }
        });
    }

    private void choiceElseMethod(int p, int p2, int p3, int p5) {
        elseSelectIv.setImageResource(p);
        workSelectIv.setImageResource(p2);
        seeSelectIv.setImageResource(p3);
        expressSelectIv.setImageResource(p5);
    }

    public void selectGender(int genderType) {
        if (genderType == INCIDENT_ELSE) {
            choiceElseMethod(R.drawable.shape_change_region_checked, R.drawable.shape_change_region_no_checked, R.drawable.shape_change_region_no_checked, R.drawable.shape_change_region_no_checked);

            selectIncident = genderType;
        } else if (genderType == INCIDENT_WORK) {
            choiceElseMethod(R.drawable.shape_change_region_no_checked, R.drawable.shape_change_region_checked, R.drawable.shape_change_region_no_checked, R.drawable.shape_change_region_no_checked);
            selectIncident = genderType;
        } else if (genderType == INCIDENT_SEE) {
            choiceElseMethod(R.drawable.shape_change_region_no_checked, R.drawable.shape_change_region_no_checked, R.drawable.shape_change_region_checked, R.drawable.shape_change_region_no_checked);
            selectIncident = genderType;
        } else if (genderType == INCIDENT_EXPRESS) {
            choiceElseMethod(R.drawable.shape_change_region_no_checked, R.drawable.shape_change_region_no_checked, R.drawable.shape_change_region_no_checked, R.drawable.shape_change_region_no_checked);
            selectIncident = genderType;
        } else {
            choiceElseMethod(R.drawable.shape_change_region_no_checked, R.drawable.shape_change_region_no_checked, R.drawable.shape_change_region_no_checked, R.drawable.shape_change_region_no_checked);
            selectIncident = genderType;
        }
    }

    public void saveSuccess() {
        genderCallback.saveGenderSuccess(selectIncident);
    }

    public interface IncidentCallback {
        void genderSelect(int numIncident);

        void saveGenderSuccess(int numIncident);
    }
}

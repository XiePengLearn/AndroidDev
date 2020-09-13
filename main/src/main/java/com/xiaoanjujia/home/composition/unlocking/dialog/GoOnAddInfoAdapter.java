package com.xiaoanjujia.home.composition.unlocking.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sxjs.jd.R;
import com.xiaoanjujia.common.widget.bottomnavigation.utils.Utils;
import com.xiaoanjujia.home.entities.ChooseYourAreaInfo;

import java.util.List;

/**
 * @Auther: xp
 * @Date: 2020/6/23 14:23
 * @Description:
 */
public class GoOnAddInfoAdapter extends RecyclerView.Adapter<GoOnAddInfoAdapter.ViewHolder> {

    private Context mContext;

    private View.OnClickListener onClickListener;

    private List<ChooseYourAreaInfo> mListData;

    public GoOnAddInfoAdapter(Context mContext,
                              View.OnClickListener onClickListener,
                              List<ChooseYourAreaInfo> mListData) {
        this.mContext = mContext;
        this.onClickListener = onClickListener;
        this.mListData = mListData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_nigeria_add_select_area, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ChooseYourAreaInfo chooseYourCardListInfo = (ChooseYourAreaInfo) mListData.get(position);
        holder.selectBankLayoutLl.setTag(chooseYourCardListInfo);
        holder.selectBankNameTv.setText(Utils.isNull(chooseYourCardListInfo.getName()) ? "" : chooseYourCardListInfo.getName());
        if (chooseYourCardListInfo.isSelect()) {
            holder.selectBankCheckIv.setBackgroundResource(R.drawable.shape_change_region_checked);
        } else {
            holder.selectBankCheckIv.setBackgroundResource(R.drawable.shape_change_region_no_checked);
        }
        if (position == mListData.size() - 1) {
            holder.mSelectBankLineView.setVisibility(View.INVISIBLE);
        } else {
            holder.mSelectBankLineView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if (mListData != null) {
            return mListData.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * 总体rl
         */
        LinearLayout selectBankLayoutLl;
        /**
         * Bnak list icon
         */
        ImageView    selectBankIconIv;

        /**
         * Bank name
         */
        TextView selectBankNameTv;

        /**
         * 选中标记
         */
        ImageView selectBankCheckIv;
        View      mSelectBankLineView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            selectBankLayoutLl = itemView.findViewById(R.id.select_bank_layout_ll);
            selectBankIconIv = itemView.findViewById(R.id.select_bank_icon_iv);
            selectBankNameTv = itemView.findViewById(R.id.select_bank_name_tv);
            selectBankCheckIv = itemView.findViewById(R.id.select_bank_check_iv);
            mSelectBankLineView = itemView.findViewById(R.id.select_bank_line_view);
            selectBankLayoutLl.setOnClickListener(onClickListener);
        }
    }
}

package com.xiaoanjujia.home.composition.main.homefragment;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author：xp on 2017/4/7 10:43.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private int firstPosition = -1;
    private GridLayoutManager.SpanSizeLookup spanSizeLookup;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if(spanSizeLookup == null){
            GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
            spanSizeLookup = layoutManager.getSpanSizeLookup();
        }
        int spanSize = spanSizeLookup.getSpanSize(parent.getChildLayoutPosition(view));

        if( spanSize == 2){
            if(firstPosition == -1){
                firstPosition = parent.getChildLayoutPosition(view);
            }
            if((parent.getChildLayoutPosition(view) - firstPosition) % 2 == 0){
                outRect.right = space/2;
                outRect.top = space;
            }
            else{
                outRect.left = space/2;
                outRect.top = space;
            }

        }

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }
}

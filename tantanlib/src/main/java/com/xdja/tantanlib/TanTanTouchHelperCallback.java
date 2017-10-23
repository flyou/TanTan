package com.xdja.tantanlib;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.List;


public class TanTanTouchHelperCallback<T> extends ItemTouchHelper.Callback {

    private final RecyclerView.Adapter adapter;
    private List<T> dataList;
    private TanTanOnSwipeListener<T> mListener;

    public TanTanTouchHelperCallback(@NonNull RecyclerView.Adapter adapter, @NonNull List<T> dataList) {
        this.adapter = checkIsNull(adapter);
        this.dataList = checkIsNull(dataList);
    }

    public TanTanTouchHelperCallback(@NonNull RecyclerView.Adapter adapter, @NonNull List<T> dataList, TanTanOnSwipeListener<T> listener) {
        this.adapter = checkIsNull(adapter);
        this.dataList = checkIsNull(dataList);
        this.mListener = listener;
    }

    private <T> T checkIsNull(T t) {
        if (t == null) {
            throw new NullPointerException();
        }
        return t;
    }

    public void setOnSwipedListener(TanTanOnSwipeListener<T> mListener) {
        this.mListener = mListener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = 0;
        int swipeFlags = 0;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof TanTanLayoutManager) {
            swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        }
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }



    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        viewHolder.itemView.setOnTouchListener(null);
        int layoutPosition = viewHolder.getLayoutPosition();
        T remove = dataList.remove(layoutPosition);
        adapter.notifyDataSetChanged();
        if (mListener != null) {
            mListener.onItemSwiped(viewHolder, remove, direction == ItemTouchHelper.LEFT ? TanTanConfig.SWIPED_LEFT : TanTanConfig.SWIPED_RIGHT);
        }
        if (adapter.getItemCount() == 0) {
            if (mListener != null) {
                mListener.onSwipedOver();
            }
        }
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        View itemView = viewHolder.itemView;
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            float ratio = dX / getThreshold(recyclerView, viewHolder);
            // ratio 最大为 1 或 -1
            if (ratio > 1) {
                ratio = 1;
            } else if (ratio < -1) {
                ratio = -1;
            }
            itemView.setRotation(ratio * TanTanConfig.DEFAULT_ROTATE_DEGREE);
            int childCount = recyclerView.getChildCount();
            // 当数据源个数大于最大显示数时
            if (childCount > TanTanConfig.DEFAULT_SHOW_ITEM) {
                for (int position = 1; position < childCount - 1; position++) {
                    int index = childCount - position - 1;
                    View view = recyclerView.getChildAt(position);
                    view.setScaleX(1 - index * TanTanConfig.DEFAULT_SCALE + Math.abs(ratio) * TanTanConfig.DEFAULT_SCALE);
                    view.setScaleY(1 - index * TanTanConfig.DEFAULT_SCALE + Math.abs(ratio) * TanTanConfig.DEFAULT_SCALE);
                    view.setTranslationY((index - Math.abs(ratio)) * itemView.getMeasuredHeight() / TanTanConfig.DEFAULT_TRANSLATE_Y);
                }
            } else {
                // 当数据源个数小于或等于最大显示数时
                for (int position = 0; position < childCount - 1; position++) {
                    int index = childCount - position - 1;
                    View view = recyclerView.getChildAt(position);
                    view.setScaleX(1 - index * TanTanConfig.DEFAULT_SCALE + Math.abs(ratio) * TanTanConfig.DEFAULT_SCALE);
                    view.setScaleY(1 - index * TanTanConfig.DEFAULT_SCALE + Math.abs(ratio) * TanTanConfig.DEFAULT_SCALE);
                    view.setTranslationY((index - Math.abs(ratio)) * itemView.getMeasuredHeight() / TanTanConfig.DEFAULT_TRANSLATE_Y);
                }
            }
            if (mListener != null) {
                if (ratio != 0) {
                    mListener.onItemSwiping(viewHolder, ratio, ratio < 0 ? TanTanConfig.SWIPING_LEFT : TanTanConfig.SWIPING_RIGHT);
                } else {
                    mListener.onItemSwiping(viewHolder, ratio, TanTanConfig.SWIPING_NONE);
                }
            }
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setRotation(0f);
    }

    private float getThreshold(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return recyclerView.getWidth() * getSwipeThreshold(viewHolder);
    }

}

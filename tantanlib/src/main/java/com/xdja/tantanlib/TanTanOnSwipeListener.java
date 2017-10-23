package com.xdja.tantanlib;

import android.support.v7.widget.RecyclerView;


public interface TanTanOnSwipeListener<T> {

    /**
     * 卡片还在滑动时回调
     *
     * @param viewHolder 该滑动卡片的viewHolder
     * @param ratio      滑动进度的比例
     * @param direction  卡片滑动的方向，TanTanConfig.SWIPING_LEFT 为向左滑，TanTanConfig.SWIPING_RIGHT 为向右滑，
     *                   TanTanConfig.SWIPING_NONE 为不偏左也不偏右
     */
    void onItemSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction);

    /**
     * 卡片完全滑出时回调
     *
     * @param viewHolder 该滑出卡片的viewHolder
     * @param t          该滑出卡片的数据
     * @param direction  卡片滑出的方向，TanTanConfig.SWIPED_LEFT 为左边滑出；TanTanConfig.SWIPED_RIGHT 为右边滑出
     */
    void onItemSwiped(RecyclerView.ViewHolder viewHolder, T t, int direction);

    /**
     * 所有的卡片全部滑出时回调
     */
    void onSwipedOver();

}


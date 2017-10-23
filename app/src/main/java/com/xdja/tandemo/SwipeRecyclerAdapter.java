package com.xdja.tandemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by fzl on 2017/08/14.
 * VersionCode: 1
 * Desc:
 */

public class SwipeRecyclerAdapter extends RecyclerView.Adapter<SwipeViewHolder> {
    private Context context;
    private List<String> dataList;
    public SwipeRecyclerAdapter(Context context, List<String> dataList) {
        this.dataList=dataList;
        this.context=context;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public SwipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.recycle_swipe_item, null);
        SwipeViewHolder myViewHodler=new SwipeViewHolder(itemView);
        return myViewHodler;
    }

    @Override
    public void onBindViewHolder(SwipeViewHolder holder, int position) {

        holder.tv.setText(dataList.get(position));
        holder.heart.setAlpha(0f);
        holder.heartBreak.setAlpha(0f);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

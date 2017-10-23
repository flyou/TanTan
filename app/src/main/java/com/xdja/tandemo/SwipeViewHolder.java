package com.xdja.tandemo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by fzl on 2017/08/14.
 * VersionCode: 1
 * Desc:
 */

public class SwipeViewHolder extends RecyclerView.ViewHolder {
   public TextView tv;
    public ImageView heart;
    public ImageView heartBreak;
    public SwipeViewHolder(View itemView) {
        super(itemView);
        tv= (TextView) itemView.findViewById(R.id.title);
        heart = (ImageView) itemView.findViewById(R.id.heart);
        heartBreak = (ImageView) itemView.findViewById(R.id.heart_break);

    }
}

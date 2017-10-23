package com.xdja.tandemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.xdja.tantanlib.TanTanConfig;
import com.xdja.tantanlib.TanTanLayoutManager;
import com.xdja.tantanlib.TanTanOnSwipeListener;
import com.xdja.tantanlib.TanTanTouchHelperCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recycleView;
    private List<String> dataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int i = 1; i < 30; i++) {

            dataList.add("第" + i + "个");
        }
        this.recycleView = (RecyclerView) findViewById(R.id.recycleView);
        SwipeRecyclerAdapter recyclerAdapter = new SwipeRecyclerAdapter(this, dataList);
        TanTanTouchHelperCallback<String> tanTanTouchHelperCallback =new TanTanTouchHelperCallback<String>(recyclerAdapter,dataList);
        ItemTouchHelper touchHelper = new ItemTouchHelper(tanTanTouchHelperCallback);
        final TanTanLayoutManager myLayoutManager = new TanTanLayoutManager(recycleView,touchHelper);
        recycleView.setLayoutManager(myLayoutManager);
        recycleView.setAdapter(recyclerAdapter);
        touchHelper.attachToRecyclerView(recycleView);
        tanTanTouchHelperCallback.setOnSwipedListener(new TanTanOnSwipeListener<String>() {
            @Override
            public void onItemSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {

                if (ratio>=-1&&ratio<=1){
                    float absRatio = Math.abs(ratio);
                    SwipeViewHolder myViewHolder= (SwipeViewHolder) viewHolder;
                  if (direction== TanTanConfig.SWIPING_RIGHT){
                      myViewHolder.heart.setVisibility(View.VISIBLE);
                      myViewHolder.heart.setAlpha(absRatio);
                  }else if (direction==TanTanConfig.SWIPING_LEFT){
                      myViewHolder.heartBreak.setVisibility(View.VISIBLE);
                      myViewHolder.heartBreak.setAlpha(absRatio);
                  }
                }
            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, String s, int direction) {
                SwipeViewHolder myViewHolder= (SwipeViewHolder) viewHolder;
                myViewHolder.heart.setVisibility(View.GONE);
                myViewHolder.heart.setAlpha(0f);
                myViewHolder.heartBreak.setVisibility(View.GONE);
                myViewHolder.heartBreak.setAlpha(0f);
                if (direction==TanTanConfig.SWIPED_LEFT){
                    Toast.makeText(MainActivity.this, "心碎["+s+"]", Toast.LENGTH_SHORT).show();
                }else if (direction==TanTanConfig.SWIPED_RIGHT){
                    Toast.makeText(MainActivity.this, "心爱["+s+"]", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onSwipedOver() {
                Toast.makeText(MainActivity.this, "什么也没有了", Toast.LENGTH_SHORT).show();
            }
        });


    }

}

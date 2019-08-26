package com.example.tantancarddemo.usemytouch;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tantancarddemo.R;

import java.util.ArrayList;
import java.util.List;

public class CardDemo3Activity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<Integer> list = new ArrayList<>();
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_demo3);
        mRecyclerView = findViewById(R.id.rv);
        initView();
        initData();

    }

    private void initView() {
        mAdapter = new MyAdapter();
        final SwipCardLayoutManager swipCardLayoutManager = new SwipCardLayoutManager(mRecyclerView);
        mRecyclerView.setLayoutManager(swipCardLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        swipCardLayoutManager.setOnFlingListener(new SwipCardLayoutManager.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter(RecyclerView.ViewHolder holder) {
                MyAdapter.MyViewHolder myHolder = (MyAdapter.MyViewHolder) holder;
                myHolder.dislikeImageView.setAlpha(0f);
                myHolder.likeImageView.setAlpha(0f);
                list.remove(0);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                makeToast(CardDemo3Activity.this, "Left");
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                makeToast(CardDemo3Activity.this, "Right");
            }

            @Override
            public void onScroll(float scrollProgressPercent, RecyclerView.ViewHolder viewHolder) {
                MyAdapter.MyViewHolder myHolder = (MyAdapter.MyViewHolder) viewHolder;
                viewHolder.itemView.setAlpha(1 - Math.abs(scrollProgressPercent) * 0.2f);
                if (scrollProgressPercent < 0) {
                    myHolder.dislikeImageView.setAlpha(Math.abs(scrollProgressPercent));
                } else if (scrollProgressPercent > 0) {
                    myHolder.likeImageView.setAlpha(Math.abs(scrollProgressPercent));
                } else {
                    myHolder.dislikeImageView.setAlpha(0f);
                    myHolder.likeImageView.setAlpha(0f);
                }
            }

        });
        swipCardLayoutManager.setOnItemClickListener(new SwipCardLayoutManager.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {

            }
        });
        //左右滑动
        findViewById(R.id.buttonleft).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipCardLayoutManager.getMlistener().selectLeft();
            }
        });
        findViewById(R.id.buttonright).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipCardLayoutManager.getMlistener().selectRight();
            }
        });
    }

    static void makeToast(Context ctx, String s) {
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }

    private void initData() {
        list.add(R.drawable.a1);
        list.add(R.drawable.a2);
        list.add(R.drawable.a3);
        list.add(R.drawable.a4);
        list.add(R.drawable.a5);
        list.add(R.drawable.a6);
        list.add(R.drawable.a7);
    }

    private class MyAdapter extends RecyclerView.Adapter {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ImageView avatarImageView = ((MyViewHolder) holder).avatarImageView;
            avatarImageView.setImageResource(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView avatarImageView;
            ImageView likeImageView;
            ImageView dislikeImageView;

            MyViewHolder(View itemView) {
                super(itemView);
                avatarImageView = (ImageView) itemView.findViewById(R.id.iv_avatar);
                likeImageView = (ImageView) itemView.findViewById(R.id.iv_like);
                dislikeImageView = (ImageView) itemView.findViewById(R.id.iv_dislike);
            }

        }
    }
}

package com.example.tantancarddemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.tantancarddemo.usemytouch.CardDemo3Activity;
import com.example.tantancarddemo.usertouchhelper.CardDemoActivity;
import com.example.tantancarddemo.useviewgroup.CardDemo2Activity;

public class CardListDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list_demo);
        findViewById(R.id.card1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CardListDemoActivity.this, CardDemoActivity.class));
            }
        });
        findViewById(R.id.card2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CardListDemoActivity.this, CardDemo2Activity.class));
            }
        });
        findViewById(R.id.card3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CardListDemoActivity.this, CardDemo3Activity.class));
            }
        });
    }
}

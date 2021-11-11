package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Init();
    }

    public void Init(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        CharSequence reviewRate = bundle.getCharSequence("reviewRate");
        CharSequence reviewPeop = bundle.getCharSequence("reviewPeople");
//        CharSequence reviewTime = bundle.getCharSequence("reviewTime");
        CharSequence reviewContent = bundle.getCharSequence("reviewContent");

        TextView reviewRateTextv = findViewById(R.id.reviewRateDetail);
        TextView reviewPeopTextv = findViewById(R.id.reviewPeopleD);
//        TextView reviewTimeTextv = findViewById(R.id.reviewTime);
        TextView reviewContentTextv = findViewById(R.id.reviewContent);

        reviewRateTextv.setText(reviewRate);
        reviewPeopTextv.setText(reviewPeop);
//        reviewTimeTextv.setText(reviewTime);
        reviewContentTextv.setText(reviewContent);

    }
}
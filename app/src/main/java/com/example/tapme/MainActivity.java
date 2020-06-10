package com.example.tapme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout relativeLayout;
    private Button mStartBtn;
    private TextView mTextView;
    private TextView mTextScore;
    private TextView mTextHighScore;
    private TextView mTextBest;
    private TextView mTextYourScore;
    private TextView mTextYour;
    private int score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        relativeLayout = findViewById(R.id.touchRelative);
        mTextView = findViewById(R.id.textViewTimer);
        mTextScore = findViewById(R.id.textViewScore);
        mTextHighScore = findViewById(R.id.textViewHighScore);
        mTextBest = findViewById(R.id.textBest);
        mStartBtn = findViewById(R.id.startBtn);
        mTextYour = findViewById(R.id.textYour);
        mTextYourScore = findViewById(R.id.textYourScore);

        relativeLayout.setBackgroundColor(Color.argb(119, 255, 0, 0));
        mTextBest.setVisibility(View.INVISIBLE);
        mTextHighScore.setVisibility(View.INVISIBLE);
        mTextYour.setVisibility(View.INVISIBLE);
        mTextYourScore.setVisibility(View.INVISIBLE);


        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });


    }

    public void startGame(){


        mTextScore.setText(String.valueOf(0));
        mStartBtn.setEnabled(false);
        mStartBtn.setVisibility(View.INVISIBLE);
        mTextHighScore.setVisibility(View.INVISIBLE);
        mTextBest.setVisibility(View.INVISIBLE);
        mTextYour.setVisibility(View.INVISIBLE);
        mTextYourScore.setVisibility(View.INVISIBLE);


        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTextView.setText(String.valueOf (millisUntilFinished/1000));
                relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int rand = new Random().nextInt(256);
                        int rand1 = new Random().nextInt(256);
                        int rand2 = new Random().nextInt(256);
                        relativeLayout.setBackgroundColor(Color.argb(255, rand, rand1, rand2));
                        score++;
                        mTextScore.setText(String.valueOf(score));
                    }
                });
            }

            @Override
            public void onFinish() {
                mTextView.setText("Finished!");
                mStartBtn.setEnabled(true);
                mStartBtn.setVisibility(View.VISIBLE);
                mStartBtn.setText("restart");
                relativeLayout.setBackgroundColor(Color.argb(119, 255, 0, 0));
                relativeLayout.setOnClickListener(null);
                mTextYourScore.setVisibility(View.VISIBLE);
                mTextYour.setVisibility(View.VISIBLE);
                mTextYour.setText(String.valueOf(score));
                int highScore = saveScore(score);
                mTextHighScore.setVisibility(View.VISIBLE);
                mTextBest.setVisibility(View.VISIBLE);
                mTextHighScore.setText(String.valueOf(highScore));
                score = 0;
            }
        }.start();

    }
    public int saveScore(int hScore){
        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        int bestScore = prefs.getInt("bestscore", 0);
        if (hScore > bestScore){
            SharedPreferences.Editor editor = getSharedPreferences("myPrefsKey", MODE_PRIVATE).edit();
            editor.putInt("bestscore", hScore);
            editor.apply();
            return hScore;
        }else{
            return bestScore;
        }
    }
}
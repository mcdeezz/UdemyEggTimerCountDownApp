package com.example.micha.eggcountdownapp;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

     ImageView eggImage;
     Button btnGo;
     SeekBar seekBarTime;
     TextView timerDisplay;
     CountDownTimer timer;
     boolean isActive = true;
     public void updateTimer(int times){

         if(isActive){

             String time;
             String padSec = "";
             String padMin = "";
             int sec, min;
             sec = times % 60;
             min = times / 60;
             if(sec <= 9)
                 padSec = "0";
             if(min <= 9)
                 padMin = "0";
             time = padMin + min + ":" + padSec + sec;

             timerDisplay.setText(time);
         }else{
             btnGo.setText("Go");
             timerDisplay.setText("0:30");
             seekBarTime.setEnabled(true);
             seekBarTime.setProgress(30);
         }


     }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        seekBarTime.setMax(600);
        seekBarTime.setProgress(30);
        seekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimer(seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


            }
        });
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnGo.setText("Stop");
                seekBarTime.setEnabled(false);

                timer = new CountDownTimer(seekBarTime.getProgress() * 1000 + 100, 1000) {
                    @Override
                    public void onTick(long l) {

                        isActive = true;
                        updateTimer((int) l / 1000);
                    }

                    @Override
                    public void onFinish() {
                        timer.cancel();
                        isActive = false;
                        MediaPlayer player = MediaPlayer.create(MainActivity.this, R.raw.horn);
                        player.start();
                        updateTimer(0);
                        isActive = true;
                    }
                }.start();
            }
        });

    }
     public void init(){
        eggImage = findViewById(R.id.egg_image_view);
        btnGo = findViewById(R.id.btn_go_view);
        seekBarTime = findViewById(R.id.seek_bar_view);
        timerDisplay = findViewById(R.id.time_view);
    }


}

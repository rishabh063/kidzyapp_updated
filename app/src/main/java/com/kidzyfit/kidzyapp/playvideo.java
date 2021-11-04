package com.kidzyfit.kidzyapp;

import android.animation.Animator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatDelegate;

import com.bumptech.glide.Glide;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class playvideo extends base2{
    ImageView extra_coin;
    TextView points_value;
    int height;
    MediaPlayer mediaPlayer,mediaPlayer2;
    int width;
    int cycle_second;
    int total_Score=0;
    int flag=0;
    TextView textView;
    boolean started=false;
    static int character=0;
    int time=0;
    static float total_time;
    static int totalmin=15;
    int second=1;
    int temp=0;
    int total_time_sec=900;
    boolean videorunning=false;
    android.widget.VideoView VideoView;
    boolean calibration_status=false;
    videoplayer videoplayer;
    int duration=0;
    int previous_score=0;
    int score=0;
    int movement=0;
    long previous_second=0;
    Date start;
    Date Pause;
    long spend_second=0;
    final int pause_time=90;
    final int break_time=20;
    Activity act;
    TextView texttimer;
    Map<String, Runnable> map = new HashMap<>();
    private boolean paused;
    int pause_timer=0;
    RelativeLayout callibration;
    Boolean caliibrating =true;
    TextView next_exercise;
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.playvideo);
        flag=0;
    }
    public int getRotation(Context context) {
        final int rotation = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getOrientation();
        switch (rotation) {
            case Surface.ROTATION_0:
            case Surface.ROTATION_180:
                return 0;
            case Surface.ROTATION_90:
                return 1;
            default:
                return -1;
        }
    }
    public void onBackPressed() {
        final Dialog dialog = new Dialog(playvideo.this);

        // setting content view to dialog
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_dialogue);

        // getting reference of TextView
        TextView dialogButtonYes = (TextView) dialog.findViewById(R.id.textViewYes);
        TextView dialogButtonNo = (TextView) dialog.findViewById(R.id.textViewNo);

        // click listener for No
        dialogButtonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // dismiss the dialog
                dialog.dismiss();

            }
        });

        // click listener for Yes
        dialogButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // dismiss the dialog and exit the exit
                dialog.dismiss();
                finishAffinity();

            }
        });

        // show the exit dialog
        dialog.show();
    }
    public Boolean callibration_screen_condition(){
        if ((counterr > 0) && ((calculator.time_for_callibration <= 10) && !started) &&   movement==0)
            return   true;
        return false;
    }
    public void  callibration_screen(){
        if (callibration_screen_condition())
        runOnUiThread(new Runnable() {
            public void run() {

                try{
                    textView.setText(calculator.getTime_for_callibration());
                }
                catch (Exception E){}
            }
        });
    }
    public void set_start(){
        Log.i("pausing", "set_start: here1"+started+" "+calculator.time_for_callibration+" "+movement);
        if (!started && calculator.time_for_callibration >= 10  )
        {   Log.i("pausing", "set_start: here2");
            starter();
            caliibrating=false;
        }
    }
    public void run(){
        Log.i("out", "run: "+calculator.outside_frame_time);
        ImageView img = (ImageView)findViewById(R.id.redbox);
        RelativeLayout frame=(RelativeLayout)findViewById(R.id.outofframe);
        next_exercise =(TextView) findViewById(R.id.next_exer_timer);
        img.setImageAlpha(127);
        if(calculator.outside_frame_time>-10 || paused || caliibrating) {
            frame.setVisibility(View.INVISIBLE);
       callibration_screen();
       set_start();
       if (started) {

           time_manager();
           try {
           break_screen_start();
           break_screen_end();


               run_screen();
           } catch (Exception e) {

           }

           pause_screen();
           if (second == 0 && totalmin == 0)
              complete();
       }
   }
        else{
            frame.setVisibility(View.VISIBLE);
      }
                    }
    private void pause_screen(){

        String vallue= String.valueOf(pause_timer);
        String finalVallue = vallue;
        if (pause_timer<10)
        {vallue="0"+vallue;
            finalVallue=vallue;
        }
        String finalVallue1 = finalVallue;
        runOnUiThread(new Runnable() {
            public void run() {
                texttimer.setText("Next Exercise in 00:"+ finalVallue1);
            }});
    }
    private void run_screen() {
        if(!paused) {   if (cycle_second>5)
        {
            TextView temp11=(TextView) findViewById(R.id.textView1);
            temp11.setVisibility(View.INVISIBLE);
        }
            callibration.setY((float) (callibration.getY()+1));
            runOnUiThread(new Runnable() {
                public void run() {
                    TextView timertext=findViewById(R.id.timeleft);
                    String secon= String.valueOf(second);
                    String min= String.valueOf(totalmin);
                    if (second<10)
                        secon="0"+secon;
                    if (totalmin<10)
                        min="0"+min;
                    timertext.setText(min+":"+secon);
                    if (cycle_second+duration<pause_time)
                    next_exercise.setText("Next Exercise in "+duration);
                    else
                        next_exercise.setText("Break in "+(pause_time-cycle_second));
                }});

            if (videorunning == false) {
                //log.i("timer", "run: here 8");
                videoplayer.update();
                previous_score = total_Score;
                String video = videoplayer.getvideoname();
                duration = videoplayer.getvideoduration();
                runOnUiThread(new Runnable() {
                    public void run() {
                        VideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + video));
                        VideoView.start();

                    }
                });
                videorunning = true;
            }
            extra_coin.setVisibility(View.VISIBLE);
            Animation animation = new TranslateAnimation(0, 0, 0, -1*height);
            Animation animation2 = new TranslateAnimation(0, 0, 0, height);
            if(mediaPlayer2.isPlaying() == true){

                mediaPlayer.setVolume(0.4f,0.4f);
            }else{

                mediaPlayer.setVolume(1,1);
            }
            map.get(videoplayer.currentvideo.funcname).run();
            orientation = getRotation(act);
            total_Score = score + previous_score;

            runOnUiThread(new Runnable() {
                public void run() {
                    if (total_Score != temp) {

                        extra_coin.startAnimation(animation);
                        points_value.startAnimation(animation2);
                        animation.setRepeatCount(0);
                        animation2.setRepeatCount(0);
                        points_value.setText("+"+String.valueOf(total_Score-temp));
                        //log.i("points", "run: "+points_value.getText()+" "+previous_score+" "+total_Score+" "+temp);
                        temp=total_Score;
                        animation.setDuration(1000);
                        animation2.setDuration(1000);
                        mediaPlayer2.start();
                        mediaPlayer2.setVolume(1,1);
                        TextView textView = (TextView) findViewById(R.id.textView2);
                        textView.setText(String.valueOf(total_Score));
                    }
                }
            });


            if (duration == 0) {
                videorunning = false;
            }
        }
    }

    private void break_screen_end() {
        if(cycle_second==pause_time+break_time)
        runOnUiThread(new Runnable() {
            public void run() {
                {
                    mediaPlayer.start();
                    RelativeLayout restscreen=findViewById(R.id.restscreen);
                    restscreen.setVisibility(View.INVISIBLE);
                    score = 0;
                    paused=false;
                    TextView temp11=(TextView) findViewById(R.id.textView1);
                    temp11.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void break_screen_start() {
       if (cycle_second==pause_time)
        runOnUiThread(new Runnable() {
            public void run() {
                {
                    pause_timer=break_time;
                    videorunning = false;
                    mediaPlayer.pause();
                    RelativeLayout restscreen=findViewById(R.id.restscreen);
                    restscreen.setVisibility(View.VISIBLE);
                    paused=true;
                }
            }
        });
    }


    private void time_manager() {
        Date current_time =Calendar.getInstance().getTime();
        spend_second=(current_time.getTime()-start.getTime())/1000;
        if (previous_second!=spend_second)
        {   total_time_sec--;
        if (total_time_sec==0)
            complete();
            cycle_second++;
            if(started && !paused)
            {
                second--;
                duration--;
                if (duration<0)
                duration=0;
                if (second<=0)
                {
                    totalmin--;
                    second=60;
                }
                counterr--;
                if (counterr < 0)
                    counterr = 0;
            }
            if (paused){
                pause_timer--;
            }
            previous_second=spend_second;
        }
        if(cycle_second==pause_time+break_time+1)
        {
            cycle_second=0;
        }

    }

    public void starter() {
        if (!started) {
            start= Calendar.getInstance().getTime();
            TextView tx1=(TextView)findViewById(R.id.textView1);
            TextView tx2=(TextView)findViewById(R.id.textView2);
            ImageView restback=findViewById(R.id.loading);
            ImageView restcharacter=findViewById(R.id.gender);
            if (character == 1) {
                mediaPlayer = MediaPlayer.create(this, R.raw.astra_sound);
                restback.setImageResource(R.drawable.astrarest);
            } else if (character == 2) {
                mediaPlayer = MediaPlayer.create(this, R.raw.doozy_sound);
                restback.setImageResource(R.drawable.doozyrest);

            } else if (character == 3) {
                mediaPlayer = MediaPlayer.create(this, R.raw.granny_sound);
                restback.setImageResource(R.drawable.grannyrest);
            } else if (character == 4) {
                mediaPlayer = MediaPlayer.create(this, R.raw.ninja_sound);
                restback.setImageResource(R.drawable.ninjarest);
            }
            SharedPreferences sharedPreferences = getSharedPreferences("personaldetails",MODE_PRIVATE);
            String gender=sharedPreferences.getString("gender","Male");
            if (gender.equals("Male"))
                restcharacter.setImageResource(R.drawable.rest_boy);
            else if (gender.equals("Female"))
                restcharacter.setImageResource(R.drawable.rest_girl);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();

            VideoView = (VideoView) findViewById(R.id.videoView1);
            ImageView imageView = findViewById(R.id.loadingimg);
            ImageView loadingback=findViewById(R.id.loadingb);
            imageView.setVisibility(View.INVISIBLE);
            callibration=findViewById(R.id.Callibration);
            RelativeLayout img_test=findViewById(R.id.text_img);
            RelativeLayout score_timer=findViewById(R.id.score_timer);
            extra_coin = findViewById(R.id.imageView2);
            points_value=(TextView) findViewById(R.id.points_value);
            score_timer.setVisibility(View.VISIBLE);
            extra_coin.setVisibility(View.INVISIBLE);
            callibration.setVisibility(View.INVISIBLE);
           previewView.getLayoutParams().height = width/8;
            previewView.setX((float) (3*width/4-10));
            previewView.setY((float) (height-width/8-10));
            previewView.getLayoutParams().width = width/4;
            img_test.setVisibility(View.INVISIBLE);
            findViewById(R.id.instructiontext).setVisibility(View.INVISIBLE);
            VideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                }
            });
            started=true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            mediaPlayer.stop();
        } catch (Exception e) {
        }
        SharedPreferences sharedPreferences;
        if (character==1)
        {  sharedPreferences = getSharedPreferences("player1",MODE_PRIVATE);}
        else if (character==2)
        { sharedPreferences = getSharedPreferences("player2",MODE_PRIVATE);}
        else if (character==3)
        { sharedPreferences = getSharedPreferences("player3",MODE_PRIVATE);}
        else  if (character==4)
        { sharedPreferences = getSharedPreferences("player4",MODE_PRIVATE);}
        else
        {
            sharedPreferences = getSharedPreferences("player5",MODE_PRIVATE);
        }
        int score= Integer.valueOf(sharedPreferences.getString("score","0"));
        if (score<total_Score)
        {String s1= String.valueOf(total_Score);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putString("score",s1);
            myEdit.commit();}
        try{mediaPlayer.stop();}
        catch (Exception E){}

    }
    public void complete(){
        try {
            mediaPlayer.stop();
        } catch (Exception e) {
        }
        SharedPreferences sharedPreferences;
        if (character==1)
        {  sharedPreferences = getSharedPreferences("player1",MODE_PRIVATE);}
        else if (character==2)
        { sharedPreferences = getSharedPreferences("player2",MODE_PRIVATE);}
        else if (character==3)
        { sharedPreferences = getSharedPreferences("player3",MODE_PRIVATE);}
        else  if (character==4)
        { sharedPreferences = getSharedPreferences("player4",MODE_PRIVATE);}
        else
        {
            sharedPreferences = getSharedPreferences("player5",MODE_PRIVATE);
        }
        int score= Integer.valueOf(sharedPreferences.getString("score","0"));
        if (score<total_Score)
        {String s1= String.valueOf(total_Score);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putString("score",s1);
            myEdit.commit();}
        try{mediaPlayer.stop();}
        catch (Exception E){}


        Intent i = new Intent(playvideo.this, result.class);
        Bundle bundle = new Bundle();
        bundle.putString("coin", String.valueOf(total_Score));
        if(flag==0) {
            total_time += (15 - totalmin) - (float) second / 60;
            flag=1;
        }
        i.putExtras(bundle);
        i.putExtra("char",character);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }
    @Override
    protected void onResume() {
        ImageView callibrateimg=findViewById(R.id.loadingb);
        textView=findViewById(R.id.instructiontext);
        if (character == 1) {
            callibrateimg.setImageResource(R.drawable.astracall);
        } else if (character == 2) {
            callibrateimg.setImageResource(R.drawable.doozycall);

        } else if (character == 3) {
            callibrateimg.setImageResource(R.drawable.grannycall);
        } else if (character == 4) {
            callibrateimg.setImageResource(R.drawable.ninjacall);
        }
        super.onResume();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        act = this;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
        previewView=findViewById(R.id.previewview);
        videoplayer = new videoplayer(calculator, character);
        videoplayer.context = this;
        map.put("squat", () -> score = 4 * calculator.squatscore);
        map.put("arm", () -> score = calculator.arm_score);
        map.put("pistol", () -> score = 5 * calculator.pistol_Score);
        map.put("sword", () -> score = 1 * calculator.stable_sword_score);
        map.put("capoeria", () -> score = 2 * calculator.capoeria_score);
        map.put("crossjumpr", () -> score = 2 * calculator.side_jump_score);
        map.put("cross", () -> score = 2 * calculator.side_jump_score);
        map.put("front", () -> score = 2 * calculator.frontrasiesscore);
        map.put("harvest", () -> score = 2 * calculator.stable_sword_score);
        map.put("hook", () -> score = 2 * calculator.stable_sword_score);
        map.put("jazz", () -> score = 3 * calculator.jazzscore);
        map.put("jog", () -> score = 1 * calculator.still_jog);
        map.put("jump", () -> score = 2 * calculator.jump_score);
        map.put("jjack", () -> score = 3* calculator.jump_jack);
        map.put("sweep", () -> score = 3 * calculator.legsweep_score);
        map.put("sidejump", () -> score = 2 * calculator.side_jump_score);
        map.put("victory", () -> score = 2 * calculator.victory_score);
        map.put("neck", () -> score = calculator.neck_right_left_score);
        ImageView imageView = findViewById(R.id.loadingimg);
        ImageView imageView2 = findViewById(R.id.pointing);
        Glide.with(act)
                .load(R.raw.load2).into(imageView);
        Glide.with(act)
                .load(R.raw.point).into(imageView2);
        mediaPlayer2 = MediaPlayer.create(this, R.raw.coin);
        mediaPlayer2.setLooping(false);
        texttimer=findViewById(R.id.resttime);
    }
    @Override
    protected void onDestroy() {
        if(flag==0) {
            total_time += (15 - totalmin) - (float) second / 60;
            flag=1;
        }
        //log.i("nice", "onBackPressed: "+totalmin+" "+second);
        clearMemory();
        super.onDestroy();
        //log.i("destorying", "onDestroy: called");
        Runtime.getRuntime().gc();
        System.gc();
    }
    public void clearMemory(){
        clearmemory2();
        try   {mediaPlayer.release();
            mediaPlayer2.release();
            VideoView.suspend();
            VideoView.destroyDrawingCache();
          }
        catch (Exception E){}
        mediaPlayer=null;
        act=null;
        System.gc();
    }
    public  void back_button_visible(){

        RelativeLayout back_button_rel;
        back_button_rel=findViewById(R.id.back_button_2);
        if(back_button_rel.getVisibility()==View.VISIBLE)
            return;
        back_button_rel.setVisibility(View.VISIBLE);
        ImageView back_Button;
        back_Button=findViewById(R.id.back_button_button);
        int cx = back_Button.getWidth() / 2;
        int cy = back_Button.getHeight() / 2;

        // get the final radius for the clipping circle
        float finalRadius = (float) Math.hypot(cx, cy);

        // create the animator for this view (the start radius is zero)
        Animator anim = ViewAnimationUtils.createCircularReveal( back_Button, cx, cy, 0f, finalRadius);

        // make the view visible and start the animation
        anim.start();
        back_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent i=new Intent(playvideo.this,sample_video_images.class);
             Log.i("valuuif", "onClick: "+(character+3));
             i.putExtra("select",(character+3));
             startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try{
                    back_button_hide();}
                catch (Exception e){

                }
            }
        }, 2200);
    }
    public void  back_button_hide(){
        RelativeLayout back_button_rel;
        back_button_rel=findViewById(R.id.back_button_2);

        ImageView back_Button;
        back_Button=findViewById(R.id.back_button_button);
        int cx = back_Button.getWidth() / 2;
        int cy = back_Button.getHeight() / 2;

        // get the final radius for the clipping circle
        float finalRadius = (float) Math.hypot(cx, cy);

        // create the animator for this view (the start radius is zero)
        Animator anim = ViewAnimationUtils.createCircularReveal( back_Button, cx, cy, finalRadius, 0f);

        // make the view visible and start the animation
        anim.start();
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                back_button_rel.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
 back_Button.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {

     }
 });
    }
}
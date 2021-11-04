package com.kidzyfit.kidzyapp;

import android.animation.Animator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class gameplay_1 extends base {
   ImageView imageView;
    public Bitmap bitmap;
    Canvas canvas;
    Paint paint;
    Random rand;
    RelativeLayout outofboundary;
    RelativeLayout multiple_faces;
    private int last_second=-100;
    private boolean callibration=false;
    private boolean started=true;
    int total_time_left=120;
    int max_target;
    TextView time_text;
    TextView score_text;
    int score=0;
    RelativeLayout game_over;
    RelativeLayout finish_level;
    RelativeLayout callibration_screen;
    int wait_time=0;
    int level_num=5;
    private boolean animation_run=false;
    private boolean animation_status=false;
    int time_val=400;
    all_bubble all_bubble=new all_bubble();
    Activity act;
    RelativeLayout Gameplay;
    all_points all_points=new all_points();
    int new_wait_time=0;
    ProgressBar progressBar;
    int max_score=180;
    private int time_for_callibration=0;
    private TextView callibration_text;
    Button bt1;
    Button bt2;
    private boolean finish_bool=false;
    private float back_pressed=0;
    private boolean once=false;
    MediaPlayer mp_bubble;
    MediaPlayer mp_time;
    MediaPlayer mp_game_over;
    MediaPlayer mp_game_win;
    MediaPlayer mp_background;
    private CountDownTimer tim;
    private FirebaseAnalytics firebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay_1);
        previewView=findViewById(R.id.preview);
        imageView=findViewById(R.id.canvas);
        progressBar=findViewById(R.id.progress);
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);


        callibration_screen=findViewById(R.id.callibration_screen);
        mp_bubble=MediaPlayer.create(this, R.raw.bubble_burst);
        mp_time=MediaPlayer.create(this, R.raw.low_time);
        mp_game_over=MediaPlayer.create(this, R.raw.loose_sound);
        mp_game_win=MediaPlayer.create(this, R.raw.win_sound);
        mp_background=MediaPlayer.create(this, R.raw.doozy_sound);
        Intent intent = getIntent();
        rand=new Random();
        callibration_text=findViewById(R.id.call_text);
        level_num = intent.getIntExtra("level",2);
        Bundle bundle = new Bundle();
        firebaseAnalytics.logEvent("gameplay1"+level_num, bundle);
        max_score=intent.getIntExtra("max",180);
        bt1=findViewById(R.id.level_Complete_button);
        bt2=findViewById(R.id.level_over_button);
        act=this;
        score_text=findViewById(R.id.score_text);
        game_over=findViewById(R.id.game_over);
        finish_level=findViewById(R.id.level_complete);
        outofboundary=findViewById(R.id.out_of_boundary);
        multiple_faces=findViewById(R.id.multiple_faces);
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Gameplay=findViewById(R.id.game_view);
        imageView.setImageBitmap(bitmap);
        canvas = new Canvas(bitmap);
        time_text=findViewById(R.id.time_text);
        max_target=height;
        paint=new Paint();
        paint.setARGB(255,20,200,20);
        all_bubble.draw_height=height/4;
        all_bubble.draw_width=height/4;
        all_bubble.img=BitmapFactory.decodeResource(getResources(),R.drawable.bubble);
        all_bubble.paint=new Paint();
        all_bubble.second_paint=new Paint();
        all_bubble.second_paint.setARGB(255,255,255,255);
        all_bubble.paint.setColor(Color.rgb(100,100,100));
        all_bubble.paint.setTextSize(height/8 );
        all_bubble.paint.setAntiAlias(true);
        all_bubble.paint.setTextAlign(Paint.Align.CENTER);
        ImageView imageView = findViewById(R.id.gif);
        progressBar.setProgress(1);
        score=0;
        start();
    }
    public void start(){
         tim=new CountDownTimer(120*1000,15 ) {

            public void onTick(long duration) {
                //tTimer.setText("seconds remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext resource id
                // Duration
                control();
            }
            public void onFinish() {
                start();
            }

        }.start();
    }
    protected void onPause() {
        if (tim!=null)
            tim.cancel();
        if ( mp_bubble!=null)
            mp_bubble.stop();
        if ( mp_time!=null)
            mp_time.stop();
        if ( mp_game_over!=null)
            mp_game_over.stop();
        if ( mp_game_win!=null)
            mp_game_win.stop();
        if ( mp_background!=null)
            mp_background.stop();
        super.onPause();

    }
    protected void onResume() {
        start();
        mp_bubble=MediaPlayer.create(this, R.raw.bubble_burst);
        mp_time=MediaPlayer.create(this, R.raw.low_time);
        mp_game_over=MediaPlayer.create(this, R.raw.loose_sound);
        mp_game_win=MediaPlayer.create(this, R.raw.win_sound);
        mp_background=MediaPlayer.create(this, R.raw.doozy_sound);
        super.onResume();
    }
    private void control() {
        Log.i("valinc", "control: here0");
        if (once)
            return;
        Log.i("valinc", "control: here1");
        multiple_faces.setVisibility(View.INVISIBLE);
        outofboundary.setVisibility(View.INVISIBLE);
        game_over.setVisibility(View.INVISIBLE);
        finish_level.setVisibility(View.INVISIBLE);
        Log.i("valinc", "control: here2");
        if (!callibration && time_for_callibration > 10) {
            callibration_screen.setVisibility(View.INVISIBLE);
            Gameplay.setVisibility(View.VISIBLE);
            callibration = true;
        } else if (!callibration) {
            return;
        }
        Log.i("valinc", "control: here3");
        if (finish_bool && !once) {
            ImageView temp = findViewById(R.id.level_complete_image);
            Glide.with(this).asGif().load(R.raw.level_complete_1).into(temp);
            Animation aniFade = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
            aniFade.setDuration(1200);
            finish_level.setAnimation(aniFade);
            finish_level.setVisibility(View.VISIBLE);
            SharedPreferences prefs = getSharedPreferences("gameplay_1", MODE_PRIVATE);
            int progress = prefs.getInt("myInt", 1); // 0 is default
            progress++;
            SharedPreferences.Editor myEdit = prefs.edit();
            Log.i("val", "control: "+progress);
            if (progress == level_num) {
                myEdit.putInt("myInt", progress);
                myEdit.commit();
            }
            if(mp_background.isPlaying()){
                mp_background.stop();
            }
            if(mp_time.isPlaying()){
                mp_time.stop();
            }
            if(!mp_game_win.isPlaying()){
                mp_game_win.start();
            }
            bt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(act, level_screen_1.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                    if(mp_game_win.isPlaying()){
                        mp_game_win.stop();

                    }
                }
            });
            once = true;
            return;
        }
        else if (total_time_left == 0 && !once) {
            TextView time_text=findViewById(R.id.time_text_new);
            ImageView temp = findViewById(R.id.game_over_img);
            Glide.with(this).asGif().load(R.raw.game_over_1).into(temp);
            Animation aniFade = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
            aniFade.setDuration(1200);
            Animation animation = new TranslateAnimation(0, -width/4-20+time_text.getWidth()/2,0, height/3-time_text.getHeight()/2);
            animation.setDuration(2400);
            animation.setFillAfter(true);
            time_text.startAnimation(animation);
            game_over.setAnimation(aniFade);
            game_over.setVisibility(View.VISIBLE);
            if(mp_background.isPlaying()){
                mp_background.stop();
            }
            if(mp_time.isPlaying()){
                mp_time.stop();
            }
            if(!mp_game_over.isPlaying()){
                mp_game_over.start();
            }
            bt2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mp_game_over.isPlaying()){
                        mp_game_over.stop();
                    }
                    Intent intent = new Intent(act, level_screen_1.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }
            });
            once = true;
            return;
        } else if (face_count > 1 && !once) {
            multiple_faces.setVisibility(View.VISIBLE);
            if(!mp_time.isPlaying()){
                mp_time.pause();
            }
            return;
        } else if (outside_frame_time < -10 && !once) {
            if(!mp_time.isPlaying()){
                mp_time.pause();
            }
            outofboundary.setVisibility(View.VISIBLE);
            return;
        } else if (!once) {
            Log.i("valinc", "control: here4");
            time_manager();
            all_bubble.add_new();
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            all_bubble.control(canvas);
            imageView.setImageBitmap(bitmap);
            all_points.delete();
            if (new_wait_time > 0)
                new_wait_time--;
            else
                new_wait_time = 0;
            if (100 * score / max_score > 98) {
                finish_bool = true;
            }
            Log.i("issue1", "control: " + (100 * score / max_score));
            progressBar.setProgress((100 * score / max_score));
            Log.i("score", "control: " + (100 * score / max_score) + " " + score + " " + max_score);
        }
    }
    public void control2(){
        Log.i("shutdown", "control2: here");
        if (time_for_callibration<=10 && !callibration)
        { setTime_for_callibration();
            callibration_text.setText(getTime_for_callibration());
            return;
        }
        if(started && callibration)
        {
            Log.i("control2", "control2: here");
            int x = (int) (translatex(leftWrist.getPosition().x) + translatex(leftThumb.getPosition().x) + translatex(leftIndex.getPosition().x) + translatex(leftPinky.getPosition().x)) / 4;
            int y = (int) (translatey(leftWrist.getPosition().y) + translatey(leftThumb.getPosition().y) + translatey(leftIndex.getPosition().y) + translatey(leftPinky.getPosition().y)) / 4;
            all_bubble.check(x,y);
            x = (int) (translatex(rightWrist.getPosition().x) + translatex(rightThumb.getPosition().x) + translatex(rightIndex.getPosition().x) + translatex(rightPinky.getPosition().x)) / 4;
            y = (int) (translatey(rightWrist.getPosition().y) + translatey(rightThumb.getPosition().y) + translatey(rightIndex.getPosition().y) + translatey(rightPinky.getPosition().y)) / 4;
            all_bubble.check(x,y);
        }

    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        int x = (int)event.getX();
//        int y = (int)event.getY();
//        all_bubble.check(x,y);
//        return super.onTouchEvent(event);
//    }

    private void time_manager() {
        Log.i("checking", "time_manager: here");
        Date current_time = Calendar.getInstance().getTime();
        if (last_second!=-100 && last_second!=current_time.getTime()/1000 && callibration && started)
        {total_time_left--;}
        if (total_time_left<0)
        { total_time_left=0;
            if (mp_time.isPlaying())
                mp_time.stop();
        }
        if (total_time_left<20){
            if (!mp_time.isPlaying())
                mp_time.start();
        }
        last_second= (int) (current_time.getTime()/1000);
        int min=total_time_left/60;
        int second=total_time_left%60;
        if (second<10)
        time_text.setText("0"+min+":0"+second);
        else
         time_text.setText("0"+min+":"+second);
    }
    class bubble{
        int x,y,height,width;
        int time;
        Bitmap bitmap;
        int start_animation;
        int end_animation;
        int num;
        public bubble(Bitmap img, int x, int y, int height, int width, int time, int num) {
            this.x = x;
            this.y = y;
            this.height = height;
            this.width = width;
            this.time =time;
            this.num=num;
            this.bitmap=img;
        }
        public void draw(Canvas canvas, Paint paint, Paint second_paint){
            if (start_animation > 0) {
                animation_status = true;
                Rect temp = new Rect(x - width / (2 * start_animation), y - height / (2 * start_animation), x + width / (2 * start_animation), y + height / (2 * start_animation));
                canvas.drawBitmap(bitmap, null, temp, null);
                start_animation--;
            }
                 else if (end_animation > 0) {
                    animation_status = true;
                    Rect temp = new Rect(x - width / (2 * end_animation), y - height / (2 * end_animation), x + width / (2 * end_animation), y + height / (2 * end_animation));
                    canvas.drawBitmap(bitmap, null, temp, null);
                    Log.i("value", "draw: "+end_animation);
                    end_animation++;}
                else if (time>0) {
                    animation_status = false;
                    Rect temp = new Rect(x - width / (2), y - height / (2), x + width / (2), y + height / (2));
                    RectF temp2=new RectF(x - width / (2) -10, y - height / (2) - 10, x + width / (2) + 10 , y + height / (2) + 10);
                    canvas.drawArc (temp2, -90, (time*360/time_val), true,  second_paint);
                    canvas.drawBitmap(bitmap, null, temp, null);
                    canvas.drawText(String.valueOf(num), x, y+height/8, paint);
                }
            }
    }
    class all_bubble {
        ArrayList<bubble> bubbles = new ArrayList<>();
        Paint paint;
        Paint second_paint;
        int draw_width;
        int draw_height;
        Bitmap img;

        public void add_new() {
            ArrayList<Integer> level_item = new ArrayList<Integer>();

            for (int i=0;i<level_num;i++){
                level_item.add(i+1);
            }
            if (bubbles.size() == 0) {
                for (int i = 0; i < level_num; i++) {
                    int temp_num = rand.nextInt(level_item.size());
                    bubble temp;
                    int temp2=0;
                    temp2=rand.nextInt(width/level_num);
                    if (temp2+draw_width+10>=width/level_num)
                    {
                        temp2=width/level_num-draw_width-10;
                    }
                    if (i == 0 || i == 4)
                        temp = new bubble(img, (width * (i) / level_num) + temp2 + draw_width / 2 + 10, rand.nextInt(height - draw_height - 10 - height / 10) + draw_height / 2 + 10 + height / 10, draw_height, draw_width, time_val, level_item.get(temp_num));
                    else
                        temp = new bubble(img, (width * (i) / level_num)+temp2  + draw_width / 2 + 10, rand.nextInt(height - draw_height - 10) + draw_height / 2 + 10, draw_height, draw_width, time_val, level_item.get(temp_num));
                    Log.i("here", "add_new: " + temp_num);
                    new_wait_time=40;
                    level_item.remove(temp_num);
                    temp.start_animation = 3;
                    bubbles.add(temp);
                }
                all_points.delete_all();
            }
        }

        public void draw(Canvas canvas, int i) {
            bubbles.get(i).draw(canvas, paint, second_paint);
        }

        public void time(int i) {
            bubbles.get(i).time--;
        }

        public void control(Canvas canvas) {
            int i = 0;
            while (i < bubbles.size()) {
                time(i);
                time_run(i);
                Log.i("end_animation", "control: " + bubbles.get(i).time);
                draw(canvas, i);
                if (bubbles.get(i).end_animation == 4) {
                    bubbles.remove(i);
                } else {
                    i++;
                }
            }
        }

        private void time_run(int i) {
            if (bubbles.get(i).time == 0) {
                bubbles.get(i).end_animation = 1;
                Log.i("end_animation", "control: " + "here");
            }
        }

        public void check(float x, float y) {
            Log.i("click", "check: "+new_wait_time);
            if (time_val<0 || new_wait_time>0 )
                return;
            int min = 100;
            for (int i = 0; i < bubbles.size(); i++) {
                if (bubbles.get(i).num < min) {
                    min = bubbles.get(i).num;
                }
            }
            for (int i = 0; i < bubbles.size(); i++) {
                if (mod(bubbles.get(i).x - x) < 250 && mod(bubbles.get(i).y - y) < 250 && bubbles.get(i).end_animation <= 0 && bubbles.get(i).start_animation <= 0) {
                    if (min == bubbles.get(i).num) {
                        score += 4;
                        all_points.add_new(bubbles.get(i).x,bubbles.get(i).y,"+4",0);
                    } else {

                        score -= 2;
                        all_points.add_new(bubbles.get(i).x,bubbles.get(i).y,"-2",1);
                    }
                    if(!mp_bubble.isPlaying())
                        mp_bubble.start();
                    bubbles.get(i).end_animation = 1;
                    break;
                }
            }
            score_text.setText(String.valueOf(score));

        }

    }
    class point{
        TextView textView;
        int time;
    }
    class all_points{
        ArrayList<point> points_texts=new ArrayList<>();
        public void add_new(int x , int y , String text , int color ) {
            TextView temp = new TextView(act);
            RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            param.setMargins(x, y, 0, 0);
            if (color==1)
            {
                temp.setTextColor(Color.rgb(200,20,20));
            }
            else{
                temp.setTextColor(Color.rgb(20,200,20));
            }
            temp.setLayoutParams(param);
            temp.setGravity(Gravity.CENTER);
            temp.setText(text);
            temp.setTextSize(height / 15);
            temp.startAnimation(AnimationUtils.loadAnimation(act, android.R.anim.fade_in));
            Gameplay.addView(temp);
            point temp_point = new point();
            temp_point.textView = temp;
            temp_point.time = 100;
            points_texts.add(temp_point);
        }
        public void delete(){
            int i=0;
            while(i<points_texts.size()) {
                points_texts.get(i).time--;
                if (points_texts.get(i).time<=0)
                {
                    points_texts.get(i).textView.startAnimation(AnimationUtils.loadAnimation(act, android.R.anim.fade_out));
                    points_texts.get(i).textView.setVisibility(View.INVISIBLE);
                    points_texts.remove(i);
                }
                else {
                    i++;
                }
            }
        }
        public void delete_all(){
            int i=0;
            while(i<points_texts.size()) {
                points_texts.get(i).textView.startAnimation(AnimationUtils.loadAnimation(act, android.R.anim.fade_out));
                points_texts.get(i).textView.setVisibility(View.INVISIBLE);
                points_texts.remove(i);
            }
        }
    }
    public  void setTime_for_callibration(){
        for (int i = 0; i <13 ; i++) {
            if (cord[i][3]<0.9 || cord[i][0]>1 || cord[i][1]>1)
            {  time_for_callibration=0; }

        }
        if (translatey(nose.getPosition().y)/height>0.8)
        {
            time_for_callibration=0;
        }
        if (translatey(nose.getPosition().y)<0.6)
        {
            time_for_callibration=0;
        }

        else if ((translatex(rightShoulder.getPosition().x)+translatex(leftShoulder.getPosition().x))/(2*width)<0.4)
        {
            //right
            time_for_callibration=0;
        }
        else if ((translatex(rightShoulder.getPosition().x)+translatex(leftShoulder.getPosition().x))/(2*width)>0.6)
        {
            //left
            time_for_callibration=0;
        }
        if (mod(leftShoulder.getPosition().x-rightShoulder.getPosition().x)/height2>0.35)
        {
            time_for_callibration=0;
        }
        else if (mod(leftShoulder.getPosition().x-rightShoulder.getPosition().x)/height2<0.1)
        {
            time_for_callibration=0;
        }
        else {
            //calibrating
            time_for_callibration+=3;
        }

    }
    public  String getTime_for_callibration(){
        for (int i = 0; i <13 ; i++) {
            if (cord[i][3]<0.9 || cord[i][0]>1 || cord[i][1]>1)
            { Log.i("pausing", "Scorecal:here also heeeee"+i+" "+cord[i][3]+" "+cord[i][0]+" "+cord[i][1]);
                return "STAND IN THE BOUNDARY";}

        }
        Log.i("pausing", "Scorecal:here also heeeee"+cord[0][0]);
        Log.i("nose y ", "getTime_for_callibration: ");
        if (translatey(nose.getPosition().y)/height>0.8)
        {
            return "MOVE DOWN";
        }
        if (translatey(nose.getPosition().y)<0.6)
        {
            return "MOVE UP";
        }

        else if ((translatex(rightShoulder.getPosition().x)+translatex(leftShoulder.getPosition().x))/(2*width)<0.4)
        {
            //right

            return "MOVE TOWARDS RIGHT";
        }
        else if ((translatex(rightShoulder.getPosition().x)+translatex(leftShoulder.getPosition().x))/(2*width)>0.6)
        {
            //left
            return "MOVE TOWARDS LEFT";
        }
        if (mod(leftShoulder.getPosition().x-rightShoulder.getPosition().x)/height2>0.35)
        {
            return "MOVE BACK";
        }
        else if (mod(leftShoulder.getPosition().x-rightShoulder.getPosition().x)/height2<0.1)
        {
            return "MOVE CLOSER";
        }
        else {
            //calibrating
            return "CALIBRATING PLEASE WAIT";
        }
    }
    boolean doubleBackToExitPressedOnce = false;
    private Toast toast = null;
    public void onBackPressed() {
        final Dialog dialog = new Dialog(gameplay_1.this);

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
                Intent i=new Intent(gameplay_1.this,level_screen_1.class);
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

package com.kidzyfit.kidzyapp;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class gameplay_0 extends base {
    Random rand;
    private int last_second=-100;
    private boolean callibration=false;
    private boolean started=true;
    private int current_fruit=1;
    int count=0;
    int total_count=0;
    Boolean animation_status=false;
    int time_for_callibration=0;
    Bitmap fruit1_img;
    Bitmap fruit2_img;
    Bitmap fruit3_img;
    Bitmap fruit4_img;
    Bitmap fruit5_img;
    Bitmap fruit6_img;
    Bitmap fruit7_img;
    Bitmap cut;
    ImageView drop;
    RelativeLayout shop1;
    RelativeLayout shop2;
    int score=0;
    private int total_time_left=30;
    int level_wait_time=5;
    RelativeLayout callibration_screen;
    TextView callibration_text;
    int result;
    int progress;
    ImageView village;
    all_fruits fruits= new all_fruits();
    private boolean animation_run=false;
    private Canvas canvas_game_canvas;
    private Bitmap bitmap_game_canvas;
    ImageView imageView_game_canvas;
    private TextView score_text;
    TextView time;
    int Level_Score=15;
    private ProgressBar progressBar;
    RelativeLayout gameview;
    Boolean finish_bool=false;
    Boolean end_bool=false;
    Boolean once=false;
    RelativeLayout outofboundary;
    RelativeLayout result_screen;
    ImageView shop3;
    RelativeLayout multiple_faces;
    MediaPlayer mp_cut;
    MediaPlayer mp_time;
    MediaPlayer mp_game_over;
    MediaPlayer mp_game_win;
    MediaPlayer mp_background;
    private float back_pressed=0;
    int game_over;
    CountDownTimer tim;
    int level_complete;
    private FirebaseAnalytics firebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rand=new Random();
        setContentView(R.layout.activity_gameplay_0);
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        previewView=findViewById(R.id.camera_view);
        previewView.setVisibility(View.VISIBLE);
        drop=findViewById(R.id.juicedrop);
        mp_cut=MediaPlayer.create(this, R.raw.fruit_cut_sound);
        mp_time=MediaPlayer.create(this, R.raw.low_time);
        mp_game_over=MediaPlayer.create(this, R.raw.loose_sound);
        mp_game_win=MediaPlayer.create(this, R.raw.win_sound);
        mp_background=MediaPlayer.create(this, R.raw.doozy_sound);
        cut= BitmapFactory.decodeResource(getResources(), R.drawable.orange_cut);
        outofboundary=findViewById(R.id.out_of_boundary);
        multiple_faces=findViewById(R.id.multiple_faces);
        gameview=findViewById(R.id.gameview);
        callibration_screen=findViewById(R.id.callibration_screen);
        callibration_text=findViewById(R.id.call_text);
        bitmap_game_canvas = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap_game_canvas = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        imageView_game_canvas = (ImageView) findViewById(R.id.image_view);
        imageView_game_canvas.setImageBitmap(bitmap_game_canvas);
        canvas_game_canvas = new Canvas(bitmap_game_canvas);
        score_text=findViewById(R.id.text_score);
        time=findViewById(R.id.time);
        progressBar=findViewById(R.id.progress);
        fruits.width=width/7;
        fruits.height=width/7;
        result_screen=findViewById(R.id.result_screen);
        shop3=findViewById(R.id.shop3);
        update_fruits();
      start();
    }
    protected void onResume() {
        start();
        mp_cut=MediaPlayer.create(this, R.raw.fruit_cut_sound);
        mp_time=MediaPlayer.create(this, R.raw.low_time);
        mp_game_over=MediaPlayer.create(this, R.raw.loose_sound);
        mp_game_win=MediaPlayer.create(this, R.raw.win_sound);
        mp_background=MediaPlayer.create(this, R.raw.doozy_sound);
        super.onResume();
    }
    protected void onPause() {
            if(tim!=null) 
tim.cancel();
        if ( mp_cut!=null)
            mp_cut.stop();
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
    public void control2(){
        Log.i("shutdown", "control2: here");
        if (!callibration)
        {
            callibrate();
            return;
        }
        if(finish_bool || end_bool)
            return;
        if(face_count>1 || outside_frame_time<-10 )
            return;
        if(started && callibration)
        {
            Log.i("control2", "control2: here");
            int x = (int) (translatex(leftWrist.getPosition().x) + translatex(leftThumb.getPosition().x) + translatex(leftIndex.getPosition().x) + translatex(leftPinky.getPosition().x)) / 4;
            int y = (int) (translatey(leftWrist.getPosition().y) + translatey(leftThumb.getPosition().y) + translatey(leftIndex.getPosition().y) + translatey(leftPinky.getPosition().y)) / 4;
            fruits.check(x, y);
            x = (int) (translatex(rightWrist.getPosition().x) + translatex(rightThumb.getPosition().x) + translatex(rightIndex.getPosition().x) + translatex(rightPinky.getPosition().x)) / 4;
            y = (int) (translatey(rightWrist.getPosition().y) + translatey(rightThumb.getPosition().y) + translatey(rightIndex.getPosition().y) + translatey(rightPinky.getPosition().y)) / 4;
            fruits.check(x, y);
        }
    }
    private void control() {
        Log.i("shutdown", "control2: here");
        multiple_faces.setVisibility(View.INVISIBLE);
        outofboundary.setVisibility(View.INVISIBLE);
        Log.i("progress", "control: progress"+progress+"cal"+callibration);
        if (!callibration)
        {

        }
        else if (finish_bool && !once){
            SharedPreferences sharedPreferences = getSharedPreferences("level"+result,MODE_PRIVATE);
            SharedPreferences prefs = getSharedPreferences("level"+result, MODE_PRIVATE);
            int progress2 = prefs.getInt("myInt", 1);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            Intent intent;
            Log.i("progress_Value2", "update_fruits: "+progress2);
            if(progress==progress2)
            {  progress++;
                myEdit.putInt("myInt", progress);
                Log.i("new_area_issue", "control: here");
            myEdit.commit();
                if(progress<=7)
                {
                    intent = new Intent(gameplay_0.this, menu.class);
                    intent.putExtra("num2",1);
                    intent.putExtra("level",result);
                }
                else if (progress==8 && result<5){
                    intent = new Intent(gameplay_0.this, Map.class);
                    intent.putExtra("new_area",1);
                    result++;
                    intent.putExtra("val",result);
                    Log.i("new_area_issue", "control: here1");
                }
                else{
                    intent = new Intent(gameplay_0.this, menu.class);
                    intent.putExtra("level",result);
                }
            }
            else{
                intent = new Intent(gameplay_0.this, menu.class);
                intent.putExtra("level",result);
            }
            Animation aniFade = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
            aniFade.setDuration(1200);
            previewView.setVisibility(View.INVISIBLE);
            result_screen.setBackgroundResource(level_complete);
            result_screen.setAnimation(aniFade);
            gameview.setVisibility(View.INVISIBLE);
            imageView_game_canvas.setVisibility(View.INVISIBLE);
            result_screen.setVisibility(View.VISIBLE);
            Log.i("value", "control: "+progress+" "+result);
            RelativeLayout timer_temp=findViewById(R.id.timer);
            timer_temp.setVisibility(View.INVISIBLE);
            once=true;
            score_text.setVisibility(View.INVISIBLE);
            Animation animation = new TranslateAnimation(0, width/4+20-progressBar.getWidth()/2,0, -50);
            animation.setDuration(1200);
            animation.setFillAfter(true);
            progressBar.startAnimation(animation);
            Button continue_temp=findViewById(R.id.continue_button);
            if(mp_time.isPlaying()){
                mp_time.stop();
            }
            if(mp_background.isPlaying()){
                mp_background.stop();
            }
            if(!mp_game_win.isPlaying()){
                mp_game_win.start();
            }
            continue_temp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    if(tim!=null) 
tim.cancel();
                    finish();
                    if(mp_game_win.isPlaying()){
                        mp_game_win.stop();
                    }
                }
            });
        }
        else if(end_bool && !once){
            Animation aniFade = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
            aniFade.setDuration(1200);
            previewView.setVisibility(View.INVISIBLE);
            result_screen.setBackgroundResource(game_over);
            result_screen.setAnimation(aniFade);
            gameview.setVisibility(View.INVISIBLE);
            imageView_game_canvas.setVisibility(View.INVISIBLE);
            result_screen.setVisibility(View.VISIBLE);
            score_text.setVisibility(View.INVISIBLE);
            Log.i("value", "control: "+progress+" "+result);
            RelativeLayout timer_temp=findViewById(R.id.timer);
            progressBar.setVisibility(View.INVISIBLE);
            Animation animation = new TranslateAnimation(0, -3*width/4-20+timer_temp.getWidth()/2,0, height/2-timer_temp.getHeight()/2);
            animation.setDuration(1200);
            animation.setFillAfter(true);
            timer_temp.startAnimation(animation);
            once=true;
            Button continue_temp=findViewById(R.id.continue_button);
            Intent intent = new Intent(gameplay_0.this, menu.class);
            intent.putExtra("level",result);
            if(mp_background.isPlaying()){
                mp_background.stop();
            }
            if(!mp_game_over.isPlaying()){
                mp_game_over.start();
            }
            if(mp_time.isPlaying()){
                mp_time.stop();
            }
            continue_temp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mp_game_over.isPlaying()){
                        mp_game_over.stop();
                    }
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    if(tim!=null) 
tim.cancel();
                    finish();
                }
            });

        }
//        else if (face_count>1 && !once){
//            multiple_faces.setVisibility(View.VISIBLE);
//            if(!mp_time.isPlaying()){
//                mp_time.pause();
//            }
//            return;
//        }
        else  if (outside_frame_time<-10 && !once) {
            if(mp_time.isPlaying()){
                mp_time.pause();
            }
            outofboundary.setVisibility(View.VISIBLE);
            return;}
        else if (started && !once){
            time_manager();
            multiple_faces.setVisibility(View.INVISIBLE);
            outofboundary.setVisibility(View.INVISIBLE);
            canvas_game_canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            time.setText(String.valueOf(total_time_left));
            if(total_time_left==0 && progress!=0)
                end_bool=true;
            Log.i("try", "control: here");
            fruits.add_new(width, height);
            fruits.draw(canvas_game_canvas);
            score_text.setText((score*100/Level_Score) + "%");
            if (score > 400)
                score = 400;
            if (score*100/(Level_Score*1.2)>80)
            {
                progressBar.setProgress(80);
                finish_bool=true;
            }
            else {
                progressBar.setProgress((int) (score * 100 / (Level_Score * 1.2)));
            }
        }
        imageView_game_canvas.setImageBitmap(bitmap_game_canvas);
    }
    class all_fruits{
        ArrayList<fruit> fruits=new ArrayList<>();
        int width;
        int height;
        public void draw(Canvas canvas){
            int i=0;
            while (i<fruits.size()){
                if (fruits.get(i).time_left>0)
                {
                    fruits.get(i).draw(canvas,width,height);
                    i++;
                }
                else {
                    fruits.remove(i);
                }
            }
        }
        public void add_new (int width,int height){
            Random random=new Random();
            Log.i("try", "add_new: here2");
            int temp1 = progress;
            if(temp1>7){
                temp1=rand.nextInt(8);
            }
            int temp = rand.nextInt(5);
            if (count<5 && temp==2 && total_count<Level_Score ) {
                Bitmap temp2;
                count++;
                total_count++;
                switch (temp1) {
                    case 2:
                        temp2 = fruit2_img;
                        break;
                    case 3:
                        temp2 = fruit3_img;
                        break;
                    case 4:
                        temp2 = fruit4_img;
                        break;
                    case 5:
                        temp2 = fruit5_img;
                        break;
                    case 6:
                        temp2 = fruit6_img;
                        break;
                    case 7:
                        temp2 = fruit7_img;
                        break;
                    default:
                        temp2 = fruit1_img;
                        break;
                }
                fruit temp_fruits=new fruit(rand.nextInt(4*width/5)+width/10, rand.nextInt(3*height/5)+height/10, temp2, 10);
                fruits.add(temp_fruits);
            }
        }
        public void check(float x, float y ){
            int i=0;
            Log.i("someissue", "here-1");
            Animation animation = new TranslateAnimation(0, 0, 0, (float) (height*2));
            Animation animationcheck = drop.getAnimation();
            Log.i("someissue", "here-2");
            while (i<fruits.size())
            {
                if (fruits.get(i).end>0)
                { Log.i("someissue", "here0");
                    if (fruits.get(i).end>3)
                    {    animation_status=false;
                        fruits.remove(i);
                    }
                    else{
                        i++;
                    }
                }
                else if (mod(fruits.get(i).x-x)<150 && mod(fruits.get(i).y-y)<150)
                {  Log.i("someissue", "here");
                    count--;

                if (!mp_cut.isPlaying())
                { mp_cut.start();}

                    if (!(animationcheck != null && animationcheck.hasStarted() && !animationcheck.hasEnded()))
                    { drop.setVisibility(View.VISIBLE);
                        drop.setAnimation(animation);
                        animation.setDuration(300);}
                    fruits.get(i).end=1;
                    animation_status=true;
                    score++;
                }
                else
                {
                    i++;
                    if (!(animationcheck != null && animationcheck.hasStarted() && !animationcheck.hasEnded()))
                    {  drop.setVisibility(View.INVISIBLE);}
                }
            }
        }
    }
    public float mod(float a) {
        if (a>0)
            return a;
        return -1*a;
    }
    class fruit{
        int x;
        int y;
        Bitmap image;
        int time_left;
        int start=4;
        int end=0;
        public fruit(int x, int y, Bitmap image, int time_left) {
            this.x = x;
            this.y = y;
            this.image = image;
            this.time_left = time_left;
        }
        public void draw(Canvas canvas, int width,int height){
            if (start>0)
            {  animation_status=true;
                Rect temp= new Rect(x-width/(2*start),y-height/(2*start),x+width/(2*start),y+height/(2*start));
                canvas.drawBitmap(image,null,temp,null);
                start--;
            }
            else if (end>0)
            {   animation_status=true;
                Rect temp= new Rect(x-width/(2*end),y-height/(2*end),x+width/(2*end),y+height/(2*end));
                canvas.drawBitmap(image,null,temp,null);
                canvas.drawBitmap(cut,null,temp,null);
                end++;
            }
            else {
                animation_status=false;
                Rect temp= new Rect(x-width/(2),y-height/(2),x+width/(2),y+height/(2));
                canvas.drawBitmap(image,null,temp,null);
            }
        }
    }
    private void time_manager() {
        Date current_time = Calendar.getInstance().getTime();
        if (last_second!=-100 && last_second!=current_time.getTime()/1000 && callibration && started)
        {total_time_left--;}
        else if (last_second!=-100 && last_second!=current_time.getTime()/1000 && callibration)
        {
            level_wait_time--;
        }
        if (total_time_left<0)
        { total_time_left=0;
            if (mp_time.isPlaying())
                mp_time.stop();
        }
        if (total_time_left<10){
            if (!mp_time.isPlaying())
                mp_time.start();
        }
        last_second= (int) (current_time.getTime()/1000);
    }
    private void callibrate() {
        callibration_screen.setVisibility(View.VISIBLE);
        if (callibration==false)
        {  Log.i("progress", "control2:1 ");
            setTime_for_callibration();
            callibration_text.setText(getTime_for_callibration());
            if (time_for_callibration>10)
            { Log.i("progress", "control2:0 "+callibration);
                callibration_screen.setVisibility(View.INVISIBLE);
                callibration=true;
                Log.i("progress", "control2:1 "+callibration);
            gameview.setVisibility(View.VISIBLE);
                Log.i("progress", "control2:2 "+callibration);

                Log.i("progress", "control2:3 "+callibration);}
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
    private void update_fruits() {
        shop1=findViewById(R.id.shop1);
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        Intent intent = getIntent();
        result = intent.getIntExtra("num",1);
        fruit1_img= BitmapFactory.decodeResource(getResources(), R.drawable.apple);
        fruit2_img= BitmapFactory.decodeResource(getResources(), R.drawable.banana);
        fruit3_img= BitmapFactory.decodeResource(getResources(), R.drawable.orange);
        if (result==1) {
            fruit4_img=BitmapFactory.decodeResource(getResources(), R.drawable.grapefruit);
            fruit5_img=BitmapFactory.decodeResource(getResources(), R.drawable.grapes);
            fruit6_img=BitmapFactory.decodeResource(getResources(), R.drawable.pomegranate);
            fruit7_img=BitmapFactory.decodeResource(getResources(), R.drawable.coconut);
            progress = intent.getIntExtra("num2",1);
            if (progress==4){
                cut=BitmapFactory.decodeResource(getResources(),R.drawable.red_cut);
                drop.setImageResource(R.drawable.red_juice_drop);
                progressBar.setProgressDrawable(getResources().getDrawable(R.drawable. progressbar_photo_red));
            }
            else if (progress==5){
                cut=BitmapFactory.decodeResource(getResources(),R.drawable.blue_cut);
                drop.setImageResource(R.drawable.blue_juice_drop);
                progressBar.setProgressDrawable(getResources().getDrawable(R.drawable. progressbar_photo_blue));
            }
            else if (progress==6){
                cut=BitmapFactory.decodeResource(getResources(),R.drawable.red_cut);
                drop.setImageResource(R.drawable.red_juice_drop);
                progressBar.setProgressDrawable(getResources().getDrawable(R.drawable. progressbar_photo_red)); 
                 
            }

            else if (progress==7){
                cut=BitmapFactory.decodeResource(getResources(),R.drawable.green_cut);
                drop.setImageResource(R.drawable.green_juice_drop);
                progressBar.setProgressDrawable(getResources().getDrawable(R.drawable. progressbar_photo_green));  
                 
            }
            game_over=R.drawable.over_1;
            level_complete=R.drawable.complete_1;
        }
        else if (result==2) {
            fruit4_img=BitmapFactory.decodeResource(getResources(), R.drawable.cherries);
            fruit5_img=BitmapFactory.decodeResource(getResources(), R.drawable.mango);
            fruit6_img=BitmapFactory.decodeResource(getResources(), R.drawable.strawberries);
            fruit7_img=BitmapFactory.decodeResource(getResources(), R.drawable.blueberries);
            progress = intent.getIntExtra("num2",1); // 0 is default
            callibration_screen.setBackgroundResource(R.drawable.callibration_2);
            shop1.setBackgroundResource(R.drawable.shop_2);
            shop3.setImageResource(R.drawable.shop_2);
            result_screen.setBackgroundResource(R.drawable.city_background);
            game_over=R.drawable.over_2;
            level_complete=R.drawable.complete_2;
            if (progress==4){
                cut=BitmapFactory.decodeResource(getResources(),R.drawable.red_cut);
                drop.setImageResource(R.drawable.red_juice_drop);
                progressBar.setProgressDrawable(getResources().getDrawable(R.drawable. progressbar_photo_red));
            }
            else if (progress==5){
                cut=BitmapFactory.decodeResource(getResources(),R.drawable.yellow_cut);
                drop.setImageResource(R.drawable.yellow_juice_drop);
                progressBar.setProgressDrawable(getResources().getDrawable(R.drawable. progressbar_photo_yellow));  
            }
            else if (progress==6){
                cut=BitmapFactory.decodeResource(getResources(),R.drawable.red_cut);
                drop.setImageResource(R.drawable.red_juice_drop);
                progressBar.setProgressDrawable(getResources().getDrawable(R.drawable. progressbar_photo_red));  
            }

            else if (progress==7){
                cut=BitmapFactory.decodeResource(getResources(),R.drawable.black_cut);
                drop.setImageResource(R.drawable.black_juice_drop);
                progressBar.setProgressDrawable(getResources().getDrawable(R.drawable. progressbar_photo_black));  
            }
        }
        else if (result==3) {
            fruit4_img=BitmapFactory.decodeResource(getResources(), R.drawable.blackberries);
            fruit5_img=BitmapFactory.decodeResource(getResources(), R.drawable.blueberries);
            fruit6_img=BitmapFactory.decodeResource(getResources(), R.drawable.watermelon);
            fruit7_img=BitmapFactory.decodeResource(getResources(), R.drawable.pear);
            progress = intent.getIntExtra("num2",1);
            callibration_screen.setBackgroundResource(R.drawable.callibration_3);
            shop1.setBackgroundResource(R.drawable.shop_3);
            shop3.setImageResource(R.drawable.shop_3);
            result_screen.setBackgroundResource(R.drawable.beach_background);
            game_over=R.drawable.over_3;
            level_complete=R.drawable.complete_3;
            if (progress==4){
                cut=BitmapFactory.decodeResource(getResources(),R.drawable.blue_cut);
                drop.setImageResource(R.drawable.blue_juice_drop);
                progressBar.setProgressDrawable(getResources().getDrawable(R.drawable. progressbar_photo_blue));  
            }
            else if (progress==5){
                cut=BitmapFactory.decodeResource(getResources(),R.drawable.black_cut);
                drop.setImageResource(R.drawable.black_juice_drop);
                progressBar.setProgressDrawable(getResources().getDrawable(R.drawable. progressbar_photo_black));  
            }
            else if (progress==6){
                cut=BitmapFactory.decodeResource(getResources(),R.drawable.green_cut);
                drop.setImageResource(R.drawable.green_juice_drop);
                progressBar.setProgressDrawable(getResources().getDrawable(R.drawable. progressbar_photo_green));  
            }

            else if (progress==7){
                cut=BitmapFactory.decodeResource(getResources(),R.drawable.green_cut);
                drop.setImageResource(R.drawable.green_juice_drop);
                progressBar.setProgressDrawable(getResources().getDrawable(R.drawable. progressbar_photo_green));
            }
        }
        else if (result==4) {
            fruit4_img=BitmapFactory.decodeResource(getResources(), R.drawable.papaya);
            fruit5_img=BitmapFactory.decodeResource(getResources(), R.drawable.guava);
            fruit6_img=BitmapFactory.decodeResource(getResources(), R.drawable.kiwi);
            fruit7_img=BitmapFactory.decodeResource(getResources(), R.drawable.melon);
            progress = intent.getIntExtra("num2",1);
            callibration_screen.setBackgroundResource(R.drawable.callibration_4);
            shop1.setBackgroundResource(R.drawable.shop_4);
            shop3.setImageResource(R.drawable.shop_4);
            result_screen.setBackgroundResource(R.drawable.amusement_background);
            game_over=R.drawable.over_4;
            level_complete=R.drawable.complete_4;
            if (progress==4){
                cut=BitmapFactory.decodeResource(getResources(),R.drawable.orange_cut);
            }
            else if (progress==5){
                cut=BitmapFactory.decodeResource(getResources(),R.drawable.green_cut);
                drop.setImageResource(R.drawable.green_juice_drop);
                progressBar.setProgressDrawable(getResources().getDrawable(R.drawable. progressbar_photo_green));  
            }
            else if (progress==6){
                cut=BitmapFactory.decodeResource(getResources(),R.drawable.green_cut);
                drop.setImageResource(R.drawable.green_juice_drop);
                progressBar.setProgressDrawable(getResources().getDrawable(R.drawable. progressbar_photo_green));  
            }

            else if (progress==7){
                cut=BitmapFactory.decodeResource(getResources(),R.drawable.green_cut);
                drop.setImageResource(R.drawable.green_juice_drop);
                progressBar.setProgressDrawable(getResources().getDrawable(R.drawable. progressbar_photo_green));  
            }
        }
        else if (result==5) {
            fruit4_img=BitmapFactory.decodeResource(getResources(), R.drawable.lemon);
            fruit5_img=BitmapFactory.decodeResource(getResources(), R.drawable.raspberries);
            fruit6_img=BitmapFactory.decodeResource(getResources(), R.drawable.plum);
            fruit7_img=BitmapFactory.decodeResource(getResources(), R.drawable.cranbery);
            progress = intent.getIntExtra("num2",1);
            shop1.setBackgroundResource(R.drawable.shop_5);
            shop3.setImageResource(R.drawable.shop_5);
            callibration_screen.setBackgroundResource(R.drawable.callibration_5);
            result_screen.setBackgroundResource(R.drawable.hotel_background);
            game_over=R.drawable.over_5;
            level_complete=R.drawable.complete_5;
            if (progress==4){
                cut=BitmapFactory.decodeResource(getResources(),R.drawable.yellow_cut);
                drop.setImageResource(R.drawable.yellow_juice_drop);
                progressBar.setProgressDrawable(getResources().getDrawable(R.drawable. progressbar_photo_yellow));  
            }
            else if (progress==5){
                cut=BitmapFactory.decodeResource(getResources(),R.drawable.red_cut);
                drop.setImageResource(R.drawable.red_juice_drop);
                progressBar.setProgressDrawable(getResources().getDrawable(R.drawable. progressbar_photo_red));  
            }
            else if (progress==6){
                cut=BitmapFactory.decodeResource(getResources(),R.drawable.purple_cut);
                drop.setImageResource(R.drawable.purple_juice_drop);
                progressBar.setProgressDrawable(getResources().getDrawable(R.drawable. progressbar_photo_purple));  
            }

            else if (progress==7){
                cut=BitmapFactory.decodeResource(getResources(),R.drawable.red_cut);
                drop.setImageResource(R.drawable.red_juice_drop);
                progressBar.setProgressDrawable(getResources().getDrawable(R.drawable. progressbar_photo_red));  
            }
        }
        if (progress==1){
            cut=BitmapFactory.decodeResource(getResources(),R.drawable.red_cut);
            drop.setImageResource(R.drawable.red_juice_drop);
            progressBar.setProgressDrawable(getResources().getDrawable(R.drawable. progressbar_photo_red));
        }
        else if (progress==2){
            cut=BitmapFactory.decodeResource(getResources(),R.drawable.yellow_cut);
            drop.setImageResource(R.drawable.yellow_juice_drop);
            progressBar.setProgressDrawable(getResources().getDrawable(R.drawable. progressbar_photo_yellow));  
        }
        else if (progress==3){
            cut=BitmapFactory.decodeResource(getResources(),R.drawable.orange_cut);
        }
        Bundle bundle = new Bundle();
         firebaseAnalytics = FirebaseAnalytics.getInstance(this);
 firebaseAnalytics = FirebaseAnalytics.getInstance(this);
firebaseAnalytics.logEvent("game_play_0 result"+result+" progress"+progress, bundle);
        Level_Score=20+result + 14*progress;
        Log.i("progress_Value", "update_fruits: "+progress);
    }
    boolean doubleBackToExitPressedOnce = false;
    private Toast toast = null;
    public void onBackPressed() {
        final Dialog dialog = new Dialog(gameplay_0.this);

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
                Intent i=new Intent(gameplay_0.this,menu.class);
                i.putExtra("level",result);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                if(tim!=null) 
tim.cancel();
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
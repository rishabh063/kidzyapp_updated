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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Timer;

public class gameplay_2 extends com.kidzyfit.kidzyapp.base {
   ImageView imageView;
    public Bitmap bitmap;
    Canvas canvas;
    Paint paint;
    all_platform platform=new all_platform();
    player player=new player();
    Random rand;
    int previous_y=0;
    Bitmap cherry;
    Bitmap ladder;
    float progress=10;
    boolean last=false;
    ImageView progress_image;
    Bitmap finish;
    boolean finish_bool=false;
    RelativeLayout outofboundary;
    RelativeLayout multiple_faces;
    Bitmap trampoline;
    private int last_second=-100;
    private boolean callibration=false;
    private boolean started=true;
    int total_time_left=120;
    ImageView target;
    int max_target;
    TextView time_text;
    int seed=2;
    int multiplier =3;
    int add=1;
    TextView score_text;
    int score=0;
    boolean set_first=true;
    int progress_start;
    private int modulus=16;
    RelativeLayout game_over;
    RelativeLayout finish_level;
    RelativeLayout callibration_screen;
    int wait_time=0;
    RelativeLayout Gameplay;
    boolean platform_stat=false;
    boolean ladder_stat=false;
    Timer timer;
    boolean jump=false;
    boolean moving=false;
    private int time_for_callibration=0;
    private TextView callibration_text;
    Button bt1;
    Button bt2;
    private Activity act;
    int level;
    private float back_pressed=0;
    private boolean once=false;
    MediaPlayer mp_jump;
    MediaPlayer mp_ladder;
    MediaPlayer mp_time;
    MediaPlayer mp_game_over;
    MediaPlayer mp_game_win;
    MediaPlayer mp_background;
    CountDownTimer tim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act=this;
        setContentView(R.layout.activity_gameplay_2);
        previewView=findViewById(R.id.preview);
        imageView=findViewById(R.id.canvas);
        callibration_screen=findViewById(R.id.callibration_screen);
        Gameplay=findViewById(R.id.game_view);
        callibration_text=findViewById(R.id.call_text);
        Intent intent = getIntent();
        seed = intent.getIntExtra("seed",1);
        modulus=intent.getIntExtra("modulus",7);
        multiplier=intent.getIntExtra("multi",3);
        level=intent.getIntExtra("level",1);
        rand=new Random();
        score_text=findViewById(R.id.score_text);
        bt1=findViewById(R.id.level_Complete_button);
        bt2=findViewById(R.id.level_over_button);
        game_over=findViewById(R.id.game_over);
        mp_jump=MediaPlayer.create(this, R.raw.jump_sound);
        mp_ladder=MediaPlayer.create(this, R.raw.ladder_sound);
        mp_time=MediaPlayer.create(this, R.raw.low_time);
        mp_game_over=MediaPlayer.create(this, R.raw.loose_sound);
        mp_game_win=MediaPlayer.create(this, R.raw.win_sound);
        mp_background=MediaPlayer.create(this, R.raw.doozy_sound);
        finish_level=findViewById(R.id.level_complete);
        outofboundary=findViewById(R.id.out_of_boundary);
        multiple_faces=findViewById(R.id.multiple_faces);
        progress_image=findViewById(R.id.progress);
        cherry=BitmapFactory.decodeResource(getResources(),R.drawable.cherry);
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        imageView.setImageBitmap(bitmap);
        canvas = new Canvas(bitmap);
        target=findViewById(R.id.target);
        time_text=findViewById(R.id.time_text);
        max_target=height;
        ladder=BitmapFactory.decodeResource(getResources(),R.drawable.ladder);
        finish=BitmapFactory.decodeResource(getResources(),R.drawable.finish);
        paint=new Paint();
        paint.setARGB(255,20,200,20);
        platform.jump_dist=2*height/7;
        player.bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.player);
        trampoline=BitmapFactory.decodeResource(getResources(),R.drawable.trampoline);
        player.width=width/8;
        player.height=width/8;
        player.x=width/2;
        player.y=height-platform.jump_dist-player.height;
        player.min_y=6*height/7-player.width/2-50;
        ImageView imageView = findViewById(R.id.gif);
       start();
    }
    private void control() {
        Log.i("valinc", "control: here0");
     if (once)
         return;
        Log.i("jump", "control: jumplast"+player.jump_last);
        multiple_faces.setVisibility(View.INVISIBLE);
        outofboundary.setVisibility(View.INVISIBLE);
        game_over.setVisibility(View.INVISIBLE);
        finish_level.setVisibility(View.INVISIBLE);
        Log.i("valinc", "control: here1");
        if (!callibration && time_for_callibration>10){
            callibration_screen.setVisibility(View.INVISIBLE);
            Gameplay.setVisibility(View.VISIBLE);
            callibration=true;
        }
        else if (!callibration)
        {
            return;
        }
        Log.i("valinc", "control: here2");
        if(set_first)
        {
            max_target=height-time_text.getBottom()-20;
            progress_start= 0;
            Log.i("info", height+"control: "+max_target+" "+progress_start);
            set_first=false;
        }
        if (finish_bool && !once)
        {     Log.i("valinc", "control: here3");
            TextView time_text=findViewById(R.id.time_text_new);
            ImageView temp=findViewById(R.id.level_complete_image);
            Glide.with(this).asGif().load(R.raw.level_complete_2).into(temp);
            Animation aniFade = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
            aniFade.setDuration(1200);
            finish_level.setAnimation(aniFade);
            finish_level.setVisibility(View.VISIBLE);
            SharedPreferences prefs = getSharedPreferences("gameplay_2", MODE_PRIVATE);
            int progress = prefs.getInt("myInt", 1); // 0 is default
            SharedPreferences.Editor myEdit = prefs.edit();
            Log.i("issue4", "onClick: "+level+" "+progress);
            if (progress<=level)
            {myEdit.putInt("myInt",level+1 );
                myEdit.commit();}
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
                    if(mp_game_win.isPlaying()){
                        mp_game_win.stop();
                    }
                    Intent intent = new Intent(act, level_screen_2.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }
            });
            once=true;
            return;
        }
        else if (total_time_left==0  && !once)
        {    Log.i("valinc", "control: here4");
            TextView time_text=findViewById(R.id.time_text_new);
            Log.i("game_over_isuue", "control: here");
            ImageView temp=findViewById(R.id.game_over_img);
            Glide.with(this).asGif().load(R.raw.game_over_2).into(temp);
            Log.i("game_over_isuue", "control:` here1");
            Animation aniFade = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
            aniFade.setDuration(1200);
            Animation animation = new TranslateAnimation(0, -width/4-20+time_text.getWidth()/2,0, height/3-time_text.getHeight()/2);
            animation.setDuration(2400);
            animation.setFillAfter(true);
            time_text.startAnimation(animation);
            game_over.setAnimation(aniFade);
            game_over.setVisibility(View.VISIBLE);
            Log.i("game_over_isuue", "control: here2");
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
                    Intent intent = new Intent(act, level_screen_2.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }
            });
            once=true;
            return;}
        else if (face_count>1 && !once){
            Log.i("valinc", "control: here5");
            multiple_faces.setVisibility(View.VISIBLE);
            if(!mp_time.isPlaying()){
                mp_time.pause();
            }
            return;
        }
       else  if (outside_frame_time<-10 && !once)
        {  Log.i("valinc", "control: here6");
            outofboundary.setVisibility(View.VISIBLE);
            if(!mp_time.isPlaying()){
                mp_time.pause();
            }
            return;}
       else if( !once)
        {  Log.i("valinc", "control: here7");
            time_manager();
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            platform.delete(height);
            if (!last) {
                platform.add_new(width);
            } else {
                platform.last(width);
            }
            platform.move();
            player.update_min(height);
            platform.draw(canvas);
            moving = false;
            if (player.y <= 4 * height / 10) {
                platform.speed = 15;
                progress += 1.7;
                moving = true;
            }
            if (progress >= 4 * max_target / 5)
                last = true;
            if (progress >= max_target) {
                progress = max_target;
            }
            player.draw(canvas);
            player.update();
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, (int) (progress_start + progress));
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            params.width = 50;
            params.height = 50;
            progress_image.setLayoutParams(params);
            imageView.setImageBitmap(bitmap);
        }
    }
    public void control2(){
        Log.i("shutdown", "control2: here");
        control();
        if (once || outside_frame_time < -10 || face_count>1){
            return;
        }
        if (time_for_callibration<=10 && !callibration)
        { setTime_for_callibration();
            callibration_text.setText(getTime_for_callibration());
            return;
        }

        wait_time++;
        Log.i("somehere", "draw: here-1");
        int y=  (int) (translatey(rightShoulder.getPosition().y) +translatey(leftShoulder.getPosition().y))/2;

        if (((float)previous_y-(float)y)/height>0.1 && previous_y!=0  && (platform_stat))
        {
            player.jump(height,0);
            jump=true;
            if (!mp_jump.isPlaying()){
                mp_jump.start();
            }
        }
        previous_y=y;
            int x= (int) translatex(nose.getPosition().x);
            y=  (int) (translatex(rightShoulder.getPosition().x) +translatex(leftShoulder.getPosition().x))/2;
            x=(x+y)/2;
            if (x+player.width/2>0 && x+player.width/2<width )
                player.x=x;
            if (!platform_stat)
                start();
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
    class point{
        int x , y;
        float width,height;
        Bitmap bitmap;

        public point(int x, int y, float width, float height, Bitmap bitmap) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.bitmap = bitmap;
        }

        public void draw(Canvas canvas , int h ){
            canvas.drawBitmap(bitmap,null,new Rect((int)(x-width/2), (int) (y-height/2), (int)(x+width/2), (int)(y+height/2)),null);
        }

    }
    class platform{
        int x , y;
        int width,height;
        Paint paint;
        Boolean cherry_status=false;
        point cherry;
        boolean last_status=false;
        point last;
        boolean ladder_status=false;
        point ladder_point;
        boolean trampoline_status=false;
        point trampoline_point;
        public void draw(Canvas canvas ){
            Log.i("somehere", "draw: here"+last_status);
            if (last_status){
                Log.i("somehere2", "draw: here"+last.x+" "+last.y+" "+last.height+" "+last.width+" "+last.bitmap);
                last.draw(canvas,height);
                return;
            }
            else {
                if (ladder_status) {
                        ladder_point.draw(canvas,height);
                    }
                if (cherry_status) {
                    cherry.draw(canvas,height);
                }
                if (trampoline_status) {
                    trampoline_point.draw(canvas,height);
                }
                }
            canvas.drawRoundRect(new RectF(x-width/2, y-height/2, x+width/2, y+height/2), 50, 50, this.paint);

        }
        public void add_point(int width,Bitmap bitmap){
            if (rand.nextInt(2)!=1)
                return;
            if (width<=0)
                return;
            int temp_x=x+rand.nextInt(this.width/4-20);
            if (rand.nextInt(2)==1)
                temp_x=x+rand.nextInt(this.width/4-20);
            int temp_y=y-this.height/2-width/16;
            int temp_width=width/8;
            int temp_height=width/8;
            Bitmap temp_bitmap=bitmap;
            cherry=new point(temp_x,temp_y,temp_width,temp_height,temp_bitmap);
            cherry_status=true;
        }
        public void add_last(int width,Bitmap bitmap){
            int temp_x=x+rand.nextInt(this.width/4-20);
            Log.i("somehere", "add_last: here_addlast");
            if (rand.nextInt(2)==1)
                temp_x=x+rand.nextInt(this.width/4-20);
            int temp_y=y-this.height/2-width/16;
            int temp_width=width/7;
            int temp_height=width/7;
            Bitmap temp_bitmap=bitmap;
            last=new point(temp_x,temp_y,temp_width,temp_height,temp_bitmap);
            last_status=true;
        }
        public void add_ladder(int width, Bitmap bitmap, int i){
            int temp_x=i;
            Log.i("somehere", "add_last: here_addlast");
            float temp_height=platform.jump_dist*2+platform.jump_dist*(1/4);
            int temp_y= (int) (y-this.height/2-temp_height/2);

            float temp_width=3*temp_height/8;
            Bitmap temp_bitmap=ladder;
            ladder_point=new point(temp_x,temp_y,temp_width,temp_height,temp_bitmap);
            ladder_status=true;

        }
        public void add_trampoline(int width, int i){
            int temp_x_2=i;
            Log.i("somehere", "add_last: here_addlast");
            int temp_y_2=y-this.height/2-width/24;
            float temp_width_2=width/6;
            float temp_height_2=width/12;
            Bitmap temp_bitmap_2=trampoline;
            trampoline_point=new point(temp_x_2,temp_y_2,temp_width_2,temp_height_2,temp_bitmap_2);
            trampoline_status=true;
        }
    }
    class all_platform{
        ArrayList<platform> all_platforms=new ArrayList<>();
        int speed;
        int gravity=-4;
        int jump_dist;
        boolean first=true;
        boolean last=true;
        int abc=0;
        public void draw(Canvas canvas){
            for (int i = 0; i <all_platforms.size() ; i++) {
                Log.i("somehere", "draw: here0");
                all_platforms.get(i).draw(canvas);
            }
        }
        public void add_new(int width){

             int min=height;
             int mini=-1;
            for (int i = 0; i < all_platforms.size(); i++) {
                if (all_platforms.get(i).y<min){
                    Log.i("tagg", "add_new: "+min);
                    min=all_platforms.get(i).y;
                    mini=i;
                }
            }   if (min ==height)
            {
                set_difficulty(0,min);
            }
                else if (min>0)
                {
                    Log.i("issue5", "add_new: "+seed);
                    set_difficulty(seed , min);
                seed=(seed*multiplier+add)%modulus;
                add++;}


        }
        public void last(int width){
            if (!last)
                return;
            platform temp=new platform();
            int min=height;
            int mini=-1;
            for (int i = 0; i < all_platforms.size(); i++) {
                if (all_platforms.get(i).y<min){
                    Log.i("tagg", "add_new: "+min);
                    min=all_platforms.get(i).y;
                    mini=i;
                }
            }
            temp.y=min- jump_dist;
            temp.x=width/2;
            temp.width=width/7;
            temp.height=width/7;
            temp.last_status=true;
            temp.add_last(width,finish);
            all_platforms.add(temp);
            last=false;
            Log.i("abcd", "first: "+all_platforms.size());
        }
        public void move(){
            for (int i = 0; i < all_platforms.size() ; i++) {
                all_platforms.get(i).y+=speed;
                if (all_platforms.get(i).cherry_status)
                    all_platforms.get(i).cherry.y+=speed;
                if (all_platforms.get(i).last_status)
                       all_platforms.get(i).last.y+=speed;
                if (all_platforms.get(i).ladder_status)
                    all_platforms.get(i).ladder_point.y+=speed;
                if (all_platforms.get(i).trampoline_status)
                    all_platforms.get(i).trampoline_point.y+=speed;
            }
            speed+=gravity;
            if (speed<=0)
                speed=0;
        }
        public void delete(int height){
            int i=0;
            while (i<all_platforms.size())
            {
               if ( all_platforms.get(i).y>2*height)
                   all_platforms.remove(i);
               else
                   i++;
            }
            Log.i("size", "delete: "+all_platforms.size());
        }
        public void set_difficulty(int d , int min){
            platform temp=new platform();
            platform temp2=new platform();
            platform temp3=new platform();
            platform temp4=new platform();
            platform temp5=new platform();
            switch (d){
                case 0:
                    temp.y=min- jump_dist;
                    temp.width=(3*width/4);
                    temp.x=width/2;
                    temp.height=50;
                    temp.paint=paint;
                    temp.add_point(width,cherry);
                    all_platforms.add(temp);
                    break;
                case 1:
                    temp.y=min- jump_dist;
                    temp.width=(4*width/11);
                    temp.x=2*width/11;
                    temp.height=50;
                    temp.paint=paint;
                    temp.add_point(width,cherry);
                    all_platforms.add(temp);
                    temp2=new platform();
                    temp2.y=min- jump_dist;
                    temp2.width=(4*width/11);
                    temp2.x=9*width/11;
                    temp2.height=50;
                    temp2.paint=paint;
                    temp2.add_point(width,cherry);

                    all_platforms.add(temp2);
                    temp3=new platform();
                    temp3.y=min- 2*jump_dist;
                    temp3.width=(width/3);
                    temp3.x=width/2;
                    temp3.height=50;
                    temp3.paint=paint;
                    temp3.add_point(width,cherry);
                    all_platforms.add(temp3);
                    break;
                case 2:
                    temp.y=min- jump_dist;
                    temp.width=(4*width/18);
                    temp.x=2*width/18;
                    temp.height=50;
                    temp.paint=paint;
                    temp.add_point(width,cherry);
                    all_platforms.add(temp);
                    temp2=new platform();
                    temp2.y=min- jump_dist;
                    temp2.width=(4*width/18);
                    temp2.x=9*width/18;
                    temp2.height=50;
                    temp2.paint=paint;
                    temp2.add_point(width,cherry);
                    temp2.add_trampoline(width , 9*width/11);
                    all_platforms.add(temp2);
                    temp3=new platform();
                    temp3.y=min- jump_dist;
                    temp3.width=(4*width/18);
                    temp3.x=16*width/18;
                    temp3.height=50;
                    temp3.paint=paint;
                    temp3.add_point(width,cherry);
                    all_platforms.add(temp3);
                    temp4=new platform();
                    temp4.y=min- 2*jump_dist;
                    temp4.width=(2*width/7);
                    temp4.x=2*width/7;
                    temp4.height=50;
                    temp4.paint=paint;
                    temp4.add_point(width,cherry);
                    all_platforms.add(temp4);
                    temp5=new platform();
                    temp5.y=min- 2*jump_dist;
                    temp5.width=(2*width/7);
                    temp5.x=5*width/7;
                    temp5.height=50;
                    temp5.paint=paint;
                    temp5.add_point(width,cherry);
                    all_platforms.add(temp5);
                    break;
                case 3:
                    temp.y=min- jump_dist;
                    temp.width=(width/3);
                    temp.x=width/2;
                    temp.height=50;
                    temp.paint=paint;
                    temp.add_point(width,cherry);
                    all_platforms.add(temp);
                    temp2=new platform();
                    temp2.y=min- 2*jump_dist;
                    temp2.width=(width/3);
                    temp2.x=width/4;
                    temp2.height=50;
                    temp2.paint=paint;
                    temp2.add_point(width,cherry);
                    all_platforms.add(temp2);
                    temp3=new platform();
                    temp3.y=min- 2*jump_dist;
                    temp3.width=(width/3);
                    temp3.x=3*width/4;
                    temp3.height=50;
                    temp3.paint=paint;
                    temp3.add_point(width,cherry);
                    all_platforms.add(temp3);
                    temp4=new platform();
                    temp4.y=min- 3*jump_dist;
                    temp4.width=(width/3);
                    temp4.x=width/2;
                    temp4.height=50;
                    temp4.paint=paint;
                    temp4.add_point(width,cherry);
                    all_platforms.add(temp4);
                    break;
                case 4:
                    temp.y=min- jump_dist;
                    temp.width=(4*width/18);
                    temp.x=2*width/18;
                    temp.height=50;
                    temp.paint=paint;
                    temp.add_point(width,cherry);
                    all_platforms.add(temp);
                    temp2=new platform();
                    temp2.y=min- jump_dist;
                    temp2.width=(4*width/18);
                    temp2.x=9*width/18;
                    temp2.height=50;
                    temp2.paint=paint;
                    temp2.add_point(width,cherry);
                    all_platforms.add(temp2);
                    temp3=new platform();
                    temp3.y=min- jump_dist;
                    temp3.width=(4*width/18);
                    temp3.x=16*width/18;
                    temp3.height=50;
                    temp3.paint=paint;
                    temp3.add_point(width,cherry);
                    all_platforms.add(temp3);
                    break;
                case 5:
                    temp.y=min- jump_dist;
                    temp.width=(width/2);
                    temp.x=width/4;
                    temp.height=50;
                    temp.paint=paint;
                    temp.add_point(width,cherry);
                    all_platforms.add(temp);
                    temp2=new platform();
                    temp2.y=min- 2*jump_dist;
                    temp2.width=(width/4);
                    temp2.x=width/2;
                    temp2.height=50;
                    temp2.paint=paint;
                    temp2.add_point(width,cherry);
                    all_platforms.add(temp2);
                    break;
                case 6:
                    temp.y=min- jump_dist;
                    temp.width=(width/2);
                    temp.x=3*width/4;
                    temp.height=50;
                    temp.paint=paint;
                    temp.add_point(width,cherry);
                    temp2.add_trampoline(width , 3*width/4);
                    all_platforms.add(temp);
                    temp2=new platform();
                    temp2.y=min- 2*jump_dist;
                    temp2.width=(width/4);
                    temp2.x=width/2;
                    temp2.height=50;
                    temp2.paint=paint;
                    temp2.add_point(width,cherry);
                    all_platforms.add(temp2);
                    break;
                case 7:
                    temp=new platform();
                    temp.y=min- jump_dist;
                    temp.width=(width/3);
                    temp.x=width/2;
                    temp.height=50;
                    temp.paint=paint;
                    temp.add_point(width,cherry);
                    all_platforms.add(temp);
                    temp2=new platform();
                    temp2.y=min- 2*jump_dist;
                    temp2.width=(width/3);
                    temp2.x=width/2;
                    temp2.height=50;
                    temp2.paint=paint;
                    temp2.add_point(width,cherry);
                    all_platforms.add(temp2);
                    temp3=new platform();
                    temp3.y=min- 3*jump_dist;
                    temp3.width=(width/3);
                    temp3.x=width/2;
                    temp3.height=50;
                    temp3.paint=paint;
                    temp3.add_point(width,cherry);
                    all_platforms.add(temp3);
                    break;
                case 8:
                    temp=new platform();
                    temp.y=min- jump_dist;
                    temp.width=(width/3);
                    temp.x=width/6;
                    temp.height=50;
                    temp.paint=paint;
                    temp.add_point(width,cherry);
                    all_platforms.add(temp);
                    temp2=new platform();
                    temp2.y=min- 2*jump_dist;
                    temp2.width=(width/3);
                    temp2.x=width/2;
                    temp2.height=50;
                    temp2.paint=paint;
                    temp2.add_point(width,cherry);
                    all_platforms.add(temp2);
                    temp3=new platform();
                    temp3.y=min- 3*jump_dist;
                    temp3.width=(width/3);
                    temp3.x=5*width/6;
                    temp3.height=50;
                    temp3.paint=paint;
                    temp3.add_point(width,cherry);
                    all_platforms.add(temp3);
                    break;
                case 9:
                    temp=new platform();
                    temp.y=min- jump_dist;
                    temp.width=(width/3);
                    temp.x=5*width/6;
                    temp.height=50;
                    temp.paint=paint;
                    temp.add_point(width,cherry);
                    temp.add_ladder(width,ladder,4*width/6);
                    all_platforms.add(temp);
                    temp2=new platform();
                    temp2.y=min- 3*jump_dist;
                    temp2.width=(width/3);
                    temp2.x=width/2;
                    temp2.height=50;
                    temp2.paint=paint;
                    temp2.add_point(width,cherry);
                    all_platforms.add(temp2);
                    temp3=new platform();
                    temp3.y=min- 4*jump_dist;
                    temp3.width=(width/3);
                    temp3.x=width/6;
                    temp3.height=50;
                    temp3.paint=paint;
                    temp3.add_point(width,cherry);
                    all_platforms.add(temp3);
                    break;
                case 10:
                    temp=new platform();
                    temp.y=min- jump_dist;
                    temp.width=(width/3);
                    temp.x=width/6;
                    temp.height=50;
                    temp.paint=paint;
                    temp.add_point(width,cherry);

                    all_platforms.add(temp);
                    temp2=new platform();
                    temp2.y=min- 2*jump_dist;
                    temp2.width=(width/2);
                    temp2.x=width/2;
                    temp2.height=50;
                    temp2.paint=paint;
                    temp2.add_point(width,cherry);
                    temp2.add_trampoline(width , width/2);
                    all_platforms.add(temp2);
                    temp3=new platform();
                    temp3.y=min- 3*jump_dist;
                    temp3.width=(width/3);
                    temp3.x=width/2;
                    temp3.height=50;
                    temp3.paint=paint;
                    temp3.add_point(width,cherry);
                    all_platforms.add(temp3);
                    break;
                case 11:
                    temp=new platform();
                    temp.y=min- jump_dist;
                    temp.width=(width/3);
                    temp.x=5*width/6;
                    temp.height=50;
                    temp.paint=paint;
                    temp.add_point(width,cherry);
                    all_platforms.add(temp);
                    temp2=new platform();
                    temp2.y=min- 2*jump_dist;
                    temp2.width=(width/2);
                    temp2.x=width/2;
                    temp2.height=50;
                    temp2.paint=paint;
                    temp2.add_point(width,cherry);
                    temp2.add_ladder(width,ladder,5*width/12);
                    all_platforms.add(temp2);
                    temp3=new platform();
                    temp3.y=min- 4*jump_dist;
                    temp3.width=(width/3);
                    temp3.x=width/2;
                    temp3.height=50;
                    temp3.paint=paint;
                    temp3.add_point(width,cherry);
                    all_platforms.add(temp3);
                    break;
                case 12:
                    temp=new platform();
                    temp.y=min- jump_dist;
                    temp.width=(width/3);
                    temp.x=width/6;
                    temp.height=50;
                    temp.paint=paint;
                    temp.add_point(width,cherry);
                    all_platforms.add(temp);
                    temp2=new platform();
                    temp2.y=min- 2*jump_dist;
                    temp2.width=(width/2);
                    temp2.x=width/2;
                    temp2.height=50;
                    temp2.paint=paint;
                    temp2.add_point(width,cherry);
                    all_platforms.add(temp2);
                    temp3=new platform();
                    temp3.y=min- 3*jump_dist;
                    temp3.width=(width/3);
                    temp3.x=width/6;
                    temp3.height=50;
                    temp3.paint=paint;
                    temp3.add_point(width,cherry);
                    all_platforms.add(temp3);
                    break;
                case 13:
                    temp=new platform();
                    temp.y=min- jump_dist;
                    temp.width=(width/3);
                    temp.x=5*width/6;
                    temp.height=50;
                    temp.paint=paint;
                    temp.add_point(width,cherry);
                    temp.add_trampoline(width , 2*width/3);
                    all_platforms.add(temp);
                    temp2=new platform();
                    temp2.y=min- 3*jump_dist;
                    temp2.width=(width/2);
                    temp2.x=width/2;
                    temp2.height=50;
                    temp2.paint=paint;
                    temp2.add_point(width,cherry);
                    all_platforms.add(temp2);
                    temp3=new platform();
                    temp3.y=min- 4*jump_dist;
                    temp3.width=(width/3);
                    temp3.x=5*width/6;
                    temp3.height=50;
                    temp3.paint=paint;
                    temp3.add_point(width,cherry);
                    all_platforms.add(temp3);
                    temp4=new platform();
                    temp4.y=min-5*jump_dist;
                    temp4.width=width/2;
                    temp4.x=width/2;
                    temp4.height=50;
                    temp4.paint=paint;
                    temp4.add_point(width,cherry);
                    all_platforms.add(temp4);
                    break;
                case 14:
                    temp=new platform();
                    temp.y=min- jump_dist;
                    temp.width=(width);
                    temp.x=width/2;
                    temp.height=50;
                    temp.paint=paint;
                    temp.add_point(width,cherry);
                    all_platforms.add(temp);
                    temp2=new platform();
                    temp2.y=min- 2*jump_dist;
                    temp2.width=(width/4);
                    temp2.x=width/8;
                    temp2.height=50;
                    temp2.paint=paint;
                    temp2.add_point(width,cherry);
                    all_platforms.add(temp2);
                    temp3=new platform();
                    temp3.y=min- 3*jump_dist;
                    temp3.width=(width/4);
                    temp3.x=width/8;
                    temp3.height=50;
                    temp3.paint=paint;
                    temp3.add_point(width,cherry);
                    all_platforms.add(temp3);
                    temp4=new platform();
                    temp4.y=min- 4*jump_dist;
                    temp4.width=(2*width/4);
                    temp4.x=width/2;
                    temp4.height=50;
                    temp4.paint=paint;
                    temp4.add_point(width,cherry);
                    all_platforms.add(temp4);
                    break;
                case 15:
                    temp=new platform();
                    temp.y=min- jump_dist;
                    temp.width=(width);
                    temp.x=width/2;
                    temp.height=50;
                    temp.paint=paint;
                    all_platforms.add(temp);
                    temp2=new platform();
                    temp2.y=min- 2*jump_dist;
                    temp2.width=(width/4);
                    temp2.x=7*width/8;
                    temp2.height=50;
                    temp2.paint=paint;
                    temp2.add_point(width,cherry);
                    temp2.add_trampoline(width,7*width/8);
                    all_platforms.add(temp2);
                    temp3=new platform();
                    temp3.y=min- 4*jump_dist;
                    temp3.width=(width/4);
                    temp3.x=7*width/8;
                    temp3.height=50;
                    temp3.paint=paint;
                    temp3.add_point(width,cherry);
                    all_platforms.add(temp3);
                    temp4=new platform();
                    temp4.y=min- 5*jump_dist;
                    temp4.width=(2*width/4);
                    temp4.x=width/2;
                    temp4.height=50;
                    temp4.paint=paint;
                    temp4.add_point(width,cherry);
                    all_platforms.add(temp4);
                    break;
            }
        }
    }
    class player{
        int x ;
        int y;
        int gravity=-4;
        int width;
        int height;
        int y_speed;
        Bitmap bitmap;
        int min_y;
        int jump_last=0;
        public void draw(Canvas canvas){
        RectF temp=new RectF(x-width/2,y-height/2,x+width/2,y+height/2);
        canvas.drawBitmap(bitmap,null,temp,null);
        }
        public void update()
        {  jump_last++;
        y-=y_speed;
        y_speed+=gravity;
        if (y>=min_y)
         {
            y=min_y;
             platform_stat=true;
             y_speed=0;
//            if (jump_last>3)
//
         }
        else{
            platform_stat=false;
        }
        if (y<-this.width/2)
        {
            y=-this.width/2;
        }
        }
        public void ladders(){
            if (y_speed<=17)
                y_speed=17;
        }
        public void jump(int height, int param) {
            if (param==0)
            y_speed=50;
            else if (param==1){
                y_speed=70;
            }
            jump_last=0;
        }

        private void update_min(int height) {
            int min=2*height;
            int temp_temp=0;
            for (int i = 0; i < platform.all_platforms.size(); i++) {
                if (platform.all_platforms.get(i).width/2 +this.width/4 > mod(platform.all_platforms.get(i).x-this.x) && platform.all_platforms.get(i).y -platform.all_platforms.get(i).height/2>=this.y+this.height/2)
                {

                    if (platform.all_platforms.get(i).y<=min)
                    {
                        Log.i("here", "update_min: here"+i+" "+min+" "+min_y);
                        min=platform.all_platforms.get(i).y-platform.all_platforms.get(i).height/2 -this.height/2;

                    }


                }
                if (platform.all_platforms.get(i).cherry_status)
                {
                    if ((mod(platform.all_platforms.get(i).cherry.x-player.x)<player.width/4+platform.all_platforms.get(i).cherry.width/2) && (mod(platform.all_platforms.get(i).cherry.y-player.y)<player.height/4+platform.all_platforms.get(i).cherry.height/2))
                    {
                        platform.all_platforms.get(i).cherry_status=false;
                        score++;
                        score_text.setText(String.valueOf(score));
                    }
                }
                if (platform.all_platforms.get(i).ladder_status )
                {
                    if ((mod(platform.all_platforms.get(i).ladder_point.x-player.x)<player.width/4+platform.all_platforms.get(i).ladder_point.width/2) && (mod(platform.all_platforms.get(i).ladder_point.y-player.y)<player.height/2+platform.all_platforms.get(i).ladder_point.height/2) )
                    {
                        ladders();
                       temp_temp=1;
                       if(!mp_ladder.isPlaying())
                           mp_ladder.start();
                    }
                    else{
                        if(mp_ladder.isPlaying())
                            mp_ladder.stop();
                    }
                }
                if (platform.all_platforms.get(i).trampoline_status )
                {
                    if ((mod(platform.all_platforms.get(i).trampoline_point.x-player.x)<player.width/4+platform.all_platforms.get(i).trampoline_point.width/2) && (mod(platform.all_platforms.get(i).trampoline_point.y-player.y)<player.height/4+platform.all_platforms.get(i).trampoline_point.height/2) )
                    {
                        jump(height,1);
                    }
                }
                if (platform.all_platforms.get(i).last_status && player.y>height/4)
                {
                    if ((mod(platform.all_platforms.get(i).last.x-player.x)<player.width/4+platform.all_platforms.get(i).last.width/2) && (mod(platform.all_platforms.get(i).last.y-player.y)<player.height/4+platform.all_platforms.get(i).last.height/2))
                       finish_bool=true;
                }
            }
            if (min_y!=min)
            {
                min_y=min;
                if (min_y>height-this.height/2){
                    min_y=height-this.height/2;
                }
            }

            if (temp_temp==0){
                ladder_stat=false;
            }
            else if (temp_temp==1){
                ladder_stat=true;
            }
        }
    }
   public void start(){
        tim=new CountDownTimer(100,30 ) {

           public void onTick(long duration) {
               //tTimer.setText("seconds remaining: " + millisUntilFinished / 1000);
               //here you can have your logic to set text to edittext resource id
               // Duration
               control();
               if (platform_stat && !moving && player.y_speed<=0){
                   jump=false;
                   cancel();
               }
           }
           public void onFinish() {
               if (!platform_stat){
                    start();
               }
           }

       }.start();
   }

    @Override
    protected void onResume() {
        start();
        mp_jump=MediaPlayer.create(this, R.raw.jump_sound);
        mp_ladder=MediaPlayer.create(this, R.raw.ladder_sound);
        mp_time=MediaPlayer.create(this, R.raw.low_time);
        mp_game_over=MediaPlayer.create(this, R.raw.loose_sound);
        mp_game_win=MediaPlayer.create(this, R.raw.win_sound);
        mp_background=MediaPlayer.create(this, R.raw.doozy_sound);
        super.onResume();
    }
    @Override
    protected void onPause() {
        if (tim!=null)
            tim.cancel();
        if ( mp_jump!=null)
            mp_jump.stop();
        if ( mp_ladder!=null)
            mp_ladder.stop();
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

    public float translatex(float x){
        float viewAspectRatio = (float) width / height;
        float imageAspectRatio = (float) width2 / height2;
        float postScaleWidthOffset = 0;
        float postScaleHeightOffset = 0;
        float scaleFactor;
        if (viewAspectRatio > imageAspectRatio) {
            // The image needs to be vertically cropped to be displayed in this view.
            scaleFactor = (float) width / width2;
            postScaleHeightOffset = ((float) width / imageAspectRatio - height) / 2;
        } else {
            // The image needs to be horizontally cropped to be displayed in this view.
            scaleFactor = (float) height / height2;
            postScaleWidthOffset = ((float) height * imageAspectRatio - width) / 2;
        }
        return width - (x*scaleFactor - postScaleWidthOffset);

    }
    public float translatey(float y){
        float viewAspectRatio = (float) width / height;
        float imageAspectRatio = (float) width2 / height2;
        float postScaleWidthOffset = 0;
        float postScaleHeightOffset = 0;
        float scaleFactor;
        if (viewAspectRatio > imageAspectRatio) {
            // The image needs to be vertically cropped to be displayed in this view.
            scaleFactor = (float) width / width2;
            postScaleHeightOffset = ((float) width / imageAspectRatio - height) / 2;
        } else {
            // The image needs to be horizontally cropped to be displayed in this view.
            scaleFactor = (float) height / height2;
            postScaleWidthOffset = ((float) height * imageAspectRatio - width) / 2;
        }
        return y*scaleFactor -postScaleHeightOffset;
    }
    private void time_manager() {
        Date current_time = Calendar.getInstance().getTime();
        if (last_second!=-100 && last_second!=current_time.getTime()/1000 && callibration && started)
        {total_time_left--;}
        if (total_time_left<0)
            total_time_left=0;
        last_second= (int) (current_time.getTime()/1000);
        int min=total_time_left/60;
        int second=total_time_left%60;
        if (second<10)
        time_text.setText("0"+min+":0"+second);
        else
         time_text.setText("0"+min+":"+second);
        if (total_time_left<0)
        { total_time_left=0;
            if (mp_time.isPlaying())
                mp_time.stop();
        }
        if (total_time_left<20){
            if (!mp_time.isPlaying())
                mp_time.start();

        }
    }
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("issue3", "onTouchEvent: "+ladder_stat);
        if (platform_stat)
        {
            player.jump(height,0);

        }
        return super.onTouchEvent(event);
    }
    boolean doubleBackToExitPressedOnce = false;
    private Toast toast = null;
    public void onBackPressed() {
        final Dialog dialog = new Dialog(gameplay_2.this);

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
                Intent i=new Intent(gameplay_2.this,level_screen_2.class);
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
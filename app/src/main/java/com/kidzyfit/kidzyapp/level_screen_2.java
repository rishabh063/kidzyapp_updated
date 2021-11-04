package com.kidzyfit.kidzyapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

public class level_screen_2 extends game_basic {
    RelativeLayout level1;
    RelativeLayout level2;
    RelativeLayout level3;
    RelativeLayout level4;
    RelativeLayout level5;
    RelativeLayout level6;
    RelativeLayout level7;
    RelativeLayout level8;
    RelativeLayout level9;
    RelativeLayout level10;
    ImageView level1_image;
    ImageView level2_image;
    ImageView level3_image;
    ImageView level4_image;
    ImageView level5_image;
    ImageView level6_image;
    ImageView level7_image;
    ImageView level8_image;
    ImageView level9_image;
    ImageView level10_image;
    TextView level1_text;
    TextView level2_text;
    TextView level3_text;
    TextView level4_text;
    TextView level5_text;
    TextView level6_text;
    TextView level7_text;
    TextView level8_text;
    TextView level9_text;
    TextView level10_text;
    Activity act;
    ImageView back;
    private float back_pressed=0;
    private FirebaseAnalytics firebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getSharedPreferences("gameplay_2", MODE_PRIVATE);
        int progress = prefs.getInt("myInt", 1); // 0 is default
        setContentView(R.layout.activity_level_screen_2);
        back=findViewById(R.id.back_button);
        Bundle bundle = new Bundle();
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
         firebaseAnalytics = FirebaseAnalytics.getInstance(this);
 firebaseAnalytics = FirebaseAnalytics.getInstance(this);
firebaseAnalytics.logEvent("level_screen_2", bundle);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenti = new Intent(level_screen_2.this, sample_video_images.class);
                intenti.putExtra("select",3);
                startActivity(intenti);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        level1=findViewById(R.id.level_1);
        level2=findViewById(R.id.level_2);
        level3=findViewById(R.id.level_3);
        level4=findViewById(R.id.level_4);
        level5=findViewById(R.id.level_5);
        level6=findViewById(R.id.level_6);
        level7=findViewById(R.id.level_7);
        level8=findViewById(R.id.level_8);
        level9=findViewById(R.id.level_9);
        level10=findViewById(R.id.level_10);
        level1_text=findViewById(R.id.level_1_text);
        level2_text=findViewById(R.id.level_2_text);
        level3_text=findViewById(R.id.level_3_text);
        level4_text=findViewById(R.id.level_4_text);
        level5_text=findViewById(R.id.level_5_text);
        level6_text=findViewById(R.id.level_6_text);
        level7_text=findViewById(R.id.level_7_text);
        level8_text=findViewById(R.id.level_8_text);
        level9_text=findViewById(R.id.level_9_text);
        level10_text=findViewById(R.id.level_10_text);
        level1.getLayoutParams().width=width/7;
        level1.getLayoutParams().height=width/7;
        level2.getLayoutParams().width=width/7;
        level2.getLayoutParams().height=width/7;
        level3.getLayoutParams().width=width/7;
        level3.getLayoutParams().height=width/7;
        level4.getLayoutParams().width=width/7;
        level4.getLayoutParams().height=width/7;
        level5.getLayoutParams().width=width/7;
        level5.getLayoutParams().height=width/7;
        level6.getLayoutParams().width=width/7;
        level6.getLayoutParams().height=width/7;
        level7.getLayoutParams().width=width/7;
        level7.getLayoutParams().height=width/7;
        level8.getLayoutParams().width=width/7;
        level8.getLayoutParams().height=width/7;
        level9.getLayoutParams().width=width/7;
        level9.getLayoutParams().height=width/7;
        level10.getLayoutParams().width=width/7;
        level10.getLayoutParams().height=width/7;
        level1_text.setTextSize(width/28);
        level2_text.setTextSize(width/28);
        level3_text.setTextSize(width/28);
        level4_text.setTextSize(width/28);
        level5_text.setTextSize(width/28);
        level6_text.setTextSize(width/28);
        level7_text.setTextSize(width/28);
        level8_text.setTextSize(width/28);
        level9_text.setTextSize(width/28);
        level10_text.setTextSize(width/28);
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        level2_image=findViewById(R.id.level2_image);
        level3_image=findViewById(R.id.level3_image);
        level4_image=findViewById(R.id.level4_image);
        level5_image=findViewById(R.id.level5_image);
        level6_image=findViewById(R.id.level6_image);
        level7_image=findViewById(R.id.level7_image);
        level8_image=findViewById(R.id.level8_image);
        level9_image=findViewById(R.id.level9_image);
        level10_image=findViewById(R.id.level10_image);
        level2_image.setColorFilter(filter);
        level3_image.setColorFilter(filter);
        level4_image.setColorFilter(filter);
        level5_image.setColorFilter(filter);
        level6_image.setColorFilter(filter);
        level7_image.setColorFilter(filter);
        level8_image.setColorFilter(filter);
        level9_image.setColorFilter(filter);
        level10_image.setColorFilter(filter);
        act=this;
        Log.i("issue4", "onCreate: "+progress);
        if(progress<2)
            level2_image.setImageResource(R.drawable.grey_circle);
        if(progress<3)
            level3_image.setImageResource(R.drawable.grey_circle);
        if(progress<4)
            level4_image.setImageResource(R.drawable.grey_circle);
        if(progress<5)
            level5_image.setImageResource(R.drawable.grey_circle);
        if(progress<6)
            level6_image.setImageResource(R.drawable.grey_circle);
        if(progress<7)
            level7_image.setImageResource(R.drawable.grey_circle);
        if(progress<8)
            level8_image.setImageResource(R.drawable.grey_circle);
        if(progress<9)
            level9_image.setImageResource(R.drawable.grey_circle);
        if(progress<10)
            level10_image.setImageResource(R.drawable.grey_circle);

        level1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(act, gameplay_2.class);
                intent.putExtra("seed", 4);
                intent.putExtra("level",1);
                intent.putExtra("modulus",7);
                intent.putExtra("multi",5);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });
        level2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progress>=2)
                {
                Intent intent = new Intent(act, gameplay_2.class);
                intent.putExtra("seed", 2);
                intent.putExtra("modulus",8);
                intent.putExtra("level",2);
                intent.putExtra("multi",9);
                startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        });
        level3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progress>=3)
                {
                Intent intent = new Intent(act, gameplay_2.class);
                intent.putExtra("seed", 1);
                intent.putExtra("level",3);
                intent.putExtra("modulus",13);
                intent.putExtra("multi",26);
                startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        });
        level4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progress>=4) {
                    Intent intent = new Intent(act, gameplay_2.class);
                    intent.putExtra("seed", 2);
                    intent.putExtra("modulus", 14);
                    intent.putExtra("level",4);
                    intent.putExtra("multi", 34);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        });
        level5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progress>=5)
                {
                Intent intent = new Intent(act, gameplay_2.class);
                intent.putExtra("seed", 5);
                intent.putExtra("modulus",14);
                    intent.putExtra("level",5);
                intent.putExtra("multi",22);
                startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        });
        level6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progress>=6)
                {
                Intent intent = new Intent(act, gameplay_2.class);
                intent.putExtra("seed", 7);
                intent.putExtra("modulus",14);
                intent.putExtra("level",6);
                intent.putExtra("multi",12);
                startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        });
        level7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progress>=7)
                {
                Intent intent = new Intent(act, gameplay_2.class);
                intent.putExtra("seed", 9);
                intent.putExtra("modulus",14);
                intent.putExtra("multi",28);
                intent.putExtra("level",7);
                startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        });
        level8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progress>=8)
                {
                Intent intent = new Intent(act,gameplay_2.class);
                intent.putExtra("seed", 4);
                intent.putExtra("modulus",15);
                intent.putExtra("multi",3);
                intent.putExtra("level",8);
                startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        });
        level9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progress>=9) {
                    Intent intent = new Intent(act, gameplay_2.class);
                    intent.putExtra("seed", 5);
                    intent.putExtra("modulus", 15);
                    intent.putExtra("multi", 2);
                    intent.putExtra("level",9);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        });
        level10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progress>=10)
                {
                Intent intent = new Intent(act, gameplay_2.class);
                intent.putExtra("seed", 7);
                intent.putExtra("modulus",16);
                intent.putExtra("multi",6);
                intent.putExtra("level",10);
                startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        });
    }
    boolean doubleBackToExitPressedOnce = false;
    private Toast toast = null;
    public void onBackPressed() {
        final Dialog dialog = new Dialog(level_screen_2.this);

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

}
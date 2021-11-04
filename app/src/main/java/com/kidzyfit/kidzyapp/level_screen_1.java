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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

public class level_screen_1 extends game_basic {
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
        setContentView(R.layout.activity_level_screen_1);
        SharedPreferences prefs = getSharedPreferences("gameplay_1", MODE_PRIVATE);
        int progress = prefs.getInt("myInt", 1); // 0 is default
        Intent intent = new Intent(this, gameplay_1.class);
        back=findViewById(R.id.back_button);
        Bundle bundle = new Bundle();
         firebaseAnalytics = FirebaseAnalytics.getInstance(this);
 firebaseAnalytics = FirebaseAnalytics.getInstance(this);
firebaseAnalytics.logEvent("level_screen1", bundle);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenti = new Intent(level_screen_1.this, sample_video_images.class);
                intenti.putExtra("select",1);
                startActivity(intenti);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        level1=findViewById(R.id.level_1);
        level2=findViewById(R.id.level_2);
        level3=findViewById(R.id.level_3);
        level4=findViewById(R.id.level_4);
        level5=findViewById(R.id.level_5);
        level1_text=findViewById(R.id.level_1_text);
        level2_text=findViewById(R.id.level_2_text);
        level3_text=findViewById(R.id.level_3_text);
        level4_text=findViewById(R.id.level_4_text);
        level5_text=findViewById(R.id.level_5_text);
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
        level1_text.setTextSize(width/28);
        level2_text.setTextSize(width/28);
        level3_text.setTextSize(width/28);
        level4_text.setTextSize(width/28);
        level5_text.setTextSize(width/28);
        level2_image=findViewById(R.id.level2_image);
        level3_image=findViewById(R.id.level3_image);
        level4_image=findViewById(R.id.level4_image);
        level5_image=findViewById(R.id.level5_image);
        level1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("level", 2);
                intent.putExtra("max", 220);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });
        if(progress<2)
            level2_image.setImageResource(R.drawable.grey_circle);
        if(progress<3)
            level3_image.setImageResource(R.drawable.grey_circle);
        if(progress<4)
            level4_image.setImageResource(R.drawable.grey_circle);
        if(progress<5)
            level5_image.setImageResource(R.drawable.grey_circle);
        level2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progress>=2)
                {intent.putExtra("level", 3);
                intent.putExtra("max", 260);
                startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        });
        level3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progress>=3)
                {intent.putExtra("level", 4);
                intent.putExtra("max", 300);
                startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        });
        level4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progress>=4)
                {intent.putExtra("level", 5);
                intent.putExtra("max", 340);
                startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        });
        level5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progress>=5)
                {intent.putExtra("level", 6);
                intent.putExtra("max", 380);
                startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        });
    }
    boolean doubleBackToExitPressedOnce = false;
    private Toast toast = null;
    public void onBackPressed() {
        final Dialog dialog = new Dialog(level_screen_1.this);

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
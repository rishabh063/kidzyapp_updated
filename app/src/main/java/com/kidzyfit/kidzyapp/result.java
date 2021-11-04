package com.kidzyfit.kidzyapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class result extends game_basic{

    private FirebaseAnalytics firebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        Intent i=getIntent();
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);


        int result = i.getIntExtra("char",1);
        setContentView(R.layout.activity_result);
        ImageView temp=findViewById(R.id.background);
        if(result==2) {
            temp.setImageResource(R.drawable.doozy_result);
        }
        else if (result==3){
            temp.setImageResource(R.drawable.granny_result);
        }
        else if (result==4){
            temp.setImageResource(R.drawable.ninja_result);
        }
         firebaseAnalytics = FirebaseAnalytics.getInstance(this);
firebaseAnalytics.logEvent("playvideo_character"+result, bundle);
        Button temp_button=findViewById(R.id.Continue_button);
        temp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(result.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    public void onBackPressed() {
        final Dialog dialog = new Dialog(result.this);

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
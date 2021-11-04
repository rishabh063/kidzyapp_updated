package com.kidzyfit.kidzyapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.MediaController;

import com.google.firebase.analytics.FirebaseAnalytics;

public class sample_video_images extends game_basic {
    Uri videoUrl ;
    ImageView back;
    private FirebaseAnalytics firebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_video_images);
        VideoView videoView = findViewById(R.id.video_view);
        videoView.getLayoutParams().height=width/4;
        videoView.getLayoutParams().width=width/2;
        back=findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenti = new Intent(sample_video_images.this, MainActivity.class);
                startActivity(intenti);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });


        ImageView background=findViewById(R.id.background);
        Button start_button=findViewById(R.id.start_button);
        ImageView start_button_2=findViewById(R.id.start);
        RelativeLayout sample_layout=findViewById(R.id.sample_video);
        TextView text=findViewById(R.id.text_sample);
        ImageView question=findViewById(R.id.sample_img);
        question.getLayoutParams().height=width/10;
        question.getLayoutParams().width=width/4;
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.start();
            }
        });
        Intent i=getIntent();
        int select=i.getIntExtra("select",1);
        Bundle bundle = new Bundle();
        firebaseAnalytics.logEvent("sample_video"+select, bundle);
        if (select==1) {
            background.setImageResource(R.drawable.reflex_drift);
            videoUrl=Uri.parse("android.resource://" + getPackageName() + "/" +String.valueOf(R.raw.bubble));
            text.setText("Hit Bubbles in Numerical Order as Fast \n as You Can By Moving Your Hand.");
            start_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(sample_video_images.this, level_screen_1.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                }

            });
            start_button_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(sample_video_images.this, level_screen_1.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }

            });
        }
        else if (select==2) {
            background.setImageResource(R.drawable.fruit_punch);
            text.setText("Cut Fruits Using Your Hands ,\n Open New Fruits and Areas.");
            videoUrl=videoUrl=Uri.parse("android.resource://" + getPackageName() + "/" +String.valueOf(R.raw.fruit));
            start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sample_video_images.this, Map.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
            start_button_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(sample_video_images.this, Map.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });
        }
        else if (select==3) {
            background.setImageResource(R.drawable.toad_valley);
            text.setText("Jump to Reach at Home before Time runs out \n, Eat Cherries In Way");
            videoUrl=Uri.parse("android.resource://" + getPackageName() + "/" +String.valueOf(R.raw.toad));
            start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sample_video_images.this, level_screen_2.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
            start_button_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(sample_video_images.this, level_screen_2.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });}
        else if (select==4) {
            videoUrl=Uri.parse("android.resource://" + getPackageName() + "/" +String.valueOf(R.raw.astra));
            text.setText("Follow Space Explorer Astra to Earn Points");
            background.setImageResource(R.drawable.astra);
            start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playvideo.character=1;
                playvideo.totalmin=15;
                Intent switchActivityIntent = new Intent(sample_video_images.this, playvideo.class);
                startActivity(switchActivityIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
            start_button_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playvideo.character=1;
                    playvideo.totalmin=15;
                    Intent switchActivityIntent = new Intent(sample_video_images.this, playvideo.class);
                    startActivity(switchActivityIntent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });}
        else  if (select==5){
            videoUrl=Uri.parse("android.resource://" + getPackageName() + "/" +String.valueOf(R.raw.doozy));
            text.setText("Follow Doozy From Magic\nLand to Earn Points");
            background.setImageResource(R.drawable.doozy);
            start_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playvideo.character=2;
                    playvideo.totalmin=15;
                    Intent switchActivityIntent = new Intent(sample_video_images.this, playvideo.class);
                    startActivity(switchActivityIntent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });
            start_button_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playvideo.character=2;
                    playvideo.totalmin=15;
                    Intent switchActivityIntent = new Intent(sample_video_images.this, playvideo.class);
                    startActivity(switchActivityIntent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });}
        else if (select==6){
            videoUrl=Uri.parse("android.resource://" + getPackageName() + "/" +String.valueOf(R.raw.granny));
            text.setText("Follow Modern Granny to Earn Points");
            background.setImageResource(R.drawable.granny);
            start_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playvideo.character=3;
                    playvideo.totalmin=15;
                    Intent switchActivityIntent = new Intent(sample_video_images.this, playvideo.class);
                    startActivity(switchActivityIntent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });
            start_button_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playvideo.character=3;
                    playvideo.totalmin=15;
                    Intent switchActivityIntent = new Intent(sample_video_images.this, playvideo.class);
                    startActivity(switchActivityIntent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });
            }
        else if (select==7){

            videoUrl=Uri.parse("android.resource://" + getPackageName() + "/" +String.valueOf(R.raw.ninja));
            text.setText("Follow Super Ninja to Earn Points");
            background.setImageResource(R.drawable.ninja);
            start_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playvideo.character=4;
                    playvideo.totalmin=15;
                    Intent switchActivityIntent = new Intent(sample_video_images.this, playvideo.class);
                    startActivity(switchActivityIntent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });
            start_button_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playvideo.character=4;
                    playvideo.totalmin=15;
                    Intent switchActivityIntent = new Intent(sample_video_images.this, playvideo.class);
                    startActivity(switchActivityIntent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });
        }
        videoView.setVideoURI(videoUrl);
        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_button.setVisibility(View.INVISIBLE);
                sample_layout.setVisibility(View.VISIBLE);
                videoView.start();
            }
        });
        ImageView cross=findViewById(R.id.cross);
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_button.setVisibility(View.VISIBLE);
                sample_layout.setVisibility(View.INVISIBLE);
                videoView.pause();
            }
        });
    }

    @Override
    public void onBackPressed() {
        final Dialog dialog = new Dialog(sample_video_images.this);

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
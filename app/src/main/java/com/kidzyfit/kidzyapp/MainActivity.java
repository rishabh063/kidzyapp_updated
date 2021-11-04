package com.kidzyfit.kidzyapp;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.MotionEventCompat;

import com.google.firebase.analytics.FirebaseAnalytics;

import static androidx.camera.core.CameraX.getContext;

public class MainActivity extends game_basic {
    Activity act;
    private static final String[] CAMERA_PERMISSION = new String[]{Manifest.permission.CAMERA};
    private static final int CAMERA_REQUEST_CODE = 10;
    int select=0;
    private float back_pressed=0;
    private static FirebaseAnalytics firebaseAnalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        firebaseAnalytics.logEvent("home", bundle);

        act=this;
        ImageView bt1=findViewById(R.id.img3);
        ImageView bt2=findViewById(R.id.img2);
        ImageView bt3=findViewById(R.id.img1);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select=1;
                check();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select=2;
                check();
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select=3;
                check();
            }
        });
        ImageView gam1=findViewById(R.id.img4);
        ImageView gam2=findViewById(R.id.img6);
        ImageView gam3=findViewById(R.id.img7);
        ImageView gam4=findViewById(R.id.img5);
        gam1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select=4;
                check();

            }
        });
        gam2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select=5;
                check();
            }
        });
        gam3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select=6;
                check();
            }
        });
        gam4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select=7;
                check();

            }
        });
    }
    public void check(){
        if (hasCameraPermission()) {
            enableCamera();
        } else {
            requestPermission();
        }
    }
    private boolean hasCameraPermission() {
        return ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED;
    }
    private void enableCamera() {
        Log.i("check", "onCreate: herefinal");
        Intent intent = new Intent(this, sample_video_images.class);
        intent.putExtra("select",select);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(
                this,
                CAMERA_PERMISSION,
                CAMERA_REQUEST_CODE
        );
    }
    public void onRequestPermissionsResult(int requestCode, String[]permissions, int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (hasCameraPermission()) {
            enableCamera();
        } else {
            Toast.makeText(this, "Please Grant Camera Permission", Toast.LENGTH_SHORT).show();
        }
    }
    boolean doubleBackToExitPressedOnce = false;
    private Toast toast = null;
    public void onBackPressed() {
        final Dialog dialog = new Dialog(MainActivity.this);

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
package com.kidzyfit.kidzyapp;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.media.Image;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.OrientationEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.pose.Pose;
import com.google.mlkit.vision.pose.PoseDetection;
import com.google.mlkit.vision.pose.PoseDetector;
import com.google.mlkit.vision.pose.PoseLandmark;
import com.google.mlkit.vision.pose.accurate.AccuratePoseDetectorOptions;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class base2 extends game_basic {
    public PreviewView previewView;
    public PreviewView previewView2;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    Canvas canvas;
    Paint redPaint = new Paint();
    int height;
    int width;
    static int counterr=0;
    static int orientation=1;
    int height2;
    static int r = 0;
    float radius;
    int status = 0;
    int counter = 0;
    int width2;
    PoseDetector poseDetector;
    private float zMin = Float.MAX_VALUE;
    private float zMax = Float.MIN_VALUE;
    List<PoseLandmark> allPoseLandmarks;
    static float[][] cord= new float[40][6];
    static poscalculator calculator;
    ProcessCameraProvider cameraProvider;
    private FirebaseAnalytics firebaseAnalytics;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calculator = new poscalculator();
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    cameraProvider = cameraProviderFuture.get();
                    bindImageAnalysis(cameraProvider);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, ContextCompat.getMainExecutor(this));
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        redPaint.setARGB(255, 255, 0, 0);
        redPaint.setStyle(Paint.Style.STROKE);
        final int[] x = {0};
        final int[] y = {0};
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
firebaseAnalytics.logEvent("base2", bundle);

    }

    private void bindImageAnalysis(@NonNull ProcessCameraProvider cameraProvider) {

        AccuratePoseDetectorOptions options =
                new AccuratePoseDetectorOptions.Builder()
                        .setDetectorMode(AccuratePoseDetectorOptions.STREAM_MODE)
                        .build();
         poseDetector = PoseDetection.getClient(options);
        Thread thread = new Thread();
        Executor executor = Executors.newCachedThreadPool();
        ImageAnalysis imageAnalysis =
                new ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).build();

        imageAnalysis.setAnalyzer(executor, new ImageAnalysis.Analyzer() {
            @Override
            public void analyze(@NonNull ImageProxy image) {
                Log.i("valyue", "analyze: " + "here");
                @SuppressLint("UnsafeExperimentalUsageError") Image mediaImage = image.getImage();
                if (mediaImage != null) {
                    InputImage image_f = InputImage.fromMediaImage(mediaImage, image.getImageInfo().getRotationDegrees());
                    boolean isImageFlipped = true;
                    Task<Pose> result =
                            poseDetector.process(image_f)
                                    .addOnSuccessListener(
                                            new OnSuccessListener<Pose>() {
                                                @SuppressLint("RestrictedApi")
                                                @Override
                                                public void onSuccess(Pose pose) {

                                                        allPoseLandmarks = pose.getAllPoseLandmarks();
                                                       try {
                                                           if (allPoseLandmarks != null) {
                                                               {
                                                                   height2 = imageAnalysis.getAttachedSurfaceResolution().getHeight();
                                                                   width2 = imageAnalysis.getAttachedSurfaceResolution().getWidth();
                                                                   for (int i = 0; i < allPoseLandmarks.size(); i++) {

                                                                       Log.i("orientation", "onSuccess: part2     "+cord[i][0]);
                                                                       cord[i][0] = 1 - (allPoseLandmarks.get(i).getPosition().y) / height2;
                                                                       cord[i][1] = 1 - allPoseLandmarks.get(i).getPosition().x / width2;
                                                                       cord[i][2] = allPoseLandmarks.get(i).getPosition3D().getZ();
                                                                       cord[i][3] = allPoseLandmarks.get(i).getInFrameLikelihood();

                                                                   }

                                                               }

                                                           }
                                                       }
                                                       catch (Exception E){

                                                       }

                                                }

                                            })
                                    .addOnCompleteListener(
                                            new OnCompleteListener<Pose>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Pose> task) {
                                                    Log.i("here", "onComplete: here222");
                                                    if (calculator != null) {
                                                        Log.i("here", "onComplete: here33322");
                                                            calculator.cord = cord;
                                                            calculator.Scorecal();
                                                            run();
                                                    }
                                                    image.close();

                                                    counterr=700;

                                                }
                                            }
                                    )
                                    .addOnFailureListener(
                                            new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    // Task failed with an exception
                                                    // ...
                                                }
                                            });

                }

            }
        });


        OrientationEventListener orientationEventListener = new OrientationEventListener(this) {
            @Override
            public void onOrientationChanged(int orientation) {
            }
        };
        orientationEventListener.enable();
        Preview preview = new Preview.Builder().build();
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_FRONT).build();
        preview.setSurfaceProvider(previewView.createSurfaceProvider());
        cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, imageAnalysis, preview);

    }

    private void drawLine(Canvas canvas, PoseLandmark point1, PoseLandmark point2, Paint redPaint) {
        redPaint.setStrokeWidth(5);
        if (point1 != null && point2 != null && point1.getInFrameLikelihood() > 0.9 && point2.getInFrameLikelihood() > 0.9)
            canvas.drawLine(width * (1 - point1.getPosition().x / width2), height * point1.getPosition().y / height2, width * (1 - point2.getPosition().x / width2), height * point2.getPosition().y / height2, redPaint);
    }

    public double angle(PointF a, PointF b, PointF c) {
        double radien = Math.atan2(c.y - b.y, c.x - b.x) - Math.atan2(a.y - b.y, a.x - b.x);
        double angle1 = Math.abs(radien * 180.0 / Math.PI);
        if (angle1 > 180)
            angle1 = 360 - angle1;
        return angle1;
    }

    public void clearmemory2(){
        try{
        poseDetector.close();}
        catch (Exception E)
        {

        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraProvider.unbindAll();
        cameraProvider=null;
    }
    public void run(){

    }
}

class point{
    float x;
    float y;
}
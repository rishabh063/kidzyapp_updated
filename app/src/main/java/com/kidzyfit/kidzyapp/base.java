package com.kidzyfit.kidzyapp;

import android.annotation.SuppressLint;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.OrientationEventListener;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;
import com.google.mlkit.vision.pose.Pose;
import com.google.mlkit.vision.pose.PoseDetection;
import com.google.mlkit.vision.pose.PoseDetector;
import com.google.mlkit.vision.pose.PoseLandmark;
import com.google.mlkit.vision.pose.accurate.AccuratePoseDetectorOptions;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class base extends game_basic{
    public PreviewView previewView;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    PoseDetector poseDetector;
    List<PoseLandmark> allPoseLandmarks;
    static float[][] cord = new float[40][6];
    ProcessCameraProvider cameraProvider;
    int height2;
    int width2;
    String issue_text;
    Boolean issue;
    int face_count;
    float outside_frame_time=0;
    public PoseLandmark leftShoulder;
    public PoseLandmark rightShoulder;
    public PoseLandmark leftElbow ;
    public PoseLandmark rightElbow ;
    public PoseLandmark leftWrist ;
    public PoseLandmark rightWrist ;
    public PoseLandmark leftHip ;
    public PoseLandmark rightHip ;
    public PoseLandmark leftKnee ;
    public PoseLandmark rightKnee ;
    public PoseLandmark leftAnkle ;
    public PoseLandmark rightAnkle ;
    public PoseLandmark leftPinky ;
    public PoseLandmark rightPinky ;
    public PoseLandmark leftIndex ;
    public PoseLandmark rightIndex ;
    public PoseLandmark leftThumb ;
    public PoseLandmark rightThumb;
    public PoseLandmark leftHeel ;
    public PoseLandmark rightHeel ;
    public PoseLandmark leftFootIndex;
    public PoseLandmark rightFootIndex;
    public PoseLandmark nose ;
    private float back_pressed=0;
    private FirebaseAnalytics firebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        firebaseAnalytics.logEvent("base", bundle);

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
    }
    private void bindImageAnalysis(@NonNull ProcessCameraProvider cameraProvider) {

        AccuratePoseDetectorOptions options =
                new AccuratePoseDetectorOptions.Builder()
                        .setDetectorMode(AccuratePoseDetectorOptions.STREAM_MODE)
                        .build();
        FaceDetectorOptions realTimeOpts =
                new FaceDetectorOptions.Builder()
                        .setContourMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
                        .build();
        FaceDetector detector = FaceDetection.getClient(realTimeOpts);
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

                                                                    Log.i("orientation", "onSuccess: part2     " + cord[i][0]);
                                                                    cord[i][0] = allPoseLandmarks.get(i).getPosition().y/height2;
                                                                    cord[i][1] = allPoseLandmarks.get(i).getPosition().x/width2 ;
                                                                    cord[i][2] = allPoseLandmarks.get(i).getPosition3D().getZ();
                                                                    cord[i][3] = allPoseLandmarks.get(i).getInFrameLikelihood();
                                                                    cord[i][4]= allPoseLandmarks.get(i).getPosition().y;
                                                                    cord[i][5] = allPoseLandmarks.get(i).getPosition().x;
                                                                }
                                                                leftShoulder = pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER);
                                                                rightShoulder = pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER);
                                                                leftElbow = pose.getPoseLandmark(PoseLandmark.LEFT_ELBOW);
                                                                rightElbow = pose.getPoseLandmark(PoseLandmark.RIGHT_ELBOW);
                                                                leftWrist = pose.getPoseLandmark(PoseLandmark.LEFT_WRIST);
                                                                rightWrist = pose.getPoseLandmark(PoseLandmark.RIGHT_WRIST);
                                                                leftHip = pose.getPoseLandmark(PoseLandmark.LEFT_HIP);
                                                                rightHip = pose.getPoseLandmark(PoseLandmark.RIGHT_HIP);
                                                                leftKnee = pose.getPoseLandmark(PoseLandmark.LEFT_KNEE);
                                                                rightKnee = pose.getPoseLandmark(PoseLandmark.RIGHT_KNEE);
                                                                leftAnkle = pose.getPoseLandmark(PoseLandmark.LEFT_ANKLE);
                                                                rightAnkle = pose.getPoseLandmark(PoseLandmark.RIGHT_ANKLE);
                                                                leftPinky = pose.getPoseLandmark(PoseLandmark.LEFT_PINKY);
                                                                rightPinky = pose.getPoseLandmark(PoseLandmark.RIGHT_PINKY);
                                                                leftIndex = pose.getPoseLandmark(PoseLandmark.LEFT_INDEX);
                                                                rightIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_INDEX);
                                                                leftThumb = pose.getPoseLandmark(PoseLandmark.LEFT_THUMB);
                                                                rightThumb = pose.getPoseLandmark(PoseLandmark.RIGHT_THUMB);
                                                                leftHeel = pose.getPoseLandmark(PoseLandmark.LEFT_HEEL);
                                                                rightHeel = pose.getPoseLandmark(PoseLandmark.RIGHT_HEEL);
                                                                leftFootIndex = pose.getPoseLandmark(PoseLandmark.LEFT_FOOT_INDEX);
                                                                rightFootIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_FOOT_INDEX);
                                                                nose = pose.getPoseLandmark(PoseLandmark.NOSE);
                                                                control2();
                                                                control3();
                                                                image.close();
                                                            }

                                                        }
                                                    } catch (Exception E) {

                                                    }

                                                }

                                            })
                                    .addOnCompleteListener(
                                            new OnCompleteListener<Pose>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Pose> task) {
                                                    Log.i("here", "onComplete: here222");
                                                    image.close();

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
                    Log.i("hehe", "onSuccess: ");
                    Task<List<Face>> result2 =
                            detector.process(image_f)
                                    .addOnSuccessListener(
                                            new OnSuccessListener<List<Face>>() {
                                                @Override
                                                public void onSuccess(List<Face> faces) {
                                                    face_count=faces.size();
                                                    Log.i("count", "onSuccess: "+face_count);
                                                }
                                            })
                                    .addOnCompleteListener(
                                            new OnCompleteListener<List<Face>>() {
                                                @Override
                                                public void onComplete(@NonNull Task<List<Face>> task) {
                                                    image.close();
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
    private String control3() {
        int flag=0;
        Log.i("count", "control3: "+outside_frame_time);
        for (int i = 0; i <12 ; i++) {
            Log.i("in_frame", "control3"+ i+" "+cord[i][3]+"");
            if ((cord[i][3]<0.95 || cord[i][0]>0.95 || cord[i][1]>0.95 || cord[i][0]<0.05 || cord[i][1]<0.05 ))
            {
                Log.i("problem", "control3: "+i);
                flag=1;
                outside_frame_time--;
                break;
            }
        }
        if (mod(leftShoulder.getPosition().x-rightShoulder.getPosition().x)/height2>0.35)
        {
            outside_frame_time-=3;
        }
       else if (flag==0 ){
            outside_frame_time=0;
        }
        if (outside_frame_time<-30){
            return "Issue";
        }
        else{
            return  "";
        }
    }
    public void control2(){

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
    boolean doubleBackToExitPressedOnce = false;
    private Toast toast = null;
}

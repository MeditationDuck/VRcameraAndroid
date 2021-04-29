package com.firstapp.ultracamera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.util.Rational;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private int REQUEST_CODE_PERMISSIONS = 101;
    private String[] REQUIRED_PERMISSIONS = new String[] {"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"};

    TextureView textureView;

    TextureView textureView2;
    public boolean lrcheck = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        textureView = (TextureView) findViewById(R.id.view_finder);

        textureView2 = (TextureView) findViewById(R.id.view_finder2);

        if(allPermissionGranted()){
                startCamera();
                startCamera2();
        }
        else{
            ActivityCompat.requestPermissions( this,REQUIRED_PERMISSIONS ,REQUEST_CODE_PERMISSIONS);
        }

    }

    private void startCamera() {

//        CameraX.unbindAll();

        Rational aspectRatio = new Rational(textureView.getWidth(), textureView.getHeight());
        Size screen = new Size(textureView.getWidth(), textureView.getHeight());
        PreviewConfig pConfig = new PreviewConfig.Builder().setTargetAspectRatio(aspectRatio).setTargetResolution(screen).build();
        Preview preview = new Preview(pConfig);

        preview.setOnPreviewOutputUpdateListener(

                new Preview.OnPreviewOutputUpdateListener() {
                    @Override

                    public void onUpdated(Preview.PreviewOutput output) {
//                        String i = Calendar.getInstance().getTime().toString();
//                        int nowTime = Integer.parseInt(i);
//                        for
//                        if(!Global.lrcheck) {
//                            ViewGroup parent = (ViewGroup) textureView.getParent();
//                            parent.removeView(textureView);
//                            parent.addView(textureView);
                            textureView.setSurfaceTexture(output.getSurfaceTexture());
//                            Log.i("check", String.valueOf(Global.lrcheck));
//                            Global.change_rl();
//                            Log.i("check", String.valueOf(Global.lrcheck));
//                        }else{
//                            ViewGroup parent2 = (ViewGroup) textureView2.getParent();
//                            parent.removeView(textureView2);
//                            parent.addView(textureView2);
//                            textureView2.setSurfaceTexture(output.getSurfaceTexture());
//                            Log.i("check", String.valueOf(Global.lrcheck));
//                            Global.change_rl();
//                        }
//                        updateTransform();
                    }
                }
        );

//        ImageCaptureConfig imageCaptureConfig = new ImageCaptureConfig.Builder().setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY).
//                setTargetRotation(getWindowManager().getDefaultDisplay().getRotation()).build();
//        final ImageCapture imgCap = new ImageCapture(imageCaptureConfig);
        CameraX.bindToLifecycle(this, preview);
    }

    private void startCamera2() {

//        CameraX.unbindAll();

        Rational aspectRatio = new Rational(textureView2.getWidth(), textureView2.getHeight());

        Size screen = new Size(textureView2.getWidth(), textureView2.getHeight());
        PreviewConfig pConfig = new PreviewConfig.Builder().setTargetAspectRatio(aspectRatio).setTargetResolution(screen).build();
        Preview preview = new Preview(pConfig);

        preview.setOnPreviewOutputUpdateListener(
                new Preview.OnPreviewOutputUpdateListener() {
                    @Override
                    public void onUpdated(Preview.PreviewOutput output) {
                        textureView2.setSurfaceTexture(output.getSurfaceTexture());
                    }
                }
        );
        CameraX.bindToLifecycle(this, preview);
    }

//    private void updateTransform() {
//        Matrix mx = new Matrix();
//        float w = textureView.getMeasuredWidth();
//        float h = textureView.getMeasuredHeight();
//
//        float cX = w/2f;
//        float cY = h/2f;
//
//        int rotationDgr;
//        int rotation = (int)textureView.getRotation();
//
//        switch(rotation){
//            case Surface.ROTATION_0:
//                rotationDgr = 0;
//                break;
//            case Surface.ROTATION_90:
//                rotationDgr = 90;
//                break;
//            case Surface.ROTATION_180:
//                rotationDgr = 180;
//                break;
//            case Surface.ROTATION_270:
//                rotationDgr = 270;
//                break;
//            default:
//                return;
//        }
//
//        //mx.postRotate((float)rotationDgr, cX, cY);
//        textureView.setTransform(mx);
//
//    }

//    public static class Global {
//        public static boolean lrcheck = false;
//        public static void change_rl() {
//            if(!lrcheck) {
//                lrcheck = true;
//            }else {
//                lrcheck = false;
//            }
//        }
//    }

    private boolean allPermissionGranted() {
        for(String permission:REQUIRED_PERMISSIONS){
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return  true;
    }
}
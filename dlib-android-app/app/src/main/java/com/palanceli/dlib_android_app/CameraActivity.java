package com.palanceli.dlib_android_app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by palance on 2017/12/19.
 */

public class CameraActivity extends AppCompatActivity {
    private static final int OVERLAY_PERMISSION_REQ_CODE = 1;
    private static final String[] permissions = new String[]{
            Manifest.permission.SYSTEM_ALERT_WINDOW,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if(!hasOverlayPermission()){
//                requestPermissions(permissions, OVERLAY_PERMISSION_REQ_CODE);
//            }
            if (!Settings.canDrawOverlays(this.getApplicationContext())) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
            }
        }
    }

    @Override   // ④
    public void onRequestPermissionsResult(int requestCode, String[] permission,
                                           int[] grantResults){
        switch (requestCode){
            case OVERLAY_PERMISSION_REQ_CODE:
                if(!hasOverlayPermission()){
                    Toast.makeText(CameraActivity.this, "CameraActivity\", \"SYSTEM_ALERT_WINDOW, permission not granted...", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            default:
                super.onRequestPermissionsResult(requestCode, permission,
                        grantResults);
        }
    }

    private boolean hasOverlayPermission(){
        // 是否有权限在其它app上绘制
        int result = ContextCompat.checkSelfPermission(this, permissions[0]);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this.getApplicationContext())) {
                    Toast.makeText(CameraActivity.this, "CameraActivity\", \"SYSTEM_ALERT_WINDOW, permission not granted...", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            }
        }
    }
}

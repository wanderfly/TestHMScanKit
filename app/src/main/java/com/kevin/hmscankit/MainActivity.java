package com.kevin.hmscankit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.kevin.hmscankit.reflection.ReflectionUtils;
import com.kevin.hmscankit.reflection.Son;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int CODE_REQUEST_CAMERA = 100;
    private static final int CODE_REQUEST_SCAN_ONE = 0X01;
    private Button mBtnScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnScan = findViewById(R.id.btn_scan);

        //ScanKitActivity
        Log.d(TAG, "onCreate: ");

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length < 2 || grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if (CODE_REQUEST_CAMERA == requestCode) {
            //方案一:通过Hmscan工具类开启扫描功能
            //ScanUtil.startScan(this, CODE_REQUEST_SCAN_ONE, new HmsScanAnalyzerOptions.Creator().create());

            //方案二:跳转到指定的的扫描activity
            //Intent intent=new Intent();
            //intent.setClass(this,ScanKitActivity.class);
            //startActivityForResult(intent,CODE_REQUEST_SCAN_ONE);

            //方案三:跳转到自定义的Activity
            Intent intent1 = new Intent();
            intent1.setClass(this, MyScanActivity.class);
            startActivityForResult(intent1, CODE_REQUEST_SCAN_ONE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_REQUEST_SCAN_ONE) {
            if (resultCode == RESULT_OK) {
                Log.d(TAG, "onActivityResult: resultCode:" + resultCode);
                HmsScan obj = data.getParcelableExtra(ScanUtil.RESULT);
                Log.d(TAG, "onActivityResult: " + obj.getOriginalValue());

                mBtnScan.setText(obj.getOriginalValue());
            }
        }
    }

    public void scanQR(View view) {
        if (checkPermission()) {
            startScan();
        } else {
            requestPermission();
        }
    }

    private boolean checkPermission() {

        int isGrant = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        Log.d(TAG, "checkPermission: 授权值:" + isGrant);
        return false;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
                CODE_REQUEST_CAMERA);
    }

    private void startScan() {

    }
}
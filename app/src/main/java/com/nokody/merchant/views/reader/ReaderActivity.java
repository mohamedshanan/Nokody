package com.nokody.merchant.views.reader;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.nokody.merchant.R;
import com.nokody.merchant.base.BaseActivity;
import com.nokody.merchant.utils.Constants;

import org.parceler.Parcels;

import butterknife.BindView;

public class ReaderActivity extends BaseActivity implements QRCodeReaderView.OnQRCodeReadListener {

    private static final int REQUEST_CODE_CAMERA = 101;
    @Nullable
    @BindView(R.id.qrReader)
    QRCodeReaderView qrReader;

    private boolean permissionGranted = false;

    @Override
    protected int getActivityView() {
        return R.layout.activity_reader;
    }

    @Override
    protected int getToolbarTitleResource() {
        return R.string.reader;
    }

    @Override
    protected boolean isHomeAsUpEnabled() {
        return true;
    }

    @Override
    protected void afterInflation(Bundle savedInstance) {
        qrReader.setOnQRCodeReadListener(this);
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        Intent data = new Intent();
        data.putExtra(Constants.ENCODED_TEXT, text);
        setResult(RESULT_OK, data);
        // Don't finish immediately to assure that onActivityResult is called
        Handler handler = new Handler();
        handler.postDelayed(() -> finish(), 300);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (permissionGranted) {
            qrReader.startCamera();
        } else {
            checkCameraPermission();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        qrReader.stopCamera();
    }

    private void checkCameraPermission() {
        int grant = PackageManager.PERMISSION_GRANTED;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == grant) {
            permissionGranted = true;
            qrReader.startCamera();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CODE_CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_CAMERA) {
            if (grantResults != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permissionGranted = true;
                qrReader.startCamera();
            } else {
                finish();
            }
        }
    }
}

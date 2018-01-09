package com.loction.umeng_test;

import android.Manifest;
import android.annotation.TargetApi;
import android.location.GpsStatus;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

public class LocationActivity extends AppCompatActivity {

    private LocationUtils mLocationUtils;
    String[] mPermissionList = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        requestPermissions();
        mLocationUtils = LocationUtils.getInstance(this);
        Toast.makeText(LocationActivity.this, "我回去地址", Toast.LENGTH_LONG).show();

        mLocationUtils.initLocation(new LocationHelper() {
            @Override
            public void UpdateLocation(Location location) {
                Log.e("LocationUtils", "location.getLatitude():" + location.getLatitude());
                Log.e("LocationUtils", "location.getLongitude():" + location.getLongitude());

                mLocationUtils.removeLocationUpdatesListener();
//                String address= LocationUtil.convertAddress(LocationActivity.this,location.getLatitude(),location.getLongitude());
            }

            @Override
            public void UpdateStatus(String provider, int status, Bundle extras) {

            }

            @Override
            public void UpdateGPSStatus(GpsStatus pGpsStatus) {

            }

            @Override
            public void UpdateLastLocation(Location location) {

            }
        });

    }

    @TargetApi(23)
    private void requestPermissions() {
        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this, mPermissionList, new PermissionsResultAction() {
            @Override
            public void onGranted() {
                Toast.makeText(LocationActivity.this, "Permissions have been granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(String permission) {
//                Toast.makeText(getActivity(), "权限已获取到", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

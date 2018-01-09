package com.loction.umeng_test;


import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * Description:获取经纬度辅助类
 *
 * @author: guoyongping
 * @date: 2017/6/7 16:17
 */

public class LocationUtils {
    private static final String TAG = "LocationUtils";

    private volatile static LocationUtils uniqueInstance;
    private LocationHelper mLocationHelper;
    private MyLocationListener myLocationListener;
    private LocationManager mLocationManager;
    private Context mContext;

    private LocationUtils(Context context) {
        mContext = context;
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    /**
     * 采用Double CheckLock(DCL)实现单例
     *
     * @param context 上下文
     * @return 单例
     */
    public static LocationUtils getInstance(Context context) {
        if (uniqueInstance == null) {
            synchronized (LocationUtils.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new LocationUtils(context);
                }
            }
        }
        return uniqueInstance;
    }

    /**
     * 初始化位置信息
     *
     * @param locationHelper 传入位置回调接口
     */
    public void initLocation(LocationHelper locationHelper) {
        Log.e(TAG , "我jinq");

        Location location = null;
        mLocationHelper = locationHelper;
        if (myLocationListener == null) {
            myLocationListener = new MyLocationListener();
        }
        // 需要检查权限,否则编译报错,想抽取成方法都不行,还是会报错。只能这样重复 code 了。
        if ( ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission
                .ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Log.e(TAG, "LocationManager.NETWORK_PROVIDER");
            if (location != null) {
                locationHelper.UpdateLastLocation(location);
            }
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, myLocationListener);
        } else {
            Log.e(TAG, "LocationManager.GPS_PROVIDER");
            location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                locationHelper.UpdateLastLocation(location);
            }
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 50, myLocationListener);
        }
    }

    private class MyLocationListener implements LocationListener {
        //定位服务状态改变会触发该函数
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.e(TAG, "onStatusChanged!");
            if (mLocationHelper != null) {
                mLocationHelper.UpdateStatus(provider, status, extras);
            }
        }

        // 定位功能开启时会触发该函数
        @Override
        public void onProviderEnabled(String provider) {
            Log.e(TAG, "onProviderEnabled!" + provider);
        }

        // 定位功能关闭时会触发该函数
        @Override
        public void onProviderDisabled(String provider) {
            Log.e(TAG, "onProviderDisabled!" + provider);
        }

        // 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        @Override
        public void onLocationChanged(Location location) {
            Log.e(TAG, "onLocationChanged!");
            if (mLocationHelper != null) {
                mLocationHelper.UpdateLocation(location);
            }
        }
    }

    /**
     * 移除定位监听
     */
    public void removeLocationUpdatesListener() {
        // 需要检查权限,否则编译不过
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission
                .ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(myLocationListener);
        }
    }
}

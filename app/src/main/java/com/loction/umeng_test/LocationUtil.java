package com.loction.umeng_test;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Description: 位置工具类
 *
 * @author: QiuXinhai
 * @date: 2017/7/27 14:06
 */
public class LocationUtil {

    /**
     *
     * @param context
     * @param latitude 经度
     * @param longitude 纬度
     * @return 详细位置信息
     */
    public static String convertAddress(Context context, double latitude, double longitude) {
        Geocoder mGeocoder = new Geocoder(context, Locale.getDefault());
        StringBuilder mStringBuilder = new StringBuilder();

        try {
            List<Address> mAddresses = mGeocoder.getFromLocation(latitude, longitude, 1);
            if (!mAddresses.isEmpty()) {
                Address address = mAddresses.get(0);
                mStringBuilder.append(address.getAdminArea()).append(", ").append(address.getLocality()).append(", ").
                        append(address.getSubLocality()).append(", ").append(address.getThoroughfare());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mStringBuilder.toString();
    }

    /**
     * 生成OpSource
     * @param classPath
     * @param appVersion
     * @param systemVersion
     * @param adviceDes
     * @param address
     * @return
     */
    public static String getOpSource(String classPath,String appVersion,String systemVersion,String adviceDes,String address){
        StringBuilder mStringBuilder = new StringBuilder();
        mStringBuilder.append(classPath).append("&").append(appVersion).append("&").
                append(systemVersion).append("&").append(adviceDes).append("&").append(address);
        return mStringBuilder.toString();
    }
}

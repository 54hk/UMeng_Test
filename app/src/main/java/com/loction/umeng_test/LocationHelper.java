package com.loction.umeng_test;

import android.location.GpsStatus;
import android.location.Location;
import android.os.Bundle;

/**
 * Description:定位回调
 *
 * @author: guoyongping
 * @date: 2017/6/7 16:25
 */

public interface LocationHelper {

    void UpdateLocation(Location location);//位置信息发生改变

    void UpdateStatus(String provider, int status, Bundle extras);//位置状态发生改变

    void UpdateGPSStatus(GpsStatus pGpsStatus);//GPS状态发生改变

    void UpdateLastLocation(Location location);

}

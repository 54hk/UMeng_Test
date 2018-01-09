package com.loction.umeng_test;

import android.app.Application;

import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

/**
 * Created by localadmin on 2017/12/10.
 */

public class App extends Application {

    {
//        String id, String secret
        PlatformConfig.setWeixin("wx2bb63b5c22c081b9", "011132d2cd6d7aa316be5323f0229c90");
        PlatformConfig.setQQZone("1106586596", "KEYPivesFRp6fsr9vRe");
        PlatformConfig.setSinaWeibo("4039291686"
                , "abb70eb07ce1da9da0ddda9f307c5e5a"
                ,"https://api.weibo.com/oauth2/default.html");
//        PlatformConfig.setAlipay("2017080408034065");
    }
    @Override
    public void onCreate() {
        super.onCreate();
        UMShareAPI.get(this);
    }
}

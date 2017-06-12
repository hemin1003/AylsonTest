package com.aylson.aylsonProtest.init;

import android.app.Application;

import com.accloud.cloudservice.AC;

/**
 * Created by Administrator on 2016/8/9.
 * <p>
 * 应用代理对象
 * 初始化AbleCloud SDK
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 加载配置
        initAbleCloudConfig();
    }

    private void initAbleCloudConfig() {
        //AC.init(this, "foshanaichen", 1031L, AC.TEST_MODE);
        AC.init(this, "foshanaichen", 1031L);
    }
}

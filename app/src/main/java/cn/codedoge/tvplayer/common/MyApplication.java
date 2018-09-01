package cn.codedoge.tvplayer.common;

import android.app.Application;
import android.content.Context;

/**
 * Created by codedoge on 2018/1/4.
 */

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

}

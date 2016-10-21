package com.liu.andfix;

import android.app.Application;
/**
 * Created by Administrator on 2016-10-20.
 */
public class App extends Application {
    private static App instance = null;

    public App() {
        instance = this;
    }

    public static App getInstance() {
        if(instance == null){
            instance = new App();
            return instance;
        }
        return instance;
    }

    @Override
    public void onCreate() {

        super.onCreate();
        MyAndFix fix = new MyAndFix();
        fix.init(this);
    }
}

package com.liu.config;

import android.content.Context;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Administrator on 2016-10-20.
 */
public class PropertiesUtils {
    private static final String TAG = "PropertiesUtils";
    private static PropertiesUtils instance = null;
    private static Properties proper = null;
    private PropertiesUtils(Context context,String assetsFilename) {
        try {
            InputStream is = context.getAssets().open(assetsFilename);
            proper = new Properties();
            proper.load(is);
        } catch (IOException e) {
            Log.e(TAG,"--Method--PropertiesUtils construction -- assets file error");
            e.printStackTrace();
        }
    }

    public static PropertiesUtils getInstance(Context context,String assetsFilename) {
        if(instance == null) {
            instance = new PropertiesUtils(context,assetsFilename);
            return instance;
        }
        return instance;
    }

    public String getProperty(String key,String defvalue) {
        if(proper != null ) {
            return proper.getProperty(key,defvalue);
        }
        return null;
    }
}

package com.liu.andfix;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by Administrator on 2016-10-20.
 */
public class HttpDownLoadUtils {

    private static String TAG = "HttpDownLoadUtils";
    //下载回调接口
    public interface DownLoadCallback{
        public void downloadSuccess();
        public void downloadFailed();
    }
    /**
     * 从网络上请求下载文件 方法里启动线程
     * @param url   请求地址
     * @param filepath  文件下载路劲 data/data/filepath/
     * @param filename  文件名
     * @param downLoadCallback  下载回调接口
     */
    public static void downloadFile(final String url, final String filepath, final String filename, final DownLoadCallback downLoadCallback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL neturl = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) neturl.openConnection();
                    connection.setConnectTimeout(5000);
                    File file = new File(filepath + filename);
                    int responseCode = connection.getResponseCode();
                    if(responseCode == 200) {
                        InputStream is = connection.getInputStream();
                        if(file.exists()) {
                            file.delete();
                        }else {
                            boolean b = FileUtils.writeStreamToFile(filepath,filename,is);
                            if(b) {
                                if(downLoadCallback != null) {
                                    downLoadCallback.downloadSuccess();
                                }
                            }else {
                                if(downLoadCallback != null) {
                                    downLoadCallback.downloadFailed();
                                }
                            }
                        }
                    }else {
                        downLoadCallback.downloadFailed();
                        Log.d(TAG,"--Method--downloadFile-->responseCode is error--"+responseCode);
                    }
                } catch (MalformedURLException e) {
                    if(downLoadCallback != null) {
                        downLoadCallback.downloadFailed();
                    }
                    Log.d(TAG,"--Method--downloadFile-->URL is not format--"+url);
                    e.printStackTrace();
                } catch (IOException e) {
                    if(downLoadCallback != null) {
                        downLoadCallback.downloadFailed();
                    }
                    Log.d(TAG,"--Method--downloadFile-->responseCode io error--");
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 获取输入流
     * @param url   请求地址
     * @return  InputStream
     */
    public static InputStream getInputStream(String url) {
        HttpURLConnection connection = null;
        try {
            URL neturl = new URL(url);
            connection = (HttpURLConnection) neturl.openConnection();
            connection.setConnectTimeout(5000);
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                return connection.getInputStream();
            } else {
                Log.d(TAG, "--Method--getInputStream-->responseCode is error--" + responseCode);
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "--Method--getInputStream-->responseCode io error--");
            return null;
        }
    }

    /**
     * 将请求流成字符串
     * @param url
     * @return String
     */
    public static String ChangeStreamToString(String url) {
        HttpURLConnection connection = null;
        StringBuffer sb = new StringBuffer();
        String line = "";
        BufferedReader read = null;
        try {
        InputStream is = getInputStream(url);
        if(is != null) {
            read = new BufferedReader(new InputStreamReader(is));
                while((line = read.readLine()) != null) {
                    sb.append(line);
                }
        }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "--Method--ChangeStreamToString-->io error");
        }
        return sb.toString();
    }
}

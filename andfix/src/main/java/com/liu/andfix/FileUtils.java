package com.liu.andfix;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Administrator on 2016-10-20.
 */
public class FileUtils {
    static String TAG = "FIleUtils";

    /**
     * 创建文件
     * @param filename
     * @return  File
     * @throws IOException
     */
    public static File createFile(String filename) throws IOException {
        File file = new File(filename);
        if(!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    /**
     *  创建文件夹
     * @param dirpath
     * @return  File
     */
    public static File createDir(String dirpath) {
        File file = new File(dirpath);
        if(!file.isDirectory() || !file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     *  将输入流写到文件中
     * @param filepath 文件路径
     * @param filename  文件名
     * @param is        输入流
     * @return  true : success , false : failed
     */
    public static boolean writeStreamToFile(String filepath, String filename, InputStream is) {
        File file = null;
        OutputStream out = null;
        if(is == null) {
            Log.e("TAG","----writeStreamToFile-->inputStream is null");
            return false;
        }
        try {
            createDir(filepath);
            file = createFile(filepath+filename);
            out = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int len=0;
            while((len = is.read(buffer))>0) {
                out.write(buffer,0,len);
            }
            out.flush();
            is.close();
            out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG,"--Method--writeStreamToFile-->error");
            return false;
        }
    }
}

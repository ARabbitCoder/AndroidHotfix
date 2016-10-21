package com.liu.andfix;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.alipay.euler.andfix.patch.PatchManager;
import com.liu.config.Config;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2016-10-20.
 */
public class MyAndFix {
    public static final String TAG = "MyAndFix";
    public static final String apatchFile = "fix.apatch";
    public static InfoHandler handler = null;

    public void init(Context context) {
        handler = new InfoHandler(context);
        inject(context);
    }
    public static void  inject(final Context context) {

        final String apatchpath = context.getDir("apatch",Context.MODE_PRIVATE).getAbsolutePath()+"/";
        final PatchManager patch = new PatchManager(context);
        patch.init(BuildConfig.VERSION_CODE+"");
        patch.loadPatch();
        HttpDownLoadUtils.downloadFile(Config.getInstance().getFixapatchUrl(), apatchpath, apatchFile, new HttpDownLoadUtils.DownLoadCallback() {
            @Override
            public void downloadSuccess() {
                File file = new File(apatchpath+apatchFile);
                if(file.exists()) {
                    try {
                        patch.addPatch(apatchpath+apatchFile);
                        Message msg = new Message();
                        msg.obj = "repaired completed";
                        handler.sendMessage(msg);
                    } catch (IOException e) {
                        Log.e(TAG,"--method--inject--addPath error");
                        Message msg = new Message();
                        msg.obj = "repaired failed";
                        handler.sendMessage(msg);
                        e.printStackTrace();
                    }
                }else {
                    Message msg = new Message();
                    msg.obj = "repaired failed";
                    handler.sendMessage(msg);
                }
            }

            @Override
            public void downloadFailed() {
                Message msg = new Message();
                msg.obj = "download patch file failed";
                handler.sendMessage(msg);
            }
        });
    }

    private class InfoHandler extends Handler {
        Context context;
        public InfoHandler(Context context) {
            this.context = context;
        }

        @Override
        public void handleMessage(Message msg) {
            String info =msg.obj.toString();
            Toast.makeText(context,info,Toast.LENGTH_SHORT).show();
            super.handleMessage(msg);
        }
    }
}

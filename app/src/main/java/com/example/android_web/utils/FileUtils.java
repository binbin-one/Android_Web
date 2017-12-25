package com.example.android_web.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * 文件操作的工具类
 */
public class FileUtils {
    //将一个输入流转换为一个字符串
    public static String formatStreamToString(InputStream stream){
        if(stream!=null){
            ByteArrayOutputStream out=new ByteArrayOutputStream();
            byte[] bytes=new byte[1024];
            int len=0;
            try {
                while((len=stream.read(bytes))!=-1){
                    out.write(bytes,0,len);
                }
                String str=out.toString();
                out.flush();
                out.close();
                stream.close();
                return str;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //执行下载文件到指定位置
    public static void downLoadFile(final String fromPath, final String savePath, final CallBack callBack){
        if(fromPath!=null&&savePath!=null){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url=new URL(fromPath);
                        HttpURLConnection conn=(HttpURLConnection) url.openConnection();
                        conn.setConnectTimeout(20*1000);
                        conn.connect();
                        InputStream input=conn.getInputStream();
                        File file=new File(savePath);
                        if(!file.getParentFile().exists())
                            file.getParentFile().mkdirs();
                        OutputStream out=new FileOutputStream(file);
                        byte[] bytes=new byte[1024];
                        for(int len=0;(len=input.read(bytes))!=-1;){
                            out.write(bytes,0,len);
                        }
                        out.flush();
                        out.close();
                        input.close();
                        callBack.success();//下载成功
                    } catch (Exception e) {
                        e.printStackTrace();
                        callBack.failed();//下载失败
                    }
                }
            }).start();
        }
    }

    public static boolean existsFile(String path){
        if(path!=null&&path.length()>0) {
            File file = new File(path);
            if(file.exists())
                return true;
        }
        return false;
    }
}

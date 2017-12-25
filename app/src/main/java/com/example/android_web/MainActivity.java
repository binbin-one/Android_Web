package com.example.android_web;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img=(ImageView) findViewById(R.id.img);
    }
    //图像下载，所有操作必须在多线程中
    public void downLoadImage(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //构建URL:java.net
                    URL url=new URL("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=4117439175,1062059118&fm=11&gp=0.jpg");
                    //HttpURLConnection是java的标准类，继承自URLConnection类，实现Web通信，获取创建连接
                    HttpURLConnection conn=(HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(10*1000);
                    conn.connect();
                    //获取服务器响应的数据流
                    InputStream input=conn.getInputStream();
                    //将数据流转换为图像
                    final Bitmap bitmap= BitmapFactory.decodeStream(input);
                    //关闭输入流
                    input.close();
                    //将图像设置到ImgView中
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            img.setImageBitmap(bitmap);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    //下载图像，并保存到SD卡指定目录下
    public void downLoadImage2(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //构建url
                    URL url=new URL("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1221542024,4073538465&fm=27&gp=0.jpg");
                    //获取创建连接
                    HttpURLConnection conn=(HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(10*1000);
                    conn.connect();
                    //获取服务器响应的数据输入流
                    InputStream input=conn.getInputStream();
                    //获取SD卡目录
                    final String path= Environment.getExternalStorageDirectory().getAbsolutePath()+"/img/img_1.jpg";
                    File file=new File(path);
                    if(!file.getParentFile().exists()){
                        file.getParentFile().mkdir();
                    }
                    //构建输出流
                    OutputStream out= new FileOutputStream(file);
                    byte[] bytes=new byte[1024];
                    while((input.read(bytes)!=-1)){
                        out.write(bytes);
                    }
                    out.flush();
                    out.close();
                    input.close();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //将数据流转换为图像
                            final Bitmap bitmap= BitmapFactory.decodeFile(path);
                            img.setImageBitmap(bitmap);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    //下载图像，并保存到应用程序指定目录下
    public void downLoadImage3(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //构建URL
                    URL url=new URL("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2721321119,1637202123&fm=27&gp=0.jpg");
                    //获取创建连接
                    HttpURLConnection conn=(HttpURLConnection) url.openConnection();
                    //设置连接时间
                    conn.setConnectTimeout(10*1000);
                    conn.connect();
                    //获取服务器响应的数据输入流
                    InputStream input=conn.getInputStream();
                    //获取应用程序根目录
                    final String path=getApplicationContext().getFilesDir().getAbsolutePath()+"/img/img2.jpg";
                    //查找父级目录是否存在，不存在则创建父级目录
                    File file=new File(path);
                    if(!file.getParentFile().mkdir()){
                        file.getParentFile().mkdir();
                    }
                    //构建文件输出流，向指定文件写入数据
                    OutputStream out=new FileOutputStream(file);
                    byte[] bytes=new byte[1024];
                    for(int len=0;(len= input.read(bytes))!=-1;){
                        out.write(bytes,0,len);
                    }
                    out.flush();
                    out.close();
                    input.close();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //读取指定文件数据流转换为图片
                            Bitmap bitmap=BitmapFactory.decodeFile(path);
                            img.setImageBitmap(bitmap);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
package com.sty.tear.off.clothes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView ivSrc;
    private ImageView ivDes;

    private Bitmap alterBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setListeners();
    }

    private void initViews(){
        //1.找到要操作的图片
        ivSrc = findViewById(R.id.iv_src);
        ivDes = findViewById(R.id.iv_des);

        //2.把要操作的图片转换成bitmap
        Bitmap srcBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.pre19);

        //3.创建原图的副本
        //3.1创建模板
        alterBitmap = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), srcBitmap.getConfig());
        //3.2以alterBitmap为模板创建一个画布
        Canvas canvas = new Canvas(alterBitmap);
        //3.3创建一个画笔
        Paint paint  = new Paint();
        //3.4开始作画
        canvas.drawBitmap(srcBitmap, new Matrix(), paint);

        //4.把alterBitmap显示到
        ivDes.setImageBitmap(alterBitmap);
    }

    private void setListeners(){
        //5.给iv设置一个触摸事件
        ivDes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //6.获取触摸事件类型
                int action = event.getAction();
                switch (action){
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE: //移动
                        for(int i = -7; i < 7; i++) {
                            for(int j = -7; j < 7; j++) {
                                if(Math.sqrt(i * i + j * j) <= 7) { //画一个圆
                                    //一次修改一个像素
                                    try{
                                        alterBitmap.setPixel((int) event.getX() + i, (int) event.getY() + j, Color.TRANSPARENT);
                                    }catch (Exception e){
                                        //e.printStackTrace();
                                    }
                                }
                            }
                        }
                        //一定要记得更新iv
                        ivDes.setImageBitmap(alterBitmap);
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    default:
                        break;
                }

                return true;
            }
        });
    }
}

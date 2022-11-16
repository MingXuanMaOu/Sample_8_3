package com.example.sample_8_3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import static com.example.sample_8_3.Map.source;
import static com.example.sample_8_3.Map.target;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    MainActivity activity;
    Paint paint;
    LBX lbx = new LBX();
    Game game = new Game(this);


    public MySurfaceView(MainActivity activity) {
        super(activity);
        this.activity = activity;
        this.getHolder().addCallback(this);
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    public void doSearch(){
        game.DFS();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        lbx.drawMap(canvas,paint);

        ArrayList<int[][]> searchProcess = game.searchProcess;
        for (int i = 0; i < searchProcess.size(); i++) {
            int[][] edge = searchProcess.get(i);
            paint.setARGB(255,0,0,0);
            float[] p1 = lbx.getPosition(edge[0][1],edge[0][0]);
            float[] p2 = lbx.getPosition(edge[1][1],edge[1][0]);

            canvas.drawLine(
                    p1[0],p1[1],p2[0],p2[1],paint
            );
        }
        if(game.pathFlag) {
            paint.setPathEffect(PathUtil.getDirectionDashEffect());
            HashMap<String, int[][]> hm = game.hm;
            int[] temp = game.target;
            int count = 0;//路径长度计数器

            while (true) {
                int[][] tempA = hm.get(temp[0] + ":" + temp[1]);
                paint.setARGB(255, 0, 0, 0);
                paint.setStrokeWidth(9);
                paint.setStrokeCap(Paint.Cap.ROUND);
                paint.setStrokeJoin(Paint.Join.ROUND);
                float[] p1 = lbx.getPosition(tempA[0][1], tempA[0][0]);
                float[] p2 = lbx.getPosition(tempA[1][1], tempA[1][0]);
                canvas.drawLine
                        (
                                p2[0],
                                p2[1],
                                p1[0],
                                p1[1],
                                paint
                        );

                count++;
                //判断有否到出发点
                if (tempA[1][0] == game.source[0] && tempA[1][1] == game.source[1]) {
                    break;
                }

                temp = tempA[1];
            }



    }

        paint.setPathEffect(null);
        paint.setARGB(255,255,0,0);
        float[] positon = lbx.getPosition(source[1],source[0]);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(40);
        canvas.drawText("S",positon[0]-10,positon[1]+12,paint);

        paint.setARGB(255,0,255,0);
        positon = lbx.getPosition(target[0][1],target[0][0]);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(40);
        canvas.drawText("T",positon[0]-10,positon[1]+14,paint);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        repaint();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    public void repaint(){
        SurfaceHolder holder = this.getHolder();
        Canvas canvas = holder.lockCanvas();
        try{
            synchronized (holder){
                onDraw(canvas);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(canvas != null){
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

}

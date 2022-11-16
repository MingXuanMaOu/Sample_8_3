package com.example.sample_8_3;

import static com.example.sample_8_3.Map.MAP_DATA;
import static com.example.sample_8_3.Map.a;
import static com.example.sample_8_3.Map.b;
import static com.example.sample_8_3.Map.h;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class LBX {

    final float yGlobalOffset = 30;
    final float xGlobalOffset = 30;
    final float xStartA = 0;
    final float xStartB = 0 + h;

    Path mPatha = new Path();

    public LBX(){
        //初始化三角形路径
        mPatha.moveTo(0, -a);
        mPatha.lineTo(h, -b);
        mPatha.lineTo(h, b);
        mPatha.lineTo(0, a);
        mPatha.lineTo(-h, b);
        mPatha.lineTo(-h, -b);
        mPatha.lineTo(0, -a);
    }

    public void drawMap(Canvas canvas, Paint paint){
        int[] colorBlack=new int[]{255,0,0,0};
        int[] colorWhite=new int[]{255,255,255,255};

        for (int row = 0; row < MAP_DATA.length; row++) {
            float yOffset = row * (a + b) + a + yGlobalOffset;
            float xStart = (row % 2 == 0) ? xStartA: xStartB;

            for (int col = 0; col < MAP_DATA[row].length; col++) {
                float xOffset = xStart + col * 2 * h + xGlobalOffset;
                int[] color = null;
                if(MAP_DATA[row][col] == 1){
                    color = colorBlack;
                }else{
                    color = colorWhite;
                }
                drawSelf(canvas,paint,xOffset,yOffset,color);
            }
        }
    }

    private void drawSelf(Canvas canvas, Paint paint, float xOffset, float yOffset, int[] color) {
        canvas.save();
        canvas.translate(xOffset,yOffset);

        paint.setARGB(color[0],color[1],color[2],color[3]);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(mPatha,paint);
        paint.setARGB(255,128,128,128);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        canvas.drawPath(mPatha,paint);
        canvas.restore();
    }

    public float[] getPosition(int row,int col)
    {
        float yOffset=row*(a+b)+a+yGlobalOffset;
        float xStart=(row%2==0)?xStartA:xStartB;
        float xOffset=xStart+col*2*h + xGlobalOffset;

        return new float[]{xOffset,yOffset};
    }
}

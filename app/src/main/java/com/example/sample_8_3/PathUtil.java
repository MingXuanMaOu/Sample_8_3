package com.example.sample_8_3;

import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;

public class PathUtil {
    static  Path makePathDash()
    {
        Path p = new Path();
        p.moveTo(4, 0);
        p.lineTo(0, -4);
        p.lineTo(8, -4);
        p.lineTo(12, 0);
        p.lineTo(8, 4);
        p.lineTo(0, 4);
        return p;
    }

    static PathEffect getDirectionDashEffect()
    {
        PathEffect result=null;

        result=new PathDashPathEffect
                (
                        makePathDash(), //形状
                        13, //间距
                        0,//首绘制偏移量
                        PathDashPathEffect.Style.ROTATE
                );

        return result;
    }
}

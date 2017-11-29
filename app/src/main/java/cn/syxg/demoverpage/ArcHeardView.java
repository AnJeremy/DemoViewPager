package cn.syxg.demoverpage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/11/29.
 */

public class ArcHeardView extends View{

    private Paint mPaint;//画笔
    private PointF mStartPoint,mEndPoint,mControlPoint;//贝塞尔曲线  起始点  终点  控制点
    private int mWidth;
    private int mHeight;
    private Path path = new Path();
    private int mArcHeight = 100;//圆弧高度
    private int mStartColor;
    private int mEndColor;
    private LinearGradient mLinearGradient;//线性渲染
    public ArcHeardView(Context context) {
        super(context);

        init();
    }

    public ArcHeardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public ArcHeardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ArcHeardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init();
    }



    private void init(){

        mPaint = new Paint();
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.FILL);//画笔为实心

        mStartPoint = new PointF(0, 0);
        mEndPoint = new PointF(0, 0);
        mControlPoint = new PointF(0, 0);


        mStartColor = Color.parseColor("#337E83");
        mEndColor = Color.parseColor("#63B0A8");

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;

        path.reset();//清除path设置的所有属性
        path.moveTo(0,0);
        path.addRect(0,0,mWidth,mHeight-mArcHeight,Path.Direction.CCW);

        mStartPoint.x = 0;
        mStartPoint.y = mHeight - mArcHeight;
        mEndPoint.x = mWidth;
        mEndPoint.y = mHeight - mArcHeight;
        mControlPoint.x = mWidth/2 - 50;
        mControlPoint.y = mHeight + 100;

        mLinearGradient = new LinearGradient(mWidth / 2, 0, mWidth / 2, mHeight, mStartColor, mEndColor, Shader.TileMode.MIRROR);

        ///SweepGradient sweepGradient = new SweepGradient(mEndPoint.x / 2,mEndPoint.y / 2,mStartColor,mEndColor);
        //mPaint.setShader(sweepGradient);


        invalidate();

       /* mWidth = w;
        mHeight = h;

        path.reset();
        // 上半部分矩形
        path.moveTo(0, 0);
        path.addRect(0, 0, mWidth, mHeight - mArcHeight, Path.Direction.CCW);
        // 起始点
        mStartPoint.x = 0;
        mStartPoint.y = mHeight - mArcHeight;
        // 终点
        mEndPoint.x = mWidth;
        mEndPoint.y = mHeight - mArcHeight;
        // 控制点
        mControlPoint.x = mWidth / 2 - 50;
        mControlPoint.y = mHeight + 100;
        // 初始化shader
        mLinearGradient = new LinearGradient(mWidth / 2, 0, mWidth / 2, mHeight, mStartColor, mEndColor, Shader.TileMode.MIRROR);


        invalidate();*/

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setShader(mLinearGradient);

        path.moveTo(mStartPoint.x, mStartPoint.y);
        //绘画贝塞尔曲线
        path.quadTo(mControlPoint.x, mControlPoint.y, mEndPoint.x, mEndPoint.y);

        canvas.drawPath(path, mPaint);
    }



    /**
     *
     * @param startColor
     * @param endColor
     */
    public void setColor(@ColorInt int startColor, @ColorInt int endColor) {
        mStartColor = startColor;
        mEndColor = endColor;
        mLinearGradient = new LinearGradient(mWidth / 2, 0, mWidth / 2, mHeight, mStartColor, mEndColor, Shader.TileMode.MIRROR);
        invalidate();
    }
}

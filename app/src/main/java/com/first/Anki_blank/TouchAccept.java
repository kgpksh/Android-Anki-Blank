package com.first.Anki_blank;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class TouchAccept extends ScrollView {
    Paint paint;
    float lastx;
    float lasty;
    Canvas mCanvas;
    Bitmap mBitmap;
    public TouchAccept(Context context) {
        super(context);
        init(context);
    }

    public TouchAccept(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context){
        int innerColor=Color.parseColor("#03A9F4");
        paint=new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(innerColor);
        paint.setStrokeWidth(10);
        lastx=-1;
        lasty=-1;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap=Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        mCanvas=new Canvas();
        mCanvas.setBitmap(mBitmap);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (mBitmap!=null){
            canvas.drawBitmap(mBitmap,0,0,null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action=ev.getAction();
        
        float x= ev.getX();
        float y= ev.getY();

        switch (action){
            case MotionEvent.ACTION_UP:
                lastx=-1;
                lasty=-1;
                mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                break;
            case MotionEvent.ACTION_DOWN:
                lastx=x;
                lasty=y;
                break;
            case MotionEvent.ACTION_MOVE:
                if (lastx!=-1){
                    mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                    mCanvas.drawRect(lastx,lasty,x,y,paint);
                }
                break;
        }
        invalidate();
        return true;
    }
}

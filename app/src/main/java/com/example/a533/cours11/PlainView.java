package com.example.a533.cours11;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.opengl.GLException;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class PlainView extends View {
    private float zoomLevel = 1f;
    private float currX = 0;
    private float currY = 0;
    private List<PlainViewDisplayable> objectsToDisplay;

    private ScaleGestureDetector scaleGestureDetector;
    private GestureDetector gestureDetector;

    private Marqueur marqueur;

    public PlainView(Context context) {
        super(context);
        Init(context,null);
    }

    public PlainView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Init(context,attrs);
    }

    public PlainView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Init(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /*Paint myPaint = new Paint();
        myPaint.setTextSize(15);
        canvas.drawText("test1", 10,100,myPaint );*/
        canvas.save();
        canvas.scale(zoomLevel,zoomLevel);
        displayObject(canvas);
        canvas.restore();
    }

    private void Init(Context context, AttributeSet set){
        objectsToDisplay = new LinkedList<PlainViewDisplayable>();
        scaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());
        gestureDetector = new GestureDetector(context, new GestureListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        gestureDetector.onTouchEvent(event);
        return true;
    }

    private void displayObject(Canvas canvas){
        for(PlainViewDisplayable objectToDisplay : objectsToDisplay){
            int positionImageX = (int)objectToDisplay.getPositionX();
            int positionImageY = (int)objectToDisplay.getPositionY();
            int imageToDisplayLeftPosition = 0;
            int imageToDisplayRightPosition = (int)objectToDisplay.getWidth();
            int imageToDisplayTopPosition = 0;
            int imageToDisplayBottomPosition = (int)objectToDisplay.getHeight();

            int imageWhereToDisplayLeftPosition = (int)(positionImageX - currX);
            int imageWhereToDisplayRightPosition = imageWhereToDisplayLeftPosition
                    + imageToDisplayRightPosition - imageToDisplayLeftPosition;
            int imageWhereToDisplayTopPosition = (int)(positionImageY + currY);
            int imageWhereToDisplayBottomPosition = imageWhereToDisplayTopPosition
                    + imageToDisplayBottomPosition - imageToDisplayTopPosition;

            canvas.drawBitmap(objectToDisplay.getBitmap(),
                    new Rect(imageToDisplayLeftPosition,imageToDisplayTopPosition,
                            imageToDisplayRightPosition, imageToDisplayBottomPosition),
                    new Rect(imageWhereToDisplayLeftPosition,imageWhereToDisplayTopPosition,
                            imageWhereToDisplayRightPosition,imageWhereToDisplayBottomPosition),null);
        }

    }

    public void addElementToDisplay(PlainViewDisplayable newObjectToDisplay){
        objectsToDisplay.add(newObjectToDisplay);
        invalidate();
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            zoomLevel *= detector.getScaleFactor();
            zoomLevel = Math.max(0.1f,Math.min(zoomLevel,5.0f));
            invalidate();
            return true;
        }
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
           currX += distanceX/zoomLevel;
           currY -= distanceY/zoomLevel;
           invalidate();
           return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Addmarker(e);
        }
    }

    private void Addmarker(MotionEvent event){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.marqueur);
        addElementToDisplay(new Marqueur(bitmap,
                (int)(event.getX()/zoomLevel),
                (int)(event.getY()/zoomLevel)));

    }


}

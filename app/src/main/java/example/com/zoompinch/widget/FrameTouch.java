package example.com.zoompinch.widget;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.FrameLayout;

/**
 * Created by nodo on 2/21/17.
 */

public class FrameTouch extends FrameLayout implements ScaleGestureDetector.OnScaleGestureListener {

    GestureDetectorCompat detector;
    ScaleGestureDetector scaleDetector;
    FrameOnTouch frameOnTouch;

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    public interface FrameOnTouch {
        void onFrameTouchUp();
        void onScrollMovie(float x, float y);
    }


    public FrameTouch(Context context) {
        super(context);
        detector = new GestureDetectorCompat(context, new MyGestureListener());
    }

    public FrameTouch(Context context, AttributeSet attrs) {
        super(context, attrs);
        detector = new GestureDetectorCompat(context, new MyGestureListener());
        scaleDetector = new ScaleGestureDetector(context, this);
    }

    public void setFrameOnTouch(FrameOnTouch frameOnTouch) {
        this.frameOnTouch = frameOnTouch;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP)
            frameOnTouch.onFrameTouchUp();

        super.dispatchTouchEvent(event);
        return detector.onTouchEvent(event);

    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            frameOnTouch.onScrollMovie(distanceX, distanceY);

            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return super.onSingleTapUp(e);
        }
    }
}

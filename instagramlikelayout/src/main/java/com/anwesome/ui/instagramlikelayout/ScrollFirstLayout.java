package com.anwesome.ui.instagramlikelayout;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by anweshmishra on 21/12/16.
 */
public class ScrollFirstLayout extends LinearLayout {
    private GestureDetector gestureDetector;
    private boolean closed = false;

    public LinearLayout getMovingView() {
        return movingView;
    }

    public LinearLayout getFixedView() {
        return fixedView;
    }

    private int y1 = 0,h =200,i=0,h1=0;
    private LinearLayout movingView,fixedView;
    private class FirstGestureListener extends GestureDetector.SimpleOnGestureListener {
        public boolean onDown(MotionEvent event) {
            return true;
        }
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
        public boolean onScroll(MotionEvent e1,MotionEvent e2,float velx,float vely) {
            if(y1>=h1) {
                closed = true;
            }
            y1+=vely;
            int w = ScrollFirstLayout.this.getMeasuredWidth();
            int h2 = fixedView.getMeasuredHeight();
            ScrollFirstLayout.this.setTranslationY(-y1);
            ScrollFirstLayout.this.setClipBounds(new Rect(0,0,w,h1-y1+h2));
            movingView.setClipBounds(new Rect(0,y1,w,h1));
            fixedView.setClipBounds(new Rect(0,0,w,h2));
            fixedView.setTranslationY(y1);
//            if(instagramLikeLayout!=null) {
//                instagramLikeLayout.requestLayout();
//                instagramLikeLayout.onScrolled((int)velx,(int)vely);
//            }
            return true;
        }
    }
    public ScrollFirstLayout(Context context, AttributeSet attrs) {
        super(context,attrs);
        h = getMeasuredHeight();
        gestureDetector = new GestureDetector(new FirstGestureListener());

    }
    public boolean onTouchEvent(MotionEvent event) {
        if(i == 0) {
            h = getMeasuredHeight();
            movingView = (LinearLayout) findViewById(R.id.moving_view);
            fixedView = (LinearLayout)findViewById(R.id.fixed_view);
            movingView.setClipChildren(true);
            fixedView.setClipChildren(true);
            h1 = movingView.getMeasuredHeight();
        }
        i++;
        return gestureDetector.onTouchEvent(event);
    }

    public float getNextViewY() {
        return fixedView.getMeasuredHeight()+movingView.getMeasuredHeight()-y1;
    }
    public boolean isClosed() {
        return closed;
    }
}

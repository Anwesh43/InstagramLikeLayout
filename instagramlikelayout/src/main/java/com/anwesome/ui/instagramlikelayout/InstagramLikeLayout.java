package com.anwesome.ui.instagramlikelayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anweshmishra on 21/12/16.
 */
public class InstagramLikeLayout extends RecyclerView{
    private List<PicTextElement> picTextElements = new ArrayList<>();
    private int currentPosition = 0;
    private GestureDetector gestureDetector;
    public InstagramLikeLayout(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public InstagramLikeLayout(Context context) {
        super(context);
    }
    public void addElement(PicTextElement picTextElement) {
        List<PicTextElement> newElementList = new ArrayList<>();
        newElementList.add(picTextElement);
        for(PicTextElement element:picTextElements) {
            newElementList.add(element);
        }
        picTextElements = newElementList;
    }
    public void create() {
        gestureDetector = new GestureDetector(new ScrollInnerViewListener());
        setLayoutManager(new ControllableLayoutManager(getContext()));
        setAdapter(new InstagramLikeAdapter());
        addItemDecoration(new CustomDecorator());
    }
    public void onScrolled(int dx,int dy) {
        Log.d("dx,dy",dx+","+dy);
        super.findChildViewUnder(dx,dy);
    }
    public boolean onTouchEvent(MotionEvent event) {
//        View child = findChildViewUnder(event.getX(),event.getY());
//        if(child!=null) {
//            ScrollFirstLayout scrollFirstLayout = (ScrollFirstLayout) child;
//            //scrollFirstLayout.setInstagramLikeLayout(this);
//            if (!scrollFirstLayout.isClosed()) {
//                return scrollFirstLayout.onTouchEvent(event);
//            }
//        }
        boolean touchEventFlag = super.onTouchEvent(event);
        if(!(currentPosition == picTextElements.size()-1 && getChildCount() == 1)) {
            ScrollFirstLayout scrollFirstLayout = (ScrollFirstLayout) getChildAt(0);
            LinearLayout fixedView = scrollFirstLayout.getFixedView();
            if (scrollFirstLayout.getMeasuredHeight() + scrollFirstLayout.getY() >= fixedView.getMeasuredHeight()) {
                fixedView.bringToFront();
                fixedView.setY(-scrollFirstLayout.getY());
            } else if (scrollFirstLayout.getMeasuredHeight() + scrollFirstLayout.getY() < 10) {
                fixedView.setY(0);
                scrollFirstLayout.removeView(fixedView);
                scrollFirstLayout.addView(fixedView, 0);
            }
        }
        return touchEventFlag;
    }
    private class InstagramLikeAdapter extends RecyclerView.Adapter<InstagramLikeViewHolder> {


        public InstagramLikeViewHolder onCreateViewHolder(ViewGroup root,int flag) {
            LayoutInflater layoutInflater = (LayoutInflater) root.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.scrollable_view,root,false);
            InstagramLikeViewHolder viewHolder = new InstagramLikeViewHolder(view);
            return viewHolder;
        }
        public void onBindViewHolder(InstagramLikeViewHolder viewHolder,int position) {
            currentPosition = position;
            viewHolder.bindViewData(picTextElements.get(position));
        }
        public int getItemCount() {
            return picTextElements.size();
        }
    }
    private class InstagramLikeViewHolder extends RecyclerView.ViewHolder {
        private ImageView mainImage,profileImage;
        private TextView title,status;
        private LinearLayout fixedView;
        public InstagramLikeViewHolder(View view) {
            super(view);
            mainImage = (ImageView) view.findViewById(R.id.img_view);
            title = (TextView) view.findViewById(R.id.view_title);
            status = (TextView)view.findViewById(R.id.status_view);
            profileImage = (ImageView)view.findViewById(R.id.profile_image);
            fixedView = (LinearLayout)view.findViewById(R.id.fixed_view);
            //fixedView.bringToFront();
        }
        public void bindViewData(PicTextElement picTextElement) {
            mainImage.setImageBitmap(picTextElement.getBitmap());
            title.setText(picTextElement.getProfileName());
            status.setText(picTextElement.getStatus());
            //mainImage.setImageBitmap(getCircularBitmap(picTextElement.getBitmap()));
            profileImage.setImageBitmap(getCircularBitmap(picTextElement.getProfileBitmap()));
        }
        private Bitmap getCircularBitmap(Bitmap bitmap) {
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            int w = bitmap.getWidth(),h = bitmap.getHeight();
            Bitmap newBitmap = Bitmap.createBitmap((w),(h), Bitmap.Config.ARGB_8888);
            int radius = w/2;
            if(w>h) {
                radius = h/2;
            }
            Canvas canvas = new Canvas(newBitmap);
            Path path = new Path();
            path.addCircle(w/2,h/2,radius, Path.Direction.CCW);
            canvas.clipPath(path);
            canvas.drawBitmap(bitmap,0,0,paint);
            return newBitmap;
        }
    }
    private class CustomDecorator extends ItemDecoration {
        public void getItemOffsets(Rect outRect,View view,RecyclerView parent,State state) {
            outRect.bottom = 20;
            outRect.top = 20;
            outRect.left = 5;
            outRect.right = 5;
        }
    }
    private class ScrollInnerViewListener extends GestureDetector.SimpleOnGestureListener {
        int index = 0;
        int j = 0;
        public boolean onDown(MotionEvent event) {
            return true;
        }
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
        public boolean onScroll(MotionEvent e1,MotionEvent e2,float velx,float vely) {
            if(e1!=null && e2!=null && picTextElements.size()>0 ) {
                View view = null;
                if(getChildCount() == 1) {
                    try {
                        Recycler mRecycler = null;
                        Field recyclerField = RecyclerView.class.getDeclaredField("mRecycler");
                        recyclerField.setAccessible(true);
                        mRecycler = (Recycler)recyclerField.get(InstagramLikeLayout.this);

                        if(e1.getY()>e2.getY() && index<picTextElements.size()-1) {
                            view = mRecycler.getViewForPosition(index+1);
                        }
                        else if(e1.getY()<e2.getY() && index!=0) {
                            view = mRecycler.getViewForPosition(index-1);
                        }
                        if(view!=null) {
                            addView(view);
                        }
                    }
                    catch (Exception ex) {

                    }
                }
                j++;
                ScrollFirstLayout firstLayout = null;
                if(getChildCount()>0) {
                    firstLayout = (ScrollFirstLayout)getChildAt(0);
                    firstLayout.onTouchEvent(e2);
                    if(e2.getY()<e1.getY()) {

                        if(firstLayout.isClosed() && index<picTextElements.size()-1) {
                            removeView(firstLayout);
                            index++;
                            j = 0;
                        }
                    }
                    else if(e1.getY()>e2.getY()) {
                        if(index>0 && !firstLayout.isClosed()) {
                            index--;
                            j=0;
                        }
                    }
                }

                if(getChildCount() == 2) {
                    ScrollFirstLayout secondLayout = (ScrollFirstLayout)getChildAt(1);
                    if(firstLayout!=null) {
                        secondLayout.setY(firstLayout.getNextViewY());
                    }
                }
            }

            return true;
        }
    }
    private class ControllableLayoutManager extends LinearLayoutManager {
        public ControllableLayoutManager(Context context) {
            super(context);
        }
        public void onLayoutChildren(Recycler recycler,RecyclerView.State state) {
            super.onLayoutChildren(recycler,state);
        }
    }
}

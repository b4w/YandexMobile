package com.app.mobile.yandex.b4w.yandexmobileapplication.model.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by KonstantinSysoev on 07.04.16.
 * <p/>
 * Class for handling of clicking on artist item.
 */
public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

    public interface IOnItemClickListener {
        void onItemClick(View view, int position);
    }

    private GestureDetector gestureDetector;
    private IOnItemClickListener iOnItemClickListener;

    public RecyclerItemClickListener(Context context, IOnItemClickListener iOnItemClickListener) {
        this.iOnItemClickListener = iOnItemClickListener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View childView = rv.findChildViewUnder(e.getX(), e.getY());
        boolean result = false;
        if (childView != null && iOnItemClickListener != null && gestureDetector.onTouchEvent(e)) {
            iOnItemClickListener.onItemClick(childView, rv.getChildPosition(childView));
            result = true;
        }
        return result;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}

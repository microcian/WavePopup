package com.wavepopuplib.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Interpolator;

public class CRImageView extends AppCompatImageView implements CircleRevealHelper.CircleRevealEnable {

    private CircleRevealHelper mCircleRevealHelper;

    public CRImageView(Context context) {
        super(context);
        init();
    }

    public CRImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CRImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getParent() instanceof GrowUpParent)
            return ((GrowUpParent) getParent()).onParentHandMotionEvent(event);
        return super.onTouchEvent(event);
    }

    private void init() {
        mCircleRevealHelper = new CircleRevealHelper(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mCircleRevealHelper.onDraw(canvas);
    }

    @Override
    public void superOnDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public void circularReveal(int centerX, int centerY, float startRadius, float endRadius, long duration, Interpolator interpolator) {
        mCircleRevealHelper.circularReveal(centerX, centerY, startRadius, endRadius, duration, interpolator);
    }

    @Override
    public void circularReveal(int centerX, int centerY, float startRadius, float endRadius) {
        mCircleRevealHelper.circularReveal(centerX, centerY, startRadius, endRadius);
    }


}

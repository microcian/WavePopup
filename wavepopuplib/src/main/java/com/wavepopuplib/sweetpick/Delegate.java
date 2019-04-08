package com.wavepopuplib.sweetpick;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.wavepopuplib.SimpleAnimationListener;
import com.wavepopuplib.effects.Effect;
import com.wavepopuplib.listener.ICheckChanged;

import java.util.List;

public abstract class Delegate implements View.OnClickListener {

    SweetSheet.Status mStatus = SweetSheet.Status.DISMISS;
    ViewGroup mParentVG;
    View mRootView;
    private ImageView mBg;
    private Effect mEffect;
    private boolean mIsBgClickEnable = true;
    SweetSheet.OnMenuItemClickListener mOnMenuItemClickListener;

    protected void init(ViewGroup parentVG) {
        mParentVG = parentVG;
        mBg = new ImageView(parentVG.getContext());
        mRootView = createView();
        mBg.setOnClickListener(this);
    }

    protected abstract View createView();

    protected abstract void setMenuList(List<String> list);
    protected abstract void setIsMultipleSelection(boolean isMultiple);
    protected abstract void setStoredData(String storedData);
    protected abstract void setCheckedChangeListener(ICheckChanged iCheckChanged, int viewId);


    void toggle() {
        switch (mStatus) {
            case SHOW:
            case SHOWING:
                dismiss();
                break;
            case DISMISS:
            case DISMISSING:
                show();
                break;
            default:
                break;
        }
    }

    protected void show() {
        if (getStatus() != SweetSheet.Status.DISMISS)
            return;
        mBg.setClickable(mIsBgClickEnable);
        showShowdown();
    }


    private void showShowdown() {
        ViewHelper.setTranslationY(mRootView, 0);
        mEffect.effect(mParentVG, mBg);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        if (mBg.getParent() != null)
            mParentVG.removeView(mBg);

        mParentVG.addView(mBg, lp);
        ViewHelper.setAlpha(mBg, 0);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mBg, "alpha", 0, 1);
        objectAnimator.setDuration(400);
        objectAnimator.start();
    }

    private void dismissShowdown() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mBg, "alpha", 1, 0);
        objectAnimator.setDuration(400);
        objectAnimator.start();
        objectAnimator.addListener(new SimpleAnimationListener() {

            @Override
            public void onAnimationEnd(Animator animation) {

                mParentVG.removeView(mBg);
            }


        });
    }

    protected void dismiss() {
        if (getStatus() == SweetSheet.Status.DISMISS)
            return;
        mBg.setClickable(false);
        dismissShowdown();

        ObjectAnimator translationOut = ObjectAnimator.ofFloat(mRootView, "translationY", 0, mRootView.getHeight());
        translationOut.setDuration(600);
        translationOut.setInterpolator(new DecelerateInterpolator());
        translationOut.addListener(new SimpleAnimationListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mStatus = SweetSheet.Status.DISMISSING;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mStatus = SweetSheet.Status.DISMISS;
                mParentVG.removeView(mRootView);
            }

        });
        translationOut.start();
    }


    void setBackgroundEffect(Effect effect) {
        mEffect = effect;
    }

    void setOnMenuItemClickListener(SweetSheet.OnMenuItemClickListener onItemClickListener) {
        mOnMenuItemClickListener = onItemClickListener;
    }

    void delayedDismiss() {
        mParentVG.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, 300);
    }

    protected SweetSheet.Status getStatus() {
        return mStatus;
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }

    void setBackgroundClickEnable(boolean isBgClickEnable) {
        mIsBgClickEnable = isBgClickEnable;
    }
}

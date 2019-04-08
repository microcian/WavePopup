package com.wavepopuplib.sweetpick;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.wavepopuplib.effects.Effect;
import com.wavepopuplib.effects.NoneEffect;
import com.wavepopuplib.listener.ICheckChanged;

import java.util.List;

public class SweetSheet {

    private static final String Tag = SweetSheet.class.getSimpleName();

    private ViewGroup mParentVG;
    private Delegate mDelegate;
    private Effect mEffect = new NoneEffect();
    private OnMenuItemClickListener mOnMenuItemClickListener;
    private boolean mIsBgClickEnable = true;

    public enum Type {
        RecyclerView, Viewpager, Custom
    }

    public SweetSheet(RelativeLayout parentVG) {
        mParentVG = parentVG;
    }

    public SweetSheet(FrameLayout parentVG) {
        mParentVG = parentVG;
    }

    public SweetSheet(ViewGroup parentVG) {

//        if (parentVG instanceof ConstraintLayout || parentVG instanceof RelativeLayout) {
//
//        } else {
//            throw new IllegalStateException("ViewGroup  must ConstraintLayout or  RelativeLayout.");
//        }
        mParentVG = parentVG;
    }

    public void setDelegate(Delegate delegate) {
        mDelegate = delegate;
        mDelegate.init(mParentVG);
        setup();
    }

    public Delegate getDelegate() {
        return mDelegate;
    }

    private void setup() {
        if (mOnMenuItemClickListener != null)
            mDelegate.setOnMenuItemClickListener(mOnMenuItemClickListener);
        if (mMenuEntities != null)
            mDelegate.setMenuList(mMenuEntities);
        mDelegate.setBackgroundEffect(mEffect);
        mDelegate.setBackgroundClickEnable(mIsBgClickEnable);
    }

    public void setBackgroundClickEnable(boolean isBgClickEnable) {
        if (mDelegate != null)
            mDelegate.setBackgroundClickEnable(isBgClickEnable);
        else
            mIsBgClickEnable = isBgClickEnable;
    }

    public void setBackgroundEffect(Effect effect) {
        if (mDelegate != null)
            mDelegate.setBackgroundEffect(effect);
        else
            mEffect = effect;
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onItemClickListener) {
        if (mDelegate != null)
            mDelegate.setOnMenuItemClickListener(onItemClickListener);
        else
            mOnMenuItemClickListener = onItemClickListener;
    }

    public void show() {
        if (mDelegate != null)
            mDelegate.show();
        else
            Log.e(Tag, "you must setDelegate before");
    }

    public void dismiss() {
        if (mDelegate != null)
            mDelegate.dismiss();
        else
            Log.e(Tag, "you must setDelegate before");
    }

    public void toggle() {
        if (mDelegate != null)
            mDelegate.toggle();
        else
            Log.e(Tag, "you must setDelegate before");
    }

    public boolean isShow() {
        if (mDelegate == null)
            return false;
        return mDelegate.getStatus() == Status.SHOW || mDelegate.getStatus() == Status.SHOWING;
    }

    public void setCheckedChangeListener(ICheckChanged iCheckChanged, int viewId) {
        if (mDelegate != null)
            mDelegate.setCheckedChangeListener(iCheckChanged, viewId);
    }

    public void setIsMultipleSelection(boolean isMultiple) {
        if (mDelegate != null)
            mDelegate.setIsMultipleSelection(isMultiple);
    }

    public void setStoredData(String storedData) {
        if (mDelegate != null)
            mDelegate.setStoredData(storedData);
    }

    private List<String> mMenuEntities;

    public void setMenuList(List<String> menuEntities) {
        if (mDelegate != null)
            mDelegate.setMenuList(menuEntities);
        else
            mMenuEntities = menuEntities;
    }

    protected enum Status {
        SHOW, SHOWING,
        DISMISS, DISMISSING
    }

    public interface OnMenuItemClickListener {
        boolean onItemClick(int position, String menuEntity);
    }
}

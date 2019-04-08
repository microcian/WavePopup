package com.wavepopuplib.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.TextView;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.wavepopuplib.R;
import com.wavepopuplib.listener.ICheckChanged;
import com.wavepopuplib.sweetpick.SweetSheet;

import java.util.Arrays;
import java.util.List;


public class MenuRVAdapter extends RecyclerView.Adapter<MenuRVAdapter.MenuVH> {

    private List<String> mDataList;
    private boolean mIsAnimation;
    private int mItemLayoutId;
    private boolean isMultiple;
    private String storedData;

    public MenuRVAdapter(List<String> dataLis, SweetSheet.Type type) {

        mDataList = dataLis;
        if (type == SweetSheet.Type.RecyclerView)
            mItemLayoutId = R.layout.item_horizon_rv;
        else
            mItemLayoutId = R.layout.item_vertical_rv;
    }

    public void setIsMultipleSelection(boolean isMultiple) {
        this.isMultiple = isMultiple;
    }

    public void setStoredData(String storedData) {
        this.storedData = storedData;
    }

    private SweetSheet.OnMenuItemClickListener mOnItemClickListener;
    private ICheckChanged iCheckChanged;
    private int viewId;

    public void setCheckChangedListener(ICheckChanged iCheckChanged, int viewId) {
        this.iCheckChanged = iCheckChanged;
        this.viewId = viewId;
    }

    @NonNull
    @Override
    public MenuVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(mItemLayoutId, null, false);
        return new MenuVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MenuVH menuVH, @SuppressLint("RecyclerView") final int position) {


        menuVH.cbItem.setTag(menuVH.getAdapterPosition());
        final String menuEntity = mDataList.get(position);

        if (!TextUtils.isEmpty(storedData)) {
            String SPLITTER = ",";
            String[] selectedChips = storedData.split(SPLITTER);
            List<String> wordList = Arrays.asList(selectedChips);
            if (wordList != null && wordList.size() > 0) {
                if (wordList.contains(menuEntity))
                    menuVH.cbItem.setChecked(true);
                else
                    menuVH.cbItem.setChecked(false);
            }
        }
        menuVH.textViewTitle.setText(menuEntity);
        if (mIsAnimation)
            animation(menuVH);

        menuVH.cbItem.setVisibility(isMultiple ? View.VISIBLE : View.GONE);
        if (null != iCheckChanged)
            menuVH.cbItem.setOnClickListener(v -> iCheckChanged.checkedChanged(menuVH.cbItem.isChecked(), position, menuEntity, viewId));
        if (null != mOnItemClickListener && !isMultiple)
            menuVH.itemView.setOnClickListener(v -> mOnItemClickListener.onItemClick(position, menuEntity));
    }

    private void animation(MenuVH menuVH) {

        ViewHelper.setAlpha(menuVH.itemView, 0);
        ViewHelper.setTranslationY(menuVH.itemView, 300);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(menuVH.itemView, "translationY", 500, 0);
        translationY.setDuration(300);
        translationY.setInterpolator(new OvershootInterpolator(1.6f));
        ObjectAnimator alphaIn = ObjectAnimator.ofFloat(menuVH.itemView, "alpha", 0, 1);
        alphaIn.setDuration(100);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translationY, alphaIn);
        animatorSet.setStartDelay(30 * menuVH.getAdapterPosition());
        animatorSet.start();
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void notifyAnimation() {
        mIsAnimation = true;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {

    }

    static class MenuVH extends RecyclerView.ViewHolder {

        CheckBox cbItem;
        TextView textViewTitle;

        MenuVH(View itemView) {
            super(itemView);

            cbItem = itemView.findViewById(R.id.cb_item);
            textViewTitle = itemView.findViewById(R.id.nameTV);
        }
    }

    public void setOnItemClickListener(SweetSheet.OnMenuItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}

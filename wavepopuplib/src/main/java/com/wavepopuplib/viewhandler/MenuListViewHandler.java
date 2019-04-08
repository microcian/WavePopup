package com.wavepopuplib.viewhandler;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.wavepopuplib.R;
import com.wavepopuplib.adapter.MenuRVAdapter;
import com.wavepopuplib.sweetpick.SweetSheet;

import java.util.List;

public class MenuListViewHandler {

    private List<String> mMenuEntities;
    private int mIndex;
    private int mRvVisibility = View.VISIBLE;
    private OnFragmentInteractionListener mOnFragmentInteractionListener;
    private RecyclerView mRV;
    private MenuRVAdapter mMenuRVAdapter;
    private View mView;
    private int mNumColumns;

    public static MenuListViewHandler getInstant(int index, int numColums, List<String> menuEntities) {
        MenuListViewHandler menuListViewHandler = new MenuListViewHandler();
        menuListViewHandler.mMenuEntities = menuEntities;
        menuListViewHandler.mIndex = index;
        menuListViewHandler.mNumColumns = numColums;
        return menuListViewHandler;
    }

    public void setOnMenuItemClickListener(OnFragmentInteractionListener onFragmentInteractionListener) {
        mOnFragmentInteractionListener = onFragmentInteractionListener;
    }

    public View onCreateView(ViewGroup container) {
        if (mView == null) {
            mView = LayoutInflater.from(container.getContext()).inflate(R.layout.layout_grid_menu, container, false);
            onViewCreated(mView);
        }
        return mView;
    }

    private void onViewCreated(View view) {
        if (mMenuEntities == null || mMenuEntities.size() == 0)
            return;

        mRV = view.findViewById(R.id.rv);
        mRV.setLayoutManager(new GridLayoutManager(view.getContext(), mNumColumns));
        mRV.setHasFixedSize(true);
        mMenuRVAdapter = new MenuRVAdapter(mMenuEntities, SweetSheet.Type.Viewpager);
        mMenuRVAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (mOnFragmentInteractionListener != null)
                    mOnFragmentInteractionListener.onFragmentInteraction(mIndex * 6 + position);
            }
        });
        mRV.setAdapter(mMenuRVAdapter);
        mRV.setVisibility(mRvVisibility);
    }


    public void animationOnStart() {
        if (mRV != null)
            mRV.setVisibility(View.GONE);
        else
            mRvVisibility = View.GONE;
    }

    public void notifyAnimation() {
        if (mRV != null) {
            mRV.setVisibility(View.VISIBLE);
            mRvVisibility = View.VISIBLE;
            mMenuRVAdapter.notifyAnimation();
        } else
            mRvVisibility = View.VISIBLE;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(int index);
    }
}

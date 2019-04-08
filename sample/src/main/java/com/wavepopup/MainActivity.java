package com.wavepopup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.wavepopuplib.effects.DimEffect;
import com.wavepopuplib.sweetpick.RecyclerViewDelegate;
import com.wavepopuplib.sweetpick.SweetSheet;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {

    RelativeLayout rlParentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rlParentView = findViewById(R.id.rlParentView);

        ArrayList<String> listData = new ArrayList<>();
        listData.add("Quick");
        listData.add("Brown");
        listData.add("Fox");
        listData.add("Jumps");
        listData.add("Over");
        listData.add("The");
        listData.add("Lazy");
        listData.add("Dog");

        SweetSheet mSweetSheet = new SweetSheet(rlParentView);
        mSweetSheet.setDelegate(new RecyclerViewDelegate(true));
        mSweetSheet.setMenuList(listData);
        mSweetSheet.setIsMultipleSelection(true);
//        mSweetSheet.setStoredData(strData);
        mSweetSheet.setBackgroundEffect(new DimEffect(1));
//        mSweetSheet.setCheckedChangeListener(this, viewId);
//        mSweetSheet.setOnMenuItemClickListener(new SweetSheet.OnMenuItemClickListener() {
//            @Override
//            public boolean onItemClick(int position, String menuEntity1) {
//
//                if (viewId == R.id.chip_area) {
//                    String strArea = chipArea.getText() + menuEntity1 + ",";
//                    storeArea(strArea);
//                    chipArea.createChips(strArea);
//                }
//                return false;
//            }
//        });
        mSweetSheet.toggle();
    }
}

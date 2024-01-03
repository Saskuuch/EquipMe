package com.example.equipme;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

//Adapter for ViewPager in ShowPiece
public class TabDapter extends FragmentStateAdapter {

    public TabDapter(@NonNull FragmentManager manager, @NonNull Lifecycle cycle){
        super(manager, cycle);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0:{
                PieceInfoFrag frag1 = new PieceInfoFrag();
                return frag1;
            }
            case 1:{
                PieceHistoryFrag frag2 = new PieceHistoryFrag();
                return frag2;
            }
            case 2:{
                PartFrag frag3 = new PartFrag();
                return frag3;
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

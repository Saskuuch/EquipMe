package com.example.equipme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class ShowPiece extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_piece);

        //View welds
        TabLayout tabLayout = findViewById(R.id.tabView);
        ViewPager2 pager = findViewById(R.id.pager);

        //Creates tabs for tablayout
        TabLayout.Tab tab1 = tabLayout.newTab();
        tab1.setText("Piece Info");
        tabLayout.addTab(tab1);

        TabLayout.Tab tab2 = tabLayout.newTab();
        tab2.setText("Maintenance History");
        tabLayout.addTab(tab2);

        TabLayout.Tab tab3 = tabLayout.newTab();
        tab3.setText("Common Parts");
        tabLayout.addTab(tab3);

        //Adds tabs to pager
        TabDapter tabDapter = new TabDapter(getSupportFragmentManager(), getLifecycle());
        pager.setAdapter(tabDapter);

        //Listeners for ViewPager and TabLayout
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position){
                super.onPageSelected(position);
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }

            @Override
            public void onPageScrollStateChanged(int state){
                super.onPageScrollStateChanged(state);
            }
        });
    }
}
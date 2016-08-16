package com.teamj.joseguaman.hitchusv2.adapters;

/**
 * Created by Jose Guaman on 14/08/2016.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.teamj.joseguaman.hitchusv2.fragments.FragmentTabPrincipal;
import com.teamj.joseguaman.hitchusv2.fragments.FragmentTabMessages;
import com.teamj.joseguaman.hitchusv2.fragments.FragmentTabSettings;


public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                FragmentTabPrincipal tab1 = new FragmentTabPrincipal();
                return tab1;
            case 1:
                FragmentTabMessages tab2 = new FragmentTabMessages();
                return tab2;
            case 2:
                FragmentTabSettings tab3 = new FragmentTabSettings();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

package com.teamj.joseguaman.hitchusv2.fragments;

/**
 * Created by Jose Guaman on 14/08/2016.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamj.joseguaman.hitchusv2.R;

public class FragmentTabMessages extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_fragment_messages, container, false);
    }
}

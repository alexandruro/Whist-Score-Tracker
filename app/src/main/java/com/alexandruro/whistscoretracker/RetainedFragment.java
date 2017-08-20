package com.alexandruro.whistscoretracker;

import android.app.Fragment;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Fragment retained during configuration changes (e.g. orientation)
 */
public class RetainedFragment extends Fragment {

    // data object we want to retain
    private ArrayList<PlayerRecord> data;

    // this method is only called once for this fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);
    }

    public void setData(ArrayList<PlayerRecord> data) {
        this.data = data;
    }

    public ArrayList<PlayerRecord> getData() {
        return data;
    }
}

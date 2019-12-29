package com.example.balewater.ui.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.balewater.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailCastleFragment extends Fragment {


    public DetailCastleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_castle, container, false);
    }

}

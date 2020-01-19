package com.example.balewater.HomeEmpleado;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.balewater.R;
import com.example.balewater.ViewModel.ClientViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class UltimasReservasFragment extends Fragment {

    ClientViewModel clientViewModel;

    TextView nombreTextView;

    public UltimasReservasFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ultimas_reservas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nombreTextView = view.findViewById(R.id.reserver_title);


        clientViewModel = ViewModelProviders.of(requireActivity()).get(ClientViewModel.class);

        clientViewModel.getNom().observe(requireActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                nombreTextView.setText(s);
            }
        });


    }
}

package com.example.balewater.HomeEmpleado;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.balewater.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeEmpleadoFragment extends Fragment {



    public HomeEmpleadoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_empleado, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Button unoEditText;
        final Button dosEditTexT;
        final Button tresEditText;

        unoEditText = view.findViewById(R.id.uno);
        dosEditTexT = view.findViewById(R.id.dos);
        tresEditText = view.findViewById(R.id.tres);

        unoEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.ultimasReservasFragment);

            }
        });
    }
}

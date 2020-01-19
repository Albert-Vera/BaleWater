package com.example.balewater.ui.home;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.balewater.R;
import com.example.balewater.ViewModel.ClientViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReservarCastleFragment extends Fragment {

    ClientViewModel clientViewModel ;


    public ReservarCastleFragment() {
        // Required empty public constructor


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reservar_castle, container, false);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText nombreClientEditText = view.findViewById(R.id.nombreCliente);
        final TextView prueba = view.findViewById(R.id.make_reserva);



        clientViewModel = ViewModelProviders.of(requireActivity()).get(ClientViewModel.class);
        // TODO solo es una prueba para ver si consigo escribir y leer valores
        clientViewModel.nom.postValue(nombreClientEditText.getText().toString());


      //  prueba.setText( clientViewModel.getNom());




        view.findViewById(R.id.enviarReserva).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(view).navigate(R.id.homeEmpleadoFragment);


            }
        });


    }
}


package com.example.balewater.LoginRegister;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.balewater.Model.Usuario;
import com.example.balewater.R;
import com.example.balewater.ViewModel.AutenticationViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrarseFragment extends Fragment {

    private AutenticationViewModel autenticationViewModel;
    NavController navController;
    //MainViewModel mainViewModel;
    TextView noRegistrar;
    private Button registrarButton;
    Usuario usuario;
    EditText emailEditText, passwordEditText;

    public RegistrarseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registrarse, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //navController = Navigation.findNavController(view);

//        mainViewModel = ViewModelProviders.of(requireActivity()).get(MainViewModel.class);
        emailEditText = view.findViewById(R.id.username_register);
        passwordEditText = view.findViewById(R.id.password_register);
        registrarButton = view.findViewById(R.id.register_button);


        autenticationViewModel = ViewModelProviders.of(requireActivity()).get(AutenticationViewModel.class);

        // Poner el estado del registro al estado INICIAR,
        // (por si se habia quedado en COMPLETADO o NOMBRE_NO_DISPONIBLE)
        autenticationViewModel.iniciarRegistro();

        registrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                autenticationViewModel.crearCuentaEIniciarSesion(emailEditText.getText().toString(), passwordEditText.getText().toString());
            }
        });

        autenticationViewModel.getEstadoDelRegistro().observe(getViewLifecycleOwner(), new Observer<AutenticationViewModel.EstadoDelRegistro>() {
            @Override
            public void onChanged(AutenticationViewModel.EstadoDelRegistro estadoDelRegistro) {
                switch (estadoDelRegistro){
                    case NOMBRE_NO_DISPONIBLE:
                        Toast.makeText(getContext(), "NOMBRE DE USUARIO NO DISPONIBLE", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        autenticationViewModel.estadoDeLaAutentication.observe(getViewLifecycleOwner(), new Observer<AutenticationViewModel.EstadoDeLaAutentication>() {
            @Override
            public void onChanged(AutenticationViewModel.EstadoDeLaAutentication estadoDeLaAutentication) {
                switch (estadoDeLaAutentication){
                    case AUTENTIFICADO:
                        Log.e("ABCD", " toy aqui Usuario toy en Autentificado 231.. " );
                        Navigation.findNavController(view).popBackStack(R.id.nav_home, false);// modificat destino
                        break;
                }
            }
        });

    }

}

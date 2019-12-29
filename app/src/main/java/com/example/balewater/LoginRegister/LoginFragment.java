package com.example.balewater.LoginRegister;


import android.app.AlertDialog;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.balewater.R;
import com.example.balewater.ViewModel.AutenticationViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    private AutenticationViewModel autenticationViewModel;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        autenticationViewModel = ViewModelProviders.of(requireActivity()).get(AutenticationViewModel.class);


        final EditText usernameEditText = view.findViewById(R.id.username);
        final EditText passwordEditText = view.findViewById(R.id.password);
        final Button loginButton = view.findViewById(R.id.login_button);
        final TextView irAlRegistroTextView = view.findViewById(R.id.ir_registro);


        irAlRegistroTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.registrarseFragment);
            }
        });

        // Si FormState es isDataValid mostrara en pantalla error o no mostrara
        autenticationViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });


        // Detecta mentres escrius text
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e("ABCD", " lalalo " );

                autenticationViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);

        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    autenticationViewModel.iniciarSesion(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autenticationViewModel.iniciarSesion(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });

        autenticationViewModel.estadoDeLaAutentication.observe(getViewLifecycleOwner(),new Observer<AutenticationViewModel.EstadoDeLaAutentication>() {
            @Override
            public void onChanged(AutenticationViewModel.EstadoDeLaAutentication estadoDeLaAutenticacion) {
                switch (estadoDeLaAutenticacion){
                    case AUTENTIFICADO:
                        Log.e("ABCD", " toy aqui Usuario Ade aqui me voy a.... " );
                        Navigation.findNavController(view).popBackStack(); //regresa patras

                        new AlertDialog.Builder(requireContext()).setTitle("\t\t           LOGIN IN      ")
                                .setMessage("\t      ")
                                .setMessage("\t                Estas dintre               ")
                                .setCancelable(true)
                                .create()
                                .show();
                        autenticationViewModel.estadoDeLaAutentication.postValue((AutenticationViewModel.EstadoDeLaAutentication.YA_AUTENTIFICADO));

                        break;

                    // YA AUTENTIFICADO es para si le das por segunda vez a LOGIN
                    // te detecta como YA ATENTIFICADO y entonces pues hace LOGIN OUT
                    case YA_AUTENTIFICADO:
                        autenticationViewModel.cerrarSesion();

                        new AlertDialog.Builder(requireContext()).setTitle("\t\t           LOGIN OUT      ")
                                .setMessage("\t      ")
                                .setMessage("\t                Estas Fora               ")
                                .setCancelable(true)
                                .create()
                                .show();
                        break;

                    case AUTENTIFICACION_INVALIDA:
                        Toast.makeText(getContext(), "CREDENCIALES NO VALIDAS", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });


        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        Log.e("ABCD", " toy aqui Usuario toy en getonBackPressed en Autentificacion " );

                        Navigation.findNavController(view).popBackStack();// modificat destino
                    }
                });
    }
}

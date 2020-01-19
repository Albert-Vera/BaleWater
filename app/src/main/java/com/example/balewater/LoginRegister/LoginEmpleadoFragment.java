package com.example.balewater.LoginRegister;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.balewater.R;
import com.example.balewater.ViewModel.AutenticationViewModel;
import com.example.balewater.ViewModel.EmpleadoViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginEmpleadoFragment extends Fragment {

    private AutenticationViewModel autenticationViewModel;


    public LoginEmpleadoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_empleado, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        autenticationViewModel = ViewModelProviders.of(requireActivity()).get(AutenticationViewModel.class);

        final EditText userStaffEditText = view.findViewById(R.id.user_staff);
        final EditText passwordStaffEditText = view.findViewById(R.id.password_staff);
        final Button loginButtonStaff = view.findViewById(R.id.login_button_staff);

//        autenticationViewModel.getLoginEmpleadoMutableData().observe(getViewLifecycleOwner(), new Observer<AutenticationViewModel.LoginEmpleado>() {
//            @Override
//            public void onChanged(AutenticationViewModel.LoginEmpleado loginEmpleadoMutableData) {
//                final EditText cosas;
//
//                switch (loginEmpleadoMutableData){
//                    case LOGIN_CLIENTE:
//                        userStaffEditText.setVisibility(EditText.GONE);
//                        userEmailEditText.setVisibility(EditText.VISIBLE);
//                        irAlRegistroTextView.setVisibility(TextView.VISIBLE);
//                        //userEmailEditText = view.findViewById(R.id.user_email);
//                        break;
//                    case LOGIN_EMPLEADO:
//                        userEmailEditText.setVisibility(EditText.GONE);
//                        irAlRegistroTextView.setVisibility(TextView.GONE);
//                        userStaffEditText.setVisibility(EditText.VISIBLE);
//                        //userEmailEditText = view.findViewById(R.id.user_staff);
//                        //TODO navigate provisonal en aunsencia de autentificación empleado
//                        // Navigation.findNavController(view).navigate(R.id.activityMainEmpleado);
//
//
//                        break;
//                }
//            }
//        });


        // Si FormState es isDataValid mostrara en pantalla error o no mostrara
        autenticationViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButtonStaff.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUserEmailError() != null) {
                    userStaffEditText.setError(getString(loginFormState.getUserEmailError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordStaffEditText.setError(getString(loginFormState.getPasswordError()));
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
                autenticationViewModel.loginDataChanged(userStaffEditText.getText().toString(),
                        passwordStaffEditText.getText().toString());
            }
        };
        final EmpleadoViewModel empleadoViewModel;
        empleadoViewModel = ViewModelProviders.of(requireActivity()).get(EmpleadoViewModel.class);


        empleadoViewModel.estoyLoginStaff.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    Log.e("ABCD", " cagate Maria " );

                }else {
                    Log.e("ABCD", " esta en false  Maria " );

                }
            }

        });


        userStaffEditText.addTextChangedListener(afterTextChangedListener);
        passwordStaffEditText.addTextChangedListener(afterTextChangedListener);


        passwordStaffEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    autenticationViewModel.iniciarSesion(userStaffEditText.getText().toString(),
                            passwordStaffEditText.getText().toString());
                }
                return false;
            }
        });
        loginButtonStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autenticationViewModel.iniciarSesion(userStaffEditText.getText().toString(),
                        passwordStaffEditText.getText().toString());
            }
        });

        autenticationViewModel.estadoDeLaAutentication.observe(getViewLifecycleOwner(),new Observer<AutenticationViewModel.EstadoDeLaAutentication>() {
            @Override
            public void onChanged(AutenticationViewModel.EstadoDeLaAutentication estadoDeLaAutenticacion) {
                switch (estadoDeLaAutenticacion){
                    case AUTENTIFICADO:

                        Navigation.findNavController(view).navigate(R.id.homeEmpleadoFragment);

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

                        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

                        builder.setTitle("\t\t           LOGIN OUT      ")
                                .setMessage("\t      ")
                                .setMessage("\tAre you sure?")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        autenticationViewModel.cerrarSesion();
                                        Navigation.findNavController(view).navigate(R.id.nav_home);
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Navigation.findNavController(view).navigate(R.id.homeEmpleadoFragment);
                                    }
                                })
                                .create()
                                .show();
                        break;

                    case AUTENTIFICACION_INVALIDA:
                        Toast.makeText(getContext(), "CREDENCIALES NO VALIDAS", Toast.LENGTH_SHORT).show();
                        break;
                        default: autenticationViewModel.estadoDeLaAutentication.postValue(AutenticationViewModel.EstadoDeLaAutentication.AUTENTIFICACION_INVALIDA);
                }
            }
        });


        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        Log.e("ABCD", " toy aqui Usuario toy en getonBackPressed en Autentificacion " );

                        Navigation.findNavController(view).popBackStack(R.id.homeEmpleadoFragment, false);// modificat destino
                    }
                });
    }


}

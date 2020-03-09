package com.example.balewater.ui;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.balewater.MainActivity;
import com.example.balewater.MyFragment;
import com.example.balewater.R;
import com.example.balewater.viewmodel.AppViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignInFragment extends MyFragment {

    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN_WITH_GOOGLE = 12345;
    private EditText userEmailEditText, passwwordEditText;
    private Button loginButton;
    private TextView irARegistrarse;
    SignInButton signInButton;
    ProgressBar signInProgressBar;
    AppViewModel appViewModel;

    public SignInFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        appViewModel = ViewModelProviders.of(requireActivity()).get(AppViewModel.class);

        signInButton = view.findViewById(R.id.signInButton);
        signInProgressBar = view.findViewById(R.id.signInProgressBar);
        userEmailEditText = view.findViewById(R.id.user_email);
        passwwordEditText = view.findViewById(R.id.password_login);
        loginButton = view.findViewById(R.id.login_button);
        irARegistrarse = view.findViewById(R.id.ir_registro);
        //appViewModel = ViewModelProviders.of(requireActivity()).get(AppViewModel.class);

        signInProgressBar.setVisibility(View.GONE);

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build());

        //TODO esto desconectado es para que no detecte si ya estas Logeado con google en Firebase
        firebaseAuthWithGoogle(GoogleSignIn.getLastSignedInAccount(requireContext()));

        view.findViewById(R.id.signInButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSignIn();
            }
        });

//        appViewModel.estadoDeLaAutentificacion.observe(getViewLifecycleOwner(), new Observer<AppViewModel.EstadoDeLaAutentificacion>() {
//            @Override
//            public void onChanged(AppViewModel.EstadoDeLaAutentificacion estadoDeLaAutentificacion) {
//                Log.e ("ABCD", "dentro de esta cosa" + estadoDeLaAutentificacion.toString());
//
//                switch (estadoDeLaAutentificacion){
//                    case AUTENTIFICADO:
//
//
//                        break;
//                }
//            }
//        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(view);
            }
        });

        irARegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.registerFragment);
            }
        });
    }

    private void signIn(final View view) {

        if (!validateForm()) {
            return;
        }


        auth.signInWithEmailAndPassword(userEmailEditText.getText().toString(), passwwordEditText.getText().toString())
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            updateUI(auth.getCurrentUser());
                            Log.e("ABCD", "AQUI PONGO ESTADOAUTENTIFICACION A AUTENTIFICADO");

                           appViewModel.estadoDeLaAutentificacion.postValue(AppViewModel.EstadoDeLaAutentificacion.AUTENTIFICADO);
                        } else {
                            Log.e("ABCD", "signInWithEmail:failure", task.getException());
                        }
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;
        String email = userEmailEditText.getText().toString();

        if (TextUtils.isEmpty(email)) {
            userEmailEditText.setError("Required.");
            valid = false;
        } else {
            userEmailEditText.setError(null);
        }
        String password = passwwordEditText.getText().toString();

        if (TextUtils.isEmpty(password)) {
            passwwordEditText.setError("Required.");
            valid = false;
        } else {
            passwwordEditText.setError(null);
        }

        return valid;
    }

    private void updateUI(FirebaseUser firebaseUser) {
        if(firebaseUser != null){
            //appViewModel.estadoDeLaAutentificacion.postValue(AppViewModel.EstadoDeLaAutentificacion.AUTENTIFICADO);

            navController.navigate(R.id.homeFragment);
        }
    }

    private void googleSignIn() {
        startActivityForResult(mGoogleSignInClient.getSignInIntent(), RC_SIGN_IN_WITH_GOOGLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN_WITH_GOOGLE) {
            try {
                firebaseAuthWithGoogle(GoogleSignIn.getSignedInAccountFromIntent(data).getResult(ApiException.class));
            } catch (ApiException e) {
                Log.e("ABCD", "signInResult:failed code=" + e.getStatusCode());
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        if(acct == null) return;

        signInProgressBar.setVisibility(View.VISIBLE);
        signInButton.setVisibility(View.GONE);

        auth.signInWithCredential(GoogleAuthProvider.getCredential(acct.getIdToken(), null))
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.e("ABCD", "signInWithCredential:success");
                            updateUI(auth.getCurrentUser());
                        } else {
                            Log.e("ABCD", "signInWithCredential:failure", task.getException());
                            signInProgressBar.setVisibility(View.GONE);
                            signInButton.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }
}

package com.example.balewater.ui;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.balewater.MainActivity;
import com.example.balewater.MyFragment;
import com.example.balewater.R;
import com.example.balewater.model.Usuario;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;


/**
 * App Solo para probar el FireBase
 * Subir datos reserva del cliente y mostrarlo en listado de clientes del menu
 * Hacer login y que muestre tu foto de perfil y datos  en Main Menu.
 * Hacer login con tu cuenta de Google y que se muestre foto de perfil y datos
 *
 *
 * A la hora de hacer register tuve problemas.
 * Al registrarse elijes una foto y se sube a Database de Firebase
 * Per al intentar mostrarse en main Menu la foto de perfil y los datos,
 * se muestran los datos del usuario pero la foto no se muestra si no Reinicias la App
 * debido a que, como la foto tarda varios segundos en subir, pues la
 * MainActivity hace varios intentos para mostrar la foto pero como aún no ha subido
 * acaba por no mostrar la foto.
 *
 *
 */

public class RegisterFragment extends MyFragment implements View.OnClickListener {

    MainActivity mainActivity;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button verifyEmailButton;
    private Button createAccountButton;
    private Button cameraImageButton;
    private boolean permissionsAcepted = false;
    private static final int RC_PERMISSIONS = 1212;
    private ImageView imag;
    StorageReference storage;
    ProgressDialog progressDialog;
    Uri path;

    public RegisterFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        storage = FirebaseStorage.getInstance().getReference();
        progressDialog = new ProgressDialog(requireContext());

        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        verifyEmailButton = view.findViewById(R.id.verifyEmailButton);
        createAccountButton = view.findViewById(R.id.createAccountButton);
        cameraImageButton = view.findViewById(R.id.anadirPhoto);
        imag = view.findViewById(R.id.imageproba);

        verifyEmailButton.setOnClickListener(this);
        createAccountButton.setOnClickListener(this);
        cameraImageButton.setOnClickListener(this);

        updateUI(auth.getCurrentUser());
    }

    public void previsualizarFoto(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent,"Seleccione la aplicacion"),10);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            path = data.getData();
            Log.e("ABCD", "estableciendo path:: " + path);
            Glide.with(this).load(path).into(imag);
        }
    }

    private void subirFoto(final String email, final String password) {
        if(path == null) return;

        user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseStorage.getInstance().getReference("fotoUser/"+user.getEmail().toString()+"_Image.jpg")
                .putFile(path)
                .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        return task.getResult().getStorage().getDownloadUrl();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        db.collection("users")
                                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .set(new Usuario(FirebaseAuth.getInstance().getCurrentUser().getEmail(),uri.toString())).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.e("ABCD", "Subida a database users correcta.............");
                               // cabeceraDelMenu();
                               // mainActivity.recreate;
                                user.reload();

                                navController.navigate(R.id.homeFragment);
                            }
                        });
                    }
                });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.anadirPhoto:

                if (!validateForm()){
                    Toast toast1 = Toast.makeText(requireActivity(), "Primero inserta todos los datos !", Toast.LENGTH_SHORT);
                    toast1.setGravity(Gravity.CENTER | Gravity.LEFT, 250, 0);
                    toast1.show();
                    return;
                }else {
                    previsualizarFoto();
                    Log.e("ABCD", "foto cargada........................");
                }
                break;
            case R.id.createAccountButton:
                createAccount(emailEditText.getText().toString(), passwordEditText.getText().toString());
                //navController.navigate(R.id.productListFragment);
                break;
            case R.id.verifyEmailButton:
                sendEmailVerification();
                break;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RC_PERMISSIONS) {
            permissionsAcepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
    }
    private void updateUI(FirebaseUser firebaseUser) {
        if(firebaseUser != null){
            Log.w("ABCD", "De aqui me piro a fragmet home");
            navController.navigate(R.id.homeFragment);
        }
    }
    private void createAccount(final String email, final String password) {
        if (!validateForm()) {
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            updateUI(auth.getCurrentUser());
                             Log.e("ABCD", "VOY A SUBIR FOTO");

                            subirFoto(email, password);

                            Log.e("ABCD", "create GUAY ", task.getException());

                            signIn(email, password);
                        } else {
                            Log.e("ABCD", "createUserWithEmail:failure", task.getException());
                        }
                    }
                });
    }

    public void signIn(String email, String password) {
        if (!validateForm()) {
            return;
        }

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            updateUI(auth.getCurrentUser());
                        } else {
                            Log.w("ABCD", "signInWithEmail:failure", task.getException());
                        }
                    }
                });
    }

    private void sendEmailVerification() {
        verifyEmailButton.setEnabled(false);

        final FirebaseUser user = auth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        verifyEmailButton.setEnabled(true);

                        if (task.isSuccessful()) {
                            Toast.makeText(requireActivity(), "Verification email sent to " + user.getEmail(), Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("ABCD", "sendEmailVerification", task.getException());
                            Toast.makeText(requireActivity(), "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = emailEditText.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Required.");
            valid = false;
        }else if(!email.contains("@")) {
            emailEditText.setError("Email no valido");
            valid = false;
        } else {
            emailEditText.setError(null);
        }

        String password = passwordEditText.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Required.");
            valid = false;
        }else if (password.trim().length() < 6){
            passwordEditText.setError("Required min 6 char.");
            valid = false;
        }else{
            passwordEditText.setError(null);
        }

        return valid;
    }


}





//    private void usuarioToFirebase(ImageView imag, EditText emailEditText){
//
//        String fecha = new ReservarCastleFragment().obtenerFecha();
//        String email = emailEditText.getText().toString();
//
//        Usuario usuario = new Usuario();
//
//        usuario.setEmail(email);
//        usuario.setFechaRegistro(fecha);
//
//        db.collection("users").add(usuario).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentReference> task) {
//                if(task.isSuccessful()){
//                    Log.e("ABCD", "Reserva guardada correctamente");
//                } else {
//                    Log.e("ABCD", "Un problema al guardar la reserva ");
//
//                }
//            }
//        });
//
//
//    }
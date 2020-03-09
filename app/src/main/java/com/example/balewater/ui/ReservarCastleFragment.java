package com.example.balewater.ui;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.balewater.MyFragment;
import com.example.balewater.R;
import com.example.balewater.model.Reserva;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReservarCastleFragment extends MyFragment {

    EditText nombreEditText;
    EditText apellidosEditText;
    EditText emailEditText;
    EditText telEditText;
    DatePicker fechaEditText;
    String fecha;
    String castleSeleccion;
    int dias;
    Button enviarReserva;
    private static final String CERO = "0";
    private static final String BARRA = "/";
    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    public ReservarCastleFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reservar_castle, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        enviarReserva = view.findViewById(R.id.enviarReserva);

        enviarReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nombreEditText = view.findViewById(R.id.nombreCliente);
                apellidosEditText = view.findViewById(R.id.apellidosClienteReserva);
                emailEditText = view.findViewById(R.id.emailreserva_castle);
                telEditText = view.findViewById(R.id.telefon_edittextReserva);

                if (!validateForm()){
                    Toast toast1 =
                            Toast.makeText(requireActivity(),
                                    "Faltan datos por introducir !", Toast.LENGTH_SHORT);
                    toast1.setGravity(Gravity.CENTER | Gravity.LEFT, 250, 0);
                    toast1.show();
                    return;
                }
                appViewModel.castleSeleccionado.observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(String nombre) {
                        castleSeleccion = nombre;
                    }
                });

                appViewModel.idSeleccionado.observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(String id) {
                        reservarCastle(id, castleSeleccion, nombreEditText.getText().toString(), apellidosEditText.getText().toString(),
                                emailEditText.getText().toString(), telEditText.getText().toString());
                    }
                });
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

                builder.setTitle("\t\t          Reserva guardada      ")
                        .setMessage("\t      ")
                        .setMessage("\t\t Recibiras un email de confirmación")
                        .create()
                        .show();
                navController.navigate(R.id.homeFragment);
            }
        });




    }
    public String obtenerFecha(){
        Calendar c = Calendar.getInstance(); System.out.println("Current time => " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy_HH-mm-ss");
        return df.format(c.getTime());
    }
    private void reservarCastle(String id, String castleSeleccion, String nombreEditText, String apellidosEditText, final String emailEditText, String telEditText) {

        fecha = obtenerFecha();

        final Reserva res = new Reserva();

        res.setIdSeleccion(id);
        res.setNombreSelecion(castleSeleccion);
        res.setNombre(nombreEditText);
        res.setApellidos(apellidosEditText);
        res.setEmail(emailEditText);
        res.setTel(telEditText);
        res.setFechaRegistro(fecha);


        db.collection("Cliente").add(res).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isSuccessful()){
                    Log.e("ABCD", "Reserva guardada correctamente  ");
                } else {
                    Log.e("ABCD", "Un problema al guardar la reserva ");

                }
            }
        });
    }
    private boolean validateForm() {
        boolean valid = true;

        String nombre = nombreEditText.getText().toString();
        if (TextUtils.isEmpty(nombre)) {
            nombreEditText.setError("Required.");
            valid = false;
        }
        String apellidos = apellidosEditText.getText().toString();
        if (TextUtils.isEmpty(apellidos)) {
            apellidosEditText.setError("Required.");
            valid = false;
        }
        String email = emailEditText.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Required.");
            valid = false;
        }
        return valid;
    }

}


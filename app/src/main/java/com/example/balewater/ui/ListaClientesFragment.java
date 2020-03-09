package com.example.balewater.ui;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.balewater.R;
import com.example.balewater.model.Cliente;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListaClientesFragment extends Fragment {


    public ListaClientesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lista_clientes, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView clienteRecyclerView = view.findViewById(R.id.clienteRecyclerView);
        Query query = FirebaseFirestore.getInstance().collection("Cliente").limit(50);

        FirestoreRecyclerOptions<Cliente> options = new FirestoreRecyclerOptions.Builder<Cliente>()
                .setQuery(query, Cliente.class)
                .setLifecycleOwner(this)
                .build();

        clienteRecyclerView.setAdapter(new ClienteAdapter(options));

    }

    class ClienteAdapter extends FirestoreRecyclerAdapter<Cliente, ClienteAdapter.ClienteViewHolder> {
        ClienteAdapter(@NonNull FirestoreRecyclerOptions<Cliente> options) { super(options); }

        @NonNull
        @Override
        public ClienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ClienteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cliente, parent, false));
        }

        @Override
        protected void onBindViewHolder(@NonNull ClienteViewHolder holder, int position, @NonNull final Cliente cliente) {
            final String clienteKey = getSnapshots().getSnapshot(position).getId();
            Log.e ("ABCD", "estoy aqui en on bind cliente" + " emilio. " + cliente.getEmail());
            holder.nombreCliente.setText("Nombre: " + cliente.getNombre());
            holder.apellidosCliente.setText("Apellidos: " + cliente.getApellidos());
            holder.email.setText("Email: " + cliente.getEmail());
            holder.telefonoCliente.setText("Tel.: " + cliente.getTel());
            holder.fechaRegistroCliente.setText("Fecha Registro: " + cliente.getFechaRegistro());

        }

        class ClienteViewHolder extends RecyclerView.ViewHolder{
            //ImageView photoImageView, imageImageView, likeImageView;
            TextView nombreCliente, apellidosCliente, telefonoCliente, fechaRegistroCliente, email;

            ClienteViewHolder(@NonNull View itemView) {
                super(itemView);

                nombreCliente = itemView.findViewById(R.id.nombre_cliente);
                apellidosCliente = itemView.findViewById(R.id.apellidos_cliente);
                telefonoCliente = itemView.findViewById(R.id.telefono_cliente);
                fechaRegistroCliente = itemView.findViewById(R.id.fecha_registro);
                email = itemView.findViewById(R.id.email_registro);
            }
        }
    }
}

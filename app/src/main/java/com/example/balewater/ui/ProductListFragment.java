package com.example.balewater.ui;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.balewater.MyFragment;
import com.example.balewater.model.Castle;
import com.example.balewater.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductListFragment extends MyFragment {



    public ProductListFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_list, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView castleRecyclerView = view.findViewById(R.id.itemList);

        Query query = FirebaseFirestore.getInstance().collection("Castle").limit(50);

        FirestoreRecyclerOptions<Castle> options = new FirestoreRecyclerOptions.Builder<Castle>()
                .setQuery(query, Castle.class)
                .setLifecycleOwner(this)
                .build();

        castleRecyclerView.setAdapter(new CastleAdapter(options));
    }



    //    // FirebaseRecyclerAdapter
    class CastleAdapter extends FirestoreRecyclerAdapter<Castle, CastleAdapter.CastleViewHolder> {
        CastleAdapter(@NonNull FirestoreRecyclerOptions<Castle> options) {super(options);}

        @NonNull
        @Override
        public CastleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new CastleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_products, parent, false));
        }

        @Override
        protected void onBindViewHolder(@NonNull CastleViewHolder holder, int position, @NonNull final Castle castle) {

            final String castleId = getSnapshots().getSnapshot(position).getId();

            Log.e("ABCD", "resultado de castle.name: "+ castle.getCastleName());
            holder.name.setText(castle.getCastleName());
            holder.desc.setText(castle.getCastleDescription());
            holder.explain.setText(castle.getCastleExplain());
            holder.prix.setText(castle.getPrecioVenta());
            Glide.with(requireContext()).load(castle.getCastleImage()).circleCrop().into(holder.imageItem);


            holder.meInteresaButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    appViewModel.idSeleccionado.postValue(castleId);
                    appViewModel.castleSeleccionado.postValue(castle.getCastleName());


                    navController.navigate(R.id.reservarCastleFragment);
                }
            });
        }

        class CastleViewHolder extends RecyclerView.ViewHolder {
            TextView name, desc, explain, prix;
            ImageView imageItem;
            Button meInteresaButton;

            CastleViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.product_name);
                desc = itemView.findViewById(R.id.textImage);
                explain = itemView.findViewById(R.id.textExplain);
                prix = itemView.findViewById(R.id.textPrix);
                meInteresaButton = itemView.findViewById(R.id.meInteresa);
                imageItem = itemView.findViewById(R.id.product_image);

            }
//        private void startAnimationImage(){
//
//            Animation animation = AnimationUtils.loadAnimation(requireActivity(), R.anim.anim_image);
//            imageItem.startAnimation(animation);
//            imageItem.animate().cancel();
//        }
        }

    }
}

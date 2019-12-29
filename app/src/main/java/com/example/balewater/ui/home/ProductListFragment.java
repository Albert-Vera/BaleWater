package com.example.balewater.ui.home;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.balewater.Model.BaleWaterResponse;
import com.example.balewater.Model.Castle;
import com.example.balewater.R;
import com.example.balewater.ViewModel.DataViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductListFragment extends Fragment {

    CastleAdapter castleAdapter;
    DataViewModel dataViewModel;


    public ProductListFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_list, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataViewModel = ViewModelProviders.of(requireActivity()).get(DataViewModel.class);

        RecyclerView recyclerView = view.findViewById(R.id.itemList);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.HORIZONTAL));

        castleAdapter = new CastleAdapter();
        recyclerView.setAdapter(castleAdapter);

        dataViewModel.obtenerCastle().observe(this, new Observer<BaleWaterResponse>() {
            @Override
            public void onChanged(BaleWaterResponse baleWaterResponse) {
                castleAdapter.establecerListaCastle(baleWaterResponse.castles);
            }
        });
    }



    class CastleAdapter extends RecyclerView.Adapter<CastleAdapter.CastleViewHolder>{

        List<Castle> castleList = new ArrayList<>();

        @NonNull
        @Override
        public CastleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new CastleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_products, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final CastleViewHolder holder, final int position) {

            final Castle castle = castleList.get(position);

            //holder.name.setText(tour.tourName);
            holder.desc.setText(castle.getCastleDescription());
            Log.e("ABCD", " lala " + castle.getCastleName());
            Glide.with(requireActivity()).load(castle.getCastleImage()).into(holder.imageItem);


            if (castle.getCastleName() != null) {
                holder.name.setText(castle.getCastleName());

            } else {
                holder.name.setVisibility(TextView.GONE);
            }


            holder.imageItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dataViewModel.castle.setValue(castle);

                    Navigation.findNavController(view).navigate(R.id.detailCastleFragment);
                }
            });
        }

        @Override
        public int getItemCount() {
            return castleList.size();
        }

        void establecerListaCastle(List<Castle> t){
            castleList = t;
            notifyDataSetChanged();
        }

        class CastleViewHolder extends RecyclerView.ViewHolder {
            TextView name, desc;
            ImageView imageItem;

            CastleViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.product_name);
                desc = itemView.findViewById(R.id.textImage);


                imageItem = itemView.findViewById(R.id.product_image);

            }
        }
    }

}

package com.example.balewater.ui;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.balewater.MyFragment;
import com.example.balewater.model.Castle;
import com.example.balewater.model.Cliente;
import com.example.balewater.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class HomeFragment extends MyFragment {

    private TextView animTextView, animTextView2;
    private ImageView imgHome;
    private AnimationDrawable frameAnimation;

    public HomeFragment(){}

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        animTextView = view.findViewById(R.id.diverTextView);
        animTextView2 = view.findViewById(R.id.text_home);

        startAnimation();






        // IMAGENES DE LA HOME ALTERNAS
        imgHome = (ImageView) view.findViewById(R.id.fotoPortada);
        imgHome.setImageResource(0);
        // set the xml with images
        imgHome.setBackgroundResource(R.drawable.animation);
        // get the background to show the animation
        frameAnimation = (AnimationDrawable) imgHome.getBackground();
        frameAnimation.start();

        view.findViewById(R.id.EntrarBoton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();

                        if(user == null) {
                            navController.navigate(R.id.signInFragment);
                        }else navController.navigate(R.id.productListFragment);
                    }
                });

            }
        });

    }


    private void startAnimation(){

        Animation animation = AnimationUtils.loadAnimation(requireActivity(), R.anim.anim);
        animTextView.startAnimation(animation);
        animTextView2.startAnimation(animation);
    }
}
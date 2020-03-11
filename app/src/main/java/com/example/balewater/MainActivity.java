package com.example.balewater;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.bumptech.glide.Glide;
import com.example.balewater.model.Usuario;
import com.example.balewater.ui.RegisterFragment;
import com.example.balewater.viewmodel.AppViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;



public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    NavController navController;
    StorageReference storage;
    AppViewModel appViewModel;
    RegisterFragment register = new RegisterFragment();

    //    boolean storeState = false;
//    public void setStoreState(boolean storeState){
//        this.storeState = storeState;
//    }
    private boolean noHayFoto = false;
    public FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        /* Load user info in drawer header*/

        storage = FirebaseStorage.getInstance().getReference();
        appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View header = navigationView.getHeaderView(0);
        final ImageView photo = header.findViewById(R.id.photoImageView);
        final TextView name = header.findViewById(R.id.displayNameTextView);
        final TextView email = header.findViewById(R.id.emailTextView);

        cabeceraMenu(photo, name, email);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment, R.id.productListFragment, R.id.contactFragment, R.id.reservarCastleFragment,
                R.id.listaClientesFragment, R.id.signInFragment)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }


    public void cabeceraMenu(final ImageView photo, final TextView name, final TextView email) {
        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    DocumentReference docRef = db.collection("users").document(user.getUid());

                    if (docRef != null) {

                        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Usuario cuerrentUser = documentSnapshot.toObject(Usuario.class);

                                if (cuerrentUser != null) { // Si se registra con usuario manual
                                    Log.e("ABCD", "toy aqui en particular subir.............");

                                    if (cuerrentUser.imagePerfil == null) {
                                        noHayFoto = true;
                                        Log.e("ABCD", "toy aqui NO HAY FOTOOOO.............");

                                    }
                                    Glide.with(MainActivity.this)
                                            .load(cuerrentUser.imagePerfil)
                                            .circleCrop()
                                            .into(photo);
                                }else{  // Si se registra con usuario Google
                                    Log.e("ABCD", "toy aqui en google subir foto.............");
                                //    MainActivity.super.onRestart();

                                    try {
                                        Glide.with(MainActivity.this)

                                                .load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString())
                                                .circleCrop()
                                                .into(photo);
                                        Log.e("ABCD", "CAGADA............. BREAK");



                                    }catch (Exception e){
                                        Log.e("ABCD", "CAGADA............. La foto aún no ha subido");
                                       // MainActivity.super.recreate();
                                    }
                                    name.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                                    email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                                }
                            }
                        });
                    }

                    name.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                    email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());


                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}



package com.example.balewater;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.example.balewater.model.Usuario;
import com.example.balewater.viewmodel.AppViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static com.google.gson.reflect.TypeToken.get;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    NavController navController;
    StorageReference storage;
    AppViewModel appViewModel;
    private boolean noHayFoto = false;
    public FirebaseFirestore db = FirebaseFirestore.getInstance();

//    public boolean storeState = false;
//    public void setStoreState ( boolean storeState){
//        this.storeState = storeState;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        /* Load user info in drawer header*/
        View header = navigationView.getHeaderView(0);
        final ImageView photo = header.findViewById(R.id.photoImageView);
        final TextView name = header.findViewById(R.id.displayNameTextView);
        final TextView email = header.findViewById(R.id.emailTextView);
        storage = FirebaseStorage.getInstance().getReference();
        appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);


        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    DocumentReference docRef = db.collection("users").document(user.getUid());


                    if (docRef != null) {
                        Log.e("ABCD", "buscando foto users   " + docRef.toString());

                        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Usuario cuerrentUser = documentSnapshot.toObject(Usuario.class);

                                if (cuerrentUser != null) {

                                    if (cuerrentUser.imagePerfil == null) noHayFoto = true;
                                    Glide.with(MainActivity.this)
                                            .load(cuerrentUser.imagePerfil)
                                            .circleCrop()
                                            .into(photo);
                                }else{

                                    Glide.with(MainActivity.this)
                                            .load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString())
                                            .circleCrop()
                                            .into(photo);
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

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment, R.id.productListFragment, R.id.contactFragment, R.id.reservarCastleFragment,
                R.id.listaClientesFragment, R.id.signInFragment)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

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



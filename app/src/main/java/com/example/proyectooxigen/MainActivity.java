package com.example.proyectooxigen;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;


    //declarar variables
    NavigationView navigationView;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();


        //LLamar al metodo para ocultar el item

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //
        ////Primero debemos revisar si es premium o no
        checkTypeofUser();
        //Fin para ocultar el item


        //
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        //Passing each menu ID as a set of Ids because each
        //menu should be considered as top level destinations.


        mAppBarConfiguration = new AppBarConfiguration.Builder(
               R.id.nav_inicio, R.id.nav_registrar, R.id.nav_premium, R.id.nav_contactenos,R.id.nav_cerrarsession,R.id.nav_compartir)
               .setDrawerLayout(drawer)
               .build();


       NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
       NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
       NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void checkTypeofUser() {


        String userID = fAuth.getCurrentUser().getUid();
        DocumentReference df = fStore.collection("Users").document(userID);
        //extract  the data from the document
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess: " + documentSnapshot.getData());
                //identify the user access level

                if (documentSnapshot.getString("TipoUsuario").equals("Usuario")) {
                    hideItem();
                } else {
                    Log.e("TipodeUsuario: ", "" + documentSnapshot.getString("TipoUsuario"));
                }

            }
        });
    }

    private void hideItem() {
        //Ocultar menu registrar
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_registrar).setVisible(false);
        Log.e("mensaje: ", "Ocultando menu");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}


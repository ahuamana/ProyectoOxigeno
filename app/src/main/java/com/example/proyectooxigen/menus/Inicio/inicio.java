package com.example.proyectooxigen.menus.Inicio;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyectooxigen.R;
import com.example.proyectooxigen.adapter.MuestrasAdapter;
import com.example.proyectooxigen.entidades.Ingreso;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class inicio extends Fragment {

    RecyclerView recyclerUsuarios;
    MuestrasAdapter adapter;

    //Firebase
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    private InicioViewModel mViewModel;

    public static inicio newInstance() {
        return new inicio();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.inicio_fragment, container, false);

        //inicializar variables
        //Inicializar firebase
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();


        //codigo
        //iniciar arraylist y refrencia al contenedor

        recyclerUsuarios= (RecyclerView) vista.findViewById(R.id.idRecycler);
        recyclerUsuarios.setLayoutManager( new LinearLayoutManager(this.getContext()));
        recyclerUsuarios.setHasFixedSize(true);

        //crear referencia a Firebase

        FirebaseUser user = fAuth.getCurrentUser();
        CollectionReference datosEmpresa = fStore.collection("DatosEmpresa");

        //
        Query query = datosEmpresa;
        //Craer un builder de firebase del children



        FirestoreRecyclerOptions <Ingreso> options = new FirestoreRecyclerOptions.Builder<Ingreso>()
                .setQuery(query,Ingreso.class)
                .build();

        //enviar los datos al adapter
        adapter=new MuestrasAdapter(options);

        //asignar datos al recyclerView
        recyclerUsuarios.setAdapter(adapter);



        return vista;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(InicioViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
}
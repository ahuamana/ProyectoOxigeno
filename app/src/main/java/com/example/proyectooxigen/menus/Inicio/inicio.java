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
import androidx.appcompat.widget.SearchView;


import com.example.proyectooxigen.R;
import com.example.proyectooxigen.adapter.MuestrasAdapter;
import com.example.proyectooxigen.entidades.Ingreso;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class inicio extends Fragment implements SearchView.OnQueryTextListener {

    RecyclerView recyclerUsuarios;
    MuestrasAdapter adapter;

    //busqueda on recycler
    private SearchView svSearch;

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

        //inicializar variables para buscar
        svSearch= (SearchView) vista.findViewById(R.id.Isearch);
        initListener();


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

    private  void initListener(){
        svSearch.setOnQueryTextListener(this);
    }

    //Inicia Busqueda en recycler view
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //It will run when you write a word on search
        processSearch(newText);

        return false;
    }

    private void processSearch(String newText) {
        adapter=null;
        Log.e("mensajebusqueda: ",newText.toLowerCase());
        FirestoreRecyclerOptions <Ingreso> newoptions = new FirestoreRecyclerOptions.Builder<Ingreso>()
                .setQuery(fStore.collection("DatosEmpresa").orderBy("DepartamentoEmpresaLoweCase").startAt(newText.toLowerCase()).limit(25).endAt(newText.toLowerCase()+'\uf8ff'),Ingreso.class)
                .build();

        //enviar los datos al adapter

        adapter=new MuestrasAdapter(newoptions);
        adapter.startListening();

        //asignar datos al recyclerView
        recyclerUsuarios.setAdapter(adapter);

    }

    private class ProductsViewHolder extends RecyclerView.ViewHolder {
        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    //Fin Busqueda en recycler view
}
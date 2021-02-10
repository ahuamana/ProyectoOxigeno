package com.example.proyectooxigen.menus.cerrarsesion;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyectooxigen.Login.start;
import com.example.proyectooxigen.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class cerrarsession extends Fragment {

    private CerrarsessionViewModel mViewModel;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();

    public static cerrarsession newInstance() {
        return new cerrarsession();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cerrarsession_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CerrarsessionViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onStart() {
        super.onStart();


        //Cerrar Session
        //String data = fAuth.getCurrentUser().getUid();
        //Log.e("UID Antes: ",data);
        fAuth.getInstance().signOut();
        //String data2 = fAuth.getCurrentUser().getUid();
        //Log.e("UID Despues: ",data2);
        startActivity(new Intent(getActivity(), start.class));
        getActivity().finish();



    }
}
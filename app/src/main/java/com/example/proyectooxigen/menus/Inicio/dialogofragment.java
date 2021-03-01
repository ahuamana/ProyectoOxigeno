package com.example.proyectooxigen.menus.Inicio;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.proyectooxigen.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

public class dialogofragment extends DialogFragment implements OnMapReadyCallback {

    //crear variables para recibir los datos del
    String nombre=null;
    String direccion=null;
    String telefono=null;
    String departamentoProvincia=null;
    String latitud=null;
    String longitud=null;
    Marker currentmarker=null;

    GoogleMap map;

    private DialogofragmentViewModel mViewModel;

    //crear 2 constructores( 1 vacio y uno con todos los datos que se recibirar)
    public dialogofragment(String nombre,String direccion,String telefonoEmpresa, String departamentoProvincia, String latitud, String longitud ) {
        this.nombre=nombre;
        this.direccion=direccion;
        this.telefono=telefonoEmpresa;
        this.departamentoProvincia=departamentoProvincia;
        this.latitud=latitud;
        this.longitud=longitud;

    }
    //crear variables para recibir los datos del
    public dialogofragment() {

    }



    public static dialogofragment newInstance() {
        return new dialogofragment();
    }

    //inicio  de generar estos metodos
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return crearDialogo();
    }

    private Dialog crearDialogo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View vista = inflater.inflate(R.layout.dialogofragment_fragment,null);
        builder.setView(vista);

        //Declarar variables y asignar

        Button btnclose = vista.findViewById(R.id.DEbtnClose);
        Button btnllamar = vista.findViewById(R.id.DEbtnllamar);
        TextView tvnombreEmpresa = vista.findViewById(R.id.DEnombreEmpresa);
        TextView tvdireccionEmpresa = vista.findViewById(R.id.DEdireccionEmpresa);
        TextView tvtelefonoEmpresa = vista.findViewById(R.id.DEtelefonoEmpresa);
        TextView tvlugarEmpresa = vista.findViewById(R.id.DElugarEmpresa);

        //inflate maps
        SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentmaps);
        mapFragment.getMapAsync(this);


        //Asignar datos
        tvnombreEmpresa.setText(nombre);
        tvtelefonoEmpresa.setText(telefono);
        tvdireccionEmpresa.setText(direccion);
        tvlugarEmpresa.setText(departamentoProvincia);

        //inicio cerrar boton
        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDestroyView();

            }
        });
        //fin cerrar boton

        //inicio boton llamar
        btnllamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",telefono, null));
                v.getContext().startActivity(intent);
            }
        });
        //fin boton llamar


        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        //asignar contexto

    }

    //fin de generar estos metodos

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.dialogofragment_fragment, container, false);





        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DialogofragmentViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap = googleMap;

        Double LatDouble=Double.parseDouble(latitud);
        Double LonDouble=Double.parseDouble(longitud);

        //Inicializar marker options
        MarkerOptions markerOptions = new MarkerOptions();


        LatLng latLng = new LatLng(LatDouble,LonDouble);
        markerOptions.position(latLng);
        markerOptions.title(nombre);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));

        currentmarker=googleMap.addMarker(markerOptions);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        assert getFragmentManager() != null;
        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentmaps);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.remove(fragment);
        ft.commit();
    }
}
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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.proyectooxigen.R;
import com.example.proyectooxigen.menus.Premium.bepremium;
import com.example.proyectooxigen.menus.Premium.premium;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
    String precio=null;
    String disponibilidad=null;

    TextView tvnombreEmpresa,tvdireccionEmpresa,tvtelefonoEmpresa,tvlugarEmpresa,tvdisponibilidadEmpresa, tvprecioEmpresa;
    TextView txtdispo, txttele,txtprecio;

    Button btnllamar,btnpremium;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    GoogleMap map;

    private DialogofragmentViewModel mViewModel;

    //crear 2 constructores( 1 vacio y uno con todos los datos que se recibirar)

    public dialogofragment(String nombre,String direccion,String telefonoEmpresa, String departamentoProvincia, String latitud, String longitud, String precio, String disponibilidad ) {
        this.nombre=nombre;
        this.direccion=direccion;
        this.telefono=telefonoEmpresa;
        this.departamentoProvincia=departamentoProvincia;
        this.latitud=latitud;
        this.longitud=longitud;

        this.precio=precio;
        this.disponibilidad=disponibilidad;

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
        btnllamar = vista.findViewById(R.id.DEbtnllamar);
        btnpremium = vista.findViewById(R.id.DEbtnpremium);
        tvnombreEmpresa = vista.findViewById(R.id.DEnombreEmpresa);
        tvdireccionEmpresa = vista.findViewById(R.id.DEdireccionEmpresa);
        tvtelefonoEmpresa = vista.findViewById(R.id.DEtelefonoEmpresa);
        tvlugarEmpresa = vista.findViewById(R.id.DElugarEmpresa);
        tvprecioEmpresa = vista.findViewById(R.id.DEprecioEmpresa);
        tvdisponibilidadEmpresa= vista.findViewById(R.id.DEdisponibilidadEmpresa);
        txtdispo= vista.findViewById(R.id.txtdispo);
        txtprecio= vista.findViewById(R.id.txtprecio);
        txttele= vista.findViewById(R.id.txttele);



        txtdispo.setVisibility(View.GONE);
        txtprecio.setVisibility(View.GONE);
        txttele.setVisibility(View.GONE);

        tvprecioEmpresa.setVisibility(View.GONE);
        tvdisponibilidadEmpresa.setVisibility(View.GONE);
        tvtelefonoEmpresa.setVisibility(View.GONE);
        btnllamar.setVisibility(View.GONE);


        checkispremium(vista);
        //inflate maps
        SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentmaps);
        mapFragment.getMapAsync(this);


        //Asignar datos
        tvnombreEmpresa.setText(nombre);
        tvtelefonoEmpresa.setText(telefono);
        tvdireccionEmpresa.setText(direccion);
        tvlugarEmpresa.setText(departamentoProvincia);

        tvdireccionEmpresa.setText(direccion);
        tvlugarEmpresa.setText(departamentoProvincia);

        //inicio volverse premium
        btnpremium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), bepremium.class);
                startActivity(i);

            }
        });
        //fin cerrar boton

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

    private void checkispremium(View vista) {

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        String userID = fAuth.getCurrentUser().getUid();
        DocumentReference df = fStore.collection("Users").document(userID);
        //extract  the data from the document
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess: " + documentSnapshot.getData());
                //identify the user access level

                if (documentSnapshot.getString("Premium").equals("1")) {

                    //activar los botones y textos si son premium
                    tvtelefonoEmpresa.setText(telefono);
                    tvprecioEmpresa.setText(precio+" soles");
                    tvdisponibilidadEmpresa.setText(disponibilidad);

                    txtdispo.setVisibility(View.VISIBLE);
                    txtprecio.setVisibility(View.VISIBLE);
                    txttele.setVisibility(View.VISIBLE);
                    tvprecioEmpresa.setVisibility(View.VISIBLE);
                    tvdisponibilidadEmpresa.setVisibility(View.VISIBLE);
                    tvtelefonoEmpresa.setVisibility(View.VISIBLE);
                    btnllamar.setVisibility(View.VISIBLE);
                    btnpremium.setVisibility(View.GONE);


                } else {
                    Log.e("Premium: ", "" + documentSnapshot.getString("Premium"));
                }

            }
        });


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
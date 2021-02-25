package com.example.proyectooxigen.menus.Registrar;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.proyectooxigen.R;
import com.example.proyectooxigen.rules.validaciones;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class registrar extends Fragment implements View.OnClickListener {

    private RegistrarViewModel mViewModel;
    //Declarar Variables
    EditText FReg_nombreempresa, FReg_direccion,FReg_departamento,FReg_provincia,FReg_Telefono,FReg_precioUni;
    Spinner FReg_spdisponiblidad, FReg_spTipoServicio;
    Button FReg_btnGuardar;
    CircleImageView FReg_ImageView;
    String ValorURL=null;
    validaciones rules= new validaciones();

    //boton obtener latitud longitud
    ImageButton FReg_btnLocation, FReg_btnTakephoto;
    private FusedLocationProviderClient client;
    EditText FReg_latitud, FReg_longitud;

    //Firebase
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    //referencias al storage
    private StorageReference mstorage;
    private static final int GALLERY_INTENT = 1;
    private ProgressDialog progressDialog;



    public static registrar newInstance() {
        return new registrar();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.registrar_fragment, container, false);





        FReg_nombreempresa = vista.findViewById(R.id.FRegistrarNombreEmpresa);
        FReg_departamento = vista.findViewById(R.id.FRegistrardepartamento);
        FReg_direccion = vista.findViewById(R.id.FRegistrarDireccion);
        FReg_provincia = vista.findViewById(R.id.FRegistrarProvincia);
        FReg_Telefono = vista.findViewById(R.id.FRegistrarTelefono);
        FReg_precioUni = vista.findViewById(R.id.FRegistrarPrecioUnidad);
        FReg_ImageView = vista.findViewById(R.id.FRegistrarImageBusiness);

        FReg_btnGuardar = vista.findViewById(R.id.FRegistrarBtnGuardar);
        FReg_btnTakephoto= vista.findViewById(R.id.FRegistrarBtnTakePhoto);

        FReg_btnLocation = vista.findViewById(R.id.FRegistrarbtnLocation);
        FReg_latitud = vista.findViewById(R.id.FRegistrarLatitud);
        FReg_longitud = vista.findViewById(R.id.FRegistrarLongitud);


        FReg_spdisponiblidad = vista.findViewById(R.id.FRegistrarSPdisponibilidad);
        FReg_spTipoServicio = vista.findViewById(R.id.FRegistrarSPTipoServicio);

        //Inicializar firebase
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        FirebaseApp.initializeApp(getActivity());
        mstorage = FirebaseStorage.getInstance().getReference();

        FReg_btnGuardar.setOnClickListener(this);

        client = LocationServices.getFusedLocationProviderClient(getActivity());

        FReg_btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED)
                {

                    //when permision is granted
                    //cal method
                    getCurrentLocation();

                }else
                {
                    //where permision is not granted
                    //request permision
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION},1000);

                }

                //fin del boton

            }
        });

        //inicia tomar foto
        //crear nuevo progressDialog (necesario para mostrar dialogo al cargar la foto)
        progressDialog = new ProgressDialog(getContext());
        FReg_btnTakephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //inicio take photo

                uploadFoto();

                //fin take photo
            }
        });
        //fin tomar foto



        return vista;
    }

    //inicio de metodos para subir foto
    private void uploadFoto() {

        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i, GALLERY_INTENT);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //inicio de if

        if(requestCode==GALLERY_INTENT&& resultCode==RESULT_OK)
        {
            Log.e("test","entro al activity result");
            progressDialog.setTitle("Subiendo Imagen");
            progressDialog.setMessage("Subiendo Foto a FireBase");
            progressDialog.setCancelable(false);
            progressDialog.show();

            Uri mipath = data.getData();

            //RIimgfoto.setImageURI(mipath);

            StorageReference filePath = mstorage.child("MuestraFotos").child(mipath.getLastPathSegment());

            filePath.putFile(mipath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    //inicio on successlistener

                    progressDialog.dismiss();
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();

                    while (!urlTask.isSuccessful());

                    Uri rutaFoto = urlTask.getResult();

                    //Para guardar y mostrar la foto de firebase
                    ValorURL=rutaFoto.toString();
                    //Uri.parse(ValorURL);


                    Glide.with(getActivity())
                            .load(rutaFoto)
                            .placeholder(R.mipmap.ic_launcher)
                            .into(FReg_ImageView);


                    Toast.makeText(getContext(), "Foto Agregada", Toast.LENGTH_SHORT).show();
                    Log.e("test","Valor foto: "+ValorURL);


                    //fin on successlistener
                }
            });


            //fin de if
        }



    }

    //fin de metodos para subir foto



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //check condition
        if(requestCode==100&&(grantResults.length > 0) && (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED))
        {
                //call method
                getCurrentLocation();
        }
        //fin de metodo
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {

        //Inicialize location manager
        LocationManager locationManager = (LocationManager) getActivity()
                .getSystemService(Context.LOCATION_SERVICE);

        //check condicion
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
        {
            //when location service is enabled
            //get last location
            client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {

                    //inicialize lcoation
                    Location  location = task.getResult();
                    //check condition
                    if(location!= null)
                    {
                        //when location is not null
                        //set laittude and longitud
                        FReg_latitud.setText(String.valueOf(location.getLatitude()));
                        FReg_longitud.setText(String.valueOf(location.getLongitude()));
                    }else
                        {
                             //when location is null
                            //inicialize location request
                            LocationRequest locationRequest = new LocationRequest()
                                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                    .setInterval(10000)
                                    .setFastestInterval(1000)
                                    .setNumUpdates(1);

                            //Inicialize location call back
                            LocationCallback locationCallback = new LocationCallback(){
                                @Override
                                public void onLocationResult(LocationResult locationResult) {
                                    //initialize location
                                    Location location1 = locationResult.getLastLocation();
                                    //set latitude and longitude
                                    FReg_latitud.setText(String.valueOf(location.getLatitude()));
                                    FReg_longitud.setText(String.valueOf(location.getLongitude()));
                                }
                            };
                            //Reuest lcoation updates
                            client.requestLocationUpdates(locationRequest,locationCallback,Looper.myLooper());

                        }


                }
            });


        }else
            {

                //when location service is not enabled
                //open location setting
                Log.e("Mensaje: ","Solicitud para activar la localizacion");
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                //displayPromptForEnablingGPS(getActivity());

            }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RegistrarViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onStart() {
        super.onStart();

        //progressDialog = new ProgressDialog(getActivity());
        //progressDialog.setTitle("Cargando Datos");
        //progressDialog.setProgress(10);
        //progressDialog.setMax(100);
        //progressDialog.setMessage("Loading...");
        //progressDialog.show();


        //Inicia el campo de traer los datos
        FirebaseUser user = fAuth.getCurrentUser();//Obtener el id del usuario que ya se creo y se puede ver ese id con el metodo getUid()
        DocumentReference df = fStore.collection("DatosEmpresa").document(user.getUid());

        df.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //Asignar los campos a cada etiqueta de xml

                        FReg_nombreempresa.setText(document.getString("NombreEmpresa"));
                        FReg_direccion.setText(document.getString("DireccionEmpresa"));
                        FReg_departamento.setText(document.getString("DepartamentoEmpresa"));
                        FReg_provincia.setText(document.getString("ProvinciaEmpresa"));
                        FReg_Telefono.setText(document.getString("TelefonoEmpresa"));
                        FReg_precioUni.setText(document.getString("PrecioUnitarioProducto"));

                        //cargando imagen
                        Glide.with(getActivity())
                                .load(document.getString("LogoEmpresaURL"))
                                .placeholder(R.mipmap.ic_launcher)
                                .into(FReg_ImageView);
                        //termino de cargar imagen

                        FReg_latitud.setText(document.getString("LatitudEmpresa"));
                        FReg_longitud.setText(document.getString("LongitudEmpresa"));
                        switch (document.getString("DisponibilidadEmpresa"))
                        {
                            case "Disponible":
                                FReg_spdisponiblidad.setSelection(1);
                                break;
                            case "No disponible":
                                FReg_spdisponiblidad.setSelection(2);
                                break;
                        }

                        switch (document.getString("ServicioEmpresa"))
                        {
                            case "Recargas":
                                FReg_spTipoServicio.setSelection(1);
                                break;
                            case "Ventas":
                                FReg_spTipoServicio.setSelection(2);
                                break;

                            case "Recarga y Venta":
                                FReg_spTipoServicio.setSelection(3);
                                break;
                        }


                        Log.e("Mensaje:", "Nombre de la empresa: " + document.getString("NombreEmpresa"));
                        //fin de asignacion
                        Log.e("Mensaje:", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.e("Mensaje:", "no hay documento");
                    }
                } else {
                    Log.e("Mensaje", "Error al consultar datos ", task.getException());
                }


            }
        });

        //fin del metodo para traer los datos y rellenarlos


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.FRegistrarBtnGuardar: {
                //Toast.makeText(getActivity(), "Probando", Toast.LENGTH_SHORT).show();
                guardarFirebase();
                //llamar al metodo para obtener la localizacion
                break;
            }

        }

        //fin del onclick
    }

    private void guardarFirebase() {

        //validar campos vacios
        boolean valid_spdisponibilidad=true, valid_spservicio=true,valid_NombreEmpresa=true, valid_DireccionEmpresa=true,
                valid_DepartamentoEmpresa=true,valid_provincia=true,valid_precio=true,valid_telefono=true, valid_location=true;

        valid_spdisponibilidad=rules.checkSpinner(FReg_spdisponiblidad,"Seleccione la disponibilidad");
        valid_spservicio=rules.checkSpinner(FReg_spTipoServicio,"Seleccione un servicio");

        valid_NombreEmpresa=rules.checkField(FReg_nombreempresa);
        valid_DepartamentoEmpresa=rules.checkField(FReg_departamento);
        valid_DireccionEmpresa=rules.checkField(FReg_direccion);
        valid_provincia=rules.checkField(FReg_provincia);
        valid_precio=rules.checkField(FReg_precioUni);
        valid_telefono=rules.checkField(FReg_Telefono);

        valid_location=rules.checkField(FReg_latitud);

        //fin de validad campos vacios

        //inicio de ifs
        if(valid_NombreEmpresa)
        {
            if(valid_DepartamentoEmpresa)
            {
                if(valid_DireccionEmpresa)
                {
                    if(valid_provincia)
                    {
                        if(valid_precio)
                        {
                            if(valid_telefono)
                            {
                                if(valid_spdisponibilidad)
                                {

                                    if(valid_spservicio)
                                    {
                                        if(valid_location)

                                        {
                                            //Inicia el boton guardar
                                            FirebaseUser user = fAuth.getCurrentUser();//Obtener el id del usuario que ya se creo y se puede ver ese id con el metodo getUid()
                                            DocumentReference df = fStore.collection("DatosEmpresa").document(user.getUid());

                                            df.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                                    if (task.isSuccessful()) {
                                                        DocumentSnapshot document = task.getResult();
                                                        if (document.exists()) {
                                                            //Actualizar campos en Firebase
                                                            FirebaseUser user = fAuth.getCurrentUser();//Obtener el id del usuario que ya se creo y se puede ver ese id con el metodo getUid()
                                                            DocumentReference df = fStore.collection("DatosEmpresa").document(user.getUid());

                                                            Map<String, Object> actualizacionDatos = new HashMap<>();
                                                            actualizacionDatos.put("NombreEmpresa",FReg_nombreempresa.getText().toString());
                                                            actualizacionDatos.put("DireccionEmpresa",FReg_direccion.getText().toString());
                                                            actualizacionDatos.put("DepartamentoEmpresa",FReg_departamento.getText().toString());
                                                            actualizacionDatos.put("TelefonoEmpresa",FReg_Telefono.getText().toString());
                                                            actualizacionDatos.put("ProvinciaEmpresa",FReg_provincia.getText().toString());
                                                            actualizacionDatos.put("PrecioUnitarioProducto",FReg_precioUni.getText().toString());
                                                            actualizacionDatos.put("DisponibilidadEmpresa",FReg_spdisponiblidad.getSelectedItem().toString());
                                                            actualizacionDatos.put("ServicioEmpresa",FReg_spTipoServicio.getSelectedItem().toString());

                                                            actualizacionDatos.put("LatitudEmpresa",FReg_latitud.getText().toString());
                                                            actualizacionDatos.put("LongitudEmpresa",FReg_longitud.getText().toString());
                                                            actualizacionDatos.put("LogoEmpresaURL",ValorURL);

                                                            df.update(actualizacionDatos);
                                                            //fin de asignacion
                                                            Log.e("Mensaje:", "DocumentSnapshot data: " + document.getData());
                                                            Toast.makeText(getActivity(), "Datos actualizados correctamente!", Toast.LENGTH_SHORT).show();
                                                            //fin de actualizacion

                                                        } else {
                                                            Log.e("Mensaje:", "no hay documento, comenzaremos a guardar los campos en firebase");

                                                            //Guardar los campos
                                                            ////Utilizaremos Map para almacenar ahi los datos de la empresa
                                                            Map<String, Object> userInfo = new HashMap<>();
                                                            userInfo.put("NombreEmpresa",FReg_nombreempresa.getText().toString());
                                                            userInfo.put("DireccionEmpresa",FReg_direccion.getText().toString());
                                                            userInfo.put("DepartamentoEmpresa",FReg_departamento.getText().toString());
                                                            userInfo.put("TelefonoEmpresa",FReg_Telefono.getText().toString());
                                                            userInfo.put("ProvinciaEmpresa",FReg_provincia.getText().toString());
                                                            userInfo.put("PrecioUnitarioProducto",FReg_precioUni.getText().toString());
                                                            userInfo.put("DisponibilidadEmpresa",FReg_spdisponiblidad.getSelectedItem().toString());
                                                            userInfo.put("ServicioEmpresa",FReg_spTipoServicio.getSelectedItem().toString());

                                                            userInfo.put("LatitudEmpresa",FReg_latitud.getText().toString());
                                                            userInfo.put("LongitudEmpresa",FReg_longitud.getText().toString());
                                                            userInfo.put("LogoEmpresaURL",ValorURL);

                                                            //enviar el map con los datos a Firebase
                                                            df.set(userInfo);
                                                            Toast.makeText(getActivity(), "Datos guardados correctamente!", Toast.LENGTH_SHORT).show();


                                                        }
                                                    } else {
                                                        Log.e("Mensaje", "Error al consultar datos ", task.getException());
                                                    }


                                                }
                                            });

                                            //fin de df.onCompleteListener

                                        }


                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        //fin de revisar los ifs



        //fin del metodo para traer los datos y rellenarlos

    }


}
package com.example.proyectooxigen.menus.Registrar;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.proyectooxigen.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class registrar extends Fragment {

    private RegistrarViewModel mViewModel;
    //Declarar Variables
    EditText FReg_nombreempresa, FReg_direccion,FReg_departamento,FReg_provincia,FReg_Telefono,FReg_precioUni;
    Spinner FReg_spdisponiblidad, FReg_spTipoServicio;
    Button FReg_btnGuardar;

    //Firebase
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

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

        FReg_btnGuardar = vista.findViewById(R.id.FRegistrarBtnGuardar);

        FReg_spdisponiblidad = vista.findViewById(R.id.FRegistrarSPdisponibilidad);
        FReg_spTipoServicio = vista.findViewById(R.id.FRegistrarSPTipoServicio);

        //Inicializar firebase
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        FReg_btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                                //enviar el map con los datos a Firebase
                                df.set(userInfo);
                                Toast.makeText(getActivity(), "Datos guardados correctamente!", Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            Log.e("Mensaje", "Error al consultar datos ", task.getException());
                        }


                    }
                });

                //fin del metodo para traer los datos y rellenarlos

                //Inicia accion del boton


                //Termina accion del boton


            }
        });





        return vista;
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

                        FReg_precioUni.setText(document.getString("NombreEmpresa"));

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
}
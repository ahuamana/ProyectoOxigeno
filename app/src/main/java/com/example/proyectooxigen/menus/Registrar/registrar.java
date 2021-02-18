package com.example.proyectooxigen.menus.Registrar;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.proyectooxigen.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
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
                //Inicia accion del boton
                FirebaseUser user = fAuth.getCurrentUser();//Obtener el id del usuario que ya se creo y se puede ver ese id con el metodo getUid()
                DocumentReference df = fStore.collection("DatosEmpresa").document(user.getUid());

                ////Utilizaremos Map para almacenar ahi los datos de la empresa
                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("NombreEmpresa",FReg_nombreempresa.getText().toString());
                userInfo.put("DireccionEmpresa",FReg_direccion.getText().toString());
                userInfo.put("DepartamentoEmpresa",FReg_departamento.getText().toString());
                userInfo.put("TelefonoEmpresa",FReg_Telefono.getText().toString());
                userInfo.put("ProvinciaEmpresa",FReg_Telefono.getText().toString());
                userInfo.put("PrecioUnitarioProducto",FReg_Telefono.getText().toString());
                userInfo.put("DisponibilidadEmpresa",FReg_spdisponiblidad.getSelectedItem().toString());
                userInfo.put("ServicioEmpresa",FReg_spTipoServicio.getSelectedItem().toString());

                //enviar el map con los datos a Firebase
                df.set(userInfo);
                Toast.makeText(getActivity(), "Datos guardados correctamente!", Toast.LENGTH_SHORT).show();
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

    }
}
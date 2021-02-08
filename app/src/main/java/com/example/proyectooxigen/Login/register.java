package com.example.proyectooxigen.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.proyectooxigen.MainActivity;
import com.example.proyectooxigen.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {
    EditText LoginNombre, LoginEmail,LoginPassword;
    Spinner LoginSpinnerTipoDeUsuario;
    Button LoginBtnregistrar;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();

        LoginNombre = findViewById(R.id.loginNombre);
        LoginEmail = findViewById(R.id.loginEmail);
        LoginPassword = findViewById(R.id.loginPassword);
        LoginSpinnerTipoDeUsuario = findViewById(R.id.loginSpinnerTipoUsuario);
        LoginBtnregistrar=findViewById(R.id.loginBtnRegistrarse);

        LoginBtnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Empieza el registro de usuario
                fAuth.createUserWithEmailAndPassword(LoginEmail.getText().toString(),LoginPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser user = fAuth.getCurrentUser();//Obtener el id del usuario que ya se creo y se puede ver ese id con el metodo getUid()
                        Toast.makeText(register.this, "Cuenta creada exitosamente!", Toast.LENGTH_SHORT).show();
                        //Guardar datos en Firestore
                        DocumentReference df = fstore.collection("Users").document(user.getUid());

                        ////Utilizaremos Map para almacenar ahi los datos del usuario
                        Map<String, Object> userInfo = new HashMap<>();
                        userInfo.put("Fullname",LoginNombre.getText().toString());
                        userInfo.put("Email",LoginEmail.getText().toString());
                        userInfo.put("Password",LoginPassword.getText().toString());
                        userInfo.put("TipoUsuario",LoginSpinnerTipoDeUsuario.getSelectedItem().toString());
                        userInfo.put("Premium","0");

                        df.set(userInfo);

                        //Fin Guardar datos
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();;
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(register.this, "Fallo! Intentelo nuevamente", Toast.LENGTH_SHORT).show();
                    }
                });

                //Termina la funcion del click re registrar
            }
        });

    }

    public void OpenLoginPage(View view) {
        startActivity(new Intent(register.this,start.class) );
    }

    //Validar campos


}
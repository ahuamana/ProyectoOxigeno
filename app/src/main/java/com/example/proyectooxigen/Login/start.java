package com.example.proyectooxigen.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.proyectooxigen.MainActivity;
import com.example.proyectooxigen.R;
import com.example.proyectooxigen.rules.validaciones;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class start extends AppCompatActivity {

    LinearLayout contenedorRegresar;
    Button loginbtnAnotherAccount, loginbtnIngresar;
    EditText STemail, STpassword;
    boolean validEmail=true;
    boolean validPassword=true;
    validaciones rules= new validaciones();

    //Referencias
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //Inicio
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        //relacion con ids del xml
        STemail = findViewById(R.id.STemail);
        STpassword=findViewById(R.id.STpassword);
        //fin de la relacion

        //ocultar el contenedor de regresar del login
        contenedorRegresar = findViewById(R.id.contenedorRegresar);
        contenedorRegresar.setVisibility(View.GONE);
        //find e ocultar contenedor

        //ocultar el Boton AnotherAccount
        loginbtnAnotherAccount = findViewById(R.id.LoginbtnAnotherAccount);
        loginbtnAnotherAccount.setVisibility(View.GONE);
        //find e ocultar  Boton AnotherAccount

        loginbtnIngresar= findViewById(R.id.LoginBtnIngresar);
        //inicio de accion del boton Ingresar
        loginbtnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Valida campos
                validEmail=rules.checkField(STemail);
                validPassword=rules.checkField(STpassword);

                if(validEmail)
                {
                    if(validPassword) {
                        //Inicia Login
                        ////Inicia metodo de signin
                        fAuth.signInWithEmailAndPassword(STemail.getText().toString(), STpassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(start.this, "Correcto!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(start.this, "Correo o Contraseña incorrecto!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        ////finaliza metodo de signin
                    }//fin valid password
                }//fin del if(validEmail)

            }
        });
        //fin de accion del boton Ingresar


    }


    public void OpenSignupPage(View view) {

        startActivity(new Intent(start.this,register.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Verificar que ningun usuario este  ya logueado
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        //fin de la verificacion

    }



}
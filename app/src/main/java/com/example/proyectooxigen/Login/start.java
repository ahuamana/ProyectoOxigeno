package com.example.proyectooxigen.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.proyectooxigen.R;

public class start extends AppCompatActivity {

    LinearLayout contenedorRegresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //ocultar el contenedor de regresar del login
        contenedorRegresar = findViewById(R.id.contenedorRegresar);
        contenedorRegresar.setVisibility(View.GONE);
        //find e ocultar contenedor

    }

    public void OpenSignupPage(View view) {

        startActivity(new Intent(start.this,register.class));
    }
}
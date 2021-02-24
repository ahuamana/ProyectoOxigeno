package com.example.proyectooxigen.menus.Contactenos;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectooxigen.R;

public class contactenos extends Fragment {

    private ContactenosViewModel mViewModel;
    TextView tvFangpage, tvnumber1,tvnumber2;

    public static contactenos newInstance() {
        return new contactenos();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.contactenos_fragment, container, false);

        tvFangpage = vista.findViewById(R.id.CTTtvFanpage);
        tvFangpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //inicio del click

                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse("https://www.facebook.com/Oxigenate-102113655265459/"));
                startActivity(browserIntent);

                //fin del click
            }
        });

        tvnumber1 = vista.findViewById(R.id.CTTtvnumber1);
        tvnumber1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //incio del click
                String phoneNumberWithCountryCode = "+51902533338";
                String message = "Hola, te contactaste con Oxigenate, \n¿En que te podemos ayudar?";

                startActivity(
                        new Intent(Intent.ACTION_VIEW,
                                Uri.parse(
                                        String.format("https://api.whatsapp.com/send?phone=%s&text=%s", phoneNumberWithCountryCode, message)
                                )
                        )
                );

                //fin del click

            }
        });

        tvnumber2 = vista.findViewById(R.id.CTTtvnumber2);
        tvnumber2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //incio del click
                String phoneNumberWithCountryCode = "+51959556825";
                String message = "Hola, te contactaste con Oxigenate, \n¿En que te podemos ayudar?";

                startActivity(
                        new Intent(Intent.ACTION_VIEW,
                                Uri.parse(
                                        String.format("https://api.whatsapp.com/send?phone=%s&text=%s", phoneNumberWithCountryCode, message)
                                )
                        )
                );

                //fin del click

            }
        });








        return vista;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ContactenosViewModel.class);
        // TODO: Use the ViewModel
    }

}
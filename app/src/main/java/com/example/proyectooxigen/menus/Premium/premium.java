package com.example.proyectooxigen.menus.Premium;

import androidx.lifecycle.ViewModelProvider;

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


import com.example.proyectooxigen.R;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;

import org.json.JSONArray;
import org.json.JSONObject;

public class premium extends Fragment {

    private PremiumViewModel mViewModel;
    //Google PLay API
    private PaymentsClient paymentsClient;


    Button btncontactar;


    public static premium newInstance() {
        return new premium();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.premium_fragment, container, false);

        btncontactar= vista.findViewById(R.id.btncontactarasesor);

        btncontactar.setOnClickListener(new View.OnClickListener() {
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
        mViewModel = new ViewModelProvider(this).get(PremiumViewModel.class);
        // TODO: Use the ViewModel

        Wallet.WalletOptions walletOptions = new Wallet.WalletOptions.Builder()
                .setEnvironment(WalletConstants.ENVIRONMENT_TEST)
                .build();


        paymentsClient = Wallet.getPaymentsClient(getActivity(),walletOptions);


    }

}
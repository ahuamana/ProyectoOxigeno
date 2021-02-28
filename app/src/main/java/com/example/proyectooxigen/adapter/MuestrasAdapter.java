package com.example.proyectooxigen.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.proyectooxigen.MainActivity;
import com.example.proyectooxigen.R;
import com.example.proyectooxigen.entidades.Ingreso;
import com.example.proyectooxigen.maps.maps;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class MuestrasAdapter extends FirestoreRecyclerAdapter<Ingreso,MuestrasAdapter.MuestrasHolder>  {

    public MuestrasAdapter(FirestoreRecyclerOptions<Ingreso> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(MuestrasHolder holder, int position, Ingreso model) {


        //asignar variables con firebase
        holder.txtnombre.setText(model.getNombreEmpresa());
        Log.e("mensajeadapter",""+String.valueOf(model.getPrecioUnitarioProducto()));

        //set first letter to Uppercase
        String input= model.getDireccionEmpresa();
        String output = input.substring(0, 1).toUpperCase() + input.substring(1);
        //send the direccion output to textview
        holder.txtdireccion.setText(output);
        holder.txtplace.setText(model.getDepartamentoEmpresa()+", "+model.getProvinciaEmpresa());


        //accion del boton
        holder.btnmasinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("mensajeimagen",""+String.valueOf(model.getLogoEmpresaURL()));
                Log.e("mensajeboton","Hizo Click");
                final Dialog dialogo=new Dialog(v.getContext(), android.R.style.Theme_DeviceDefault_Light_Dialog);
                dialogo.setContentView(R.layout.dialogo_fulldata_empresa);

                //
                Fragment firstfragment = new maps();
                FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                fragmentManager.beginTransaction().add(R.id.frgmaps,firstfragment).commit();



                //Variables del dialogo
                ImageView fotoempresa = dialogo.findViewById(R.id.DEfotoEmpresa);
                Button btnclose = dialogo.findViewById(R.id.DEbtnClose);
                Button btnllamar = dialogo.findViewById(R.id.DEbtnllamar);
                TextView tvnombreEmpresa = dialogo.findViewById(R.id.DEnombreEmpresa);
                TextView tvdireccionEmpresa = dialogo.findViewById(R.id.DEdireccionEmpresa);
                TextView tvtelefonoEmpresa = dialogo.findViewById(R.id.DEtelefonoEmpresa);
                TextView tvlugarEmpresa = dialogo.findViewById(R.id.DElugarEmpresa);

                //asignar valores a cada caja de texto e imagenes
                Glide.with(dialogo.getContext())
                        .load(model.getLogoEmpresaURL())
                        .placeholder(R.mipmap.ic_launcher)
                        .into(fotoempresa);

                tvnombreEmpresa.setText(model.getNombreEmpresa());
                tvdireccionEmpresa.setText(output);
                tvtelefonoEmpresa.setText(model.getTelefonoEmpresa());
                tvlugarEmpresa.setText(model.getDepartamentoEmpresa()+", "+model.getProvinciaEmpresa());

                //iniciar cierre dialogo
                btnclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogo.dismiss();
                    }
                });
                //finalizar cierre dialogo

                btnllamar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", model.getTelefonoEmpresa(), null));
                        v.getContext().startActivity(intent);

                    }
                });


                dialogo.show();

            }
        });

        //action del boton

        //Integer.valueOf(model.getPrecioDB());
        Glide.with(holder.foto.getContext())
                .load(model.getLogoEmpresaURL())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.foto);

    }

    @NonNull
    @Override
    public MuestrasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_view,parent,false);




        return new MuestrasHolder(vista);
    }



    public class MuestrasHolder extends RecyclerView.ViewHolder {

        //crear variables para hacer la refenrencia al molde xml
        TextView txtnombre, txtdireccion, txtplace, txtprovincia;
        Button btnmasinfo;
        ImageView foto;



        public MuestrasHolder(@NonNull View vista) {
            super(vista);

            txtnombre= vista.findViewById(R.id.tvName);
            txtdireccion=vista.findViewById(R.id.tvAddress);
            txtplace=vista.findViewById(R.id.tvPlace);
            //txtprovincia=vista.findViewById(R.id.MMtxtBQV);
            btnmasinfo=vista.findViewById(R.id.btnmasinfo);
            foto=vista.findViewById(R.id.ILimagen);

        }
    }



}

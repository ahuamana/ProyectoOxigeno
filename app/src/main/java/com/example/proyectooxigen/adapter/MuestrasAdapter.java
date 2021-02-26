package com.example.proyectooxigen.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.proyectooxigen.R;
import com.example.proyectooxigen.entidades.Ingreso;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class MuestrasAdapter extends FirestoreRecyclerAdapter<Ingreso,MuestrasAdapter.MuestrasHolder> {

    public MuestrasAdapter(FirestoreRecyclerOptions<Ingreso> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(MuestrasHolder holder, int position, Ingreso model) {

        //asignar variables con firebase
        holder.txtnombre.setText(model.getNombreEmpresa());
        Log.e("mensajeadapter",""+String.valueOf(model.getPrecioUnitarioProducto()));
        holder.txtdireccion.setText(model.getDireccionEmpresa());

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
        TextView txtnombre, txtdireccion, txtdepartamento, txtprovincia;
        Button btnmasinfo;
        ImageView foto;

        public MuestrasHolder(@NonNull View vista) {
            super(vista);

            txtnombre= vista.findViewById(R.id.tvName);
            txtdireccion=vista.findViewById(R.id.tvAddress);
            //txtdepartamento=vista.findViewById(R.id.MMtxtTiempo);
            //txtprovincia=vista.findViewById(R.id.MMtxtBQV);
            btnmasinfo=vista.findViewById(R.id.btnmasinfo);
            foto=vista.findViewById(R.id.ILimagen);


        }
    }



}

package com.example.practica01.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practica01.R;
import com.example.practica01.model.Plato;

import java.util.List;

public class PlatoAdapter extends RecyclerView.Adapter<PlatoAdapter.PlatoViewHolder> {

    private List<Plato> mDataset;
    private AppCompatActivity activity;

    public PlatoAdapter(List<Plato> mDataset, AppCompatActivity activity) {
        this.mDataset = mDataset;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PlatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_plato,parent,false);
        PlatoViewHolder vh = new PlatoViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull PlatoViewHolder holder, int position) {

        final Plato plato = mDataset.get(position);

        holder.nombreTV.setText(plato.getNombre());
        holder.precioTV.setText("$"+plato.getPrecio().toString());
        holder.descripcionTV.setText(plato.getDescripcion());
        if(activity.getIntent().getExtras().containsKey("Listar")){
            holder.pedirButtonCV.setVisibility(View.GONE);
        }
        if(activity.getIntent().getExtras().containsKey("Pedido")){
            holder.pedirButtonCV.setVisibility(View.VISIBLE);
        }
        holder.pedirButtonCV.setOnClickListener((View v)->{
            Intent intent = new Intent();
            intent.putExtra("Plato",plato);
            activity.setResult(Activity.RESULT_OK,intent);
            activity.finish();
        });

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class PlatoViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView nombreTV;
        TextView precioTV;
        TextView descripcionTV;
        Button pedirButtonCV;


        public PlatoViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.platoCV);
            nombreTV = itemView.findViewById(R.id.nombrePlatoCV);
            precioTV = itemView.findViewById(R.id.precioPlatoCV);
            descripcionTV = itemView.findViewById(R.id.descripcionPlatoCV);
            pedirButtonCV = itemView.findViewById(R.id.pedirButtonCV);



        }
    }
}

package com.example.practica01.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

        Plato plato = mDataset.get(position);

        holder.nombreTV.setText(plato.getNombre());
        holder.precioTV.setText("Precio: $"+plato.getPrecio().toString());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class PlatoViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView nombreTV;
        TextView precioTV;

        public PlatoViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.platoCV);
            nombreTV = itemView.findViewById(R.id.nombrePlatoCV);
            precioTV = itemView.findViewById(R.id.precioPlatoCV);

        }
    }
}

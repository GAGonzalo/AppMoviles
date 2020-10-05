package com.example.practica01.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practica01.R;
import com.example.practica01.model.Plato;

import java.util.List;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.PlatoViewHolder> {

    private final List<Plato> mDataset;
    private final AppCompatActivity activity;



    public PedidoAdapter(List<Plato> mDataset, AppCompatActivity activity) {
        this.mDataset = mDataset;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PlatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pedidoview_plato,parent,false);
        PedidoAdapter.PlatoViewHolder vh = new PedidoAdapter.PlatoViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PlatoViewHolder holder, int position) {
        final Plato plato = mDataset.get(position);
        holder.nombrePlato.setText(plato.getNombre());
        holder.precioPlato.setText("$"+plato.getPrecio().toString());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class PlatoViewHolder extends RecyclerView.ViewHolder {
        TextView nombrePlato;
        TextView precioPlato;


        public PlatoViewHolder(@NonNull View itemView) {

            super(itemView);
            this.nombrePlato=itemView.findViewById(R.id.platoTV32);
            this.precioPlato=itemView.findViewById(R.id.precioTV32);
            

        }
    }


}

package com.example.practica01.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practica01.R;
import com.example.practica01.model.Plato;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class PlatoAdapter extends RecyclerView.Adapter<PlatoAdapter.PlatoViewHolder> {

    private List<Plato> mDataset;
    private AppCompatActivity activity;
    private FirebaseStorage storage;
    ImageView iv;

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

        storage = FirebaseStorage.getInstance();
        iv=holder.imageView;
        someFunction(holder.imageView,plato.getId());
        holder.nombreTV.setText(plato.getNombre());
        holder.precioTV.setText("$"+plato.getPrecio().toString());
        holder.descripcionTV.setText(plato.getDescripcion());
        holder.imageView=iv;

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
        ImageView imageView;
        Button pedirButtonCV;


        public PlatoViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.platoCV);
            nombreTV = itemView.findViewById(R.id.nombrePlatoCV);
            precioTV = itemView.findViewById(R.id.precioPlatoCV);
            descripcionTV = itemView.findViewById(R.id.descripcionPlatoCV);
            pedirButtonCV = itemView.findViewById(R.id.pedirButtonCV);
            imageView = itemView.findViewById(R.id.imageView);



        }
    }

    private void someFunction(ImageView imageView, Long id) {
        // Creamos una referencia al storage con la Uri de la img
        StorageReference gsReference = storage.getReferenceFromUrl("gs://lab-dam-295616.appspot.com/images/plato_"+id+".jpg");

        final long THREE_MEGABYTE = 3 * 1024 * 1024;
        gsReference.getBytes(THREE_MEGABYTE).addOnSuccessListener(bytes -> {
            // Exito
            Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

            imageView.setMinimumHeight(dm.heightPixels);
            imageView.setMinimumWidth(dm.widthPixels);
            imageView.setImageBitmap(bm);

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Error - Cargar una imagen por defecto
                System.out.println("error");
            }
        });
    }
}

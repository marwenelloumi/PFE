package com.example.kheerMaro.Controller;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kheerMaro.Model.Plant;
import com.example.kheerMaro.R;
import com.example.kheerMaro.UI.CommndActivity;

import java.util.List;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.PlantViewHolder>{

    private Context context;
    private List<Plant> plantList;


    public PlantAdapter(Context context, List<Plant> plantList) {
        this.context = context;
        this.plantList = plantList;
    }

    @NonNull
    @Override
    public PlantAdapter.PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_plant,parent,false);

        return new PlantViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull PlantAdapter.PlantViewHolder holder, int position) {
        Plant plant = plantList.get(position);
        holder.name.setText(plant.getName());
        holder.description.setText((plant.getDescription()));
    }

    @Override
    public int getItemCount() {
        return plantList.size();
    }

    public class PlantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
     {
        private TextView name,description;
        public PlantViewHolder(@NonNull View itemView) {
            super(itemView);
       itemView.setOnClickListener(this);
        name=itemView.findViewById(R.id.nomdelaplante);
        description=itemView.findViewById(R.id.desc);
        }

        @Override
        public void onClick(View v) {
        int position = getAdapterPosition();
        Plant plante = plantList.get(position);
            Intent intent = new Intent(context, CommndActivity.class);
            intent.putExtra("id",plante.getId());
            intent.putExtra("plantName",plante.getName());
            context.startActivity(intent);

        }

    }
}

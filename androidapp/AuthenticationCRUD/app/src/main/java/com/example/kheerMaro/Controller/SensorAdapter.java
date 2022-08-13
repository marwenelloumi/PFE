package com.example.kheerMaro.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kheerMaro.R;

import java.util.List;

public class SensorAdapter extends RecyclerView.Adapter<SensorAdapter.SensorAdapterViewHolder>{
    private Context context;
    private List<String> sensorList;

    public  SensorAdapter(Context context, List<String> sensorList) {
        this.context = context;
        this.sensorList = sensorList;
    }

    @NonNull
    @Override
    public SensorAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sensor_list,parent,false);

        return new SensorAdapter.SensorAdapterViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull SensorAdapterViewHolder holder, int position) {
    String sensor = sensorList.get(position);
    if(sensor.substring(0,11).equals("Température"))
        holder.image.setImageResource(R.drawable.ic_tem);
    if(sensor.substring(0,8).equals("Humidité"))
        holder.image.setImageResource(R.drawable.ic_hum);
    if(sensor.substring(0,15).equals("Humidité du sol"))
       holder.image.setImageResource(R.drawable.ic_soil);
    holder.valeurCapteur.setText(sensor);


    }

    @Override
    public int getItemCount() {
        return sensorList.size();
    }
    public class SensorAdapterViewHolder extends RecyclerView.ViewHolder
    {
        private TextView valeurCapteur;
        private ImageView image;
        public SensorAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            valeurCapteur =itemView.findViewById(R.id.valeurCapteur);
            image=itemView.findViewById(R.id.imageView3);
        }
    }
}

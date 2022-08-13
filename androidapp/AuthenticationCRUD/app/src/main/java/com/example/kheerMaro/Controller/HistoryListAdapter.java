package com.example.kheerMaro.Controller;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kheerMaro.Model.Command;
import com.example.kheerMaro.Model.Plant;
import com.example.kheerMaro.R;
import com.example.kheerMaro.UI.HistoryActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.kheerMaro.SERVER.URLs.ROOT_URL;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.HistoryListViewHolder> {
    private Context context;
    private List<Plant> plantList;


    public HistoryListAdapter(Context context, List<Plant> plantList) {
        this.context = context;
        this.plantList = plantList;
    }

    @NonNull
    @Override
    public HistoryListAdapter.HistoryListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_list,parent,false);

        return new HistoryListAdapter.HistoryListViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final HistoryListAdapter.HistoryListViewHolder holder, int position) {
        Plant plant = plantList.get(position);
        holder.name.setText(plant.getName());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL )
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiHold apiHold;
        apiHold = retrofit.create(ApiHold.class);
        Call<Command> call = apiHold.getCommandsDate(plant.getId(),1);
        call.enqueue(new Callback<Command>() {
            @Override
            public void onResponse(Call<Command> call, Response<Command> response) {
                Command command = response.body();
                String date=command.getRecptionDate();
                String dateFormate = formatDate(date);
                holder.date.setText((dateFormate));
            }

            @Override
            public void onFailure(Call<Command> call, Throwable t) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return plantList.size();
    }

    public class HistoryListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView name,date;
        public HistoryListViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name=itemView.findViewById(R.id.HLName);
            date=itemView.findViewById(R.id.HLDesc);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Plant plante = plantList.get(position);
            Intent intent = new Intent( context, HistoryActivity.class);
            intent.putExtra("id",plante.getId());
            context.startActivity(intent);
        }

    }
    private String formatDate(String dateStr)
    {
            String MM = dateStr.substring(5,7);
            String dd = dateStr.substring(8,10);
            String HHmm = dateStr.substring(11,16);
            return dd+"/"+MM+", "+HHmm;
    }
}

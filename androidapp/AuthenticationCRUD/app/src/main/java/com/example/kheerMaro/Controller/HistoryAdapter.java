package com.example.kheerMaro.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kheerMaro.Model.Command;
import com.example.kheerMaro.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.CommandViewHolder> {
    private Context context;
    private List<Command> commandList;

    public  HistoryAdapter(Context context, List<Command> commandList) {
        this.context = context;
        this.commandList = commandList;
    }

    @NonNull
    @Override
    public HistoryAdapter.CommandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.operation_list,parent,false);

        return new HistoryAdapter.CommandViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.CommandViewHolder holder, int position) {
        Command comamnd = commandList.get(position);
        Boolean etat=comamnd.getCommandeSysteme();
        String result="";
        if(etat) result="Activation de la pompe";
        else result = "Désactivation de la pompe";
        holder.op.setText(result);
        String newDate = formatDate(comamnd.getRecptionDate());
        holder.date.setText(newDate);

    }

    @Override
    public int getItemCount() {
        return commandList.size();
    }

    public class CommandViewHolder extends RecyclerView.ViewHolder
    {
        private TextView date,op;
        public CommandViewHolder(@NonNull View itemView) {
            super(itemView);
            op=itemView.findViewById(R.id.Hop);
            date=itemView.findViewById(R.id.Hdesc);
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

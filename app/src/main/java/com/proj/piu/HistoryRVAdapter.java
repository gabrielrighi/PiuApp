package com.proj.piu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoryRVAdapter extends RecyclerView.Adapter<HistoryRVAdapter.ViewHolder> {
    private ArrayList<HistoryModal> historyModalArrayList;
    private Context context;

    public HistoryRVAdapter(ArrayList<HistoryModal> historyModalArrayList, Context context){
        this.historyModalArrayList = historyModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // on below line we are setting data
        // to our views of recycler view item.
        HistoryModal modal = historyModalArrayList.get(position);
        holder.historyEspecieTV.setText(modal.getEspecie());
        holder.historyNomeComumTV.setText(modal.getNomeComum());
        holder.historyDataHoraTV.setText(modal.getDataHora());

        int enviado = modal.getEnviado();
        long idGravacao = modal.getId();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(enviado == 1){
                    Intent retornoAct = new Intent(context,RetornoActivity.class);

                    retornoAct.putExtra("idGravacao",idGravacao);

                    context.startActivity(retornoAct);
                    ((Activity)context).finish();
                }else{
                    Intent audioAct = new Intent(context,AudioActivity.class);

                    audioAct.putExtra("idGravacao",idGravacao);

                    context.startActivity(audioAct);
                    ((Activity)context).finish();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list
        return historyModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        private TextView historyEspecieTV, historyNomeComumTV, historyDataHoraTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            historyEspecieTV = itemView.findViewById(R.id.txtEspecie);
            historyNomeComumTV = itemView.findViewById(R.id.txtNomeComum);
            historyDataHoraTV = itemView.findViewById(R.id.txtDataHora);
        }
    }
}

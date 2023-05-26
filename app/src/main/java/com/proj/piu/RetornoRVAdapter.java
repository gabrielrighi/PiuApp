package com.proj.piu;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RetornoRVAdapter extends RecyclerView.Adapter<RetornoRVAdapter.ViewHolder> {
    private ArrayList<RetornoModal> retornoModalArrayList;
    private Context context;

    public RetornoRVAdapter(ArrayList<RetornoModal> retornoModalArrayList, Context context){
        this.retornoModalArrayList = retornoModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.retorno_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // on below line we are setting data
        // to our views of recycler view item.
        RetornoModal modal = retornoModalArrayList.get(position);
        holder.retornoNomeCientificoTV.setText(modal.getNomeCientifico());
        holder.retornoNomeComumTV.setText(modal.getNomeComum());
        //holder.retornoConfiancaTV.setText(String.valueOf(modal.getConfianca()));
        holder.retornoConfiancaTV.setText(modal.getConfiancaFormat());
        holder.retornoDataHoraTV.setText(modal.getDataHora());

        String idEspecie = modal.getEspecie();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent detalhesAct = new Intent(context,DetalhesActivity.class);
                detalhesAct.putExtra("idEspecie",idEspecie);
                context.startActivity(detalhesAct);

            }
        });

    }

    @Override
    public int getItemCount() {
        // returning the size of our array list
        return retornoModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        private TextView retornoNomeCientificoTV, retornoNomeComumTV, retornoConfiancaTV, retornoDataHoraTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            retornoNomeCientificoTV = itemView.findViewById(R.id.txtNomeCientifico);
            retornoNomeComumTV = itemView.findViewById(R.id.txtNomeComum);
            retornoConfiancaTV = itemView.findViewById(R.id.txtConfianca);
            retornoDataHoraTV = itemView.findViewById(R.id.txtDataHora);
        }
    }
}

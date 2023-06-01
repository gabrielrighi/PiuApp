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

public class EspeciesRVAdapter extends RecyclerView.Adapter<EspeciesRVAdapter.ViewHolder> {
    private ArrayList<EspeciesModal> especiesModalArrayList;
    private Context context;

    public EspeciesRVAdapter(ArrayList<EspeciesModal> especiesModalArrayList, Context context){
        this.especiesModalArrayList = especiesModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.especies_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // on below line we are setting data
        // to our views of recycler view item.
        EspeciesModal modal = especiesModalArrayList.get(position);
        holder.especiesNomeCientificoTV.setText(modal.getNomeCientifico());
        holder.especiesNomeComumTV.setText(modal.getNomeComum());

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
        return especiesModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        private TextView especiesNomeCientificoTV, especiesNomeComumTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            especiesNomeCientificoTV = itemView.findViewById(R.id.txtNomeCientifico);
            especiesNomeComumTV = itemView.findViewById(R.id.txtNomeComum);
        }
    }
}

package acp.example.myapplication2.Logic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import acp.example.myapplication2.Model.Ingredientes;
import acp.example.myapplication2.R;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class MostrarListaIngre extends RealmRecyclerViewAdapter<Ingredientes, MostrarListaIngre.MyViewHolder> {

    public MostrarListaIngre(@Nullable OrderedRealmCollection<Ingredientes> data) {
        super(data, true);
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ingreView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mostra_lista_ingre, parent,false);
        MyViewHolder mvh = new MyViewHolder(ingreView);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final Ingredientes ingredientes = getItem(position);

        if( ingredientes != null){
            holder.fratxtQua.setText(ingredientes.getQua_ing());
            holder.fratxtMed.setText(ingredientes.getMed_ing());
            holder.fratxtIng.setText(ingredientes.getDes_ing());
        }
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId_ing();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView fratxtQua;
        TextView fratxtMed;
        TextView fratxtIng;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fratxtQua = itemView.findViewById(R.id.fratxtQua);
            fratxtMed = itemView.findViewById(R.id.fratxtMed);
            fratxtIng = itemView.findViewById(R.id.fratxtIng);
        }

    }
}

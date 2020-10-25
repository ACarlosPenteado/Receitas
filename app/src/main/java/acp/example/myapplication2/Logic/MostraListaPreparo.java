package acp.example.myapplication2.Logic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import acp.example.myapplication2.Model.ModoPreparo;
import acp.example.myapplication2.R;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class MostraListaPreparo extends RealmRecyclerViewAdapter<ModoPreparo, MostraListaPreparo.MyViewHolder> {

    private OnItemClickListener1 mlistener1;

    public interface OnItemClickListener1 {
        void onItemClick1(int position);
    }

    public void setOnItemClickListener1(OnItemClickListener1 listener1) {
        mlistener1 = listener1;
    }

    public MostraListaPreparo(@Nullable OrderedRealmCollection<ModoPreparo> data) {
        super(data, true);
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ingreView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mostrar_lista_prepa, parent,false);
        MyViewHolder mvh = new MyViewHolder(ingreView, mlistener1);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final ModoPreparo modoPreparo = getItem(position);

        if( modoPreparo != null ){
            holder.fraimvPre.setImageResource(R.drawable.ic_check_24dp);
            holder.fratxtPre.setText(modoPreparo.getDes_mod().toString());
        }
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId_mod();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView fraimvPre;
        TextView fratxtPre;

        MyViewHolder(@NonNull View itemView, final OnItemClickListener1 listener1) {
            super(itemView);
            fraimvPre = itemView.findViewById(R.id.fraimvPre);
            fratxtPre = itemView.findViewById(R.id.fratxtPre);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( listener1 != null){
                        int position = getAbsoluteAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener1.onItemClick1(position);
                        }
                    }
                }
            });
        }
    }
}

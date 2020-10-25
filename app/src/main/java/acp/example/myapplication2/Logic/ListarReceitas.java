package acp.example.myapplication2.Logic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import acp.example.myapplication2.MainActivity;
import acp.example.myapplication2.Model.Receitas;
import acp.example.myapplication2.Mostrar_Receitas;
import acp.example.myapplication2.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class ListarReceitas extends
        RecyclerView.Adapter<ListarReceitas.MyViewHolder> {

    private Context mcontext;
    private ReceitaClickInterface receitaClickInterface;
    private ArrayList<Receitas> mReceitas;
    Global global = new Global();

    public ListarReceitas(ArrayList<Receitas> mReceitas,
                          Context context, ReceitaClickInterface receitaClickInterface) {
        this.mReceitas = mReceitas;
        this.mcontext = context;
        this.receitaClickInterface = receitaClickInterface;
    }

    public interface ReceitaClickInterface {
        void onItemClick(int position);
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View receView = LayoutInflater.from(parent.getContext()).inflate(R.layout.linha_receitas, parent,false);
        MyViewHolder mvh = new MyViewHolder(receView);

        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        final Receitas mreceitas = mReceitas.get(i);
        final Bitmap img;
        if( mreceitas != null){
            String txt = mReceitas.get(i).getImg_rec();
            if(txt.equals("foto")){
                holder.imVimg_rec.setImageResource(R.drawable.ic_menu_camera);
            } else {
                img = global.txtTOimg(txt);
                holder.imVimg_rec.setImageBitmap(img);
            }
            holder.txVdes_rec.setText(mReceitas.get( i ).getDes_rec());
            holder.txVtem_rec.setText(mReceitas.get( i ).getTem_rec());
            holder.txVren_rec.setText(mReceitas.get( i ).getRen_rec());
        }
    }

    @Override
    public int getItemCount() {
        if( mReceitas != null ){
            return mReceitas.size();
        }
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imVimg_rec;
        private TextView txVdes_rec;
        private TextView txVtem_rec;
        private TextView txVren_rec;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imVimg_rec = itemView.findViewById(R.id.imVimg_rec);
            txVdes_rec = itemView.findViewById(R.id.txVdes_rec);
            txVtem_rec = itemView.findViewById(R.id.txVtem_rec);
            txVren_rec = itemView.findViewById(R.id.txVren_rec);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    receitaClickInterface.onItemClick(getAbsoluteAdapterPosition());
                }
            });

        }

    }
}

package acp.example.myapplication2.Fragmentos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import acp.example.myapplication2.Logic.SharedViewModel;
import acp.example.myapplication2.R;
import io.realm.Realm;

public class Frag_dialog_deleta_receita extends DialogFragment {

    private SharedViewModel fragViewModel;
    private  static final String TAG = "Frag_deleta_receita";
    private Boolean ok;
    private TextView txtDeleta;
    private Button btnCancel, btnOk;
    private Realm realm;
    private int id_rec;
    private String tip_rec;

    public  interface OnReceitaListener{
        void confirmaExclusao(Boolean confirma);
    }

    public  OnReceitaListener mOnReceitaListener;

   public Frag_dialog_deleta_receita() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Script", "onCreate()");
        fragViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        setCancelable(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.frag_dialog_deleta_receita, container, false);
        txtDeleta = view.findViewById(R.id.txtnome);

        id_rec = fragViewModel.junto.getId_rec();
        tip_rec = fragViewModel.junto.getTip_rec();

        btnCancel = view.findViewById(R.id.btnCancel);
        btnOk = view.findViewById(R.id.btnOk);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean ok = true;
                mOnReceitaListener.confirmaExclusao(ok);
                dismiss();
            }
        });
        return view;
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            mOnReceitaListener = (OnReceitaListener) getActivity();
        } catch (ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException: "+ e.getMessage() );
        }
    }
}

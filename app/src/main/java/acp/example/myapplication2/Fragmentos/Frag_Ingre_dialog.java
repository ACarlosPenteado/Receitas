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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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

public class Frag_Ingre_dialog extends DialogFragment {

    private SharedViewModel fragViewModel;
    private  static final String TAG = "Frag_Ingre_dialog";

    private final String[] MEDIDAS = new String[]{ "Kilo(s)", "Xícara(s) de Chá",
            "Colher(es) de Sopa", "Colher(es) de Chá", "Colher(es) de Café", "Gramas", "Ml",
            "Copo(s) Americano", "Copo(s) de Requeijão", "Litro(s)" };

    private Boolean OK = null;
    private TextView txtnome;
    private AutoCompleteTextView actmedi;
    private EditText edTxqua, edTxing;
    Button btnCancel, btnOk;

    public interface OnIngreListener{
        void sendIngre(String qua, String med, String desc);
    }

    public OnIngreListener mOnIngreListener;
    String qua, med, desc;

    public Frag_Ingre_dialog() {
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

        View view = inflater.inflate(R.layout.frag_dialog_ingre, container, false);
        txtnome = view.findViewById(R.id.txtnome);
        edTxqua = view.findViewById(R.id.edTxqua_ing);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, MEDIDAS);
        actmedi = (AutoCompleteTextView) view.findViewById(R.id.actvMedida1);
        actmedi.setAdapter(adapter);

        //txtnome.setText("Frag_Ingre_dialog");
        edTxing = view.findViewById(R.id.edTxing_rec);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnOk = view.findViewById(R.id.btnOk);
        edTxqua.requestFocus();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (edTxing.getText().length() == 0){
                    mostrarToast("AVISO", "Nome do ingrediente não pode estar vazio!");
                    edTxing.requestFocus();
                } else {
                   //mostrarToast(""+edTxqua.getText()+"", " "+medi.getText()+" "+edTxing.getText()+"");
                   String quan = String.valueOf(edTxqua.getText());
                   String medi = String.valueOf(actmedi.getText());
                   String desc = String.valueOf(edTxing.getText());
                   //fragViewModel.pega_dados_Ing(quan, medi, desc);
                   mOnIngreListener.sendIngre( quan, medi, desc );
                   dismiss();

                }
            }
        });
        return view;
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void mostrarToast(String text1, String text2){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup)getActivity().findViewById(R.id.layout_base));
        TextView txt1 = layout.findViewById(R.id.txtToast1);
        TextView txt2 = layout.findViewById(R.id.txtToast2);
        ImageView img1 = layout.findViewById(R.id.imgToast);

        txt1.setText(text1);
        txt2.setText(text2);
        Glide.with(getActivity()).load(R.drawable.ic_baseline_perm_device_information_24)
                .apply(RequestOptions.circleCropTransform()).into(img1);
        Toast toast = new Toast(getActivity().getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            mOnIngreListener = (OnIngreListener) getActivity();
        } catch (ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage() );
        }
    }

}

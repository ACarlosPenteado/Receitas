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
import io.realm.Realm;

public class Frag_Ingre_dialog1 extends DialogFragment {

    private Realm realm;
    private SharedViewModel fragIngreViewModel;
    private  static final String TAG = "Frag_Ingre_dialog";
    private Boolean OK = null;
    int inc_alt;

    private final String[] MEDIDAS = new String[]{ "Kilo(s)", "Xícara(s) de Chá",
            "Colher(es) de Sopa", "Colher(es) de Chá", "Colher(es) de Café", "Gramas", "Ml",
            "Copo(s) Americano", "Copo(s) de Requeijão", "Litro(s)" };

    private TextView txtnome;
    private EditText edTxqua;
    private AutoCompleteTextView medi;
    private EditText edTxing;

    Button btnCancel, btnDel, btnOk;

    public interface OnIngreListener1{
        void sendIngre(String qua, String med, String desc);
        void alt_Ingre(int idi, String qua, String med, String desc);
        void apagaIngre(int idi);
    }

    public OnIngreListener1 mOnIngreListener;

    String qua, med, desc;
    int idi;

    public Frag_Ingre_dialog1() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Script", "onCreate()");
        setCancelable(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_dialog_ingre1, container, false);
        fragIngreViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);

        txtnome = view.findViewById(R.id.txtnome);
        edTxqua = view.findViewById(R.id.edTxqua_ing);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, MEDIDAS);
        medi = view.findViewById(R.id.actvMedida1);
        medi.setAdapter(adapter);
        //txtnome.setText("Frag_Ingre_dialog1");
        edTxing = view.findViewById(R.id.edTxdes_ing);

        btnCancel = view.findViewById(R.id.btnCancel);
        btnDel = view.findViewById(R.id.btnDel);
        btnOk = view.findViewById(R.id.btnOk);

        inc_alt = fragIngreViewModel.junto.getQl();

        idi = fragIngreViewModel.junto.getId_ing();
        qua = fragIngreViewModel.junto.getQua_ing();
        med = fragIngreViewModel.junto.getMed_ing();
        desc = fragIngreViewModel.junto.getDes_ing();

        edTxqua.setText(qua);
        medi.setText(med);
        edTxing.setText(desc);

        edTxing.requestFocus();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
                onDestroyView();
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnIngreListener.apagaIngre(idi);
                onDestroyView();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (edTxing.getText().length() == 0){
                   mostrarToast("AVISO", "Nome do ingrediente não pode estar vazio!");
                   edTxing.requestFocus();
                } else {
                   if( inc_alt == 1 ) {
                       mOnIngreListener.sendIngre(edTxqua.getText().toString(), medi.getText().toString(), edTxing.getText().toString());
                   } else if( inc_alt == 2 ){
                       mOnIngreListener.alt_Ingre( idi, edTxqua.getText().toString(), medi.getText().toString(), edTxing.getText().toString());
                   }
                   onDestroyView();
               }
            }
        });
        return view;
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
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            mOnIngreListener = (OnIngreListener1) getActivity();
        } catch (ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage() );
        }
    }

}

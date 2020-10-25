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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import acp.example.myapplication2.R;

public class Frag_Prepa_dialog extends DialogFragment {

    private  static final String TAG = "Frag_Prepa_dialog";
    private Boolean OK = null;

    Button btnCancel, btnOk;

    public interface OnPrepaListener{
        void sendPrepa(String prepa);
    }

    public OnPrepaListener mOnPrepaListener;

    String prepa;

    public Frag_Prepa_dialog() {
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
        View view = inflater.inflate(R.layout.frag_dialog_prepa, container, false);
        EditText edTxpre = view.findViewById(R.id.edTxpre_rec);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnOk = view.findViewById(R.id.btnOk);

        edTxpre.setText("");
        edTxpre.requestFocus();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edTxpre.setText("");
                onDestroyView();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if (edTxpre.getText().length() == 0){
                    mostrarToast("AVISO", "Modo de preparo não pode estar vazio!");
                    edTxpre.setText("");
                    edTxpre.requestFocus();
                 } else {
                    mOnPrepaListener.sendPrepa(edTxpre.getText().toString());
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
            mOnPrepaListener = (OnPrepaListener) getActivity();
        } catch (ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage() );
        }
    }

}

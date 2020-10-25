package acp.example.myapplication2.Fragmentos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import acp.example.myapplication2.Logic.SharedViewModel;
import acp.example.myapplication2.R;
import io.realm.Realm;

public class Frag_dialog_restore extends DialogFragment {

    private  static final String TAG = "Frag_restaura_arquivo";
    private Boolean ok;
    private Button btnCancel, btnOk;

    public  interface OnRestoreListener{
        void confirmaRestore(Boolean confirma);
    }

    public Frag_dialog_restore.OnRestoreListener mOnRestoreListener;

    public Frag_dialog_restore() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.frag_dialog_restore, container, false);

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
                mOnRestoreListener.confirmaRestore(ok);
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
            mOnRestoreListener = (Frag_dialog_restore.OnRestoreListener) getActivity();
        } catch (ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException: "+ e.getMessage() );
        }
    }
}

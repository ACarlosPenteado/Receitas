package acp.example.myapplication2.Fragmentos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import acp.example.myapplication2.R;

public class Frag_Busca_dialog extends DialogFragment {

    private  static final String TAG = "DialogCustom";

    public interface OnFotoListener{
        void sendFoto(String foto);
    }

    public OnFotoListener mOnFotoListener;

    String url;
    ImageView ivFrag;

    public Frag_Busca_dialog() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_dialog_custom, container, false);
        EditText edtxt1 = view.findViewById(R.id.edtxt1);
        TextView txcancel = view.findViewById(R.id.txcancel);
        TextView txOk = view.findViewById(R.id.txOk);
        ivFrag = view.findViewById(R.id.ivfrag);

        txcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtxt1.setText("");
                ivFrag.setImageBitmap(null);
                getDialog().dismiss();
            }
        });

        edtxt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = edtxt1.getText().toString();
                if(url.isEmpty()){
                    Toast.makeText(getActivity(), "Digite o caminho da imagem", Toast.LENGTH_SHORT).show();
                } else {
                    Picasso.get()
                            .load(url)
                            .into(ivFrag);

                }
            }
        });

        txOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = edtxt1.getText().toString();
                if(url.isEmpty()){
                    Toast.makeText(getActivity(), "Digite o caminho da imagem", Toast.LENGTH_SHORT).show();
                } else {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    Bitmap bitmap = viewToBitmap(ivFrag, ivFrag.getWidth(), ivFrag.getHeight());
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageBytes = baos.toByteArray();
                    String imageString = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
                    mOnFotoListener.sendFoto(imageString);
                    getDialog().dismiss();
                }
            }
        });
        return view;
    }

    public static Bitmap viewToBitmap(View view, int width, int height){
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas( bitmap );
        view.draw(canvas);
        return bitmap;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            mOnFotoListener = (OnFotoListener) getActivity();
        } catch (ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage() );
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }


}

package acp.example.myapplication2.ui.Drawer.Ajuda;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import acp.example.myapplication2.R;

public class Ajuda_Frag extends Fragment {

    TextView txtAjuda;
    Button btnInicio, btnListar, btnIncluir, btnMostrar;
    View inicio, listar, incluir, mostrar;
    ConstraintSet.Motion motion;
    public Ajuda_Frag() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frag_ajuda, container, false);
        txtAjuda = root.findViewById(R.id.txtajuda);
        btnInicio = root.findViewById(R.id.inicio);
        btnListar = root.findViewById(R.id.listar);
        btnIncluir = root.findViewById(R.id.incluir);
        btnMostrar = root.findViewById(R.id.mostrar);
        inicio = root.findViewById(R.id.inicio_frag);
        listar = root.findViewById(R.id.listar_frag);
        incluir = root.findViewById(R.id.incluir_frag);
        mostrar = root.findViewById(R.id.mostrar_frag);

        txtAjuda.setText("Ajuda");

        inicio.setVisibility(View.INVISIBLE);
        listar.setVisibility(View.INVISIBLE);
        incluir.setVisibility(View.INVISIBLE);
        mostrar.setVisibility(View.INVISIBLE);

        btnInicio.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                txtAjuda.setText("INÍCIO");
                inicio.setVisibility(View.VISIBLE);
                Inicio_Frag_ajuda inicioFragAjuda = new Inicio_Frag_ajuda();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.inicio_frag, inicioFragAjuda);
                transaction.commit();

            }
        });
        btnListar.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                txtAjuda.setText("LISTAR");
                /*listar.setVisibility(View.VISIBLE);
                Listar_Frag_ajuda listarFragAjuda = new Listar_Frag_ajuda();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.listar_frag, listarFragAjuda);
                transaction.commit();*/
            }
        });
        btnIncluir.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                txtAjuda.setText("INCLUIR");
                /*incluir.setVisibility(View.VISIBLE);
                Incluir_Frag_ajuda incluirFragAjuda = new Incluir_Frag_ajuda();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.incluir_frag, incluirFragAjuda);
                transaction.commit();*/
            }
        });
        btnMostrar.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                txtAjuda.setText("MOSTRAR");
                /*mostrar.setVisibility(View.VISIBLE);
                Mostrar_Frag_ajuda mostrarFragAjuda = new Mostrar_Frag_ajuda();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.mostrar_frag, mostrarFragAjuda);
                transaction.commit();*/
            }
        });
        return root;
    }
}
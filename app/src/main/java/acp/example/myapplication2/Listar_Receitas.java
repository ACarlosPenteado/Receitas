package acp.example.myapplication2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.Snackbar;

import acp.example.myapplication2.Fragmentos.Frag_dialog_deleta_receita;
import acp.example.myapplication2.Fragmentos.Frag_dialog_vazia;
import acp.example.myapplication2.Logic.Configuracao;
import acp.example.myapplication2.Logic.DataHelper;
import acp.example.myapplication2.Logic.ListarReceitas;
import acp.example.myapplication2.Model.Receitas;
import io.realm.Realm;
import io.realm.RealmResults;
import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class Listar_Receitas extends AppCompatActivity
        implements ListarReceitas.ReceitaClickInterface,
        Frag_dialog_vazia.OnVaziaListener {

    private String tipo_rec, ajuda;
    private Realm realm;
    DataHelper myHelper;
    private RecyclerView rcvReceitasLista;
    private RecyclerView.LayoutManager layoutManager;
    private RealmResults<Receitas> listaReceitas;
    private ListarReceitas receitaAdapter;
    Configuracao application;
    ConstraintLayout constraintLayout;
    FloatingTextButton fab1;

    public void confirmaVazia(Boolean confirma) {
        if (confirma) {
            Intent intent1 = new Intent(Listar_Receitas.this, IncluirReceitas.class);
            intent1.putExtra("TIP_REC", tipo_rec);
            startActivity(intent1);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            Intent intent2 = new Intent(Listar_Receitas.this, MainActivity.class);
            startActivity(intent2);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_receitas);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        application = (Configuracao) getApplication();
        ShimmerFrameLayout container1 =
                (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container1);
        container1.startShimmer();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        tipo_rec = intent.getStringExtra("TIP_REC");

        ajuda = "Clique sobre a receita que quer ver" +
                " ou clique no botão para adicionar uma nova";
        final TextView textView = findViewById(R.id.txvLista);

        FloatingTextButton ftb = findViewById(R.id.add_button);
        constraintLayout = findViewById(R.id.listar);
        fab1 = findViewById(R.id.fabAjuda1);

        String trec = "Lista de Receitas " + tipo_rec;
        textView.setText(trec);

        realm = Realm.getDefaultInstance();
        listaReceitas = realm.where(Receitas.class)
                .equalTo("tip_rec", tipo_rec).findAll();

        if (listaReceitas.size() == 0) {
            application.save = false;
            Frag_dialog_vazia dialog1 = new Frag_dialog_vazia();
            dialog1.show(getSupportFragmentManager(), "Base de Dados vazia...");
        } else {
            rcvRecMostrar();
        }
        ftb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                application.save = true;
                Intent intent1 = new Intent(Listar_Receitas.this, IncluirReceitas.class);
                intent1.putExtra("TIP_REC", tipo_rec);
                startActivity(intent1);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar scb1 = Snackbar.make(constraintLayout, ajuda, Snackbar.LENGTH_INDEFINITE)
                        .setBackgroundTint(Color.TRANSPARENT)
                        .setActionTextColor(Color.RED)
                        .setAction("X", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Snackbar scb2 = Snackbar.make(constraintLayout,"", Snackbar.LENGTH_SHORT)
                                        .setBackgroundTint(Color.TRANSPARENT);
                                        //scb2.show();
                            }
                        }).setTextColor(Color.CYAN);


                scb1.show();
            }
        });

    }

    private void rcvRecMostrar() {
        int i;
        rcvReceitasLista = findViewById(R.id.rcvReceitas);

        myHelper = new DataHelper(realm);
        myHelper.selectreceita(tipo_rec);

        receitaAdapter = new ListarReceitas(myHelper.justRefresh(), this, this);
        layoutManager = new LinearLayoutManager(this);
        rcvReceitasLista.setHasFixedSize(true);
        rcvReceitasLista.setLayoutManager(layoutManager);

        rcvReceitasLista.setAdapter(receitaAdapter);
        rcvReceitasLista.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(Listar_Receitas.this, Mostrar_Receitas.class);
        intent.putExtra("ID_REC", listaReceitas.get(position).getId_rec());
        intent.putExtra("TIP_REC", tipo_rec);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
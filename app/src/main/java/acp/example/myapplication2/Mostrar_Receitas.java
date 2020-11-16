package acp.example.myapplication2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.Snackbar;

import org.sufficientlysecure.htmltextview.HtmlFormatter;
import org.sufficientlysecure.htmltextview.HtmlFormatterBuilder;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import acp.example.myapplication2.Fragmentos.Frag_dialog_deleta_receita;
import acp.example.myapplication2.Logic.DataHelper;
import acp.example.myapplication2.Logic.Global;
import acp.example.myapplication2.Logic.MostraListaPreparo;
import acp.example.myapplication2.Logic.MostrarListaIngre;
import acp.example.myapplication2.Logic.SharedViewModel;
import acp.example.myapplication2.Model.Receitas;
import io.realm.Realm;
import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class Mostrar_Receitas extends AppCompatActivity
        implements Frag_dialog_deleta_receita.OnReceitaListener {

    private SharedViewModel mostrarViewModel;
    private final int PERMISSAO_REQUEST = 3;
    private final int CAMERA = 4;
    private static final String TAG = "Mostrar_Receitas";
    private int id_rec;
    private ArrayList<Receitas> listaReceitas;
    private MostrarListaIngre ingreAdapter;
    private MostraListaPreparo prepAdapter;
    Realm realm;
    String fotoString;
    String tipo_rec, ajuda;
    private ImageView foto;
    private TextView desc;
    private TextView temp;
    private TextView rend;
    private RecyclerView rvIngre, rvPrepa;
    Bitmap decodedImage;
    Global global = new Global();
    private Boolean ok;
    Animation myAnim;

    public void confirmaExclusao(Boolean confirma) {
        ok = confirma;
        if (ok) {
            realm = Realm.getDefaultInstance();
            DataHelper.apagaReceita(realm, id_rec);
            DataHelper.apagaIngredientes(realm, id_rec);
            DataHelper.apagaPreparos(realm, id_rec);
            Toast.makeText(this, "Receita apagada", Toast.LENGTH_SHORT).show();
            realm.close();
            Intent intent = new Intent(this, Listar_Receitas.class);
            intent.putExtra("TIP_REC", tipo_rec);
            startActivity(intent);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.MyTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_receitas);

        verificaPermissao();

        ShimmerFrameLayout container1 =
                (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container1);
        container1.startShimmer();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mostrarViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        Log.d(TAG, "Iniciando tela...");
        Intent intent = getIntent();
        tipo_rec = intent.getStringExtra("TIP_REC");
        id_rec = intent.getIntExtra("ID_REC", 0);

        ajuda = "Clique sobre a receita que quer ver" +
                " ou clique no botão para adicionar uma nova";

        desc = (TextView) findViewById(R.id.mostrar);
        temp = (TextView) findViewById(R.id.txtMTemp);
        rend = (TextView) findViewById(R.id.txtMRend);
        foto = (ImageView) findViewById(R.id.ivMFoto);
        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        foto.startAnimation(myAnim);

        realm = Realm.getDefaultInstance();
        listaReceitas = new ArrayList<>(realm.where(acp.example.myapplication2.Model.Receitas.class)
                .equalTo("id_rec", id_rec).findAll());

        desc.setText(listaReceitas.get(0).getDes_rec());
        temp.setText(listaReceitas.get(0).getTem_rec());
        rend.setText(listaReceitas.get(0).getRen_rec());
        fotoString = listaReceitas.get(0).getImg_rec();

        if (fotoString == "foto") {
            foto.setImageResource(R.drawable.ic_menu_camera);
        } else {
            decodedImage = global.txtTOimg(fotoString);
            foto.setImageBitmap(decodedImage);
        }

        rvIngre = findViewById(R.id.rv_ingre);
        ingreAdapter = new MostrarListaIngre(realm.where(acp.example.myapplication2.Model.Ingredientes.class)
                .equalTo("id_irec", id_rec).findAll());
        rvIngre.setHasFixedSize(true);
        rvIngre.setLayoutManager(new LinearLayoutManager(getApplication()));
        rvIngre.setAdapter(ingreAdapter);
        rvIngre.addItemDecoration(new DividerItemDecoration(getApplication(), DividerItemDecoration.VERTICAL));

        rvPrepa = findViewById(R.id.rv_prepa);
        prepAdapter = new MostraListaPreparo(realm.where(acp.example.myapplication2.Model.ModoPreparo.class)
                .equalTo("id_mrec", id_rec).findAll());
        rvPrepa.setHasFixedSize(true);
        rvPrepa.setLayoutManager(new LinearLayoutManager(getApplication()));
        rvPrepa.setAdapter(prepAdapter);
        rvPrepa.addItemDecoration(new DividerItemDecoration(getApplication(), DividerItemDecoration.VERTICAL));

        realm.close();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_mostrar_receitas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(this, "" + tipo_rec, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, Listar_Receitas.class);
                intent.putExtra("TIP_REC", tipo_rec);
                startActivity(intent);
                break;
            case R.id.editaR:
                Intent intent1 = new Intent(this, EditarReceitas.class);
                intent1.putExtra("ID_REC", id_rec);
                intent1.putExtra("TIP_REC", tipo_rec);
                startActivity(intent1);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.apagaR:
                mostrarViewModel.pass_receita(id_rec, (String) tipo_rec);
                Frag_dialog_deleta_receita dialog1 = new Frag_dialog_deleta_receita();
                dialog1.show(getSupportFragmentManager(), "Apagar Receita");
                break;
            case R.id.compartilhaR:
                compartilhar();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void compartilhar() {

        String branco = "<p></p>";
        String nReceita =""+desc.getText().toString()+"";
        String receita =
                "<p><b>Tempo Preparo: " + temp.getText().toString() + " minutos</b></p>" +
                        "<small><b>Rendimento: " + rend.getText().toString() + " porções</b></small>\n";

        String ingre = "<H1>Ingredientes:</H1>";

        String ingrediente = "";
        int i;
        for (i = 0; i < ingreAdapter.getItemCount(); i++) {
            ingrediente = ingrediente + "<h1>" + ingreAdapter.getItem(i).getQua_ing() + " " +
                    ingreAdapter.getItem(i).getMed_ing() + " " +
                    ingreAdapter.getItem(i).getDes_ing() + "</h1>\n";
        }
        String mod = "Modo Preparo:";

        String preparo = "";
        for (i = 0; i < prepAdapter.getItemCount(); i++) {
            preparo = preparo + "<h3>" + prepAdapter.getItem(i).getDes_mod() + "</h3>\n";
        }

        Intent sendIntent = new Intent(Intent.ACTION_SEND);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        decodedImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), decodedImage, "Receitas", null);
        Uri uri = Uri.parse(path);

        sendIntent.setType("image/*");
        sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, nReceita);
        sendIntent.setType("*/*");
        sendIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(receita + branco + ingre + ingrediente + branco + mod + preparo));

        try {
            startActivity(Intent.createChooser(sendIntent, "Compartilhar usando:"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Mostrar_Receitas.this, "Não há app para compartilhar.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean verificaPermissao() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, CAMERA);
        } else {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            PERMISSAO_REQUEST);
                }
            }
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            PERMISSAO_REQUEST);
                }
            }
        }
        return true;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Listar_Receitas.class);
        intent.putExtra("TIP_REC", tipo_rec);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //realm.close();
    }
}

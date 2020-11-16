package acp.example.myapplication2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import acp.example.myapplication2.Fragmentos.Frag_Busca_dialog;
import acp.example.myapplication2.Fragmentos.Frag_Ingre_dialog;
import acp.example.myapplication2.Fragmentos.Frag_Prepa_dialog;
import acp.example.myapplication2.Logic.Configuracao;
import acp.example.myapplication2.Logic.DataHelper;
import acp.example.myapplication2.Logic.Global;
import acp.example.myapplication2.Logic.IncLisIngItem;
import acp.example.myapplication2.Logic.IncLisPreItem;
import acp.example.myapplication2.Logic.ListarIngredientes1;
import acp.example.myapplication2.Logic.ListarPreparo1;
import acp.example.myapplication2.Model.Ingredientes;
import acp.example.myapplication2.Model.ModoPreparo;
import acp.example.myapplication2.Model.Receitas;
import io.realm.Realm;

public class IncluirReceitas extends AppCompatActivity
        implements Frag_Busca_dialog.OnFotoListener,
        Frag_Ingre_dialog.OnIngreListener,
        Frag_Prepa_dialog.OnPrepaListener {

    private static final String TAG = "IncluirReceitas";
    private final int TIRAR_FOTO = 1;
    private final int GALERIA_IMAGENS = 2;
    private final int PERMISSAO_REQUEST = 3;
    private final int CAMERA = 4;
    private int nextId_rec, nextId_ing, nextId_mod;
    String tipo_rec, fotoString;
    int id_rec;
    private EditText desc, temp, rend;
    String mqua, mmed, mdes, mprepa;
    private ImageView foto;
    private Boolean OK = null;
    private RecyclerView rcvIngre, rcvModo;
    private ListarIngredientes1 ingreAdapter1;
    private ListarPreparo1 prepaAdapter1;
    private ArrayList<IncLisIngItem> mlistIngre;
    private ArrayList<IncLisPreItem> mlistPrepa;

    private Realm realm;
    Configuracao application;
    Global global = new Global();
    Bitmap imagemGaleria;

    public void sendFoto(String nfoto) {
        fotoString = nfoto;
        Bitmap decodedImage = global.txtTOimg(fotoString);
        foto.setImageBitmap(decodedImage);
    }

    public void sendIngre(String qua, String med, String des) {
        mqua = qua;
        mmed = med;
        mdes = des;
        Number idNum = realm.where(Ingredientes.class).max("id_ing");
        if (idNum == null) {
            nextId_ing = 1;
        } else {
            nextId_ing = idNum.intValue() + 1;
        }
        OK = DataHelper.novoIngrediente(realm, nextId_ing, nextId_rec,
                mqua, mmed, mdes);
        if (!OK) {
            mostrarToast("AVISO", "Ingrediente NÃO Salvo");
        }
        rcvIngreMostrar(mqua, mmed, mdes);
    }

    public void sendPrepa(String prepa) {
        mprepa = prepa;
        Number idNum = realm.where(ModoPreparo.class).max("id_mod");
        if (idNum == null) {
            nextId_mod = 1;
        } else {
            nextId_mod = idNum.intValue() + 1;
        }
        OK = DataHelper.novoPreparo(realm, nextId_mod, nextId_rec, mprepa);
        if (!OK) {
            mostrarToast("AVISO", "Modo de preparo NÃO Salvo");
        }
        rcvPrepaMostrar(mprepa);
    }

   /* @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @SuppressLint("SourceLockedOrientationActivity")*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.MyTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incluir_receitas);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);

        application = (Configuracao) getApplication();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        Log.d(TAG, "Iniciando tela...");
        Intent intent = getIntent();
        tipo_rec = intent.getStringExtra("TIP_REC");

        mlistIngre = new ArrayList<>();
        mlistPrepa = new ArrayList<>();

        desc = findViewById(R.id.edTxdes_rec);
        foto = findViewById(R.id.imgVimg_rec);
        temp = findViewById(R.id.edTxtem_rec);
        rend = findViewById(R.id.edTxren_rec);

        desc.requestFocus();

        foto.setImageResource(R.drawable.ic_menu_camera);
        foto.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Preparando camera.");
                if (verificaPermissao()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(IncluirReceitas.this);
                    builder.setTitle("AVISO");
                    builder.setMessage("De onde virá a imagem?");
                    builder.setPositiveButton("Nova Imagem", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                startActivityForResult(takePictureIntent, TIRAR_FOTO);
                            }
                        }
                    });
                    builder.setNegativeButton("Buscar na galeria", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore
                                    .Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, GALERIA_IMAGENS);
                        }
                    });
                    builder.setNeutralButton("Buscar na Internet", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            Frag_Busca_dialog dialog = new Frag_Busca_dialog();
                            dialog.show(getSupportFragmentManager(), "Imagem da Internet");
                        }
                    });
                    AlertDialog alerta = builder.create();
                    alerta.show();
                }
            }
        });

        realm = Realm.getDefaultInstance();
        Number idNum = realm.where(Receitas.class).max("id_rec");
        if (idNum == null) {
            nextId_rec = 1;
        } else {
            nextId_rec = idNum.intValue() + 1;
        }
        realm.close();

        LinearLayout lnlAddIngre = findViewById(R.id.lnladdIngre);
        LinearLayout lnlAddPrepa = findViewById(R.id.lnlAddPrep);

        rcvIngre = findViewById(R.id.rcvIngredientes);
        rcvModo = findViewById(R.id.rcvModoPreparo);

        lnlAddIngre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (desc.getText().length() == 0) {
                    mostrarToast("AVISO", "Nome da receita não pode estar vazio!");
                    desc.requestFocus();
                } else {
                    Frag_Ingre_dialog dialog1 = new Frag_Ingre_dialog();
                    dialog1.show(getSupportFragmentManager(), "Incluir ingredientes");
                }
            }
        });

        lnlAddPrepa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (desc.getText().length() == 0) {
                    mostrarToast("AVISO", "Nome da receita não pode estar vazio!");
                    desc.requestFocus();
                } else {
                    Frag_Prepa_dialog dialog2 = new Frag_Prepa_dialog();
                    dialog2.show(getSupportFragmentManager(), "Incluir modo de preparo");
                }
            }
        });
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

    private void rcvIngreMostrar(String mqua, String mmed, String mdes) {

        mlistIngre.add(new IncLisIngItem(nextId_ing, mqua, mmed, mdes));
        ingreAdapter1 = new ListarIngredientes1(mlistIngre, this);
        rcvIngre.setHasFixedSize(true);
        rcvIngre.setLayoutManager(new LinearLayoutManager(this));
        rcvIngre.setAdapter(ingreAdapter1);
        rcvIngre.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

    private void rcvPrepaMostrar(String mprepa) {

        mlistPrepa.add(new IncLisPreItem(nextId_mod, mprepa));
        prepaAdapter1 = new ListarPreparo1(mlistPrepa, this);
        rcvModo.setHasFixedSize(true);
        rcvModo.setLayoutManager(new LinearLayoutManager(this));
        rcvModo.setAdapter(prepaAdapter1);
        rcvModo.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_incluir_receitas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.salvarI:
                if (desc.getText().length() == 0) {
                    mostrarToast("AVISO", "Nome da Receita não pode estar vazio!");
                    desc.requestFocus();
                } else {
                    if (fotoString == null) {
                        mostrarToast("AVISO", "Imagem da receita não pode estar vazia!");
                        foto.requestFocus();
                    } else {
                        mostrarToast("Aviso", tipo_rec);
                        if (tipo_rec != null) {

                            OK = DataHelper.novaReceita(realm, nextId_rec, tipo_rec, desc.getText().toString(), fotoString, temp.getText().toString(), rend.getText().toString());
                            if (OK) {
                                mostrarToast("AVISO", "Receita salva!");
                                limparCampos();
                            } else {
                                mostrarToast("AVISO", "Receita não salva!");
                            }
                            Intent intent = new Intent(IncluirReceitas.this, Listar_Receitas.class);
                            intent.putExtra("TIP_REC", tipo_rec);
                            startActivity(intent);
                        }
                    }
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void limparCampos() {
        desc.setText("");
        foto.setImageResource(R.drawable.ic_menu_camera);
        temp.setText("");
        rend.setText("");
        rcvIngre.setAdapter(null);
        rcvModo.setAdapter(null);
        desc.requestFocus();
    }

    public void mostrarToast(String text1, String text2) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.layout_base));
        TextView txt1 = layout.findViewById(R.id.txtToast1);
        TextView txt2 = layout.findViewById(R.id.txtToast2);
        ImageView img1 = layout.findViewById(R.id.imgToast);

        txt1.setText(text1);
        txt2.setText(text2);
        Glide.with(getBaseContext()).load(R.drawable.ic_baseline_perm_device_information_24)
                .apply(RequestOptions.circleCropTransform()).into(img1);
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TIRAR_FOTO && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            fotoString = global.imgTOtxt(imageBitmap);
            Bitmap decodedImage = global.txtTOimg(fotoString);
            exibirImagem(decodedImage);
        }

        if (requestCode == GALERIA_IMAGENS && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            imagemGaleria = (BitmapFactory.decodeFile(picturePath));
            fotoString = global.imgTOtxt(imagemGaleria);
            exibirImagem(imagemGaleria);
        }
    }

    private void exibirImagem(Bitmap image) {
        int targetW = image.getWidth();
        int targetH = image.getHeight();

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        foto.setImageBitmap(image);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (application.save == true) {
            Intent intent = new Intent(IncluirReceitas.this, Listar_Receitas.class);
            intent.putExtra("TIP_REC", tipo_rec);
            intent.putExtra("ID_REC", id_rec);
            startActivity(intent);
        } else if (application.save == false) {
            Intent intent = new Intent(IncluirReceitas.this, MainActivity.class);
            startActivity(intent);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}


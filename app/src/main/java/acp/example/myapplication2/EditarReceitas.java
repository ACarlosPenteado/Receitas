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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import acp.example.myapplication2.Fragmentos.Frag_Busca_dialog;
import acp.example.myapplication2.Fragmentos.Frag_Ingre_dialog;
import acp.example.myapplication2.Fragmentos.Frag_Ingre_dialog1;
import acp.example.myapplication2.Fragmentos.Frag_Prepa_dialog;
import acp.example.myapplication2.Fragmentos.Frag_Prepa_dialog1;
import acp.example.myapplication2.Logic.DataHelper;
import acp.example.myapplication2.Logic.Global;
import acp.example.myapplication2.Logic.IncLisIngItem;
import acp.example.myapplication2.Logic.IncLisPreItem;
import acp.example.myapplication2.Logic.ListarIngredientes1;
import acp.example.myapplication2.Logic.ListarPreparo1;
import acp.example.myapplication2.Logic.MostraListaPreparo;
import acp.example.myapplication2.Logic.MostrarListaIngre;
import acp.example.myapplication2.Logic.SharedViewModel;
import acp.example.myapplication2.Model.Ingredientes;
import acp.example.myapplication2.Model.ModoPreparo;
import acp.example.myapplication2.Model.Receitas;
import io.realm.Realm;

public class EditarReceitas extends AppCompatActivity
        implements Frag_Busca_dialog.OnFotoListener,
        Frag_Ingre_dialog.OnIngreListener,
        Frag_Prepa_dialog.OnPrepaListener,
        Frag_Ingre_dialog1.OnIngreListener1,
        Frag_Prepa_dialog1.OnPrepaListener1 {

    private static final String CATEGORIA = "Script";
    private SharedViewModel editarViewModel;
    private static final String TAG = "EditarReceitas";
    private final int TIRAR_FOTO = 1;
    private final int GALERIA_IMAGENS = 2;
    private final int PERMISSAO_REQUEST = 3;
    private final int CAMERA = 4;
    private int nextId_ing, nextId_mod;
    String tipo_rec, fotoString;
    private int mid, id_rec;
    private EditText desc, temp, rend;
    String mqua, mmed, mdes, mprepa;
    private ImageView foto;
    private Boolean OK = true;
    private RecyclerView rcvIngre, rcvModo;
    private LayoutManager layoutManager;
    private Receitas listaReceitas;
    private ListarIngredientes1 ingreAdapter1;
    private ListarIngredientes1 ingredientes1;
    private ListarPreparo1 prepAdapter1;
    private ListarPreparo1 preparo1;
    private MostrarListaIngre ingreAdapter;
    private MostraListaPreparo prepAdapter;
    private ArrayList<IncLisIngItem> listIngre;
    private ArrayList<IncLisPreItem> listPrepa;

    private Realm realm;
    Global global = new Global();
    Bitmap imagemGaleria;
    Animation myAnim;

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
        OK = DataHelper.novoIngrediente(realm, nextId_ing, id_rec,
                mqua, mmed, mdes);
        if (!OK) {
            mostrarToast("AVISO", "Ingrediente NÃO Salvo");
        }
        rcvIngreMostrar();
    }

    public void alt_Ingre(int id_ing, String qua_ing, String med_ing, String des_ing) {
        mid = id_ing;
        mqua = qua_ing;
        mmed = med_ing;
        mdes = des_ing;
        OK = DataHelper.alteraIngre(realm, mid, mqua, mmed, mdes);
        if (OK) {
            rcvIngreMostrar1();
        } else {
            mostrarToast("AVISO", "Ingrediente NÃO Alterado");
        }
    }

    public void apagaIngre(int id_ing) {
        mid = id_ing;
        OK = DataHelper.apagaIngre(realm, mid);
        if (OK) {
            rcvIngreMostrar1();
        } else {
            mostrarToast("AVISO", "Ingrediente NÃO Apagado");
        }
    }

    public void sendPrepa(String prepa) {
        mprepa = prepa;
        Number idNum = realm.where(ModoPreparo.class).max("id_mod");
        if (idNum == null) {
            nextId_mod = 1;
        } else {
            nextId_mod = idNum.intValue() + 1;
        }
        OK = DataHelper.novoPreparo(realm, nextId_mod, id_rec,
                mprepa);
        if (!OK) {
            mostrarToast("AVISO", "Modo de preparo NÃO Salvo");
        }
        rcvPrepaMostrar();
    }

    public void alt_Prepa(int id_mod, String des_mod) {
        mid = id_mod;
        mdes = des_mod;
        OK = DataHelper.alteraPrepa(realm, mid, mdes);
        if (OK) {
            rcvPrepaMostrar1();
        } else {
            mostrarToast("AVISO", "Modo de Preparo NÃO Alterado");
        }
    }

    public void apagaPrepa(int id_mod) {
        mid = id_mod;
        OK = DataHelper.apagaPrepa(realm, id_mod);
        if (OK) {
            rcvPrepaMostrar1();
        } else {
            mostrarToast("AVISO", "Modo de Preparo NÃO Apagado");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.MyTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incluir_receitas);
        Log.i(CATEGORIA, getLocalClassName());

        editarViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        Log.d(TAG, "Iniciando tela de edição...");
        Intent intent = getIntent();
        tipo_rec = intent.getStringExtra("TIP_REC");
        id_rec = intent.getIntExtra("ID_REC", 0);

        listIngre = new ArrayList<>();
        listPrepa = new ArrayList<>();

        desc = findViewById(R.id.edTxdes_rec);
        foto = findViewById(R.id.imgVimg_rec);
        temp = findViewById(R.id.edTxtem_rec);
        rend = findViewById(R.id.edTxren_rec);
        desc.requestFocus();

        carregaReceita(id_rec);

        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        foto.startAnimation(myAnim);
        foto.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Preparando camera.");
                if (verificaPermissao()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditarReceitas.this);
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
                    AlertDialog alerta = builder.create();
                    alerta.show();
                }
            }
        });

        ImageButton imgBtnAddIng = findViewById(R.id.imgBtnAddIng);
        ImageButton imgBtnAddPre = findViewById(R.id.imgBtnAddPre);
        rcvIngre = findViewById(R.id.rcvIngredientes);
        rcvModo = findViewById(R.id.rcvModoPreparo);

        imgBtnAddIng.setOnClickListener(new View.OnClickListener() {
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
        imgBtnAddPre.setOnClickListener(new View.OnClickListener() {
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

    private void rcvIngreMostrar() {

        listIngre.add(new IncLisIngItem(nextId_ing, mqua, mmed, mdes));
        ingreAdapter1 = new ListarIngredientes1(listIngre, this);
        rcvIngre.setHasFixedSize(true);
        rcvIngre.setLayoutManager(new LinearLayoutManager(this));
        rcvIngre.setAdapter(ingreAdapter1);
        rcvIngre.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        ingreAdapter1.setOnItemClickListener(new ListarIngredientes1.OnItemCliclListener() {
            @Override
            public void onItemClick(int position) {
                Frag_Ingre_dialog1 dialog1 = new Frag_Ingre_dialog1();
                editarViewModel.pass_dados_Ing(listIngre.get(position).getMintId(),
                        listIngre.get(position).getMtxtListQtd(),
                        listIngre.get(position).getMtxtLisMed(),
                        listIngre.get(position).getMtxtListIng());
                dialog1.show(getSupportFragmentManager(), "Incluir ingredientes");
            }

        });
    }

    private void rcvPrepaMostrar() {

        listPrepa.add(new IncLisPreItem(nextId_mod, mprepa));
        prepAdapter1 = new ListarPreparo1(listPrepa, this);
        rcvModo.setHasFixedSize(true);
        rcvModo.setLayoutManager(new LinearLayoutManager(this));
        rcvModo.setAdapter(prepAdapter1);
        rcvModo.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        prepAdapter1.setOnItemClickListener(new ListarPreparo1.OnItemCliclListener() {
            @Override
            public void onItemClick(int position) {
                editarViewModel.pass_inc_alt(1);
                Frag_Prepa_dialog1 dialog2 = new Frag_Prepa_dialog1();
                editarViewModel.pass_dados_Mod(listPrepa.get(position).getMintId(), listPrepa.get(position).getMtxtListPrep());
                dialog2.show(getSupportFragmentManager(), "Incluir Modo de Preparo");
            }
        });
    }

    public void carregaReceita(int mid_rec) {
        realm = Realm.getDefaultInstance();
        listaReceitas = realm.where(Receitas.class)
                .equalTo("id_rec", mid_rec).findFirst();

        desc.setText(listaReceitas.getDes_rec());
        temp.setText(listaReceitas.getTem_rec());
        rend.setText(listaReceitas.getRen_rec());
        fotoString = listaReceitas.getImg_rec();
        desc.requestFocus();
        if (fotoString.equals("foto")) {
            foto.setImageResource(R.drawable.ic_menu_camera);
        } else {
            Bitmap decodedImage = global.txtTOimg(fotoString);
            foto.setImageBitmap(decodedImage);
        }
        //realm.close();
        rcvIngreMostrar1();
        rcvPrepaMostrar1();
    }

    private void rcvIngreMostrar1() {
        int i;
        listIngre = new ArrayList<>();
        //realm = Realm.getDefaultInstance();
        ingreAdapter = new MostrarListaIngre(realm.where(Ingredientes.class)
                .equalTo("id_irec", id_rec).findAll());
        for (i = 0; i < ingreAdapter.getItemCount(); i++) {
            listIngre.add(new IncLisIngItem(ingreAdapter.getItem(i).getId_ing(),
                    ingreAdapter.getItem(i).getQua_ing(),
                    ingreAdapter.getItem(i).getMed_ing(),
                    ingreAdapter.getItem(i).getDes_ing()));
        }
        ingredientes1 = new ListarIngredientes1(listIngre, this);
        rcvIngre = findViewById(R.id.rcvIngredientes);
        rcvIngre.setAdapter(null);
        layoutManager = new LinearLayoutManager(this);
        rcvIngre.setHasFixedSize(true);
        rcvIngre.setLayoutManager(layoutManager);
        rcvIngre.setAdapter(ingredientes1);
        rcvIngre.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        ingredientes1.setOnItemClickListener(new ListarIngredientes1.OnItemCliclListener() {
            @Override
            public void onItemClick(int position) {
                editarViewModel.pass_inc_alt(2);
                Frag_Ingre_dialog1 dialog1 = new Frag_Ingre_dialog1();
                editarViewModel.pass_dados_Ing(ingreAdapter.getItem(position).getId_ing(),
                        ingreAdapter.getItem(position).getQua_ing(),
                        ingreAdapter.getItem(position).getMed_ing(),
                        ingreAdapter.getItem(position).getDes_ing());
                dialog1.show(getSupportFragmentManager(), "Incluir ingredientes");
            }

        });
        //realm.close();
    }

    private void rcvPrepaMostrar1() {
        int i;
        listPrepa = new ArrayList<>();
        //realm = Realm.getDefaultInstance();
        prepAdapter = new MostraListaPreparo(realm.where(ModoPreparo.class)
                .equalTo("id_mrec", id_rec).findAll());
        for (i = 0; i < prepAdapter.getItemCount(); i++) {
            listPrepa.add(new IncLisPreItem(prepAdapter.getItem(i).getId_mod(), prepAdapter.getItem(i).getDes_mod()));
        }
        preparo1 = new ListarPreparo1(listPrepa, this);
        rcvModo = findViewById(R.id.rcvModoPreparo);
        rcvModo.setAdapter(null);
        layoutManager = new LinearLayoutManager(this);
        rcvModo.setHasFixedSize(true);
        rcvModo.setLayoutManager(layoutManager);
        rcvModo.setAdapter(preparo1);
        rcvModo.addItemDecoration(new DividerItemDecoration(getApplication(), DividerItemDecoration.VERTICAL));
        preparo1.setOnItemClickListener(new ListarPreparo1.OnItemCliclListener() {
            @Override
            public void onItemClick(int position) {
                editarViewModel.pass_inc_alt(2);
                Frag_Prepa_dialog1 dialog2 = new Frag_Prepa_dialog1();
                editarViewModel.pass_dados_Mod(prepAdapter.getItem(position).getId_mod(), prepAdapter.getItem(position).getDes_mod());
                dialog2.show(getSupportFragmentManager(), "Incluir Modo de Preparo");
            }
        });
        //realm.close();
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
                        realm = Realm.getDefaultInstance();
                        if (tipo_rec != null) {
                            OK = DataHelper.alteraReceita(realm, id_rec, desc.getText().toString(), fotoString, temp.getText().toString(), rend.getText().toString());
                            mostrarToast(desc.getText().toString(), String.valueOf(OK));
                            if (OK) {
                                mostrarToast("AVISO", "Receita alterada!");
                            } else {
                                mostrarToast("AVISO", "Receita não alterada!");
                            }
                            Intent intent = new Intent(this, Listar_Receitas.class);
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

    /*private void limparCampos() {
        desc.setText("");
        foto.setImageResource(R.drawable.ic_menu_camera);
        temp.setText("");
        rend.setText("");
        rcvIngre.setAdapter(null);
        rcvModo.setAdapter(null);
        desc.requestFocus();
    }*/

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TIRAR_FOTO && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            fotoString = global.imgTOtxt(imageBitmap);
            Bitmap decodedImage = global.txtTOimg(fotoString);
            //foto.setImageBitmap(decodedImage);
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
        Intent intent = new Intent(EditarReceitas.this, Mostrar_Receitas.class);
        intent.putExtra("TIP_REC", tipo_rec);
        intent.putExtra("ID_REC", id_rec);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}


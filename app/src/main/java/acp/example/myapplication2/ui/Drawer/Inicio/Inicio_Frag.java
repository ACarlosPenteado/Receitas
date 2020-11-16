package acp.example.myapplication2.ui.Drawer.Inicio;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterViewFlipper;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

import acp.example.myapplication2.Listar_Receitas;
import acp.example.myapplication2.Logic.FlipperAdapterS;
import acp.example.myapplication2.Logic.FlipperAdapterI;
import acp.example.myapplication2.Logic.Global;
import acp.example.myapplication2.Model.Receitas;
import acp.example.myapplication2.R;
import io.realm.Realm;
import io.realm.RealmResults;

public class Inicio_Frag extends Fragment {

    private AdapterViewFlipper adapterViewFlipper;
    private static ArrayList<String> novoTxt = new ArrayList<String>();
    private static ArrayList<Bitmap> novaImg = new ArrayList<Bitmap>();
    private static final int[] novaImg2 = { R.drawable.doces, R.drawable.salgadas };

    Realm realm;
    Global global = new Global();
    LinearLayout llDoces, llSalgadas;
    ImageButton btnDoces, btnSalgadas;
    Animation rotate, bounce;
    Animation blink_anim, mixed_anim;

    public Inicio_Frag(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frag_inicio, container, false);
        realm = Realm.getDefaultInstance();
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 50;
        RealmResults<Receitas> listReceitas = realm.where(Receitas.class).findAll();
        if (listReceitas.isEmpty()) {
            adapterViewFlipper = root.findViewById(R.id.viewFlipperI);
            FlipperAdapterI flipperAdapter = new FlipperAdapterI(getContext(), novaImg2/*, novoTxt*/);
            adapterViewFlipper.setAdapter(flipperAdapter);
            adapterViewFlipper.setFlipInterval(10000);
            adapterViewFlipper.setTransitionName("rotate");
            adapterViewFlipper.setAutoStart(true);

        } else {
            for (int i = 0; i < listReceitas.size(); i++) {
                if (novaImg != null) {
                    novoTxt.add(carregaTextFlipper(listReceitas.get(i).getDes_rec()));
                    novaImg.add(carregaImageFlipper(listReceitas.get(i).getImg_rec()));
                }
            }
            adapterViewFlipper = root.findViewById(R.id.viewFlipperI);
            FlipperAdapterS flipperAdapter = new FlipperAdapterS(getContext(), novoTxt, novaImg);
            adapterViewFlipper.setAdapter(flipperAdapter);
            adapterViewFlipper.setAutoStart(true);
            adapterViewFlipper.setFlipInterval(1000);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                adapterViewFlipper.setElevation(4);
            }
            adapterViewFlipper.startFlipping();
        }


        btnDoces = root.findViewById(R.id.imgBtnDoce);
        btnDoces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Listar_Receitas.class);
                intent.putExtra("TIP_REC", "Doces");
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        btnSalgadas = root.findViewById(R.id.imgBtnSalgada);
        btnSalgadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Listar_Receitas.class);
                intent.putExtra("TIP_REC", "Salgadas");
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        ShimmerFrameLayout container1 =
                (ShimmerFrameLayout) root.findViewById(R.id.shimmer_view_container1);
        container1.startShimmer();
        ShimmerFrameLayout container2 =
                (ShimmerFrameLayout) root.findViewById(R.id.shimmer_view_container2);
        container2.startShimmer();

        rotate = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);
        bounce = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce);
        blink_anim = AnimationUtils.loadAnimation(getActivity(), R.anim.blink_anim);
        mixed_anim = AnimationUtils.loadAnimation(getActivity(), R.anim.mixed_anim);

        llDoces = root.findViewById(R.id.llDoces);
        llSalgadas = root.findViewById(R.id.llSalgadas);
        //        llDoces.setAnimation(blink_anim);
        //        llSalgadas.setAnimation(blink_anim);

        return root;
    }

    private String carregaTextFlipper(String txt) {
        String nova;
        nova = txt;
        return nova;
    }

    private Bitmap carregaImageFlipper(String txtimg) {
        Bitmap bmp = global.txtTOimg(txtimg);
        Bitmap nova = tamanhoImagem(bmp, 600, 500);
        return nova;
    }

    public Bitmap tamanhoImagem(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

}

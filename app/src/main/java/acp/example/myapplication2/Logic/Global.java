package acp.example.myapplication2.Logic;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;

import acp.example.myapplication2.IncluirReceitas;
import acp.example.myapplication2.R;

public class Global extends Application {

    ByteArrayOutputStream baos;
    Bitmap bitmap;
    byte[] imageBytes;
    byte[] imageBytes2;
    public boolean save;

    public String imgTOtxt( Bitmap ftbit ){

        baos = new ByteArrayOutputStream();
        ftbit.compress(Bitmap.CompressFormat.PNG, 100, baos);
        imageBytes = baos.toByteArray();
        String fotoString = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);

        return fotoString;
    }
    public Bitmap txtTOimg( String ftstr ){

        imageBytes2 = Base64.decode(ftstr, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes2, 0,
                imageBytes2.length);

        return decodedImage;
    }

}

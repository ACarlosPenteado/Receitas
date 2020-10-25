package acp.example.myapplication2.ui.Drawer.Backup;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import acp.example.myapplication2.Fragmentos.Frag_dialog_deleta_receita;
import acp.example.myapplication2.Fragmentos.Frag_dialog_restore;
import acp.example.myapplication2.Logic.RealmMigration;
import acp.example.myapplication2.R;
import io.realm.Realm;

public class Backup_Frag extends Fragment implements Frag_dialog_restore.OnRestoreListener {

    private File EXPORT_REALM_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    private String EXPORT_REALM_FILE_NAME = "Receitas.realm";
    private String IMPORT_REALM_FILE_NAME = "Receitas_bkp.realm";

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private final static String TAG = Backup_Frag.class.getName();

    private RealmMigration rm;
    private Activity activity;
    private Realm realm;
    private Boolean ok;

    @Override
    public void confirmaRestore(Boolean confirma) {
        ok = confirma;
        if( ok ) {
            rm.restore();
        }
    }

    public Backup_Frag() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frag_backup, container, false);

        rm = new RealmMigration(getActivity());
        Button btnBkp = root.findViewById(R.id.btn_backup);
        Button btnRst = root.findViewById(R.id.btn_Restore);

        btnBkp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkStoragePermissions(getActivity());
                rm.backup();
            }
        });

        btnRst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Frag_dialog_restore dialog1 = new Frag_dialog_restore();
                dialog1.show(getActivity().getSupportFragmentManager(), "Restaurar Arquivo");
            }
        });

        return root;
    }

    private void checkStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

}
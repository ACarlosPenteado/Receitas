package acp.example.myapplication2.Logic;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import io.realm.Realm;

public class RealmMigration {

    private final static String TAG = RealmMigration.class.getName();

    private Context context;
    private Realm realm;

    public RealmMigration(Context context) {
        this.realm = Realm.getDefaultInstance();
        this.context = context;
    }

    public void backup() {

        File exportRealmFile = null;

        File exportRealmPATH = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        String exportRealmFileName = "Receitas.realm";

        Log.d(TAG, "Realm DB Path = "+realm.getPath());

        // create a backup file
        exportRealmFile = new File(exportRealmPATH, exportRealmFileName);

        // if backup file already exists, delete it
        exportRealmFile.delete();

        // copy current realm to backup file
        realm.writeCopyTo(exportRealmFile);

        String msg =  "Diretório de Backup: "+ context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        Log.d(TAG, msg);

        realm.close();

    }

    public void restore() {

        //Restore
        File exportRealmPATH = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        String FileName = "Receitas.realm";

        String restoreFilePath = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/"+FileName;

        Log.d(TAG, "oldFilePath = " + restoreFilePath);

        copyBundledRealmFile(restoreFilePath, FileName);

        String msg =  "Diretório de Restore: "+ realm.getPath();
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

        Log.d(TAG, "Arquivo restaurado");

    }

    private String copyBundledRealmFile(String oldFilePath, String outFileName) {
        try {
            File file = new File(context.getFilesDir(), outFileName);

            Log.d(TAG, "context.getFilesDir() = " + context.getFilesDir().toString());
            FileOutputStream outputStream = new FileOutputStream(file);

            FileInputStream inputStream = new FileInputStream(new File(oldFilePath));

            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, bytesRead);
            }
            outputStream.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String dbPath(){

        return realm.getPath();
    }
}

package acp.example.myapplication2.Model;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

public class ModoPreparo extends RealmObject {

    @PrimaryKey
    private int id_mod;
    private int id_mrec;
    private String des_mod;

    public ModoPreparo() {
    }

    public ModoPreparo(int id_mod, int id_mrec, String des_mod) {
        this.id_mod = id_mod;
        this.id_mrec = id_mrec;
        this.des_mod = des_mod;
    }

    public int getId_mrec() {
        return id_mrec;
    }

    public void setId_mrec(int id_mrec) {
        this.id_mrec = id_mrec;
    }

    public int getId_mod() {
        return id_mod;
    }

    public void setId_mod(int id_mod) {
        this.id_mod = id_mod;
    }

    public String getDes_mod() {
        return des_mod;
    }

    public void setDes_mod(String des_mod) {
        this.des_mod = des_mod;
    }


}

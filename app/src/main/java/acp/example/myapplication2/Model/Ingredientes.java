package acp.example.myapplication2.Model;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

public class Ingredientes extends RealmObject {

    @PrimaryKey
    private int id_ing;
    private int id_irec;
    private String qua_ing;
    private String med_ing;
    private String des_ing;

    public Ingredientes() {
    }

    public Ingredientes(int id_ing, int id_irec, String qua_ing, String med_ing, String des_ing) {
        this.id_ing = id_ing;
        this.id_irec = id_irec;
        this.qua_ing = qua_ing;
        this.med_ing = med_ing;
        this.des_ing = des_ing;
    }

    public int getId_irec() {
        return id_irec;
    }

    public void setId_irec( int id_irec) {
        this.id_irec = id_irec;
    }

    public int getId_ing() {
        return id_ing;
    }

    public void setId_ing(int id_ing) {
        this.id_ing = id_ing;
    }

    public String getQua_ing() {
        return qua_ing;
    }

    public void setQua_ing(String qua_ing) {
        this.qua_ing = qua_ing;
    }

    public String getMed_ing() {
        return med_ing;
    }

    public void setMed_ing(String med_ing) {
        this.med_ing = med_ing;
    }

    public String getDes_ing() {
        return des_ing;
    }

    public void setDes_ing(String des_ing) {
        this.des_ing = des_ing;
    }


}

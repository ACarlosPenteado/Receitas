package acp.example.myapplication2.Model;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

public class Receitas extends RealmObject implements Serializable {

    @PrimaryKey
    private int id_rec;
    private String tip_rec;
    private String des_rec;
    private String tem_rec;
    private String ren_rec;
    private String img_rec;

    public Receitas(RealmResults<Receitas> id_rec) {
    }

    public Receitas(){
    }

    public Receitas(int id_rec, String tip_rec, String des_rec, String img_rec, String tem_rec, String ren_rec ) {
        this.id_rec = id_rec;
        this.tip_rec = tip_rec;
        this.des_rec = des_rec;
        this.img_rec = img_rec;
        this.tem_rec = tem_rec;
        this.ren_rec = ren_rec;
    }

    public int getId_rec() {
        return id_rec;
    }

    public void setId_rec(int id_rec) {
        this.id_rec = id_rec;
    }

    public String getTip_rec() {
        return tip_rec;
    }

    public void setTip_rec(String tip_rec) {
        this.tip_rec = tip_rec;
    }

    public String getDes_rec() {
        return des_rec;
    }

    public void setDes_rec(String des_rec) {
        this.des_rec = des_rec;
    }

    public String getImg_rec() {
        return img_rec;
    }

    public void setImg_rec(String img_rec) {
        this.img_rec = img_rec;
    }

    public String getTem_rec() {
        return tem_rec;
    }

    public void setTem_rec(String tem_rec) {
        this.tem_rec = tem_rec;
    }

    public String getRen_rec() {
        return ren_rec;
    }

    public void setRen_rec(String ren_rec) {
        this.ren_rec = ren_rec;
    }

}

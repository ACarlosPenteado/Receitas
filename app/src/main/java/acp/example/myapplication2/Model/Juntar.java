package acp.example.myapplication2.Model;

import io.realm.RealmObject;

public class Juntar {

    private int id_rec;
    private String tip_rec;
    private String des_rec;
    private String tem_rec;
    private String ren_rec;
    private String img_rec;

    private int id_ing;
    private int id_irec;
    private String qua_ing;
    private String med_ing;
    private String des_ing;

    private int id_mod;
    private int id_mrec;
    private String des_mod;
    private int ql;
    private StringBuffer txtAjuda;

    public int getQl() {
        return ql;
    }

    public void setQl(int ql) {
        this.ql = ql;
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

    public String getImg_rec() {
        return img_rec;
    }

    public void setImg_rec(String img_rec) {
        this.img_rec = img_rec;
    }

    public int getId_ing() {
        return id_ing;
    }

    public void setId_ing(int id_ing) {
        this.id_ing = id_ing;
    }

    public int getId_irec() {
        return id_irec;
    }

    public void setId_irec(int id_irec) {
        this.id_irec = id_irec;
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

    public int getId_mod() {
        return id_mod;
    }

    public void setId_mod(int id_mod) {
        this.id_mod = id_mod;
    }

    public int getId_mrec() {
        return id_mrec;
    }

    public void setId_mrec(int id_mrec) {
        this.id_mrec = id_mrec;
    }

    public String getDes_mod() {
        return des_mod;
    }

    public void setDes_mod(String des_mod) {
        this.des_mod = des_mod;
    }

    public StringBuffer getTxtAjuda() {
        return txtAjuda;
    }

    public void setTxtAjuda(StringBuffer txtAjuda) {
        this.txtAjuda = txtAjuda;
    }
}

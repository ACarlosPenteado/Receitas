package acp.example.myapplication2.Logic;

import acp.example.myapplication2.Listar_Receitas;
import acp.example.myapplication2.Model.Receitas;
import io.realm.RealmResults;

public class LisReceitas {
    private int mid_rec;
    private String mtip_rec;
    private String mdes_rec;
    private String mtem_rec;
    private String mren_rec;
    private String mimg_rec;

    public LisReceitas(RealmResults<Receitas> listaReceitas, Listar_Receitas listar_receitas) {
    }

    public LisReceitas(int mid_rec, String mtip_rec, String mdes_rec, String mimg_rec, String mtem_rec, String mren_rec ) {
        this.mid_rec = mid_rec;
        this.mtip_rec = mtip_rec;
        this.mdes_rec = mdes_rec;
        this.mimg_rec = mimg_rec;
        this.mtem_rec = mtem_rec;
        this.mren_rec = mren_rec;
    }

    public int getMid_rec() {
        return mid_rec;
    }

    public void setMid_rec(int mid_rec) {
        this.mid_rec = mid_rec;
    }

    public String getMtip_rec() {
        return mtip_rec;
    }

    public void setMtip_rec(String mtip_rec) {
        this.mtip_rec = mtip_rec;
    }

    public String getMdes_rec() {
        return mdes_rec;
    }

    public void setMdes_rec(String mdes_rec) {
        this.mdes_rec = mdes_rec;
    }

    public String getMtem_rec() {
        return mtem_rec;
    }

    public void setMtem_rec(String mtem_rec) {
        this.mtem_rec = mtem_rec;
    }

    public String getMren_rec() {
        return mren_rec;
    }

    public void setMren_rec(String mren_rec) {
        this.mren_rec = mren_rec;
    }

    public String getMimg_rec() {
        return mimg_rec;
    }

    public void setMimg_rec(String mimg_rec) {
        this.mimg_rec = mimg_rec;
    }
}

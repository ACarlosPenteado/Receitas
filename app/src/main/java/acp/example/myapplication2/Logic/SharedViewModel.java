package acp.example.myapplication2.Logic;

import androidx.lifecycle.ViewModel;
import acp.example.myapplication2.Model.Juntar;

public class SharedViewModel extends ViewModel {

    public Juntar junto = new Juntar();

    public void listaData(String tipo){
        junto.setTip_rec(tipo);
    }

    public void pass_receita( int id_rec, String tip_rec ){
        junto.setId_rec(id_rec);
        junto.setTip_rec(tip_rec);
    }

    public void pass_dados_Ing( int id_ing, String qua_ing, String med_ing, String des_ing){
        junto.setId_ing(id_ing);
        junto.setQua_ing(qua_ing);
        junto.setMed_ing(med_ing);
        junto.setDes_ing(des_ing);
    }

    public void pass_dados_Mod( int id_mod, String des_mod){
        junto.setId_mod(id_mod);
        junto.setDes_mod(des_mod);
    }

    public void pass_text_ajuda( StringBuffer txt_ajuda){
        junto.setTxtAjuda(txt_ajuda);
    }

    public void pass_inc_alt( int ql){
        junto.setQl( ql );
    }


}

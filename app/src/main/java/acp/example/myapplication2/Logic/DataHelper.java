package acp.example.myapplication2.Logic;

import android.widget.Toast;

import java.util.ArrayList;

import acp.example.myapplication2.Model.Ingredientes;
import acp.example.myapplication2.Model.ModoPreparo;
import acp.example.myapplication2.Model.Receitas;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;

public class DataHelper {
    static RealmResults<Receitas> receitas;
    static RealmResults<Ingredientes> ingredientes;
    static RealmResults<ModoPreparo> modoPreparo;
    static Realm realm;
    static Boolean saved = null;

    public DataHelper(Realm realm) {
        this.realm = realm;
    }

    public void selectreceita(String tip_rec){

        receitas = realm.where(Receitas.class)
                .equalTo("tip_rec", tip_rec).findAll();

    }

    public static RealmResults<Receitas> receitasTipo(String tip_rec){

        receitas = realm.where(Receitas.class)
                .equalTo("tip_rec", tip_rec).findAll();

        return receitas;
    }

    public ArrayList<Receitas> justRefresh(){
        ArrayList<Receitas> listReceita = new ArrayList<>();
        for( Receitas r : receitas ){
            listReceita.add(r);
        }
        return listReceita;
    }

    public static Boolean novoIngrediente(Realm realm, int id_ing, int id_irec, String qua_ing, String med_ing, String des_ing ) {
        if( realm == null){
            saved = false;
        } else {
            try{
                realm.beginTransaction();
                Ingredientes ingredientes = realm.createObject(Ingredientes.class, id_ing);
                ingredientes.setId_irec(id_irec);
                ingredientes.setQua_ing(qua_ing);
                ingredientes.setMed_ing(med_ing);
                ingredientes.setDes_ing(des_ing);
                realm.commitTransaction();
                saved = true;
            } catch (RealmException e){
                e.printStackTrace();
                saved = false;
            }
        }
        return saved;
    }

    public static Boolean alteraIngre(Realm realm, int id_ing, String qua_ing, String med_ing, String des_ing) {
        if( realm == null ){
            saved = false;
        } else {
            try{
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmResults<Ingredientes> ingredientes = realm.where(Ingredientes.class)
                                .equalTo("id_ing", id_ing).findAll();
                        ingredientes.setString("qua_ing", qua_ing);
                        ingredientes.setString("med_ing", med_ing);
                        ingredientes.setString("des_ing", des_ing);
                        saved = true;
                    }
                });
            } catch (RealmException e){
                e.printStackTrace();
                saved = false;
            }
        }
        return saved;
    }

    public static Boolean apagaIngre(Realm realm, final  int id){
        if( realm == null){
            saved = false;
        } else {
            try{
                final RealmResults<Ingredientes> ingreItem = realm.where(Ingredientes.class)
                        .equalTo("id_ing", id).findAll();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        if( ingreItem != null ){
                            ingreItem.deleteAllFromRealm();
                        }
                    }
                });
                saved = true;
            } catch (RealmException e){
                e.printStackTrace();
                saved = false;
            }
        }
        return saved;
    }
    public static Boolean apagaIngredientes(Realm realm, final long id) {
        if( realm == null){
            saved = false;
        } else {
            try{
                final RealmResults<Ingredientes> ingreItem = realm.where(Ingredientes.class)
                        .equalTo("id_irec", id).findAll();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                       if( ingreItem != null ){
                           ingreItem.deleteAllFromRealm();
                        }
                    }
                });
                saved = true;
            } catch (RealmException e){
                e.printStackTrace();
                saved = false;
            }
        }
        return saved;
    }

    public static Boolean novoPreparo(Realm realm, int id_mod, int id_mrec, String des_mod){
        if( realm == null){
            saved = false;
        } else {
            try{
                realm.beginTransaction();
                ModoPreparo modoPreparo = realm.createObject(ModoPreparo.class, id_mod);
                modoPreparo.setId_mrec(id_mrec);
                modoPreparo.setDes_mod(des_mod);
                realm.commitTransaction();
                saved = true;
            } catch (RealmException e){
                e.printStackTrace();
                saved = false;
            }
        }
        return saved;
    }

    public static Boolean alteraPrepa(Realm realm, int id_mod, String des_mod) {
        if( realm == null ){
            saved = false;
        } else {
            try{
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmResults<ModoPreparo> modoPreparos = realm.where(ModoPreparo.class)
                                .equalTo("id_mod", id_mod).findAll();
                        modoPreparos.setString("des_mod", des_mod);
                        saved = true;
                    }
                });
            } catch (RealmException e){
                e.printStackTrace();
                saved = false;
            }
        }
        return saved;
    }

    public static Boolean apagaPrepa(Realm realm, final int id) {
        if( realm == null){
            saved = false;
        } else {
            try{
                final RealmResults<ModoPreparo> prepaItem = realm.where(ModoPreparo.class)
                        .equalTo("id_mod", id).findAll();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        if( prepaItem != null ){
                            prepaItem.deleteAllFromRealm();
                        }
                    }
                });
                saved = true;
            } catch (RealmException e){
                e.printStackTrace();
                saved = false;
            }
        }
        return saved;
    }

    public static Boolean apagaPreparos(Realm realm, final long id) {
        if( realm == null){
            saved = false;
        } else {
            try{
                final RealmResults<ModoPreparo> prepaItem = realm.where(ModoPreparo.class)
                        .equalTo("id_mrec", id).findAll();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        if( prepaItem != null ){
                            prepaItem.deleteAllFromRealm();
                        }
                    }
                });
                saved = true;
            } catch (RealmException e){
                e.printStackTrace();
                saved = false;
            }
        }
        return saved;
    }

    public static Boolean novaReceita(Realm realm, int id_rec, String tip_rec, String des_rec, String img_rec, String tem_rec, String ren_rec ) {
        if( realm == null){
            saved = false;
        } else {
            try{
                realm.beginTransaction();
                Receitas receitas = realm.createObject(Receitas.class, id_rec);
                receitas.setTip_rec(tip_rec);
                receitas.setDes_rec(des_rec);
                receitas.setImg_rec(img_rec);
                receitas.setTem_rec(tem_rec);
                receitas.setRen_rec(ren_rec);
                realm.commitTransaction();
                saved = true;
            } catch (RealmException e){
                e.printStackTrace();
                saved = false;
            }
        }
        return saved;
    }

    public static Boolean alteraReceita(Realm realm, int id_rec, String des_rec, String img_rec, String tem_rec, String ren_rec) {

        if( realm == null ){
            saved = false;
        } else {
            try{
                realm.beginTransaction();
                Receitas receitas = realm.where(Receitas.class)
                        .equalTo("id_rec", id_rec).findFirst();
                receitas.setDes_rec(des_rec);
                receitas.setImg_rec(img_rec);
                receitas.setTem_rec(tem_rec);
                receitas.setRen_rec(ren_rec);
                realm.commitTransaction();
                saved = true;
            } catch (RealmException e){
                e.printStackTrace();
                saved = false;
            }
        }
        return saved;
    }

    public static Boolean apagaReceita(Realm realm, final long id) {
        if( realm == null){
            saved = false;
        } else {
            try{
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmResults<Receitas> receitaItem = realm.where(Receitas.class).equalTo("id_rec", id).findAll();
                        if( receitaItem != null ){
                            receitaItem.deleteAllFromRealm();
                        }
                    }
                });
                saved = true;
            } catch (RealmException e){
                e.printStackTrace();
                saved = false;
            }
        }
        return saved;
    }


}

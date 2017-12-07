package dev.com.jongewaard.bbdd_realm.app;

import android.app.Application;

import java.util.concurrent.atomic.AtomicInteger;

import dev.com.jongewaard.bbdd_realm.models.Board;
import dev.com.jongewaard.bbdd_realm.models.Note;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by german on 7-12-17.
 */

public class MyApplication extends Application {

    //el AtomicInteger tiene un metodo implementado que incrementa en uno.
    public static AtomicInteger BoardID = new AtomicInteger();
    public static AtomicInteger NoteID = new AtomicInteger();

    @Override
    public void onCreate() {
        super.onCreate();

        /* Voy a leer de la base de datos cual es el Máximo por cada tabla, cual es el maximo
        valor Id que tenemos. Si la BBDD esta blanca (vacia) vamos a tener un ID Cero! */
        Realm realm = Realm.getDefaultInstance(); //asumo que la BBDD esta configurada
        BoardID = getIdByTable(realm, Board.class); //recojo los datos
        NoteID = getIdByTable(realm, Note.class); //recojo los datos
        realm.close();//cierro la base de datos



    }

    //metodo que hago privado para utilizarlo solo aquí. Este metodo va a trabajar con clases
    // genericas, no sabemos cual es y a la T la extendemos a Realm
    private <T extends RealmObject> AtomicInteger getIdByTable(Realm realm, Class<T> anyClass){
        RealmResults<T> results = realm.where(anyClass).findAll();
        //si hay mas de uno.           dame el maximo fieldName intValue pasamos to do a un tipo entero
                                                                       //AtomicInteger() sin pasar nada es cero!
        return (results.size() > 0)? new AtomicInteger(results.max("id").intValue()): new AtomicInteger();
    }
}

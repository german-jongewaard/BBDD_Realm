package dev.com.jongewaard.bbdd_realm.app;

import android.app.Application;

/**
 * Created by german on 7-12-17.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        /* Voy a leer de la base de datos cual es el Máximo por cada tabla,
            cual es el maximo valor Id que tenemos. Si la BBDD esta blanca (vacia) vamos a tener un ID Cero!*/
    }
}

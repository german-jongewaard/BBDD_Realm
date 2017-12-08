package dev.com.jongewaard.bbdd_realm.models;

import java.util.Date;

import dev.com.jongewaard.bbdd_realm.app.MyApplication;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by german on 7-12-17.
 */

public class Note extends RealmObject {


    @PrimaryKey    //valor semantico a la base de datos
    private int id;
    @Required    //valor semantico a la base de datos
    private String description;
    @Required    //valor semantico a la base de datos
    private Date createAt;

    public Note(){}

    public Note(String description){
        this.id = MyApplication.NoteID.incrementAndGet();
        this.description = description;
        this.createAt = new Date(); //Cada vez que creo una instancia, le paso la fecha del
                                    // mismo segundo en que se crea
    }

    public int getId() { return id; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Date getCreateAt() { return createAt; }
}

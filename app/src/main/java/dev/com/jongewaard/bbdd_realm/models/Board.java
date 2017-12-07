package dev.com.jongewaard.bbdd_realm.models;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by german on 7-12-17.
 */

public class Board extends RealmObject{

    @PrimaryKey    //valor semantico a la base de datos
    private int id;
    @Required    //valor semantico a la base de datos
    private String title;
    @Required    //valor semantico a la base de datos
    private Date createAt;

    private RealmList<Note> notes; //esto ya crea la relacion en nuestra base de datos

    public Board(){}

    public Board(String title){
        this.id = 0;
        this.title = title;
        this.createAt = new Date();
        this.notes = new RealmList<Note>();
    }

    public int getId() { return id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public Date getCreateAt() { return createAt; }

    public RealmList<Note> getNotes() { return notes; }
}

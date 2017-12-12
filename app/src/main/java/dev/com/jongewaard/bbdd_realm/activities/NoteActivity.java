package dev.com.jongewaard.bbdd_realm.activities;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import dev.com.jongewaard.bbdd_realm.R;
import dev.com.jongewaard.bbdd_realm.adapters.BoardAdapter;
import dev.com.jongewaard.bbdd_realm.adapters.NoteAdapter;
import dev.com.jongewaard.bbdd_realm.models.Board;
import dev.com.jongewaard.bbdd_realm.models.Note;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;

public class NoteActivity extends AppCompatActivity implements RealmChangeListener<Board> {

    private Realm realm;

    private FloatingActionButton fab;

    private ListView listView; //Luego de crear el NoteAdapter...
    private NoteAdapter adapter;
    private RealmList<Note> notes;

    private int boardId;
    private Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        // DbRealm
        realm = Realm.getDefaultInstance();

        if(getIntent().getExtras() != null) boardId = getIntent().getExtras().getInt("id");

        board = realm.where(Board.class).equalTo("id", boardId).findFirst();
        board.addChangeListener(this);
        notes = board.getNotes();

        this.setTitle(board.getTitle());

        fab = (FloatingActionButton) findViewById(R.id.fabAddNote);
        listView = (ListView) findViewById(R.id.listViewNote);

        adapter=new NoteAdapter(this, notes, R.layout.list_view_note_item);

        listView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertForCreatingNote("Add new Note", "Type a note for " + board.getTitle() + ".");
            }
        });
     }

            /* CRUD Actions */

    //** CRUD Actions **/ Seccion 7 Clase 80
    private void createNewNote(final String note) {

        realm.beginTransaction();//empieza

        Note _note  = new Note(note);
        realm.copyToRealm(_note);//esta linea guarda la nota en la BBDD
        board.getNotes().add(_note);//aqui añado a la pizarra la nota que he creado

        realm.commitTransaction();//termina

    }

    private void editNote(String newNoteDescription, Note note){
        realm.beginTransaction();//empieza
        note.setDescription(newNoteDescription); //escribe en la nota
        realm.copyToRealmOrUpdate(note); //copia y actualiza la nota
        realm.commitTransaction();//termina
    }

    private void deleteNote(Note note){
        realm.beginTransaction();//empieza
        note.deleteFromRealm();
        realm.commitTransaction();//termina
    }

    private void deleteAll(){
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }


        //** Dioalogs **/

    //elemento usado en MaterialDesign
    private void showAlertForCreatingNote(String title, String message){
        //creo una instancia del Builder, es un Pop-pup que va a tener un Input
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if(title != null) builder.setTitle(title);
        if(message != null) builder.setMessage(message);

        //inflo la vista
        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_create_note, null);
        builder.setView(viewInflated);//le paso la lista para que la embeba

        final EditText input = (EditText) viewInflated.findViewById(R.id.editTextNewNote);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //trim() es para borrar los espacios al final, por si el usuario los dejara
                String note = input.getText().toString().trim();
                if(note.length() > 0)
                    createNewNote(note);

                else
                    Toast.makeText(getApplicationContext(), "The note can't be empty", Toast.LENGTH_LONG).show();
            }
        });
        //aqui lo crea y lo enseña! (a la alerta)
        AlertDialog dialog = builder.create();
        dialog.show();
    }



    @Override
    public void onChange(Board board) {

    }
}

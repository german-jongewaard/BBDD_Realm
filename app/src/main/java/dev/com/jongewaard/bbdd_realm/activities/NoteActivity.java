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
import io.realm.RealmResults;

public class NoteActivity extends AppCompatActivity {

    private Realm realm;

    private FloatingActionButton fab;

    private ListView listView; //Luego de crear el NoteAdapter...
    private NoteAdapter adapter;
    private RealmResults<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);


        // DbRealm
        realm = Realm.getDefaultInstance();
        notes = realm.where(Note.class).findAll();
        notes.addChangeListener((OrderedRealmCollectionChangeListener<RealmResults<Note>>) this);


        adapter = new NoteAdapter(this, notes, R.layout.list_view_note_item);
        listView = (ListView) findViewById(R.id.listViewNote);
        listView.setAdapter(adapter);

        fab = (FloatingActionButton) findViewById(R.id.fabAddNote);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertForCreatingNote("Aa listadd New Note", "Type a name for your new note");
            }
        });
    }

    //** Dioalogs **/
    //elemento usado en MaterialDesign
    private void showAlertForCreatingNote(String title, String message){
        //creo una instancia del Builder, es un Pop-pup que va a tener un Input
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if(title != null) builder.setTitle(title);
        if(message != null) builder.setMessage(message);

        //inflo la vista
        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_create_board, null);
        builder.setView(viewInflated);//le paso la lista para que la embeba

        final EditText input = (EditText) viewInflated.findViewById(R.id.editTextNewBoard);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //trim() es para borrar los espacios al final, por si el usuario los dejara
                String boardName = input.getText().toString().trim();
                if(boardName.length() > 0)
                    //createNew(boardName);
                     System.out.println();
                else
                    Toast.makeText(getApplicationContext(), "The name is required to create a new Board", Toast.LENGTH_SHORT).show();
            }
        });
        //aqui lo crea y lo enseña! (a la alerta)
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

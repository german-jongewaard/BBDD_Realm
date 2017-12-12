package dev.com.jongewaard.bbdd_realm.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

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

        

    }
}

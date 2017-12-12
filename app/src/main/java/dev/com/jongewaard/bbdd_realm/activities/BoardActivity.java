package dev.com.jongewaard.bbdd_realm.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import dev.com.jongewaard.bbdd_realm.R;
import dev.com.jongewaard.bbdd_realm.adapters.BoardAdapter;
import dev.com.jongewaard.bbdd_realm.models.Board;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class BoardActivity extends AppCompatActivity implements RealmChangeListener<Board>, AdapterView.OnItemClickListener{

    private Realm realm;

    private FloatingActionButton fab;

    private ListView listView; //Luego de crear el BoardAdapter...
    private BoardAdapter adapter;
    private RealmResults<Board> boards;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        // DbRealm
        realm = Realm.getDefaultInstance();
        boards = realm.where(Board.class).findAll();
        boards.addChangeListener((OrderedRealmCollectionChangeListener<RealmResults<Board>>) this);




        adapter = new BoardAdapter(this, boards, R.layout.list_view_board_item);
        listView = (ListView) findViewById(R.id.listViewBoard);
        listView.setAdapter(adapter);

        fab = (FloatingActionButton) findViewById(R.id.fabAddBoard);

        //para ejecutar la Alerta llamo al metodo showAlertForCreatingBoard AQUI!!!
        //showAlertForCreatingBoard("title", "message");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertForCreatingBoard("Aa listadd New Board", "Type a name for your new board");
            }
        });
    }


    //** CRUD Actions **/ Seccion 7 Clase 80
    private void createNewBoard(final String boardName) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Board board = new Board(boardName);
                realm.copyToRealm(board);
            }
        });

    }


    //** Dioalogs **/
    //elemento usado en MaterialDesign
    private void showAlertForCreatingBoard(String title, String message){
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
                        createNewBoard(boardName);
                    else
                    Toast.makeText(getApplicationContext(), "The name is required to create a new Board", Toast.LENGTH_SHORT).show();
            }
        });
        //aqui lo crea y lo enseña! (a la alerta)
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onChange(Board board) {
        /* Esto es para refrescar el adaptado, cada vez que se lanza el evento el cual
        conlleva un cambio en la lista del resultado, refresca el adaptador  */
        adapter.notifyDataSetChanged();
    }

    /* Luego de implementar en la clase el: AdapterView.OnItemClickListener. genero el onItemClick
     * para que sea usado para el ListView que tenemos*/
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(BoardActivity.this, NoteActivity.class);
        intent.putExtra("id", boards.get(position).getId());//aquí le paso el Id
        startActivity(intent);
    }
}

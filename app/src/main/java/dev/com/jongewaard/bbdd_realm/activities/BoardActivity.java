package dev.com.jongewaard.bbdd_realm.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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

public class BoardActivity extends AppCompatActivity implements RealmChangeListener<RealmResults<Board>>, AdapterView.OnItemClickListener{

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
        boards.addChangeListener(this);

        adapter = new BoardAdapter(this, boards, R.layout.list_view_board_item);
        listView = (ListView) findViewById(R.id.listViewBoard);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        fab = (FloatingActionButton) findViewById(R.id.fabAddBoard);

        //para ejecutar la Alerta llamo al metodo showAlertForCreatingBoard AQUI!!!
        //showAlertForCreatingBoard("title", "message");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertForCreatingBoard("Aa listadd New Board", "Type a name for your new board");
            }
        });

        /* Borra toda la BBDD
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
        */
        //aqui registro la vista, sin este paso no se puede ver las opciones de borrar y editar
        registerForContextMenu(listView);

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

    //metodo para editar una nota
    private void editBoard(String newName, Board board){
        realm.beginTransaction();
        board.setTitle(newName); //cojo el Titulo
        realm.copyToRealmOrUpdate(board); //copio y lo actualizo
        realm.commitTransaction();

    }

    //metodo para borrar una Nota
    private void deleteBoard(Board board){

        realm.beginTransaction();//Borro toda la BBDD
        board.deleteFromRealm();//con este metodo borramos desde la BBDD
        realm.commitTransaction();
    }

    //metodo para borrar toda la BBDD
    private void deleteAll(){
        realm.beginTransaction();//Borro toda la BBDD
        realm.deleteAll();
        realm.commitTransaction();
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
                //trim() es para borrar l AdapterView.AdapterContextMenuInfo  info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo(); //aqui tengo la información ya..os espacios al final, por si el usuario los dejara
                String boardName = input.getText().toString().trim();
                if(boardName.length() > 0)
                        createNewBoard(boardName);
                    else
                    Toast.makeText(getApplicationContext(), "The name is required to create a new Board", Toast.LENGTH_LONG).show();
            }
        });
        //aqui lo crea y lo enseña! (a la alerta)
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /* Aqui muestro mensaje de la EDICION */
    private void showAlertForEditingBoard(String title, String message, final Board board){
        //creo una instancia del Builder, es un Pop-pup que va a tener un Input
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if(title != null) builder.setTitle(title);
        if(message != null) builder.setMessage(message);

        //inflo la vista
        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_create_board, null);
        builder.setView(viewInflated);//le paso la lista para que la embeba

        final EditText input = (EditText) viewInflated.findViewById(R.id.editTextNewBoard);
        input.setText(board.getTitle()); // cuando voy a editar muestra el texto de lo que quiero editar


        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //trim() es para borrar los espacios al final, por si el usuario los dejara
                String boardName = input.getText().toString().trim();
                if(boardName.length() == 0)
                    Toast.makeText(getApplicationContext(), "The name is required to create a new Board", Toast.LENGTH_LONG).show();
                else if(boardName.equals(board.getTitle()))
                    Toast.makeText(getApplicationContext(), "The name is the same than is was before", Toast.LENGTH_LONG).show();
                else
                    editBoard(boardName, board); //aqui edita


            }
        });
        //aqui lo crea y lo enseña! (a la alerta)
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    /* Events */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_board_activity, menu);

        return super.onCreateOptionsMenu(menu);
    }


    //tengo que sobrescribir el metodo que controlaba los eventos
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.delete_all:
                deleteAll();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            AdapterView.AdapterContextMenuInfo  info = (AdapterView.AdapterContextMenuInfo)menuInfo; //aqui tengo la información ya..
            menu.setHeaderTitle(boards.get(info.position).getTitle()); // aqui paso el titulo
            getMenuInflater().inflate(R.menu.context_menu_board_activity, menu);//aqui lo inflo!
    }


    //aqui manejo los dos eventos, el borrar solamente una nota y el editar una nota.
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo  info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo(); //aqui tengo la información ya..

        switch (item.getItemId()){

            case R.id.delete_board:

                deleteBoard(boards.get(info.position));//aqui ya puedo borrar y esta implementado

                return true;

            case R.id.edit_board:

                showAlertForEditingBoard("Edit Board", "Change the name of the board", boards.get(info.position));

                return true;

            default:
                return super.onContextItemSelected(item);

        }



    }

    @Override
    public void onChange(RealmResults<Board> boards) {
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

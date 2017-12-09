package dev.com.jongewaard.bbdd_realm.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import dev.com.jongewaard.bbdd_realm.R;

public class BoardActivity extends AppCompatActivity {

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        fab = (FloatingActionButton) findViewById(R.id.fabAddBoard);
    }

    //elemento usado en MaterialDesign
    private void showAlertForCreatingBoard(String title, String message){
        //creo una instancia del Builder, es un Pop-pup que va a tener un Input
        AlertDialog.Builder builder = new AlertDialog.Builder(this);




    }
}

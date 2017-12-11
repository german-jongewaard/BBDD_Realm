package dev.com.jongewaard.bbdd_realm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import dev.com.jongewaard.bbdd_realm.R;
import dev.com.jongewaard.bbdd_realm.models.Board;
import dev.com.jongewaard.bbdd_realm.models.Note;

/**
 * Created by german on 11-12-17.
 */

public class BoardAdapter extends BaseAdapter {

    private Context context;
    private List<Board> list;
    private int layout;

    public BoardAdapter(Context context, List<Board> boards, int layout){
        this.context = context;
        this.list = boards;
        this.layout = layout;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Board getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder vh;

        if(convertView == null) {
            //inflo la vista con el layout que nos pasan del constructor
            convertView = LayoutInflater.from(context).inflate(layout,null);
            vh = new ViewHolder();
            vh.title = (TextView) convertView.findViewById(R.id.textViewBoardTitle);
            vh.notes = (TextView) convertView.findViewById(R.id.textViewBoardNotes);
            vh.createdAt = (TextView) convertView.findViewById(R.id.textViewBoardDate);
            //dentro del convertView paso el ViewHolder como un Tag
            convertView.setTag(vh);
        }else {
            //lo casteo a ViewHolder porque estoy seguro que el convertView es un VH y
            // no hace falta hacer un tr catch
            vh = (ViewHolder) convertView.getTag();
        }

        Board board = list.get(position); //lo cargo con los datos de nuestro modelo
        vh.title.setText(board.getTitle());

        int numberOfNotes = board.getNotes().size();//numero de notas que voy a meter (agarro el numero)
        //si el numero de notas es igual a uno, pongo en singular, sino en plural
        String textForNotes = (numberOfNotes == 1) ? numberOfNotes + " Note" : numberOfNotes + " Notes";
        vh.notes.setText(textForNotes);

        //formateo de la fecha!
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String createdAt = df.format(board.getCreateAt());
        vh.createdAt.setText(createdAt);//cojo la fecha y la paso a String

        return convertView;
    }


    public class ViewHolder {
        TextView title;
        TextView notes;
        TextView createdAt;
    }
}

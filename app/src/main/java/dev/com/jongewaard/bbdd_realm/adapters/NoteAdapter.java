package dev.com.jongewaard.bbdd_realm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import dev.com.jongewaard.bbdd_realm.R;
import dev.com.jongewaard.bbdd_realm.models.Note;

/**
 * Created by german on 12-12-17.
 */

public class NoteAdapter extends BaseAdapter {

    private Context context;
    private List<Note> list;
    private int layout;

    public NoteAdapter(Context context, List<Note> notes, int layout) {
        this.context = context;
        this.list = notes;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Note getItem(int position) {
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
            vh.descripcion = (TextView) convertView.findViewById(R.id.textViewNoteDescription);
            vh.createdAt = (TextView) convertView.findViewById(R.id.textViewNotesCreatedAt);
            //dentro del convertView paso el ViewHolder como un Tag
            convertView.setTag(vh);
        }else {
            //lo casteo a ViewHolder porque estoy seguro que el convertView es un VH y
            // no hace falta hacer un tr catch
            vh = (ViewHolder) convertView.getTag();
        }

        //aqui cojo el objeto con el que voy a trabajar, el objeto trabaja en toda nuestra lista
        Note notes = list.get(position); //lo cargo con los datos de nuestro modelo

        vh.descripcion.setText(notes.getDescription());

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy"); //aqui le doy formato a la fecha!
        String date = df.format(notes.getCreateAt());
        vh.createdAt.setText(date);/* como aqui el formato no es adecuado, tengo que transformarlo
                                    y  hacer el paso de arriba*/

        return convertView;
    }


    public class ViewHolder {
        TextView descripcion;
        TextView createdAt;
    }
}

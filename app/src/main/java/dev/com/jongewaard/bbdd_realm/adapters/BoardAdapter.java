package dev.com.jongewaard.bbdd_realm.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

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






        return null;
    }


    public class ViewHolder {
        TextView title;
        TextView notes;
        TextView createdAt;
    }
}

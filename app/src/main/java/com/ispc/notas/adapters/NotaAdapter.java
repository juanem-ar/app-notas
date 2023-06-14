package com.ispc.notas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ispc.notas.R;
import com.ispc.notas.models.Nota;
import com.ispc.notas.services.NotaService;
import com.ispc.notas.utils.DbHelper;

import java.util.List;

public class NotaAdapter extends ArrayAdapter<Nota> {

    private LayoutInflater inflater;
    private NotaService notaService;
    private List<Nota> notas;

    public DbHelper dbHelper;

    public NotaAdapter(Context context, List<Nota> notas) {
        super(context, 0, notas);
        dbHelper = new DbHelper(context);
        inflater = LayoutInflater.from(context);
        notaService = new NotaService(context);
        this.notas = notas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_nota, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tituloTextView = convertView.findViewById(R.id.tituloTextView);
            viewHolder.deleteButton = convertView.findViewById(R.id.deleteButton);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Nota nota = getItem(position);

        if (nota != null) {
            String notaText = nota.getTitle() + "\n" + nota.getDescription();
            viewHolder.tituloTextView.setText(notaText);

            viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eliminarNota(nota);
                }
            });
        }

        return convertView;
    }

    private void eliminarNota(Nota nota) {
        notaService.deleteNota(dbHelper.getWritableDatabase(), nota.getId());
        notas.remove(nota);
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        TextView tituloTextView;
        ImageButton deleteButton;
    }
}

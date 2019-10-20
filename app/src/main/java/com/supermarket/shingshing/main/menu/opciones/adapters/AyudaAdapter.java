package com.supermarket.shingshing.main.menu.opciones.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.supermarket.shingshing.R;
import com.supermarket.shingshing.models.AyudaModel;

import java.util.ArrayList;

public class AyudaAdapter extends BaseExpandableListAdapter {
    private ArrayList<AyudaModel> list;
    private Context context;

    public AyudaAdapter(Context context, ArrayList<AyudaModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getSubtitulos().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition).getTitulo();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getSubtitulos().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String titulo = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_ayuda_titulo, null);
        }

        TextView listTitleTextView = convertView.findViewById(R.id.tvItemAyudaTitulo);
        ImageView iconArrow = convertView.findViewById(R.id.ivItemAyudaArrow);

        listTitleTextView.setText(titulo);
        if (isExpanded) {
            iconArrow.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_arrow_up_gris));
        } else {
            iconArrow.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_arrow_down_gris));
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String subtitulo = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_ayuda_subtitulo, null);
        }

        TextView expandedListTextView = convertView.findViewById(R.id.tvItemAyudaSubtitulo);
        expandedListTextView.setText(subtitulo);

        return convertView;
    }
}

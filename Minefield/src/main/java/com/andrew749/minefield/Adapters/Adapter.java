package com.andrew749.minefield.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.andrew749.minefield.R;

/**
 * Created by andrew on 17/05/13.
 */
public class Adapter extends BaseAdapter {
    int nom;
    static String elementName;
    static LayoutInflater inflater;

    public Adapter(Context context, String nameOfElement, int numberOfElements) {
        nom = numberOfElements;
        elementName = nameOfElement;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();

            view = inflater.inflate(R.layout.item, viewGroup, false);
            holder.image = (ImageView) view.findViewById(R.id.imageButton);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        return view;
    }
}

class ViewHolder {
    ImageView image;
}

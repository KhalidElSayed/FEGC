package com.alorma.megsjc.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alorma.megsjc.R;
import com.alorma.megsjc.data.cursor.AgrupamentsCursor;
import com.alorma.megsjc.data.model.Agrupament;
import com.squareup.picasso.Picasso;

/**
 * Created by Bernat on 7/10/13.
 */
public class AgrupamentsAdapter extends CursorAdapter {

    private final LayoutInflater inflater;

    public AgrupamentsAdapter(Context context) {
        super(context, null, 0);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.row, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView text1 = (TextView) view.findViewById(R.id.textView);
        ImageView image1 = (ImageView) view.findViewById(R.id.imageView);

        Agrupament agrupament = new AgrupamentsCursor().readCursor(cursor);

        text1.setText(agrupament.toString());
    }
}

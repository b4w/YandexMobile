package com.app.mobile.yandex.b4w.yandexmobileapplication.controller.adapters;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;

/**
 * Created by KonstantinSysoev on 17.04.16.
 * <p/>
 * Abstract class for ArtistCursorAdapter.
 */
public abstract class RecyclerViewCursorAdapter<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    private Cursor cursor;

    public void swapCursor(final Cursor cursor) {
        this.cursor = cursor;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.cursor != null
                ? this.cursor.getCount()
                : 0;
    }

    public Cursor getItem(final int position) {
        if (this.cursor != null && !this.cursor.isClosed()) {
            this.cursor.moveToPosition(position);
        }
        return this.cursor;
    }

    public Cursor getCursor() {
        return this.cursor;
    }

    @Override
    public final void onBindViewHolder(final VH holder, final int position) {
        final Cursor cursor = this.getItem(position);
        this.onBindViewHolder(holder, cursor);
    }

    public abstract void onBindViewHolder(final VH holder, final Cursor cursor);
}

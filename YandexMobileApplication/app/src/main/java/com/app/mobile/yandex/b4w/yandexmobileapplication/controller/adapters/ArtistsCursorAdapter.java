package com.app.mobile.yandex.b4w.yandexmobileapplication.controller.adapters;

import android.content.res.Resources;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.mobile.yandex.b4w.yandexmobileapplication.R;
import com.app.mobile.yandex.b4w.yandexmobileapplication.controller.pojo.Artist;
import com.app.mobile.yandex.b4w.yandexmobileapplication.controller.pojo.Cover;
import com.app.mobile.yandex.b4w.yandexmobileapplication.controller.util.StringUtils;
import com.app.mobile.yandex.b4w.yandexmobileapplication.model.db.IDBConstants;
import com.squareup.picasso.Picasso;

/**
 * Created by KonstantinSysoev on 17.04.16.
 * <p/>
 * Artists cursor adapter for display artists list.
 */
public class ArtistsCursorAdapter extends RecyclerViewCursorAdapter<ArtistsCursorAdapter.ViewHolder>
        implements View.OnClickListener {

    private OnItemClickListener onItemClickListener;

    public ArtistsCursorAdapter(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artists_item_layout, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final Cursor cursor) {
        holder.bindData(cursor);
    }

    @Override
    public void onClick(final View view) {
        if (onItemClickListener != null) {
            final RecyclerView recyclerView = (RecyclerView) view.getParent();
            final int position = recyclerView.getChildLayoutPosition(view);
            if (position != RecyclerView.NO_POSITION) {
                final Cursor cursor = this.getItem(position);
                onItemClickListener.onItemClicked(getArtistByCursor(cursor));
            }
        }
    }

    /**
     * Get new obj Artist(); from cursor.
     *
     * @param cursor
     * @return new Artist();
     */
    private Artist getArtistByCursor(final Cursor cursor) {
        return new Artist(cursor.getInt(cursor.getColumnIndex(IDBConstants.ID)),
                cursor.getString(cursor.getColumnIndex(IDBConstants.NAME)),
                (cursor.getString(cursor.getColumnIndex(IDBConstants.GENRES))).split(","),
                cursor.getInt(cursor.getColumnIndex(IDBConstants.ALBUMS)),
                cursor.getInt(cursor.getColumnIndex(IDBConstants.TRACKS)),
                cursor.getString(cursor.getColumnIndex(IDBConstants.LINK)),
                cursor.getString(cursor.getColumnIndex(IDBConstants.DESCRIPTION)),
                new Cover(cursor.getString(cursor.getColumnIndex(IDBConstants.COVER_SMALL)),
                        cursor.getString(cursor.getColumnIndex(IDBConstants.COVER_BIG))));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView coverSmall;
        private TextView name;
        private TextView genres;
        private TextView albums;
        private TextView tracks;

        public ViewHolder(final View itemView) {
            super(itemView);
            coverSmall = (ImageView) itemView.findViewById(R.id.cover_small);
            name = (TextView) itemView.findViewById(R.id.name);
            genres = (TextView) itemView.findViewById(R.id.genres);
            albums = (TextView) itemView.findViewById(R.id.albums);
            tracks = (TextView) itemView.findViewById(R.id.tracks);
        }

        public void bindData(final Cursor cursor) {
            final String cursorCoverSmall = cursor.getString(cursor.getColumnIndex(IDBConstants.COVER_SMALL));
            final String cursorName = cursor.getString(cursor.getColumnIndex(IDBConstants.NAME));
            final String cursorGenres = cursor.getString(cursor.getColumnIndex(IDBConstants.GENRES));
            final int cursorAlbums = cursor.getInt(cursor.getColumnIndex(IDBConstants.ALBUMS));
            final int cursorTracks = cursor.getInt(cursor.getColumnIndex(IDBConstants.TRACKS));

            final Resources resources = itemView.getContext().getResources();
            setCoverSmallImage(coverSmall, cursorCoverSmall);
            name.setText(cursorName);
            genres.setText(cursorGenres);
            albums.setText(String.format(resources.getString(R.string.album_message), cursorAlbums,
                    StringUtils.getWordEnding(cursorAlbums, StringUtils.ALBUMS)));
            tracks.setText(String.format(resources.getString(R.string.track_message), cursorTracks,
                    StringUtils.getWordEnding(cursorTracks, StringUtils.TRACKS)));
        }

        /**
         * Loading of the image from Yandex json link.
         *
         * @param imageView - item imageView.
         * @param imageLink - link to image.
         */
        private void setCoverSmallImage(final ImageView imageView, final String imageLink) {
            if (imageLink != null && !imageLink.isEmpty()) {
                Picasso.with(imageView.getContext())
                        .load(imageLink)
                        .placeholder(R.drawable.load_holder)
                        .into(imageView);
            } else {
                imageView.setImageResource(R.drawable.load_holder);
            }
        }
    }

    /**
     * Interface for callback when click on artist item.
     */
    public interface OnItemClickListener {
        void onItemClicked(Artist artist);
    }
}

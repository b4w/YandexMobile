package com.app.mobile.yandex.b4w.yandexmobileapplication.controller.adapters;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.mobile.yandex.b4w.yandexmobileapplication.controller.util.StringUtils;
import com.app.mobile.yandex.b4w.yandexmobileapplication.controller.pojo.Artist;
import com.app.mobile.yandex.b4w.yandexmobileapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by KonstantinSysoev on 02.04.16.
 * <p/>
 * Adapter for display list of artists.
 */
public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ViewHolder> {

    private List<Artist> artists;

    public ArtistsAdapter(List<Artist> artists) {
        this.artists = artists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artists_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        TODO: не забыть переместить загрузку изображений в отдельный поток, правильное окончание в песнях и альбомах
        final Artist artist = artists.get(position);

        setCoverSmallImage(holder.coverSmall, artist.getCover().getSmall());

        holder.name.setText(artist.getName());
        holder.genres.setText(StringUtils.getStringFromStringArray(artist.getGenres()));

        final Resources resources = holder.tracks.getContext().getResources();
        holder.albums.setText(String.format(resources.getString(R.string.album_message), artist.getAlbums(),
                resources.getString(R.string.albums_genitive)));
        holder.tracks.setText(String.format(resources.getString(R.string.track_message), artist.getTracks(),
                resources.getString(R.string.tracks_genitive)));
    }

    /**
     * Loading of the image from Yandex json link.
     *
     * @param imageView - item imageView.
     * @param imageLink - link to image.
     */
    private void setCoverSmallImage(ImageView imageView, String imageLink) {
        if (imageLink != null && !imageLink.isEmpty()) {
            Picasso.with(imageView.getContext())
                    .load(imageLink)
                    .placeholder(R.drawable.load_holder)
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.load_holder);
        }
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (artists != null)
            size = artists.size();
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // TODO: добавить coverBig для хранения фотографии?
        private ImageView coverSmall;
        private TextView name;
        private TextView genres;
        private TextView albums;
        private TextView tracks;

        public ViewHolder(View itemView) {
            super(itemView);
            coverSmall = (ImageView) itemView.findViewById(R.id.cover_small);
            name = (TextView) itemView.findViewById(R.id.name);
            genres = (TextView) itemView.findViewById(R.id.genres);
            albums = (TextView) itemView.findViewById(R.id.albums);
            tracks = (TextView) itemView.findViewById(R.id.tracks);
        }
    }
}

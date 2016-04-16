package com.app.mobile.yandex.b4w.yandexmobileapplication.ui.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.mobile.yandex.b4w.yandexmobileapplication.R;
import com.app.mobile.yandex.b4w.yandexmobileapplication.model.adapters.ArtistsAdapter;
import com.app.mobile.yandex.b4w.yandexmobileapplication.model.pojo.Artist;
import com.app.mobile.yandex.b4w.yandexmobileapplication.data.IYandexArtistsService;
import com.app.mobile.yandex.b4w.yandexmobileapplication.data.db.SQLiteHelper;
import com.app.mobile.yandex.b4w.yandexmobileapplication.model.util.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by KonstantinSysoev on 05.04.16.
 */
public class ArtistsFragment extends Fragment {

    /**
     * Interface for opening of the complete information on the artist.
     */
    public interface IOpenViewArtistCallback {
        void openViewArtist(Artist artist);
    }

    private static final String TAG = ArtistsFragment.class.getSimpleName();

    private RecyclerView artists;
    private ArtistsAdapter artistsAdapter;
    private List<Artist> artistsList;
    private IOpenViewArtistCallback iOpenViewArtistCallback;

    /**
     * Return new instance ArtistsFragment.
     *
     * @return new ArtistsFragment();
     */
    public static ArtistsFragment getInstance() {
        return new ArtistsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            iOpenViewArtistCallback = (IOpenViewArtistCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement IOpenViewArtistCallback");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_artists, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initListItemsView();
    }

    /**
     * Load tmp data artists list.
     */
    private void loadData() {
        Log.d(TAG, "loadData() started");
        artistsList = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IYandexArtistsService.YA_ARTISTS_LINK)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IYandexArtistsService iYandexArtistsService = retrofit.create(IYandexArtistsService.class);
        Call<List<Artist>> call = iYandexArtistsService.loadArtists();

        call.enqueue(new Callback<List<Artist>>() {
            @Override
            public void onResponse(Call<List<Artist>> call, Response<List<Artist>> response) {
                artistsList = response.body();

                // добавить сохранение в отдельном потоке
                final SQLiteHelper sqLiteHelper = new SQLiteHelper(getActivity().getApplicationContext());
                sqLiteHelper.onUpgrade(sqLiteHelper.getWritableDatabase(), 1, 1);
                sqLiteHelper.insertArtistsInDB(artistsList);
                sqLiteHelper.close();

                artistsAdapter = new ArtistsAdapter(artistsList);
                initListItemsView();
            }

            @Override
            public void onFailure(Call<List<Artist>> call, Throwable t) {
                // TODO: добавить snackbar
                // Snackbar.make(R.layout.fragment_artists, "TEST", Snackbar.LENGTH_LONG).show();
                Log.e(TAG, "Error when request to http json");
            }
        });
        Log.d(TAG, "loadData() done");
    }

    /**
     * Initialize artists view list.
     */
    private void initListItemsView() {
        Log.d(TAG, "initListItemsView() started");
        artists = (RecyclerView) getActivity().findViewById(R.id.artists);
        final LinearLayoutManager manager = new LinearLayoutManager(getActivity().getApplicationContext());
        artists.setLayoutManager(manager);
        artists.setAdapter(artistsAdapter);
        artists.addOnItemTouchListener(new RecyclerItemClickListener(getActivity().getApplicationContext(), new RecyclerItemClickListener.IOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // можно доставать из бд через picasso image и передавать ее здесь через view.findViewById(R.id.cover_big)
                iOpenViewArtistCallback.openViewArtist(artistsList.get(position));
            }
        }));
        Log.d(TAG, "initListItemsView() done");
    }
}

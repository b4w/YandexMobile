package com.app.mobile.yandex.b4w.yandexmobileapplication.model.content;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

/**
 * Created by KonstantinSysoev on 17.04.16.
 */
public class LoaderCallbackCursor implements LoaderManager.LoaderCallbacks<Cursor> {

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//        return new DBCursorLoader(getActivity(), getActivity().getContentResolver());;
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}

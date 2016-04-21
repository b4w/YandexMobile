package com.app.mobile.yandex.b4w.yandexmobileapplication.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewFragment;

import com.app.mobile.yandex.b4w.yandexmobileapplication.model.db.IDBConstants;

/**
 * Created by KonstantinSysoev on 21.04.16.
 * Fragment for opening official site by link in browser.
 */
public class OfficialSiteFragment extends WebViewFragment {
    private static final String TAG = OfficialSiteFragment.class.getSimpleName();

    private String link;

    /**
     * Return new instance OfficialSiteFragment.
     *
     * @return new OfficialSiteFragment();
     */
    public static OfficialSiteFragment getInstance(String link) {
        OfficialSiteFragment fragment = new OfficialSiteFragment();
        Bundle bundle = new Bundle();
        bundle.putString(IDBConstants.LINK, link);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() started");
        View result = super.onCreateView(inflater, container, savedInstanceState);
        // added JavaScript and screen zooming
        getWebView().getSettings().setJavaScriptEnabled(true);
        getWebView().getSettings().setSupportZoom(true);
        getWebView().getSettings().setBuiltInZoomControls(true);
        getWebView().loadUrl(getArguments().getString(IDBConstants.LINK));
        Log.d(TAG, "onCreateView() done");
        return result;
    }
}

package com.app.mobile.yandex.b4w.yandexmobileapplication.view.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.app.mobile.yandex.b4w.yandexmobileapplication.R;
import com.app.mobile.yandex.b4w.yandexmobileapplication.model.db.IDBConstants;

/**
 * Created by KonstantinSysoev on 21.04.16.
 * Fragment for opening official site by link in browser.
 */
public class OfficialSiteFragment extends Fragment {
    private static final String TAG = OfficialSiteFragment.class.getSimpleName();

    private WebView webView;

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
        final View view = inflater.inflate(R.layout.fragment_off_site, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        webView = (WebView) getActivity().findViewById(R.id.off_site_web_view);
        initWebView();
    }

    /**
     * Initialize webView for opening official site.
     */
    private void initWebView() {
        Log.d(TAG, "initWebView() started");
        // open within WebView not a default browser
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        // added JavaScript and screen zooming
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.loadUrl(getArguments().getString(IDBConstants.LINK));
        Log.d(TAG, "initWebView() done");
    }
}

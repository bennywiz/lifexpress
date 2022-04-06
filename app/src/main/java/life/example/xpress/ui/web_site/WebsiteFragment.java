package life.example.xpress.ui.web_site;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import life.example.lifexpress.R;
import life.example.xpress.DataBase.DataHelper;
import life.example.xpress.MainActivity;

public class WebsiteFragment extends Fragment {



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_website, container, false);
        View v1 = inflater.inflate(R.layout.app_bar_main, container, false);

        DataHelper dataHelper = new DataHelper(getActivity());
        if (dataHelper.getlangue().equals("English")) {
            ((MainActivity) getActivity()).getSupportActionBar().setTitle("About us");
        }

        WebView mWebView = v.findViewById(R.id.webView1);
        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient());
        mWebView.getSettings().setUserAgentString("Android");

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        mWebView.loadUrl("https://lifexpress.bi/");


       // ExtendedFloatingActionButton fab = v1.findViewById(R.id.fab);
       // fab.setVisibility(View.GONE);



        return v;
    }
}
package com.ssdi.pricesplash;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;


import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;


class HelloWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

}
public class TabFragment1 extends Fragment {

    WebView webView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tabfragment1, container, false);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        webView = (WebView) view.findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new HelloWebViewClient());
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebViewClient(new WebViewClient(){
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){
                view.loadUrl(request.getUrl().toString());
                return false;
            }
        });
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN){
                    switch (keyCode){
                        case KeyEvent.KEYCODE_BACK:
                            if(webView.canGoBack()){
                                webView.goBack();
                                return true;
                            }
                            break;
                    }
                }
                return false;
            }

        });

        switch(getArguments().getInt("position")){
            case 0:
                webView.loadUrl("https://www.amazon.com/s?k="+getArguments().getString("product_name")+"&ref=nb_sb_noss");
                System.out.println("https://www.amazon.com/s?k="+getArguments().getString("product_name")+"&ref=nb_sb_noss");
                break;
            case 1:

                webView.loadUrl("https://www.bhphotovideo.com/c/search?Ntt="+getArguments().getString("product_name")+"&N=0&InitialSearch=yes&sts=ma&Top+Nav-Search=");
                System.out.println("https://www.bestbuy.com/site/searchpage.jsp?st="+getArguments().getString("product_name")+
                            "&_dyncharset=UTF-8&_dynSessConf=&id=pcat17071&type=page&sc=Global&cp=1&nrp=&sp=&qp=&list=n&af=true&iht=y&usc=All+Categories&ks=960&keys=keys");

                break;
            case 2:
                webView.loadUrl("https://www.walmart.com/search/?cat_id=0&query="+getArguments().getString("product_name"));
                webView.reload();
                System.out.println("https://www.walmart.com/search/?cat_id=0&query="+getArguments().getString("product_name"));
                break;
            case 3:
                webView.loadUrl("https://www.target.com/s?searchTerm="+getArguments().getString("product_name"));
                System.out.println("https://www.target.com/s?searchTerm="+getArguments().getString("product_name"));

                break;

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       //Toast.makeText(getContext(),webView.getUrl(),Toast.LENGTH_SHORT).show();
        //System.out.println(webView.getUrl());
        DatabaseHelper helper = new DatabaseHelper(getContext());
        new MaterialDialog.Builder(getContext())
                .positiveText("Save")
                .negativeText("Discard")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();

                    }
                })
                .titleColorRes(R.color.colorPrimaryDark)
                .title("Give the name to this page in your history.")
                .inputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE)
                .inputType(InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("Name", null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        System.out.println(input.toString());
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        String website = null;
                        switch(getArguments().getInt("position")) {
                            case 0: website = "Amazon";
                                break;
                            case 1: website = "B&H";
                                break;
                            case 2: website = "Walmart";
                                break;
                            case 3: website = "Target";
                                break;
                        }
                        long id = helper.insert(webView.getUrl(),website,dialog.getInputEditText().getText().toString());
                        System.out.println(id);
                    }
                }).show();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_navigation, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}

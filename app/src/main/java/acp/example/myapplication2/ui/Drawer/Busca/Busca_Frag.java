package acp.example.myapplication2.ui.Drawer.Busca;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import acp.example.myapplication2.R;

public class Busca_Frag extends Fragment {

    //private Toolbar toolbar;
    //private AppCompatButton btn_ok;
    private ImageButton btn_back;
    private ImageButton btn_forward;
    private ImageButton btn_home;
    private ImageButton btn_refresh;
    private ImageButton btn_share;
    //private EditText edittext;
    private WebView webview;
    private String Share_url,Title_url;
    private ProgressBar progressbar;

    @Nullable
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.frag_busca, container, false );

        //toolbar = view.findViewById( R.id.toolbarfb);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        /*activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        //btn_ok = view.findViewById(R.id.btn_go);
        btn_back = view.findViewById(R.id.btn_back);
        btn_forward = view.findViewById(R.id.btn_forward);
        btn_home = view.findViewById(R.id.btn_home);
        btn_refresh = view.findViewById(R.id.btn_refresh);
        btn_share = view.findViewById(R.id.btn_share);

        //edittext= view.findViewById(R.id.search_load_edit_text);
        webview= view.findViewById(R.id.webview);
        progressbar= view.findViewById(R.id.progressbar);

        final String txtUrl = "https://www.google.com/search?q=receitas&prmd=ivn&source=lnms&tbm=isch&sa=X&ved=2ahUKEwirnfT3kvXoAhU0IbkGHZuNCDQQ_AUoAXoECAsQAQ&biw=424&bih=592";

        webview.loadUrl(txtUrl);

        webview.getSettings().setJavaScriptEnabled(true);

        webview.setWebChromeClient(new WebChromeClient()
        {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressbar.setProgress(newProgress);
                super.onProgressChanged(view, newProgress);

                if(newProgress==100)
                {
                    progressbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
                //set favicon to imageview
                //imageview.setImageBitmap(icon);
            }
        });

        webview.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                progressbar.setVisibility(View.VISIBLE);

                if(!txtUrl.equals(url))
                {
                    //edittext.setText(url);
                }
                else
                {
                    //edittext.setText("");
                }

                super.onPageStarted(view, url, favicon);
            }
            @Override
            public void onPageFinished(WebView view,String url)
            {
                Share_url=url;
                super.onPageFinished(view,url);
            }



        });

        webview.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(final String url, final String userAgent, String contentDisposition, String mimetype, long contentLength) {

                downloadDialog(url,userAgent,contentDisposition,mimetype);

            }

        });

        /*btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text=edittext.getText().toString();
                searchOrLoad(text);

            }
        });*/

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(webview.canGoBack())
                    webview.goBack();
            }
        });

        btn_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(webview.canGoForward())
                    webview.goForward();
            }
        });

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webview.loadUrl(txtUrl);
            }
        });

        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webview.reload();
            }
        });

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent=new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT,Share_url);
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,"URL");
                startActivity(Intent.createChooser(shareIntent,"Share with your friends"));
            }
        });


        return view;
    }



    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode==KeyEvent.KEYCODE_BACK)&&this.webview.canGoBack())
        {
            webview.goBack();
            return true;
        }

        return super.getActivity().onKeyDown(keyCode, event);
    }

    void searchOrLoad(String txt)
    {
        if(Patterns.WEB_URL.matcher(txt.toLowerCase()).matches())
        {

            if(txt.contains("http://")||txt.contains("https://"))
            {
                webview.loadUrl(txt);
            }
            else
            {
                webview.loadUrl("http://"+txt);
            }
        }
        else
        {
            webview.loadUrl("https://www.google.com/search?q="+txt);
        }
        hideKeyboard();

    }

   @Override
    public void onDetach() {
        super.onDetach();

    }

    public void hideKeyboard()
    {
        InputMethodManager inputMethodManager= (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view1=getActivity().getCurrentFocus();
        inputMethodManager.hideSoftInputFromWindow(view1.getWindowToken(),0);
    }

    public void downloadDialog(final String url,final String userAgent,String contentDisposition,String mimetype)
    {
        //file name
        final String filename = URLUtil.guessFileName(url,contentDisposition,mimetype);

        //Creates AlertDialog.
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        //title of Dialog
        builder.setTitle("Download");
        //Message of Dialog.
        builder.setMessage("Do you want to save " +filename);

        //if YES button clicks
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                //DownloadManager.Request created with url.
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                //cookie
                String cookie= CookieManager.getInstance().getCookie(url);
                //Add cookie and User-Agent to request
                request.addRequestHeader("Cookie",cookie);
                request.addRequestHeader("User-Agent",userAgent);

                //file scanned by MediaScannar
                request.allowScanningByMediaScanner();
                //Download is visible and its progress, after completion too.
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                //DownloadManager created
                DownloadManager downloadManager= (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                //Saving file in Download folder
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
                //download enqued
                downloadManager.enqueue(request);
            }
        });
        //If Cancel button clicks
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                //cancel the dialog if Cancel clicks
                dialog.cancel();
            }

        });

        //Shows alertdialog
        builder.create().show();


    }


}

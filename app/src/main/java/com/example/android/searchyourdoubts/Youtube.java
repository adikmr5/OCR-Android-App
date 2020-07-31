package com.example.android.searchyourdoubts;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Youtube#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Youtube extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String data;
    WebView webView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Youtube() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Youtube.
     */
    // TODO: Rename and change types and number of parameters
    public static Youtube newInstance(String param1, String param2) {
        Youtube fragment = new Youtube();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_youtube, container, false);
        data=Output.getData();
//        textView=view.findViewById(R.id.text);
//        textView.setText(data);
        webView=view.findViewById(R.id.web_youtube);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://m.youtube.com/results?search_query="+data.substring(0,30));

        return view;
    }
}
package com.example.android.ali.myquotes.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.method.ArrowKeyMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.ali.myquotes.R;
import com.example.android.ali.myquotes.utils.AppConstants;


public class QuoteFragment extends Fragment {
    private String quoteText;
    private TextView mQuoteTextView;

    public QuoteFragment() {
        // Required empty public constructor
    }

    public static QuoteFragment newInstance(String quoteText) {
        QuoteFragment fragment = new QuoteFragment();
        Bundle args = new Bundle();
        args.putString(AppConstants.EXTRA_QUOTE_TEXT, quoteText);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            quoteText = getArguments().getString(AppConstants.EXTRA_QUOTE_TEXT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quote, container, false);

        mQuoteTextView = view.findViewById(R.id.tv_quote_text);
        mQuoteTextView.setText(quoteText);
        mQuoteTextView.setMovementMethod(new ArrowKeyMovementMethod());
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}

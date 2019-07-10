package com.example.android.ali.myquotes.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.ali.myquotes.R;
import com.example.android.ali.myquotes.utils.AppConstants;

public class RemarkFragment extends Fragment {
    private String mRemark;
    private TextView mRemarkTextView;
    public RemarkFragment() {
        // Required empty public constructor
    }


    public static RemarkFragment newInstance(String remark) {
        RemarkFragment fragment = new RemarkFragment();
        Bundle args = new Bundle();
        args.putString(AppConstants.EXTRA_QUOTE_REMARK, remark);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRemark = getArguments().getString(AppConstants.EXTRA_QUOTE_REMARK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_remark, container, false);
        mRemarkTextView = view.findViewById(R.id.tv_quote_remark);
        mRemarkTextView.setText(mRemark);
        return view;
    }

}

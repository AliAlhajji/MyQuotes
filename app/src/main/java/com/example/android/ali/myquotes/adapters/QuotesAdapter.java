package com.example.android.ali.myquotes.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.ali.myquotes.R;
import com.example.android.ali.myquotes.model.Quote;

import java.util.List;

public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.QuoteViewHolder> {
    private List<Quote> mQuotes;
    private Context mContext;
    private QuoteClickListener quoteClickListener;

    public QuotesAdapter(Context mContext, QuoteClickListener quoteClickListener) {
        this.mContext = mContext;
        this.quoteClickListener = quoteClickListener;
    }

    public List<Quote> getData() {
        return mQuotes;
    }

    public void setData(List<Quote> mQuotes) {
        this.mQuotes = mQuotes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public QuoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.quotes_list_item, viewGroup, false);
        QuoteViewHolder viewHolder = new QuoteViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuoteViewHolder quoteViewHolder, int i) {
        String quote = mQuotes.get(i).getText();
        quoteViewHolder.quoteTextView.setText(quote);
    }

    @Override
    public int getItemCount() {
        if(mQuotes != null){
            return mQuotes.size();
        }
        return 0;
    }

    public interface QuoteClickListener{
        void onQuoteClickListener(int i);
    }

    public class QuoteViewHolder extends RecyclerView.ViewHolder{
        TextView quoteTextView;
        public QuoteViewHolder(@NonNull View itemView) {
            super(itemView);
            quoteTextView = itemView.findViewById(R.id.tv_quote);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quoteClickListener.onQuoteClickListener(getAdapterPosition());
                }
            });
        }
    }
}

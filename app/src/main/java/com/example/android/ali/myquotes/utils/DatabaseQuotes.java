package com.example.android.ali.myquotes.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.ali.myquotes.R;
import com.example.android.ali.myquotes.model.Quote;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DatabaseQuotes {
    private FirebaseDatabase database;
    private DatabaseReference bookQuotesRef;
    private Context context;
    private DatabaseQuotesListener listener;

    public DatabaseQuotes(Context context, String bookID){
        this.context = context;
        this.database = FirebaseDatabase.getInstance();
        this.bookQuotesRef = database.getReference().child(AppConstants.BOOKQUOTES_REF).child(bookID);
    }

    public DatabaseQuotes(Context context, String bookID, DatabaseQuotesListener listener){
        this.context = context;
        this.database = FirebaseDatabase.getInstance();
        this.bookQuotesRef = database.getReference().child(AppConstants.BOOKQUOTES_REF).child(bookID);
        this.listener = listener;
    }

    public interface DatabaseQuotesListener{
        void updateAdapter(List<Quote> quotes);
        void showProgressBar(boolean show);
        void updateQuote(Quote quote);

    }

    public void getBookQuotes(){
        final List<Quote> quotes = new ArrayList<>();
        listener.showProgressBar(true);
        bookQuotesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                quotes.clear();
                ProgressBar progressBar = new ProgressBar(context);
                progressBar.setVisibility(View.VISIBLE);
                for(DataSnapshot booksSnapshot: dataSnapshot.getChildren()){
                    Quote quote = booksSnapshot.getValue(Quote.class);
                    quote.setId(booksSnapshot.getKey());
                    quotes.add(quote);
                }
                listener.updateAdapter(quotes);
                listener.showProgressBar(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, context.getString(R.string.error_fetch_data), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteQuote(String quoteID){
        bookQuotesRef.child(quoteID).removeValue();
    }

    public void saveQuote(Quote quote){
        if(quote.getId()==null){
            String key = bookQuotesRef.push().getKey();
            quote.setId(key);
        }
        bookQuotesRef.child(quote.getId()).setValue(quote);
    }

    public void getSingleQuote(final String quoteID){
        listener.showProgressBar(true);
        bookQuotesRef.child(quoteID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Quote quote = dataSnapshot.getValue(Quote.class);
                quote.setId(dataSnapshot.getKey());
                listener.updateQuote(quote);
                listener.showProgressBar(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void deleteBookQuotes(){
        bookQuotesRef.removeValue();
    }

}

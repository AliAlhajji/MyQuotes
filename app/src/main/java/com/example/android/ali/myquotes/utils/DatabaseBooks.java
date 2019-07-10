package com.example.android.ali.myquotes.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.ali.myquotes.R;
import com.example.android.ali.myquotes.adapters.BooksAdapter;
import com.example.android.ali.myquotes.model.Book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DatabaseBooks {
    private FirebaseDatabase database;
    private DatabaseReference userBooksRef;
    private Context context;
    private DatabaseBooksListener listener;

    public DatabaseBooks(Context context, String userID, DatabaseBooksListener listener){
        this.context = context;
        this.database = FirebaseDatabase.getInstance();
        this.userBooksRef = database.getReference().child(AppConstants.USERBOOKS_REF).child(userID);
        this.listener = listener;
    }

    public DatabaseBooks(Context context, String userID){
        this.context = context;
        this.database = FirebaseDatabase.getInstance();
        this.userBooksRef = database.getReference().child(AppConstants.USERBOOKS_REF).child(userID);
    }


    public interface DatabaseBooksListener{
        void updateAdapter(List<Book> books);
        void showProgressBar(boolean show);
    }

    public void saveBook(Book book){
        if(book.getId() == null){
            String key = userBooksRef.push().getKey();
            book.setId(key);
        }
        userBooksRef.child(book.getId())
                .setValue(book);
    }

    public void getUserBooks(){
        final List<Book> books = new ArrayList<>();
        listener.showProgressBar(true);
        userBooksRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                books.clear();
                for(DataSnapshot booksSnapshot: dataSnapshot.getChildren()){
                    Book book = booksSnapshot.getValue(Book.class);
                    book.setId(booksSnapshot.getKey());
                    books.add(book);
                }
                listener.updateAdapter(books);
                listener.showProgressBar(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, context.getString(R.string.error_fetch_data), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteBook(String bookID) {
        userBooksRef.child(bookID).removeValue();
    }

}

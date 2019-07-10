package com.example.android.ali.myquotes.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.ali.myquotes.R;
import com.example.android.ali.myquotes.model.Book;

import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {
    List<Book> mBooks;
    Context mContext;
    OnClick onClick;

    public BooksAdapter(Context context, OnClick onClick){
        this.mContext = context;
        this.onClick = onClick;
    }

    public interface OnClick{
        void onClickListener(int i);
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.books_list_item, viewGroup, false);
        BookViewHolder viewHolder = new BookViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder bookViewHolder, int i) {
        String title = this.mBooks.get(i).getTitle();
        String author = this.mBooks.get(i).getAuthor();
        bookViewHolder.titleTextView.setText(title);
        bookViewHolder.authorTextView.setText(author);
    }

    @Override
    public int getItemCount() {
        if(mBooks != null){
            return mBooks.size();
        }
        return 0;
    }

    public void setData(List<Book> books){
        mBooks = books;
        notifyDataSetChanged();
    }

    public List<Book> getData(){
        return this.mBooks;
    }

    public class BookViewHolder extends RecyclerView.ViewHolder{
        TextView titleTextView, authorTextView;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.books_list_book_title);
            authorTextView = itemView.findViewById(R.id.books_list_book_author);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClick.onClickListener(getAdapterPosition());
                }
            });
        }
    }
}

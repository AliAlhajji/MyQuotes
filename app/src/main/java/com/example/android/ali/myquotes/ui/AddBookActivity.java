package com.example.android.ali.myquotes.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.ali.myquotes.R;
import com.example.android.ali.myquotes.model.Book;
import com.example.android.ali.myquotes.utils.AppConstants;
import com.example.android.ali.myquotes.utils.DatabaseBooks;
import com.google.firebase.auth.FirebaseAuth;

public class AddBookActivity extends AppCompatActivity {
    DatabaseBooks db;
    FirebaseAuth mAuth;
    TextInputLayout mTitle, mAuthor;
    Button mSave;
    String userID;
    String mBookID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        db = new DatabaseBooks(this, userID);
        System.out.println(userID);

        if(mAuth.getCurrentUser() == null || db == null){
            finish();
        }

        userID = mAuth.getCurrentUser().getUid();
        mTitle = findViewById(R.id.et_book_title);
        mAuthor = findViewById(R.id.et_book_author);
        mSave = findViewById(R.id.button_save_book);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(getIntent().hasExtra(AppConstants.EXTRA_BOOK)){
            Book book = getIntent().getParcelableExtra(AppConstants.EXTRA_BOOK);
            mTitle.getEditText().setText(book.getTitle());
            mAuthor.getEditText().setText(book.getAuthor());
            mBookID = book.getId();
        }

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBook();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void saveBook(){
        if(validateInput(mTitle) && validateInput(mAuthor)){
            String title = mTitle.getEditText().getText().toString();
            String author = mAuthor.getEditText().getText().toString();
            Book book = new Book(title, author);
            book.setId(mBookID);

            db.saveBook(book);
            Toast.makeText(this, getString(R.string.data_add_successfully), Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    private boolean validateInput(TextInputLayout textInputLayout){
        String input = textInputLayout.getEditText().getText().toString();
        if(input.isEmpty()){
            textInputLayout.setErrorEnabled(true);
            textInputLayout.getEditText().setError("Required");
            return false;
        }
        textInputLayout.setErrorEnabled(false);
        return true;
    }

    private void deleteBook(){
        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setMessage(R.string.dialog_confirm_delete_book)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteBook(mBookID);
                        dialog.cancel();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create();
        alertDialog.show();
    }
}

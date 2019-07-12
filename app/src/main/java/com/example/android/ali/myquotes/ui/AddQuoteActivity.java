package com.example.android.ali.myquotes.ui;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.ali.myquotes.R;
import com.example.android.ali.myquotes.model.Book;
import com.example.android.ali.myquotes.model.Quote;
import com.example.android.ali.myquotes.utils.AppConstants;
import com.example.android.ali.myquotes.utils.DatabaseQuotes;
import com.google.firebase.auth.FirebaseAuth;

public class AddQuoteActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseQuotes db;
    private Quote quote;
    private Book book;

    private TextInputLayout mQuote, mPage, mRemark;
    private Spinner mSpinner;
    private Button mSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quote);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null || !getIntent().hasExtra(AppConstants.EXTRA_BOOK)) {
            finish();
        }
        book = getIntent().getParcelableExtra(AppConstants.EXTRA_BOOK);
        db = new DatabaseQuotes(this, book.getId());

        mQuote = findViewById(R.id.et_quote_text);
        mPage = findViewById(R.id.et_quote_page);
        mRemark = findViewById(R.id.et_quote_remark);
        mSave = findViewById(R.id.button_save_quote);
        mSpinner = findViewById(R.id.spinner_quote_color);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(getIntent().hasExtra(AppConstants.EXTRA_QUOTE)){
            quote = getIntent().getParcelableExtra(AppConstants.EXTRA_QUOTE);
            mQuote.getEditText().setText(quote.getText());
            mPage.getEditText().setText(quote.getPage()+"");
            mRemark.getEditText().setText(quote.getRemark());
        }
        else{
            quote = new Quote();
        }

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveQuote();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private boolean validateInput(TextInputLayout textInputLayout){
        if(textInputLayout.getEditText().getText().length() > 0){
            textInputLayout.setErrorEnabled(false);
            return true;
        }
        textInputLayout.setErrorEnabled(true);
        textInputLayout.setError(getString(R.string.error_field_required));
        return false;
    }

    private void saveQuote(){
        if(!validateInput(mQuote) && !validateInput(mRemark)){
            return;
        }

        String text = mQuote.getEditText().getText().toString();
        String remark = mRemark.getEditText().getText().toString();
        String color = mSpinner.getSelectedItem().toString();
                        /*
                Since it is allowed to leave the page field empty, we have to check if it's empty or not
                before casting the string to int. An empty string will throw an exception.
                 */
        if (!mPage.getEditText().getText().toString().isEmpty()) {
            try {
                int page = Integer.valueOf(mPage.getEditText().getText().toString());
                quote.setPage(page);
            } catch (NumberFormatException e) {

                return;
            }
        }
        quote.setText(text);
        quote.setRemark(remark);
        quote.setColor(color);
        db.saveQuote(quote);
        Toast.makeText(this, getString(R.string.data_add_successfully), Toast.LENGTH_SHORT).show();
        finish();
    }
}

package com.phantasmlance.mvvm_note_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class NewNoteActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY_TITLE = "note_app.TITLE";
    public static final String EXTRA_REPLY_CONTENT = "note_app.CONTENT";

    private EditText mEditTitle, mEditContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        mEditTitle = findViewById(R.id.edtTitle);
        mEditContent = findViewById(R.id.edtContent);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Create Note");
        actionBar.setDisplayHomeAsUpEnabled(true);

        final Button buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(mEditTitle.getText()) && TextUtils.isEmpty(mEditContent.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String title = mEditTitle.getText().toString();
                replyIntent.putExtra(EXTRA_REPLY_TITLE, title);

                String content = mEditContent.getText().toString();
                replyIntent.putExtra(EXTRA_REPLY_CONTENT, content);

                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }
}
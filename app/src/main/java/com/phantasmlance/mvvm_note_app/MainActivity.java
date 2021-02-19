package com.phantasmlance.mvvm_note_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.phantasmlance.mvvm_note_app.models.Note;
import com.phantasmlance.mvvm_note_app.viewmodels.NoteViewModel;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    AutoCompleteTextView search;
    FloatingActionButton add;
    RecyclerView recyclerViewNote;

    private NoteViewModel mNoteViewModel;

    public static final int NEW_NOTE_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = findViewById(R.id.actvSearch);
        add = findViewById(R.id.fabAdd);

        // setup RecyclerView
        recyclerViewNote = findViewById(R.id.recyclerViewNote);
        final NoteListAdapter adapter = new NoteListAdapter(this);
        recyclerViewNote.setAdapter(adapter);
        recyclerViewNote.setLayoutManager(new LinearLayoutManager(this));

        // Observer
        mNoteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        mNoteViewModel.getAllNotes().observe(this, adapter::setNotes); // adapter.setNotes(notes)

        add.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NewNoteActivity.class);
            startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            assert data != null;
            Note note = new Note(
                    Objects.requireNonNull(data.getStringExtra(NewNoteActivity.EXTRA_REPLY_TITLE)),
                    Objects.requireNonNull(data.getStringExtra(NewNoteActivity.EXTRA_REPLY_CONTENT))
            );
            mNoteViewModel.insert(note);
        } else {
            Toast.makeText(this, R.string.empty_not_saved, Toast.LENGTH_LONG).show();
        }
    }
}
package com.phantasmlance.mvvm_note_app.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.phantasmlance.mvvm_note_app.database.NoteDao;
import com.phantasmlance.mvvm_note_app.database.NoteRoomDatabase;
import com.phantasmlance.mvvm_note_app.models.Note;

import java.util.List;

public class NoteRepository {

    private NoteDao mNoteDao;
    private LiveData<List<Note>> mAllNotes;

    public NoteRepository(Application application) {
        NoteRoomDatabase db = NoteRoomDatabase.getDatabase(application);
        mNoteDao = db.noteDao();
        mAllNotes = mNoteDao.getSortedNotes();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Note>> getAllNotes() {
        return mAllNotes;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Note note) {
        NoteRoomDatabase.databaseWriteExecutor.execute(() -> mNoteDao.insert(note));
    }
}

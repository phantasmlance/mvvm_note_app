package com.phantasmlance.mvvm_note_app.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.phantasmlance.mvvm_note_app.models.Note;

import java.util.List;

@Dao
public interface NoteDao {
    // allowing the insert of the same note multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Note note);

    @Query("DELETE FROM note_table")
    void deleteAll();

    @Query("SELECT * FROM note_table ORDER BY title ASC")
    LiveData<List<Note>> getSortedNotes();
}

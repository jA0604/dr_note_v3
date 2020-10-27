package ru.netology.dr_note_v3.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.netology.dr_note_v3.model.Note;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM note_table")
    List<Note> getAll();

    @Query("SELECT * FROM note_table")
    LiveData<List<Note>> getAllLiveData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

}

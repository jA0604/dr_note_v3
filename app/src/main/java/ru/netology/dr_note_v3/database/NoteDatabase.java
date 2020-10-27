package ru.netology.dr_note_v3.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ru.netology.dr_note_v3.model.Note;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();
}
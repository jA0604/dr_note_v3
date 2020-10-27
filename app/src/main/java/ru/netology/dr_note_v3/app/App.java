package ru.netology.dr_note_v3.app;

import android.app.Application;

import androidx.room.Room;

import ru.netology.dr_note_v3.database.NoteDao;
import ru.netology.dr_note_v3.database.NoteDatabase;
import ru.netology.dr_note_v3.database.PinCodeDao;
import ru.netology.dr_note_v3.database.PinCodeDatabase;

public class App extends Application {
    private NoteDao noteDao;
    private PinCodeDao pinCodeDao;
    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        NoteDatabase database = Room.databaseBuilder(getApplicationContext(),
                NoteDatabase.class, "notes_db")
                .allowMainThreadQueries()
                .build();

        noteDao = database.noteDao();

        PinCodeDatabase pinCodeDatabase = Room.databaseBuilder(getApplicationContext(),
                PinCodeDatabase.class, "pincode_db")
                .allowMainThreadQueries()
                .build();

        pinCodeDao = pinCodeDatabase.pinCodeDao();
    }

    public NoteDao getNoteDao() {
        return noteDao;
    }

    public PinCodeDao getPinCodeDao() {
        return pinCodeDao;
    }
}

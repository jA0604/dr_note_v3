package ru.netology.dr_note_v3.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ru.netology.dr_note_v3.model.PinCode;

@Database(entities = {PinCode.class}, version = 1, exportSchema = false)
public abstract class PinCodeDatabase extends RoomDatabase {
    public abstract PinCodeDao pinCodeDao();
}
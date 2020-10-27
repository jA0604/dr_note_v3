package ru.netology.dr_note_v3.utils;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import ru.netology.dr_note_v3.model.Note;

public class NoteLiveData extends LiveData<Note> {

    public NoteLiveData() {
    }

    public NoteLiveData(Note value) {
        super(value);
    }

    @Override
    protected void postValue(Note value) {
        super.postValue(value);
    }

    @Override
    protected void setValue(Note value) {
        super.setValue(value);
    }

    @Nullable
    @Override
    public Note getValue() {
        return super.getValue();
    }

    public void setNoteLiveDataValue(Note note) {
        setValue(note);
    }
}
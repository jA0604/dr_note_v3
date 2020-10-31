package ru.netology.dr_note_v3.screens.editnote;

import androidx.lifecycle.ViewModel;
import ru.netology.dr_note_v3.app.App;
import ru.netology.dr_note_v3.model.Note;
import ru.netology.dr_note_v3.utils.NoteLiveData;

public class EditNoteFragmentViewModel extends ViewModel {
    private NoteLiveData noteLiveData = new NoteLiveData();

    public void updateNote() {
        App.getInstance().getNoteDao().update(noteLiveData.getValue());
    }

    public NoteLiveData getNoteLiveData() {
        return this.noteLiveData;
    }

    public void setNoteLiveData(Note note) {
        Note noteNew = App.getInstance().getNoteDao().findById(note.id);
        noteLiveData.setNoteLiveDataValue(noteNew);
    }

    public void deleteNote() {
        App.getInstance().getNoteDao().delete(noteLiveData.getValue());
    }

}

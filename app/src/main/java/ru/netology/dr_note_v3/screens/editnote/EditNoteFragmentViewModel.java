package ru.netology.dr_note_v3.screens.editnote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ru.netology.dr_note_v3.app.App;
import ru.netology.dr_note_v3.model.Note;

public class EditNoteFragmentViewModel extends ViewModel {
    private MutableLiveData<Note> noteMutableLiveData = new MutableLiveData<>();
    public LiveData<Note> noteLiveData = noteMutableLiveData;
    private boolean firsStart = true;


    public void updateNote() {
        App.getInstance().getNoteDao().update(noteMutableLiveData.getValue());
    }

    public void setNoteLiveData(Note note) {
        if (firsStart) {
            Note noteNew = App.getInstance().getNoteDao().findById(note.id);
            noteMutableLiveData.setValue(noteNew);
            firsStart = false;
        }
    }

    public void deleteNote() {
        App.getInstance().getNoteDao().delete(noteMutableLiveData.getValue());
    }

}

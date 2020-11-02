package ru.netology.dr_note_v3.screens.addnote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.Calendar;
import ru.netology.dr_note_v3.app.App;
import ru.netology.dr_note_v3.model.Note;

public class AddNewNoteFragmentViewModel extends ViewModel {
    private MutableLiveData<Note> noteMutableLiveData = new MutableLiveData<>();
    public LiveData<Note> noteLiveData = noteMutableLiveData;

    public AddNewNoteFragmentViewModel() {
        Note note = new Note();
        Calendar calendar = Calendar.getInstance();
        note.timestamp = calendar.getTimeInMillis();
        noteMutableLiveData.setValue(note);
    }

    public boolean insertNote() {
        if (!isEmpty()) {
            App.getInstance().getNoteDao().insert(noteLiveData.getValue());
            return true;
        }
        return false;
    }

    private boolean isEmpty() {
        return  noteLiveData.getValue().name.equals("") &&
                noteLiveData.getValue().text.equals("") &&
                !noteLiveData.getValue().hasDeadline;
    }
}

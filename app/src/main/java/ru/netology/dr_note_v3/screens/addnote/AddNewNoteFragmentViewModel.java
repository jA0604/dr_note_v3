package ru.netology.dr_note_v3.screens.addnote;

import androidx.lifecycle.ViewModel;
import java.util.Calendar;
import ru.netology.dr_note_v3.app.App;
import ru.netology.dr_note_v3.model.Note;
import ru.netology.dr_note_v3.utils.NoteLiveData;

public class AddNewNoteFragmentViewModel extends ViewModel {
    private NoteLiveData noteLiveData;

    public AddNewNoteFragmentViewModel() {
        Note note = new Note();
        Calendar calendar = Calendar.getInstance();
        note.timestamp = calendar.getTimeInMillis();
        this.noteLiveData = new NoteLiveData(note);
    }
    public void insertNote() {
        if (!isEmpty()) {
            App.getInstance().getNoteDao().insert(noteLiveData.getValue());
        }
    }

    private boolean isEmpty() {
        return  noteLiveData.getValue().name.equals("") &&
                noteLiveData.getValue().text.equals("") &&
                !noteLiveData.getValue().hasDeadline;
    }

    public NoteLiveData getNoteLiveData() {
        return this.noteLiveData;
    }

}

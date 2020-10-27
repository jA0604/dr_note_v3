package ru.netology.dr_note_v3.screens.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;
import ru.netology.dr_note_v3.app.App;
import ru.netology.dr_note_v3.model.Note;

public class MainFragmentViewModel extends ViewModel {
    LiveData<List<Note>> noteListLiveData = App.getInstance().getNoteDao().getAllLiveData();

    public LiveData<List<Note>> getNoteListLiveData() {
        return noteListLiveData;
    }
}
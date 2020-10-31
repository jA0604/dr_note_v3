package ru.netology.dr_note_v3.screens.editnote;

import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import ru.netology.dr_note_v3.R;
import ru.netology.dr_note_v3.databinding.FragmentEditNoteBinding;
import ru.netology.dr_note_v3.model.Note;
import ru.netology.dr_note_v3.utils.Constants;

public class EditNoteFragment extends Fragment {
    private FragmentEditNoteBinding efBinding;
    private EditNoteFragmentViewModel efViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        efBinding = FragmentEditNoteBinding.inflate(inflater, container, false);
        return efBinding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        saveInLiveData();
        efBinding = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        setHasOptionsMenu(true);
        initialithation();
    }

    private void initialithation() {
        efViewModel = new ViewModelProvider(this).get(EditNoteFragmentViewModel.class);

        //прием данных что новая заметка
        efViewModel.setNoteLiveData((Note) getArguments().getSerializable(Constants.KEY_EXTRA_NOTE));
        bind();

        clickButton(efBinding.btnSaveNote);
        clickButton(efBinding.tvDeadline);

        efBinding.cbHasdeadline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                tvDeadLineVisibility(b);
                if  (b)  callDataPicker();
                efViewModel.getNoteLiveData().getValue().hasDeadline = b;
            }
        });
    }

    private void tvDeadLineVisibility(boolean b) {
        if (b) efBinding.tvDeadline.setVisibility(View.VISIBLE);
        else efBinding.tvDeadline.setVisibility(View.GONE);
    }

    private void clickButton(View viewButton) {
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.btn_save_note:
                        saveInLiveData();
                        efViewModel.updateNote();
                        Constants.APP_ACTIVITY.navController.navigate(R.id.action_editNoteFragment_to_mainFragment);
                        break;
                    case R.id.tv_deadline:
                        callDataPicker();
                        break;
                }
            }
        });
    }

    private void callDataPicker() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(efViewModel.getNoteLiveData().getValue().timestamp);
        DatePickerDialog datePickerDialog = new DatePickerDialog(Constants.APP_ACTIVITY,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        efBinding.tvDeadline.setText(getString(R.string.str_tv_calendar) + i2 + "-" + (i1 +1) + "-" + i);

                        Calendar calendarTimestamp = Calendar.getInstance();
                        calendarTimestamp.set(i, i1, i2);
                        long l = calendarTimestamp.getTimeInMillis();
                        efViewModel.getNoteLiveData().getValue().timestamp = calendarTimestamp.getTimeInMillis();
                    }
                },  calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }


    private void saveInLiveData() {
        efViewModel.getNoteLiveData().getValue().name = efBinding.etInputNameNote.getText().toString();
        efViewModel.getNoteLiveData().getValue().text = efBinding.etInputTextNote.getText().toString();
    }

    private void bind() {
        efBinding.etInputNameNote.setText(efViewModel.getNoteLiveData().getValue().name);
        efBinding.etInputTextNote.setText(efViewModel.getNoteLiveData().getValue().text);
        efBinding.cbHasdeadline.setChecked(efViewModel.getNoteLiveData().getValue().hasDeadline);
        tvDeadLineVisibility(efBinding.cbHasdeadline.isChecked());
        SimpleDateFormat formating = new SimpleDateFormat("dd.MM.YYYY HH:mm:ss:SSS", Locale.getDefault());
        efBinding.tvDeadline.setText(getString(R.string.str_tv_calendar) + formating.format(efViewModel.getNoteLiveData().getValue().timestamp));
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit_note_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.icon_delete:
                efViewModel.deleteNote();
            case android.R.id.home:
                Constants.APP_ACTIVITY.navController.navigate(R.id.action_editNoteFragment_to_mainFragment);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
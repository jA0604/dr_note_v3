package ru.netology.dr_note_v3.screens.addnote;

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
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import ru.netology.dr_note_v3.R;
import ru.netology.dr_note_v3.databinding.FragmentAddNewNoteBinding;
import ru.netology.dr_note_v3.screens.dialog.MessageDialog;
import ru.netology.dr_note_v3.utils.Constants;

public class AddNewNoteFragment extends Fragment {
    private FragmentAddNewNoteBinding afBinding;
    private AddNewNoteFragmentViewModel afViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        afBinding = FragmentAddNewNoteBinding.inflate(inflater, container, false);
        return afBinding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        saveInLiveData();
        afBinding = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        setHasOptionsMenu(true);
        initialithation();
    }
    private void initialithation() {
        afViewModel = new ViewModelProvider(this).get(AddNewNoteFragmentViewModel.class);
        bind();

        afBinding.tvDeadline.setVisibility((afBinding.cbHasdeadline.isChecked() ? View.VISIBLE : View.GONE));

        afBinding.tvDeadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callDataPicker();
            }
        });

        afBinding.cbHasdeadline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    afBinding.tvDeadline.setVisibility(View.VISIBLE);
                    callDataPicker();
                } else {
                    afBinding.tvDeadline.setVisibility(View.GONE);
                }
                afViewModel.getNoteLiveData().getValue().hasDeadline = b;
            }
        });

    }

    private void callDataPicker() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(afViewModel.getNoteLiveData().getValue().timestamp);
        DatePickerDialog datePickerDialog = new DatePickerDialog(Constants.APP_ACTIVITY,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        afBinding.tvDeadline.setText(getString(R.string.str_tv_calendar) + i2 + "-" + (i1 +1) + "-" + i);

                        Calendar calendarTimestamp = Calendar.getInstance();
                        calendarTimestamp.set(i, i1, i2);
                        afViewModel.getNoteLiveData().getValue().timestamp = calendarTimestamp.getTimeInMillis();

                    }
                },  calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void saveInLiveData() {
        afViewModel.getNoteLiveData().getValue().name = afBinding.etInputNameNote.getText().toString();
        afViewModel.getNoteLiveData().getValue().text = afBinding.etInputTextNote.getText().toString();
    }

    private void bind() {
        afBinding.etInputNameNote.setText(afViewModel.getNoteLiveData().getValue().name);
        afBinding.etInputTextNote.setText(afViewModel.getNoteLiveData().getValue().text);
        afBinding.cbHasdeadline.setChecked(afViewModel.getNoteLiveData().getValue().hasDeadline);
        SimpleDateFormat formating = new SimpleDateFormat("dd.MM.YYYY HH:mm:ss:SSS", Locale.getDefault());
        afBinding.tvDeadline.setText(getString(R.string.str_tv_calendar) + formating.format(afViewModel.getNoteLiveData().getValue().timestamp));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add_note_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Constants.APP_ACTIVITY.navController.navigate(R.id.action_addNewNoteFragment_to_mainFragment);
                break;
            case R.id.icon_save:
                saveInLiveData();
                if (afViewModel.insertNote()) {
                    Constants.APP_ACTIVITY.navController.navigate(R.id.action_addNewNoteFragment_to_mainFragment);
                    break;
                } else {
                    new MessageDialog(getString(R.string.message_dialog_title_all_fields_empty), getString(R.string.message_dialog_all_fields_empty));
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
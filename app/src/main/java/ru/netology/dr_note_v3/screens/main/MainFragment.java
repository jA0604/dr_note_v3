package ru.netology.dr_note_v3.screens.main;

import android.os.Bundle;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import java.util.List;
import ru.netology.dr_note_v3.R;
import ru.netology.dr_note_v3.databinding.FragmentMainBinding;
import ru.netology.dr_note_v3.model.Note;
import ru.netology.dr_note_v3.utils.Constants;

public class MainFragment extends Fragment {
    private FragmentMainBinding mfBinding;
    private MainFragmentViewModel mfViewModel;
    private RecyclerView rvNoteItems;
    private NoteListAdapter adapter;

    private OnBackPressedCallback callbackOnBackPressed;
    private long backPressedTime = 0L;
    private Toast backToast;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callbackOnBackPressed = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (backPressedTime + 2000 > System.currentTimeMillis()) {
                    backToast.cancel();
                    Constants.APP_ACTIVITY.finish();
                } else {
                    backToast = Toast.makeText(Constants.APP_ACTIVITY, "Нажмите еще раз, чтобы выйти", Toast.LENGTH_SHORT);
                    backToast.show();
                }
                backPressedTime = System.currentTimeMillis();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callbackOnBackPressed);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mfBinding = FragmentMainBinding.inflate(inflater, container, false);
        return mfBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mfBinding = null;
    }

    @Override
    public void onStart() {
        super.onStart();

        setHasOptionsMenu(true);
        initialithation();
    }

    private void initialithation() {
        mfViewModel = new ViewModelProvider(this).get(MainFragmentViewModel.class);
        adapter = new NoteListAdapter();
        rvNoteItems = mfBinding.rvNoteListContainer;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Constants.APP_ACTIVITY, RecyclerView.VERTICAL, false);
        rvNoteItems.setLayoutManager(linearLayoutManager);

        rvNoteItems.setAdapter(adapter);

        mfViewModel.getNoteListLiveData().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.setItems(notes);
            }
        });

        mfBinding.fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //передача данных что новая заметка
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.KEY_EXTRA_NOTE, null);
                Constants.APP_ACTIVITY.navController.navigate(R.id.action_mainFragment_to_addNewNoteFragment, bundle);
            }
        });

    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_settings:
                Constants.APP_ACTIVITY.navController.navigate(R.id.action_mainFragment_to_settingFragment);
                break;
//            case android.R.id.home:
//                Constants.APP_ACTIVITY.finish();
//                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
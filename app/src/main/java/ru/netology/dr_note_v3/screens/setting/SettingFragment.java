package ru.netology.dr_note_v3.screens.setting;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import ru.netology.dr_note_v3.R;
import ru.netology.dr_note_v3.databinding.FragmentSettingBinding;
import ru.netology.dr_note_v3.screens.dialog.MessageDialog;
import ru.netology.dr_note_v3.utils.Constants;

public class SettingFragment extends Fragment {
    private FragmentSettingBinding sfBinding;
    private SettingFragmentViewModel sfViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sfBinding = FragmentSettingBinding.inflate(inflater, container, false);
        return sfBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        sfBinding = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        setHasOptionsMenu(true);

        initialithation();
    }

    private void initialithation() {
        clickButton(sfBinding.btn1);
        clickButton(sfBinding.btn2);
        clickButton(sfBinding.btn3);
        clickButton(sfBinding.btn4);
        clickButton(sfBinding.btn5);
        clickButton(sfBinding.btn6);
        clickButton(sfBinding.btn7);
        clickButton(sfBinding.btn8);
        clickButton(sfBinding.btn9);
        clickButton(sfBinding.btn0);
        clickButton(sfBinding.btnBs);
        clickButton(sfBinding.btnReset);

        sfViewModel = new ViewModelProvider(this).get(SettingFragmentViewModel.class);
        sfViewModel.getPinLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showPinView(s.length());
                behaviorView();

            }
        });
    }

    private void clickButton(View viewButton) {
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.btn_reset:
                        resetPinView();
                        break;
                    case R.id.btn_bs:
                        backSpace();
                        break;
                    default: {
                        sfViewModel.setPin(((Button) view).getText().toString());
                    }
                }
            }
        });
    }

    private void showPinView(int i) {
        switch (i) {
            case 0:
                sfBinding.tvPin1.setBackgroundResource(R.drawable.drw_ring);
                sfBinding.tvPin2.setBackgroundResource(R.drawable.drw_ring);
                sfBinding.tvPin3.setBackgroundResource(R.drawable.drw_ring);
                sfBinding.tvPin4.setBackgroundResource(R.drawable.drw_ring);
                break;
            case 1:
                sfBinding.tvPin1.setBackgroundResource(R.drawable.drw_btn_oval_pin);
                break;
            case 2:
                sfBinding.tvPin1.setBackgroundResource(R.drawable.drw_btn_oval_pin);
                sfBinding.tvPin2.setBackgroundResource(R.drawable.drw_btn_oval_pin);
                break;
            case 3:
                sfBinding.tvPin1.setBackgroundResource(R.drawable.drw_btn_oval_pin);
                sfBinding.tvPin2.setBackgroundResource(R.drawable.drw_btn_oval_pin);
                sfBinding.tvPin3.setBackgroundResource(R.drawable.drw_btn_oval_pin);
                break;
            case 4:
                sfBinding.tvPin1.setBackgroundResource(R.drawable.drw_btn_oval_pin);
                sfBinding.tvPin2.setBackgroundResource(R.drawable.drw_btn_oval_pin);
                sfBinding.tvPin3.setBackgroundResource(R.drawable.drw_btn_oval_pin);
                sfBinding.tvPin4.setBackgroundResource(R.drawable.drw_btn_oval_pin);
                break;
        }
    }

    private void resetPinView() {
        sfViewModel.getPinLiveData().setValue("");
        showPinView(0);
    }

    private void backSpace() {
        switch (sfViewModel.getPinLiveData().getValue().length()) {
            case 1:
                sfBinding.tvPin1.setBackgroundResource(R.drawable.drw_ring);
                break;
            case 2:
                sfBinding.tvPin2.setBackgroundResource(R.drawable.drw_ring);
                break;
            case 3:
                sfBinding.tvPin3.setBackgroundResource(R.drawable.drw_ring);
                break;
        }
        sfViewModel.pinCodeBackspace();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Constants.APP_ACTIVITY.navController.navigate(R.id.action_settingFragment_to_mainFragment);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void behaviorView() {
        switch (sfViewModel.actionState()) {
            case Constants.REPEAT_NEW_PIN:
                new MessageDialog(getString(R.string.message_dialog_title_new_pin), getString(R.string.message_dialog_new_pin));
                resetPinView();
                break;
            case Constants.REPEAT_NEW_PIN_INCORRECT:
                new MessageDialog(getString(R.string.message_dialog_title_new_pin), getString(R.string.message_dialog_new_pin_incorrect));
                resetPinView();
                break;
            case Constants.NAVIGATE:
                Constants.APP_ACTIVITY.navController.navigate(R.id.action_settingFragment_to_mainFragment);
                break;
        }
    }
}
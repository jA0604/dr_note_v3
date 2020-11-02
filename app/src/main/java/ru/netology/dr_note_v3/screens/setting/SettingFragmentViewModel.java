package ru.netology.dr_note_v3.screens.setting;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.netology.dr_note_v3.app.App;
import ru.netology.dr_note_v3.model.PinCode;
import ru.netology.dr_note_v3.utils.Constants;
import ru.netology.dr_note_v3.utils.PinHash;

public class SettingFragmentViewModel extends ViewModel {
    private String newPin = "";
    private MutableLiveData<String> pinCodeMutableLiveData = new MutableLiveData<>("");
    public LiveData<String> pinCodeLiveData = pinCodeMutableLiveData;
    private List<PinCode> listPinCode = App.getInstance().getPinCodeDao().getAll();

    public void setPin(String pin) {
        pinCodeMutableLiveData.setValue(pinCodeMutableLiveData.getValue() + pin);
    }

    public void pinCodeBackspace() {
        if (pinCodeMutableLiveData.getValue().length() > 0 && pinCodeMutableLiveData.getValue().length() < 5) {
            pinCodeMutableLiveData.setValue(pinCodeMutableLiveData.getValue().substring(0, pinCodeMutableLiveData.getValue().length() - 1));
        }
    }

    public void resetPinCode() {
        pinCodeMutableLiveData.setValue("");
    }

    public int actionState() {
        if (pinCodeMutableLiveData.getValue().length() == 4) {
            if (newPin.equals("")) { //Если новый ПИН не был ранее введен
                this.newPin = pinCodeMutableLiveData.getValue();
                return Constants.REPEAT_NEW_PIN;
            } else if (this.pinCodeMutableLiveData.getValue().equals(newPin)) { //Если повторный ПИН совпадает с новым
                updatePinCode(new PinHash().getHash(pinCodeMutableLiveData.getValue()));
                return Constants.NAVIGATE;
            } else { //Если повторный ПИН НЕ совпадает с новым
                return Constants.REPEAT_NEW_PIN_INCORRECT;
            }
        } else {
            return Constants.NO_ACTION;
        }
    }

    private String getHashPinCodeExist() {
        if (listPinCode.size() == 0) {
            return "";
        } else {
            return listPinCode.get(0).getPinHash();
        }
    }
    private void insertPinCode(String hashPinCode) {
        App.getInstance().getPinCodeDao().insert(new PinCode(hashPinCode));
    }
    private void updatePinCode(String hashPinCode) {
        PinCode pinCode = new PinCode(hashPinCode);
        pinCode.id = 1;
        App.getInstance().getPinCodeDao().update(pinCode);
    }
}
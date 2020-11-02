package ru.netology.dr_note_v3.screens.start;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.netology.dr_note_v3.app.App;
import ru.netology.dr_note_v3.model.PinCode;
import ru.netology.dr_note_v3.utils.Constants;
import ru.netology.dr_note_v3.utils.PinHash;

public class StartFragmentViewModel extends ViewModel {
    private String newPin = "";
    private List<PinCode> listPinCode = App.getInstance().getPinCodeDao().getAll();
    private MutableLiveData<String> pinCodeMutableLiveData = new MutableLiveData<>("");
    public LiveData<String> pinCodeLiveData = pinCodeMutableLiveData;


    public StartFragmentViewModel() {
        newPin = getHashPinCodeExist();
    }

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
            if (newPin.equals("")) {    //Если первый ввод
                newPin = pinCodeMutableLiveData.getValue();
                return Constants.REPEAT_NEW_PIN;
            } else if (getHashPinCodeExist().equals("") &&
                    pinCodeMutableLiveData.getValue().equals(newPin)) { //Если первый вход и повторный ПИН совпадает с новым
                    insertPinCode(new PinHash().getHash(pinCodeMutableLiveData.getValue()));
                return Constants.NAVIGATE;
            } else if (getHashPinCodeExist().equals("") &&
                    !pinCodeMutableLiveData.getValue().equals(newPin)) { //Если первый вход и повторный ПИН НЕ совпадает с новым
                return Constants.REPEAT_NEW_PIN_INCORRECT;
            } else if (!getHashPinCodeExist().equals("") &&
                    (new PinHash().getHash(pinCodeMutableLiveData.getValue())).equals(newPin)) { //Если не первый вход и совпадает с ПИНом из БД
                return Constants.NAVIGATE;
            } else { //Если не первый вход и НЕ совпадает с ПИНом из БД
                return Constants.REPEAT_PIN_INCORRECT;
            }
        } else  if (newPin.equals("") && pinCodeMutableLiveData.getValue().length() == 0) { //Если первый вход и ПИН не вводился (начальный запуск)
            return Constants.NEW_PIN;
        }
        return Constants.NO_ACTION;
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

}

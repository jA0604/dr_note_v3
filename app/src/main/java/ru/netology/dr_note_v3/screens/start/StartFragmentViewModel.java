package ru.netology.dr_note_v3.screens.start;

import androidx.lifecycle.ViewModel;
import ru.netology.dr_note_v3.utils.Constants;
import ru.netology.dr_note_v3.utils.PinHash;
import ru.netology.dr_note_v3.utils.PinLiveData;

public class StartFragmentViewModel extends ViewModel {
    private PinLiveData pinLiveData;
    private String newPin = "";

    public StartFragmentViewModel() {
        this.pinLiveData = new PinLiveData("");
        this.newPin = pinLiveData.getHashPinCodeExist();
    }

    public PinLiveData getPinLiveData() {
        return this.pinLiveData;
    }

    public void setPin(String pin) {
        this.pinLiveData.setValue(this.pinLiveData.getValue() + pin);
    }

    public void pinCodeBackspace() {
        pinLiveData.setValue(pinLiveData.getValue().substring(0, pinLiveData.getValue().length() - 1));
    }

    public int actionState() {
        if (newPin.equals("")) { //Если первый вход
            newPin = pinLiveData.getValue();
            return Constants.REPEAT_NEW_PIN;
        } else if (pinLiveData.getHashPinCodeExist().equals("") &&
                   pinLiveData.getValue().equals(newPin)) { //Если первый вход и повторный ПИН совпадает с новым
            pinLiveData.insertPinCode(new PinHash().getHash(pinLiveData.getValue()));
            return Constants.NAVIGATE;
        } else if (pinLiveData.getHashPinCodeExist().equals("") &&
                  !pinLiveData.getValue().equals(newPin)) { //Если первый вход и повторный ПИН НЕ совпадает с новым
            return Constants.REPEAT_NEW_PIN_INCORRECT;
        } else if (!pinLiveData.getHashPinCodeExist().equals("") &&
                    (new PinHash().getHash(pinLiveData.getValue())).equals(newPin)) { //Если не первый вход и совпадает с ПИНом из БД
            return Constants.NAVIGATE;
        } else { //Если не первый вход и НЕ совпадает с ПИНом из БД
            return Constants.REPEAT_PIN_INCORRECT;
        }
    }
}

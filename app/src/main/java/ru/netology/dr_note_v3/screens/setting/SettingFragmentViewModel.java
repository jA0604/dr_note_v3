package ru.netology.dr_note_v3.screens.setting;

import androidx.lifecycle.ViewModel;
import ru.netology.dr_note_v3.utils.Constants;
import ru.netology.dr_note_v3.utils.PinHash;
import ru.netology.dr_note_v3.utils.PinLiveData;

public class SettingFragmentViewModel extends ViewModel {
    private PinLiveData pinLiveData = new PinLiveData("");
    private String newPin = "";

    public SettingFragmentViewModel() {
        this.pinLiveData = new PinLiveData("");
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
        if (newPin.equals("")) { //Если новый ПИН не был ранее введен
            this.newPin = pinLiveData.getValue();
            return Constants.REPEAT_NEW_PIN;
        } else if (this.pinLiveData.getValue().equals(newPin)) { //Если повторный ПИН совпадает с новым
            this.pinLiveData.updatePinCode(new PinHash().getHash(pinLiveData.getValue()));
            return Constants.NAVIGATE;
        } else { //Если повторный ПИН НЕ совпадает с новым
            return Constants.REPEAT_NEW_PIN_INCORRECT;
        }
    }
}
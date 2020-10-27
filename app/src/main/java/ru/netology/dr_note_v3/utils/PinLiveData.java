package ru.netology.dr_note_v3.utils;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.util.List;

import ru.netology.dr_note_v3.app.App;
import ru.netology.dr_note_v3.model.PinCode;

public class PinLiveData extends LiveData<String> {
    private List<PinCode> pinCodeList;

    public PinLiveData(String value) {
        super(value);
        pinCodeList = App.getInstance().getPinCodeDao().getAll();
    }

    @Override
    protected void postValue(String value) {
        super.postValue(value);
    }

    @Override
    public void setValue(String value) {
        super.setValue(value);
    }

    @Nullable
    @Override
    public String getValue() {
        return super.getValue();
    }

    public String getHashPinCodeExist() {
        if (pinCodeList.size() == 0) {
            return "";
        } else {
            return pinCodeList.get(0).getPinHash();
        }
    }
    public void insertPinCode(String hashPinCode) {
        App.getInstance().getPinCodeDao().insert(new PinCode(hashPinCode));
    }
    public void updatePinCode(String hashPinCode) {
        PinCode pinCode = new PinCode(hashPinCode);
        pinCode.id = 1;
        App.getInstance().getPinCodeDao().update(pinCode); //new PinCode(hashPinCode));
    }
}

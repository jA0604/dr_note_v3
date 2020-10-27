package ru.netology.dr_note_v3.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pin_table")
public class PinCode {
    @PrimaryKey(autoGenerate = true)
    public int id = 0;

    @ColumnInfo(name = "pin")
    public String pinHash = "";

    public PinCode(String pinHash) {
        this.pinHash = pinHash;
    }

    public String getPinHash() {
        return pinHash;
    }
}
package ru.netology.dr_note_v3.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "note_table")
public class Note implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id = 0;

    @ColumnInfo(name = "name")
    public String name = "";

    @ColumnInfo(name = "text")
    public String text = "";

    @ColumnInfo(name = "timestamp")
    public long timestamp;

    @ColumnInfo(name = "hasdeadline")
    public boolean hasDeadline = false;

    public Note() {
    }
}


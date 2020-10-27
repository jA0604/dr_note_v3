package ru.netology.dr_note_v3.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.netology.dr_note_v3.model.PinCode;

@Dao
public interface PinCodeDao {
    @Query("SELECT * FROM pin_table")
    List<PinCode> getAll();

    @Query("SELECT * FROM pin_table")
    LiveData<List<PinCode>> getAllLiveData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PinCode pinCode);

    @Update()
    void update(PinCode pinCode);

}
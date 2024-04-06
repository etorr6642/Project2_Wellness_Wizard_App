package com.example.project2_wellness_wizard_app.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project2_wellness_wizard_app.database.entities.User;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... user);

    @Delete
    void delete(User user);

    @Query(" SELECT * FROM " + WellnessWizardDatabase.USER_TABLE + " ORDER BY username")
    List<User> getAllUsers();

}

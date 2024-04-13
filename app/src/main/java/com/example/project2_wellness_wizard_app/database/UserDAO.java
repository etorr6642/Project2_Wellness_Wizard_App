package com.example.project2_wellness_wizard_app.database;

import androidx.lifecycle.LiveData;
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
    LiveData<List<User>> getAllUsers();

    @Query(" DELETE FROM " + WellnessWizardDatabase.USER_TABLE)
    void deleteAll();

    @Query(" SELECT * FROM " + WellnessWizardDatabase.USER_TABLE + " WHERE username = :username")
    LiveData<User> getUserbyUserName(String username);

    @Query(" SELECT * FROM " + WellnessWizardDatabase.USER_TABLE + " WHERE userID = :userID")
    LiveData<User> getUserbyUserId(int userID);

    @Query("SELECT * FROM " + WellnessWizardDatabase.USER_TABLE + " WHERE isAdmin = true")
    LiveData<User> getUserByIsAdmin();

    @Query("SELECT username FROM " + WellnessWizardDatabase.USER_TABLE + " WHERE userId = :userId")
    String getUsername(int userId);

    @Query("SELECT password FROM " + WellnessWizardDatabase.USER_TABLE + " WHERE userId = :userId")
    String getPassword(int userId);

    @Query("SELECT username FROM " + WellnessWizardDatabase.USER_TABLE + " ORDER by username")
    List<String> getAllUsernames();

    @Query("DELETE FROM "+ WellnessWizardDatabase.USER_TABLE + " WHERE username = :username")
    void deleteByUsername(String username);

}

package com.example.project2_wellness_wizard_app.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project2_wellness_wizard_app.database.entities.User;
import com.example.project2_wellness_wizard_app.database.entities.UserInfo;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface UserInfoDAO {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserInfo userInfo);
    @Update
    void update(UserInfo userInfo);

    @Delete
    void delete(UserInfo userInfo);


    @Query(" SELECT * FROM " + WellnessWizardDatabase.USER_INFO_TABLE + " ORDER BY date DESC")
    List<UserInfo> getAllRecords();

    @Query(" SELECT water FROM " + WellnessWizardDatabase.USER_INFO_TABLE + " WHERE water > 0 AND userId = :userId ORDER BY date DESC")
    List<Integer> getAllWaterRecords(int userId);

    @Query(" SELECT vitMeds FROM " + WellnessWizardDatabase.USER_INFO_TABLE + " WHERE vitMeds != '' AND userId = :userId ORDER BY date DESC")
    List<String> getallVitMedRecords(int userId);

    @Query(" SELECT timeOfDay FROM " + WellnessWizardDatabase.USER_INFO_TABLE + " WHERE timeOfDay != '' AND userId = :userId ORDER BY date DESC")
    List<String> getallTimeOfDayRecords(int userId);
  
    @Query(" SELECT weight FROM " + WellnessWizardDatabase.USER_INFO_TABLE + " WHERE weight > 0 AND userId = :userId ORDER BY date DESC")
    List<Double> getAllWeightRecords(int userId);

    @Query(" SELECT food FROM " + WellnessWizardDatabase.USER_INFO_TABLE + " WHERE food != '' AND userId = :userId ORDER BY date DESC")
    List<String> getAllFoodRecords(int userId);

    @Query(" SELECT calories FROM " + WellnessWizardDatabase.USER_INFO_TABLE + " WHERE calories > 0 AND userId = :userId ORDER BY date DESC")
    List<Integer> getAllCalorieRecords(int userId);

    @Query(" DELETE FROM " + WellnessWizardDatabase.USER_INFO_TABLE + " WHERE userId = :userId")
    void deleteByUserId(int userId);
}

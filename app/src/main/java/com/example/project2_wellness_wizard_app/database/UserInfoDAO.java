package com.example.project2_wellness_wizard_app.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project2_wellness_wizard_app.database.entities.UserInfo;

import java.util.List;

@Dao
public interface UserInfoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserInfo userInfo);

    @Query(" SELECT * FROM " + WellnessWizardDatabase.USER_INFO_TABLE + " ORDER BY date DESC")
    List<UserInfo> getAllRecords();

    @Query(" SELECT water FROM " + WellnessWizardDatabase.USER_INFO_TABLE + " WHERE userId = :userId ORDER BY date DESC")
    List<Integer> getAllWaterRecords(int userId);



}

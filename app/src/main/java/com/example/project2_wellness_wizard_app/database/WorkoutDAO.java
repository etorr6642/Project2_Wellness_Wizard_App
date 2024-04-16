package com.example.project2_wellness_wizard_app.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project2_wellness_wizard_app.database.entities.Workout;

@Dao
public interface WorkoutDAO {

    @Insert
    void insert(Workout...workout);
    @Update
    void update(Workout...workouts);

    @Delete
    void delete(Workout workout);

    @Query("DELETE FROM " + WellnessWizardDatabase.WORKOUT_TABLE)
    void deleteALlWorkout();

    @Query("SELECT workout FROM " + WellnessWizardDatabase.WORKOUT_TABLE + " ORDER BY RANDOM() LIMIT 1")
    String getRandomWorkout();

    @Query("DELETE FROM " + WellnessWizardDatabase.WORKOUT_TABLE+ " WHERE workoutId = :workoutId")
    void deleteWorkout(int workoutId);
}

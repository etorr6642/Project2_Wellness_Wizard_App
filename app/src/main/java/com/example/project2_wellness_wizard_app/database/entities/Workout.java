package com.example.project2_wellness_wizard_app.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project2_wellness_wizard_app.database.WellnessWizardDatabase;

import java.util.Objects;

@Entity(tableName = WellnessWizardDatabase.WORKOUT_TABLE)
public class Workout {

    @PrimaryKey(autoGenerate = true)
    private int workoutId;

    private String workout;

    public Workout(String workout) {
        this.workout = workout;
    }

    @Override
    public String toString() {
        return "Workout{" +
                "workout='" + workout + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Workout workout1 = (Workout) o;
        return workoutId == workout1.workoutId && Objects.equals(workout, workout1.workout);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workoutId, workout);
    }


    public int getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }
    public String getWorkout() {
        return workout;
    }

    public void setWorkout(String workout) {
        this.workout = workout;
    }
}

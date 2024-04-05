package com.example.project2_wellness_wizard_app.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project2_wellness_wizard_app.database.WellnessWizardDatabase;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(tableName = WellnessWizardDatabase.USER_INFO_TABLE)
public class UserInfo {
    @PrimaryKey(autoGenerate = true)
    private int userInfoID;

    private String food;
    private int calories;
    private String vitMeds;
    private String timeOfDay;
    private int water;
    private double weight;
    private LocalDateTime date;

    public UserInfo(String food, int calories, String vitMeds, String timeOfDay, int water, double weight) { //letting db create id
        this.food = food;
        this.calories = calories;
        this.vitMeds = vitMeds;
        this.timeOfDay = timeOfDay;
        this.water = water;
        this.weight = weight;
        date = LocalDateTime.now();
    }

    @NonNull
    @Override
    public String toString() {
        return "UserInfo{" +
                "userInfoID=" + userInfoID +
                ", food='" + food + '\'' +
                ", calories=" + calories +
                ", vitMeds='" + vitMeds + '\'' +
                ", timeOfDay='" + timeOfDay + '\'' +
                ", water=" + water +
                ", weight=" + weight +
                ", date=" + date.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return userInfoID == userInfo.userInfoID && calories == userInfo.calories && Double.compare(water, userInfo.water) == 0 && Double.compare(weight, userInfo.weight) == 0 && Objects.equals(food, userInfo.food) && Objects.equals(vitMeds, userInfo.vitMeds) && Objects.equals(timeOfDay, userInfo.timeOfDay) && Objects.equals(date, userInfo.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userInfoID, food, calories, vitMeds, timeOfDay, water, weight, date);
    }

    public int getUserInfoID() {
        return userInfoID;
    }

    public void setUserInfoID(int userInfoID) {
        this.userInfoID = userInfoID;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getVitMeds() {
        return vitMeds;
    }

    public void setVitMeds(String vitMeds) {
        this.vitMeds = vitMeds;
    }

    public String getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(String timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}

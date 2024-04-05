package com.example.project2_wellness_wizard_app.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.project2_wellness_wizard_app.database.TypeConverters.LocalDateTypeConverter;
import com.example.project2_wellness_wizard_app.database.entities.User;
import com.example.project2_wellness_wizard_app.database.entities.UserInfo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@TypeConverters(LocalDateTypeConverter.class)
@Database(entities = {User.class, UserInfo.class}, version = 1, exportSchema = false)
public abstract class WellnessWizardDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "WellnessWizard_Database";
    public static final String USER_INFO_TABLE = "userInfoTable";
    public static final String USER_TABLE = "userTable";

    public static volatile WellnessWizardDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
}

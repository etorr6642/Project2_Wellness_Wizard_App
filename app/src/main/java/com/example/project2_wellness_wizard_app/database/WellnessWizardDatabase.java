package com.example.project2_wellness_wizard_app.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.example.project2_wellness_wizard_app.MainActivity;
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

    static WellnessWizardDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (WellnessWizardDatabase.class){
                if(INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WellnessWizardDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    //add default users here
    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            Log.i(MainActivity.TAG, "Database Created! ");
            //TODO: add databaseWriteExecuter(() -> {...}
        }
    };

    public abstract UserInfoDAO userInfoDAO();
}

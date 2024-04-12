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
import com.example.project2_wellness_wizard_app.WorkoutActivity;
import com.example.project2_wellness_wizard_app.database.TypeConverters.LocalDateTypeConverter;
import com.example.project2_wellness_wizard_app.database.entities.User;
import com.example.project2_wellness_wizard_app.database.entities.UserInfo;
import com.example.project2_wellness_wizard_app.database.entities.Workout;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//Wipe data and change version number to 1 if users go missing
@TypeConverters(LocalDateTypeConverter.class)
@Database(entities = {User.class, UserInfo.class, Workout.class}, version = 2, exportSchema = false)
public abstract class WellnessWizardDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "WellnessWizardDatabase";
    public static final String USER_INFO_TABLE = "userInfoTable";
    public static final String USER_TABLE = "usertable";
    public static final String WORKOUT_TABLE = "workoutTable";

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
                            .addCallback(addDefaultWorkouts) //added default workouts
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    //add default users here
    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.i(MainActivity.TAG, "Database Created! ");
            databaseWriteExecutor.execute(() -> {
                UserDAO dao = INSTANCE.userDAO();
                dao.deleteAll();
                User admin = new User("admin1", "admin1");
                admin.setAdmin(true);
                dao.insert(admin);
                User testUser1 = new User("testUser1", "testUser1");
                dao.insert(testUser1);
            });
        }
    };

    //add some default workouts, that way we start with some
    private static final RoomDatabase.Callback addDefaultWorkouts = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                WorkoutDAO dao = INSTANCE.workoutDAO();
                //dao.deleteALlWorkout();

                Workout Cardio = new Workout( "Jumping Jacks: 30 seconds\n" + "High knees: Bring knees up high for 30 seconds\n" + "Burpees: Perform 10 burpees\n" + "Repeat 3 times.\n");
                dao.insert(Cardio);
                Workout Back = new Workout("Lat pull-down: 10 reps\n" + "Seated Cable Row: 12 reps\n" + "Bent Over Rows: 10 reps\n" + "Pull-Ups: 6-8 Reps (use assistance if needed)\n" + "Repeat each 3 times.\n");
                dao.insert(Back);
                Workout BackBiceps = new Workout("Lat Pull-Down: 10 reps\n" + "Chest Supported Row: 10 reps\n" + "Seated Cable Row: 12 reps\n" + "Lat Push-Downs: 12 reps\n" + "Dumbbell Concentration Curls: 12 reps\n" + "Dumbbell Hammer Curls: 12 reps\n" + "Repeat each 3 times.\n");
                dao.insert(BackBiceps);
            });
        }
    };

    public abstract UserInfoDAO userInfoDAO();

    public abstract UserDAO userDAO();

    public abstract WorkoutDAO workoutDAO();
}

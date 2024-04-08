package com.example.project2_wellness_wizard_app.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.project2_wellness_wizard_app.MainActivity;
import com.example.project2_wellness_wizard_app.database.entities.User;
import com.example.project2_wellness_wizard_app.database.entities.UserInfo;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class UserInfoRepository {
    private UserInfoDAO userInfoDAO;
    private UserDAO userDAO;

    private WorkoutDAO workoutDAO;
    private ArrayList<UserInfo> allLogs;

    private static UserInfoRepository repository;

    private UserInfoRepository(Application application){
        WellnessWizardDatabase db = WellnessWizardDatabase.getDatabase(application);
        this.userInfoDAO = db.userInfoDAO();
        this.userDAO =db.userDAO();
        this.allLogs = (ArrayList<UserInfo>) this.userInfoDAO.getAllRecords();
    }

    public static UserInfoRepository getRepository(Application application){
        if(repository!=null){
            return repository;
        }
        Future<UserInfoRepository> future = WellnessWizardDatabase.databaseWriteExecutor.submit(
                new Callable<UserInfoRepository>() {
                    @Override
                    public UserInfoRepository call() throws Exception {
                        return new UserInfoRepository(application);
                    }
                }
        );
        try{
            return future.get();
        }catch (InterruptedException | ExecutionException e){
            Log.d(MainActivity.TAG, "Problem getting UserInfoRepository, thread error");
        }
        return null;
    }

    public ArrayList<UserInfo> getAllLogs(){
        Future<ArrayList<UserInfo>> future = WellnessWizardDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<UserInfo>>() {
                    @Override
                    public ArrayList<UserInfo> call() throws Exception {
                        return (ArrayList<UserInfo>) userInfoDAO.getAllRecords();
                    }
                });
        try{
            return future.get();
        }catch (InterruptedException|ExecutionException e){
            Log.i(MainActivity.TAG, "Problem when getting all UserInfor in the repository");
        }
        return null;
    }

    public ArrayList<Integer> getAllWaterLogs(){
        Future<ArrayList<Integer>> future = WellnessWizardDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<Integer>>() {
                    @Override
                    public ArrayList<Integer> call() throws Exception {
                        return (ArrayList<Integer>) userInfoDAO.getAllWaterRecords();
                    }
                });
        try{
            return future.get();
        }catch (InterruptedException|ExecutionException e){
            Log.i(MainActivity.TAG, "Problem when getting all UserInfor in the repository");
        }
        return null;
    }

    public void insertUserInfo(UserInfo userInfo){
        WellnessWizardDatabase.databaseWriteExecutor.execute(()->{
            userInfoDAO.insert(userInfo);
        });
    }

    public void insertUser(User... user){
        WellnessWizardDatabase.databaseWriteExecutor.execute(()->{
            userDAO.insert(user);
        });
    }

    public LiveData<User> getUserByUserName(String username) {
        return userDAO.getUserbyUserName(username);
    }

    public LiveData<User> getUserByUserId(int userId) {
        return userDAO.getUserbyUserId(userId);
    }

}

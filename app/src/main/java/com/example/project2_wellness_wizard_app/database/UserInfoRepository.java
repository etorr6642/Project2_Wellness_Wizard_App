package com.example.project2_wellness_wizard_app.database;

import android.app.Application;
import android.util.Log;

import com.example.project2_wellness_wizard_app.MainActivity;
import com.example.project2_wellness_wizard_app.database.entities.User;
import com.example.project2_wellness_wizard_app.database.entities.UserInfo;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class UserInfoRepository {
    private UserInfoDAO userInfoDAO;
    private ArrayList<UserInfo> allLogs;

    public UserInfoRepository(Application application){
        WellnessWizardDatabase db = WellnessWizardDatabase.getDatabase(application);
        this.userInfoDAO = db.userInfoDAO();
        this.allLogs = (ArrayList<UserInfo>) this.userInfoDAO.getAllRecords();
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

    public void insertUserInfo(UserInfo userInfo){
        WellnessWizardDatabase.databaseWriteExecutor.execute(()->{
            userInfoDAO.insert(userInfo);
        });
    }
}

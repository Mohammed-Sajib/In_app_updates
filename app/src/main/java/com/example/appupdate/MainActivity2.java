package com.example.appupdate;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;

public class MainActivity2 extends AppCompatActivity {

    private int REQUEST_CODE = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //In-App Udpates Implementation (1st part)------------------------------------------------------
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(MainActivity2.this);
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {
                if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        & result.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)){
                    try {
                        appUpdateManager.startUpdateFlowForResult(result, AppUpdateType.FLEXIBLE,MainActivity2.this,REQUEST_CODE);
                    } catch (IntentSender.SendIntentException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        });

    }

    //In-App Udpates Implementation (2nd part)------------------------------------------------------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE){
            Toast.makeText(MainActivity2.this,"Start Download",Toast.LENGTH_SHORT).show();
            if (requestCode!=RESULT_OK){
                Log.d("mmm","Update Fail"+resultCode);
            }
        }
    }
}
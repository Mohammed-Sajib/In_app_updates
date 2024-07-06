
# Android-InApp-Update
InApp update library for Android, In-app update is a library a new request flow to prompt active users to update your app.

In-app updates works only with devices running Android 5.0 (API level 21) or higher, and requires you to use Play Core library 1.5.0 or higher. Additionally, in-app updates support apps running on only Android mobile devices and tablets, and Chrome OS devices.
  

```
And then in the other gradle file(may be your app gradle or your own module library gradle, but never add in both of them to avoid conflict.)
```java
dependencies {

    implementation 'androidx.appcompat:appcompat:1.7.0'
    implementation 'com.google.android.material:material:1.12.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.2.1'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.6.1'

    implementation "com.google.android.play:app-update:2.1.0"

}
```          
How to use
-----
**Step 1:** Initiate AppUpdateManager in MainActivity:
```java
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        inAppUpdate();
    }
```
**Step 2:** Initiate AppUpdateManager
```java

  public static final int RC_APP_UPDATE = 100;
    private AppUpdateManager mAppUpdateManager;

**Step 3:** void inAppUpdate
```java
 private void inAppUpdate() {
        mAppUpdateManager = AppUpdateManagerFactory.create(this);
        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {
                if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                    try {
                        mAppUpdateManager.startUpdateFlowForResult(result, AppUpdateType.IMMEDIATE, MainActivity.this
                                , RC_APP_UPDATE);

                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        mAppUpdateManager.registerListener(installStateUpdatedListener);
    }


**Step 4:**InstallStateUpdatedListener
```java
    //======================
    private InstallStateUpdatedListener installStateUpdatedListener = new InstallStateUpdatedListener() {
        @Override
        public void onStateUpdate(InstallState state) {
            if (state.installStatus() == InstallStatus.DOWNLOADED) {
                showCompletedUpdate();
            }
        }
    };

**Step 4:** void showCompletedUpdate
```java
    private void showCompletedUpdate() {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "New app is ready!",
                Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Install", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAppUpdateManager.completeUpdate();
            }
        });
        snackbar.show();

    }

**Step 5:** void onStop
```java
    @Override
    protected void onStop() {
        if (mAppUpdateManager != null)
            mAppUpdateManager.unregisterListener(installStateUpdatedListener);
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    /* we can check without requestCode == RC_APP_UPDATE because
    we known exactly there is only requestCode from  startUpdateFlowForResult() */
        if (requestCode == RC_APP_UPDATE && resultCode != RESULT_OK) {
            Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

**Step 4:** onResume
```java
    @Override
    protected void onResume() {
        super.onResume();
        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {
                if (result.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    try {
                        mAppUpdateManager.startUpdateFlowForResult(result, AppUpdateType.IMMEDIATE, MainActivity.this
                                , RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
```

After meeting these requirements, your app can support the following UX for in-app updates:

## Update flows:
Your app can use the Google Play Core libraries to support the following UX flows for in-app updates:
## Flexible updates:
Flexible updates provide background download and installation with graceful state monitoring. This UX flow is appropriate when it's acceptable for the user to use the app while downloading the update. For example, you might want to encourage users to try a new feature that's not critical to the core functionality of your app.

##### Immediate:
A full screen user experience that requires the user to update and restart the app in order to continue using the app. This UX is best for cases where an update is critical for continued use of the app. After a user accepts an immediate update, Google Play handles the update installation and app restart.

![Android-InApp-Update Immediate - Example](https://developer.android.com/images/app-bundle/immediate_flow.png)
##### Flexible: [BETA]
A user experience that provides background download and installation with graceful state monitoring. This UX is appropriate when it’s acceptable for the user to use the app while downloading the update. For example, you want to urge users to try a new feature that’s not critical to the core functionality of your app.

![Android-InApp-Update Flexible - Example](https://developer.android.com/images/app-bundle/flexible_flow.png)
##### Any Queries? or Feedback, please let me know by opening a [new issue](https://github.com/Mohammed-Sajib/In_app_updates)!

## Contact
#### Mohammad Sajib
* :facebook: Facebook: [https://www.facebook.com/mohammadsajib8/ "Mohaamad Sajib on Facebook")
* :email: e-mail: mdsajib3k@gmail.com
* :mag_right: LinkedIn: [Mohaamad Sajib](https://www.linkedin.com/in/mohammad-sajib0/ "Mohaamad Sajib on LinkedIn")
* :thumbsup: Twitter: [@Tech miui](https://twitter.com/tech_miui "Mohaamad Sajib on twitter")    
* :camera: Instagram: [@Mohaamad Sajib_](https://www.instagram.com/mohammad_sajib0/ "Mohaamad Sajib on Instagram")   

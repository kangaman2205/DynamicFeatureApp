package com.example.dynamicfeatureapp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.play.core.splitinstall.SplitInstallManager;
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory;
import com.google.android.play.core.splitinstall.SplitInstallRequest;
import com.google.android.play.core.splitinstall.SplitInstallSessionState;
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener;
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus;
import com.google.android.play.core.tasks.OnFailureListener;
import com.google.android.play.core.tasks.OnSuccessListener;

import static android.content.ContentValues.TAG;
public class DynamicModelDownloader
{
    private int sessionID;
    public void startSilentDownload(Context context)
    {
        SplitInstallManager splitInstallManager = SplitInstallManagerFactory.create(context);
        SplitInstallRequest.Builder splitInstallRequestBuilder = SplitInstallRequest.newBuilder();
        SplitInstallRequest splitInstallRequest = splitInstallRequestBuilder.addModule(MODULE_NAME).build();
        SplitInstallStateUpdatedListener listener = new SplitInstallStateUpdatedListener() {
            @Override
            public void onStateUpdate(SplitInstallSessionState splitInstallSessionState) {
                if(splitInstallSessionState.sessionId() == sessionID) {
                    if(splitInstallSessionState.status() == SplitInstallSessionStatus.INSTALLED)
                    {
                        Toast.makeText(context, "Dynamic Module downloaded", Toast.LENGTH_SHORT)
                             .show();
                    }
                }
            }
        };

        splitInstallManager.registerListener(listener);

        splitInstallManager.startInstall(splitInstallRequest)
                           .addOnFailureListener(new OnFailureListener() {
                               @Override
                               public void onFailure(Exception e) {
                                   Log.d(TAG, "Exception: " + e);
                               }
                           })
                           .addOnSuccessListener(new OnSuccessListener<Integer>() {
                               @Override
                               public void onSuccess(Integer sessionId) {
                                   sessionID = sessionId;
                               }
                           });
    }

    public static boolean isFeatureInstalled(Context context)
    {
        SplitInstallManager splitInstallManager = SplitInstallManagerFactory.create((context));
        return splitInstallManager.getInstalledModules().contains(MODULE_NAME);

    }

    private static final String MODULE_NAME = "sampleModule";
}

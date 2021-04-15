package com.example.dynamicfeatureapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.play.core.splitcompat.SplitCompat;
import com.google.android.play.core.splitinstall.SplitInstallManager;
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory;
import com.google.android.play.core.splitinstall.SplitInstallRequest;
public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(newBase);
        SplitCompat.install(this);
    }

    /*
    download the module and load one parent .so
     */
    public void startDownload(View view)
    {
        new DynamicModelDownloader().startSilentDownload(this.getApplicationContext());
    }

    public void loadSingleLib(View view)
    {
        if(DynamicModelDownloader.isFeatureInstalled(getApplicationContext()))
        {
            System.loadLibrary("native-lib");
            Toast.makeText(getApplicationContext(), "Main Library loaded", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadAllLibWithDependencies(View view)
    {
        if(DynamicModelDownloader.isFeatureInstalled(getApplicationContext()))
        {
            System.loadLibrary("dependentSymbol");
            System.loadLibrary("native-lib");
            Toast.makeText(getApplicationContext(), "All Libraries loaded", Toast.LENGTH_SHORT).show();
        }
    }
}
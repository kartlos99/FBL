package com.example.kdiakonidze.fbsimplelogin;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by k.diakonidze on 9/1/2015.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

    }


}

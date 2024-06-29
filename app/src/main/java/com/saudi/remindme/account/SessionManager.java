package com.saudi.remindme.account;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String SHARED_PREF_NAME = "loginPref";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String IS_LOGIN = "IsLogin";
    private static final String KEY_USER_TYPE = "type";
    private static SessionManager mInstance;
    private static Context mCtx;

    private SessionManager(Context context) {
        mCtx = context;
    }

    public static synchronized SessionManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SessionManager(context);
        }
        return mInstance;
    }

    public boolean createSession(String userId, String name, String email, String userType, boolean remember) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NAME, name);
        editor.putBoolean(IS_LOGIN, remember);
        // editor.putString(KEY_USER_NAME, userName);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_USER_ID, userId);
        editor.putString(KEY_USER_TYPE, userType);
        // editor.putBoolean(KEY_REMEMBER, remember);
        editor.apply();

        return true;
    }

  /*  public boolean isRemember() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(KEY_REMEMBER, false);
    }*/

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    public boolean logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }


    public String getKeyUserId() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_ID, null);
    }

    public String getKeyName() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NAME, null);
    }

    public String getKeyEmail() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, null);
    }

    public String getKeyUserType() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_TYPE, null);
    }
}



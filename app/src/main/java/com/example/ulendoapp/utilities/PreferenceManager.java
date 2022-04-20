package com.example.ulendoapp.utilities;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * The type Preference manager.
 */
public class PreferenceManager {
    private final SharedPreferences sharedPreferences;

    /**
     * Instantiates a new Preference manager.
     *
     * @param context the context
     */
    public PreferenceManager(Context context){
        sharedPreferences = context.getSharedPreferences(Constants.KEY_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Put boolean.
     *
     * @param key   the key
     * @param value the value
     */
    public void putBoolean(String key, Boolean value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * Get boolean boolean.
     *
     * @param key the key
     * @return the boolean
     */
    public Boolean getBoolean(String key){
        return sharedPreferences.getBoolean(key, false);
    }

    /**
     * Put string.
     *
     * @param key   the key
     * @param value the value
     */
    public void putString(String key, String value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * Get string string.
     *
     * @param key the key
     * @return the string
     */
    public String getString(String key){
        return sharedPreferences.getString(key, null);
    }

    /**
     * Clear.
     */
    public void clear(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}

package org.d3ifcool.testing;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context){
        sharedPreferences = context.getSharedPreferences("AppKey",0);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public void setFlag(Boolean flag,String idkandang){
        editor.putBoolean(idkandang , flag);
        editor.commit();

    }

    public boolean getFlag(String idkandang){
        return sharedPreferences.getBoolean(idkandang,false);

    }

    public void setCurrentTime(String currentTime){
        editor.putString("KEY_TIME",currentTime);
        editor.commit();
    }

    public String getCurrentTime(){
        return sharedPreferences.getString("KEY_TIME","");
    }
}

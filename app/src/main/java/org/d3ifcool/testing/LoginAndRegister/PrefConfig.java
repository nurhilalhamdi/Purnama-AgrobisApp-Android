package org.d3ifcool.testing.LoginAndRegister;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.orgd3if4019.testing.R;

public class PrefConfig {

    private SharedPreferences sharedPreferences;
    private Context context;

    public PrefConfig(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_file), Context.MODE_PRIVATE);
    }

    public void writeLoginStatus(boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.pref_login_status),status);
        editor.commit();
    }
    

    public boolean readLoginStatus(){
        return sharedPreferences.getBoolean(context.getString(R.string.pref_login_status),false);
    }

    public void writeName(String nama){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_user_name),nama);
        editor.commit();
    }



    public String readName(){
        return sharedPreferences.getString(context.getString(R.string.pref_user_name),"User");
    }


    public void writeId(String id){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_user_id),id);
        editor.commit();
    }



    public String readId(){
        return sharedPreferences.getString(context.getString(R.string.pref_user_id),"Id");
    }



    public void displayToast(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}

package project.recsound.common;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    static final String PREFERENCES = "RECSOUND";

    public static void setDataObject(Context context, String data){
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES,context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(data,"data_object");
        editor.commit();
    }

    public static String getDataObject (Context context){
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES,context.MODE_PRIVATE);
        return preferences.getString("data_object","not_data");
    }

    public static void setServiceStart(Context context, Boolean isStart){
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES,context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("service_is_start",isStart);
        editor.commit();
    }

    public static Boolean isServiceStart (Context context){
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES,context.MODE_PRIVATE);
        return preferences.getBoolean("service_is_start",false);
    }

    public static void setNumberPowerSettings(Context context, String number){
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES,context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("number_power",number);
        editor.commit();
    }

    public static String getSettingsNumberPower(Context context){
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES,context.MODE_PRIVATE);
        return preferences.getString("number_power","3");
    }

    public static void setNumberTimeSettings(Context context, String number){
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES,context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("number_time",number);
        editor.commit();
    }

    public static String getSettingsNumberTime(Context context){
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES,context.MODE_PRIVATE);
        return preferences.getString("number_time","5");
    }

    public static void setSettingsVibrate(Context context, Boolean vibrate){
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES,context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("vibrate",vibrate);
        editor.commit();
    }

    public static Boolean getSettingsVibrate (Context context){
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES,context.MODE_PRIVATE);
        return preferences.getBoolean("vibrate",true);
    }

    public static void setSettingsNotification(Context context, Boolean notification){
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES,context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("notification",notification);
        editor.commit();
    }

    public static Boolean getSettingsNotification (Context context){
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES,context.MODE_PRIVATE);
        return preferences.getBoolean("notification",true);
    }

    public static void setVisibleTutorial(Context context, Boolean tutorial){
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES,context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("tutorial",tutorial);
        editor.commit();
    }

    public static Boolean getVisibleTutorial (Context context){
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES,context.MODE_PRIVATE);
        return preferences.getBoolean("tutorial",true);
    }




}

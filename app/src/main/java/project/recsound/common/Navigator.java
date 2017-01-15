package project.recsound.common;

import android.content.Context;
import android.content.Intent;

import project.recsound.modules.main.MainActivity;
import project.recsound.modules.settings.SettingsActivity;

/**
 * Created by susy on 20/12/16.
 */

public class Navigator {

    public static void startMainActivity(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        context.startActivity(intent);
    }

    public static void startSettingsActivity(Context context){
        Intent intent = new Intent(context, SettingsActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        context.startActivity(intent);
    }

}

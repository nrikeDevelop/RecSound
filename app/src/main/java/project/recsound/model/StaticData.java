package project.recsound.model;

import android.os.Environment;

/**
 * Created by susy on 10/01/17.
 */

public class StaticData {

    static String name_folder = "RecSound";
    public static String  path_folder = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+name_folder;

    public static String getPath_folder() {
        return path_folder;
    }

}

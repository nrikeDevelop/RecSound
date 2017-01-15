package project.recsound.modules.main;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import java.util.ArrayList;

import nucleus.presenter.Presenter;
import project.recsound.common.Files;
import project.recsound.common.Preferences;
import project.recsound.common.RecService;
import project.recsound.model.FileSound;
import project.recsound.model.StaticData;

/**
 * Created by susy on 20/12/16.
 */

public class MainPresenter extends Presenter<MainActivity> {

    public void startService(Context context){
        Intent serviceIntent = new Intent(context, RecService.class);
        if(!Preferences.isServiceStart(context)){
            context.startService(serviceIntent);
            Preferences.setServiceStart(context,true);
        }else{
            context.stopService(serviceIntent);
            context.startService(serviceIntent);
        }
    }

    public void requestListSounds(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                final ArrayList <FileSound> fileSounds = Files.getFilesList(StaticData.getPath_folder());
                if(fileSounds.size() > 0){
                    if(getView() != null) getView().successList(fileSounds);
                }else{
                    if(getView() != null) getView().errorList();
                }
            }
        },750);

    }

}

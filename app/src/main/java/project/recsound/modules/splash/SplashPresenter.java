package project.recsound.modules.splash;

import android.os.Handler;

import nucleus.presenter.Presenter;

/**
 * Created by susy on 20/12/16.
 */

public class SplashPresenter extends Presenter<SplashActivity> {

    public void requestData(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getView() != null) getView().onSuccess();
            }
        },2000);
    }

}

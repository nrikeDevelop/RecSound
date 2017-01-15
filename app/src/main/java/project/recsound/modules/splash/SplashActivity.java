package project.recsound.modules.splash;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusActivity;
import project.recsound.R;
import project.recsound.common.Navigator;

@RequiresPresenter(SplashPresenter.class)
public class SplashActivity extends NucleusActivity<SplashPresenter> {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = this;

        setViews();
    }

    public void setViews(){
        setFonts();
        getPresenter().requestData();
    }

    public void setFonts(){

    }

    public void onSuccess(){
        Navigator.startMainActivity(context);
    }

    public void onError(String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}

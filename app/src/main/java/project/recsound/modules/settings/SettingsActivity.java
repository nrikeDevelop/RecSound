package project.recsound.modules.settings;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusActivity;
import project.recsound.R;
import project.recsound.common.Preferences;

@RequiresPresenter(SettingsPresenter.class)
public class SettingsActivity extends NucleusActivity<SettingsPresenter> {

    @BindView(R.id.settings_service) TextView serviceText;
    @BindView(R.id.settings_number_text) TextView numberPowerText;
    @BindView(R.id.settings_time_number_text) TextView numberTimeText;
    @BindView(R.id.settings_sitch_notification) Switch notificationSwitch;
    @BindView(R.id.settings_sitch_vibrate)Switch vibrateSwitch;
    @BindView(R.id.settings_sitch_tutorial) Switch tutorialSwitch;

    Context context;
    int numberPower;
    int numberWait;
    //POWER
    @OnClick(R.id.settings_more_button)
    public void onClickMore(){
        if(numberPower < 5){
            numberPower = numberPower + 1;
            Preferences.setNumberPowerSettings(this,String.valueOf(numberPower));
            numberPowerText.setText(String.valueOf(numberPower));
        }
    }

    @OnClick(R.id.setting_less_button)
    public void onClickLess(){

        if(numberPower > 2){
            numberPower = numberPower-1;
            Preferences.setNumberPowerSettings(this,String.valueOf(numberPower));
            numberPowerText.setText(String.valueOf(numberPower));
        }
    }

    //TIME
    @OnClick(R.id.settings_time_more_button)
    public void onClickMoreTime(){

        if(numberWait >= 10){
            numberWait = numberWait + 5;
        }else{
            numberWait = numberWait + 1;
        }
        Preferences.setNumberTimeSettings(context,String.valueOf(numberWait));
        numberTimeText.setText(String.valueOf(numberWait));

    }

    @OnClick(R.id.settings_time_less_button)
    public void onClickLessTime(){
        if(numberWait > 1){
            if(numberWait >= 10){
                numberWait = numberWait - 5;
            }else{
                numberWait = numberWait - 1;
            }            Preferences.setNumberTimeSettings(context,String.valueOf(numberWait));
            numberTimeText.setText(String.valueOf(numberWait));
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ButterKnife.bind(this);
        setUpViews();
    }

    public void setUpViews(){
        setUpFonts();
        context = this;

        numberPowerText.setText(Preferences.getSettingsNumberPower(this));
        numberPower = Integer.parseInt(numberPowerText.getText().toString());

        numberTimeText.setText(Preferences.getSettingsNumberTime(this));
        numberWait = Integer.parseInt(numberTimeText.getText().toString());

        notificationSwitch.setChecked(Preferences.getSettingsNotification(this));
        vibrateSwitch.setChecked(Preferences.getSettingsVibrate(this));
        tutorialSwitch.setChecked(Preferences.getVisibleTutorial(this));


        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Preferences.setSettingsNotification(context,b);
            }
        });

        vibrateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Preferences.setSettingsVibrate(context,b);
            }
        });

        tutorialSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Preferences.setVisibleTutorial(context,b);
                if(b){
                    Toast.makeText(context, getResources().getString(R.string.toast_restart_app), Toast.LENGTH_SHORT).show();
                }
            }
        });

        if(Preferences.isServiceStart(this)){
            serviceText.setText(getResources().getString(R.string.settings_service_actived));
        }else{
            serviceText.setText(getResources().getString(R.string.settings_service_disactived));
        }
    }

    public void setUpFonts(){
        Typeface mavenpro_bold = Typeface.createFromAsset(getAssets(),"MavenPro-Bold.ttf");
    }
}

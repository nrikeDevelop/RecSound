package project.recsound.modules.main;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusActivity;
import project.recsound.R;
import project.recsound.common.Files;
import project.recsound.common.Navigator;
import project.recsound.common.Preferences;
import project.recsound.model.FileSound;

@RequiresPresenter(MainPresenter.class)
public class MainActivity extends NucleusActivity<MainPresenter> implements ViewAdapter {

    @BindView(R.id.main_tutorial_relative_layout) RelativeLayout relativeLayoutTurotial;
    @BindView(R.id.main_tutorial_button) Button tutorialButton;
    @BindView(R.id.main_tutorial_checkbox)CheckBox tutorialCheckbox;
    @BindView(R.id.main_tutorial_text)TextView tutorialText;
    @BindView(R.id.main_tutorial_title)TextView tutorialTitle;
    @BindView(R.id.toolbar_title)TextView toolbarTitle;
    @BindView(R.id.main_empty_text)TextView emptyText;
    @BindView(R.id.main_recicler_view) RecyclerView recyclerView;
    @BindView(R.id.main_loading) RelativeLayout mainLoading;
    @BindView(R.id.main_empty_relative_layout) RelativeLayout relativeLayoutEmpty;

    boolean PERMISION = false;
    int REQUEST_CODE_ASK_PERMISSIONS = 123;
    ArrayList<FileSound> fileSounds;
    Adapter adapter;
    ViewAdapter viewAdapter;
    Context context;

    @OnClick(R.id.toolbar_settings)
    public void onCLickSettings(){
        Navigator.startSettingsActivity(this);
    }

    @OnClick(R.id.main_tutorial_button)
    public void onClickButtonTutorial(){
        relativeLayoutTurotial.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;
        setUpViews();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[] {
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, REQUEST_CODE_ASK_PERMISSIONS);
        }else{
            PERMISION = true;
            Files.createDirectory();
            getPresenter().startService(this);
            getPresenter().requestListSounds();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(REQUEST_CODE_ASK_PERMISSIONS == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Files.createDirectory();
                getPresenter().startService(this);
                getPresenter().requestListSounds();
                PERMISION = true;

            } else {

                Toast.makeText(this, getResources().getString(R.string.toast_permision_not_permited), Toast.LENGTH_SHORT).show();
                finish();

            }
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(PERMISION) getPresenter().requestListSounds();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void refreshList() {
        getPresenter().requestListSounds();
        mainLoading.setVisibility(View.VISIBLE);
    }

    public void setUpViews(){
        setFonts();

        if(Preferences.getVisibleTutorial(this)){
            relativeLayoutTurotial.setVisibility(View.VISIBLE);
        }else{
            relativeLayoutTurotial.setVisibility(View.GONE);
        }

        viewAdapter = this;
        fileSounds = new ArrayList<>();
        notyfiList();

        tutorialCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Preferences.setVisibleTutorial(context,false);
                }else{
                    Preferences.setVisibleTutorial(context,true);
                }
            }
        });
    }

    public void setFonts(){
        Typeface mavenpro_bold = Typeface.createFromAsset(getAssets(),"MavenPro-Bold.ttf");
        toolbarTitle.setTypeface(mavenpro_bold);
        emptyText.setTypeface(mavenpro_bold);
        tutorialText.setTypeface(mavenpro_bold);
        tutorialTitle.setTypeface(mavenpro_bold);
        tutorialButton.setTypeface(mavenpro_bold);
        tutorialCheckbox.setTypeface(mavenpro_bold);
    }

    public void successList(ArrayList<FileSound> fileSounds){
        this.fileSounds.clear();
        this.fileSounds = fileSounds;
        notyfiList();
        relativeLayoutEmpty.setVisibility(View.GONE);
        mainLoading.setVisibility(View.GONE);
    }

    public void errorList(){
        this.fileSounds.clear();
        notyfiList();
        relativeLayoutEmpty.setVisibility(View.VISIBLE);
        mainLoading.setVisibility(View.GONE);

    }

    public void notyfiList(){
        adapter = new Adapter(fileSounds,this,viewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

}



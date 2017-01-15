package project.recsound.common;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;
import java.util.ArrayList;

import project.recsound.R;
import project.recsound.model.FileSound;
import project.recsound.model.StaticData;

/**
 * Created by susy on 10/01/17.
 */

public class Files {

    public static boolean createDirectory(){
        File folder = new File(StaticData.getPath_folder());
        if(!folder.exists()){
            folder.mkdirs();
            return true;
        }else{
            return false;
        }
    }

    public static ArrayList<FileSound> getFilesList(String path){

        ArrayList<FileSound> files = new ArrayList<>();
        FileSound fileSound;

        File f = new File(path);
        String sound [] = f.list();

        for ( int i = 0; i < sound.length; i++ ){
            String split_file [] = sound[i].split(">");fileSound = new FileSound(split_file[0],split_file[1]);
            files.add(fileSound);
        }

        return files;
    }

    public static boolean renameFile(FileSound file, String fileNewName){
        String fileToRename = file.getTitle()+">"+file.getDate()+">.mp3";
        String fileRenamed = fileNewName+">"+file.getDate()+">.mp3";

        File f1 = new File(StaticData.getPath_folder()+"/"+fileToRename);
        File f2 = new File(StaticData.getPath_folder()+"/"+fileRenamed);

        f1.renameTo(f2);
        return true;
    }

    public static boolean playFile(FileSound file, Context context){

        String file_path = StaticData.getPath_folder()+"/"+file.getTitle()+">"+file.getDate()+">.mp3";
        File f = new File(file_path);
/*
        MediaPlayer mediaPlayer = new MediaPlayer();
        try{
            mediaPlayer.setDataSource(file_path);
            mediaPlayer.prepare();
            mediaPlayer.start();
        }catch (Exception e){
            return false;
        }
        */
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
        Uri data = Uri.fromFile(f);
        intent.setDataAndType(data, "audio/mp3");
        context.startActivity(intent);

        return true;
    }

    public static Boolean removeFile(FileSound file){
        String file_path = StaticData.getPath_folder()+"/"+file.getTitle()+">"+file.getDate()+">.mp3";
        File f = new File(file_path);
        if(f.delete()){
            return true;
        }else{
            return false;
        }
    }

    public static Boolean shareFile(Context context, FileSound file){

        String file_path = StaticData.getPath_folder()+"/"+file.getTitle()+">"+file.getDate()+">.mp3";

        Intent shareFile = new Intent(Intent.ACTION_SEND);
        shareFile.setType("audio/*");
        shareFile.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(file_path)));
        context.startActivity(Intent.createChooser(shareFile,context.getResources().getString(R.string.chooser_audio)));

        return true;
    }
    
}

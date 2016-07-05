package myapp.tae.ac.uk.simplegameinfo.Util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import myapp.tae.ac.uk.simplegameinfo.Constants.Constants;
import myapp.tae.ac.uk.simplegameinfo.Model.Data;

/**
 * Created by Karma on 09/06/16.
 */
public class FileUtil {
    public static void writeFile(Activity context, String path, String data){
        try {
            FileOutputStream fos = context.openFileOutput(path, context.MODE_PRIVATE);
            OutputStreamWriter outWriter = new OutputStreamWriter(fos);
            outWriter.write(data);
            outWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFile(Activity context, String path){
        try {
            FileInputStream fis = context.openFileInput(Constants.DATA_FILE_PATH);
            InputStreamReader isReader = new InputStreamReader(fis);
            char[] inputBuffer = new char[128];
            String data = "";
            int charRead;
            while ((charRead = isReader.read(inputBuffer))>0){
                String readString=String.copyValueOf(inputBuffer,0,charRead);
                data +=readString;
            }
            isReader.close();
            return data;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Data> convertFileDataTextToList(String data){
        List<Data> listData = new ArrayList<>();
        String[] arrayGames = data.split(Constants.GAME_SEPARATOR);
        Data gameData;
        for (int i = 0; i < arrayGames.length; i++) {
            String game = arrayGames[i];
            String[] arrayGameData = game.split(Constants.GAME_DATA_SEPARATOR_REGEX);
            gameData = new Data();
            gameData.setName(arrayGameData[0]);
            gameData.setJackpot(Integer.parseInt(arrayGameData[1]));
            gameData.setDate(arrayGameData[2]);
            listData.add(gameData);
        }
        return listData;
    }

    public static String convertListToFileDataText(List<Data> listData){
        String fileData = "";
        for (int i = 0; i < listData.size(); i++) {
            fileData+=listData.get(i).getName()+Constants.GAME_DATA_SEPARATOR;
            fileData+=listData.get(i).getJackpot().toString()+Constants.GAME_DATA_SEPARATOR;
            fileData+=listData.get(i).getDate();
            if(i!=listData.size()-1)
                fileData+=Constants.GAME_SEPARATOR;
        }
        return fileData;
    }
}

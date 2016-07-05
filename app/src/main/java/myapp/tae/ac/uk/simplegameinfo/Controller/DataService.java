package myapp.tae.ac.uk.simplegameinfo.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import javax.inject.Inject;

import myapp.tae.ac.uk.simplegameinfo.Api.GameApiService;
import myapp.tae.ac.uk.simplegameinfo.Constants.Constants;
import myapp.tae.ac.uk.simplegameinfo.DI.components.APIComponent;
import myapp.tae.ac.uk.simplegameinfo.Interfaces.DataServiceInterface;
import myapp.tae.ac.uk.simplegameinfo.Model.Data;
import myapp.tae.ac.uk.simplegameinfo.Model.Games;
import myapp.tae.ac.uk.simplegameinfo.Util.FileUtil;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by Karma on 20/06/16..
 */
public class DataService extends java.util.Observable implements DataServiceInterface {
    @Inject
    GameApiService apiService;
    @Inject
    SharedPreferences sharedPreferences;
    private List<Data> gameData;
    private final static int READ_BUFFER_SIZE = 128;

    @Override
    public void initiateInjectionGraph(APIComponent apiComponent) {
        apiComponent.inject(this);
    }

    @Override
    public List<Data> getGameData() {
        return gameData;
    }

    @Override
    public Subscription retrieveLatestGames() {
        Subscription subscription = apiService.getResult()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Games>() {
                    @Override
                    public void onCompleted() {
                        setChanged();
                        notifyObservers(Constants.Game_DATA_FETCHED);
                    }

                    @Override
                    public void onError(Throwable e) {
                        setChanged();
                        notifyObservers(Constants.GAME_DATA_FETCH_ERROR);
                    }

                    @Override
                    public void onNext(Games games) {
                        if(games!=null)
                            gameData = games.getData();
                    }
                });
        return subscription;
    }

    @Override
    public String readFile(Activity context) {
        String data = FileUtil.readFile(context, Constants.DATA_FILE_PATH);
        return data;
    }

    @Override
    public void writeToFile(Activity context, String data) {
        FileUtil.writeFile(context, Constants.DATA_FILE_PATH, data);
    }

    @Override
    public void saveLastDataRetrievalTime(long readTime) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(Constants.LAST_DATA_RETRIEVAL_TIME, readTime);
        editor.commit();
    }

    @Override
    public long getLastDataRetrievalTimeInMilli() {
        long lastDataRetrievalTime = sharedPreferences.getLong(Constants.LAST_DATA_RETRIEVAL_TIME,0);
        return lastDataRetrievalTime;
    }


}

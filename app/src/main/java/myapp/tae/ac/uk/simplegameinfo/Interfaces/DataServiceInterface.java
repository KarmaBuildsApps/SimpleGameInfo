package myapp.tae.ac.uk.simplegameinfo.Interfaces;

import android.app.Activity;
import android.content.Context;

import java.util.List;

import myapp.tae.ac.uk.simplegameinfo.DI.components.APIComponent;
import myapp.tae.ac.uk.simplegameinfo.Model.Data;
import myapp.tae.ac.uk.simplegameinfo.Model.Games;
import rx.Subscription;

/**
 * Created by Karma on 20/06/16..
 */
public interface DataServiceInterface {

    public void initiateInjectionGraph(APIComponent apiComponent);


    public List<Data> getGameData();

    public Subscription retrieveLatestGames();

    public String readFile(Activity context);

    public void writeToFile(Activity context, String data);

    public void saveLastDataRetrievalTime(long readTime);

    public long getLastDataRetrievalTimeInMilli();
}

package myapp.tae.ac.uk.simplegameinfo.Controller;


import android.app.Activity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import myapp.tae.ac.uk.simplegameinfo.Constants.Constants;
import myapp.tae.ac.uk.simplegameinfo.DI.components.APIComponent;
import myapp.tae.ac.uk.simplegameinfo.Interfaces.DataServiceInterface;
import myapp.tae.ac.uk.simplegameinfo.Interfaces.GameView;
import myapp.tae.ac.uk.simplegameinfo.Model.Data;
import myapp.tae.ac.uk.simplegameinfo.R;
import myapp.tae.ac.uk.simplegameinfo.Util.FileUtil;
import rx.Subscription;

/**
 * Created by Karma on 20/06/16.
 */
public class GameController implements Observer {

    private final GameView view;
    private DataServiceInterface dataService;

    public GameController(GameView view){
        this.view = view;
        dataService = new DataService();
        ((DataService) dataService).addObserver(this);
    }

    public Subscription retrieveData() {
        view.showProgressDialog();
        return dataService.retrieveLatestGames();
    }

    public void setDataServiceApiComponent(APIComponent apiComponent){
        dataService.initiateInjectionGraph(apiComponent);
    }

    public void showDataFetchError(int resId) {
        view.showDataFetchError(resId);
    }

    public Data reformatData(Data gameData) {
        String gameDate = gameData.getDate();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
        Date localDate;
        try {
            localDate = df.parse(gameDate);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm yyyy-MM-dd", Locale.getDefault());
            gameDate = sdf.format(localDate);
            gameData.setDate(gameDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return gameData;
    }

    public void getGameData() {
        long lastDataRetrievalTime = dataService.getLastDataRetrievalTimeInMilli();
        long currentTime = System.currentTimeMillis();
        long difference = currentTime-lastDataRetrievalTime;

        if(lastDataRetrievalTime!=0 && difference<=Constants.DEFAULT_DATA_EXPIRY_TIME){
            String dataFromFile = dataService.readFile((Activity) view);
            if(dataFromFile!=null){
                List<Data> dataList = FileUtil.convertFileDataTextToList(dataFromFile);
                view.updateList(dataList);
                return;
            }
        }

        Subscription subscription = retrieveData();
        view.addSubscription(subscription);
    }

    @Override
    public void update(Observable observable, Object data) {
        view.stopProgressDialog();
        switch ((int)data){
            case Constants.Game_DATA_FETCHED:
                List<Data> gameData = dataService.getGameData();
                view.updateList(gameData);
                String dataForFile = FileUtil.convertListToFileDataText(gameData);
                dataService.writeToFile((Activity) view,dataForFile );
                long currentTime = System.currentTimeMillis();
                dataService.saveLastDataRetrievalTime(currentTime);
            break;
            case Constants.GAME_DATA_FETCH_ERROR:
                showDataFetchError(R.string.data_fetch_error);
                break;
            default:
        }
    }
}

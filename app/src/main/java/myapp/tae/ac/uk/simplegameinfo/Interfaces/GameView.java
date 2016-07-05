package myapp.tae.ac.uk.simplegameinfo.Interfaces;

import java.util.List;

import myapp.tae.ac.uk.simplegameinfo.Model.Data;
import rx.Subscription;

/**
 * Created by Karma on 20/06/16.
 */
public interface GameView {

    public void updateList(List<Data> gameList);

    public void showProgressDialog();

    public void stopProgressDialog();

    void showDataFetchError(int resId);

    void addSubscription(Subscription subscription);
}

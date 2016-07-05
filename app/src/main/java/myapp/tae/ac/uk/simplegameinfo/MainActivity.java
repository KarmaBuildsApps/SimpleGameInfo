package myapp.tae.ac.uk.simplegameinfo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import myapp.tae.ac.uk.simplegameinfo.Constants.Constants;
import myapp.tae.ac.uk.simplegameinfo.DI.components.APIComponent;
import myapp.tae.ac.uk.simplegameinfo.Interfaces.GameView;
import myapp.tae.ac.uk.simplegameinfo.Model.Data;
import myapp.tae.ac.uk.simplegameinfo.Controller.GameController;
import myapp.tae.ac.uk.simplegameinfo.UI.GameDetailActivity;
import myapp.tae.ac.uk.simplegameinfo.UI.adapter.GameListAdapter;
import myapp.tae.ac.uk.simplegameinfo.Util.RxUtils;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity implements GameView {
    @Bind(R.id.rcGameList)
    RecyclerView rcGameList;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private GameListAdapter adapter;
    private GameController controller;
    private ProgressDialog progressDialog;
    private CompositeSubscription compositeSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        controller = new GameController(this);
        setInjections();
        compositeSubscription = new CompositeSubscription();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data ... ");
        rcGameList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GameListAdapter(this);
        rcGameList.setAdapter(adapter);
        controller.getGameData();
    }

    public void showDetailActivity(Data gameData) {
        Intent intent = new Intent(this, GameDetailActivity.class);
        gameData = controller.reformatData(gameData);
        intent.putExtra(Constants.GAME_DETAIL, gameData);
        startActivity(intent);

    }

    @Override
    public void showProgressDialog() {
        if(!progressDialog.isShowing())
            progressDialog.show();
    }

    @Override
    public void stopProgressDialog() {
        if(progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void showDataFetchError(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_LONG).show();
    }

    @Override
    public void addSubscription(Subscription subscription) {
        compositeSubscription.add(subscription);
    }

    @Override
    public void updateList(List<Data> gameList) {
        adapter.setData(gameList);
    }

    private void setInjections() {
        ButterKnife.bind(this);
        APIComponent apiComponent = ((MyApplication) getApplication()).getApiComponent();
        controller.setDataServiceApiComponent(apiComponent);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        ArrayList<Data> gameData = savedInstanceState.getParcelableArrayList(Constants.GAME_LIST_RESTORE);
        adapter.setData(gameData);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(Constants.GAME_LIST_RESTORE,
                (ArrayList<? extends Parcelable>) adapter.getGameList());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        compositeSubscription = RxUtils.getNewCompositeSubIfUnsubscribe(compositeSubscription);
    }

    @Override
    protected void onPause() {
        super.onPause();
        RxUtils.unsubscribeIfNotNull(compositeSubscription);
    }
}

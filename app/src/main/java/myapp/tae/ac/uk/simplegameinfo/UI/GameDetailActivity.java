package myapp.tae.ac.uk.simplegameinfo.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;


import butterknife.Bind;
import butterknife.ButterKnife;
import myapp.tae.ac.uk.simplegameinfo.Constants.Constants;
import myapp.tae.ac.uk.simplegameinfo.Model.Data;
import myapp.tae.ac.uk.simplegameinfo.R;

/**
 * Created by Karma on 20/06/16.
 */
public class GameDetailActivity extends AppCompatActivity {
    private static final String TAG = GameDetailActivity.class.getSimpleName();
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tvTitle)
    TextView tvGameTitle;
    @Bind(R.id.tvJackpot)
    TextView tvJackpot;
    @Bind(R.id.tvDate)
    TextView tvDate;
    private Data gameDetailInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_detail_activity);
        ButterKnife.bind(this);
        setupToolbar();
        if(gameDetailInfo==null){
            gameDetailInfo = getIntent().getParcelableExtra(Constants.GAME_DETAIL);
        }else {
            gameDetailInfo = savedInstanceState.getParcelable(Constants.GAME_DETAIL_RESTORE);
        }
        setValuesForGameViews();
    }

    private void setValuesForGameViews() {
        tvGameTitle.setText(gameDetailInfo.getName());
        tvJackpot.setText("£"+ gameDetailInfo.getJackpot()+".00");
        tvDate.setText(gameDetailInfo.getDate());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Constants.GAME_DETAIL_RESTORE, gameDetailInfo);
        super.onSaveInstanceState(outState);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

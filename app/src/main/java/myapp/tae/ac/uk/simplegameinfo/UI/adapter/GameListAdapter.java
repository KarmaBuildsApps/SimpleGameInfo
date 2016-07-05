package myapp.tae.ac.uk.simplegameinfo.UI.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import myapp.tae.ac.uk.simplegameinfo.Interfaces.OnListItemClickListener;
import myapp.tae.ac.uk.simplegameinfo.MainActivity;
import myapp.tae.ac.uk.simplegameinfo.Model.Data;
import myapp.tae.ac.uk.simplegameinfo.R;

/**
 * Created by Karma on 20/06/16.
 */
public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.ViewHolder> {
    private Context context;
    private List<Data> gameData;

    public GameListAdapter(Context context){
        this.context = context;
        this.gameData = new ArrayList<>();
    }

    @Override
    public GameListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.game_row_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GameListAdapter.ViewHolder holder, int position) {
        final String game = gameData.get(position).getName();
        holder.tvGame.setText(game);
        holder.setListItemClickListener(new OnListItemClickListener() {
            @Override
            public void onListItemClicked(View view, int position) {
                ((MainActivity)context).showDetailActivity(gameData.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return gameData.size();
    }

    public void setData(List<Data> data) {
        this.gameData = data;
        notifyDataSetChanged();
    }

    public List<Data> getGameList() {
        return gameData;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @Bind(R.id.tvGame)
        TextView tvGame;
        private OnListItemClickListener listItemClickListener;

        public ViewHolder(View itemView) {
            super(itemView);
                ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void setListItemClickListener(OnListItemClickListener listItemClickListener){
            this.listItemClickListener = listItemClickListener;
        }
        @Override
        public void onClick(View v) {
            if(listItemClickListener!=null)
                listItemClickListener.onListItemClicked(v, getLayoutPosition());
        }
    }
}

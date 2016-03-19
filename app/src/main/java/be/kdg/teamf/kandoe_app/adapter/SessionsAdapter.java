package be.kdg.teamf.kandoe_app.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import be.kdg.teamf.kandoe_app.R;
import be.kdg.teamf.kandoe_app.resource.SessionResource;

/**
 * Created by admin on 18/03/2016.
 */
public class SessionsAdapter extends RecyclerView
        .Adapter<SessionsAdapter
        .DataObjectHolder> {
    private ArrayList<SessionResource> mDataset;
    private Context mContext;
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View
            .OnClickListener{
        TextView sessionName;
        TextView circles;
        TextView players;
        ImageView turn;
        CardView cardView;

        public DataObjectHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            sessionName = (TextView) itemView.findViewById(R.id.textViewSessionName);
            circles = (TextView) itemView.findViewById(R.id.textViewCircles);
            players = (TextView) itemView.findViewById(R.id.textViewPlayers);
            turn = (ImageView) itemView.findViewById(R.id.imageViewTurn);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public SessionsAdapter(Context context, ArrayList<SessionResource> myDataset) {
        mContext = context;
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.sessionName.setText(mDataset.get(position).getNameSession());
        holder.players.setText("Spelers: " + Integer.toString(mDataset.get(position).getAmountOfUsers()));
        holder.circles.setText("Cirkels: " + Integer.toString(mDataset.get(position).getAmountOfCircles()));
        // TODO: 19/03/2016 string.format
        // Check if logged in user is the user on turn in the session
        SharedPreferences prefs = mContext.getSharedPreferences("Logindetails", Context.MODE_PRIVATE);
        int loggedInUserId = prefs.getInt("id", -1);
        if (mDataset.get(position).getCurrentUser().equals(loggedInUserId)) {
            holder.turn.setImageResource(R.drawable.green_dot);
        }
        else {
            holder.turn.setImageResource(R.drawable.red_dot);
        }

        if (mDataset.get(position).isGameOver()) {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.closedSession));
            holder.turn.setImageResource(R.drawable.red_dot);
        }
        else {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.openSession));
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public SessionResource getItem(int position) {
        return mDataset.get(position);
    }

    public void setDataset(ArrayList<SessionResource> sessionResources) {
        mDataset = sessionResources;
        notifyDataSetChanged();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}

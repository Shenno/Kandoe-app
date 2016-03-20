package be.kdg.teamf.kandoe_app.adapter;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import be.kdg.teamf.kandoe_app.MainActivity;
import be.kdg.teamf.kandoe_app.R;
import be.kdg.teamf.kandoe_app.fragments.SessionPostFragment;
import be.kdg.teamf.kandoe_app.resource.CardSessionResource;
import be.kdg.teamf.kandoe_app.resource.SessionResource;


/**
 * Created by Shenno Willaert on 14/03/2016.
 */

/**
 * Adapter to handle the cards of a session
 * RecyclerSwipeAdapter is a thirdparty plugin to make swipe left and right
 * on menuitems possible.
 */
public class SessionAdapter extends RecyclerSwipeAdapter<SessionAdapter.SimpleViewHolder> {
    private Context mContext;
    private SessionResource session;

    public SessionAdapter(Context context, SessionResource objects) {
        this.mContext = context;
        this.session = objects;
    }

    public void setSession(SessionResource session) {
        this.session = session;
        notifyDataSetChanged();
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.swipelistlayout, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
        final List<CardSessionResource> cards = session.getCardSessionResources();
        final CardSessionResource card = cards.get(position);
        viewHolder.cardName.setText(card.getCard());
        viewHolder.ringColor.setText(String.format("%d", card.getDistanceToCenter()));
        Picasso.with(mContext).load(card.getImage()).into(viewHolder.ivCard);

        // Conditional formatting colors
        int color = pimpCard(card.getDistanceToCenter());
        viewHolder.ringColor.setBackgroundColor(color);

        // Swipe showmode
        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        // Drag From Left
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper1));

        // Handling different events when swiping
        viewHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onClose(SwipeLayout layout) {
                //when the SurfaceView totally cover the BottomView.
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                //you are swiping.
            }

            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {
                //when the BottomView totally show.
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                //when user's hand released.
            }
        });

        viewHolder.swipeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((((SwipeLayout) v).getOpenStatus() == SwipeLayout.Status.Close)) {
                }
            }
        });

        viewHolder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        viewHolder.btnPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkConditionsSession() ){
                    fragmentJump(session.getId(), card.getId());
                }
                else {
                    Snackbar.make(v, "Het is niet jouw beurt!", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        // mItemManger is member in RecyclerSwipeAdapter Class
        mItemManger.bindView(viewHolder.itemView, position);
    }

    protected boolean checkConditionsSession() {
        SharedPreferences prefs = mContext.getSharedPreferences("Logindetails", Context.MODE_PRIVATE);
        int loggedInUserId = prefs.getInt("id", -1);
        return session.getCurrentUser().equals(loggedInUserId) && !session.isGameOver();
    }

    private int pimpCard(Integer distanceToCenter) {
        int bgColor = ContextCompat.getColor(mContext, R.color.divider);
        if (distanceToCenter.equals(0)) {
            bgColor = ContextCompat.getColor(mContext, R.color.cardDistWinner);
        } else if (distanceToCenter.equals(1)) {
            bgColor = ContextCompat.getColor(mContext, R.color.cardDist1);
        } else if (distanceToCenter.equals(2)) {
            bgColor = ContextCompat.getColor(mContext, R.color.cardDist2);
        } else if (distanceToCenter.equals(3)) {
            bgColor = ContextCompat.getColor(mContext, R.color.cardDist3);
        } else if (distanceToCenter >= 4 && distanceToCenter <= session.getAmountOfCircles() ) {
            bgColor = ContextCompat.getColor(mContext, R.color.cardDist4);
        }
        return bgColor;
    }

    public void fragmentJump(int sessionId, int cardId) {
        if (mContext == null)
            return;
        if (mContext instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) mContext;
            Fragment frag = new SessionPostFragment();
            Bundle args = new Bundle();
            args.putInt("sessionId", sessionId);
            args.putInt("cardId", cardId);
            frag.setArguments(args);
            mainActivity.switchContent(frag);
        }
    }

    @Override
    public int getItemCount() {
        return session.getCardSessionResources().size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    //  ViewHolder Class

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        ImageView ivCard;
        TextView cardName;
        ImageButton btnPush;
        RelativeLayout row;
        TextView ringColor;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            ivCard = (ImageView) itemView.findViewById(R.id.cardImage);
            cardName = (TextView) itemView.findViewById(R.id.cardName);
            btnPush = (ImageButton) itemView.findViewById(R.id.btnPush);
            row = (RelativeLayout) itemView.findViewById(R.id.rowitem);
            ringColor = (TextView) itemView.findViewById(R.id.colorRing);
        }
    }
}

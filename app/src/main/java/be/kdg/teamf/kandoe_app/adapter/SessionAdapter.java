package be.kdg.teamf.kandoe_app.adapter;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import be.kdg.teamf.kandoe_app.MainActivity;
import be.kdg.teamf.kandoe_app.R;
import be.kdg.teamf.kandoe_app.fragments.HomeFragment;
import be.kdg.teamf.kandoe_app.resource.CardSessionResource;
import be.kdg.teamf.kandoe_app.resource.SessionResource;


/**
 * Created by admin on 14/03/2016.
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
        //    viewHolder.tvName.setText((item.getName()) + "  -  Row Position " + position);
        //    viewHolder.tvEmailId.setText(item.getEmailId());
        //    viewHolder.swipeLayout.getRootView().setBackgroundColor(Color.GREEN);

        final CardSessionResource card = cards.get(position);
        viewHolder.cardName.setText(card.getCard());
        viewHolder.score.setText(card.getDistanceToCenter().toString());
        Picasso.with(mContext).load(card.getImage()).into(viewHolder.ivCard);
        //// TODO: 15/03/2016 Betere kleuren
        if(card.getDistanceToCenter() == 8) {
            viewHolder.swipeLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.topcard));
        }
        else {
            viewHolder.swipeLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.background_holo_light));
        }

        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        // Drag From Left
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper1));

        // Drag From Right
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper));


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


                    //   Toast.makeText(mContext, " onClick : " + item.getName() + " \n" + item.getEmailId(), Toast.LENGTH_SHORT).show();
                }

            }
        });


        viewHolder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Toast.makeText(mContext, Integer.toString(position), Toast.LENGTH_SHORT).show();
            }
        });



        viewHolder.btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentJump(session.getId(), card.getId());
                //  Toast.makeText(v.getContext(), "Clicked on Map " + viewHolder.tvName.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });


        viewHolder.tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // Toast.makeText(view.getContext(), "Clicked on Share " + viewHolder.tvName.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             //   Toast.makeText(view.getContext(), "Clicked on Edit  " + viewHolder.tvName.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });



        // mItemManger is member in RecyclerSwipeAdapter Class
        mItemManger.bindView(viewHolder.itemView, position);

    }

    public void fragmentJump(int sessionId, int cardId) {
        if (mContext == null)
            return;
        if (mContext instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) mContext;
            Fragment frag = new HomeFragment();
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
        TextView score;
        TextView tvEdit;
        TextView tvShare;
        ImageButton btnLocation;
        RelativeLayout row;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            ivCard = (ImageView) itemView.findViewById(R.id.cardImage);
            cardName = (TextView) itemView.findViewById(R.id.cardName);
            score = (TextView) itemView.findViewById(R.id.score);
            tvEdit = (TextView) itemView.findViewById(R.id.tvEdit);
            tvShare = (TextView) itemView.findViewById(R.id.tvShare);
            btnLocation = (ImageButton) itemView.findViewById(R.id.btnLocation);
            row = (RelativeLayout) itemView.findViewById(R.id.rowitem);
        }
    }
}

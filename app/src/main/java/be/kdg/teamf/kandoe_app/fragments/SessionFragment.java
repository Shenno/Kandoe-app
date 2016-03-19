package be.kdg.teamf.kandoe_app.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;

import java.util.Collections;
import java.util.Comparator;

import be.kdg.teamf.kandoe_app.MainActivity;
import be.kdg.teamf.kandoe_app.R;
import be.kdg.teamf.kandoe_app.adapter.SessionAdapter;
import be.kdg.teamf.kandoe_app.deco.SimpleItemDividerDecoration;
import be.kdg.teamf.kandoe_app.resource.CardSessionResource;
import be.kdg.teamf.kandoe_app.resource.SessionResource;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static be.kdg.teamf.kandoe_app.application.KandoeApplication.getKandoeService;


/**
 * Created by admin on 11/03/2016.
 */
public class SessionFragment extends Fragment implements Callback<SessionResource> {

    private RecyclerView mRecyclerView;
    private SessionAdapter mAdapter;
    private SwipeRefreshLayout srl;
    private TextView sessionInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_session, container, false);
        sessionInfo = (TextView) rootView.findViewById(R.id.textViewInfoSession);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        // Layout Managers:
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setVisibility(View.VISIBLE);

        // Creating Adapter object
        //RipAdapter mAdapter = new RipAdapter(getActivity(), new ArrayList<Student>());
        mAdapter = new SessionAdapter(getActivity(), new SessionResource());

        // Setting Mode to Single to reveal bottom View for one item in List
        // Setting Mode to Mutliple to reveal bottom Views for multile items in List
        ((SessionAdapter) mAdapter).setMode(Attributes.Mode.Single);

        mRecyclerView.setAdapter(mAdapter);

        /* Scroll Listeners */
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e("RecyclerView", "onScrollStateChanged");

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        srl = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        srl.setColorSchemeColors(R.color.colorPrimary);
        /*
        * Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
 * performs a swipe-to-refresh gesture.
 */
        srl.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i("TEST", "onRefresh called from SwipeRefreshLayout");
                        updateSession();
                    }
                }
        );
        mRecyclerView.addItemDecoration(new SimpleItemDividerDecoration(getActivity()));
        updateSession();
        return rootView;
    }

    private SessionResource sortCards(SessionResource sessionResource) {
        Collections.sort(sessionResource.getCardSessionResources(), new Comparator<CardSessionResource>() {
            @Override
            public int compare(CardSessionResource lhs, CardSessionResource rhs) {
                return lhs.getDistanceToCenter().compareTo(rhs.getDistanceToCenter());
            }
        });
        return sessionResource;
    }

    private void updateSession() {
        SharedPreferences prefs = getActivity().getSharedPreferences("Logindetails", Context.MODE_PRIVATE);
        Bundle bundle = getArguments();
        int sessionId = bundle.getInt("sessionId", -1);
        getKandoeService().getSession(prefs.getString("token", null), sessionId, this);
    }

    @Override
    public void success(SessionResource sessionResource, Response response) {
        ((MainActivity) getActivity()).getSupActionBar().setTitle(sessionResource.getNameSession());
        sessionResource = sortCards(sessionResource);
        mAdapter.setSession(sessionResource);
        updateSessionInfo(sessionResource.isGameOver(), sessionResource.getCurrentUser());
        //Toast.makeText(getActivity(), Integer.toString(sessionResource.getCurrentUser()), Toast.LENGTH_LONG).show();
        srl.setRefreshing(false);
    }

    private void updateSessionInfo(boolean gameOver, Integer currentUser) {
        SharedPreferences prefs = getActivity().getSharedPreferences("Logindetails", Context.MODE_PRIVATE);
        int loggedInUser = prefs.getInt("id", -1);
        if (gameOver) {
            sessionInfo.setText("Het spel is afgelopen!");
            return;
        }
        else if (currentUser == loggedInUser) {
            sessionInfo.setText("Het is jouw beurt! Swipe een kaartje naar rechts.");
            return;
        }
        sessionInfo.setText("Wacht je beurt af!");
    }

    @Override
    public void failure(RetrofitError error) {
        Toast.makeText(getActivity(), "werktniet", Toast.LENGTH_LONG).show();
    }
}

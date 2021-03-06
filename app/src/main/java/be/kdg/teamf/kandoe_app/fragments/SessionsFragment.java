package be.kdg.teamf.kandoe_app.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import be.kdg.teamf.kandoe_app.MainActivity;
import be.kdg.teamf.kandoe_app.R;
import be.kdg.teamf.kandoe_app.adapter.SessionsAdapter;
import be.kdg.teamf.kandoe_app.resource.SessionResource;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static be.kdg.teamf.kandoe_app.application.KandoeApplication.getKandoeService;

/**
 * Created by Shenno Willaert on 18/03/2016.
 */

/**
 * Fragment where a list of sessions is shown belonging to the logged in user.
 * Conditional formatting included
 * RecyclerView used to show sessions in CardView
 */
public class SessionsFragment extends Fragment implements Callback<List<SessionResource>> {
    private RecyclerView mRecyclerView;
    private SessionsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_sessions, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new SessionsAdapter(getActivity(), new ArrayList<SessionResource>());
        mRecyclerView.setAdapter(mAdapter);
        updateSessions();
        mAdapter.setOnItemClickListener(new SessionsAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                SessionResource sessionResource = mAdapter.getItem(position);
                fragmentJump(sessionResource.getId());
            }
        });
        // Change title
        ((MainActivity) getActivity()).getSupActionBar().setTitle("Overzicht sessies");
        return rootView;
    }

    public void fragmentJump(int sessionId) {
        if (getActivity() == null) {
            return;
        }
        if (getActivity() instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) getActivity();
            Fragment frag = new SessionFragment();
            Bundle args = new Bundle();
            args.putInt("sessionId", sessionId);
            frag.setArguments(args);
            mainActivity.switchContent(frag);
        }
    }

    private void updateSessions() {
        SharedPreferences prefs = getActivity().getSharedPreferences("Logindetails", Context.MODE_PRIVATE);
        getKandoeService().getSessions(prefs.getString("token", null), this);
    }

    /**
     * Function to sort sessions
     * @param sessionResource
     * @return
     */
    private List<SessionResource> sortSessions(List<SessionResource> sessionResource) {
        Collections.sort(sessionResource, new Comparator<SessionResource>() {
            @Override
            public int compare(SessionResource lhs, SessionResource rhs) {
                int b1 = lhs.isGameOver() ? 1 : 0;
                int b2 = rhs.isGameOver() ? 1 : 0;

                return b1 - b2;
            }
        });
        return sessionResource;
    }

    @Override
    public void success(List<SessionResource> sessionResources, Response response) {
        sessionResources = sortSessions(sessionResources);
        ArrayList<SessionResource> resources = new ArrayList<>(sessionResources);
        mAdapter.setDataset(resources);
    }

    @Override
    public void failure(RetrofitError error) {
        Toast.makeText(getActivity(), "An error occurred!", Toast.LENGTH_LONG).show();
    }
}

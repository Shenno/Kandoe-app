package be.kdg.teamf.kandoe_app.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import be.kdg.teamf.kandoe_app.R;
import be.kdg.teamf.kandoe_app.resource.CardSessionResource;
import be.kdg.teamf.kandoe_app.resource.SessionResource;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static be.kdg.teamf.kandoe_app.application.KandoeApplication.getKandoeService;


/**
 * Created by Shenno Willaert on 15/03/2016.
 */

/**
 * Fragment to update a session when a card is moved
 */
public class SessionPostFragment extends Fragment implements Callback<SessionResource> {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        int cardId = bundle.getInt("cardId", -1);
        int sessionId = bundle.getInt("sessionId", -1);
        SharedPreferences prefs = getActivity().getSharedPreferences("Logindetails", Context.MODE_PRIVATE);
        String token = prefs.getString("token", null);
        CardSessionResource cardSessionResource = new CardSessionResource();
        cardSessionResource.setId(cardId);
        getKandoeService().updateSession(token, sessionId, cardSessionResource, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_sessionpost, container, false);
        return rootView;
    }

    @Override
    public void success(SessionResource sessionResource, Response response) {
        Snackbar.make(getView(), "Zet gelukt!", Snackbar.LENGTH_LONG).show();
        Bundle args = new Bundle();
        args.putInt("sessionId", sessionResource.getId());
        Fragment fragment = new SessionFragment();
        fragment.setArguments(args);
        FragmentManager fragMan = getFragmentManager();
        FragmentTransaction fragTran = fragMan.beginTransaction();
        fragTran.replace(R.id.frame_container, fragment);
        fragTran.addToBackStack(null);
        fragTran.commit();
    }

    @Override
    public void failure(RetrofitError error) {
        Snackbar.make(getView(), "Er is iets foutgelopen!", Snackbar.LENGTH_LONG).show();
    }
}

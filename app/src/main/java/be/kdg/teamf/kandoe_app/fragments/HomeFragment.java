package be.kdg.teamf.kandoe_app.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import be.kdg.teamf.kandoe_app.MainActivity;
import be.kdg.teamf.kandoe_app.R;
import be.kdg.teamf.kandoe_app.resource.UserResource;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static be.kdg.teamf.kandoe_app.application.KandoeApplication.getKandoeService;


/**
 * Created by Shenno Willaert on 10/03/2016.
 */

/**
 * Homefragment with simple frontpage view
 * In this fragment the logged in user's data is loaded into the
 * navigation header section.
 */
public class HomeFragment extends Fragment implements Callback<UserResource> {
    private NavigationView navigationView;
    private TextView tvEmail;
    private TextView tvName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        tvEmail = (TextView) getActivity().findViewById(R.id.textView_email);
        tvName = (TextView) getActivity().findViewById(R.id.textView_username);
        checkLoggedInStatus();
        // Change title
        ((MainActivity) getActivity()).getSupActionBar().setTitle("Home");
        return rootView;
    }

    /**
     * Function to update navigationview based on the fact
     * a user is logged in or not.
     */
    private void checkLoggedInStatus(){
        SharedPreferences prefs = getActivity().getSharedPreferences("Logindetails", Context.MODE_PRIVATE);
        if(prefs.getString("token", null) == null) {
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_register).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
            navigationView.getMenu().findItem(R.id.nav_play).setEnabled(false);
            tvEmail.setText("Gelieve in te loggen/registeren");
            tvName.setText("Niet ingelogd");
        }
        else {
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_register).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
            navigationView.getMenu().findItem(R.id.nav_play).setEnabled(true);
            getKandoeService().getUserinfo(prefs.getString("token", null), this);
        }
    }

    private void updateSharedPref(int id) {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("Logindetails", Context.MODE_PRIVATE).edit();
        editor.putInt("id", id);
        editor.apply();
    }

    @Override
    public void success(UserResource userResource, Response response) {
        if(userResource != null) {
            tvEmail.setText(userResource.getUsername());
            String fullName = userResource.getFirstName() + " " + userResource.getLastName();
            tvName.setText(fullName);
            updateSharedPref(userResource.getId());
        }
    }

    @Override
    public void failure(RetrofitError error) {

    }
}
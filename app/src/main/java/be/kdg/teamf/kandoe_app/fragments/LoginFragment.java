package be.kdg.teamf.kandoe_app.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import be.kdg.teamf.kandoe_app.R;
import be.kdg.teamf.kandoe_app.resource.UserResourcePost;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static be.kdg.teamf.kandoe_app.application.KandoeApplication.getKandoeService;


/**
 * Created by admin on 9/03/2016.
 */
public class LoginFragment extends Fragment implements Callback<String> {

    private EditText etEmail;
    private EditText etPassword;
    private Button butSubmit;
    private TextView tvRegister;
    private NavigationView navigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        coupleWidgets(rootView);
        initListeners();
        return rootView;
    }

    private void coupleWidgets(View rootView) {
        etEmail = (EditText) rootView.findViewById(R.id.editTextEmail);
        etPassword = (EditText) rootView.findViewById(R.id.editTextPwd);
        butSubmit = (Button) rootView.findViewById(R.id.buttonSubmit);
        tvRegister = (TextView) rootView.findViewById(R.id.textViewRegister);
        navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
    }

    private void initListeners() {
        butSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToRegisterFrag();
            }
        });
    }

    private void changeToRegisterFrag() {
        navigationView.getMenu().findItem(R.id.nav_register).setChecked(true); //Change highlight in menu
        Fragment fragment = new RegisterFragment();
        FragmentManager fragMan = getFragmentManager();
        FragmentTransaction fragTran = fragMan.beginTransaction();
        fragTran.replace(R.id.frame_container, fragment);
        fragTran.addToBackStack(null);
        fragTran.commit();
    }

    private void signIn() {
        getKandoeService().login(new UserResourcePost(etEmail.getText().toString(), etPassword.getText().toString()), this);
    }

    private void updateSharedPref(String token) {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("Logindetails", Context.MODE_PRIVATE).edit();
        String bearerToken = "Bearer \""+ token + "\"";
        editor.putString("token", bearerToken);
        editor.apply();
    }

    @Override
    public void success(String s, Response response) {
        //Als de login succes vol is, zet dan de token in een sharedPrefence
        updateSharedPref(s);
        Snackbar.make(getView(), "Succesvol ingelogd!" , Snackbar.LENGTH_LONG).show();
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true); //Change highlight in menu
        Fragment fragment = new HomeFragment();
        FragmentManager fragMan = getFragmentManager();
        FragmentTransaction fragTran = fragMan.beginTransaction();
        fragTran.replace(R.id.frame_container, fragment);
        fragTran.addToBackStack(null);
        fragTran.commit();
    }

    @Override
    public void failure(RetrofitError error) {
        Snackbar.make(getView(), "Oeps, login incorrect", Snackbar.LENGTH_LONG).show();
    }
}

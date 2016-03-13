package be.kdg.teamf.kandoe_app.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import be.kdg.teamf.kandoe_app.R;
import be.kdg.teamf.kandoe_app.resource.UserResourceRegister;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static be.kdg.teamf.kandoe_app.application.KandoeApplication.getKandoeService;


/**
 * Created by admin on 9/03/2016.
 */
public class RegisterFragment extends Fragment implements Callback<String> {
    private EditText etFirstname;
    private EditText etLastname;
    private EditText etEmail;
    private EditText etPwd;
    private EditText etPwdConfirm;
    private Button butSubmit;
    private NavigationView navigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);
        coupleWidgets(rootView);
        initListeners();
        return rootView;
    }

    private void coupleWidgets(View rootView) {
        etFirstname = (EditText) rootView.findViewById(R.id.editTextFirstname);
        etLastname = (EditText) rootView.findViewById(R.id.editTextLastname);
        etEmail = (EditText) rootView.findViewById(R.id.editTextEmail);
        etPwd = (EditText) rootView.findViewById(R.id.editTextPwd);
        etPwdConfirm = (EditText) rootView.findViewById(R.id.editTextPwdConfirm);
        butSubmit = (Button) rootView.findViewById(R.id.buttonSubmit);
        navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
    }

    private void initListeners() {
        butSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {
        //// TODO: 11/03/2016 dubbelpwd check
        UserResourceRegister userResourceRegister = new UserResourceRegister
                (   etEmail.getText().toString(),
                    etPwd.getText().toString(),
                    etFirstname.getText().toString(),
                    etLastname.getText().toString()
                );
        getKandoeService().registerUser(userResourceRegister, this);
    }

    private void updateSharedPref(String token) {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("Logindetails", Context.MODE_PRIVATE).edit();
        String bearerToken = "Bearer \""+ token + "\"";
        editor.putString("token", bearerToken);
        editor.apply();
    }

    @Override
    public void success(String s, Response response) {
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
        Snackbar.make(getView(), "Oeps, je hebt iets verkeerd gedaan", Snackbar.LENGTH_LONG).show();
    }
}

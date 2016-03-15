package be.kdg.teamf.kandoe_app.application;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.logging.Level;

import be.kdg.teamf.kandoe_app.service.KandoeService;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by admin on 8/03/2016.
 */
public class KandoeApplication extends Application {
    private static KandoeService kandoeService;

    @Override
    public void onCreate() {
        super.onCreate();
        kandoeService = createDemoService();
    }

    public static KandoeService getKandoeService() {
        return kandoeService;
    }

    private KandoeService createDemoService() {
        final Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss") // Om DateTimes van te converteren
                .create();

        return new RestAdapter.Builder()
                .setConverter(new GsonConverter(gson))
                .setEndpoint("http://10.0.3.2:9966/kandoe")
                .setLogLevel(RestAdapter.LogLevel.FULL) // Om af te drukken welke http-calls er effectief gebeuren
                .build()
                .create(KandoeService.class);
    }
}

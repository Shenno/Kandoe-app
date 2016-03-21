package be.kdg.teamf.kandoe_app.service;



import java.util.List;

import be.kdg.teamf.kandoe_app.resource.CardSessionResource;
import be.kdg.teamf.kandoe_app.resource.SessionResource;
import be.kdg.teamf.kandoe_app.resource.UserResource;
import be.kdg.teamf.kandoe_app.resource.UserResourcePost;
import be.kdg.teamf.kandoe_app.resource.UserResourceRegister;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;


/**
 * Created by admin on 8/03/2016.
 */
public interface KandoeService {

    @POST("/api/users")
    void registerUser(@Body UserResourceRegister urr, Callback<String> token);

    @GET("/api/users/me")
    void getUserinfo(@Header("Authorization") String token, Callback<UserResource> ur);

    @POST("/api/login")
    void login(@Body UserResourcePost userResourcePost, Callback<String> token);

    @GET("/api/sessions")
    void getSessions(@Header("Authorization") String token, Callback<List<SessionResource>> sr);

    @GET("/api/sessions/{sessionId}")
    void getSession(@Header("Authorization") String token, @Path("sessionId") int sessionId, Callback<SessionResource> sr);

    @POST("/api/sessions/{sessionId}")
    void updateSession(@Header("Authorization") String token, @Path("sessionId") int sessionId,
                       @Body CardSessionResource cardSessionResource, Callback<SessionResource> sr);

}


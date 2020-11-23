package eu.berdosi.app.heartbeat.network;

import eu.berdosi.app.heartbeat.model.LoginResponse;
import eu.berdosi.app.heartbeat.model.RegisterResponse;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AppService {
    @FormUrlEncoded
    @POST("/auth/register")
    Call<RegisterResponse> createUser (
            @Field("name") String _name,
            @Field("email") String _email,
            @Field("phone") String _phone,
            @Field("password") String _password
    );

    @FormUrlEncoded
    @POST("auth/login")
    Call<LoginResponse> login (
            @Field("email") String _email,
            @Field("password") String _password
    );


}

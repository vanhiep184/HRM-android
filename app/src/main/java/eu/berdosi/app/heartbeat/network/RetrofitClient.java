package eu.berdosi.app.heartbeat.network;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class RetrofitClient {
    private  Retrofit retrofit;
    private  static RetrofitClient instance;
    private  RetrofitClient() {
        retrofit = new Retrofit.Builder().baseUrl("http://192.168.43.76:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public static RetrofitClient getInstance() {
        if(instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }
    public AppService getAppService() {
        return retrofit.create(AppService.class);
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

}

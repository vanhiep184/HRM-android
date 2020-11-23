package eu.berdosi.app.heartbeat.model;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {
    @SerializedName( "status" )
    private String status;
    @SerializedName( "data" )
    private Object data;
    public Object getData() {
        return data;
    }

}

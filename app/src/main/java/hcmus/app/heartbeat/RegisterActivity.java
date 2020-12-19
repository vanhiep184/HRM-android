package hcmus.app.heartbeat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import hcmus.app.heartbeat.databinding.ActivityRegisterBinding;
import hcmus.app.heartbeat.model.RegisterResponse;
import hcmus.app.heartbeat.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    public static String USER_TOKEN = "USER_TOKEN";
    private ActivityRegisterBinding binding;
    private String inputEmail;
    private String inputPassword;
    private String inputName;
    private String inputConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        binding.buttonRegister.setOnClickListener(view -> {
            inputName = binding.editTextName.getText().toString();
            inputEmail = binding.editTextEmail.getText().toString();
            inputPassword = binding.editTextPassword.getText().toString();
            inputConfirmPassword = binding.editTextConfirm.getText().toString();
//            Toast.makeText(getApplicationContext(), inputEmail + " " + inputPassword + " " + inputName,
//                    Toast.LENGTH_LONG).show();
            Call<RegisterResponse> call = RetrofitClient
                    .getInstance()
                    .getAppService()
                    .createUser(inputName, inputEmail, "123", inputPassword);
            call.enqueue(new Callback<RegisterResponse>() {
                             @Override
                             public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                                 Log.d("Body", "Body");
                                 if (response.code() == 200) {
                                     try {
                                         JSONObject responseObject = new JSONObject(response.body().getData().toString());
                                         String token = (String) responseObject.getString("token");
                                         //Save token to local storage
                                         SharedPreferences.Editor editor = getSharedPreferences(USER_TOKEN, MODE_PRIVATE).edit();
                                         editor.putString("token", token);
                                         editor.apply();
                                        //Read token from local storage
                                         SharedPreferences prefs = getSharedPreferences(USER_TOKEN, MODE_PRIVATE);
                                         String retreivedToken  = prefs.getString("token", "none");
                                         Log.d("TOKEN", retreivedToken);
                                         //Login then go to application context
                                         Toast.makeText(RegisterActivity.this, "Register successfully" , Toast.LENGTH_LONG).show();
                                         startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                         finish();
                                     } catch (JSONException e) {
                                         e.printStackTrace();
                                     }

                                 } else {
                                     Toast.makeText(RegisterActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                 }
                             }

                             @Override
                             public void onFailure(Call<RegisterResponse> call, Throwable t) {
                                 Toast.makeText(RegisterActivity.this, "Failed" +t.getMessage(), Toast.LENGTH_LONG).show();
                             }
                         }
            );
        });
        binding.buttonLogin.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

    }
}
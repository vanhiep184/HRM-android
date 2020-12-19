package hcmus.app.heartbeat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Toast;

import hcmus.app.heartbeat.databinding.ActivityLoginBinding;
import hcmus.app.heartbeat.model.LoginResponse;
import hcmus.app.heartbeat.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private String inputEmail;
    private String inputPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);

        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
        binding.buttonRegister.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        });

        binding.buttonLogin.setOnClickListener(view -> {
            inputEmail = binding.editTextEmail.getText().toString();
            inputPassword = binding.editTextPassword.getText().toString();
            Toast.makeText(getApplicationContext(), inputEmail+ " " + inputPassword,
                Toast.LENGTH_LONG).show();

            Call<LoginResponse> call = RetrofitClient
                    .getInstance()
                    .getAppService()
                    .login( inputEmail, inputPassword);
            call.enqueue(new Callback<LoginResponse>() {
                             @Override
                             public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                                 if (response.code() == 200) {
                                     Toast.makeText(LoginActivity.this, "Login successfully" , Toast.LENGTH_LONG).show();
                                     startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                     finish();
                                 }
                             }

                             @Override
                             public void onFailure(Call<LoginResponse> call, Throwable t) {
                                 Toast.makeText(LoginActivity.this, "Failed" +t.getMessage(), Toast.LENGTH_LONG).show();
                             }
                         }
            );

            startActivity(new Intent(getApplicationContext(), MainActivity.class));

        });
    }
}
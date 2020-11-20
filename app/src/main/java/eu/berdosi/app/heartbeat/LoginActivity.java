package eu.berdosi.app.heartbeat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import eu.berdosi.app.heartbeat.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private String inputEmail;
    private String inputPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);

        binding.buttonRegister.setOnClickListener(view -> {
            startActivity(new Intent(this,RegisterActivity.class));
        });

        binding.buttonLogin.setOnClickListener(view -> {
            inputEmail = binding.editTextEmail.getText().toString();
            inputPassword = binding.editTextPassword.getText().toString();
            Toast.makeText(getApplicationContext(), inputEmail+ " " + inputPassword,
                Toast.LENGTH_LONG).show();
        });
    }
}
package eu.berdosi.app.heartbeat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Toast;

import eu.berdosi.app.heartbeat.databinding.ActivityLoginBinding;
import eu.berdosi.app.heartbeat.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private String inputEmail;
    private String inputPassword;
    private String inputName;
    private String inputConfirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_register);
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        binding.buttonRegister.setOnClickListener(view -> {
            inputName = binding.editTextName.getText().toString();
            inputEmail = binding.editTextEmail.getText().toString();
            inputPassword = binding.editTextPassword.getText().toString();
            inputConfirmPassword = binding.editTextConfirm.getText().toString();
            Toast.makeText(getApplicationContext(), inputEmail+ " " + inputPassword + " " + inputName,
                    Toast.LENGTH_LONG).show();
        });

        binding.buttonLogin.setOnClickListener(view -> {
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        });
    }
}
package com.example.find_it.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.find_it.R;
import com.example.find_it.login.model.ErrorResponse;
import com.example.find_it.login.model.LoginData;
import com.example.find_it.login.model.LoginResponse;

public class LoginActivity extends AppCompatActivity implements LoginView {
    private LoginPresenter presenter;
    private View progressBar;
    private TextView emailErrorOutput;
    private TextView passwordErrorOutput;
    private View loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        presenter = new LoginPresenter(this);
        progressBar = findViewById(R.id.progressBar);

        emailErrorOutput = findViewById(R.id.email_error_output);
        passwordErrorOutput = findViewById(R.id.password_error_output);

        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiateUserLogin();
            }
        });
    }

    private void initiateUserLogin() {
        signalLoginToUser(true);
        presenter.loginUser(getLoginData());
    }

    private void signalLoginToUser(boolean progress) {
        if (progress) {
            progressBar.setVisibility(View.VISIBLE);
            loginButton.setEnabled(false);
        } else {
            progressBar.setVisibility(View.GONE);
            loginButton.setEnabled(true);
        }
        emailErrorOutput.setText("");
        passwordErrorOutput.setText("");
    }

    private LoginData getLoginData() {
        String email = ((EditText) findViewById(R.id.email_input)).getText().toString();
        String password = ((EditText) findViewById(R.id.password_input))
                .getText().toString();
        return new LoginData(email, password);
    }

    @Override
    public void onLoginSuccess(LoginResponse response) {
        signalLoginToUser(false);
        Intent intent = new Intent(this, TokensActivity.class);
        intent.putExtra("ACCESS_TOKEN", response.getAccessToken());
        intent.putExtra("REFRESH_TOKEN", response.getRefreshToken());
        startActivity(intent);
    }

    @Override
    public void onLoginFailure(ErrorResponse errorResponse) {
        signalLoginToUser(false);

        String emailError = errorResponse.getErrors().getEmailError();
        String passwordError = errorResponse.getErrors().getPasswordError();

        if (emailError != null) {
            emailErrorOutput.setText(emailError);
        }
        if (passwordError != null) {
            passwordErrorOutput.setText(passwordError);
        }
    }

    @Override
    public void onNetworkError() {
        Snackbar snackbar = Snackbar.make(
                findViewById(R.id.login_layout), "Network Error", Snackbar.LENGTH_INDEFINITE
        );
        snackbar.setAction("Retry", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiateUserLogin();
            }
        });
        snackbar.show();
        signalLoginToUser(false);
    }
}

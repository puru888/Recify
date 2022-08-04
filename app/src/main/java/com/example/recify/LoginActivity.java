package com.example.recify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import com.example.recify.db.AppDatabase;
import com.example.recify.db.Dao.RegisterDao;
import com.example.recify.entities.Register;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    public static final String EXTRA_LOGIN_ID = "loginId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextInputLayout email = findViewById(R.id.activity_login_email);
        TextInputLayout password = findViewById(R.id.activity_login_password);

        RegisterDao registerDao = AppDatabase.getDatabaseInstance(this).registerDao();
        Resources resources = getResources();

        findViewById(R.id.activity_login_loginBtn).setOnClickListener(view -> {
            String emailValue = email.getEditText().getText().toString();
            String passwordValue = password.getEditText().getText().toString();
            AppDatabase.databaseWriteExecutor.execute(() -> {
                if (!registerDao.exist(emailValue)) {
                    runOnUiThread(() -> email.setError(resources.getString(R.string.login_invalidUsername)));
                    return;
                } else {
                    runOnUiThread(() -> email.setError(null));
                }

                Register user = registerDao.validate(emailValue, passwordValue);

                if (user == null) {
                    runOnUiThread(() -> password.setError(resources.getString(R.string.login_invalidPassword)));
                    return;
                } else {
                    runOnUiThread(() -> password.setError(null));
                }

                Intent homeIntent = new Intent(this, HomeActivity.class);
                homeIntent.putExtra(EXTRA_LOGIN_ID, user.getId());
                startActivity(homeIntent);

            });

        });
        findViewById(R.id.activity_login_registerBtn).setOnClickListener(view -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
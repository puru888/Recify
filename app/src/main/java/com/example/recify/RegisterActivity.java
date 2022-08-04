package com.example.recify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Toast;

import com.example.recify.db.AppDatabase;
import com.example.recify.db.Dao.RegisterDao;
import com.example.recify.entities.Register;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextInputLayout email = findViewById(R.id.register_email);
        TextInputLayout password = findViewById(R.id.register_password);
        TextInputLayout repeatPassword = findViewById(R.id.register_confirm_password);
        TextInputLayout firstName = findViewById(R.id.register_first_name);
        TextInputLayout lastName = findViewById(R.id.register_last_name);

        resources = getResources();

        findViewById(R.id.register_register).setOnClickListener(v -> {
            if (isEmpty(email, password, repeatPassword, firstName, lastName)) {
                return;
            }

            if (!doPasswordMatch(password, repeatPassword)) {
                return;
            }

            Register register = new Register(getValue(firstName),getValue(lastName),getValue(email),getValue(password));
            RegisterDao registerDao = AppDatabase.getDatabaseInstance(this).registerDao();

            AppDatabase.databaseWriteExecutor.execute(() -> {
                registerDao.insert(register);
                runOnUiThread(() -> Toast.makeText(this, "User created Successfully", Toast.LENGTH_SHORT).show());
                finish();
            });

        });

    }

    private String getValue(@NonNull TextInputLayout view) {
        return view.getEditText().getText().toString();
    }

    private boolean doPasswordMatch(@NonNull TextInputLayout password, @NonNull TextInputLayout repeatPassword) {
        String passwordValue = password.getEditText().getText().toString();
        String passwordRepeatValue = repeatPassword.getEditText().getText().toString();
        if (!passwordValue.equals(passwordRepeatValue)) {
            repeatPassword.setError(resources.getString(R.string.register_passwordDoesNotMatch));
            repeatPassword.setErrorEnabled(true);
            return false;
        }
        repeatPassword.setErrorEnabled(false);
        return true;
    }

    private boolean isEmpty(TextInputLayout... views) {
        boolean foundError = false;

        for (TextInputLayout view : views) {
            String value = view.getEditText().getText().toString();
            if (value.isEmpty()) {
                view.setError(resources.getString(R.string.register_emptyError));
                view.setErrorEnabled(true);
                foundError = true;
            } else {
                view.setErrorEnabled(false);
            }

        }
        return foundError;
    }
}
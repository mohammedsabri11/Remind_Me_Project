
package com.saudi.remindme.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.saudi.remindme.BaseActivity;
import com.saudi.remindme.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpStep1Activity extends BaseActivity {

    private EditText email, password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_step1);

        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        confirmPassword = findViewById(R.id.editTextConfirmPassword);

        findViewById(R.id.buttonNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    Intent intent = new Intent(SignUpStep1Activity.this, SignUpActivity.class);
                    intent.putExtra("email", email.getText().toString());
                    intent.putExtra("password", password.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }

    private boolean validateInput() {
        boolean isValid = true;

        isValid = isFieldNotEmpty(email, R.string.error_empty_email) && isValid;
        isValid = isFieldNotEmpty(password, R.string.error_empty_password) && isValid;
        isValid = isFieldNotEmpty(confirmPassword, R.string.error_empty_password) && isValid;
        isValid = emailValidator() && isValid;
        isValid = isPasswordStrong() && isValid;
        isValid = isPasswordMatch() && isValid;

        return isValid;
    }

    protected boolean isFieldNotEmpty(EditText field, int errorMessageResId) {
        String value = field.getText().toString();
        if (value.isEmpty()) {
            field.setError(getString(errorMessageResId));
            field.requestFocus();
            return false;
        }
        return true;
    }

    private boolean isPasswordMatch() {
        String value = password.getText().toString();
        String valueConfirm = confirmPassword.getText().toString();
        if (!value.isEmpty() && isPasswordStrong() && !value.equals(valueConfirm)) {

            confirmPassword.requestFocus();
            confirmPassword.setError(getString(R.string.error_mismatch_password));
            return false;
        }
        return true;
    }

    private boolean isPasswordStrong() {
        String value = password.getText().toString();
        //"^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&(){}:;\',?/*~$^+=<>]).{8,20}$"
        String pa = "^(?=.*[a-zA-Z]{8,})(?=.*[0-9])(?=.*[@$!%*?&])[A-Za-z0-9@$!%*?&]{10,}$";
        if (!value.matches(pa)) {
            password.setError(getString(R.string.error_weak_password));
            password.requestFocus();
            return false;
        }
        return true;
    }

    private boolean emailValidator() {
        String value = email.getText().toString();
        Pattern pattern = Pattern.compile(getString(R.string.email_pattern));
        Matcher matcher = pattern.matcher(value);
        if (!value.isEmpty() && (!matcher.matches() || value.length() < 5)) {
            email.setError(getString(R.string.error_error_email));
            email.requestFocus();
            return false;
        }
        return true;
    }
}

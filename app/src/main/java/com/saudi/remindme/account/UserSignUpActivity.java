package com.saudi.remindme.account;

import static com.saudi.remindme.process.Constant.USER;
import static com.saudi.remindme.process.Process.USER_SIGN_UP;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.saudi.remindme.R;

import java.util.Map;


public class UserSignUpActivity extends SignUpActivity {
    EditText healthStatusDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);
        init();
    }


    @Override
    protected void init() {
        super.init();
        healthStatusDetails = findViewById(R.id.editTextHealthStatus);
        findViewById(R.id.buttonRegister).setOnClickListener(this);
        findViewById(R.id.textViewLogIn).setOnClickListener(this);
        findViewById(R.id.editTextDateOfBirth).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.buttonRegister:
                handleRegistration();


                break;
            default:
                super.onClick(view);

        }

    }
    private void handleRegistration() {
        if (validateInput()) {
            register(getParameters());
        }
    }
    
    private boolean validateInput() {
        boolean isValid = true;

        isValid = isFieldNotEmpty(firstName, R.string.error_empty_f_name) && isValid;
        isValid = isFieldNotEmpty(lastName, R.string.error_empty_l_name) && isValid;
        isValid = isFieldNotEmpty(dateOfBirth, R.string.error_empty_dateOfBirth) && isValid;
        isValid = isFieldNotEmpty(mobile, R.string.error_empty_phone_number) && isValid;
        isValid = phoneValidator() && isValid;
        isValid = isFieldNotEmpty(healthStatusDetails, R.string.error_empty_Health_Status_Details) && isValid;
        genderSelected();

        return isValid;
    }
    
    private boolean phoneValidator() {
        String value = mobile.getText().toString();
        if (!value.matches("^05\\d{8}$")) {
            mobile.setError(getString(R.string.error_invalid_phone));
            mobile.requestFocus();
            return false;
        }
        return true;
    }


    @Override
    protected Map<String, String> getParameters() {
        Map<String, String> parms = super.getParameters();
        parms.put("userType", USER);
        parms.put("healthStatusDetails", healthStatusDetails.getText().toString());
        parms.put("op", USER_SIGN_UP);
        return parms;
    }


}

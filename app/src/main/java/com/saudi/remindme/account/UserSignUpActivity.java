package com.saudi.remindme.account;

import static com.saudi.remindme.process.Constant.USER;
import static com.saudi.remindme.process.Process.USER_SIGN_UP;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.saudi.remindme.R;

import java.util.Map;


public class UserSignUpActivity extends SignUpActivity {
    EditText providerFirstName, providerLastName, healthStatusDetails;
    CheckBox isCareProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);
        init();
    }


    @Override
    protected void init() {
        super.init();
        isCareProvider = findViewById(R.id.checkboxIsCareProvider);
        isCareProvider.setVisibility(View.VISIBLE);
        providerFirstName = findViewById(R.id.editTextProviderFirstName);
        providerLastName = findViewById(R.id.editTextProviderLastName);
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
    @Override
    protected boolean validateInput() {
        boolean isValid = super.validateInput();


        isValid = isFieldNotEmpty(healthStatusDetails, R.string.error_empty_Health_Status_Details) && isValid;
        isValid = isFieldNotEmpty(providerFirstName, R.string.error_empty_provider_f_name) && isValid;
        isValid = isFieldNotEmpty(providerLastName, R.string.error_empty_provider_l_name) && isValid;


        return isValid;
    }


    @Override
    protected Map<String, String> getParameters() {
        Map<String, String> parms = super.getParameters();
        parms.put("userType", USER);
        parms.put("careProviderName", providerFirstName.getText().toString() + " " + providerLastName.getText().toString());
        parms.put("healthStatusDetails", healthStatusDetails.getText().toString());
        parms.put("op", USER_SIGN_UP);
        return parms;
    }


}









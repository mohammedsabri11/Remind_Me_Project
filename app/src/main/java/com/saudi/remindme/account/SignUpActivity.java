package com.saudi.remindme.account;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.saudi.remindme.BaseActivity;
import com.saudi.remindme.R;
import com.saudi.remindme.Url;
import com.saudi.remindme.process.IResult;
import com.saudi.remindme.process.Server;
import com.saudi.remindme.statedialog.DatePickerHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public abstract class SignUpActivity extends BaseActivity implements View.OnClickListener, IResult {
    String gender = "Male";
    RadioButton selectedGender;
    protected EditText firstName, lastName, dateOfBirth, mobile;
    protected String email, password;



    protected void init() {
        firstName = findViewById(R.id.editTextFirstName);
        lastName = findViewById(R.id.editTextLastName);
        dateOfBirth = findViewById(R.id.editTextDateOfBirth);
        mobile = findViewById(R.id.editTextMobile);

        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.textViewLogIn:
                startLoginActivity();
                break;


            case R.id.editTextDateOfBirth:
                birthDatePicker();


                break;
            default:
                break;

        }
    }

    public void birthDatePicker() {
        DatePickerHelper.showDatePicker(this, date -> {
            Log.d("birthDatePicker: ", "birthDatePicker: " + date);
            dateOfBirth.setText(date);
        });
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
        finish();
    }

    protected void genderSelected() {
        RadioGroup radioGroup = findViewById(R.id.radioGroupGender);
        int selectedId = radioGroup.getCheckedRadioButtonId();


        if (selectedId == R.id.radioButtonMale) {

            gender = "Male";
        } else if (selectedId == R.id.radioButtonFemale) {
            // Female selected
            gender = "Female";
        }
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

    protected void register(Map<String, String> params) {
        if (email == null || password == null) {
            showFailDialog("An error occurred. Please try again.");
            return;
        }
        showProgressDialog(this, getString(R.string.do_operation_message));
        Server.getInstance(this).post(params, Url.URL_User, 2, this);
    }

    protected Map<String, String> getParameters() {
        Map<String, String> params = new HashMap<>();

        params.put("fullName", firstName.getText().toString() + " " + lastName.getText().toString());
        params.put("gender", gender);
        params.put("phone", mobile.getText().toString());
        params.put("dob", dateOfBirth.getText().toString());
        params.put("email", email);
        params.put("password", password);
        return params;
    }

    @Override
    public void onServerSuccess(int requestId, JSONObject jsonObject) {
        try {
            showSuccessDialog(jsonObject.getString("msg"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServerError(int requestId, String error) {
        showFailDialog(error);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        Intent intent = new Intent(this, LogInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}

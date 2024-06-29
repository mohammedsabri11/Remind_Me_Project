package com.saudi.remindme.account;

import android.content.DialogInterface;
import android.content.Intent;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public abstract class SignUpActivity extends BaseActivity implements View.OnClickListener, IResult {
 String gender="Male";
    RadioButton selectedGender;
    private EditText firstName, lastName, dateOfBirth, mobile, email, password, confirmPassword;



    protected void init() {
        firstName = findViewById(R.id.editTextFirstName);
        lastName = findViewById(R.id.editTextLastName);
       // radioGroupGender = findViewById(R.id.radioGroupGender);
       // int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
        dateOfBirth = findViewById(R.id.editTextDateOfBirth);
        mobile = findViewById(R.id.editTextMobile);
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        confirmPassword = findViewById(R.id.editTextConfirmPassword);
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
            Log.d("birthDatePicker: ", "birthDatePicker: "+date);
            dateOfBirth.setText(date);
        });
    }
    private void startLoginActivity() {
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
        finish();
    }
    private void genderSelected () {
    RadioGroup radioGroup = findViewById(R.id.radioGroupGender);
    int selectedId = radioGroup.getCheckedRadioButtonId();



        if (selectedId ==R.id.radioButtonMale) {

            gender=  "Male";
        } else if (selectedId ==R.id.radioButtonFemale) {
            // Female selected
            gender=  "Female";
        }
    }
    protected boolean validateInput() {
        boolean isValid = true;

        isValid = isFieldNotEmpty(firstName, R.string.error_empty_f_name) && isValid;
        isValid = isFieldNotEmpty(lastName, R.string.error_empty_l_name) && isValid;
        isValid = isFieldNotEmpty(dateOfBirth, R.string.error_empty_dateOfBirth) && isValid;
        isValid = isFieldNotEmpty(mobile, R.string.error_empty_phone_number) && isValid;
        isValid = isFieldNotEmpty(email, R.string.error_empty_email) && isValid;
        isValid = isFieldNotEmpty(password, R.string.error_empty_password) && isValid;
        //isValid = isFieldNotEmpty(confirmPassword, R.string.error_empty_password) && isValid;
        isValid = phoneValidator() && isValid;
        isValid = emailValidator() && isValid;
        isValid = isPasswordStrong() && isValid;
        isValid = isPasswordMatch() && isValid;
        Log.d("validateInput: gender",  gender);
        genderSelected ();
        Log.d("validateInput: gender",  gender);
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
        if (!value.isEmpty() &&isPasswordStrong()&& !value.equals(valueConfirm)) {

            confirmPassword.requestFocus();
            confirmPassword.setError(getString(R.string.error_mismatch_password));
            return false;
        }
        return true;
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
    private boolean isPasswordStrong() {
        String value = password.getText().toString();
        //"^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&(){}:;',?/*~$^+=<>]).{8,20}$"
        String pa ="^(?=.*[a-zA-Z]{8,})(?=.*[0-9])(?=.*[@$!%*?&])[A-Za-z0-9@$!%*?&]{10,}$";
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

    protected void register(Map<String, String> params) {
        showProgressDialog(this, getString(R.string.do_operation_message));
        Server.getInstance(this).post(params, Url.URL_User, 2, this);
    }

    protected Map<String, String> getParameters() {
        Map<String, String> params = new HashMap<>();

        params.put("fullName", firstName.getText().toString() + " " + lastName.getText().toString());
        params.put("gender", gender);
        params.put("phone", mobile.getText().toString());
        params.put("dob", dateOfBirth.getText().toString());
        params.put("email", email.getText().toString());
        params.put("password", password.getText().toString());
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







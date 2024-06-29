package com.saudi.remindme.account;

import static com.saudi.remindme.process.Constant.ADMIN;
import static com.saudi.remindme.process.Constant.CONSULTANT;
import static com.saudi.remindme.process.Constant.USER;
import static com.saudi.remindme.process.Process.LOGIN;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatCheckBox;

import com.saudi.remindme.BaseActivity;
import com.saudi.remindme.R;
import com.saudi.remindme.Url;
import com.saudi.remindme.admin.MainAdminActivity;
import com.saudi.remindme.consultant.MainConsultantActivity;
import com.saudi.remindme.process.IResult;
import com.saudi.remindme.process.Server;
import com.saudi.remindme.user.MainUserActivity2;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LogInActivity extends BaseActivity implements View.OnClickListener, IResult {
    EditText editTextEmail, editTextPassword;
    AppCompatCheckBox RememerMe;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_log_in);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        RememerMe = findViewById(R.id.checkboxRememerMe);
        findViewById(R.id.buttonLogIn).setOnClickListener(this);
        mContext = this;
        findViewById(R.id.textViewRegister).setOnClickListener(this);


    }


    public void login() {

        showProgressDialog(mContext, "Waiting for Authentication");
        Server.getInstance(mContext).post(getParameters(), Url.URL_User, 2, this);


    }

    private Map<String, String> getParameters() {

        Map<String, String> parm = new HashMap<String, String>();
        parm.put("email", editTextEmail.getText().toString());
        parm.put("password", editTextPassword.getText().toString());

        parm.put("op", LOGIN);
        return parm;
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.textViewRegister) {

            Intent intent = new Intent(mContext, RegisterAsActivity.class);
            startActivity(intent);
            finish();


        } else if (v.getId() == R.id.buttonLogIn) {


            validateInput();
        }

    }

    protected void validateInput() {
        boolean isValid = true;
        isValid = isValid && isFieldNotEmpty(editTextEmail, getResources().getString(R.string.error_empty_email));
        isValid = isValid && isFieldNotEmpty(editTextPassword, getResources().getString(R.string.error_empty_password));

        if (isValid) {

            login();

        }
    }

    protected boolean isFieldNotEmpty(EditText field, String error) {
        String value = field.getText().toString();
        if (value.isEmpty()) {
            field.setError(error);
            field.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void onServerSuccess(int requestId, JSONObject jsonObject) {

        hideProgressDialog();
        try {

            JSONObject object = jsonObject.getJSONObject("profile");
            String id = object.getString("muserid");
            String name = object.getString("fullname");

            String email = object.getString("email");
            String user_group = object.getString("usertype");
            String is_Active = object.getString("isactive");
            boolean isActive = !is_Active.equals("0");
            if (!isActive) {
                showFailDialog("Your account is inactive, you must wait for the administrator to activate your account ");
                return;
            }

            SessionManager.getInstance(LogInActivity.this).createSession(id, name, email, user_group, RememerMe.isChecked());


            Intent intent = userInterface();
            if (intent != null) {

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else
                Toast.makeText(getApplicationContext(), "error intent=null", Toast.LENGTH_LONG).show();


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServerError(int requestId, String error) {
        showFailDialog(error);
    }

    public Intent userInterface() {
        Intent intent = null;

        switch (SessionManager.getInstance(getApplicationContext()).getKeyUserType()) {
            case CONSULTANT:

                intent = new Intent(getApplicationContext(), MainConsultantActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                break;
            case ADMIN:

                intent = new Intent(getApplicationContext(), MainAdminActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                break;
            case USER:

                intent = new Intent(getApplicationContext(), MainUserActivity2.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                break;
        }


        return intent;
    }
}






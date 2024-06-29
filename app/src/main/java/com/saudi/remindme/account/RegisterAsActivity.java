package com.saudi.remindme.account;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.saudi.remindme.R;

public class RegisterAsActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_as);


        findViewById(R.id.buttonConsultantRegister).setOnClickListener(this);
        findViewById(R.id.textViewContinueLogIn).setOnClickListener(this);
        findViewById(R.id.buttonPatientRegister).setOnClickListener(this);

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {

            case R.id.buttonConsultantRegister:

                intent = new Intent(this, ConsultantSignUpActivity.class);
                break;

            case R.id.buttonPatientRegister:

                intent = new Intent(this, UserSignUpActivity.class);
                break;

            case R.id.textViewContinueLogIn:

                intent = new Intent(this, LogInActivity.class);
                break;

        }

        if (intent != null) {
            startActivity(intent);
        }


    }


}






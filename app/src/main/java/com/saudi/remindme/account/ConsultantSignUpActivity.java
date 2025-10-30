package com.saudi.remindme.account;

import static com.saudi.remindme.process.Constant.CONSULTANT;
import static com.saudi.remindme.process.Process.CONSULTANT_SIGN_UP;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.saudi.remindme.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Map;


public class ConsultantSignUpActivity extends SignUpActivity {


    private Bitmap bitmap = null;
    private ImageView imageViewCertification;
    private ImageButton buttonChooseImage;
    private EditText speciality, experienceYear;
    private Context mContext;
    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK
                        && result.getData() != null) {
                    Uri uri = result.getData().getData();
                    try {

                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        imageViewCertification.setImageBitmap(bitmap);

                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(mContext, getResources().getString(R.string.error_select_image), Toast.LENGTH_SHORT).show();

                    }
                    //use photoUri here
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulant_sign_up);

        mContext = this;
        init();
    }


    @Override
    protected void init() {
        super.init();
        experienceYear = findViewById(R.id.editTextExperienceYear);
        speciality = findViewById(R.id.editTextSpeciality);
        speciality = findViewById(R.id.editTextSpeciality);
        imageViewCertification = findViewById(R.id.imageViewCertification);
        buttonChooseImage = findViewById(R.id.buttonChooseImage);
        findViewById(R.id.editTextDateOfBirth).setOnClickListener(this);
        buttonChooseImage.setOnClickListener(this);
        findViewById(R.id.buttonRegister).setOnClickListener(this);
        findViewById(R.id.textViewLogIn).setOnClickListener(this);


    }

    private void selectImage() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");

        launcher.launch(Intent.createChooser(intent, "Choose Image!"));
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        switch (view.getId()) {


            case R.id.buttonChooseImage:
                selectImage();
                break;

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
        isValid = isValid && isFieldNotEmpty(speciality, R.string.error_empty_speciality);
        isValid = isValid && isFieldNotEmpty(experienceYear, R.string.error_empty_Experience_Year);
        genderSelected();

        if (bitmap == null) {
            buttonChooseImage.setFocusable(true);
            buttonChooseImage.requestFocus();
            isValid = false;
        }

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
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        String image = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
        String imageName = String.valueOf(Calendar.getInstance().getTimeInMillis());
        parms.put("image", image);
        parms.put("imageName", imageName);
        parms.put("speciality", speciality.getText().toString());
        parms.put("experience", experienceYear.getText().toString());
        parms.put("userType", CONSULTANT);
        parms.put("op", CONSULTANT_SIGN_UP);

        return parms;
    }


}

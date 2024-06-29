package com.saudi.remindme.consultant.ui.fragment;


import static com.saudi.remindme.process.Process.LOAD_MY_EVALUATIONS;
import static com.saudi.remindme.process.Process.UPDATE_SHORT_DESCRIPTION;
import static com.saudi.remindme.process.Process.UPDATE_SHORT_DESCRIPTION_AND_PROFILE_IMAGE;
import static com.saudi.remindme.process.ProcessId.LOAD_EVALUATIONS;
import static com.saudi.remindme.process.ProcessId.UPDATE_PROFILE;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.saudi.remindme.R;
import com.saudi.remindme.Url;
import com.saudi.remindme.account.SessionManager;
import com.saudi.remindme.admin.ui.model.ConsultantItem;
import com.saudi.remindme.consultant.ui.adapter.EvaluationAdapter;
import com.saudi.remindme.process.Server;
import com.saudi.remindme.user.ui.model.EvaluationItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EvaluationFragment extends ParentFragment {
    private final List<EvaluationItem> evaluations = new ArrayList<>();
    private ImageButton editProfileButton;
    private AppCompatEditText shortDescriptionEditText;
    private AppCompatButton saveChangesButton;
    private ShapeableImageView profileImageView;
    private TextView nameTextView;
    private TextView shortDescriptionTextView;
    private TextView specialityTextView;
    private TextView experienceYearTextView;
    private TextView evaluationCountTextView;
    private Bitmap profileBitmap;
    private ImageButton chooseProfileButton;
    private ConsultantItem consultantProfile;
    private int state = -1;
    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK
                        && result.getData() != null) {
                    Uri uri = result.getData().getData();
                    try {
                        setUpdateMode();
                        profileBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                        profileImageView.setImageBitmap(profileBitmap);

                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), getResources().getString(R.string.error_select_image), Toast.LENGTH_SHORT).show();

                    }
                    //use photoUri here
                }
            }
    );

    public static EvaluationFragment newInstance() {
        return new EvaluationFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        load(Url.URL_EVALUATION, this.getParameters(LOAD_MY_EVALUATIONS), LOAD_EVALUATIONS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_evaluation, container, false);
        initView(view);
        emptyView.setOnClickListener(v -> load(Url.URL_EVALUATION, this.getParameters(LOAD_MY_EVALUATIONS), LOAD_EVALUATIONS));
        return view;
    }

    public void checkDataAvailability() {
        if (evaluations.isEmpty()) {
            showNoDataAvailableMessage();
        } else {
            showDataAvailable();
        }
    }

    public void setProfile(JSONObject consultant) {
        try {
            String userId = consultant.getString("muserid");
            String name = consultant.getString("fullname");
            String shortDescription = consultant.getString("shortDescription");
            String gender = consultant.getString("gender");
            String experience = consultant.getString("experience");
            String speciality = consultant.getString("speciality");
            String evaluationCount = consultant.getString("evaluation_count");
            String profileUrl = consultant.getString("profile");
            consultantProfile = new ConsultantItem(userId, name, shortDescription, gender, speciality, experience, evaluationCount, profileUrl);

            setProfileModelView();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setProfileModelView() {
        setProfileImage();
        nameTextView.setText(consultantProfile.getName());
        shortDescriptionTextView.setText(consultantProfile.getShortDescription());
        specialityTextView.setText(consultantProfile.getSpeciality());
        experienceYearTextView.setText(consultantProfile.getExperience());
        evaluationCountTextView.setText(consultantProfile.getEvaluation());
        shortDescriptionEditText.setText(consultantProfile.getShortDescription());
    }

    public void setEvaluations(JSONArray evaluationList) {
        try {
            evaluations.clear();
            for (int i = 0; i < evaluationList.length(); i++) {
                JSONObject evaluation = evaluationList.getJSONObject(i);
                String id = evaluation.getString("evaluationId");
                String userId = evaluation.getString("user_id");
                String userName = evaluation.getString("fullname");
                float rating = Float.parseFloat(evaluation.getString("Rating"));
                String review = evaluation.getString("Review");
                String date = evaluation.getString("Date");
                EvaluationItem evaluationObj = new EvaluationItem(id, userId, userName, review, rating, date);
                evaluations.add(evaluationObj);
            }

            checkDataAvailability();
            EvaluationAdapter evaluationAdapter = new EvaluationAdapter(getContext(), evaluations);
            recyclerView.setAdapter(evaluationAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServerSuccess(int requestId, JSONObject responseObj) {
        try {
            hideProgressDialog();
            if (requestId == UPDATE_PROFILE) {
                setViewMode();
                load(Url.URL_EVALUATION, this.getParameters(LOAD_MY_EVALUATIONS), LOAD_EVALUATIONS);
                return;
            }
            setProfile(responseObj.getJSONObject("profile"));
            setEvaluations(responseObj.getJSONArray("Data"));
        } catch (JSONException e) {
            Log.d("onResponse :", "onResponse:catch error " + e);
        }
    }

    @Override
    public void onServerError(int requestId, String error) {
        //implemented interface method onServerError
        //it return  data as String to display
        if (requestId == LOAD_EVALUATIONS) {
            emptyView.setText(error);
        } else
            showFailedDialog(error);

    }

    @Override
    protected Map<String, String> getParameters(String op) {
        Map<String, String> params = super.getParameters(op);
        params.put("consultantId", SessionManager.getInstance(getActivity()).getKeyUserId());
        return params;
    }

    @Override
    protected View initView(View view) {
        super.initView(view);
        profileImageView = view.findViewById(R.id.profile);
        nameTextView = view.findViewById(R.id.user_name);
        shortDescriptionTextView = view.findViewById(R.id.short_description);
        specialityTextView = view.findViewById(R.id.consultantSpeciality);
        experienceYearTextView = view.findViewById(R.id.ExperienceYear);
        evaluationCountTextView = view.findViewById(R.id.evaluationCount);
        editProfileButton = view.findViewById(R.id.editImageButton);
        shortDescriptionEditText = view.findViewById(R.id.shortDescriptionEditText);
        saveChangesButton = view.findViewById(R.id.saveChangeButton);
        chooseProfileButton = view.findViewById(R.id.chooseProfile);

        editProfileButton.setOnClickListener(v -> {
            updateViewVisibility();
            chooseProfileButton.setOnClickListener(v1 -> selectProfileImage());
        });

        saveChangesButton.setOnClickListener(v -> updateProfile());

        return view;
    }

    private void updateViewVisibility() {
        if (state == -1) {
            setUpdateMode();
        } else if (state == 1) {
            setViewMode();
        }
    }

    private void setUpdateMode() {
        shortDescriptionTextView.setVisibility(View.GONE);
        shortDescriptionEditText.setVisibility(View.VISIBLE);
        saveChangesButton.setVisibility(View.VISIBLE);
        chooseProfileButton.setVisibility(View.VISIBLE);
        editProfileButton.setBackground(getActivity().getDrawable(R.drawable.baseline_close_24));
        state = 1;
    }

    private void setViewMode() {
        shortDescriptionTextView.setVisibility(View.VISIBLE);
        shortDescriptionEditText.setVisibility(View.GONE);
        saveChangesButton.setVisibility(View.GONE);
        editProfileButton.setBackground(getActivity().getDrawable(R.drawable.baseline_edit_24));
        chooseProfileButton.setVisibility(View.GONE);
        state = -1;
    }

    private void setProfileImage() {
        if (consultantProfile.getProfile().equalsIgnoreCase("null")) {
            int profileDrawable = consultantProfile.getGender().equalsIgnoreCase("Male") ? R.drawable.c_m_profile : R.drawable.female_profile;
            profileImageView.setImageDrawable(getActivity().getDrawable(profileDrawable));
        } else {
            Glide.with(getActivity()).load(Url.URL_LOAD_IMAGE + consultantProfile.getProfile()).into(profileImageView);
        }
    }

    private void updateProfile() {
        Map<String, String> params = new HashMap<>();

        String shortDescription = shortDescriptionEditText.getText().toString();
        if (profileBitmap == null) {
            params.put("op", UPDATE_SHORT_DESCRIPTION);

        } else {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            profileBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            String image = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
            String imageName = String.valueOf(Calendar.getInstance().getTimeInMillis());
            params.put("image", image);
            params.put("imageName", imageName);
            params.put("op", UPDATE_SHORT_DESCRIPTION_AND_PROFILE_IMAGE);
        }

        params.put("consultantId", SessionManager.getInstance(getActivity()).getKeyUserId());

        params.put("shortDescription", shortDescription);
        Server.getInstance(getActivity()).post(params, Url.URL_User, UPDATE_PROFILE, this);


        showProgressDialog(getActivity(), getResources().getString(R.string.do_operation_message));
    }

    private void selectProfileImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        launcher.launch(Intent.createChooser(intent, "Choose Image!"));
    }


}

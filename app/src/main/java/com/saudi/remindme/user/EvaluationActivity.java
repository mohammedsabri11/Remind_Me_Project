package com.saudi.remindme.user;


import static com.saudi.remindme.process.Process.LOAD_CONSULTANT_EVALUATIONS;
import static com.saudi.remindme.process.ProcessId.ADD_EVALUATION_REQUEST_ID;
import static com.saudi.remindme.process.ProcessId.DELETE_EVALUATION_REQUEST_ID;
import static com.saudi.remindme.process.ProcessId.LOAD_CONSULTANT_EVALUATIONS_REQUEST_ID;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.saudi.remindme.R;
import com.saudi.remindme.Url;
import com.saudi.remindme.account.SessionManager;
import com.saudi.remindme.admin.ui.model.ConsultantItem;
import com.saudi.remindme.process.Server;
import com.saudi.remindme.statedialog.ConfirmationDialog;
import com.saudi.remindme.statedialog.ReviewDialog;
import com.saudi.remindme.user.ui.adapter.EvaluationAdapter;
import com.saudi.remindme.user.ui.interfaces.OnDeleteListener;
import com.saudi.remindme.user.ui.model.EvaluationItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EvaluationActivity extends ListActivity implements OnDeleteListener, View.OnClickListener {


    private final List<EvaluationItem> evaluations = new ArrayList<>();
    private ShapeableImageView profileImageView;
    private TextView nameTextView;
    private TextView shortDescriptionTextView;
    private TextView specialityTextView;
    private TextView experienceYearTextView;
    private TextView genderTextView;
    private ImageButton chatButton;
    private ImageButton makeReviewButton;
    private ConsultantItem consultant;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mContext = EvaluationActivity.this;
        Intent intent = getIntent();
        consultant = (ConsultantItem) intent.getSerializableExtra("consultant");
        initView();


        loadDataList(Url.URL_EVALUATION, getParams(LOAD_CONSULTANT_EVALUATIONS), LOAD_CONSULTANT_EVALUATIONS_REQUEST_ID);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected Map<String, String> getParams(String operation) {
        Map<String, String> parms = new HashMap<String, String>();
        parms.put("consultantId", consultant.getId());
        parms.put("userId", SessionManager.getInstance(mContext).getKeyUserId());
        parms.put("op", operation);
        return parms;
    }

    public void setEvaluations(JSONArray evaluationList) {
        try {
            evaluations.clear();
            boolean isRate = false;

            for (int i = 0; i < evaluationList.length(); i++) {
                JSONObject evaluation = evaluationList.getJSONObject(i);

                String id = evaluation.getString("evaluationId");
                String user_id = evaluation.getString("user_id");
                String userName = evaluation.getString("fullname");
                float Rating = Float.parseFloat(evaluation.getString("Rating"));
                String Review = evaluation.getString("Review");
                String date = evaluation.getString("Date");
                if (user_id.equals(SessionManager.getInstance(mContext).getKeyUserId())) {

                    isRate = true;
                }
                EvaluationItem evaluationObj = new EvaluationItem(id, user_id, userName, Review, Rating, date);

                evaluations.add(evaluationObj);

            }
            if (isRate) {
                makeReviewButton.setVisibility(View.GONE);
            } else
                makeReviewButton.setVisibility(View.VISIBLE);

            checkDataAvailability();
            EvaluationAdapter evaluationAdapter = new EvaluationAdapter(mContext, evaluations);
            recyclerView.setAdapter(evaluationAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    //implemented interface method onServerSuccess
    //it return  data as json  object
    public void onServerSuccess(int requestId, JSONObject responseObj) {

        try {

            switch (requestId) {
                case DELETE_EVALUATION_REQUEST_ID:
                    showSuccessDialog(responseObj.getString("msg"));
                    loadDataList(Url.URL_EVALUATION, getParams(LOAD_CONSULTANT_EVALUATIONS), LOAD_CONSULTANT_EVALUATIONS_REQUEST_ID);
                    break;

                case ADD_EVALUATION_REQUEST_ID:
                    showSuccessDialog(responseObj.getString("msg"));
                    loadDataList(Url.URL_EVALUATION, getParams(LOAD_CONSULTANT_EVALUATIONS), LOAD_CONSULTANT_EVALUATIONS_REQUEST_ID);
                    break;
                case LOAD_CONSULTANT_EVALUATIONS_REQUEST_ID:
                    setEvaluations(responseObj.getJSONArray("Data"));
                    break;
            }
        } catch (JSONException e) {
            Log.d("onResponse :", "onResponse:catch error " + e);
        }
    }

    public void checkDataAvailability() {
        if (evaluations.isEmpty()) {
            showNoDataAvailableMessage();
        } else {
            showDataAvailable();
        }
    }

    @Override
    public void onServerError(int requestId, String error) {
        //implemented interface method onServerError
        //it return  data as String to display
        if (requestId == LOAD_CONSULTANT_EVALUATIONS_REQUEST_ID) {
            emptyView.setText(error);
        } else
            showFailDialog(error);

    }

    public void addEvaluation(int Rating, String review) {
        Map<String, String> parm = new HashMap<String, String>();
        parm.put("rating", String.valueOf(Rating));
        parm.put("review", review);
        parm.put("date", currentDate());
        parm.put("userId", SessionManager.getInstance(mContext).getKeyUserId());
        parm.put("consultantId", consultant.getId());
        parm.put("op", "Add");
        //Server server=new Server(mContext);
        Server.getInstance(mContext).post(parm, Url.URL_EVALUATION, ADD_EVALUATION_REQUEST_ID, this);
        showProgressDialog(mContext, getResources().getString(R.string.do_operation_message));
    }

    public String currentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(calendar.getTime());
    }

    public void deleteEvaluation(String evaluationId) {
        Map<String, String> parm = new HashMap<String, String>();
        parm.put("evaluationId", evaluationId);
        parm.put("userId", SessionManager.getInstance(mContext).getKeyUserId());
        parm.put("op", "Delete");
        //Server server=new Server(mContext);
        Server.getInstance(mContext).post(parm, Url.URL_EVALUATION, DELETE_EVALUATION_REQUEST_ID, this);
        showProgressDialog(mContext, getResources().getString(R.string.do_operation_message));
    }

    @Override
    public void onDelete(String remindID) {
        String title = getResources().getString(R.string.confirm_title);
        String mess = getResources().getString(R.string.evaluation_delete_message_confirm);


        ConfirmationDialog.showConfirmationDialog(mContext, title, mess, () -> deleteEvaluation((remindID)));

    }

    @Override
    protected void initView() {
        super.initView();
        profileImageView = findViewById(R.id.profile);
        nameTextView = findViewById(R.id.user_name);
        shortDescriptionTextView = findViewById(R.id.short_description);
        specialityTextView = findViewById(R.id.consultantSpeciality);
        experienceYearTextView = findViewById(R.id.ExperienceYear);
        genderTextView = findViewById(R.id.gender);
        makeReviewButton = findViewById(R.id.makeReview);
        chatButton = findViewById(R.id.chatButton);
        makeReviewButton.setOnClickListener(this);
        chatButton.setOnClickListener(this);
        setConsultantDetails();

    }


    private void setConsultantDetails() {

        if (consultant.getProfile().equalsIgnoreCase("null")) {
            if (consultant.getGender().equalsIgnoreCase("Male")) {
                profileImageView.setImageDrawable(getDrawable(R.drawable.c_m_profile));
            } else
                profileImageView.setImageDrawable(getDrawable(R.drawable.female_profile));
        } else {

            Glide.with(mContext).load(Url.URL_LOAD_IMAGE + consultant.getProfile()).into(profileImageView);
        }
        nameTextView.setText(consultant.getName());
        shortDescriptionTextView.setText(consultant.getShortDescription());
        specialityTextView.setText(consultant.getSpeciality());
        experienceYearTextView.setText(consultant.getExperience());
        genderTextView.setText(consultant.getGender());


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.chatButton:
                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra("consultant", consultant);
                startActivity(intent);
                break;

            case R.id.makeReview:
                ReviewDialog reviewDialog = new ReviewDialog(mContext, this::addEvaluation);
                reviewDialog.show();
                break;
            default:
                break;

        }
    }
}





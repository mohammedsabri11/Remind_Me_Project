package com.saudi.remindme.user;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.saudi.remindme.R;
import com.saudi.remindme.Url;
import com.saudi.remindme.admin.ui.model.ConsultantItem;
import com.saudi.remindme.user.ui.adapter.FindConsultantAdapter;
import com.saudi.remindme.user.ui.interfaces.OnItemListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FindConsultantActivity extends ListActivity implements OnItemListener {

    public static final int LOAD_CONSULTANT = 1;
    FindConsultantAdapter findConsultantAdapter;
    List<ConsultantItem> consultantModels = new ArrayList<>();
    String operation = "LoadConsultantList";
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activit_find_consult);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mContext = FindConsultantActivity.this;
        initView();
        loadDataList(Url.URL_USER_MANAGER, getParams(operation), LOAD_CONSULTANT);

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


    public void checkDataAvailability() {
        if (consultantModels.isEmpty()) {
            showNoDataAvailableMessage();
        } else {
            showDataAvailable();
        }
    }

    @Override
    //implemented interface method onServerSuccess
    //it return  data as json  object
    public void onServerSuccess(int requestId, JSONObject responseObj) {
        //implemented interface method onServerSuccess
        //it return  data as json  object

        try {

            consultantModels.clear();
            JSONArray queryList = responseObj.getJSONArray("Data");
            for (int i = 0; i < queryList.length(); i++) {
                JSONObject consultant = queryList.getJSONObject(i);

                String id = consultant.getString("muserid");
                String name = consultant.getString("fullname");
                String shortDescription = consultant.getString("shortDescription");
                String gender = consultant.getString("gender");
                String experience = consultant.getString("experience");
                String speciality = consultant.getString("speciality");
                String evaluation = consultant.getString("evaluation_count");
                String profile = consultant.getString("profile");
                ConsultantItem consultantItem = new ConsultantItem(id, name, shortDescription, gender, speciality, experience, evaluation, profile);
                consultantModels.add(consultantItem);

            }

            checkDataAvailability();
            findConsultantAdapter = new FindConsultantAdapter(mContext, consultantModels, this);
            recyclerView.setAdapter(findConsultantAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServerError(int requestId, String error) {
        //implemented interface method onServerError
        //it return  data as String to display
        if (requestId == LOAD_CONSULTANT) {
            emptyView.setText(error);
        } else
            showFailDialog(error);
    }


    @Override
    public void onChatClick(ConsultantItem consultant) {
        Intent intent = new Intent(mContext, ChatActivity.class);
        intent.putExtra("consultant", consultant);
        startActivity(intent);
    }

    @Override
    public void onEvaluationClick(ConsultantItem consultantItem) {
        Intent intent = new Intent(mContext, EvaluationActivity.class);
        intent.putExtra("consultant", consultantItem);
        startActivity(intent);
    }


}





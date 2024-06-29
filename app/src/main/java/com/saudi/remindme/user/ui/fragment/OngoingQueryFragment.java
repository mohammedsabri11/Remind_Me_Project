package com.saudi.remindme.user.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.saudi.remindme.R;
import com.saudi.remindme.Url;
import com.saudi.remindme.admin.ui.model.ConsultantItem;
import com.saudi.remindme.user.ChatActivity;
import com.saudi.remindme.user.EvaluationActivity;
import com.saudi.remindme.user.FindConsultantActivity;
import com.saudi.remindme.user.ui.adapter.OngoingQueryAdapter;
import com.saudi.remindme.user.ui.interfaces.OnItemListener;
import com.saudi.remindme.user.ui.model.ConsultantChatItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class OngoingQueryFragment extends ParentFragment implements OnItemListener {

    private static final int LOAD_ONGOING_QUERY_USER = 1;
    OngoingQueryAdapter ongoingQueryAdapter;
    List<ConsultantChatItem> consultantList = new ArrayList<>();
    String operation = "LoadConsultants";

    public static OngoingQueryFragment newInstance() {
        return new OngoingQueryFragment();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        load(Url.URL_QUERY, getParameters(operation), LOAD_ONGOING_QUERY_USER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_ongoing_query, container, false);
        initView(view);
        emptyView.setOnClickListener(v -> load(Url.URL_QUERY, getParameters(operation), LOAD_ONGOING_QUERY_USER));
        FloatingActionButton newQuery = view.findViewById(R.id.addNewQuery);
        newQuery.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), FindConsultantActivity.class);
            startActivity(intent);
        });

        return view;
    }


    public void checkDataAvailability() {
        if (consultantList.isEmpty()) {
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

            consultantList.clear();
            JSONArray queryList = responseObj.getJSONArray("Data");
            for (int i = 0; i < queryList.length(); i++) {
                JSONObject consultant = queryList.getJSONObject(i);

                String id = consultant.getString("muserid");
                String name = consultant.getString("fullname");
                String shortDescription = consultant.getString("shortDescription");
                String gender = consultant.getString("gender");
                String experience = consultant.getString("experience");
                String speciality = consultant.getString("speciality");
                String evaluation = "0";
                String profile = consultant.getString("profile");
                String lastMessage = consultant.getString("querytext");
                String lastReply = consultant.getString("queryreply");

                if (!lastReply.isEmpty() && !lastReply.equals("null")) {
                    lastMessage = lastReply;
                }
                ConsultantItem consultantItem = new ConsultantItem(id, name, shortDescription, gender, speciality, experience, evaluation, profile);
                ConsultantChatItem consultantModel = new ConsultantChatItem(consultantItem, lastMessage);
                consultantList.add(consultantModel);

            }

            checkDataAvailability();
            ongoingQueryAdapter = new OngoingQueryAdapter(getActivity(), consultantList, this);
            recyclerView.setAdapter(ongoingQueryAdapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServerError(int requestId, String error) {
        //implemented interface method onServerError
        //it return  data as String to display
        if (requestId == LOAD_ONGOING_QUERY_USER) {
            emptyView.setText(error);
        } else
            showFailedDialog(error);

    }

    @Override
    public void onChatClick(ConsultantItem consultant) {
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        intent.putExtra("consultant", consultant);
        startActivity(intent);
    }

    @Override
    public void onEvaluationClick(ConsultantItem consultant) {
        Intent intent = new Intent(getActivity(), EvaluationActivity.class);
        intent.putExtra("consultant", consultant);
        startActivity(intent);
    }
}
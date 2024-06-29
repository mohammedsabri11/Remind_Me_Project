package com.saudi.remindme.consultant.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saudi.remindme.R;
import com.saudi.remindme.Url;
import com.saudi.remindme.admin.ui.model.PatientItem;
import com.saudi.remindme.consultant.ChatActivity;
import com.saudi.remindme.consultant.ui.adapter.OngoingQueryAdapter;
import com.saudi.remindme.consultant.ui.interfaces.OnOngoingQueryListener;
import com.saudi.remindme.consultant.ui.model.PatientChatItem;
import com.saudi.remindme.statedialog.PatientDetailsDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class OngoingConsultantQueryFragment extends ParentFragment implements OnOngoingQueryListener {

    private static final int LOAD_ONGOING_QUERY_CONSULTANT = 1;

    OngoingQueryAdapter consultantQueryAdapter;
    List<PatientChatItem> chatItemList = new ArrayList<>();
    String operation = "LoadUsers";

    public static OngoingConsultantQueryFragment newInstance() {


        return new OngoingConsultantQueryFragment();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        load(Url.URL_QUERY, getParameters(operation), LOAD_ONGOING_QUERY_CONSULTANT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_consultant_ongoing_query, container, false);
        initView(view);//_consultant

        emptyView.setOnClickListener(v -> load(Url.URL_QUERY, getParameters(operation), LOAD_ONGOING_QUERY_CONSULTANT));
        checkDataAvailability();

        return view;
    }


    public void checkDataAvailability() {
        if (chatItemList.isEmpty()) {
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

            chatItemList.clear();
            JSONArray queryList = responseObj.getJSONArray("Data");
            for (int i = 0; i < queryList.length(); i++) {
                JSONObject consultant = queryList.getJSONObject(i);

                String id = consultant.getString("muserid");
                String name = consultant.getString("fullname");
                String dob = consultant.getString("dob");
                String gender = consultant.getString("gender");
                String phone = consultant.getString("mobile");
                String healthStatusDetails = consultant.getString("healtstatusdetails");


                String lastMessage = consultant.getString("querytext");
                String lastReply = consultant.getString("queryreply");

                if (!lastReply.isEmpty() && !lastReply.equals("null")) {
                    lastMessage = lastReply;
                }
                PatientItem patientItem = new PatientItem(id, name, dob, gender, phone, healthStatusDetails);
                PatientChatItem chatItem = new PatientChatItem(patientItem, lastMessage);

                chatItemList.add(chatItem);

            }


            checkDataAvailability();
            consultantQueryAdapter = new OngoingQueryAdapter(getActivity(), chatItemList, this);


            recyclerView.setAdapter(consultantQueryAdapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServerError(int requestId, String error) {
        //implemented interface method onServerError
        //it return  data as String to display
        if (requestId == LOAD_ONGOING_QUERY_CONSULTANT) {
            emptyView.setText(error);
        } else
            showFailedDialog(error);

    }


    @Override
    public void onChat(PatientItem patient) {
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        intent.putExtra("patient", patient);
        startActivity(intent);
    }

    @Override
    public void onProfile(PatientItem patient) {
        PatientDetailsDialog patientDetailsDialog = new PatientDetailsDialog(getActivity(), patient);
        patientDetailsDialog.show();
    }
}
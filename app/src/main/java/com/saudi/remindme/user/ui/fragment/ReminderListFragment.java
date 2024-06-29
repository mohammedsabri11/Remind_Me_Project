package com.saudi.remindme.user.ui.fragment;

import static com.saudi.remindme.process.Process.LOAD_MY_REMINDER;
import static com.saudi.remindme.process.ProcessId.DELETE_REMINDER;
import static com.saudi.remindme.process.ProcessId.LOAD_REMINDER_LIST;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.saudi.remindme.R;
import com.saudi.remindme.Url;
import com.saudi.remindme.account.SessionManager;
import com.saudi.remindme.process.Server;
import com.saudi.remindme.statedialog.ConfirmationDialog;
import com.saudi.remindme.user.NewReminderActivity;
import com.saudi.remindme.user.ui.adapter.ReminderAdapter;
import com.saudi.remindme.user.ui.interfaces.OnDeleteListener;
import com.saudi.remindme.user.ui.model.ReminderItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ReminderListFragment extends ParentFragment implements OnDeleteListener {


    ReminderAdapter reminderAdapter;
    List<ReminderItem> reminderItems = new ArrayList<>();


    public static ReminderListFragment newInstance() {


        return new ReminderListFragment();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        load(Url.URL_REMIND, getParameters(LOAD_MY_REMINDER), LOAD_REMINDER_LIST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reminder_list, container, false);
        initView(view);

        emptyView.setOnClickListener(v -> load(Url.URL_REMIND, getParameters(LOAD_MY_REMINDER), LOAD_REMINDER_LIST));
        FloatingActionButton newRemind = view.findViewById(R.id.addNewRemind);

        newRemind.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), NewReminderActivity.class);

            startActivity(intent);
        });


        return view;
    }


    public void checkDataAvailability() {
        if (reminderItems.isEmpty()) {
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

            if (requestId == DELETE_REMINDER) {

                showSuccessDialog(responseObj.getString("msg"));
                load(Url.URL_REMIND, getParameters(LOAD_MY_REMINDER), LOAD_REMINDER_LIST);
                return;
            }
            reminderItems.clear();
            JSONArray remindList = responseObj.getJSONArray("ReminderList");

            for (int i = 0; i < remindList.length(); i++) {
                JSONObject remind = remindList.getJSONObject(i);

                String id = remind.getString("reminderid");
                String dateTime = remind.getString("datetime");
                String reminderDetails = remind.getString("appointmentdetails");

                ReminderItem reminderItem = new ReminderItem(id, dateTime, reminderDetails);

                reminderItems.add(reminderItem);

            }


            checkDataAvailability();
            reminderAdapter = new ReminderAdapter(getActivity(), reminderItems, this);


            recyclerView.setAdapter(reminderAdapter);


        } catch (JSONException e) {
            Log.d("onResponse :", "onResponse:catch error " + e);
        }
    }

    @Override
    public void onServerError(int requestId, String error) {
        //implemented interface method onServerError
        //it return  data as String to display
        if (requestId == LOAD_REMINDER_LIST) {
            emptyView.setText(error);
        } else
            showFailedDialog(error);

    }

    public void deleteReminder(String reminderId) {
        Map<String, String> parm = new HashMap<String, String>();
        parm.put("reminderId", reminderId);
        parm.put("userId", SessionManager.getInstance(getActivity()).getKeyUserId());
        parm.put("op", "Delete");
        // Server server=new Server(getActivity(),this);
        Server.getInstance(getActivity()).post(parm, Url.URL_REMIND, DELETE_REMINDER, this);
        showProgressDialog(getActivity(), getResources().getString(R.string.do_operation_message));
    }

    @Override
    public void onDelete(String remindID) {
        String title = getResources().getString(R.string.confirm_title);
        String mess = getResources().getString(R.string.reminder_delete_message_confirm);


        ConfirmationDialog.showConfirmationDialog(getActivity(), title, mess, () -> deleteReminder(remindID));

    }

}
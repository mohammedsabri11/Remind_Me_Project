package com.saudi.remindme.admin.ui.fragment;


import static com.saudi.remindme.process.Process.ACTIVATE_USER;
import static com.saudi.remindme.process.Process.DE_ACTIVATE_USER;
import static com.saudi.remindme.process.ProcessId.CHANGE_USER_STATE_REQUEST_ID;
import static com.saudi.remindme.process.ProcessId.LOAD_USER_REQUEST_ID;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.saudi.remindme.R;
import com.saudi.remindme.Url;
import com.saudi.remindme.admin.ui.adapter.UserAdapter;
import com.saudi.remindme.admin.ui.interfaces.OnUserListener;
import com.saudi.remindme.admin.ui.model.PatientItem;
import com.saudi.remindme.process.Process;
import com.saudi.remindme.statedialog.ConfirmationDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserListFragment extends BaseUserFragment<PatientItem> implements OnUserListener {


    private UserAdapter userAdapter;


    public static UserListFragment newInstance() {
        return new UserListFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        load(Url.URL_USER_MANAGER, getParameters(Process.LOAD_USER), LOAD_USER_REQUEST_ID);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        initView(view);
        itemList = new ArrayList<>();
        emptyView.setOnClickListener(v -> load(Url.URL_USER_MANAGER, getParameters(Process.LOAD_USER), LOAD_USER_REQUEST_ID));
        return view;
    }


    public void setUserList(JSONObject jsonObject) {
        itemList.clear();
        try {
            JSONArray users = jsonObject.getJSONArray("UserList");
            for (int i = 0; i < users.length(); i++) {
                String id = users.getJSONObject(i).getString("muserid");
                String name = users.getJSONObject(i).getString("fullname");
                String gender = users.getJSONObject(i).getString("gender");
                String healtstatusdetails = users.getJSONObject(i).getString("healtstatusdetails");
                String careprovidername = users.getJSONObject(i).getString("careprovidername");
                String is_Active = users.getJSONObject(i).getString("isactive");
                boolean isActive = !is_Active.equals("0");

                PatientItem patientItem = new PatientItem(id, name, gender, careprovidername, healtstatusdetails, isActive);
                itemList.add(patientItem);
            }
        } catch (JSONException e) {
            // Log the exception
            Log.d("UserListFragment", "Error in setUserList for requestId: ", e);

            // Display a user-friendly error message or handle the exception appropriately
        }
    }

    @Override
    public void onServerSuccess(int requestId, JSONObject jsonObject) {
        try {
            switch (requestId) {
                case CHANGE_USER_STATE_REQUEST_ID:
                    showSuccessDialog(jsonObject.getString("msg"));
                    load(Url.URL_USER_MANAGER, getParameters(Process.LOAD_USER), LOAD_USER_REQUEST_ID);
                    break;
                case LOAD_USER_REQUEST_ID:
                    setUserList(jsonObject);
                    checkDataAvailability();
                    userAdapter = new UserAdapter(getActivity(), itemList, this);
                    recyclerView.setAdapter(userAdapter);
                    break;
                default:
                    // Handle default case
            }
        } catch (JSONException e) {
            // Log the exception
            //logger.error("Error in onServerSuccess for requestId: " + requestId, e);
            // Display a user-friendly error message or handle the exception appropriately
            Log.d("UserListFragment", "Error in onServerSuccess for requestId: " + requestId, e);
        }
    }

    @Override
    public void onServerError(int requestId, String error) {
        //implemented interface method onServerError
        //it return  data as String to display
        if (requestId == CHANGE_USER_STATE_REQUEST_ID) {
            showFailedDialog(error);
        } else
            checkDataAvailability();
    }


    @Override
    public void onDeActivate(String name, String userID) {

        String title = getResources().getString(R.string.confirm_title);
        String message = getResources().getString(R.string.de_activate_message_confirm);
        ConfirmationDialog.showConfirmationDialog(getActivity(), title, message, () -> updateUserStatus(userID, DE_ACTIVATE_USER));
    }

    @Override
    public void onActivate(String name, String userID) {

        String title = getResources().getString(R.string.confirm_title);
        String message = getResources().getString(R.string.activate_message_confirm);
        ConfirmationDialog.showConfirmationDialog(getActivity(), title, message, () -> updateUserStatus(userID, ACTIVATE_USER));


    }


}
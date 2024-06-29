package com.saudi.remindme.admin.ui.fragment;


import static com.saudi.remindme.process.ProcessId.CHANGE_USER_STATE_REQUEST_ID;

import com.saudi.remindme.R;
import com.saudi.remindme.Url;
import com.saudi.remindme.admin.ListFragment;
import com.saudi.remindme.process.Server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseUserFragment<T> extends ListFragment {


    protected List<T> itemList;


    protected Map<String, String> getParameters(String operation) {
        Map<String, String> params = new HashMap<>();
        params.put("op", operation);
        return params;
    }

    public void checkDataAvailability() {
        if (itemList.isEmpty()) {
            showNoDataAvailableMessage();
        } else {
            showDataAvailable();
        }
    }


    protected void updateUserStatus(String userID, String operation) {
        Map<String, String> params = getParameters(operation);
        params.put("userId", userID);

        Server.getInstance(getActivity()).post(params, Url.URL_USER_MANAGER, CHANGE_USER_STATE_REQUEST_ID, this);
        showProgressDialog(getActivity(), getResources().getString(R.string.do_operation_message));
    }

}







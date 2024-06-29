package com.saudi.remindme.user.ui.fragment;


import com.saudi.remindme.account.SessionManager;
import com.saudi.remindme.admin.ListFragment;

import java.util.HashMap;
import java.util.Map;

public abstract class ParentFragment extends ListFragment {


    protected Map<String, String> getParameters(String operation) {
        Map<String, String> params = new HashMap<>();
        params.put("op", operation);
        params.put("userId", SessionManager.getInstance(getActivity()).getKeyUserId());
        return params;
    }


}







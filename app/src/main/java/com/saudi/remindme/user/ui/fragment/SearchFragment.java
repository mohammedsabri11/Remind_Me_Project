package com.saudi.remindme.user.ui.fragment;


import android.view.MenuItem;

import com.saudi.remindme.account.SessionManager;
import com.saudi.remindme.admin.ListFragment;

import java.util.HashMap;
import java.util.Map;

public abstract class SearchFragment extends ListFragment {


    public abstract boolean onMenuItemActionExpand(MenuItem item);

    public abstract boolean onMenuItemActionCollapse(MenuItem item);

    protected Map<String, String> getParameters(String operation) {
        Map<String, String> params = new HashMap<>();
        params.put("op", operation);
        params.put("userId", SessionManager.getInstance(getActivity()).getKeyUserId());
        return params;
    }


}







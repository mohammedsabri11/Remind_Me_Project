package com.saudi.remindme.user;


import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saudi.remindme.BaseActivity;
import com.saudi.remindme.R;
import com.saudi.remindme.process.IResult;
import com.saudi.remindme.process.Server;

import java.util.HashMap;
import java.util.Map;

public abstract class ListActivity extends BaseActivity implements IResult {


    protected RecyclerView recyclerView;
    protected TextView emptyView;


    protected void initView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        linearLayoutManager.setStackFromEnd(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        emptyView = findViewById(R.id.empty_view);

    }


    protected void loadDataList(String url, Map<String, String> params, int OP_ID) {

        Server.getInstance(this).get(params, url, OP_ID, this);
    }


    protected Map<String, String> getParams(String operation) {
        Map<String, String> parms = new HashMap<String, String>();

        parms.put("op", operation);
        return parms;
    }

    protected void showNoDataAvailableMessage() {

        recyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);

    }

    protected void showDataAvailable() {

        recyclerView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);

    }


}







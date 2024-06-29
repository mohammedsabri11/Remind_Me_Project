package com.saudi.remindme.admin;


import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saudi.remindme.BaseFragment;
import com.saudi.remindme.R;
import com.saudi.remindme.process.Server;

import java.util.Map;

public abstract class ListFragment extends BaseFragment {


    protected RecyclerView recyclerView;
    protected TextView emptyView;


    protected View initView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        linearLayoutManager.setStackFromEnd(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        emptyView = view.findViewById(R.id.empty_view);

        return view;
    }


    protected void load(String url, Map<String, String> params, int OP_ID) {

        Server.getInstance(getActivity()).get(params, url, OP_ID, this);
    }


    protected abstract Map<String, String> getParameters(String operation);

    protected void showNoDataAvailableMessage() {

        recyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);

    }

    protected void showDataAvailable() {

        recyclerView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);

    }


}







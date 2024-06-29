package com.saudi.remindme.user.ui.fragment;

import static com.saudi.remindme.process.Process.LOAD__INFO;
import static com.saudi.remindme.process.ProcessId.LOAD_INFO;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.saudi.remindme.R;
import com.saudi.remindme.Url;
import com.saudi.remindme.user.ui.adapter.BaseInfoAdapter;
import com.saudi.remindme.user.ui.model.BaseInfoModel;
import com.saudi.remindme.user.ui.model.InfoModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class InfoFragment extends SearchFragment implements SearchView.OnQueryTextListener {

    BaseInfoAdapter infoAdapter;

    List<BaseInfoModel> baseInfoList = new ArrayList<>();


    public static InfoFragment newInstance() {
        return new InfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        load(Url.URL_INFO, getParameters(LOAD__INFO), LOAD_INFO);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search");

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText == null || newText.trim().isEmpty()) {
            resetSearch();
            return false;
        }

        List<BaseInfoModel> filteredValues = new ArrayList<BaseInfoModel>(baseInfoList);
        for (BaseInfoModel value : baseInfoList) {
            if (!value.getTitle().toLowerCase().contains(newText.toLowerCase())) {
                filteredValues.remove(value);
            }
        }
        infoAdapter = new BaseInfoAdapter(getActivity(), filteredValues);

        recyclerView.setAdapter(infoAdapter);


        return false;
    }

    public void resetSearch() {
        infoAdapter = new BaseInfoAdapter(getActivity(), baseInfoList);

        recyclerView.setAdapter(infoAdapter);
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return true;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        load(Url.URL_INFO, getParameters(LOAD__INFO), LOAD_INFO);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        initView(view);


        return view;
    }


    public void checkDataAvailability() {
        if (baseInfoList.isEmpty()) {
            showNoDataAvailableMessage();
        } else {
            showDataAvailable();
        }
    }

    public List<InfoModel> setInfoModel(JSONArray jsonInfoList) {
        //implemented interface method onServerSuccess
        //it return  data as json  object
        List<InfoModel> infoList = new ArrayList<>();
        try {

            //  JSONArray jsonInfoList = responseObj.getJSONArray("infoList");
            for (int i = 0; i < jsonInfoList.length(); i++) {
                JSONObject info = jsonInfoList.getJSONObject(i);

                String id = info.getString("infoid");
                String title = info.getString("title");

                String instructions = info.getString("instructions");

                InfoModel infoModel = new InfoModel(id, title, instructions);

                infoList.add(infoModel);

            }


        } catch (JSONException e) {
            e.printStackTrace();
            emptyView.setText(e.getMessage());
            checkDataAvailability();
        }
        return infoList;
    }

    @Override
    //implemented interface method onServerSuccess
    //it return  data as json  object
    public void onServerSuccess(int requestId, JSONObject responseObj) {
        //implemented interface method onServerSuccess
        //it return  data as json  object

        try {
            baseInfoList.clear();

            List<InfoModel> infoModels = setInfoModel(responseObj.getJSONArray("infoList1"));
            baseInfoList.add(new BaseInfoModel(" Information About alzheimer's disease", infoModels));

            List<InfoModel> infoModels2 = setInfoModel(responseObj.getJSONArray("infoList2"));
            baseInfoList.add(new BaseInfoModel("If you are the patient", infoModels2));

            List<InfoModel> infoModels3 = setInfoModel(responseObj.getJSONArray("infoList3"));
            baseInfoList.add(new BaseInfoModel("How to deal with alzheimer's patient", infoModels3));
            checkDataAvailability();
            infoAdapter = new BaseInfoAdapter(getActivity(), baseInfoList);
            recyclerView.setAdapter(infoAdapter);


        } catch (JSONException e) {
            e.printStackTrace();
            emptyView.setText(e.getMessage());
            checkDataAvailability();
        }
    }

    @Override
    public void onServerError(int requestId, String error) {
        //implemented interface method onServerError
        //it return  data as String to display

        emptyView.setText(error);
        checkDataAvailability();


    }


}
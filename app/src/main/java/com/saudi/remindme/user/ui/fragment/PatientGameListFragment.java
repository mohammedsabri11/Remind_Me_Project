package com.saudi.remindme.user.ui.fragment;


import static com.saudi.remindme.process.Process.LOAD_PATIENT_GAME_LIST;
import static com.saudi.remindme.process.ProcessId.LOAD_PATIENT_GAME_REQUEST_ID;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saudi.remindme.R;
import com.saudi.remindme.Url;
import com.saudi.remindme.consultant.ui.model.GameItem;
import com.saudi.remindme.user.ui.adapter.GameAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PatientGameListFragment extends ParentFragment implements GameAdapter.GameInterface {

    private final List<GameItem> gameList = new ArrayList<>();
    private GameAdapter gameAdapter;

    public static PatientGameListFragment newInstance() {
        return new PatientGameListFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        load(Url.URL_GAME, getParameters(LOAD_PATIENT_GAME_LIST), LOAD_PATIENT_GAME_REQUEST_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_game_list, container, false);
        initView(view);
        emptyView.setOnClickListener(v -> load(Url.URL_GAME, getParameters(LOAD_PATIENT_GAME_LIST), LOAD_PATIENT_GAME_REQUEST_ID));
        return view;
    }

    public void checkDataAvailability() {
        if (gameList.isEmpty()) {
            showNoDataAvailableMessage();
        } else {
            showDataAvailable();
        }
    }

    @Override
    public void onServerSuccess(int requestId, JSONObject jsonObject) {
        try {
            JSONArray users = jsonObject.getJSONArray("GameList");

            for (int i = 0; i < users.length(); i++) {
                String id = users.getJSONObject(i).getString("id");
                String name = users.getJSONObject(i).getString("name");
                String description = users.getJSONObject(i).getString("description");
                String link = users.getJSONObject(i).getString("link");

                GameItem game = new GameItem(id, name, description, link);
                gameList.add(game);
            }

            checkDataAvailability();
            gameAdapter = new GameAdapter(getActivity(), gameList, this);
            recyclerView.setAdapter(gameAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServerError(int requestId, String error) {
        checkDataAvailability();
        if (requestId == LOAD_PATIENT_GAME_REQUEST_ID) {
            emptyView.setText(error);
        }
    }

    @Override
    public void onPlay(String link) {
        if (!link.startsWith("http://") && !link.startsWith("https://")) {
            link = "http://" + link;
        }
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(browserIntent);
    }
}
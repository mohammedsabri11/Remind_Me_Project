package com.saudi.remindme.consultant.ui.fragment;

import static com.saudi.remindme.process.Process.ADD_GAME_TO_USER;
import static com.saudi.remindme.process.Process.DELETE_GAME;
import static com.saudi.remindme.process.Process.LOAD_CONSULTANT_GAME;
import static com.saudi.remindme.process.ProcessId.DELETE_GAME_REQUEST_ID;
import static com.saudi.remindme.process.ProcessId.LOAD_GAME;
import static com.saudi.remindme.process.ProcessId.LOAD_PATIENT_LIST_REQUEST_ID;
import static com.saudi.remindme.process.ProcessId.SHARE_GAME_REQUEST_ID;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.saudi.remindme.R;
import com.saudi.remindme.Url;
import com.saudi.remindme.account.SessionManager;
import com.saudi.remindme.admin.ui.model.PatientItem;
import com.saudi.remindme.consultant.GameAddActivity;
import com.saudi.remindme.consultant.GameEditActivity;
import com.saudi.remindme.consultant.ui.adapter.GameAdapter;
import com.saudi.remindme.consultant.ui.model.GameItem;
import com.saudi.remindme.process.Server;
import com.saudi.remindme.statedialog.ConfirmationDialog;
import com.saudi.remindme.statedialog.SpinnerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GameFragment extends ParentFragment implements GameAdapter.OnMenuListener, SpinnerDialog.OnUserSelectedListener {


    private final List<GameItem> gameList = new ArrayList<>();
    private final List<PatientItem> patientList = new ArrayList<>();
    String gameID = "";

    // TODO: Rename and change types and number of parameters
    public static GameFragment newInstance() {
        return new GameFragment();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        load(Url.URL_GAME, getParameters(LOAD_CONSULTANT_GAME), LOAD_GAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        initView(view);
        emptyView.setOnClickListener(v -> load(Url.URL_GAME, getParameters(LOAD_CONSULTANT_GAME), LOAD_GAME));
        FloatingActionButton addNewGame = view.findViewById(R.id.addNewGame);
        addNewGame.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), GameAddActivity.class);

            startActivity(intent);
        });

        return view;
    }


    public void checkDataAvailability() {
        if (gameList.isEmpty()) {
            showNoDataAvailableMessage();
        } else {
            showDataAvailable();
        }
    }


    public void setPatientList(JSONObject responseObj) {
        patientList.clear();
        try {
            hideProgressDialog();

            patientList.add(new PatientItem("", "select  User"));
            JSONArray users = responseObj.getJSONArray("UserList");

            for (int i = 0; i < users.length(); i++) {
                String id = users.getJSONObject(i).getString("muserid");
                String name = users.getJSONObject(i).getString("fullname");
                PatientItem userModel = new PatientItem(id, name);
                patientList.add(userModel);

            }
            showShareDialog();
        } catch (JSONException e) {
            // Log the exception
            Log.d("GameFragment", "Error in setPatientList for requestId: ", e);

        }
    }

    public void setGameList(JSONObject responseObj) {
        gameList.clear();
        try {

            JSONArray games = responseObj.getJSONArray("GameList");

            for (int i = 0; i < games.length(); i++) {

                JSONObject game = games.getJSONObject(i);
                String id = game.getString("gameid");
                String name = game.getString("name");
                String description = game.getString("description");
                String link = game.getString("link");

                GameItem gameItem = new GameItem(id, name, description, link);
                gameList.add(gameItem);

            }
            checkDataAvailability();
            GameAdapter gameAdapter = new GameAdapter(getActivity(), gameList);
            gameAdapter.onMenuListener = this;
            recyclerView.setAdapter(gameAdapter);


        } catch (JSONException e) {
            // Log the exception
            Log.d("GameFragment", "Error in setGameList for requestId: ", e);

            // Display a user-friendly error message or handle the exception appropriately
        }
    }

    @Override
    //implemented interface method onServerSuccess
    //it return  data as json  object
    public void onServerSuccess(int requestId, JSONObject responseObj) {
        //implemented interface method onServerSuccess
        //it return  data as json  object

        try {

            switch (requestId) {
                // check if result for  delete Game
                case DELETE_GAME_REQUEST_ID:
                    showSuccessDialog(responseObj.getString("msg"));
                    load(Url.URL_GAME, getParameters(LOAD_CONSULTANT_GAME), LOAD_GAME);
                    break;
                // check if result for  SHARE Game
                case SHARE_GAME_REQUEST_ID:
                    showSuccessDialog(responseObj.getString("msg"));
                    break;

                // check if result for  LOAD PATIENT
                case LOAD_PATIENT_LIST_REQUEST_ID:
                    setPatientList(responseObj);
                    break;

                // check if result for  LOAD GAME
                case LOAD_GAME:
                    setGameList(responseObj);
                    break;

                default:
                    break;
            }

        } catch (JSONException e) {
            Log.d("onErrorResponse :", e.getMessage());
            // e.printStackTrace();
        }
    }

    @Override
    public void onServerError(int requestId, String error) {
        //implemented interface method onServerError
        //it return  data as String to display
        if (requestId == DELETE_GAME_REQUEST_ID || requestId == SHARE_GAME_REQUEST_ID) {
            showFailedDialog(error);
        } else
            checkDataAvailability();


    }

    public void deleteGame(String gameID) {
        Map<String, String> parm = new HashMap<String, String>();
        parm.put("gameId", gameID);
        parm.put("consultantId", SessionManager.getInstance(getActivity()).getKeyUserId());
        parm.put("op", DELETE_GAME);
        Server.getInstance(getActivity()).post(parm, Url.URL_GAME, DELETE_GAME_REQUEST_ID, this);
        showProgressDialog(getActivity(), getResources().getString(R.string.do_operation_message));
    }


    @Override
    public void onEdit(GameItem game) {
        Intent intent = new Intent(getActivity(), GameEditActivity.class);
        intent.putExtra("game", game);
        startActivity(intent);
    }

    @Override
    public void onDelete(String gameID) {

        String title = getResources().getString(R.string.confirm_title);
        String mess = getResources().getString(R.string.game_delete_message_confirm);
        ConfirmationDialog.showConfirmationDialog(getActivity(), title, mess, () -> deleteGame(gameID));

    }

    @Override
    public void onShare(String gameID) {
        this.gameID = gameID;
        if (patientList.isEmpty()) {
            loadUsers();
        } else {
            showShareDialog();
        }
    }

    public void loadUsers() {
        Map<String, String> parms = new HashMap<String, String>();
        parms.put("op", "LoadUser");
        Server.getInstance(getActivity()).get(parms, Url.URL_USER_MANAGER, LOAD_PATIENT_LIST_REQUEST_ID, this);
        showProgressDialog(getActivity(), getResources().getString(R.string.do_operation_message));


    }

    public void showShareDialog() {
        ArrayAdapter<PatientItem> userArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, patientList);
        userArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerDialog.showShareDialog(getActivity(), userArrayAdapter, this);
    }

    public void shareGame(String gameID, String userID) {
        Map<String, String> parm = new HashMap<String, String>();
        parm.put("gameId", gameID);
        parm.put("userId", userID);
        parm.put("op", ADD_GAME_TO_USER);
        Server.getInstance(getActivity()).post(parm, Url.URL_GAME, SHARE_GAME_REQUEST_ID, this);
        showProgressDialog(getActivity(), getResources().getString(R.string.do_operation_message));
    }


    @Override
    public void onUserSelected(String selectedUser) {
        shareGame(gameID, selectedUser);
    }
}
package com.saudi.remindme.consultant;


import static com.saudi.remindme.process.ProcessId.LOAD_MY_QUERY;
import static com.saudi.remindme.process.ProcessId.QUERY_REPLY;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.saudi.remindme.R;
import com.saudi.remindme.Url;
import com.saudi.remindme.account.SessionManager;
import com.saudi.remindme.admin.ui.model.PatientItem;
import com.saudi.remindme.consultant.ui.adapter.ChatAdapter;
import com.saudi.remindme.process.Server;
import com.saudi.remindme.user.ListActivity;
import com.saudi.remindme.user.ui.model.QueryItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends ListActivity implements ChatAdapter.QueryInterface {


    PatientItem patient;
    List<QueryItem> mQueryItems = new ArrayList<>();
    ChatAdapter chatAdapter;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_reply);

        mContext = ChatActivity.this;
        Intent intent = getIntent();
        patient = (PatientItem) intent.getSerializableExtra("patient");
        setToolbar();
        initView();


        loadDataList(Url.URL_QUERY, getParams("LoadQueries"), LOAD_MY_QUERY);

    }

    protected void setToolbar() {
        androidx.appcompat.widget.Toolbar mToolbar = findViewById(R.id.my_toolbar);
        TextView title = findViewById(R.id.UserTextView);
        ShapeableImageView profileImageView = findViewById(R.id.profileImageView);

        title.setText(patient.getName());


        if (patient.getGender().equalsIgnoreCase("Male")) {
            profileImageView.setBackground(mContext.getDrawable(R.drawable.c_m_profile));
        } else
            profileImageView.setBackground(mContext.getDrawable(R.drawable.female_profile));

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initView() {
        super.initView();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        emptyView = findViewById(R.id.empty_view);

    }


    protected Map<String, String> getParams(String operation) {

        Map<String, String> parms = new HashMap<String, String>();
        parms.put("userId", patient.getId());
        parms.put("consultantId", SessionManager.getInstance(mContext).getKeyUserId());
        parms.put("op", operation);
        return parms;
    }

    @Override
    public void onServerSuccess(int requestId, JSONObject responseObj) {
        //implemented interface method onServerSuccess
        //it return  data as json  object

        try {
            hideProgressDialog();


            mQueryItems.clear();
            JSONArray queryList = responseObj.getJSONArray("QueryList");
            for (int i = 0; i < queryList.length(); i++) {
                JSONObject query = queryList.getJSONObject(i);

                String id = query.getString("queryid");
                String text = query.getString("querytext");
                String querydate = query.getString("querydate");
                String queryreply = query.getString("queryreply");
                String replydate = query.getString("replydate");
                QueryItem queryModel = new QueryItem(id, text, querydate, queryreply, replydate);

                mQueryItems.add(queryModel);

            }


            checkDataAvailability();


            chatAdapter = new ChatAdapter(mContext, mQueryItems);


            recyclerView.setAdapter(chatAdapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onServerError(int requestId, String error) {
        if (requestId == QUERY_REPLY) {
            showFailDialog(error);
        } else
            checkDataAvailability();


    }

    public void checkDataAvailability() {
        if (mQueryItems.isEmpty()) {
            showNoDataAvailableMessage();
        } else {
            showDataAvailable();
        }
    }


    public void showReplyDialog(String queryID) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.reply_dailog, null);
        dialogBuilder.setView(dialogView);

        EditText editTextReply = dialogView.findViewById(R.id.editTextReply);
        ImageButton buttonCancel = dialogView.findViewById(R.id.buttonCancel);
        Button buttonConfirm = dialogView.findViewById(R.id.buttonConfirm);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        //send sms message from smart watch without open message app
        buttonCancel.setOnClickListener(view -> {
            alertDialog.dismiss();
        });

        buttonConfirm.setOnClickListener(view -> {
            String queryReply = editTextReply.getText().toString();
            // Handle the user's reply (e.g., save it, process it)
            if (!queryReply.isEmpty()) {
                sendReply(queryID, queryReply);
                alertDialog.dismiss();
            }

        });
    }

    public void sendReply(String queryId, String reply) {
        Map<String, String> parm = new HashMap<String, String>();
        parm.put("queryId", queryId);
        parm.put("userId", patient.getId());
        parm.put("reply", reply);
        parm.put("consultantId", SessionManager.getInstance(this).getKeyUserId());
        parm.put("op", "Reply");
        Server.getInstance(this).post(parm, Url.URL_QUERY, QUERY_REPLY, this);
        showProgressDialog(this, getResources().getString(R.string.do_operation_message));
    }

    @Override
    public void onReply(String queryID) {
        showReplyDialog(queryID);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}






package com.saudi.remindme.user;


import static com.saudi.remindme.process.ProcessId.ADD_QUERY;
import static com.saudi.remindme.process.ProcessId.DELETE_QUERY;
import static com.saudi.remindme.process.ProcessId.LOAD_MY_QUERY;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatEditText;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.saudi.remindme.R;
import com.saudi.remindme.Url;
import com.saudi.remindme.account.SessionManager;
import com.saudi.remindme.admin.ui.model.ConsultantItem;
import com.saudi.remindme.process.Server;
import com.saudi.remindme.statedialog.ConfirmationDialog;
import com.saudi.remindme.user.ui.adapter.ChatAdapter;
import com.saudi.remindme.user.ui.interfaces.OnDeleteListener;
import com.saudi.remindme.user.ui.model.QueryItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends ListActivity implements View.OnClickListener, OnDeleteListener {


    AppCompatEditText text_query;
    String sQuery;
    ConsultantItem consultant;
    List<QueryItem> mQueryItems = new ArrayList<>();
    ChatAdapter chatAdapter;
    boolean isValid;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        mContext = ChatActivity.this;
        consultant = (ConsultantItem) intent.getSerializableExtra("consultant");
        setToolbar();

        initView();
        loadQueryList();

    }

    protected void setToolbar() {
        androidx.appcompat.widget.Toolbar mToolbar = findViewById(R.id.my_toolbar);
        TextView title = findViewById(R.id.UserTextView);
        ShapeableImageView profileImageView = findViewById(R.id.profileImageView);

        title.setText(consultant.getName());

        if (consultant.getProfile().equalsIgnoreCase("null")) {
            if (consultant.getGender().equalsIgnoreCase("Male")) {
                profileImageView.setBackground(mContext.getDrawable(R.drawable.c_m_profile));
            } else
                profileImageView.setBackground(mContext.getDrawable(R.drawable.female_profile));
        } else {
            Glide.with(mContext).load(Url.URL_LOAD_IMAGE + consultant.getProfile()).into(profileImageView);

        }
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initView() {
        super.initView();

        text_query = findViewById(R.id.messageEditText);
        findViewById(R.id.buttonSendQuery).setOnClickListener(this);
    }

    private void loadQueryList() {


        loadDataList(Url.URL_QUERY, getParams("LoadQueries"), LOAD_MY_QUERY);

    }

    private void sendQuery() {
        Map<String, String> parms = new HashMap<String, String>();
        parms.put("querytext", sQuery);
        parms.put("consultantId", consultant.getId());
        parms.put("userId", SessionManager.getInstance(mContext).getKeyUserId());
        parms.put("op", "Add");
        showProgressDialog(mContext, getResources().getString(R.string.do_operation_message));
        Server.getInstance(mContext).post(parms, Url.URL_QUERY, ADD_QUERY, this);
        showProgressDialog(mContext, getResources().getString(R.string.do_operation_message));
    }

    protected Map<String, String> getParams(String operation) {
        Map<String, String> parms = new HashMap<String, String>();
        parms.put("consultantId", consultant.getId());
        parms.put("userId", SessionManager.getInstance(mContext).getKeyUserId());
        parms.put("op", operation);
        return parms;
    }

    @Override
    public void onServerSuccess(int requestId, JSONObject responseObj) {
        //implemented interface method onServerSuccess
        //it return  data as json  object

        try {
            hideProgressDialog();
            if (requestId == ADD_QUERY) {
                text_query.setText("");
            }
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
        if (requestId == LOAD_MY_QUERY) {
            emptyView.setText(error);
        } else
            showFailDialog(error);


    }

    public void checkDataAvailability() {
        if (mQueryItems.isEmpty()) {
            showNoDataAvailableMessage();
        } else {
            showDataAvailable();
        }
    }

    private void isValidate() {
        isValid = true;
        sQuery = text_query.getText().toString();


        if (sQuery.equals("")) {
            isValid = false;
            Toast.makeText(mContext, "you can't send empty message", Toast.LENGTH_SHORT).show();

        } else {

            text_query.setText("");
        }


    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.buttonSendQuery) {
            isValidate();

            if (isValid) {

                sendQuery();
            }

        }


    }

    public void deleteQuery(String queryId) {
        Map<String, String> parm = new HashMap<String, String>();
        parm.put("queryId", queryId);
        parm.put("userId", SessionManager.getInstance(mContext).getKeyUserId());
        parm.put("consultantId", consultant.getId());

        parm.put("op", "Delete");
        //Server server=new Server(mContext);
        Server.getInstance(mContext).post(parm, Url.URL_QUERY, DELETE_QUERY, this);
        showProgressDialog(mContext, getResources().getString(R.string.do_operation_message));
    }


    @Override
    public void onDelete(String queryID) {

        String title = getResources().getString(R.string.confirm_title);
        String mess = getResources().getString(R.string.query_delete_message_confirm);
        ConfirmationDialog.showConfirmationDialog(mContext, title, mess, () -> deleteQuery(queryID));
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






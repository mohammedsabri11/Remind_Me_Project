package com.saudi.remindme.consultant;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatEditText;

import com.saudi.remindme.BaseActivity;
import com.saudi.remindme.R;
import com.saudi.remindme.Url;
import com.saudi.remindme.account.SessionManager;
import com.saudi.remindme.consultant.ui.model.GameItem;
import com.saudi.remindme.process.IResult;
import com.saudi.remindme.process.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GameEditActivity extends BaseActivity implements View.OnClickListener, IResult {
    private AppCompatEditText name, description, link;
    private GameItem game;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_game);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mContext = this;
        init();
    }

    private void init() {
        name = findViewById(R.id.editTextGameName);
        description = findViewById(R.id.editTextDescription);
        link = findViewById(R.id.editTextGameLink);

        Intent intentPrev = getIntent();
        game = (GameItem) intentPrev.getSerializableExtra("game");

        setCurrentValue();

        findViewById(R.id.buttonEditGame).setOnClickListener(this);
    }

    private void setCurrentValue() {
        name.setText(game.getName());
        link.setText(game.getLink());
        description.setText(game.getDescription());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonEditGame) {
            validateInput();
        }
    }

    private void validateInput() {
        boolean isValid = true;
        isValid = isValid && isFieldNotEmpty(name, getResources().getString(R.string.error_empty_game_name));
        isValid = isValid && isFieldNotEmpty(description, getResources().getString(R.string.error_empty_game_description));
        isValid = isValid && isFieldNotEmpty(link, getResources().getString(R.string.error_empty_game_link));

        if (isValid) {
            editGame();
        }
    }

    private boolean isFieldNotEmpty(EditText field, String error) {
        String value = field.getText().toString();
        if (value.isEmpty()) {
            field.setError(error);
            field.requestFocus();
            return false;
        }
        return true;
    }

    private void editGame() {
        showProgressDialog(mContext, getResources().getString(R.string.do_operation_message));
        Server.getInstance(mContext).post(getParameters(), Url.URL_GAME, 1, this);
    }

    private Map<String, String> getParameters() {
        Map<String, String> params = new HashMap<>();
        params.put("gameId", game.getId());
        params.put("name", name.getText().toString());
        params.put("description", description.getText().toString());
        params.put("link", link.getText().toString());
        params.put("consultantId", SessionManager.getInstance(mContext).getKeyUserId());
        params.put("op", "Edit");
        return params;
    }

    @Override
    public void onServerSuccess(int requestId, JSONObject jsonObject) {


        try {
            showSuccessDialog(jsonObject.getString("msg"));


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServerError(int requestId, String error) {

        showFailDialog(error);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        Intent intent = new Intent(mContext, MainConsultantActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}











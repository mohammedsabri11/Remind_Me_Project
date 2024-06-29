package com.saudi.remindme.admin.ui.fragment;

import static com.saudi.remindme.process.Constant.PROCESS_MASSEGE;
import static com.saudi.remindme.process.Process.ACTIVATE_USER;
import static com.saudi.remindme.process.Process.DE_ACTIVATE_USER;
import static com.saudi.remindme.process.ProcessId.CHANGE_CONSULTANT_STATE_REQUEST_ID;
import static com.saudi.remindme.process.ProcessId.LOAD_CONSULTANT_REQUEST_ID;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.saudi.remindme.R;
import com.saudi.remindme.Url;
import com.saudi.remindme.admin.ui.adapter.ConsultantAdapter;
import com.saudi.remindme.admin.ui.interfaces.OnUserListener;
import com.saudi.remindme.admin.ui.model.ConsultantItem;
import com.saudi.remindme.process.Process;
import com.saudi.remindme.statedialog.ConfirmationDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ConsultantListFragment extends BaseUserFragment<ConsultantItem> implements OnUserListener, ConsultantAdapter.OnUser {


    private ConsultantAdapter consultantAdapter;

    public static ConsultantListFragment newInstance() {
        return new ConsultantListFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        load(Url.URL_USER_MANAGER, getParameters(Process.LOAD_CONSULTANT), LOAD_CONSULTANT_REQUEST_ID);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_consultant_list, container, false);
        itemList = new ArrayList<>();
        initView(view);
        emptyView.setOnClickListener(v -> load(Url.URL_USER_MANAGER, getParameters(Process.LOAD_CONSULTANT), LOAD_CONSULTANT_REQUEST_ID));
        return view;
    }


    public void setUserList(JSONObject jsonObject) {
        itemList.clear();
        try {


            JSONArray users = jsonObject.getJSONArray("ConsultantList");
            for (int i = 0; i < users.length(); i++) {
                String id = users.getJSONObject(i).getString("muserid");
                String name = users.getJSONObject(i).getString("fullname");
                String gender = users.getJSONObject(i).getString("gender");
                String profile = users.getJSONObject(i).getString("profile");
                String imageUrl = users.getJSONObject(i).getString("certificateImage");
                String is_Active = users.getJSONObject(i).getString("isactive");
                boolean isActive = !is_Active.equals("0");

                ConsultantItem userModel = new ConsultantItem(id, name, gender, profile, imageUrl, isActive);

                itemList.add(userModel);
            }
        } catch (JSONException e) {

            Log.d("ConsultantListFragment", "Error in setUserList for requestId: ", e);
            // Display a user-friendly error message or handle the exception appropriately
        }
    }


    @Override
    //implemented interface method onServerSuccess
    //it return  data as json  object
    public void onServerSuccess(int requestId, JSONObject jsonObject) {
        try {
            switch (requestId) {
                case CHANGE_CONSULTANT_STATE_REQUEST_ID:
                    showSuccessDialog(jsonObject.getString(PROCESS_MASSEGE));
                    load(Url.URL_USER_MANAGER, getParameters(Process.LOAD_CONSULTANT), LOAD_CONSULTANT_REQUEST_ID);
                    break;
                case LOAD_CONSULTANT_REQUEST_ID:
                    setUserList(jsonObject);

                    checkDataAvailability();
                    consultantAdapter = new ConsultantAdapter(getActivity(), itemList, this, this);

                    recyclerView.setAdapter(consultantAdapter);
                    break;
                default:
                    // Handle default case
            }
        } catch (JSONException e) {
            // Log the exception
            //logger.error("Error in onServerSuccess for requestId: " + requestId, e);
            // Display a user-friendly error message or handle the exception appropriately
            Log.d("ConsultantListFragment", "Error in onServerSuccess for requestId: " + requestId, e);
        }
    }

    @Override
    public void onServerError(int requestId, String error) {
        //implemented interface method onServerError
        //it return  data as String to display

        if (requestId == CHANGE_CONSULTANT_STATE_REQUEST_ID) {
            showFailedDialog(error);
        } else checkDataAvailability();

    }

    public void displayImageForPreview(String link) {


        final Dialog dialog = new Dialog(getActivity());

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.image_preview_dailog, null);

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);

        AppCompatImageView imageView = dialog.findViewById(R.id.imageViewCertificate);
        Glide.with(getActivity()).load(Url.URL_LOAD_IMAGE + link).into(imageView);

        dialog.setCancelable(true);
        dialog.findViewById(R.id.buttonHide).setOnClickListener(view1 -> dialog.dismiss());

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    @Override
    public void onReview(String name, String imageName) {
        displayImageForPreview(imageName);
    }

    @Override
    public void onDeActivate(String name, String userID) {

        String title = getResources().getString(R.string.confirm_title);
        String message = getResources().getString(R.string.de_activate_message_confirm);
        ConfirmationDialog.showConfirmationDialog(getActivity(), title, message, () -> updateUserStatus(userID, DE_ACTIVATE_USER));
    }

    @Override
    public void onActivate(String name, String userID) {

        String title = getResources().getString(R.string.confirm_title);
        String message = getResources().getString(R.string.activate_message_confirm);
        ConfirmationDialog.showConfirmationDialog(getActivity(), title, message, () -> updateUserStatus(userID, ACTIVATE_USER));


    }
}
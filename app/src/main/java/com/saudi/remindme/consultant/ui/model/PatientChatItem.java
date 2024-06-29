package com.saudi.remindme.consultant.ui.model;

import com.saudi.remindme.admin.ui.model.PatientItem;

import java.io.Serializable;

public class PatientChatItem implements Serializable {


    private final String lastMessage;
    public PatientItem patientItem;

    public PatientChatItem(PatientItem patientItem, String lastMessage) {
        //super(id,  name,  dob,  gender,  phone,health_details);
        this.patientItem = patientItem;
        this.lastMessage = lastMessage;

    }

    public PatientItem getPatientItem() {
        return patientItem;
    }

    public String getLastMessage() {
        return lastMessage;
    }


}

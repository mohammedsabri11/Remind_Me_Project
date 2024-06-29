package com.saudi.remindme.user.ui.model;

import com.saudi.remindme.admin.ui.model.ConsultantItem;

import java.io.Serializable;

public class ConsultantChatItem implements Serializable {


    private final ConsultantItem consultantItem;
    private String lastMessage;

    public ConsultantChatItem(ConsultantItem consultantItem, String lastMessage) {
        this.consultantItem = consultantItem;//new ConsultantItem ( id,  name,  gender,  profile,  shortDescription,  speciality,  experience,  evaluation);
        this.lastMessage = lastMessage;
    }

    public ConsultantChatItem(ConsultantItem consultantItem) {
        this.consultantItem = consultantItem;

    }

    public ConsultantItem getConsultantItem() {
        return consultantItem;
    }

    public String getLastMessage() {
        return lastMessage;
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
